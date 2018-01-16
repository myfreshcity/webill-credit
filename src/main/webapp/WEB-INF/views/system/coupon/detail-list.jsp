<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layout/comtag.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
 
<html>
<head>
<!-- BEGIN PAGE LEVEL PLUGINS -->
<link
	href="${staticBase}/assets/global/plugins/datatables/datatables.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="${staticBase}/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${staticBase}/assets/global/plugins/jquery.min.js"></script>	
<script type="text/javascript">
	/**************************************************************险种搜索****************start*************************************/
	function getPremiumList() {
	    $.ajax({
	        type: 'POST',
	        url: BASE_PATH+'premiumOrder/premiumList',
	        data: $('#getPremiumList').serialize(),
	        dataType: 'json',
	        success: function (data) {
	        	var plist = data;
	        	var tbody = "";
                var trs = "";
	        	$.each(plist,function(n,p){
	                trs = "<tr><td><input name='premiumId' type='checkbox' value="+p.id+"></td><td>"+p.prmTypeStr+"</td><td>"+p.prmName+"</td></tr>";
	                tbody += trs;
	            });
	        	$('#premiuBody').html(tbody);
	        },
	        error: function () {
	            alert("出错了,请重试!");
	        }
	    });
	}
	/**************************************************************险种搜索****************end*************************************/
	
	/**************************************************************添加险种****************start***********************************/
	function premiumSubmit() {
	    $.ajax({
	        type: 'POST',
	        url: BASE_PATH+'premiumOrder/addPremiumToOrder',
	        data: $('#premiumSubmit').serialize(),
	        dataType: 'json',
	        success: function (data) {
	        	if(data.status==200){
	        		alert(data.msg);
	        		location.reload();
	        	}
	        },
	        error: function () {
	            alert(data.msg);
	        }
	    });
	}
	/**************************************************************添加险种****************end*************************************/
	
	/**************************************************************添加配送****************start***********************************/
	function addUserContact() {
	    $.ajax({
	        type: 'POST',
	        url: BASE_PATH+'userContact/addUserContact',
	        data: $('#UserContact').serialize(),
	        dataType: 'json',
	        success: function (data) {
	        	if(data.status==200){
	        		alert(data.msg);
	        		location.reload();
	        	}
	        },
	        error: function () {
	            alert(data.msg);
	        }
	    });
	}
	/**************************************************************添加配送****************end*************************************/
	
	/**************************************************************险种列表全选****************start********************************/
    $(function() {
    	$("#allPremium").click(function() {
			var f = $(this).is(":checked");    		
    		$('input:checkbox').each(function() {
    	        $(this).prop('checked', f);
    		});
        });
     });
	/**************************************************************险种列表全选****************end*************************************/
	
	/**************************************************************确认推送****************start*************************************/
    function confrimPush(id) {
	    $.ajax({
	        type: 'GET',
	        url: BASE_PATH+'premiumOrder/confirmPush/?orderId='+id,
	        dataType: 'json',
	        success: function (data) {
	        	if(data.status==200){
	        		alert(data.msg);
	        	}else if(data.status==400){
	        		alert(data.msg);
	        	}else if(data.status==500){
	        		alert(data.msg);
	        	}
	        },
	        error: function () {
	            alert(data.msg);
	        }
	    });
	}
	/**************************************************************确认推送****************end*************************************/
	/**************************************************************删除投保方案****************start*************************************/
    function delPremium(id) {
	    $.ajax({
	        type: 'GET',
	        url: BASE_PATH+'premiumOrder/delPremiumDetail/?detailId='+id,
	        dataType: 'json',
	        success: function (data) {
        		alert(data.msg);
       		    location.reload();
	        },
	        error: function () {
	            alert(data.msg);
	        }
	    });
	}
	/**************************************************************删除投保方案****************end*************************************/
	function autoCount() {
		$("input[name='pValue']").each(function() {
    		var pValue =$(this).val();
        	var detailId =$(this).prev("input[name='detailId']").val();
        	$(this).next("input[name='prmValueStr']").val(detailId+"|"+pValue);
		});
	    $.ajax({
	        type: 'POST',
	        url: BASE_PATH+'premiumOrder/autoCount',
	        data: $('#form_sample_3').serialize(),
	        dataType: 'json',
	        success: function (data) {
	        	if(data.status==500){
	        		alert(data.msg);
	        	}else{
		        	$("#ciValue").val(data.ciValue);
		        	$("#taxValue").val(data.taxValue);
		        	$("#biValue").val(data.biValue);
		        	$("#discountMoney").val(data.discountMoney);
		        	$("#prmValue").val(data.prmValue);
	        	}
			},
			error : function() {
				alert(data.msg);
			}
		});
	}
