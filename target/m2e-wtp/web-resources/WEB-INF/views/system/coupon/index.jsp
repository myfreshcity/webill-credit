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
	        <!-- BEGIN THEME GLOBAL STYLES -->
        <link href="${staticBase}/assets/global/css/components.min.css" rel="stylesheet" id="style_components" type="text/css" />
        <link href="${staticBase}/assets/global/css/plugins.min.css" rel="stylesheet" type="text/css" />
        <!-- END THEME GLOBAL STYLES -->
	<link href="http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700&subset=all" rel="stylesheet" type="text/css" />
<link href="${staticBase}/assets/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker.css" rel="stylesheet" type="text/css" />
<link href="${staticBase}/assets/global/plugins/bootstrap-datepicker/css/bootstrap-timepicker.min.css" rel="stylesheet" type="text/css" />
<link href="${staticBase}/assets/global/plugins/bootstrap-datepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" type="text/css" />
<link href="${staticBase}/assets/layouts/layout/css/layout.min.css" rel="stylesheet" type="text/css" />
<link rel="shortcut icon" href="favicon.ico" />
<script type="text/javascript">
/**************************************************************规则列表****************start*************************************/
function getRuleList() {
    $.ajax({
        type: 'POST',
        url: BASE_PATH+'coupon/ruleList',
        data: $('#getRuleList').serialize(),
        dataType: 'json',
        success: function (data) {
        	var prlist = data;
        	var tbody = "";
            var trs = "";
        	$.each(prlist,function(n,r){
                trs = "<tr onclick='getRuleInfo()'><td>"+r.id+"</td><td>"+r.ruleName+"</td><td>"+r.useScopeStr+"</td><td>"+r.insurerLimit+"</td><td>"+r.saleAmtStr+"</td></tr>";
                tbody += trs;
            });
        	$('#ruleBody').html(tbody);
        },
        error: function () {
            alert("出错了,请重试!");
        }
    });
}
/**************************************************************规则列表****************end*************************************/

function getRuleInfo(){
	var ruleId ="";
	var ruleName ="";
	$("#ruleBody tr").click(function() {
		ruleId = $(this).children().eq(0).html();
		ruleName = $(this).children().eq(1).html();
		$("#ruleId").val(ruleId);	
		$("#ruleName").val(ruleName);
		$('#myRuleList').modal('hide');
	})
}
function chooseValidDay(){
	var f = $("input[name=validTimeStr]:checked").val();
	var str = "";
	 if(f==1){
		 str = '<input type="text" name="cpValidDay" value="" placeholder="有效天数"/>';
	 }else{
		 str = '<div class="input-group date date-picker margin-bottom-1" data-date-format="yyyy-mm-dd"><input type="text" class="form-control form-filter input-sm" readonly name="cpEndTime" placeholder="终止日期" value=""> <span class="input-group-btn"><button class="btn btn-sm default" type="button"><i class="fa fa-calendar"></i></button></span></div>';
	 }
	 $("#validTime").html(str);
}
function chooseSendRange(){
	var flag = $("input[name=sendRange]:checked").val();
	var str = "";
	if(flag==1){
		 str = '';
	}else if(flag==2){
		 str = '<label class="control-label col-md-3">车牌号<span class="required">*</span></label><div class="col-md-5"><input type="text" name="sendTarget" value="" data-required="1" class="form-control"/></div>';
	 }else{
		 str = '<label class="control-label col-md-3">天数<span class="required">*</span></label><div class="col-md-5"><input type="text" name="sendTarget" value="" data-required="1" class="form-control"/></div>';
	 }
	 $("#sendRangeDetail").html(str);
}

function sendWayChange(){
	var selectParam = $('#sendWay').val();
	if(selectParam==0){
		$('#actionTypeGrop').html('<label class="control-label col-md-3">动作类型<span class="required">*</span></label><div class="col-md-5"><select name="sendTarget" id="actionType"><option value="微信关注">微信关注</option><option value="违章查询">违章查询</option><option value="违章代缴">违章代缴</option><option value="在线投保">在线投保</option></select></div>');
		$('#planAmt').html('<label class="control-label col-md-3">发放数量<spaclass="required"> * </span></label><div class="col-md-5"><input type="text" name="planAmt" value="" data-required="1" class="form-control"/></div>');
		
	}else{
		$('#actionTypeGrop').html('');
		$('#planAmt').html('');
	}
}
function showSendCoupon(data) {
	$.ajax({
        type: 'POST',
        url: BASE_PATH+'coupon/getCouponStatus',
        data: {"id":data},
        dataType: 'json',
        success: function (result) {
        	var cp = JSON.parse(result.obj);
        	if(cp.sendWay=="1"){
        		//手动发放
        		if(cp.status=="700"){
        			alert("发放已结束，不能重复发放！");
        		}else{
					$("#couponId").val(data);
					$("#sendCoupon").modal('show');
        		}
        	}else{
        		//系统发放
				if(confirm("确定要发放优惠券吗？")){
					$.ajax({
					    type: 'POST',
					    url: BASE_PATH+'coupon/saveSendCoupon',
					    data: {"id":data},
					    dataType: 'json',
					    success: function (r) {
					    	if(r.status==200){
					    		alert(r.msg);
					    		location.reload();
					    	}
					    },
					    error: function () {
					        alert("错误");
					    }
					});
				}
        	}
        },
        error: function () {
            alert("错误");
        }
    });
}

