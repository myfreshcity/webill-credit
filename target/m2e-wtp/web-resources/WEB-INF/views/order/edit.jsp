<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <!-- BEGIN PAGE LEVEL PLUGINS -->
    <link href="${staticBase}/assets/global/plugins/datatables/datatables.min.css" rel="stylesheet" type="text/css"/>
    <link href="${staticBase}/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css" rel="stylesheet"
          type="text/css"/>
    <!-- END PAGE LEVEL PLUGINS -->
</head>
<body>

<!-- BEGIN PAGE TITLE-->
<h3 class="page-title"> 订单管理
    <small>订单相关操作</small>
</h3>
<!-- END PAGE TITLE-->

<div class="row">
    <div class="col-md-12">
        <!-- BEGIN VALIDATION STATES-->
        <div class="portlet light portlet-fit portlet-form bordered">
            <div class="portlet-title">
                <div class="caption">
                    <i class="icon-settings font-dark"></i>
                    <span class="caption-subject font-dark sbold uppercase">订单编辑</span>
                </div>
            </div>
            <div class="portlet-body">
                <!-- BEGIN FORM-->
                <form action="#" id="form_sample_3" class="form-horizontal">
                    <div class="form-body">
                        <div class="alert alert-danger display-hide">
                            <button class="close" data-close="alert"></button> 部分项目填写有误，详细见下 </div>
                        <div class="alert alert-success display-hide">
                            <button class="close" data-close="alert"></button> 校验通过! </div>
                        <div class="form-group">
                            <label class="control-label col-md-3">Name
                                <span class="required"> * </span>
                            </label>
                            <div class="col-md-4">
                                <input type="text" name="name" data-required="1" class="form-control" /> </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">Email Address
                                <span class="required"> * </span>
                            </label>
                            <div class="col-md-4">
                                <div class="input-group">
                                                        <span class="input-group-addon">
                                                            <i class="fa fa-envelope"></i>
                                                        </span>
                                    <input type="email" name="email" class="form-control" placeholder="Email Address"> </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-md-3">Occupation&nbsp;&nbsp;</label>
                            <div class="col-md-4">
                                <input name="occupation" type="text" class="form-control" /> </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-md-3">Select2 Dropdown
                                <span class="required"> * </span>
                            </label>
                            <div class="col-md-4">
                                <select class="form-control select2me" name="options2">
                                    <option value="">Select...</option>
                                    <option value="Option 1">Option 1</option>
                                    <option value="Option 2">Option 2</option>
                                    <option value="Option 3">Option 3</option>
                                    <option value="Option 4">Option 4</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-md-3">Datepicker</label>
                            <div class="col-md-4">
                                <div class="input-group date date-picker" data-date-format="dd-mm-yyyy">
                                    <input type="text" class="form-control" readonly name="datepicker">
                                    <span class="input-group-btn">
                                                            <button class="btn default" type="button">
                                                                <i class="fa fa-calendar"></i>
                                                            </button>
                                                        </span>
                                </div>
                                <!-- /input-group -->
                                <span class="help-block"> select a date </span>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-md-3">Membership
                                <span class="required"> * </span>
                            </label>
                            <div class="col-md-4">
                                <div class="radio-list" data-error-container="#form_2_membership_error">
                                    <label>
                                        <input type="radio" name="membership" value="1" /> Fee </label>
                                    <label>
                                        <input type="radio" name="membership" value="2" /> Professional </label>
                                </div>
                                <div id="form_2_membership_error"> </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-md-3">Services
                                <span class="required"> * </span>
                            </label>
                            <div class="col-md-4">
                                <div class="checkbox-list" data-error-container="#form_2_services_error">
                                    <label>
                                        <input type="checkbox" value="1" name="service" /> Service 1 </label>
                                    <label>
                                        <input type="checkbox" value="2" name="service" /> Service 2 </label>
                                    <label>
                                        <input type="checkbox" value="3" name="service" /> Service 3 </label>
                                </div>
                                <span class="help-block"> (select at least two) </span>
                                <div id="form_2_services_error"> </div>
                            </div>
                        </div>
                    </div>
                    <div class="form-actions">
                        <div class="row">
                            <div class="col-md-offset-3 col-md-9">
                                <button type="submit" class="btn green">提交</button>
                                <button type="button" class="btn default">取消</button>
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
    <script src="${staticBase}/assets/global/plugins/select2/js/select2.full.min.js" type="text/javascript"></script>
    <script src="${staticBase}/assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
    <script src="${staticBase}/assets/global/plugins/jquery-validation/js/localization/messages_zh.min.js" type="text/javascript"></script>
    <script src="${staticBase}/assets/global/plugins/jquery-validation/js/additional-methods.min.js" type="text/javascript"></script>

    <script src="${staticBase}/assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" type="text/javascript" charset="UTF-8"></script>
    <script src="${staticBase}/assets/global/plugins/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js" type="text/javascript"></script>
    <!-- END PAGE LEVEL PLUGINS -->
    <!-- BEGIN PAGE LEVEL SCRIPTS -->
    <script src="${base}/assets/pages/order/form-validation.js" type="text/javascript"></script>
    <!-- END PAGE LEVEL SCRIPTS -->
</content>
</html>