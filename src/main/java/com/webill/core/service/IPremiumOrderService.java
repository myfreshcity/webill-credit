package com.webill.core.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.repository.query.Param;

import com.baomidou.mybatisplus.plugins.Page;
import com.webill.core.ResponseInfo;
import com.webill.core.model.AskPrice;
import com.webill.core.model.PersonMsg;
import com.webill.core.model.PremiumDetail;
import com.webill.core.model.PremiumOrder;
import com.webill.framework.service.ISuperService;

/**
 *
 * PremiumOrder 数据服务层接口
 *
 */
public interface IPremiumOrderService extends ISuperService<PremiumOrder> {

	/**   
	 * @Title: savePoByHistoryOrDefault   
	 * @Description: 根据历史订单或者默认订单创建共有和私有订单  
	 * @author: WangLongFei  
	 * @date: 2017年9月13日 下午3:10:13   
	 * @param carNo			车牌号
	 * @param userId		用户ID
	 * @param insurerCom	保险公司名字
	 * @return  
	 * @return: ResponseInfo  
	 */
	ResponseInfo savePoByHistoryOrDefault(String carNo,String userId,String insurerCom);

	/**
	 * @Title: checkCarNo
	 * @Description: 校验车牌号和车主名字是否匹配
	 * @author: WangLongFei
	 * @date: 2017年6月5日 下午4:05:14
	 * @param carNo
	 *            车牌号
	 * @param username
	 *            车主姓名
	 * @return
	 * @return: ResponseInfo
	 */
	ResponseInfo checkCarNo(String carNo, String username);
	
	/**   
	 * @Title: updateOrderCanPay   
	 * @Description: 判断订单状态是否可支付，返回订单状态：
	 *						1、状态为4100（已查看）时，投保信息不完善，不可支付，不做任何操作
	 *						2、状态为4200（完成投保信息）时，修改订单状态为4400（待支付）
	 * @author: WangLongFei  
	 * @date: 2017年7月25日 下午4:40:52   
	 * @param orderId
	 * @return  
	 * @return: ResponseInfo  
	 */
	Integer updateOrderCanPay(String orderId);
	
	/**   
	 * @Title: getPremiumOrder   
	 * @Description: 根据投保订单ID获取订单信息  
	 * @author: WangLongFei  
	 * @date: 2017年7月15日 下午2:56:24   
	 * @param orderId
	 * @return  
	 * @return: String  
	 */
	String getPremiumOrder(String orderId,String carNo,String userId);

	/**   
	 * @Title: getStatusStr   
	 * @Description: 获取状态描述  
	 * @author: WangLongFei  
	 * @date: 2017年7月13日 下午1:16:07   
	 * @param status
	 * @return  
	 * @return: String  
	 */
	String getStatusStr(Integer status);
	
	/**   
	 * @Title: getBaoXianStr   
	 * @Description: 根据拼音获取保险中文  
	 * @author: WangLongFei  
	 * @date: 2017年7月18日 上午10:36:26   
	 * @param pingyin 保险拼音
	 * @return  
	 * @return: String  
	 */
	String getBaoXianStr(String pingyin);
	
	/**
	 * @Title: askPremiumPrice
	 * @Description: 询价接口
	 * @author: WangLongFei
	 * @date: 2017年6月5日 上午11:07:27
	 * @param ciInsurerCom
	 *            交强险保险人
	 * @param carNo
	 *            车牌号
	 * @param details
	 *            询价方案
	 * @return
	 * @return: String
	 */
	AskPrice askPremiumPrice(String ciInsurerCom, String carNo, List<PremiumDetail> details);

	/**
	 * @Title: updatePremiumOrderInfo
	 * @Description: 更改订单接口
	 * @author: WangLongFei
	 * @date: 2017年6月3日 上午10:39:20
	 * @param map
	 *            修改参数
	 * @return
	 * @return: boolean
	 */
	boolean updatePremiumOrderInfo(Map<String, Object> map);

	/**
	 * @Title: createNewPremiumOrderByDefault
	 * @Description: 无历史保单信息时候生成默认订单
	 * @author: WangLongFei
	 * @date: 2017年6月6日 下午2:42:50
	 * @param carNo
	 *            车牌号
	 * @param userId
	 *            用户id
	 * @param insurerCom
	 *            保险公司
	 * @return
	 * @return: ResponseInfo
	 */
	ResponseInfo addNewPremiumOrderByDefault(String carNo,String userId,String insurerCom);
	
	/**   
	 * @Title: addNewPremiumOrderByHistory   
	 * @Description: 根据历史保单创建新订单  
	 * @author: WangLongFei  
	 * @date: 2017年9月21日 上午9:29:41   
	 * @return  
	 * @return: ResponseInfo  
	 */
	ResponseInfo addNewPremiumOrderByHistory(String orderId);

