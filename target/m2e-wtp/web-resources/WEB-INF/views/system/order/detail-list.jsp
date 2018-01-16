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
<h3 class="page-title">违章详情
    <small>共x条违章记录</small>
</h3>
<!-- END PAGE TITLE-->
<div class="row">
    <div class="col-md-12">
        <!-- Begin: life time stats -->
        <div class="portlet light portlet-fit portlet-datatable bordered">
            <div class="portlet-title">
                <div class="actions">
                    <div class="btn-group">
                        <a href='<c:url value="/order/confirmPay?orderid=${orderid}"/>' class="btn sbold green">
                            <i class="fa fa-plus"></i>确认缴费</a>
                    </div>
                </div>
            </div>
            <div class="portlet-body">
                <div class="table-container">
                    <table class="table table-striped table-bordered table-hover table-checkable" id="datatable_ajax">
                        <thead>
                        <tr role="row" class="heading">
                            <th width="10%"> 违章时间</th>
                            <th width="15%"> 违章地点</th>
                            <th width="200"> 违章原因</th>
                            <th width="10%">罚款金额</th>
                            <th width="10%"> 违章扣分</th>
                            <th width="10%"> 服务费</th>
                        </tr>
                        <c:forEach items="${list}" var="t" varStatus="s">
	                        <tr role="row" class="heading">
	                            <td width="10%">${t.occurTime}</td>
	                            <td width="15%">${t.address}</td>
	                            <td width="200">${t.content}</td>
	                            <td width="10%">${t.price}</td>
	                            <td width="10%">${t.score}</td>
	                            <td width="10%">${t.serverFee}</td>
	                        </tr>
                        </c:forEach>
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
    <script src="${base}/assets/pages/system/order/detail-list.js" type="text/javascript"></script>
    <!-- END PAGE LEVEL SCRIPTS -->
</content>
</html>