</script>
<!-- END PAGE LEVEL PLUGINS -->
</head>
<body>
<form action="#" id="form_sample_3" class="form-horizontal" >
	<!-- BEGIN PAGE TITLE-->
	<div style="float: left; margin: 3px;">
		<h3 class="page-title">订单详情</h3>
	</div>
		
	<button type="button" style="float: right; margin: 20px 5px; padding: 2px 10px; line-height: 30px; background-color: red; color: white;" id="cancel">返回</button>
	<button type="button" style="float: right; margin: 20px 5px; padding: 2px 10px; line-height: 30px; background-color: red; color: white;">选择优惠券</button>
	<button type="button" style="float: right; margin: 20px 5px; padding: 2px 10px; line-height: 30px; background-color: red; color: white;">获取保费</button>
	<button type="button" style="float: right; margin: 20px 5px; padding: 2px 10px; line-height: 30px; background-color: red; color: white;">修改车辆信息</button>
	<button type="button" style="float: right; margin: 20px 5px; padding: 2px 10px; line-height: 30px; background-color: red; color: white;" data-toggle="modal" data-target="#myModal">添加险种</button>
	<button type="submit" style="float: right; margin: 20px 5px; padding: 2px 10px; line-height: 30px; background-color: red; color: white;">保存</button>
	<button type="button" id="push" onclick="confrimPush(${po.id})" style="float: right; margin: 20px 5px; padding: 2px 10px; line-height: 30px; background-color: red; color: white;">确认推送</button>
		
	
	<!-- END PAGE TITLE-->
	<div class="row">
		<div class="col-md-12">
			<!-- Begin: life time stats -->
			<div class="portlet light portlet-fit portlet-datatable bordered">

				<div class="portlet-body">
					<h4>车主信息</h4>
						<div class="form-body">
							<div class="alert alert-danger display-hide">
								<button class="close" data-close="alert"></button>
								部分项目填写有误，详细见下
							</div>
							<div class="alert alert-success display-hide">
								<button class="close" data-close="alert"></button>
								信息添加成功!
							</div>
							<input type="text" name="id" value="${po.id}" style="display:none"/>
							<div class="form-group">
								<label class="control-label col-md-3">车牌号 <span
									class="required"> * </span>
								</label>
								<div class="col-md-2">
									<input type="text" name="licenseNo" value="${po.licenseNo}"
										data-required="1" class="form-control"
										onkeyup="this.value=value.replace(/^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}/g,'')" />
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-md-3">车型 <span
									class="required"> * </span>
								</label>
								<div class="col-md-2">
									<input type="text" name="brandName" value="${po.brandName}"
										data-required="1" class="form-control" />
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-md-3">微信昵称 <span
									class="required"> * </span>
								</label>
								<div class="col-md-2">
									<input type="text" name="weixinNick" value="${po.weixinNick}"
										data-required="1" class="form-control"
										onkeyup="this.value=value.replace(/[^\u4E00-\u9FA5\w\\,\\，\\.\\。\\(\\)]/g,'')" />
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-md-3">联系人手机 <span
									class="required"> * </span>
								</label>
								<div class="col-md-2">
									<input type="text" name="mobile" value="${po.mobile}"
										data-required="1" class="form-control"
										onkeyup="this.value=value.replace(/[^\u4E00-\u9FA5\w\\,\\，\\.\\。\\(\\)]/g,'')" />
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-md-3">车主姓名 <span
									class="required"> * </span>
								</label>
								<div class="col-md-2">
									<input type="text" name="carOwner" value="${po.carOwner}"
										data-required="1" class="form-control"
										onkeyup="this.value=value.replace(/[^\u4E00-\u9FA5\w\\,\\，\\.\\。\\(\\)]/g,'')" />
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-md-3">车主手机 <span
									class="required"> * </span>
								</label>
								<div class="col-md-2">
									<input type="text" name="mobileNo" value="${po.mobileNo}"
										data-required="1" class="form-control" />
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-md-3">上年投保公司<span
									class="required"> * </span>
								</label>
								<div class="col-md-2">
									<input type="text" name="insurerCom" value="${po.insurerCom}"
										data-required="1" class="form-control" />
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-md-3">上年保费<span
									class="required"> * </span>
								</label>
								<div class="col-md-2">
									<input type="text" name="lastValue" value="${po.lastValue}"
										data-required="1" class="form-control" />
								</div>
							</div>
						</div>
						<h4>投保方案</h4>
						<div class="table-container">
							<table class="table table-striped table-bordered table-hover table-checkable">
								<thead>
									<tr role="row" class="heading">
										<th width="10%">操作</th>
										<th width="10%">保险公司</th>
										<th>保险公司简介</th>
										<th width="10%">折扣比例</th>
										<th width="10%">报价链接</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td width="10%"><input type="radio" name="ciInsurerCom" checked="checked" value="阳光保险" <c:if test="${po.ciInsurerCom=='阳光保险'}"> checked="checked"</c:if>/></td>
										<td width="10%">阳光保险</td>
										<td>阳光保险集团股份有限公司是国内七大保险集团之一，由中国石油化工集团公司、中国铝业公司、中国南方航空集团公司、中国外运长航集团有限公司、广东店里发展股份有限公司等大型企业集团于2005年发起组建，注册资本金45.6亿元人民币。</td>
										<td width="10%">30%</td>
										<td width="10%">www.yangguangbaoxian.com</td>
									</tr>
									<tr>
										<td width="10%"><input type="radio" name="ciInsurerCom" value="大地保险" <c:if test="${po.ciInsurerCom=='大地保险'}"> checked="checked"</c:if> /></td>
										<td width="10%">大地保险</td>
										<td>阳光保险集团股份有限公司是国内七大保险集团之一，由中国石油化工集团公司、中国铝业公司、中国南方航空集团公司、中国外运长航集团有限公司、广东店里发展股份有限公司等大型企业集团于2005年发起组建，注册资本金45.6亿元人民币。</td>
										<td width="10%">25</td>
										<td width="10%">www.dadibaoxian.com</td>
									</tr>								
									<tr>
										<td width="10%"><input type="radio" name="ciInsurerCom" value="太平洋保险" <c:if test="${po.ciInsurerCom=='太平洋保险'}"> checked="checked"</c:if> /></td>
										<td width="10%">太平洋保险</td>
										<td>阳光保险集团股份有限公司是国内七大保险集团之一，由中国石油化工集团公司、中国铝业公司、中国南方航空集团公司、中国外运长航集团有限公司、广东店里发展股份有限公司等大型企业集团于2005年发起组建，注册资本金45.6亿元人民币。</td>
										<td width="10%">25</td>
										<td width="10%">www.taipingyangbaoxian.com</td>
									</tr>								
									<tr>
										<td width="10%"><input type="radio" name="ciInsurerCom" value="人民保险" <c:if test="${po.ciInsurerCom=='人民保险'}"> checked="checked"</c:if> /></td>
										<td width="10%">人民保险</td>
										<td>阳光保险集团股份有限公司是国内七大保险集团之一，由中国石油化工集团公司、中国铝业公司、中国南方航空集团公司、中国外运长航集团有限公司、广东店里发展股份有限公司等大型企业集团于2005年发起组建，注册资本金45.6亿元人民币。</td>
										<td width="10%">25</td>
										<td width="10%">www.renshoubaoxian.com</td>
									</tr>								
									<tr>
										<td width="10%"><input type="radio" name="ciInsurerCom" value="平安保险" <c:if test="${po.ciInsurerCom=='平安保险'}"> checked="checked"</c:if> /></td>
										<td width="10%">平安保险</td>
										<td>阳光保险集团股份有限公司是国内七大保险集团之一，由中国石油化工集团公司、中国铝业公司、中国南方航空集团公司、中国外运长航集团有限公司、广东店里发展股份有限公司等大型企业集团于2005年发起组建，注册资本金45.6亿元人民币。</td>
										<td width="10%">25</td>
										<td width="10%">www.pingan.com</td>
									</tr>								
								</tbody>
							</table>
						</div>
						
						<c:if test="${po.jcpdlist!=null&&fn:length(po.jcpdlist)>0}"><h4>基础险</h4></c:if>
						<table class="table table-striped table-bordered table-hover table-checkable">
							<thead>
								<c:forEach items="${po.jcpdlist}" var="jcpd">
									<tr>
										<td width="25%">${jcpd.prmName}</td>
										<td width="25%">
											<c:set value="${fn:split(jcpd.premiumType,',') }" var="premiumTypes"></c:set>
											<select name="amountstr">
												<c:forEach items="${premiumTypes}" var="pt" varStatus="x">
													<c:if test="${x.first}">
														<c:if test="${fn:contains(pt,'万')}">
															<option value="" onclick="">其他</option>	
														</c:if>
													</c:if>
													<option value="${jcpd.id}|${pt}">${pt}</option>
												</c:forEach>
											</select>
										</td>
										<td>
											<input name="detailId" type="text" value="${jcpd.id}" style="display:none;">
											<input name="pValue" type="text" value="${jcpd.prmValue}"> 元
											<input name="prmValueStr" type="text" value="${jcpd.id}|${fjpd.prmValue}" style="display:none;"> 
									    </td>
									    <td width="25%"><button type="button" onclick="delPremium(${jcpd.id})" style="background-color: red; color: white;width:60px;">删除</button></td>
									</tr>
								</c:forEach>
							</thead>
						</table>
						<c:if test="${po.fjpdlist!=null&&fn:length(po.fjpdlist)>0}"><h4>附加险</h4></c:if>
						<table class="table table-striped table-bordered table-hover table-checkable">
							<thead>
								<c:forEach items="${po.fjpdlist}" var="fjpd">
									<tr>
										<td width="25%">${fjpd.prmName}</td>
										<td width="25%">
											<c:set value="${fn:split(fjpd.premiumType,',') }" var="premiumTypes"></c:set>
											<select name="amountstr">
												<c:forEach items="${premiumTypes}" var="pt">
													<option value="${fjpd.id}|${pt}">${pt}</option>
												</c:forEach>
											</select>
										</td>
										<td>
											<input name="detailId" type="text" value="${fjpd.id}" style="display:none;">
											<input name="pValue" type="text" value="${fjpd.prmValue}"> 元
											<input name="prmValueStr" type="text" value="${fjpd.id}|${fjpd.prmValue}" style="display:none;"> 
									    </td>
									    <td width="25%"><button type="button" onclick="delPremium(${fjpd.id})" style="background-color: red; color: white;width:60px;">删除</button></td>
									</tr>
								</c:forEach>
							</thead>
						</table>
						<c:if test="${po.jqpdlist!=null&&fn:length(po.jqpdlist)>0}"><h4>交强险</h4></c:if>
						<table class="table table-striped table-bordered table-hover table-checkable">
							<thead>
								<c:forEach items="${po.jqpdlist}" var="jqpd">
									<tr>
										<td width="25%">${jqpd.prmName}</td>
										<td width="25%">
											<c:set value="${fn:split(jqpd.premiumType,',') }" var="premiumTypes"></c:set>
											<select name="amountstr">
												<c:forEach items="${premiumTypes}" var="pt">
													<option value="${jqpd.id}|${pt}">${pt}</option>
												</c:forEach>
											</select>
										</td>
										<td>
											<input name="detailId" type="text" value="${jqpd.id}" style="display:none;">
											<input name="pValue" type="text" value="${jqpd.prmValue}"> 元
											<input name="prmValueStr" type="text" value="${jqpd.id}|${jqpd.prmValue}" style="display:none;"> 
									    </td>
									    <td width="25%"><button type="button" onclick="delPremium(${jqpd.id})" style="background-color: red; color: white;width:60px;">删除</button></td>
									</tr>
								</c:forEach>
							</thead>
						</table>
						<div style="float: left; margin: 3px;">
							<h4>保费</h4>
						</div>
						<button type="button" style="float: right; margin: 10px 5px; padding: 3px 10px; line-height: 30px; background-color: red; color: white;" onclick="autoCount()">自动计算</button>
						<table class="table table-striped table-bordered table-hover table-checkable">
							<thead>
								<tr>
									<td width="30%">交强险金额：</td>
									<td><input name="ciValue" type="text" id="ciValue" value="${po.ciValue}" readonly="readonly" style="border: 0;"> 元</td>
								</tr>
								<tr>
									<td width="30%">车船税金额：</td>
									<td><input name="taxValue" type="text" id="taxValue" value="${po.taxValue}" readonly="readonly" style="border: 0;"> 元</td>
								</tr>
								<tr>
									<td width="30%">商业险金额：</td>
									<td><input name="biValue" type="text" id="biValue" value="${po.biValue}" readonly="readonly" style="border: 0;"> 元</td>
								</tr>
								<tr>
									<td width="30%">折扣比例：</td>
									<td><input name="discount" type="text" value="${po.discount}" style="border-color: red;"> %</td>
								</tr>
								<tr>
									<td width="30%">折扣金额：</td>
									<td><input name="discountMoney" type="text" id="discountMoney" value="${po.discountMoney}" readonly="readonly" style="border: 0;"> 元</td>
								</tr>
								<tr style="display:none">
									<td width="30%">优惠券金额：</td>
									<td><input name="couponMoney" type="text" value="" style="border: 0;"> 元</td>
								</tr>
								<tr>
									<td width="30%">实付金额：</td>
									<td><input name="prmValue" type="text" id="prmValue" value="${po.prmValue}" readonly="readonly" style="border: 0;"> 元</td>
								</tr>
							</thead>
						</table>
						<h4>投保时间</h4>
						<table class="table table-striped table-bordered table-hover table-checkable">
							<thead>
								<tr>
									<td width="30%">交强险生效时间：</td>
									<td>
										<div class="input-group date date-picker margin-bottom-1" style="width:150px;" data-date-format="yyyy-mm-dd">
											<input type="text" class="form-control form-filter input-sm" readonly name="ciStartDate" placeholder="From" value="<fmt:formatDate value='${po.ciStartDate}' pattern='yyyy-MM-dd'/>"> 
											<span class="input-group-btn">
												<button class="btn btn-sm default" type="button">
													<i class="fa fa-calendar"></i>
												</button>
											</span>
										</div>
									</td>
								</tr>
								<tr>
									<td width="30%">商业险生效时间：</td>
									<td>
										<div class="input-group date date-picker margin-bottom-1" style="width:150px;" data-date-format="yyyy-mm-dd">
											<input type="text" class="form-control form-filter input-sm" readonly name="biStartDate" placeholder="From" value="<fmt:formatDate value='${po.biStartDate}' pattern='yyyy-MM-dd'/>"> 
											<span class="input-group-btn">
												<button class="btn btn-sm default" type="button">
													<i class="fa fa-calendar"></i>
												</button>
											</span>
										</div>									
									</td>
								</tr>
							</thead>
						</table>
						<h4>投保信息</h4>
						<table class="table table-striped table-bordered table-hover table-checkable">
							<thead>
								<tr>
									<td width="30%">车主姓名：</td>
									<td><input name="ownerName" type="text" value="${po.ownerName}"></td>
									<td width="30%">车主身份证号：</td>
									<td><input name="ownerIdNo" type="text" value="${po.ownerIdNo}"></td>
								</tr>
								<tr>
									<td width="30%">投保人姓名：</td>
									<td><input name="applicantName" type="text" value="${po.applicantName}"></td>
									<td width="30%">投保人身份证号：</td>
									<td><input name="applicantIdNo" type="text" value="${po.applicantIdNo}"></td>
								</tr>
								<tr>
									<td width="30%">被保人姓名：</td>
									<td><input name="insuredName" type="text" value="${po.insuredName}"></td>
									<td width="30%">被保人身份证号：</td>
									<td><input name="insuredIdNo" type="text" value="${po.insuredIdNo}"></td>
								</tr>
							</thead>
						</table>
						<div style="float: left; margin: 3px;">
							<h4>配送信息</h4>
						</div>
						<button type="button" style="float: right; margin: 10px 5px; padding: 3px 10px; line-height: 30px; background-color: red; color: white;" data-toggle="modal" data-target="#myUserContact">添加</button>
						<table class="table table-striped table-bordered table-hover" id="premiumList">
							<c:if test="${not empty (po.uclist)}">
								<thead>
									<tr role="row" class="heading">
										<th width="10%"></th>
										<th width="15%">联系人姓名</th>
										<th width="15%">联系电话</th>
										<th width="15%">电子邮箱</th>
										<th width="15%">所在地区</th>
										<th>详细地址</th>
									</tr>
								</thead>
							</c:if>
							<tbody id="ucBody">
								<c:forEach items="${po.uclist}" var="uc">
									<tr>
										<td>
											<input type="radio" name="contactId" value="${uc.id}" 
												<c:if test="${uc.isDefault==1}"> checked="checked" </c:if>
											/>
										</td>
										<td>${uc.contactName}</td>
										<td>${uc.mobile}</td>
										<td>${uc.email}</td>
										<td>${uc.province}${uc.city}</td>
										<td>${uc.address}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>						
						<h4>保单信息</h4>
						<table class="table table-striped table-bordered table-hover table-checkable">
							<thead>
								<tr>
									<td width="30%">投保单号：</td>
									<td><input name="preInsureNo" type="text" value="${po.preInsureNo}"></td>
								</tr>
								<tr>
									<td width="30%">保单号：</td>
									<td><input name="insureNo" type="text" value="${po.insureNo}"></td>
								</tr>
								<tr>
									<td width="30%">保单交易号：</td>
									<td><input name="transacNo" type="text" value="${po.transacNo}"></td>
								</tr>
							</thead>
						</table>
						<h4>备注</h4>
						<table class="table table-striped table-bordered table-hover table-checkable">
							<thead>
								<tr>
								<td>
								<textarea rows="5" cols="200" name="remark">${po.remark}</textarea>
								</td>
								</tr>
							</thead>
						</table>												
						
					
				</div>
			</div>
		</div>
	</div>
