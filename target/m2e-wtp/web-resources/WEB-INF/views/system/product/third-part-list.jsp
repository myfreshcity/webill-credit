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
<script src="${staticBase}/assets/global/plugins/jquery.min.js" type="text/javascript"></script>
<style type="text/css">
	#bigDataList_filter{
		display:none;
	}
</style>
<script type="text/javascript">
	/**************************************************************发布商品****************start*************************************/
	function publish(caseCode,fristCategory,secondCategory,companyName,planName){
		$.post(BASE_PATH+'product/prodcutByCaseCode',{caseCode:caseCode},function(data){
			if(data.status == 200){
				window.location.href= BASE_PATH+'product/prodDetail/'+caseCode+'/'+fristCategory+'/'+secondCategory+'/'+companyName+'?planName='+planName;
        	}else if(data.status == 210) {
				alert(data.msg);
        	}
        });
	}	
	/**************************************************************发布商品****************end***************************************/
</script>
<!-- END PAGE LEVEL PLUGINS -->
</head>
<body>

	<h3 class="page-title">
		增加商品
	</h3>
	<div class="row">
		<div class="col-md-12">
			<!-- Begin: life time stats -->
			<div class="portlet light portlet-fit portlet-datatable bordered">
				<div class="portlet-title">
					<div class="caption">
						<i class="icon-settings font-dark"></i> <span
							class="caption-subject font-dark sbold uppercase">商品渠道查询列表</span>
					</div>
				</div>
				<div class="portlet-body">
					<div class="table-container">
						<div class="form horizontal-form">
							<div class="form-body col-md-12">
								<div class="form-group col-md-3">
									<label class="control-label col-md-4">全局搜索</label>
									<div class="col-md-8" data-column="0">
										<input type="text" name="global" id="global" value="${po.licenseNo}"
											class="form-control form-filter" />
									</div>
								</div>
								<div class="form-group col-md-3">
									<label class="control-label col-md-4">产品名称 </label>
									<div class="col-md-8" data-column="0">
										<input type="text" name="prodName" id="prodName" value="${po.licenseNo}"
											class="form-control form-filter" />
									</div>
								</div>
								<div class="form-group col-md-3">
									<label class="control-label col-md-4">保险公司</label>
									<div class="col-md-8">
										<select name="companyName" id="companyName" class="form-control form-filter">
											<option value="">请选择保险</option>
											<option value="长生人寿">长生人寿</option>
											<option value="平安健康">平安健康</option>
											<option value="华安保险">华安保险</option>
											<option value="华泰财险">华泰财险</option>
										</select>
									</div>
								</div>
								<div class="form-group col-md-3">
									<label class="control-label col-md-4">渠道名称</label>
									<div class="col-md-8">
										<select name="thirdPartName" id="thirdPartName" class="form-control form-filter">
											<option value="">请选择渠道</option>
											<option value="惠泽">惠泽</option>
										</select>
									</div>
								</div>
							</div>
							<!-- END FORM-->
						</div>
						<table
							class="table table-striped table-bordered table-hover table-checkable order-column" id="bigDataList">
							<thead>
								<tr role="row" class="heading">
									<th width="10%" style="text-align: center;">方案代码</th>
									<th width="10%" style="text-align: center;">产品名称</th>
									<th width="10%" style="text-align: center;">计划名称</th>
									<th width="10%" style="text-align: center;">保险公司</th>
									<th width="10%" style="text-align: center;">渠道名称</th>
									<th width="10%" style="text-align: center;">齐欣一级分类</th>
									<th width="10%" style="text-align: center;">齐欣二级分类</th>
									<th width="5%" style="text-align: center;">操作</th>
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
<content tag="pageJs"> <!-- BEGIN PAGE LEVEL PLUGINS -->
<script
	src="${base}/assets/global/scripts/datatable.js" type="text/javascript"></script>
<script
	src="${staticBase}/assets/global/plugins/datatables/datatables.min.js"
	type="text/javascript"></script> <script
	src="${staticBase}/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js"
	type="text/javascript"></script> <script
	src="${staticBase}/assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js"
	type="text/javascript"></script> <!-- END PAGE LEVEL PLUGINS --> <!-- BEGIN PAGE LEVEL SCRIPTS -->
<script src="${base}/assets/pages/system/product/third-part-list.js"
	type="text/javascript"></script> <!-- END PAGE LEVEL SCRIPTS --> </content>
</html>