function saveSendObj(){
	$.ajax({
        type: 'POST',
        url: BASE_PATH+'coupon/saveSendCoupon',
        data: $('#sendCouponForm').serialize(),
        dataType: 'json',
        success: function (data) {
        	if(data.status==200){
        		alert(data.msg);
        		location.reload();
        	}
        },
        error: function () {
            alert("错误");
        }
    });
}
function stopSendCoupon(data){
	//异步获取优惠券状态
	$.ajax({
        type: 'POST',
        url: BASE_PATH+'coupon/getCouponStatus',
        data: {"id":data},
        dataType: 'json',
        success: function (result) {
        	var cp = JSON.parse(result.obj);
        	if(cp.sendWay=="1"&&cp.status=="700"){
        		//手动发放
       			alert("发放已结束，不能操作！");
        	}else{
        		//系统发放
	        	$.ajax({
			        type: 'POST',
			        url: BASE_PATH+'coupon/saveStopCoupon',
			        data: {'id':data},
			        dataType: 'json',
			        success: function (data) {
			        	if(data.status==200){
			        		alert(data.msg);
			        		location.reload();
			        	}
			        },
			        error: function () {
			            alert("错误");
			        }
			    });
        	}
        },
        error: function () {
            alert("错误");
        }
    });
}
</script>
<!-- END PAGE LEVEL PLUGINS -->
</head>
<body>

	<h3 class="page-title">
		优惠券管理
	</h3>
	<div class="row">
		<div class="col-md-12">
			<!-- Begin: life time stats -->
			<div class="portlet light portlet-fit portlet-datatable bordered">
				<div class="portlet-title">
					<div class="caption">
						<i class="icon-settings font-dark"></i> <span
							class="caption-subject font-dark sbold uppercase">优惠券列表</span>
					</div>
					<button type="button" class="btn sbold green" style="float: right; margin: 10px 5px; padding: 3px 10px; line-height: 30px;  color: white;" data-toggle="modal" data-target="#newCoupon">新建</button>
				</div>
				<div class="portlet-body">
					<div class="table-container">
						<table
							class="table table-striped table-bordered table-hover table-checkable"
							id="datatable_ajax">
							<thead>
								<tr role="row" class="heading">
									<th>优惠券名称</th>
									<th width="6%">发放方式</th>
									<th width="5%">优惠券有效期</th>
									<th width="7%">发放开<br/>始时间</th>
									<th width="7%">发放结<br/>束时间</th>
									<th width="6%">规则名称</th>
									<th width="5%">使用范围</th>
									<th width="6%">消费下限</th>
									<th width="6%">优惠额度</th>
									<th width="6%">发放人数</th>
									<th width="6%">发放数量</th>
									<th width="6%">领用数量</th>
									<th width="6%">使用数量</th>
									<th width="7%">创建时间</th>
									<th width="6%">状态</th>
									<th width="6%">操作</th>
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
<!-------------------------------------------------------- 新建优惠券--------------------------------------start---------------------------------------->
<div class="modal fade" id="newCoupon" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h3 class="modal-title" id="myModalLabel">新建优惠券</h3>
      </div>
      <div class="modal-body">

		<form action="#" id="form_sample_3" class="form-horizontal">
			<!-- END PAGE TITLE-->
			<div class="row">
				<div class="col-md-12">
					<div class="portlet light portlet-fit portlet-datatable bordered">
						<div class="portlet-body">
							<div class="form-body">
								<div class="alert alert-danger display-hide">
		                            <button class="close" data-close="alert"></button> 部分项目填写有误，详细见下 
		                            </div>
		                        <div class="alert alert-success display-hide">
		                            <button class="close" data-close="alert"></button> 信息添加成功!
		                         </div>							
								<div class="form-group">
									<label class="control-label col-md-3">优惠券名称 <span
										class="required"> * </span>
									</label>
									<div class="col-md-5">
										<input type="text" name="cpName" value="" class="form-control" />
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-3">优惠券说明 <span
										class="required"> * <br/>需要换行用"|"分割 </span>
									</label>
									<div class="col-md-5">
										<textarea rows="10" cols="50" name="cpDesc"></textarea>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-3">规则名称 <span
										class="required"> * </span>
									</label>
									<div class="col-md-5">
										<input type="text" name="ruleName" id="ruleName" value=""
											data-required="1" readonly="readonly"  class="form-control"/>
										<input type="text" name="ruleId" value="" id="ruleId" style="display:none;"/>
										<button type="button" class="btn sbold green" style="margin: 8px 3px; padding: 2px 5px; line-height: 20px;  color: white;" data-toggle="modal" data-target="#myRuleList" onclick="getRuleList()">选择规则</button>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-3">领用方式<span
										class="required"> * </span>
									</label>
									<div class="col-md-5">
										<select name=receiveWay>
											<option value="0" selected="selected">直接发放卡包</option>
											<option value="1">点击领用</option>
										</select>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-3">优惠券有效期<span
										class="required"> * </span>
									</label>
									<div class="col-md-5">
										<input type="radio" name="validTimeStr" value="1"  onchange="chooseValidDay()"> 有效天数
										<input type="radio" name="validTimeStr" value="2" checked="checked" onchange="chooseValidDay()"> 终止日期
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-3"><span
										class="required"> * </span>
									</label>
									<div class="col-md-5" id="validTime">
										<div class="input-group date date-picker margin-bottom-1" data-date-format="yyyy-mm-dd">
											<input type="text" class="form-control form-filter input-sm" readonly name="cpEndTime" placeholder="终止日期" value=""> <span class="input-group-btn">
												<button class="btn btn-sm default" type="button">
													<i class="fa fa-calendar"></i>
												</button>
											</span>
										</div>	
									</div>								
								</div>
								<div class="form-group">
									<label class="control-label col-md-3">发放方式<span
										class="required"> * </span>
									</label>
									<div class="col-md-5">
										<select name="sendWay" id="sendWay" onchange="sendWayChange()">
											<option value="0" selected="selected">系统发放</option>
											<option value="1">手动发放</option>
										</select>
									</div>
								</div>
								<div class="form-group" id="actionTypeGrop">
									<label class="control-label col-md-3">动作类型<span
										class="required"> * </span>
									</label>
									<div class="col-md-5">
										<select name="sendTarget" id="actionType">
											<option value="微信关注">微信关注</option>
											<option value="违章查询">违章查询</option>
											<option value="违章代缴">违章代缴</option>
											<option value="在线投保">在线投保</option>
										</select>
									</div>
								</div>
								<div class="form-group" id="planAmt">
									<label class="control-label col-md-3">发放数量<span
										class="required"> * </span>
									</label>
									<div class="col-md-5">
										<input type="text" name="planAmt" value="" data-required="1" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-3">发放开始时间</label>
                                    <div class="col-md-7">
                                        <div class="input-group date form_datetime" data-date-format="yyyy-mm-dd hh:ii:ss">
                                            <input type="text" size="16" readonly name="sendStartTimeStr" id="sendStartTimeStr" class="form-control" placeholder="From" required="required">
                                            <span class="input-group-btn">
                                                <button class="btn default date-reset" type="button">
                                                    <i class="fa fa-times"></i>
                                                </button>
                                                <button class="btn default date-set" type="button">
                                                    <i class="fa fa-calendar"></i>
                                                </button>
                                            </span>
                                        </div>
                                    </div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-3">发放结束时间</label>
                                    <div class="col-md-7">
                                        <div class="input-group date form_datetime" data-date-format="yyyy-mm-dd hh:ii:ss">
                                            <input type="text" size="16" readonly name="sendEndTimeStr" id="sendEndTimeStr" class="form-control" placeholder="To" required="required">
                                            <span class="input-group-btn">
                                                <button class="btn default date-reset" type="button">
                                                    <i class="fa fa-times"></i>
                                                </button>
                                                <button class="btn default date-set" type="button">
                                                    <i class="fa fa-calendar"></i>
                                                </button>
                                            </span>
                                        </div>
                                    </div>
								</div>
								
			                    <div class="form-actions">
			                        <div class="row">
			                            <div class="col-md-offset-3 col-md-9">
			                                <button type="submit" class="btn btn-primary">提交</button>
			                                <button type="button" class="btn btn-default" id="cancel">取消</button>
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
    </div>
  </div>