</form>	
	
	
<!-------------------------------------------------------- 险种列表--------------------------------------start---------------------------------------->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h3 class="modal-title" id="myModalLabel">险种列表</h3>
      </div>
      <div class="modal-body">

		<!-- END PAGE TITLE-->
		<div class="row">
			<div class="col-md-12">
				<!-- Begin: life time stats -->
				<div class="portlet light portlet-fit portlet-datatable bordered">
					<div class="portlet-body">
						<div class="table-container">
							<form action="" id="getPremiumList">
								<div class="form horizontal-form">
									<div class="form-body">
										<div class="form-group">
											<label class="control-label col-md-1" style="width:100px;">险种类型</label>
											<div class="col-md-2">
												<select name="prmType" style="width:80px;">
													<option value="">-----</option>
													<option value="1">基础险</option>
													<option value="2">附加险</option>
													<option value="3">交强险</option>
												</select>
											</div>
										</div>
										<div class="form-group">
											<label class="control-label col-md-1" style="width:100px;">险种名称 </label>
											<div class="col-md-2">
												<input name="orderId" type="text" value="${po.id}" style="display:none"/>
												<input type="text" name="prmName" value="${prmType}" onkeyup="this.value=value.replace(/[^\u4E00-\u9FA5\w\\,\\，\\.\\。\\(\\)]/g,'')" />
											</div>
										</div>
									</div>
									<div class="form-actions right">
										<input type="button" value="搜索" onclick="getPremiumList()"/> 
									</div>
									<!-- END FORM-->
								</div>
							</form>
							<form action="${base}/premiumOrder/addPremiumToOrder" method="POST" id="premiumSubmit">
								<table class="table table-striped table-bordered table-hover" id="premiumList">
									<thead>
										<tr role="row" class="heading">
											<th width="20%"><input type="checkbox" id="allPremium"/></th>
											<th width="30%">险种类型</th>
											<th>险种名称</th>
										</tr>
									</thead>
									<tbody id="premiuBody">
										<c:forEach items="${plist}" var="p">
											<tr>
												<td><input name="premiumId" type="checkbox" value="${p.id}"></td>
												<td>${p.prmTypeStr}</td>
												<td>${p.prmName}</td>
											</tr>
										</c:forEach>
									</tbody>
									<input name="orderId" type="text" value="${po.id}" style="display:none"/>
								</table>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
					
	  </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button type="button" class="btn btn-primary" onclick="premiumSubmit()">确定</button>
      </div>
    </div>
  </div>
