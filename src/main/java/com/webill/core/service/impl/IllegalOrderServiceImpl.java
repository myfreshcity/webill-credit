package com.webill.core.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;
import com.webill.app.SystemProperty;
import com.webill.app.util.DateUtil;
import com.webill.core.ResponseInfo;
import com.webill.core.model.CarInfo;
import com.webill.core.model.IllegalDetail;
import com.webill.core.model.IllegalOrder;
import com.webill.core.model.WechatTemplateMsg;
import com.webill.core.service.IIllegalDetailService;
import com.webill.core.service.IIllegalOrderService;
import com.webill.core.service.mapper.IllegalOrderMapper;
import com.webill.framework.service.impl.SuperServiceImpl;

import reactor.core.Reactor;
import reactor.event.Event;

/**
 *
 * IllegalOrder 服务层接口实现类
 *
 */
@Service("illegalOrderService")
public class IllegalOrderServiceImpl extends SuperServiceImpl<IllegalOrderMapper, IllegalOrder> implements IIllegalOrderService {
    @Autowired
    @Qualifier("rootReactor")
    private Reactor r;

	@Autowired
	private IIllegalDetailService illegalDetailService;
	
    @Autowired
    protected SystemProperty constPro; 
	

	@Override
	public Page<IllegalOrder> getIllegalOrderList(Page<IllegalOrder> page, IllegalOrder iorder) {
		List<IllegalOrder> illolist = baseMapper.getIllegalOrderList(page, iorder);
		for(IllegalOrder i:illolist){
			if(i.getStatus()==3){//状态，-1:废弃，0:待缴费，1:缴费中，2:已缴费，3:缴费失败
				i.setOrderStatus("缴费失败");
			}else if(i.getStatus()==2){
				i.setOrderStatus("已缴费");
			}else if(i.getStatus()==1){
				i.setOrderStatus("缴费中");
			}else if(i.getStatus()==0){
				i.setOrderStatus("待缴费");
			}else{
				i.setOrderStatus("废弃");
			}
		}
		page.setRecords(illolist);
		return page;
	}

	@Override
	public IllegalOrder getIllegalOrderInfo(Map<String, Object> map) {
		return baseMapper.getIllegalOrderInfo(map);
	}

	@Transactional
	@Override
	public ResponseInfo saveConfirmPay(CarInfo car) {
		ResponseInfo result = null;
		boolean f = false;
    	String str = car.getPenalizeIds().trim();
    	IllegalDetail detail = null;
    	int index = 0;
    	int count = 0;
    	String[] illstr =null;
    	if(str.length()>0){
    		illstr =str.split(",");
    		//判断所有违章处罚单号是否全部更新完成
    		for(String ids:illstr){
    			detail = new IllegalDetail();
    			index = ids.indexOf("|");
    			//要修改处罚单号
    			detail.setIllid(ids.substring(index+1));
    			//查询条件id
    			detail.setId(Integer.valueOf(ids.substring(0, index)));
    			f = illegalDetailService.updateSelectiveById(detail);
    			if(f){
    				count++;
    			}
    		}
    		if(count==illstr.length){
    			IllegalOrder illo = new IllegalOrder();
    			illo.setStatus(2);
    			illo.setId(Integer.valueOf(car.getOrderId()));
    			f = this.updateSelectiveById(illo);
    			if(f){
        			//推送模板
    				WechatTemplateMsg wtm = this.getWaitPushIllegalPay(car.getOrderId());
    				if(wtm!=null){
    					r.notify("template.send", Event.wrap(wtm));
    				}
    				result = ResponseInfo.SUCCESS;
    			}
    		}else{
    			result = ResponseInfo.ACTION_FAIL;
    		}
    	}else{
    		result = ResponseInfo.ACTION_FAIL;
    	}
    	
		return result;
	}

	@Transactional
	@Override
	public ResponseInfo savePaySuccess(String id) {
		ResponseInfo result = null;
		boolean f = false;
		// 修改订单信息到数据库
		IllegalOrder iorder = new IllegalOrder();
		IllegalOrder whereEntity = new IllegalOrder();
		whereEntity.setId(Integer.valueOf(id));
		//支付成功时候订单状态改为----缴费中
		iorder.setStatus(1);
		iorder.setUpdateTime(new Date());
		f = this.updateSelective(iorder, whereEntity);
		//更新违章详情为支付成功，即后台的缴费中----------------------------------------begin
		IllegalOrder o = this.selectById(id);
		String[] detailIds =  o.getDetailIds().split(",");
		IllegalDetail detail = null;	
		IllegalDetail detailwhere = null;
		int count = 0;
		for(String s:detailIds){
			detailwhere = illegalDetailService.selectById(s);
			detail = new IllegalDetail();
			detail.setStatus(1);
			detail.setOrderId(Integer.valueOf(id));;
			f = illegalDetailService.updateSelective(detail, detailwhere);
			if(f){
				count++;
			}
		}
		if(count==detailIds.length){
			f = true;
		}else{
			f = false;
		}
		//-------------------------------------------------------------------end
		if(f){
			result = ResponseInfo.SUCCESS;
		}else{
			result = ResponseInfo.ACTION_FAIL;
		}
		return result;
	}

	@Override
	public List<IllegalOrder> getOnlyIllegalOrderByMap(Map<String, Object> map) {
		return baseMapper.getOnlyIllegalOrderByMap(map);
	}

	@Override
	public WechatTemplateMsg getWaitPushIllegalPay(String orderId) {
		IllegalOrder order = baseMapper.getWaitPushIllegalPay(orderId);
		
		//封装违章缴费完成模板数据
		WechatTemplateMsg wtm = new WechatTemplateMsg();
		if(order != null){
			wtm.setUrl(constPro.BASE_WEIXIN_URL+"/wx/illegalOrderDetail/"+orderId);
			wtm.setTemplate_id(constPro.ILLEGAL_PAY_TEMPLATE_ID);
			wtm.setTouser(order.getOpenId());
			
			TreeMap<String, String> tradeNo = WechatTemplateMsg.item(order.getTradeNo(), "#173177");
			TreeMap<String, String> licenseNo = WechatTemplateMsg.item(order.getLicenseNo(), "#173177");
			TreeMap<String, String> updateTime = WechatTemplateMsg.item(DateUtil.getDate(order.getUpdateTime()), "#173177");
			TreeMap<String, String> count = WechatTemplateMsg.item(order.getCount().toString(), "#173177");
			TreeMap<String, String> totalCount = WechatTemplateMsg.item(order.getTotalCount().toString(), "#173177");
			
			TreeMap<String,TreeMap<String,String>> data = new TreeMap<String,TreeMap<String,String>>();
			data.put("tradeNo", tradeNo);
			data.put("licenseNo", licenseNo);
			data.put("updateTime", updateTime);
			data.put("count", count);
			data.put("totalCount", totalCount);
			wtm.setData(data);
		}
		
		return wtm;
	}
}