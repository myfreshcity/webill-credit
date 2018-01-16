<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layout/comtag.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
 
<html>
<head>
        <!-- BEGIN GLOBAL MANDATORY STYLES -->
        <link href="http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700&subset=all" rel="stylesheet" type="text/css" />
        <link href="${staticBase}/assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
        <link href="${staticBase}/assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css" />
        <link href="${staticBase}/assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <link href="${staticBase}/assets/global/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css" />
        <link href="${staticBase}/assets/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet" type="text/css" />
        <!-- END GLOBAL MANDATORY STYLES -->
        <!-- BEGIN PAGE LEVEL PLUGINS -->
        <link href="${staticBase}/assets/global/plugins/bootstrap-fileinput/bootstrap-fileinput.css" rel="stylesheet" type="text/css" />
        <!-- END PAGE LEVEL PLUGINS -->
        <!-- BEGIN THEME GLOBAL STYLES -->
        <link href="${staticBase}/assets/global/css/components.min.css" rel="stylesheet" id="style_components" type="text/css" />
        <link href="${staticBase}/assets/global/css/plugins.min.css" rel="stylesheet" type="text/css" />
        <!-- END THEME GLOBAL STYLES -->
        <!-- BEGIN THEME LAYOUT STYLES -->
        <link href="${staticBase}/assets/layouts/layout/css/layout.min.css" rel="stylesheet" type="text/css" />
        <link href="${staticBase}/assets/layouts/layout/css/custom.min.css" rel="stylesheet" type="text/css" />
        <!-- END THEME LAYOUT STYLES -->

<!-- BEGIN PAGE LEVEL PLUGINS -->
<link
	href="${staticBase}/assets/global/plugins/datatables/datatables.min.css"
	rel="stylesheet" type="text/css" />
