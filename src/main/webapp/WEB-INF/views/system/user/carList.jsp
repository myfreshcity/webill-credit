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
<link
	href="${staticBase}/assets/global/plugins/bootstrap-select/css/bootstrap-select.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="${staticBase}/assets/global/plugins/jquery-multi-select/css/multi-select.css"
	rel="stylesheet" type="text/css" />
<link
	href="${staticBase}/assets/global/plugins/select2/css/select2.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="${staticBase}/assets/global/plugins/select2/css/select2-bootstrap.min.css"
	rel="stylesheet" type="text/css" />
<script src="${staticBase}/assets/global/plugins/jquery.min.js" type="text/javascript"></script>
<script type="text/javascript">
function saveNewOrder() {
	$("#getCarInfo").attr("action",BASE_PATH+'premiumOrder/saveNewPremiumOrder');
    $.ajax({
        type: 'POST',
        url: BASE_PATH+'premiumOrder/saveNewPremiumOrder',
        data: $('#getCarInfo').serialize(),
        dataType: 'json',
        success: function (data) {
        	if(data.status==200){
        		alert(data.msg);
        		window.location.href=BASE_PATH+"premiumOrder/orderDetail/"+data.obj;
        	}
        },
        error: function () {
            alert(data.msg);
        }
    });
}

</script>
</head>
<body>

	<!-- BEGIN PAGE TITLE-->
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
				<div class="portlet-title">
					<div class="caption">
						<i class="icon-settings font-dark"></i> <span
							class="caption-subject font-dark sbold uppercase">车辆列表</span>
					</div>
				</div>
				<div class="portlet-body">
					<div class="table-container">
						<table
							class="table table-striped table-bordered table-hover table-checkable"
							id="datatable_ajax">
							<thead>
								<tr role="row" class="heading">
									<th width="2%">序号</th>
									<th width="10%">车牌号</th>
									<th width="15%">车主姓名</th>
									<th width="20%">初次登记日</th>
									<th width="10%">投保公司</th>
									<th width="20%">保险到期时间</th>
									<th >操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${ciList }" var="ci" varStatus="s" >
									<tr>
										<td >${s.index+1}</td>
										<td >${ci.licenseNo}</td>
										<td >${ci.carOwner}</td>
										<td ><fmt:formatDate value="${ci.enrollDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
										<td >${ci.insurerCom}</td>
										<td ><fmt:formatDate value="${ci.prmEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
										<td style="display:none;">${ci.id}</td>
										<td>
											<button class="btn btn-sm btn-outline grey-salsa" onclick="editCar(${userId},${ci.id})">车辆信息</button>
											&nbsp;&nbsp;&nbsp;&nbsp;
											<button type="button" class="btn sbold green"  onclick="newPremiumOrder(this)">新建投保订单</button>
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
<!-------------------------------------------------------- 新建订单--------------------------------------start---------------------------------------->
<div class="modal fade" id="newPremiumOrder" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h3 class="modal-title" id="myModalLabel">新建订单</h3>
      </div>
      <div class="modal-body">

		<form action="" id="getCarInfo" class="form-horizontal" method="post">
			<!-- END PAGE TITLE-->
			<div class="row">
				<div class="col-md-12">
					<div class="portlet light portlet-fit portlet-datatable bordered">
						<div class="portlet-body">
							<div class="form-body">
								<div class="form-group">
									<label class="control-label col-md-3">车牌号 <span
										class="required"> * </span>
									</label>
									<div class="col-md-5">
										<input type="text" name="licenseNo" id="licenseNo" value="" data-required="1" class="form-control" readonly="readonly" />
									</div>
								</div>
								<div id="carInfo">
									<div class="form-group">
										<label class="control-label col-md-3">车主姓名<span
											class="required"> * </span>
										</label>
										<div class="col-md-5">
											<input type="text" name="ownerName" id="ownerName" value=""
												data-required="1" class="form-control" readonly="readonly"/>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-md-3">投保公司<span
											class="required"> * </span>
										</label>
										<div class="col-md-5">
											<input type="text" name="ciInsurerCom" id="ciInsurerCom" value="" data-required="1" readonly="readonly" class="form-control"/>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-md-3">到期时间<span
											class="required"> * </span>
										</label>
										<div class="col-md-5">
											<input type="text" name="ciStartDate" id="ciStartDate" value="" readonly="readonly" data-required="1" class="form-control" />
										</div>
									</div>
									<input type="text" name="userId" value="${userId}" style="display: none"/>
									<input type="text" name="carId" id="carId" value="${carId}" style="display: none"/>
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
        <button type="button" class="btn btn-primary" onclick="saveNewOrder()">确定</button>
      </div>
    </div>
  </div>
