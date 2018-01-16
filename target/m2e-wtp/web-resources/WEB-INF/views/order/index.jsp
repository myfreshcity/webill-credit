<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layout/comtag.jsp" %>
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
        <!-- Begin: life time stats -->
        <div class="portlet light portlet-fit portlet-datatable bordered">
            <div class="portlet-title">
                <div class="caption">
                    <i class="icon-settings font-dark"></i>
                    <span class="caption-subject font-dark sbold uppercase">订单列表</span>
                </div>
                <div class="actions">
                    <div class="btn-group">
                        <a href='<c:url value="/order/add"/>' class="btn sbold green">
                            <i class="fa fa-plus"></i> 新增订单</a>
                    </div>
                </div>
            </div>
            <div class="portlet-body">
                <div class="table-container">
                    <div class="form horizontal-form">
                        <!-- BEGIN FORM-->
                        <div class="form-body">
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label class="control-label col-md-3">First Name</label>
                                        <div class="col-md-9">
                                            <input type="text" class="form-control form-filter input-sm" name="order_customer_name" placeholder="Chee Kin">
                                        </div>
                                    </div>
                                </div>
                                <!--/span-->
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label class="control-label col-md-3">Last Name</label>
                                        <div class="col-md-9">
                                            <select name="foo" class="form-control">
                                                <option value="1">Abc</option>
                                                <option value="1">Abc</option>
                                                <option value="1">ABC</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <!--/span-->
                            </div>
                            <!--/row-->
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label class="control-label col-md-3">Gender</label>
                                        <div class="col-md-9">
                                            <select class="form-control">
                                                <option value="">Male</option>
                                                <option value="">Female</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <!--/span-->
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label class="control-label col-md-3">日期</label>
                                        <div class="col-md-4">
                                            <div class="input-group input-large date-picker input-daterange" data-date="10/11/2012" data-date-format="mm/dd/yyyy">
                                                <input type="text" class="form-control" name="from">
                                                <span class="input-group-addon"> 到 </span>
                                                <input type="text" class="form-control" name="to"> </div>
                                            <!-- /input-group -->
                                        </div>
                                    </div>
                                </div>
                                <!--/span-->
                            </div>
                            <!--/row-->
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label class="control-label col-md-3">Category</label>
                                        <div class="col-md-9">
                                            <select name="order_status" class="form-control form-filter input-sm">
                                                <option value="">Select...</option>
                                                <option value="pending">Pending</option>
                                                <option value="closed">Closed</option>
                                                <option value="hold">On Hold</option>
                                                <option value="fraud">Fraud</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <!--/span-->
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label class="control-label col-md-3">Membership</label>
                                        <div class="col-md-9">
                                            <div class="radio-list">
                                                <label class="radio-inline">
                                                    <span><input type="radio" name="optionsRadios4" value="option1" class="form-filter"></span> Free </label>
                                                <label class="radio-inline">
                                                    <span class="checked"><input type="radio" name="optionsRadios4" value="option2" checked="" class="form-filter"></span> Professional </label>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!--/span-->
                            </div>
                        </div>
                        <div class="form-actions right">
                            <div class="row">
                                <div class="col-md-offset-3 col-md-9">
                                    <button class="btn btn-sm green btn-outline filter-submit margin-bottom">
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
                    <div class="table-actions-wrapper">
                        <span> </span>
                        <select class="table-group-action-input form-control input-inline input-small input-sm">
                            <option value="">请选择</option>
                            <option value="Cancel">Cancel</option>
                            <option value="Cancel">Hold</option>
                            <option value="Cancel">On Hold</option>
                            <option value="Close">Close</option>
                        </select>
                        <button class="btn btn-sm green table-group-action-submit">
                            <i class="fa fa-check"></i> 提交
                        </button>
                    </div>
                    <table class="table table-striped table-bordered table-hover table-checkable" id="datatable_ajax">
                        <thead>
                        <tr role="row" class="heading">
                            <th width="2%">
                                <input type="checkbox" class="group-checkable"></th>
                            <th width="5%"> Record&nbsp;#</th>
                            <th width="15%"> Date</th>
                            <th width="200"> Customer</th>
                            <th width="10%"> Ship&nbsp;To</th>
                            <th width="10%"> Price</th>
                            <th width="10%"> Amount</th>
                            <th width="10%"> Status</th>
                            <th width="10%"> Actions</th>
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
<content tag="pageJs">
    <!-- BEGIN PAGE LEVEL PLUGINS -->
    <script src="${base}/assets/global/scripts/datatable.js" type="text/javascript"></script>
    <script src="${staticBase}/assets/global/plugins/datatables/datatables.min.js" type="text/javascript"></script>
    <script src="${staticBase}/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js"
            type="text/javascript"></script>
    <script src="${staticBase}/assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js"
            type="text/javascript"></script>
    <!-- END PAGE LEVEL PLUGINS -->
    <!-- BEGIN PAGE LEVEL SCRIPTS -->
    <script src="${base}/assets/pages/order/table-datatables-ajax.js" type="text/javascript"></script>
    <!-- END PAGE LEVEL SCRIPTS -->
</content>
</html>
