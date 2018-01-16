<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layout/comtag.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<!-- BEGIN PAGE LEVEL PLUGINS -->
<link
	href="${staticBase}/assets/global/plugins/datatables/datatables.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="${staticBase}/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<!-- END PAGE LEVEL PLUGINS -->
</head>
<body>

	<!-- BEGIN PAGE TITLE-->
	<h3 class="page-title">
		违章详情 <small>共${ci.illCount}条违章记录</small>
	</h3>
	<!-- END PAGE TITLE-->
	<div class="row">
		<div class="col-md-12">
			<!-- Begin: life time stats -->
			<div class="portlet light portlet-fit portlet-datatable bordered">

				<div class="portlet-body">
					<h3>车主信息</h3>
					<form action="${base}/illegalOrder/confirmPay" id="form_sample_3" class="form-horizontal" method="post" accept-charset="UTF-8">
						<div class="form-actions" style="float: right;margin-right: 10px;">
							<div class="row">
								<div class="col-md-offset-3 col-md-9">
									<button type="submit" class="btn green" <c:if test="${status==2}"> style="display:none;"</c:if>>确认缴费</button>
								</div>
							</div>
						</div>
						<div class="form-body">
							<div class="alert alert-danger display-hide">
								<button class="close" data-close="alert"></button>
								部分项目填写有误，详细见下
							</div>
							<div class="alert alert-success display-hide">
								<button class="close" data-close="alert"></button>
								信息添加成功!
							</div>
							<input id="orderId" name="orderId" value="${orderId}" hidden>
							<div class="form-group">
								<label class="control-label col-md-3">车牌号 <span
									class="required"> * </span>
								</label>
								<div class="col-md-4">
									<input type="text" name="licenseNo" value="${ci.licenseNo}"
										data-required="1" class="form-control"
										onkeyup="this.value=value.replace(/^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}/g,'')" readonly="readonly"/>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-md-3">车型 <span
									class="required"> * </span>
								</label>
								<div class="col-md-4">
									<input type="text" name="brandName" value="${ci.brandName}"
										data-required="1" class="form-control" readonly="readonly"/>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-md-3">车主姓名 <span
									class="required"> * </span>
								</label>
								<div class="col-md-4">
									<input type="text" name="carOwner" value="${ci.carOwner}"
										data-required="1" class="form-control" readonly="readonly"/>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-md-3">手机号码 <span
									class="required"> * </span>
								</label>
								<div class="col-md-4">
									<input type="text" name="ownerPhone" value="${ci.ownerPhone}"
										data-required="1" class="form-control" readonly="readonly"/>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-md-3">发动机号 <span
									class="required"> * </span>
								</label>
								<div class="col-md-4">
									<input type="text" name="engineNo" value="${ci.engineNo}"
										data-required="1" class="form-control" readonly="readonly"/>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-md-3">车架号 <span
									class="required"> * </span>
								</label>
								<div class="col-md-4">
									<input type="text" name="frameNo" value="${ci.frameNo}"
										data-required="1" class="form-control" readonly="readonly"/>
								</div>
							</div>
						</div>

						<div class="table-container">
							<table
								class="table table-striped table-bordered table-hover table-checkable"
								id="datatable_ajax">
								<thead>
									<tr role="row" class="heading">
										<th width="10%">违章时间</th>
										<th width="15%">违章地点</th>
										<th width="200">违章原因</th>
										<th width="10%">罚款金额</th>
										<th width="10%">违章扣分</th>
										<th width="10%">服务费</th>
										<th width="10%">违章处罚单号</th>
									</tr>
									<c:forEach items="${list}" var="t" varStatus="s">
										<tr role="row" class="body">
											<td width="10%"><fmt:formatDate value='${t.occurTime}'
													pattern='yyyy-MM-dd HH:mm:ss' /></td>
											<td width="10%">${t.address}</td>
											<td>${t.content}</td>
											<td width="5%">${t.price}</td>
											<td width="5%">${t.score}</td>
											<td width="5%">${t.serverFee}</td>
											<td width="10%"><input name="tid" value="${t.id}" style="display:none;"/><input name="penalizeId" value="${t.illid}" <c:if test="${status==2}"> readonly="readonly"</c:if>/><input name="penalizeIds" value="${t.id}|${t.illid}" style="display:none;"/></td>
										</tr>
									</c:forEach>
								</thead>
							</table>
						</div>
					</form>
				</div>
			</div>
			<!-- End: life time stats -->
		</div>
	</div>
</body>
<content tag="pageJs"> <!-- BEGIN PAGE LEVEL PLUGINS --> <script
	src="${base}/assets/global/scripts/datatable.js" type="text/javascript"></script>
<script
	src="${staticBase}/assets/global/plugins/datatables/datatables.min.js"
	type="text/javascript"></script> <script
	src="${staticBase}/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js"
	type="text/javascript"></script> <script
	src="${staticBase}/assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js"
	type="text/javascript"></script> <!-- END PAGE LEVEL PLUGINS --> <!-- BEGIN PAGE LEVEL SCRIPTS -->
	<script src="${base}/assets/pages/system/illegalOrder/detail-list.js"></script>
<!-- END PAGE LEVEL SCRIPTS --> </content>
</html>
