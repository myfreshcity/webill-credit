<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layout/comtag.jsp" %>
<html>
<head>
    <!-- BEGIN PAGE LEVEL PLUGINS -->
    <link href="${staticBase}/assets/global/plugins/datatables/datatables.min.css" rel="stylesheet" type="text/css"/>
    <link href="${staticBase}/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css" rel="stylesheet"
          type="text/css"/>

      <link href="${staticBase}/assets/global/plugins/bootstrap-select/css/bootstrap-select.min.css" rel="stylesheet" type="text/css" />
    <link href="${staticBase}/assets/global/plugins/jquery-multi-select/css/multi-select.css" rel="stylesheet" type="text/css" />
    <link href="${staticBase}/assets/global/plugins/select2/css/select2.min.css" rel="stylesheet" type="text/css" />
    <link href="${staticBase}/assets/global/plugins/select2/css/select2-bootstrap.min.css" rel="stylesheet" type="text/css" />
    <!-- END PAGE LEVEL PLUGINS -->
<link href="${staticBase}/assets/global/plugins/cityselect/css/common.css" rel="stylesheet">
<link href="${staticBase}/assets/global/plugins/cityselect/css/select2.css" rel="stylesheet">

</head>
<body>

	<div style="float: left; margin: 3px;">
		<h3 class="page-title">用户信息</h3>
	</div>
<!-- END PAGE TITLE-->

<div class="row">
    <div class="col-md-12">
        <!-- BEGIN VALIDATION STATES-->
        <div class="portlet light portlet-fit portlet-form bordered">
    		<div class="portlet-title">
				<div class="caption">
					<i class="icon-settings font-dark"></i> <span
						class="caption-subject font-dark sbold uppercase">配送信息</span>
				</div>
			</div>
            <div class="portlet-body">
                <!-- BEGIN FORM-->
			<form action="#" id="form_sample_3" class="form-horizontal" >
				<!-- END PAGE TITLE-->
				<div class="row">
					<div class="col-md-12">
						<div class="portlet light portlet-fit portlet-datatable bordered">
							<div class="portlet-body">
								<div class="form-body">
									<input id="userId" name="userId" value="${uc.userId}" style="display:none" >
									<input name="id" value="${uc.id}" style="display:none" >
									<div class="form-group">
										<label class="control-label col-md-3">微信昵称 <span
											class="required"> * </span>
										</label>
										<div class="col-md-5">
											<input type="text" name="weixinNick" value="${uc.weixinNick }"
												data-required="1" class="form-control" readonly="readonly"/>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-md-3">收件人姓名<span
											class="required"> * </span>
										</label>
										<div class="col-md-5">
											<input type="text" name="contactName" value="${uc.contactName}"
												data-required="1" class="form-control" />
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-md-3">联系人手机 <span
											class="required"> * </span>
										</label>
										<div class="col-md-5">
											<input type="text" name="mobile" value="${uc.mobile}" data-required="1" class="form-control"/>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-md-3">电子邮箱 <span
											class="required"> * </span>
										</label>
										<div class="col-md-5">
											<input type="text" name="email" value="${uc.email}" data-required="1" class="form-control" />
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-md-3">所在地区<span
											class="required"> * </span>
										</label>
										<div class="col-md-5">
										        <select id="loc_province" style="width:120px;"></select>
											    <select id="loc_city" style="width:120px; margin-left: 10px"></select>
											    <select id="loc_town" style="width:120px;margin-left: 10px"></select>
											<input type="text" name="province" id="province" value="${uc.province}" class="form-control" style="display:none"/>
											<input type="text" name="city" id="city" value="${uc.city}" class="form-control" style="display:none"/>
											<input type="text" name="area" id="area" value="${uc.area}" class="form-control" style="display:none"/>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-md-3">详细地址<span
											class="required"> * </span>
										</label>
										<div class="col-md-5">
											<input type="text" name="address" value="${uc.address}"
												data-required="1" class="form-control" />
										</div>
									</div>
				                    <div class="form-actions">
				                        <div class="row">
				                            <div class="col-md-offset-3 col-md-9">
				                                <button type="submit" class="btn green">提交</button>
				                                <button type="button" class="btn default" onclick="javascript:history.back(-1);">取消</button>
				                            </div>
				                        </div>
				                    </div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</form>
                <!-- END FORM-->
            </div>
            <!-- END VALIDATION STATES-->
        </div>
    </div>
</div>

</body>
<content tag="pageJs">
    <!-- BEGIN PAGE LEVEL PLUGINS -->
    <script type="text/javascript" src="${staticBase}/assets/global/plugins/jquery.min.js"></script>
 <script src="${staticBase}/assets/global/plugins/select2/js/select2.full.min.js" type="text/javascript"></script>
    <script src="${staticBase}/assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
    <script src="${staticBase}/assets/global/plugins/jquery-validation/js/localization/messages_zh.min.js" type="text/javascript"></script>
    <script src="${staticBase}/assets/global/plugins/jquery-validation/js/additional-methods.min.js" type="text/javascript"></script>

    <script src="${staticBase}/assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" type="text/javascript" charset="UTF-8"></script>
    <script src="${staticBase}/assets/global/plugins/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js" type="text/javascript"></script>

    <script src="${staticBase}/assets/global/plugins/bootstrap-select/js/bootstrap-select.min.js" type="text/javascript"></script>
    <script src="${staticBase}/assets/global/plugins/jquery-multi-select/js/jquery.multi-select.js" type="text/javascript"></script>
    <script src="${staticBase}/assets/global/plugins/jquery.quicksearch.js" type="text/javascript"></script>
    
    <script type="text/javascript" src="${staticBase}/assets/global/plugins/cityselect/js/area.js"></script>
<script type="text/javascript" src="${staticBase}/assets/global/plugins/cityselect/js/location.js"></script>
<script type="text/javascript" src="${staticBase}/assets/global/plugins/cityselect/js/select2.js"></script>
<script type="text/javascript" src="${staticBase}/assets/global/plugins/cityselect/js/select2_locale_zh-CN.js"></script>
    <!-- END PAGE LEVEL PLUGINS -->
    <!-- BEGIN PAGE LEVEL SCRIPTS -->
    <script src="${base}/assets/pages/system/userContact/edit.js" type="text/javascript"></script>
    <!-- END PAGE LEVEL SCRIPTS -->
     <script>
        jQuery(document).ready(function() {
        	
        });
    </script>
</content>
</html>