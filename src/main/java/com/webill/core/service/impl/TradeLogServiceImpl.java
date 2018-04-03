package com.webill.core.service.impl;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import com.webill.app.SystemProperty;
import com.webill.app.util.LianLianUtil;
import com.webill.app.util.TransNoUtil;
import com.webill.core.model.EmailBean;
import com.webill.core.model.InfoGoods;
import com.webill.core.model.TradeLog;
import com.webill.core.model.User;
import com.webill.core.model.lianlianEntity.CertPayWebReq;
import com.webill.core.model.lianlianEntity.RiskBean;
import com.webill.core.service.IInfoGoodsService;
import com.webill.core.service.ISendEmailService;
import com.webill.core.service.ITradeLogService;
import com.webill.core.service.IUserService;
import com.webill.core.service.mapper.TradeLogMapper;
import com.webill.framework.common.JSONUtil;
import com.webill.framework.service.impl.SuperServiceImpl;

/**
 *
 * TradeLog 服务层接口实现类
 *
 */
@Service
public class TradeLogServiceImpl extends SuperServiceImpl<TradeLogMapper, TradeLog> implements ITradeLogService {
	@Autowired
    private SystemProperty constPro;
	@Autowired
	private IUserService userService;
	@Autowired
	private IInfoGoodsService infoGoodsService;
	@Autowired
	private ISendEmailService sendEmailService;

	/**
	 * 标准支付：认证支付请求数据
	 */
	@Override
	public CertPayWebReq certPayRequest(HttpServletRequest req, Integer userId, Integer infoGoodsId){
		User user = userService.selectById(userId);
		InfoGoods infoGoods = infoGoodsService.selectById(infoGoodsId);
		String transNo = TransNoUtil.genRzTransNo(); //交易流水号
		Date date = new Date(); //订单时间
		
		// 交易流水入库
		TradeLog tl = new TradeLog();
		tl.setUserId(userId);
		tl.setTransNo(transNo);
		tl.setPayType(0); //交易类型：0-用户报告 1-会员
		tl.setPayDirec(0); //交易方向：0-线上支付 1-线下支付
		tl.setPayWay(2); //支付方式：0-其他 1-支付宝 2-银联支付
		tl.setPayStatus(0); //支付状态： 0-未支付 1-支付成功 2-支付失败
		tl.setPrice(infoGoods.getPrice());
		tl.setMsgKey(infoGoods.getId().toString());
		tl.setCreatedTime(date);
		this.insertSelective(tl);
		
		// 构造认证支付请求对象
        CertPayWebReq cpwr = new CertPayWebReq();
        cpwr.setVersion(constPro.LIANLIANPAY_VERSION);
        cpwr.setOid_partner(constPro.LIANLIANPAY_CERT_PAETNER);
        cpwr.setUser_id(userId.toString());
        cpwr.setBusi_partner(constPro.LIANLIANPAY_BUSI_PARTNER);
        cpwr.setNo_order(transNo);
        cpwr.setDt_order(new SimpleDateFormat("yyyyMMddHHmmss").format(date));
        cpwr.setName_goods("微账房-信息报告购买"); // 商品名称
        cpwr.setInfo_order("微账房-信息报告购买"); // 订单备注信息，在异步通知中会回传给商户系统
        if (constPro.IS_PRODUCT) {
        	cpwr.setMoney_order(new DecimalFormat("0.00").format(infoGoods.getPrice().doubleValue()/100));
		} else {
			cpwr.setMoney_order(new DecimalFormat("0.00").format(0.01));
		}
        cpwr.setNotify_url(constPro.LIANLIANPAY_RETURN_URL + "/api/trade/notifyUrl");
        cpwr.setUrl_return(constPro.LIANLIANPAY_RETURN_URL + "/api/trade/urlReturn");
        cpwr.setUserreq_ip(LianLianUtil.getIpAddr(req).replaceAll("\\.", "_"));
        cpwr.setTimestamp(LianLianUtil.getCurrentDateTimeStr());
        
        cpwr.setId_type("0"); //身份证
        cpwr.setId_no(user.getIdNo()); //证件号码(必填)
        cpwr.setAcct_name(user.getRealName()); //银行账号姓名 (必填)
        // 商戶从自己系统中获取用户身份信息（认证支付必须将用户身份信息传输给连连，且修改标记flag_modify设置成1：不可修改）
        cpwr.setFlag_modify("1");
        //风控参数
        RiskBean riskJson = new RiskBean();
		riskJson.setFrms_ware_category("1008");//商品类目：生活服务
		riskJson.setUser_info_mercht_userno(userId.toString());
		riskJson.setUser_info_dt_register(new SimpleDateFormat("yyyyMMddHHMMSS").format(user.getCreatedTime()));
		riskJson.setUser_info_bind_phone(user.getMobileNo());
		String rj = JSONUtil.toJSONString(riskJson);
		cpwr.setRisk_item(rj);
		cpwr.setSign_type(constPro.LIANLIANPAY_SIGN_TYPE);
		cpwr.setSign(LianLianUtil.genSign(JSON.parseObject(JSON.toJSONString(cpwr)), constPro.LIANLIANPAY_PRIVATE_KEY));
		
		return cpwr;
	}
	
