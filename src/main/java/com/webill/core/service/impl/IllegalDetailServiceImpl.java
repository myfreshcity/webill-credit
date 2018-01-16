package com.webill.core.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webill.app.SystemProperty;
import com.webill.app.util.OrderUtils;
import com.webill.core.ResponseInfo;
import com.webill.core.model.CarInfo;
import com.webill.core.model.IllegalDetail;
import com.webill.core.model.IllegalOrder;
import com.webill.core.service.ICarInfoService;
import com.webill.core.service.IIllegalDetailService;
import com.webill.core.service.IIllegalOrderService;
import com.webill.core.service.mapper.IllegalDetailMapper;
import com.webill.framework.service.impl.SuperServiceImpl;

/**
 *
 * IllegalDetail 服务层接口实现类
 *
 */
@Service("illegalDetailService")
public class IllegalDetailServiceImpl extends SuperServiceImpl<IllegalDetailMapper, IllegalDetail> implements IIllegalDetailService {

	private static Log logger = LogFactory.getLog(IllegalDetailServiceImpl.class);
	
	@Autowired
	private IIllegalOrderService illegalOrderService;
	
	@Autowired
	private ICarInfoService carInfoService;
    @Autowired
    protected SystemProperty constPro;
    
	@Transactional
	@Override
	public ResponseInfo saveIIllegalDetails(List<IllegalDetail> details, IllegalOrder order) {
		boolean f = false;
		logger.info("订单表存入数据：");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("license_no", order.getLicenseNo());
		List<CarInfo> cilist = carInfoService.selectByMap(map);
		if(cilist.size()==1){
			order.setCarId(cilist.get(0).getId());
		}
		logger.info("**** IllegalOrderServiceImpl.insertSelective");
		f = illegalOrderService.insertSelective(order);
		
		// 生成订单号
		String tradeNo = OrderUtils.genOrderNo("illegal",order.getId().toString());
		logger.info("生成订单号：" + tradeNo);
		
		//更新交易编号
		order.setTradeNo(tradeNo);
		f = illegalOrderService.updateSelectiveById(order);
		
		//更新detail 表的服务费用
		IllegalDetail entity = null;
		IllegalDetail whereEntity = null;
		logger.info("**** IllegalOrderServiceImpl.updateSelective totalCount:"+details.size());
		if(details.size()>0){
			for(IllegalDetail d:details){
				entity = new IllegalDetail();
				whereEntity = new IllegalDetail();
				whereEntity.setId(d.getId());
				entity.setServerFee(Integer.valueOf(constPro.SERVER_FEE));
				f = this.updateSelective(entity, whereEntity);
				if(!f){
					break;
				}
			}
		}
		if (f) {
			return ResponseInfo.SUCCESS;
		} else {
			return ResponseInfo.ACTION_FAIL;
		}
	}

}