</div>
<!--------------------------------------------------------险种列表---------------------------end------------------------------------------------------>

<!-------------------------------------------------------- 添加配送信息--------------------------------------start---------------------------------------->
<div class="modal fade" id="myUserContact" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h3 class="modal-title" id="myModalLabel">配送信息</h3>
      </div>
      <div class="modal-body">

		<form action="#" id="UserContact" class="form-horizontal" >
			<!-- END PAGE TITLE-->
			<div class="row">
				<div class="col-md-12">
					<div class="portlet light portlet-fit portlet-datatable bordered">
						<div class="portlet-body">
							<div class="form-body">
								<input id="userId" name="userId" value="${po.userId}" style="display:none" >
								<div class="form-group">
									<label class="control-label col-md-3">微信昵称 <span
										class="required"> * </span>
									</label>
									<div class="col-md-5">
										<input type="text" name="weixinNick" value="${po.weixinNick }"
											data-required="1" class="form-control" readonly="readonly"/>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-3">收件人姓名<span
										class="required"> * </span>
									</label>
									<div class="col-md-5">
										<input type="text" name="contactName" value="${uc.contactName}"
											data-required="1" class="form-control" />
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-3">联系人手机 <span
										class="required"> * </span>
									</label>
									<div class="col-md-5">
										<input type="text" name="mobile" value="${uc.mobile}" data-required="1" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-3">电子邮箱 <span
										class="required"> * </span>
									</label>
									<div class="col-md-5">
										<input type="text" name="email" value="${uc.email}" data-required="1" class="form-control" />
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-3">所在地区<span
										class="required"> * </span>
									</label>
									<div class="col-md-5">
										<input type="text" name="city" value="${uc.city}"
											data-required="1" class="form-control" />
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-3">详细地址<span
										class="required"> * </span>
									</label>
									<div class="col-md-5">
										<input type="text" name="address" value="${uc.address}"
											data-required="1" class="form-control" />
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</form>
					
	  </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button type="button" class="btn btn-primary" onclick="addUserContact()">确定</button>
      </div>
    </div>
  </div>
