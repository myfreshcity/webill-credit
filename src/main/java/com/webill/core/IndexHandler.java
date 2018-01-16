package com.webill.core;

import com.alibaba.fastjson.JSON;
import com.webill.app.SystemProperty;
import com.webill.core.model.OrderLog;
import com.webill.core.model.ProductLog;
import com.webill.core.model.TOrder;
import com.webill.core.model.TemplateMsgResult;
import com.webill.core.model.User;
import com.webill.core.model.WechatTemplateMsg;
import com.webill.core.service.IMessageService;
import com.webill.core.service.IOrderLogService;
import com.webill.core.service.IProductLogService;
import com.webill.core.service.ITOrderService;
import com.webill.core.service.IUserCouponService;
import com.webill.core.service.IUserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import reactor.core.Reactor;
import reactor.event.Event;
import reactor.spring.annotation.Selector;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//事件的处理类,一般是以Hander结尾
@Component
public class IndexHandler {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    @Qualifier("rootReactor")
    private Reactor reactor;

    @Autowired
    protected SystemProperty constPro;
    
    @Autowired
    private IUserCouponService userCouponService;
    
    @Autowired
    private IMessageService messageService;
    
    @Autowired
    private IUserService userService;
    
    @Autowired
    private IProductLogService productLogService;
    
    @Autowired
    private IOrderLogService orderLogService;
    
	@Autowired
	private ITOrderService tOrderService;

    
    /**   
     * @Title: couponSendTopic   
     * @Description: 关注发放优惠券并，推送微信消息  
     * @author: WangLongFei  
     * @date: 2017年9月19日 下午5:33:32   
     * @param evt
     * @throws Exception  
     * @return: void  
     */
    @Selector(value = "coupon.send", reactor = "@rootReactor")
    public void couponSendTopic(Event<Map> evt) throws Exception {
    	boolean f = false;
    	Map<String,Object> map = evt.getData();
    	f = userCouponService.addUserCouponByEvent(map);
    	String content="";
    	if(f){
    		logger.info("fromUserName:"+map.get("fromUserName")+" get coupon success...");
    		content="欢迎关注，一个惊喜已经放入您的账户,<a href='"+constPro.BASE_WEIXIN_URL+"/wx/wapHome'>去看看</a>";
    		//发放优惠券成功，推送文字消息
        	String result = messageService.sendMessage(map.get("touser").toString(), content);
        			
    	}else{
    		content="欢迎关注，<a href='"+constPro.BASE_WEIXIN_URL+"/wx/wapHome'>去看看</a>";
    		//发放优惠券成功，推送文字消息
    		String result = messageService.sendMessage(map.get("touser").toString(), content);
    		
    	}
    }
    
    /**   
     * @Title: templateSendTopic   
     * @Description: 模板推送  
     * @author: WangLongFei  
     * @date: 2017年9月19日 下午5:30:18   
     * @param evt
     * @throws Exception  
     * @return: void  
     */
    @Selector(value = "template.send", reactor = "@rootReactor")
    public void templateSendTopic(Event<WechatTemplateMsg> evt) throws Exception {
    	WechatTemplateMsg wtm = evt.getData();
		TemplateMsgResult result = messageService.sendTemplateMessage(wtm);
    }
    
    /**   
     * @Title: messageSendTopic   
     * @Description: 推送微信消息  
     * @author: WangLongFei  
     * @date: 2017年9月19日 下午5:34:46   
     * @param evt
     * @throws Exception  
     * @return: void  
     */
    @Selector(value = "message.send", reactor = "@rootReactor")
    public void messageSendTopic(Event<Map> evt) throws Exception {
    	Map<String,Object> map = evt.getData();
    	String content="";
    	logger.info("fromUserName:"+map.get("fromUserName")+" get coupon success...");
    	content="欢迎关注，一个惊喜已经放入您的账户,<a href='"+constPro.BASE_WEIXIN_URL+"/wx/wapHome'>去看看</a>";
    	//发放优惠券成功，推送文字消息
    	String result = messageService.sendMessage(map.get("touser").toString(), content);
    }
    
    /** 
     * @Title: userSubscribeFlagUpdateTopic 
     * @Description: 用户关注，取消关注更新
     * @author: WangLongFei
     * @date: 2017年12月25日 下午2:27:33 
     * @param evt
     * @throws Exception
     * @return: void
     */
    @Selector(value = "userSubscribeFlag.update", reactor = "@rootReactor")
    public void userSubscribeFlagUpdateTopic(Event<User> evt) throws Exception {
    	User user = evt.getData();
    	Map<String,Object> map = new HashMap<String,Object>();
    	map.put("open_id", user.getOpenId());
    	List<User> uList = userService.selectByMap(map);
    	boolean f = false;
    	for (User u : uList) {
    		u.setSubscribeFlag(user.getSubscribeFlag());
    		f = userService.updateSelectiveById(u);
    		if(!f){
    			logger.info("修改userId="+u.getId()+"为"+user.getSubscribeFlag()+"时，出错！");
    			break;
    		}
		}
    }
    @Selector(value = "prodLog.onShelf", reactor =  "@rootReactor")
    public void updateOnShelf(Event<ProductLog> evt) throws Exception {
    	ProductLog pl = evt.getData();
    	productLogService.insertSelective(pl);
    }
    
    /** 
     * @Title: userUpdateTopic 
     * @Description: 更新用户信息
     * @author: WangLongFei
     * @date: 2017年12月25日 下午2:27:14 
     * @param evt
     * @throws Exception
     * @return: void
     */
    @Selector(value = "user.update", reactor = "@rootReactor")
    public void userUpdateTopic(Event<User> evt) throws Exception {
    	User user = evt.getData();
    	boolean f = userService.updateSelectiveById(user);
    	if(f){
    		logger.info("更新用户信息成功！");
    	}else{
    		logger.info("更新用户信息失败！=====>"+JSON.toJSONString(user));
    	}
    }
    
    /** 
     * @Title: saveOrderLog 
     * @Description: 订单操作日志
     * @author: WangLongFei
     * @date: 2017年12月25日 下午2:27:02 
     * @param evt
     * @throws Exception
     * @return: void
     */
    @Selector(value = "orderLog.operate", reactor =  "@rootReactor")
    public void saveOrderLog(Event<String> evt) throws Exception {
		TOrder  po = tOrderService.selectById(evt.getData());
		//保存订单日志
		OrderLog oLog = new OrderLog();
		oLog.setCreatedTime(new Date());
		oLog.setOrderId(po.getId());
		oLog.setOperatorId(po.getUserId());
		oLog.setOrderTStatus(po.gettStatus());
		oLog.setPayStatus(po.getPayStatus());
		oLog.setIssueStatus(po.getIssueStatus());
		oLog.setRemark(po.getRemark());
    	orderLogService.insertSelective(oLog);
    	logger.info("保存订单日志=====>"+JSON.toJSONString(oLog));
    }

}