</div>
<!--------------------------------------------------------新建优惠券---------------------------end------------------------------------------------------>		
	
<!-------------------------------------------------------- 新建发放--------------------------------------start---------------------------------------->
<div class="modal fade" id="sendCoupon" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h3 class="modal-title" id="myModalLabel">新建发放</h3>
      </div>
      <div class="modal-body">
		<form action="" id="sendCouponForm" class="form-horizontal" method="post">
			<!-- END PAGE TITLE-->
			<div class="row">
				<div class="col-md-12">
					<div class="portlet light portlet-fit portlet-datatable bordered">
						<div class="portlet-body">
							<div class="form-body">
								<div class="form-group">
									<label class="control-label col-md-3">发放对象<span
										class="required"> * </span>
									</label>
									<div class="col-md-8">
										<input type="radio" name="sendRange" value="2" onchange="chooseSendRange()">车牌号
										<input type="radio" name="sendRange" value="3" checked="checked" onchange="chooseSendRange()">距离保期多少天以内
									</div>
								</div>
								<div class="form-group" id="sendRangeDetail">
									<label class="control-label col-md-3"><span
										class="required"> * </span>
									</label>
									<div class="col-md-5" >
										<input type="text" name="sendTarget" value=""  class="form-control"/>
									</div>								
								</div>
								<input type="text" name="id" value="" id="couponId" class="form-control" style="display:none;"/>
							</div>
						</div>
					</div>
				</div>
			</div>
		</form>		
					
	  </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button type="button" class="btn btn-primary" onclick="saveSendObj()">确定</button>
      </div>
    </div>
  </div>
