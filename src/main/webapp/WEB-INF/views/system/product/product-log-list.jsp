<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layout/comtag.jsp"%>
<html>
<head>
<!-- BEGIN PAGE LEVEL PLUGINS -->
<link
	href="${staticBase}/assets/global/plugins/datatables/datatables.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="${staticBase}/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<link
	href="${staticBase}/assets/global/plugins/bootstrap-switch/css/bootstrap-switch.css"
	rel="stylesheet" type="text/css" />
<script src="${staticBase}/assets/global/plugins/jquery.min.js" type="text/javascript"></script>
<style type="text/css">
	.bootstrap-switch .bootstrap-switch-handle-on.bootstrap-switch-primary{
	   background:#1bbc9b;
	}
</style>
<script type="text/javascript">
	function go(){
		history.go(-1);
	}
</script>
<!-- END PAGE LEVEL PLUGINS -->
</head>
<body>

	<h3 class="page-title">
		商品列表
	</h3>
	<div class="row">
		<div class="col-md-12">
			<!-- Begin: life time stats -->
			<div class="portlet light portlet-fit portlet-datatable bordered">
				<div class="portlet-title">
					<div class="caption">
						<i class="icon-settings font-dark"></i> <span
							class="caption-subject font-dark sbold uppercase">商品日志</span>
					</div>
					<button type="button" class="btn green-meadow" style="float: right; margin: 10px 5px; padding: 3px 10px; line-height: 30px; color: white;" data-toggle="modal" onclick="go()">返回</button>
				</div>
				<div class="portlet-body">
					<div class="table-container">
						<table class="table table-striped table-bordered table-hover table-checkable" id="example">
							<thead>
								<tr role="row" class="heading">
									<th width="10%" style="text-align: center;">编号</th>
									<th width="10%" style="text-align: center;">商品ID</th>
									<th width="10%" style="text-align: center;">方案代码</th>
									<th width="10%" style="text-align: center;">平台价格</th>
									<th width="10%" style="text-align: center;">上下架</th>
									<th width="10%" style="text-align: center;">审核状态</th>
									<th width="10%" style="text-align: center;">操作人</th>
									<th width="10%" style="text-align: center;">操作时间</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${productLogList}" var="productLog">
									<tr role="row">
										<td width="10%" style="text-align: center;">${productLog.id}</td>
										<td width="10%" style="text-align: center;">${productLog.prodId}</td>
										<td width="10%" style="text-align: center;">${productLog.caseCode}</td>
										<td width="10%" style="text-align: center;">${productLog.price}</td>
										<td width="10%" style="text-align: center;">
											<c:if test="${productLog.offShelfStatus==1}">已上架</c:if>
											<c:if test="${productLog.offShelfStatus==0}">未上架</c:if>
										</td>
										<td width="10%" style="text-align: center;">
											<c:if test="${productLog.checkStatus==1}">已审核</c:if>
											<c:if test="${productLog.checkStatus==0}">未审核</c:if>
										</td>
										<td width="10%" style="text-align: center;">${productLog.userName}</td>
										<td width="10%" style="text-align: center;"><fmt:formatDate value='${productLog.createdTime }' pattern='yyyy-MM-dd hh:mm:ss'/></td>
									</tr>
								</c:forEach>							
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<!-- End: life time stats -->
		</div>
	</div>
	
</body>
<content tag="pageJs"> <!-- BEGIN PAGE LEVEL PLUGINS -->
<script	src="${base}/assets/global/scripts/datatable.js" type="text/javascript"></script>
<script
	src="${staticBase}/assets/global/plugins/datatables/datatables.min.js"
	type="text/javascript"></script> <script
	src="${staticBase}/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js"
	type="text/javascript"></script> <script
	src="${staticBase}/assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js"
	type="text/javascript"></script> <!-- END PAGE LEVEL PLUGINS --> <!-- BEGIN PAGE LEVEL SCRIPTS -->
<script src="${staticBase}/assets/global/plugins/bootstrap-switch/js/bootstrap-switch.js" type="text/javascript"></script>
<script src="${base}/assets/pages/system/product/product-log.js" type="text/javascript"></script> 
</content>
</html>
