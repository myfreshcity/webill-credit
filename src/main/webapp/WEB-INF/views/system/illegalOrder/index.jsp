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
	<h3 class="page-title">
		订单管理 <small>订单相关操作</small>
	</h3>
	<!-- END PAGE TITLE-->
	<div class="row">
		<div class="col-md-12">
			<!-- Begin: life time stats -->
			<div class="portlet light portlet-fit portlet-datatable bordered">
				<div class="portlet-title">
					<div class="caption">
						<i class="icon-settings font-dark"></i> <span
							class="caption-subject font-dark sbold uppercase">订单列表</span>
					</div>
				</div>
				<div class="portlet-body">
					<div class="table-container">
						<div class="form horizontal-form">
							<div class="form-body col-md-12">
								<div class="form-group col-md-3">
									<label class="control-label col-md-4">状态</label>
									<div class="col-md-8">
										<select name="status" class="form-control form-filter">
											<option value="">请选择订单状态</option>
											<option value="-1">废弃</option>
											<option value="0">待缴费</option>
											<option value="1">缴费中</option>
											<option value="2">已缴费</option>
											<option value="3">缴费失败</option>
										</select>
									</div>
								</div>
								<div class="form-group col-md-3">
									<label class="control-label col-md-4">车牌号 </label>
									<div class="col-md-8">
										<input type="text" name="licenseNo" value="${ci.licenseNo}"
											class="form-control form-filter" />
									</div>
								</div>
								<div class="form-group col-md-3">
									<label class="control-label col-md-4">车主姓名 </label>
									<div class="col-md-8">
										<input type="text" name="carOwner" value="${ci.carOwner}"
											class="form-control form-filter"
											onkeyup="this.value=value.replace(/[^\u4E00-\u9FA5\w\\,\\，\\.\\。\\(\\)]/g,'')" />
									</div>
								</div>
								<div class="form-group col-md-3 col-sm-12" style="margin-right:20px">
                                    <label class="control-label col-md-4">订单时间</label>
                                    <div class="col-md-8">
                                        <div class="input-group input-large date-picker input-daterange" data-date-format="yyyy-mm-dd">
                                            <input type="text" class="form-control form-filter" name="timeFrom" readonly value="${ci.starTime}">
                                            <span class="input-group-addon"> to </span>
                                            <input type="text" class="form-control form-filter" name="timeTo" readonly value="${ci.endTime}"> 
                                        </div>
                                        <!-- /input-group -->
                                    </div>
                                </div>
							</div>
							<div class="form-actions right">
								<div class="row">
									<div class="col-md-offset-3 col-md-9">
										<button
											class="btn btn-sm green btn-outline filter-submit margin-bottom">
											<i class="fa fa-search"></i> 查询
										</button>
										<button class="btn btn-sm red btn-outline filter-cancel">
											<i class="fa fa-times"></i> 重置
										</button>
									</div>
								</div>
							</div>
							<!-- END FORM-->
						</div>
						<table
							class="table table-striped table-bordered table-hover table-checkable"
							id="datatable_ajax">
							<thead>
								<tr role="row" class="heading">
									<th width="12%">订单时间</th>
									<th width="11%">车牌号</th>
									<th width="11%">车主姓名</th>
									<th width="11%">违章数</th>
									<th width="11%">订单号</th>
									<th width="11%">状态</th>
									<th width="11%">操作时间</th>
									<th width="11%">操作人</th>
									<th width="5%">操作</th>
								</tr>
							</thead>
							<tbody></tbody>
						</table>
					</div>
				</div>
			</div>
			<!-- End: life time stats -->
		</div>
	</div>
</body>
<content tag="pageJs"> <!-- BEGIN PAGE LEVEL PLUGINS --> <script
	src="${base}/assets/global/scripts/my97/WdatePicker.js"></script> <script
	src="${base}/assets/global/scripts/datatable.js" type="text/javascript"></script>
<script
	src="${staticBase}/assets/global/plugins/datatables/datatables.min.js"
	type="text/javascript"></script> <script
	src="${staticBase}/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js"
	type="text/javascript"></script> <script
	src="${staticBase}/assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js"
	type="text/javascript"></script> <!-- END PAGE LEVEL PLUGINS --> <!-- BEGIN PAGE LEVEL SCRIPTS -->
<script src="${base}/assets/pages/system/illegalOrder/list.js"
	type="text/javascript"></script> <!-- END PAGE LEVEL SCRIPTS --> </content>
</html>