<link href="${staticBase}/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css" rel="stylesheet" type="text/css" />
<link href="${staticBase}/assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="${staticBase}/assets/global/plugins/bootstrap-fileinput/fileinput.min.css" rel="stylesheet" type="text/css" />
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
	        type: 'POST',
	        url: BASE_PATH+'premiumOrder/delPremiumDetail',
	        data:{'id':id},
	        dataType: 'json',
	        contentType: "application/x-www-form-urlencoded; charset=utf-8", 
	        success: function (data) {
        		alert(data.msg);
        		if(data.status=='200'){
	        		$('#'+id).remove();
        		}
	        },
	        error: function () {
	            alert(data.msg);
	        }
	    });
	}
	/**************************************************************删除投保方案****************end*************************************/
	function autoCount() {
	    $.ajax({
	        type: 'POST',
	        url: BASE_PATH+'premiumOrder/autoCount',
	        data: $('#priceSubmit').serialize(),
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
	/********************************** 关闭事件 *****************************start**********************************************/
/* 	$(function (){
		$('#myPremiumEdit').on('hide.bs.modal',function(){
			window.location.reload();
		})
	}) */
	$(function (){
		$('#myPremiumEdit').on('hidden.bs.modal',function(){
			window.location.reload();
			autoCount();
		})
	})
	/********************************** 关闭事件 *****************************end**********************************************/
	/********************************** 人工报价 *****************************start**********************************************/
	function priceSubmit() {
		$("input[name='pValue']").each(function() {
    		var pValue =$(this).val();
        	var detailId =$(this).prev("input[name='detailId']").val();
        	$(this).next("input[name='prmValueStr']").val(detailId+"|"+pValue);
		});
	    $.ajax({
	        type: 'POST',
	        url: BASE_PATH+'premiumOrder/addPriceToPremium',
	        data: $('#priceSubmit').serialize(),
	        dataType: 'json',
	        success: function (data) {
	        	if(data.status==200){
	        		alert(data.msg);
	        		$('#myPremiumEdit').modal('hide');
	        		window.location.reload();
	        	}
	        },
	        error: function () {
	            alert(data.msg);
	        }
	    });
	}	
	/********************************** 人工报价 *****************************end**********************************************/
</script>
<!-- END PAGE LEVEL PLUGINS -->
</head>
<body>
<form action="#" id="form_sample_3" class="form-horizontal" >
	<!-- BEGIN PAGE TITLE-->
	<div style="float: left; margin: 3px;">
		<a href='<c:url value="/user/detail/${userId}"/>'><h3 class="page-title">用户信息</h3></a>
	</div>
	<div style="float: left; margin: 3px;">
		<a href='<c:url value="/user/carList/${userId}"/>'><h3 class="page-title">车辆信息</h3></a>
	</div>
	<div style="float: left; margin: 3px;">
		<a href='<c:url value="/user/contactList/${userId}"/>'><h3 class="page-title">配送信息</h3></a>
	</div>
	
	<button type="button" class="btn sbold green" style="float: right; margin: 20px 5px; padding: 2px 10px; line-height: 30px;  color: white;" onclick="editContact(${userId},'add')">新增联系方式</button>
	<button type="button" class="btn sbold green" style="float: right; margin: 20px 5px; padding: 2px 10px; line-height: 30px;  color: white;" onclick="editCar(${userId},'')">新增车辆</button>
	<button type="submit" class="btn sbold green" style="float: right; margin: 20px 5px; padding: 2px 10px; line-height: 30px;  color: white;" onclick="javascript:history.back(-1);">返回</button>
			
	<!-- END PAGE TITLE-->
	<div class="row">
		<div class="col-md-12">
			<!-- Begin: life time stats -->
			<div class="portlet light portlet-fit portlet-datatable bordered">

				<div class="portlet-body">
					<h4>用户信息</h4>
						<div class="form-body">
							<div class="alert alert-danger display-hide">
								<button class="close" data-close="alert"></button>
								部分项目填写有误，详细见下
							</div>
							<div class="alert alert-success display-hide">
								<button class="close" data-close="alert"></button>
								信息添加成功!
							</div>
							<input type="text" name="id" value="${user.id}" style="display:none"/>
							<div class="form-group">
								<label class="control-label col-md-3">微信昵称 <span
									class="required"> * </span>
								</label>
								<div class="col-md-3">
									<input type="text" name="weixinNick" value="${user.weixinNick}" readonly="readonly" style="border: 0px;background-color: white;"
										data-required="1" class="form-control"
										onkeyup="this.value=value.replace(/[^\u4E00-\u9FA5\w\\,\\，\\.\\。\\(\\)]/g,'')" />
								</div>
							</div>							
							<div class="form-group">
								<label class="control-label col-md-3">真实姓名 <span
									class="required"> * </span>
								</label>
								<div class="col-md-3">
									<input type="text" name="realName" value="${user.realName}" class="form-control" />
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-md-3">身份证号<span
									class="required"> * </span>
								</label>
								<div class="col-md-3">
									<input type="text" name="idNo" value="${user.idNo}" data-required="1" class="form-control"
										onkeyup="this.value=value.replace(/^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}/g,'')" />
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-md-3">推荐人 <span
									class="required"> * </span>
								</label>
								<div class="col-md-3">
									<input type="text" name="recommendName" value="${user.realName}" readonly="readonly" style="border: 0px;background-color: white;"
										data-required="1" class="form-control"
										onkeyup="this.value=value.replace(/[^\u4E00-\u9FA5\w\\,\\，\\.\\。\\(\\)]/g,'')" />
								</div>
							</div>							
		                    <div class="form-actions">
		                        <div class="row">
		                            <div class="col-md-offset-3 col-md-9">
		                                <button type="submit" class="btn green">提交</button>
		                                <button type="button" class="btn default" onclick="javascript:history.back(-1);">取消</button>
		                            </div>
		                        </div>
		                    </div>							
						</div>
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
						<div class="pre-scrollable">
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
<!-------------------------------------------------------- 编辑险种 ------------------------------start-------------------------------------------------------------------------------->
<div class="modal fade" id="myPremiumEdit" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" style="width: 1000px;height: 800px;" role="document">
    <div class="modal-content" >
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h3 class="modal-title" id="myModalLabel">编辑险种</h3>
      </div>
      <div class="modal-body">

		<!-- END PAGE TITLE-->
		<div class="row">
			<div class="col-md-12">
				<!-- Begin: life time stats -->
				<div class="portlet light portlet-fit portlet-datatable bordered">
					<div class="portlet-body">
						<div  class="pre-scrollable">
							<form action="${base}/premiumOrder/confirmPremiumOrder" method="POST" id="priceSubmit">
					          	<div>折扣比例：<input name="discount" type="text" value="100"> %</div>
								<table class="table table-striped table-bordered table-hover" id="premiumList">
									<thead>
										<tr role="row" class="heading">
											<th>险种名称</th>
											<th width="30%">保额</th>
											<th width="30%">价格</th>
											<th width="15%">删除</th>
										</tr>
									</thead>
									<tbody id="premiuBody">
									<!------------------------------------------------------------ 基础险-------------------------------------start ---------------------------------------------------->
										<c:if test="${po.jcpdlist!=null&&fn:length(po.jcpdlist)>0}">
											<div style="float: left; margin: 3px;">
												<h4>基础险</h4>
											</div>						
										</c:if>
										<c:forEach items="${po.jcpdlist}" var="jcpd">
											<tr id="${jcpd.id}">
												<td >${jcpd.prmName}</td>
												<td width="30%">
													<c:set value="${fn:split(jcpd.premiumType,',') }" var="premiumTypes"></c:set>
													<select name="amountstr">
														<c:forEach items="${premiumTypes}" var="pt">
															<option value="${jcpd.id}|${pt}" <c:if test="${jcpd.amount==pt}"> selected="selected" </c:if>>${pt}</option>
														</c:forEach>
													</select>
												</td>
												<td width="30%">
													<input name="detailId" type="text" value="${jcpd.id}" style="display:none;">
													<input name="pValue" type="text" value="${jcpd.prmValue}"> 元
													<input name="prmValueStr" type="text" value="${jcpd.id}|${fjpd.prmValue}" style="display:none;"> 
											    </td>
<%-- 											    <td width="15%"><button type="button" onclick="delPremium(${jcpd.id})" class="btn sbold green" style="color: white;width:60px;">删除</button></td> --%>
											    <td width="15%">
											    	<button type="button" onclick="delPremium(${jcpd.id})" class="btn sbold green" style="color: white;width:60px;">
											    		删除
											    	</button>
											    </td>
											</tr>
										</c:forEach>
									<!------------------------------------------------------------ 基础险-------------------------------------end ---------------------------------------------------->
									</tbody>
								</table>									
								<table class="table table-striped table-bordered table-hover" id="premiumList">
									<thead>
										<tr role="row" class="heading">
											<th>险种名称</th>
											<th width="30%">保额</th>
											<th width="30%">价格</th>
											<th width="15%">删除</th>
										</tr>
									</thead>
									<tbody id="premiuBody">									
									<!------------------------------------------------------------ 附加险-------------------------------------start ---------------------------------------------------->
										<c:if test="${po.fjpdlist!=null&&fn:length(po.fjpdlist)>0}">
											<div style="float: left; margin: 3px;">
												<h4>附加险</h4>
											</div>						
										</c:if>
										<c:forEach items="${po.fjpdlist}" var="fjpd">
											<tr id="${fjpd.id}">
												<td width="25%">${fjpd.prmName}</td>
												<td width="25%">
													<c:set value="${fn:split(fjpd.premiumType,',') }" var="premiumTypes"></c:set>
													<select name="amountstr">
														<c:forEach items="${premiumTypes}" var="pt">
															<option value="${fjpd.id}|${pt}" <c:if test="${fjpd.amount==pt}"> selected="selected" </c:if>>${pt}</option>
														</c:forEach>
													</select>
												</td>
												<td>
													<input name="detailId" type="text" value="${fjpd.id}" style="display:none;">
													<input name="pValue" type="text" value="${fjpd.prmValue}"> 元
													<input name="prmValueStr" type="text" value="${fjpd.id}|${fjpd.prmValue}" style="display:none;"> 
											    </td>
											    <td width="25%"><button type="button" class="btn sbold green" onclick="delPremium(${fjpd.id})" style="color: white;width:60px;">删除</button></td>
											</tr>
										</c:forEach>
									<!------------------------------------------------------------ 附加险-------------------------------------end ---------------------------------------------------->
									</tbody>
								</table>									
								<table class="table table-striped table-bordered table-hover" id="premiumList">
									<thead>
										<tr role="row" class="heading">
											<th>险种名称</th>
											<th width="30%">保额</th>
											<th width="30%">价格</th>
											<th width="15%">删除</th>
										</tr>
									</thead>
									<tbody id="premiuBody">										
									<!------------------------------------------------------------ 交强险-------------------------------------start ---------------------------------------------------->
										<c:if test="${po.jqpdlist!=null&&fn:length(po.jqpdlist)>0}">
											<div style="float: left; margin: 3px;">
												<h4>交强险</h4>
											</div>						
										</c:if>										
										<c:forEach items="${po.jqpdlist}" var="jqpd">
											<tr id="${jqpd.id}">
												<td width="25%">${jqpd.prmName}</td>
												<td width="25%">
													<c:set value="${fn:split(jqpd.premiumType,',') }" var="premiumTypes"></c:set>
													<select name="amountstr">
														<c:forEach items="${premiumTypes}" var="pt">
															<option value="${jqpd.id}|${pt}" <c:if test="${jqpd.amount==pt}"> selected="selected" </c:if>>${pt}</option>
														</c:forEach>
													</select>
												</td>
												<td>
													<input name="detailId" type="text" value="${jqpd.id}" style="display:none;">
													<input name="pValue" type="text" value="${jqpd.prmValue}"> 元
													<input name="prmValueStr" type="text" value="${jqpd.id}|${jqpd.prmValue}" style="display:none;"> 
											    </td>
											    <td width="25%"><button type="button" onclick="delPremium(${jqpd.id})" class="btn sbold green" style="color: white;width:60px;">删除</button></td>
											</tr>
										</c:forEach>
									<!------------------------------------------------------------ 交强险-------------------------------------end ---------------------------------------------------->

									</tbody>
									<input name="id" type="text" value="${po.id}" style="display:none"/>
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
        <button type="button" class="btn btn-primary" onclick="priceSubmit()">确定</button>
      </div>
    </div>
  </div>
</div>

<!-------------------------------------------------------- 编辑险种 ------------------------------end-------------------------------------------------------------------------------->
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
        <!-- BEGIN CORE PLUGINS -->
        <script src="${staticBase}/assets/global/plugins/jquery.min.js" type="text/javascript"></script>
        <script src="${staticBase}/assets/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="${staticBase}/assets/global/plugins/js.cookie.min.js" type="text/javascript"></script>
        <script src="${staticBase}/assets/global/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js" type="text/javascript"></script>
        <script src="${staticBase}/assets/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
        <script src="${staticBase}/assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
        <script src="${staticBase}/assets/global/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
        <script src="${staticBase}/assets/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js" type="text/javascript"></script>
        <!-- END CORE PLUGINS -->



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
        <!-- BEGIN PAGE LEVEL PLUGINS -->
		<script src="${staticBase}/assets/global/plugins/bootstrap-fileinput/fileinput.js" type="text/javascript"></script>
        <!-- END PAGE LEVEL PLUGINS -->
        <!-- BEGIN THEME GLOBAL SCRIPTS -->
		<script src="${staticBase}/assets/global/scripts/app.min.js" type="text/javascript"></script>
        <!-- END THEME GLOBAL SCRIPTS -->

        <!-- BEGIN THEME LAYOUT SCRIPTS -->
		<script src="${staticBase}/assets/layouts/layout/scripts/layout.min.js"></script>
		<script src="${staticBase}/assets/layouts/layout/scripts/demo.min.js"></script>
		<script src="${staticBase}/assets/layouts/global/scripts/quick-sidebar.min.js"></script>
        <!-- END THEME LAYOUT SCRIPTS -->
<script src="${base}/assets/pages/system/user/detail.js" type="text/javascript"></script>
<script type="text/javascript">
	function editContact(id,flag){
		location.href=BASE_PATH+"userContact/selectUserContact/"+id+"/"+flag;
	}
	function editCar(userId,carId){
		if(carId!=""){
			location.href=BASE_PATH+"car/selectCarInfo?userId="+userId+"&&carId="+carId;
		}else{
			location.href=BASE_PATH+"car/selectCarInfo?userId="+userId;
		}
	}
</script>
</content>
</html>
