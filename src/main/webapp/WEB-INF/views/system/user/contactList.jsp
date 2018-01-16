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
							class="caption-subject font-dark sbold uppercase">配送列表</span>
					</div>
				</div>
				<div class="portlet-body">
					<div class="table-container">
						<table class="table table-striped table-bordered table-hover" id="premiumList">
							<thead>
								<tr role="row" class="heading">
									<th width="15%">微信昵称</th>
									<th width="10%">联系人姓名</th>
									<th width="10%">联系电话</th>
									<th width="15%">电子邮箱</th>
									<th width="15%">所在地区</th>
									<th >详细地址</th>
									<th width="10%">状态</th>
									<th width="10%">操作</th>
								</tr>
							</thead>
							<tbody id="ucBody">
								<c:forEach items="${ucList}" var="uc" varStatus="s">
									<tr>
										<td>${uc.weixinNick}</td>
										<td>${uc.contactName}</td>
										<td>${uc.mobile}</td>
										<td>${uc.email}</td>
										<td>${uc.province}${uc.city}${uc.area}</td>
										<td>${uc.address}</td>
										<td>
											<c:if test="${uc.status == '0' }">正常</c:if>
										</td>
										<td>
											<button class="btn btn-sm btn-outline grey-salsa" onclick="editContact(${uc.id},'update')">修改</button>
											<c:if test="${uc.isDefault=='0' }">
												<button id="setDefault" class="btn btn-sm btn-outline grey-salsa" onclick="setDefault(${uc.id})">设置默认</button>
											</c:if> 
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

<script type="text/javascript" src="${staticBase}/assets/global/plugins/jquery.min.js"></script>
	<script src="http://www.jq22.com/jquery/1.11.1/jquery.min.js"></script>
	<script src="http://www.jq22.com/jquery/bootstrap-3.3.4.js"></script>
<script type="text/javascript" src="${staticBase}/assets/global/plugins/jquery-cityselect/js/jquery.cityselect.js"></script>

<!-- BEGIN PAGE LEVEL SCRIPTS --> <%-- <script src="${base}/assets/pages/system/staff/list.js" type="text/javascript"></script> --%>
<!-- END PAGE LEVEL SCRIPTS --> <script
	type="text/javascript">
	$(function(){ 
		$("#city").citySelect();
	}); 
        function setDefault(id) {
    	    $.ajax({
    	        type: 'POST',
    	        url: BASE_PATH+'userContact/updateDefault',
    	        data:{'id':id},
    	        dataType: 'json',
    	        success: function (data) {
            		alert(data.msg);
            		window.location.reload();
    	        },
    	        error: function () {
    	            alert("错误！");
    	        }
    	    });
    	}
    	/**************************************************************添加配送****************start***********************************/
    	function getContact(id) {
    	    $.ajax({
    	        type: 'GET',
    	        url: BASE_PATH+'userContact/selectUserContact/'+id,
    	        dataType: 'json',
    	        success: function (data) {
    	        	alert(111);
    	        	if(data.status==200){
    	        		alert(data.msg);
    	        		location.reload();
    	        	}
    	        },
    	        error: function () {
    	            alert("错误！");
    	        }
    	    });
    	}
    	/**************************************************************添加配送****************end*************************************/
	    
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
