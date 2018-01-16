<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layout/comtag.jsp" %>
<html>
<head>
    <!-- BEGIN PAGE LEVEL PLUGINS -->
    <link href="${staticBase}/assets/global/plugins/datatables/datatables.min.css" rel="stylesheet" type="text/css"/>
    <link href="${staticBase}/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css" rel="stylesheet"
          type="text/css"/>
    <!-- END PAGE LEVEL PLUGINS -->
       <link href="${staticBase}/assets/global/plugins/bootstrap-select/css/bootstrap-select.min.css" rel="stylesheet" type="text/css" />
    <link href="${staticBase}/assets/global/plugins/jquery-multi-select/css/multi-select.css" rel="stylesheet" type="text/css" />
    <link href="${staticBase}/assets/global/plugins/select2/css/select2.min.css" rel="stylesheet" type="text/css" />
    <link href="${staticBase}/assets/global/plugins/select2/css/select2-bootstrap.min.css" rel="stylesheet" type="text/css" />
  
</head>
<body>

<!-- BEGIN PAGE TITLE-->
<h3 class="page-title"> 员工管理
    <small></small>
</h3>
<!-- END PAGE TITLE-->
<div class="row">
    <div class="col-md-12">
        <!-- Begin: life time stats -->
        <div class="portlet light portlet-fit portlet-datatable bordered">
            <div class="portlet-title">
                <div class="caption">
                    <i class="icon-settings font-dark"></i>
                    <span class="caption-subject font-dark sbold uppercase">员工列表</span>
                </div>
                <div class="actions">
                    <div class="btn-group">
                        <a href='<c:url value="/staff/add"/>' class="btn sbold green">
                            <i class="fa fa-plus"></i> 新增员工</a>
                    </div>
                </div>
            </div>
            <div class="portlet-body">
                <div class="table-container">
                 <div class="form horizontal-form">
                          <div class="form-body">
                              <div class="col-md-12">
										<div class="col-md-4">
											<div class="form-group">
												<label class="control-label col-md-3">门店名称</label>
												<div class="col-md-7">
													<select class="form-control form-filter" name="orgId"id="orgId">
														
													</select>
												</div>
											</div>
										</div>
										<div class="col-md-4">
											<div class="form-group">
												<label class="control-label col-md-4">店员身份</label>
												<div class="col-md-7">
													 <select name="orgRole" class="form-control form-filter">
													 	<option value="">请选择店员身份</option>
		                                                <option value="1">店长</option>
		                                                <option value="2">店员</option>
		                                                <option value="3">加盟店长</option>
		                                                <option value="0">加盟店员</option>
		                                            </select>
												</div>
											</div>
										</div>
									</div>
                            </div>
                       <div class="form-actions right">
                            <div class="row">
                                <div class="col-md-offset-3 col-md-9">
                                    <button class="btn btn-sm green btn-outline filter-submit margin-bottom" >
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
                <!--     <div class="table-actions-wrapper">
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
                    </div> -->
                    <table class="table table-striped table-bordered table-hover table-checkable" id="datatable_ajax">
                        <thead>
                        <tr role="row" class="heading">
                            <th width="2%">序号</th>
                            <th width="10%">姓名 </th>
                            <th width="15%">电话</th>
                            <th width="10%">微信昵称</th>
                            <th width="10%">所属门店</th>
                            <th width="10%"> openId</th>
                            <th width="10%"> 身份</th>
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
    
    
     <script src="${staticBase}/assets/global/plugins/select2/js/select2.full.min.js" type="text/javascript"></script>
    <script src="${staticBase}/assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
    <script src="${staticBase}/assets/global/plugins/jquery-validation/js/localization/messages_zh.min.js" type="text/javascript"></script>
    <script src="${staticBase}/assets/global/plugins/jquery-validation/js/additional-methods.min.js" type="text/javascript"></script>

    
    <script src="${staticBase}/assets/global/plugins/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js" type="text/javascript"></script>
    <script src="${staticBase}/assets/global/plugins/bootstrap-select/js/bootstrap-select.min.js" type="text/javascript"></script>
    <script src="${staticBase}/assets/global/plugins/jquery-multi-select/js/jquery.multi-select.js" type="text/javascript"></script>
    <script src="${staticBase}/assets/global/plugins/jquery.quicksearch.js" type="text/javascript"></script>
  
    <!-- BEGIN PAGE LEVEL SCRIPTS -->
    <%-- <script src="${base}/assets/pages/system/staff/list.js" type="text/javascript"></script> --%>
    <script src="${base}/assets/pages/system/staff/list.js" type="text/javascript"></script>
    <!-- END PAGE LEVEL SCRIPTS -->
    <script type="text/javascript">
     	$.ajax({
		url: BASE_PATH + 'phone/orgnizationList',
		type: 'post',
		dataType: 'json',
		success: function(data){
			$("#orgId").empty();
			$("#orgId").select2({
				  data: data
			})
		}
    });
    </script>
</content>
</html>
