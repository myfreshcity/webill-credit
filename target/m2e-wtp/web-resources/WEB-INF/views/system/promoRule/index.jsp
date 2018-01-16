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
	//------------------------------------使用范围动态选项---------------start-----------------------------------
	function rangeChange() {
		var selectParam = $('#useRange').val();
		if(selectParam==2){
			$('#insurerLimit').find("option").remove(); 
			$('#insurerLimit').html('<option value=\"阳光保险\">阳光保险</option><option value=\"太平洋保险\">太平洋保险</option><option value=\"人民保险\">人民保险</option><option value=\"平安保险\">平安保险</option>');
		}else{
			$('#insurerLimit').html('<option value=\"服务费\">服务费</option>');
		}
	}
	//------------------------------------使用范围动态选项---------------end-----------------------------------
	//------------------------------------规则启用、禁用操作--------------start----------------------------
	function doRule(data,flag){
		$.ajax({
	        type: 'POST',
	        url: BASE_PATH+'promoRule/doRuleAble',
	        data: {'id':data,'flag':flag},
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
	//------------------------------------规则启用、禁用操作--------------end----------------------------
</script>
<!-- END PAGE LEVEL PLUGINS -->
</head>
<body>

	<h3 class="page-title">
		规则管理
	</h3>
	<div class="row">
		<div class="col-md-12">
			<!-- Begin: life time stats -->
			<div class="portlet light portlet-fit portlet-datatable bordered">
				<div class="portlet-title">
					<div class="caption">
						<i class="icon-settings font-dark"></i> <span
							class="caption-subject font-dark sbold uppercase">规则列表</span>
					</div>
					<button type="button" class="btn sbold green" style="float: right; margin: 10px 5px; padding: 3px 10px; line-height: 30px;  color: white;" data-toggle="modal" data-target="#newCouponModal">新建</button>
				</div>
				<div class="portlet-body">
					<div class="table-container">
						<table
							class="table table-striped table-bordered table-hover table-checkable"
							id="datatable_ajax">
							<thead>
								<tr role="row" class="heading">
									<th>优惠券规则名称</th>
									<th width="7%">使用地区</th>
									<th width="7%">使用范围</th>
									<th width="7%">使用条件</th>
									<th width="7%">消费下限</th>
									<th width="7%">优惠效果</th>
									<th width="7%">优惠额度</th>
									<th width="7%">领用条件</th>
									<th width="7%">是否可转赠</th>
									<th width="7%">是否活动<br>同时使用</th>
									<th width="7%">状态</th>
									<th width="10%">操作</th>
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
	
	
<!-------------------------------------------------------- 新建规则--------------------------------------start---------------------------------------->
<div class="modal fade" id="newCouponModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h3 class="modal-title" id="myModalLabel">新建规则</h3>
      </div>
      <div class="modal-body">

		<form action="#" id="form_sample_3" class="form-horizontal">
			<!-- END PAGE TITLE-->
			<div class="row">
				<div class="col-md-12">
					<div class="portlet light portlet-fit portlet-datatable bordered">
						<div class="portlet-body">
							<div class="form-body">
								<div class="form-group">
									<label class="control-label col-md-3">规则名称 <span
										class="required"> * </span>
									</label>
									<div class="col-md-5">
										<input type="text" name="ruleName" value="" class="form-control" />
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-3">使用地区<span
										class="required"> * </span>
									</label>
									<div class="col-md-5">
										<input type="text" name="areaLimit" id="areaLimit" value="全部" class="form-control" readonly="readonly"/>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-3">使用范围<span
										class="required"> * </span>
									</label>
									<div class="col-md-5">
										<select name="useRange" id="useRange" onchange="rangeChange()">
											<option value="1">违章</option>
											<option value="2">投保</option>
										</select>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-3">使用条件<span
										class="required"> * </span>
									</label>
									<div class="col-md-5">
										<select name="insurerLimit" id="insurerLimit">
											<option value="服务费">服务费</option>
										</select>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-3">消费下限<span
										class="required"> * </span>
									</label>
									<div class="col-md-5">
										<input type="text" name="amtLimit" id="amtLimit" value="0" data-required="1" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-3">优惠效果<span
										class="required"> * </span>
									</label>
									<div class="col-md-5">
										<select name="saleResult">
											<option value="0" selected="selected">抵用</option>
											<option value="1">折扣</option>
											<!-- <option value="2">兑换礼包</option> -->
										</select>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-3">优惠额度<span
										class="required"> * <br>选择优惠效果为“折扣时” 例：85折，请输入0.85</span>
									</label>
									<div class="col-md-5">
										<input type="text" name="saleAmt" id="saleAmt" value="" data-required="1" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-3">领用条件<span
										class="required"> * </span>
									</label>
									<div class="col-md-5">
										<input type="text" name="" id="" value="所有用户" readonly="readonly" data-required="1" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-3">每人领用上限<span
										class="required"> * </span>
									</label>
									<div class="col-md-5">
										<input type="text" name="maxNum" id="maxNum" value="1" readonly="readonly" data-required="1" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-3">是否可转赠<span
										class="required"> * </span>
									</label>
									<div class="col-md-5">
										<select name="transferType">
											<option value="1" selected="selected">可转赠</option>
											<option value="2">只可转赠</option>
											<option value="0">不可转赠</option>
										</select>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-3">是否可与促销活动同时使用<span
										class="required"> * </span>
									</label>
									<div class="col-md-5">
										<select name="activityType">
											<option value="1" selected="selected">可以</option>
											<option value="2">只可</option>
											<option value="0">不可</option>
										</select>
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
<!--------------------------------------------------------新建规则---------------------------end------------------------------------------------------>	
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
	src="${staticBase}/assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js"
	type="text/javascript"></script> <script
	src="${base}/assets/pages/system/promoRule/list.js"
	type="text/javascript"></script> <script
	src="${base}/assets/pages/system/promoRule/add.js"
	type="text/javascript"></script> <script>
		jQuery(document).ready(function() {

		});
	</script> </content>
</html>
