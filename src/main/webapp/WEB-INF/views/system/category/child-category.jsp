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
	function deleteCat(data){
		$.post(BASE_PATH+'category/deleteCat', {id:data}, function(data){
			if(data.status == 200){
				alert(data.msg);
				window.location.reload();
			}
		});
	}
</script>
<!-- END PAGE LEVEL PLUGINS -->
</head>
<body>

	<h3 class="page-title">
		商品分类
	</h3>
	<div class="row">
		<div class="col-md-12">
			<!-- Begin: life time stats -->
			<div class="portlet light portlet-fit portlet-datatable bordered">
				<div class="portlet-title">
					<div class="caption">
						<i class="icon-settings font-dark"></i> <span
							class="caption-subject font-dark sbold uppercase">商品子分类</span>
					</div>
					<button type="button" class="btn green-meadow" style="float: right; margin: 10px 5px; padding: 3px 10px; line-height: 30px; color: white;" data-toggle="modal" onclick="go()">返回</button>
				</div>
				<div class="portlet-body">
					<div class="table-container">
						<table class="table table-striped table-bordered table-hover table-checkable" id="example">
							<thead>
								<tr role="row" class="heading">
									<th width="10%" style="text-align: center;">编号</th>
									<th width="10%" style="text-align: center;">分类名称</th>
									<th width="10%" style="text-align: center;">级别</th>
									<th width="10%" style="text-align: center;">排序</th>
									<th width="10%" style="text-align: center;">操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${childCatList}" var="childCat">
									<tr role="row">
										<td width="10%" style="text-align: center;">${childCat.id}</td>
										<td width="10%" style="text-align: center;">${childCat.catName}</td>
										<td width="10%" style="text-align: center;">
											<c:if test="${childCat.level == 1}">一级</c:if>
											<c:if test="${childCat.level == 2}">二级</c:if>
										</td>
										<td width="10%" style="text-align: center;">${childCat.sortIndex}</td>
										<td width="10%" style="text-align: center;">
											<button id="deleteOne" class="btn btn-sm btn-outline grey-salsa" onclick="deleteCat('${childCat.id}')">删除</button>
										</td>
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
</content>
</html>