</div>
<!--------------------------------------------------------新建发放---------------------------end------------------------------------------------------>	

<!-------------------------------------------------------- 规则列表--------------------------------------start---------------------------------------->
<div class="modal fade" id="myRuleList" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h3 class="modal-title" id="myRuleList">规则列表</h3>
      </div>
      <div class="modal-body">

		<!-- END PAGE TITLE-->
		<div class="row">
			<div class="col-md-12">
				<!-- Begin: life time stats -->
				<div class="portlet light portlet-fit portlet-datatable bordered">
					<div class="portlet-body">
						<div class="table-container">
							<%-- <form action="" id="getRuleList">
								<div class="form horizontal-form">
									<div class="form-body">
										<div class="form-group">
											<label class="control-label col-md-1" style="width:100px;">规则类型</label>
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
											<label class="control-label col-md-1" style="width:100px;">规则名称 </label>
											<div class="col-md-2">
												<input name="orderId" type="text" value="${po.id}" style="display:none"/>
												<input type="text" name="prmName" value="${prmType}" onkeyup="this.value=value.replace(/[^\u4E00-\u9FA5\w\\,\\，\\.\\。\\(\\)]/g,'')" />
											</div>
										</div>
									</div>
									<div class="form-actions right">
										<input type="button" value="搜索" onclick="getRuleList()"/> 
									</div>
									<!-- END FORM-->
								</div>
							</form> --%>
							<form action="${base}/coupon/ruleList" method="POST" id="ruleSubmit">
								<table class="table table-striped table-bordered table-hover" id="ruleList">
									<thead>
										<tr role="row" class="heading">
											<th width="20%">编号</th>
											<th>规则名称</th>
											<th width="20%">规则范围</th>
											<th width="20%">规则条件</th>
											<th width="20%">优惠额度</th>
										</tr>
									</thead>
									<tbody id="ruleBody">
										<c:forEach items="${rlist}" var="r">
											<tr >
												<td>${r.id}</td>
												<td>${r.ruleName}</td>
												<td>${r.useScopeStr}</td>
												<td>${r.insurerLimit}</td>
												<td>${r.saleAmtStr}</td>
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
      </div>
    </div>
  </div>
</div>
<!--------------------------------------------------------规则列表---------------------------end------------------------------------------------------>
</body>
<content tag="pageJs"> <script
	src="${base}/assets/global/scripts/datatable.js" type="text/javascript"></script>

<script
	src="${staticBase}/assets/global/plugins/jquery-validation/js/jquery.validate.min.js"
	type="text/javascript"></script> <script
	src="${staticBase}/assets/global/plugins/jquery-validation/js/localization/messages_zh.min.js"
	type="text/javascript"></script> <script
	src="${staticBase}/assets/global/plugins/jquery-validation/js/additional-methods.min.js"
	type="text/javascript"></script> <script
	src="${staticBase}/assets/global/plugins/datatables/datatables.min.js"
	type="text/javascript"></script> <script
	src="${staticBase}/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js"
	type="text/javascript"></script> <script
	src="${staticBase}/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js"
	type="text/javascript"></script> <script
	src="${base}/assets/pages/system/coupon/list.js" type="text/javascript"></script>
<script src="${base}/assets/pages/system/coupon/add.js"
	type="text/javascript"></script> <script
	src="${staticBase}/assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js"
	type="text/javascript"></script> <script
	src="${staticBase}/assets/global/plugins/bootstrap-datepicker/js/bootstrap-datetimepicker.min.js"
	type="text/javascript"></script> <script
	src="${staticBase}/assets/global/plugins/bootstrap-datepicker/js/bootstrap-timepicker.min.js"
	type="text/javascript"></script> <script
	src="${staticBase}/assets/global/plugins/components-date-time-pickers.js"
	type="text/javascript"></script> <script>
		jQuery(document).ready(function() {

		});
	</script> </content>
</html>