	@Override
	public void emailOrderPayParam(String orderNo, String orderDt, String orderMoney) {
		EmailBean eb = new EmailBean();
		eb.setOrderNo(orderNo);
		String orderDate = null;
		try {
			Date date = new SimpleDateFormat("yyyyMMddHHmmss").parse(orderDt);
			orderDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		eb.setOrderDt(orderDate);
		eb.setOrderMoney(orderMoney);
		eb.setSubject("微账房认证支付");
		eb.setFtlFile("orderPay");
		sendEmailService.sendEmail(eb);
	}
	
	@Override
	public Page<TradeLog> getTradeLogList(Page<TradeLog> page, TradeLog tl){
		List<TradeLog> tlList = baseMapper.getTradeLogList(page, tl);
		for (TradeLog tlog : tlList) {
			if (tlog.getCreatedTime() != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				tlog.setOrderTimeStr(sdf.format(tlog.getCreatedTime()));
			}
		}
		page.setRecords(tlList);
		return page;
	}
	
	@Override
	public Page<TradeLog> getRechargeList(Page<TradeLog> page, TradeLog tl){
		List<TradeLog> tlList = baseMapper.getRechargeList(page, tl);
		for (TradeLog tlog : tlList) {
			if (tlog.getCreatedTime() != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				tlog.setOrderTimeStr(sdf.format(tlog.getCreatedTime()));
			}
		}
		page.setRecords(tlList);
		return page;
	}
	
	/**
	 * 更新交易流水状态
	 */
	@Override
	public boolean updateTradeStatus(String transNo, String reqStr) {
		boolean f = false;
		TradeLog tl = new TradeLog();
    	tl.setTransNo(transNo);
		TradeLog tlog = this.selectOne(tl);
		tlog.setPayStatus(1); //支付状态： 0-未支付 1-支付成功 2-支付失败
		tlog.setRemark(reqStr);
		f = this.updateSelectiveById(tlog);
		return f;
	}
	
	/**
	 * 添加用户次数
	 */
	@Override
	public boolean addUserTimes(String transNo) {
		boolean f = false;
		
		TradeLog tl = new TradeLog();
    	tl.setTransNo(transNo);
		TradeLog tlog = this.selectOne(tl);
		
		InfoGoods infoGoods = infoGoodsService.selectById(tlog.getMsgKey());
		User user = new User(); 
		User userMain = userService.selectById(tlog.getUserId());
		user.setId(userMain.getId());
		if (infoGoods.getInfoLevel() == 0) { // 用户信息等级：0-基础版 1-标准版
			if (userMain.getStandardTimes() > 0) {
				user.setStandardTimes(userMain.getStandardTimes() + infoGoods.getTimes());
			}else {
				user.setStandardTimes(infoGoods.getTimes());
			}
		}else {
			if (userMain.getAdvancedTimes() > 0) {
				user.setAdvancedTimes(userMain.getAdvancedTimes() + infoGoods.getTimes());
			}else {
				user.setAdvancedTimes(infoGoods.getTimes());
			}
		}
		f = userService.updateSelectiveById(user);
		if (f) {
			tl.setId(tlog.getId());
			tl.setIsAddTimes(1); // 是否增加次数：0-未增加 1-增加成功
			f = this.updateSelectiveById(tl);
		}
		return f;
	}
	
	/**
	 * 充值-修改用户表查询次数
	 */
	@Override
	public boolean updateSelTimes(String userMobileNo, Integer infoLevel, Integer times){
		Map<String, Object> userMap = new HashMap<>();
		userMap.put("mobile_no", userMobileNo);
		List<User> userList = userService.selectByMap(userMap);
		User userMain = userList.get(0);
		
		User user = new User();
		user.setId(userMain.getId());
		
		if (infoLevel == 0) { // 用户信息等级：0-基础版 1-标准版
			if (userMain.getStandardTimes() < 0) {
				user.setStandardTimes(times);
			}else {
				user.setStandardTimes(userMain.getStandardTimes() + times);
			}
		}else if (infoLevel == 1)  {
			if (userMain.getAdvancedTimes() < 0) {
				user.setAdvancedTimes(times);
			}else {
				user.setAdvancedTimes(userMain.getAdvancedTimes() + times);
			} 
		}
		
		boolean f = userService.updateSelectiveById(user);
		return f;
	} 
}