</div>
<!--------------------------------------------------------添加配送信息---------------------------end------------------------------------------------------>
</body>
<content tag="pageJs"> <!-- BEGIN PAGE LEVEL PLUGINS --> 
<script language="javascript">
</script>




<script src="${staticBase}/assets/global/plugins/select2/js/select2.full.min.js" type="text/javascript"></script>
<script src="${staticBase}/assets/global/plugins/bootstrap-select/js/bootstrap-select.min.js" type="text/javascript"></script>
<script src="${staticBase}/assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" type="text/javascript" charset="UTF-8"></script>
<script src="${staticBase}/assets/global/plugins/datatables/datatables.min.js" type="text/javascript"></script> 
<script src="${staticBase}/assets/layouts/layout/scripts/table-datatables-managed.min.js"></script>
<script src="${staticBase}/assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="${staticBase}/assets/global/plugins/jquery-validation/js/additional-methods.min.js" type="text/javascript"></script>
<script src="${base}/assets/global/scripts/datatable.js" type="text/javascript"></script>
<script src="${staticBase}/assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" type="text/javascript"></script>
<script src="${staticBase}/assets/global/plugins/jquery-multi-select/js/jquery.multi-select.js" type="text/javascript"></script>
<script src="${staticBase}/assets/global/plugins/jquery.quicksearch.js" type="text/javascript"></script>
<script src="${base}/assets/pages/system/premiumOrder/detail-list.js" type="text/javascript"></script>
     <script>
        jQuery(document).ready(function() {
        	
        });
    </script>
</content>
</html>