	/**
	 * @Title: getLowestInfo
	 * @Description: 比较各个保险公司获取最低保险公司和最低保险价格
	 * @author: WangLongFei
	 * @date: 2017年6月6日 下午2:46:48
	 * @param carNo
	 *            车牌号
	 * @param details
	 *            投保方案
	 * @return
	 * @return: Map<String,Object> 
	 */
	Map<String, Object> getLowestInfo(String carNo, List<PremiumDetail> details);

	/**   
	 * @Title: getOnePoByMap   
	 * @Description: 根据条件获取一个对象  
	 * @author: WangLongFei  
	 * @date: 2017年6月6日 下午4:32:40   
	 * @param map 条件
	 * @return  
	 * @return: PremiumOrder  
	 */
	PremiumOrder getOnePoByMap(Map<String,Object> map);
	
	/**   
	 * @Title: getPremiumOrderList   
	 * @Description: 根据条件获取投保订单列表  
	 * @author: WangLongFei  
	 * @date: 2017年6月22日 上午9:59:59   
	 * @param page
	 * @param po
	 * @return  
	 * @return: Page<PremiumOrder>  
	 */
	Page<PremiumOrder> getPremiumOrderList(Page<PremiumOrder> page, PremiumOrder po);
	
	/**   
	 * @Title: getPremiumOrderById   
	 * @Description: 根据ID获取订单详情  
	 * @author: WangLongFei  
	 * @date: 2017年7月3日 下午5:57:59   
	 * @return  
	 * @return: PremiumOrder  
	 */
	PremiumOrder getPremiumOrderById(@Param(value = "id") String id);
	
	/**   
	 * @Title: createPremiumOrder   
	 * @Description: 创建订单  
	 * @author: WangLongFei  
	 * @date: 2017年7月5日 上午9:52:47   
	 * @param po
	 * @param pdlist
	 * @return  
	 * @return: String  
	 */
	String savePremiumOrder(PremiumOrder po,List<PremiumDetail> pdlist);
	
	/**   
	 * @Title: addPremiumToOrder   
	 * @Description: 给投保订单添加投保方案  
	 * @author: WangLongFei  
	 * @date: 2017年7月5日 上午11:31:14   
	 * @param map
	 * @return  
	 * @return: String  
	 */
	boolean addPremiumToOrder(String orderId,String premiumId);
	
	/**   
	 * @Title: addPriceToPremium   
	 * @Description: 给方案添加价格  
	 * @author: WangLongFei  
	 * @date: 2017年8月23日 下午3:18:30   
	 * @param po
	 * @return  
	 * @return: boolean  
	 */
	boolean addPriceToPremium(PremiumOrder po);
	
	/**   
	 * @Title: isDuringPremiumTime   
	 * @Description: 判断是否在保期内  
	 * @author: WangLongFei  
	 * @date: 2017年7月6日 上午11:53:43   
	 * @param licenseNo
	 * @return  
	 * @return: Integer  
	 */
	Integer isDuringPremiumTime(String licenseNo);
	
	/**   
	 * @Title: saveBackPremiumOrder   
	 * @Description: 投保订单，确认投保方案  
	 * @author: WangLongFei  
	 * @date: 2017年7月7日 下午4:11:44   
	 * @param po
	 * @return  
	 * @return: boolean  
	 */
	boolean saveBackPremiumOrder(PremiumOrder po);
	
	/**   
	 * @Title: autoCount   
	 * @Description: 计算保费  
	 * @author: WangLongFei  
	 * @date: 2017年7月18日 下午4:32:05   
	 * @param po
	 * @return  
	 * @return: PremiumOrder  
	 */
	PremiumOrder autoCount(PremiumOrder po);
	
	/**   
	 * @Title: updateConfirmPush   
	 * @Description: 确认推送  
	 * @author: WangLongFei  
	 * @date: 2017年7月14日 下午2:34:15   
	 * @param po
	 * @return  
	 * @return: boolean  
	 */
	Integer updateConfirmPush(String orderId);
	
	/**   
	 * @Title: savePaySuccess   
	 * @Description: 保存支付成功操作
	 * @author: WangLongFei  
	 * @date: 2017年7月15日 下午8:34:30   
	 * @param id
	 * @return  
	 * @return: ResponseInfo  
	 */
	ResponseInfo savePaySuccess(String id);
	
	/**   
	 * @Title: saveLookPremiumOrder   
	 * @Description: 用户查看订单操作  
	 * @author: WangLongFei  
	 * @date: 2017年7月17日 下午4:33:16   
	 * @param orderId
	 * @return  
	 * @return: PremiumOrder  
	 */
	PremiumOrder saveLookPremiumOrder(String orderId,String licenseNo,String userId);
	
	/**   
	 * @Title: saveCopyOrder   
	 * @Description: 复制公有订单为私有订单  
	 * @author: WangLongFei  
	 * @date: 2017年9月15日 上午12:11:34   
	 * @param parentOrderId		公有订单id
	 * @param userId			用户id
	 * @return  
	 * @return: PremiumOrder  
	 */
	String saveCopyOrder(String orderId,String userId);
	
