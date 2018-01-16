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
<!-- END PAGE LEVEL PLUGINS -->
</head>
<body>

	<!-- BEGIN PAGE TITLE-->
	<h3 class="page-title">险种列表</h3>
	<!-- END PAGE TITLE-->
	<div class="row">
		<div class="col-md-12">
			<!-- Begin: life time stats -->
			<div class="portlet light portlet-fit portlet-datatable bordered">
				<div class="portlet-body">
					<div class="table-container">
						<div class="form horizontal-form">
							<div class="form-body">
								<div class="form-group">
									<label class="control-label col-md-1">险种类型</label>
									<div class="col-md-2">
										<select name="prmType" class="form-control form-filter">
											<option value="">-----</option>
											<option value="1">基础险</option>
											<option value="2">附加险</option>
											<option value="3">交强险</option>
										</select>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-1">险种名称 </label>
									<div class="col-md-2">
										<input type="text" name="prmName" value="${prmType}"
											class="form-control form-filter"
											onkeyup="this.value=value.replace(/[^\u4E00-\u9FA5\w\\,\\，\\.\\。\\(\\)]/g,'')" />
									</div>
								</div>
							</div>
							<div class="form-actions right">
								<div class="row">
									<div class="col-md-offset-3 col-md-9">
										<button
											class="btn btn-sm green btn-outline filter-submit margin-bottom">
											<i class="fa fa-search"></i>搜索
										</button>
										<button class="btn btn-sm red btn-outline filter-cancel">
											<i class="fa fa-times"></i> 重置
										</button>
									</div>
								</div>
							</div>
							<!-- END FORM-->
						</div>
						<form action="${base}/premiumOrder/addPremiumToOrder"
							method="POST">
							<table
								class="table table-striped table-bordered table-hover table-checkable"
								id="datatable_ajax">
								<thead>
									<tr role="row" class="heading">
										<th width="10%"><input type="checkbox"
											class="group-checkable" data-set="#sample_1 .checkboxes" /></th>
										<th width="10%">险种类型</th>
										<th>险种名称</th>
									</tr>
								</thead>
								<tbody></tbody>
								<input name="orderId" type="text" value="9" />
								<input name="submit" type="submit" value="确定" />
							</table>
						</form>
					</div>
				</div>
			</div>
			<!-- End: life time stats -->
		</div>
	</div>
</body>
<content tag="pageJs">
<script src="${base}/assets/global/scripts/datatable.js" type="text/javascript"></script>
<script src="${staticBase}/assets/global/plugins/datatables/datatables.min.js"
	type="text/javascript"></script> <script
	src="${staticBase}/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js"
	type="text/javascript"></script> <script
	src="${staticBase}/assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js"
	type="text/javascript"></script> <script
	src="${base}/assets/pages/system/premiumOrder/premiumList.js"
	type="text/javascript"></script> 
</content>
</html>
