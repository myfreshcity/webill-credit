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
	        	}else{
	        		alert(data.msg);
	        	}
	        },
	        error: function () {
	            alert(data.msg);
	        }
	    });
	}
	function getCarInfo() {
		$("#getCarInfo").attr("action",BASE_PATH+'premiumOrder/getCarInfo');
	    $.ajax({
	        type: 'POST',
	        url: BASE_PATH+'premiumOrder/getCarInfo',
	        data: $('#getCarInfo').serialize(),
	        dataType: 'json',
	        success: function (data) {
	        	$("#ownerName").val(data.carOwner);
	        	$("#ciInsurerCom").val(data.insurerCom);
        	  	var month=0;
        	    var day=0;
        	    var date=new Date(data.prmEndTime); 
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

	<h3 class="page-title">
		订单管理 <small>订单相关操作</small>
	</h3>
	<div class="row">
		<div class="col-md-12">
			<!-- Begin: life time stats -->
			<div class="portlet light portlet-fit portlet-datatable bordered">
				<div class="portlet-title">
					<div class="caption">
						<i class="icon-settings font-dark"></i> <span
							class="caption-subject font-dark sbold uppercase">订单列表</span>
					</div>
					<button type="button" class="btn sbold green" style="float: right; margin: 10px 5px; padding: 3px 10px; line-height: 30px; color: white;" data-toggle="modal" data-target="#newPremiumOrderModal">添加</button>
				</div>
				<div class="portlet-body">
					<div class="table-container">
						<div class="form horizontal-form">
							<div class="form-body col-md-12">
								<div class="form-group col-md-3">
									<label class="control-label col-md-4">投保公司 </label>
									<div class="col-md-8">
										<select name="ciInsurerCom" class="form-control form-filter">
											<option value="">请选择保险</option>
											<option value="太平洋保险">太平洋保险</option>
											<option value="人民保险">人民保险</option>
											<option value="大地保险">大地保险</option>
											<option value="阳光保险">阳光保险</option>
										</select>
									</div>
								</div>
								<div class="form-group col-md-3">
									<label class="control-label col-md-4">车牌号 </label>
									<div class="col-md-8">
										<input type="text" name="licenseNo" value="${po.licenseNo}"
											class="form-control form-filter" />
									</div>
								</div>
								<div class="form-group col-md-3">
									<label class="control-label col-md-4">车主姓名 </label>
									<div class="col-md-8">
										<input type="text" name="carOwner" value="${po.carOwner}"
											class="form-control form-filter"
											onkeyup="this.value=value.replace(/[^\u4E00-\u9FA5\w\\,\\，\\.\\。\\(\\)]/g,'')" />
									</div>
								</div>
								<div class="form-group col-md-3">
									<label class="control-label col-md-4">状态</label>
									<div class="col-md-8">
										<select name="status" class="form-control form-filter">
											<option value="">请选择状态</option>
											<option value="-1">已废弃</option>
											<option value="2000">已创建</option>
											<option value="2100">无报价</option>
											<option value="2200">有报价</option>
											<option value="3000">预约</option>
											<option value="4000">已推送</option>
											<option value="4100">已查看</option>
											<option value="4200">完成投保信息</option>
											<option value="4300">已确认</option>
											<option value="4400">待支付</option>
											<option value="5000">询价中</option>
											<option value="7000">支付完成</option>
											<option value="7100">支付失败</option>
											<option value="8000">已过期</option>
											<option value="9000">已完成</option>
											<option value="9100">已删除</option>
										</select>
									</div>
								</div>
								<div class="form-group col-md-3 col-sm-12" style="margin-right:10px">
                                    <label class="control-label col-md-4">订单时间</label>
                                    <div class="col-md-8">
                                        <div class="input-group input-large date-picker input-daterange" data-date-format="yyyy-mm-dd">
                                            <input type="text" class="form-control form-filter" name="timeFrom" readonly value="${po.timeFrom}">
                                            <span class="input-group-addon"> to </span>
                                            <input type="text" class="form-control form-filter" name="timeTo" readonly value="${po.timeTo}"> 
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
						<table
							class="table table-striped table-bordered table-hover"
							id="datatable_ajax">
							<thead>
								<tr role="row" class="heading">
									<th width="10%">订单号</th>
									<th width="10%">车牌号</th>
									<th width="10%">微信昵称</th>
									<th width="10%">车主姓名</th>
									<th width="10%">到期时间</th>
									<th width="10%">订单时间</th>
									<th width="10%">投保公司</th>
									<th width="10%">保费</th>
									<th width="10%">状态</th>
									<th width="10%">操作时间</th>
									<th width="10%">操作人</th>
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
	
	
<!-------------------------------------------------------- 新建订单--------------------------------------start---------------------------------------->
<div class="modal fade" id="newPremiumOrderModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
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
										<input type="text" name="licenseNo" value="${ci.licenseNo }"
											data-required="1" class="form-control" />
									</div>
									<button type="button" onclick="getCarInfo()">搜索</button>
								</div>
								<div id="carInfo">
									<div class="form-group">
										<label class="control-label col-md-3">车主姓名<span
											class="required"> * </span>
										</label>
										<div class="col-md-5">
											<input type="text" name="ownerName" id="ownerName" value="${ci.carOwner}"
												data-required="1" class="form-control" readonly="readonly"/>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-md-3">投保公司<span
											class="required"> * </span>
										</label>
										<div class="col-md-5">
											<input type="text" name="ciInsurerCom" id="ciInsurerCom" value="${ci.insurerCom}" data-required="1" readonly="readonly" class="form-control"/>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-md-3">到期时间<span
											class="required"> * </span>
										</label>
										<div class="col-md-5">
											<input type="text" name="ciStartDate" id="ciStartDate" value="${ci.prmEndTime}" readonly="readonly" data-required="1" class="form-control"/>
										</div>
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
        <button type="button" class="btn btn-primary" onclick="saveNewOrder()">确定</button>
      </div>
    </div>
  </div>
</div>
<!--------------------------------------------------------新建订单---------------------------end------------------------------------------------------>	
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
<script src="${base}/assets/pages/system/premiumOrder/list.js"
	type="text/javascript"></script> <!-- END PAGE LEVEL SCRIPTS --> </content>
</html>