	/**   
	 * @Title: updateStatus   
	 * @Description: 修改订单  
	 * @author: WangLongFei  
	 * @date: 2017年7月18日 下午5:58:19   
	 * @param orderId
	 * @param status
	 * @return  
	 * @return: boolean  
	 */
	boolean updateStatus(String orderId,Integer status);
	
	/**   
	 * @Title: updateOrderByAskPrice   
	 * @Description: 根据询价结果更新订单信息(包括订单价格和方案价格)
	 * @author: WangLongFei  
	 * @date: 2017年9月12日 下午1:48:37   
	 * @param orderId	订单id
	 * @param nList	处理后的投保方案
	 * @return  
	 * @return: boolean  
	 */
	boolean updateOrderByAskPrice(String orderId,List<PremiumDetail> nList);
	
	/**   
	 * @Title: updateInsurecom   
	 * @Description: 修改投保公司    
	 * @author: WangLongFei  
	 * @date: 2017年9月12日 下午2:02:04   
	 * @param ciInsurerCom		保险公司名字
	 * @param orderId		订单ID
	 * @return  
	 * @return: boolean  
	 */
	boolean updateInsurecom(String ciInsurerCom,String orderId);
	
	/**   
	 * @Title: updatePremiumDetail   
	 * @Description: 修改投保方案  
	 * @author: WangLongFei  
	 * @date: 2017年9月12日 上午11:56:15   
	 * @param orderId	订单ID
	 * @param nList		新投保方案
	 * @return  
	 * @return: boolean  
	 */
	boolean updatePremiumDetail(String orderId,List<PremiumDetail> nList);
	
	/**   
	 * @Title: updatePersonMsg   
	 * @Description: 修改投保人信息    
	 * @author: WangLongFei  
	 * @date: 2017年9月12日 下午2:01:34   
	 * @param orderId	订单ID
	 * @param pm		投保人信息
	 * @return  
	 * @return: boolean  
	 */
	boolean updatePersonMsg(String orderId,PersonMsg pm);
	
	/**   
	 * @Title: delPremiumOrder   
	 * @Description: 删除订单即投保方案  
	 * @author: WangLongFei  
	 * @date: 2017年9月15日 上午2:52:22   
	 * @param orderId
	 * @return  
	 * @return: boolean  
	 */
	boolean deletePremiumOrder(String orderId);
	
	/**   
	 * @Title: getWaitPushOrderList   
	 * @Description: 获取待推送的公有订单列表  
	 * @author: WangLongFei  
	 * @date: 2017年9月20日 下午4:38:15   
	 * @param po
	 * @return  
	 * @return: List<PremiumOrder>  
	 */
	List<PremiumOrder> getWaitPushOrderList(PremiumOrder po);
	
	/**   
	 * @Title: getWaitPushOrderListAgain   
	 * @Description: 获取待推送的公有订单列表 (再次推送已经推送过的，间隔天数)
	 * @author: WangLongFei  
	 * @date: 2017年9月20日 下午4:38:15   
	 * @param po
	 * @return  
	 * @return: List<PremiumOrder>  
	 */
	List<PremiumOrder> getWaitPushOrderListAgain(PremiumOrder po);
	
	/**   
	 * @Title: getWaitPushPrmPrice   
	 * @Description: 获取待推送车险报价  
	 * @author: WangLongFei  
	 * @date: 2017年9月22日 上午11:10:55   
	 * @param po
	 * @return  
	 * @return: List<PremiumOrder>  
	 */
	List<PremiumOrder> getWaitPushPrmPrice(PremiumOrder po);
	
	/**   
	 * @Title: updateConfirmPay   
	 * @Description: 确认支付  
	 * @author: WangLongFei  
	 * @date: 2017年9月22日 下午2:13:17   
	 * @param orderId
	 * @return  
	 * @return: boolean  
	 */
	boolean updateConfirmPay(String orderId,String payImgUrl);
	
	
	/**   
	 * @Title: checkDetailPrice   
	 * @Description: 判断投保方案价格是否完整程度：
	 * 					全部报价成功--200；
	 * 					商业险报价成功，交强险报价失败--300；
	 * 					交强险和车船税报价成功,商业险报价失败--400；
	 * 					全部报价失败--500；
	 * 					投保方案为空--600；
	 * @author: WangLongFei  
	 * @date: 2017年10月16日 下午4:12:40   
	 * @param orderId
	 * @return  
	 * @return: Integer  
	 */
	Integer checkDetailPrice(String orderId);
	
	/**   
	 * @Title: getWaitPushPrmIssue   
	 * @Description: 车险出单成功通知  
	 * @author: WangLongFei  
	 * @date: 2017年9月25日 上午9:38:27   
	 * @param id
	 * @return  
	 * @return: PremiumOrder  
	 */
	PremiumOrder getWaitPushPrmIssue(String id);
}