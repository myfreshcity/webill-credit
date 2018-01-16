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
            </div>
            <div class="portlet-body">
                <div class="table-container">
                    <table class="table table-striped table-bordered table-hover table-checkable" id="datatable_ajax">
                        <thead>
                        <tr role="row" class="heading">
                            <th width="2%">
                            <input type="checkbox" class="group-checkable"></th>
                            <th width="10%"> 车牌号</th>
                            <th width="15%"> 车主姓名</th>
                            <th width="200"> 车辆类型</th>
                            <th width="10%"> 车架号</th>
                            <th width="10%"> 发动机号</th>
                            <th width="10%"> 违章数</th>
                            <th width="10%"> 订单号</th>
                            <th width="10%"> 状态</th>
                            <th width="10%"> 操作</th>
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
    <script src="${base}/assets/pages/system/order/list.js" type="text/javascript"></script>
    <!-- END PAGE LEVEL SCRIPTS -->
</content>
</html>