</div>
<!--------------------------------------------------------新建订单---------------------------end------------------------------------------------------>	
</body>
<content tag="pageJs"> <!-- BEGIN PAGE LEVEL PLUGINS --> <script
	src="${base}/assets/global/scripts/datatable.js" type="text/javascript"></script>
<script
	src="${staticBase}/assets/global/plugins/datatables/datatables.min.js"
	type="text/javascript"></script> <script
	src="${staticBase}/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js"
	type="text/javascript"></script> <script
	src="${staticBase}/assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js"
	type="text/javascript"></script> <!-- END PAGE LEVEL PLUGINS --> <script
	src="${staticBase}/assets/global/plugins/select2/js/select2.full.min.js"
	type="text/javascript"></script> <script
	src="${staticBase}/assets/global/plugins/jquery-validation/js/jquery.validate.min.js"
	type="text/javascript"></script> <script
	src="${staticBase}/assets/global/plugins/jquery-validation/js/localization/messages_zh.min.js"
	type="text/javascript"></script> <script
	src="${staticBase}/assets/global/plugins/jquery-validation/js/additional-methods.min.js"
	type="text/javascript"></script> <script
	src="${staticBase}/assets/global/plugins/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"
	type="text/javascript"></script> <script
	src="${staticBase}/assets/global/plugins/bootstrap-select/js/bootstrap-select.min.js"
	type="text/javascript"></script> <script
	src="${staticBase}/assets/global/plugins/jquery-multi-select/js/jquery.multi-select.js"
	type="text/javascript"></script> <script
	src="${staticBase}/assets/global/plugins/jquery.quicksearch.js"
	type="text/javascript"></script> <!-- BEGIN PAGE LEVEL SCRIPTS --> <%-- <script src="${base}/assets/pages/system/staff/list.js" type="text/javascript"></script> --%>
<!-- END PAGE LEVEL SCRIPTS --> <script
	type="text/javascript">
	function editContact(id,flag){
		location.href=BASE_PATH+"userContact/selectUserContact/"+id+"/"+flag;
	}
	function editCar(userId,carId){
		if(carId!=""){
			location.href=BASE_PATH+"car/selectCarInfo?userId="+userId+"&carId="+carId;
		}else{
			location.href=BASE_PATH+"car/selectCarInfo?userId="+userId;
		}
	}
	function newPremiumOrder(e){
		var licenseNo =$(e).parent().parent().find("td:eq(1)").text();
		var carOwner =$(e).parent().parent().find("td:eq(2)").text();
		var enrollDate =$(e).parent().parent().find("td:eq(3)").text();
		var ciInsurerCom =$(e).parent().parent().find("td:eq(4)").text();
		var prmEndTime =$(e).parent().parent().find("td:eq(5)").text();
		var carId =$(e).parent().parent().find("td:eq(6)").text();
		$("#licenseNo").val(licenseNo);
		$("#carOwner").val(carOwner);
		$("#enrollDate").val(enrollDate);
		$("#ownerName").val(carOwner);
		$("#ciInsurerCom").val(ciInsurerCom);
		$("#carId").val(carId);
	  	var month=0;
	    var day=0;
	    if(prmEndTime!=""){
		    var date=new Date(prmEndTime); 
			if ((date.getMonth() + 1) >= 10) {
				month = date.getMonth() + 1;
			} else {
				month = "0" + (date.getMonth() + 1);
			}
			if (date.getDate() >= 10) {
				day = date.getDate();
			} else {
				day = "0" + date.getDate();
			}
			var setDate = date.getFullYear() + "-" + month + "-" + day;
			$("#ciStartDate").val(setDate);
	    }
		$('#newPremiumOrder').modal("show");

}
	</script> 
	
	</content>
</html>
