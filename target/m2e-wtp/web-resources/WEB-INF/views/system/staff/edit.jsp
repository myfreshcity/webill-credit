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
</head>
<body>

<!-- BEGIN PAGE TITLE-->
<h3 class="page-title"> 员工管理
    <small></small>
</h3>
<!-- END PAGE TITLE-->

<div class="row">
    <div class="col-md-12">
        <!-- BEGIN VALIDATION STATES-->
        <div class="portlet light portlet-fit portlet-form bordered">
            <div class="portlet-title">
                <div class="caption">
                    <i class="icon-settings font-dark"></i>
                    <span class="caption-subject font-dark sbold uppercase">员工编辑</span>
                </div>
            </div>
            <div class="portlet-body">
                <!-- BEGIN FORM-->
                <form action="#" id="form_sample_3" class="form-horizontal">
                    <div class="form-body">
                        <div class="alert alert-danger display-hide">
                            <button class="close" data-close="alert"></button> 部分项目填写有误，详细见下 
                            </div>
                        <div class="alert alert-success display-hide">
                            <button class="close" data-close="alert"></button> 信息添加成功!
                         </div>
                        <input id="id" name="id" value="${user.id}" hidden>
                        <div class="form-group">
                            <label class="control-label col-md-3">微信openid
                                <span class="required"> * </span>
                            </label>
                            <div class="col-md-4">
                                <input type="text" name="openId" value="${user.openId}" data-required="1" class="form-control" onkeyup="this.value=value.replace(/[^\u4E00-\u9FA5\w\\,\\，\\.\\。\\(\\)]/g,'')"  /> 
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-md-3">店员姓名
                                <span class="required"> * </span>
                            </label>
                            <div class="col-md-4">
                                <input type="text" name="realName" value="${user.realName}" data-required="1" class="form-control" onkeyup="this.value=value.replace(/[^\u4E00-\u9FA5\w\\,\\，\\.\\。\\(\\)]/g,'')" /> 
                             </div>
                        </div>
                     <%--    <div class="form-group">
                            <label class="control-label col-md-3">密码
                           	 <span class="required"> * </span>
                            </label>
                            <div class="col-md-4">
                                <input name="password" type="password" value="${user.password}" class="form-control" /> </div>
                        </div> --%>
                         <div class="form-group">
                            <label class="control-label col-md-3">电话
                                <span class="required"> * </span>
                            </label>
                            <div class="col-md-4">
                                <input type="text" name="mobileNo" value="${user.mobileNo}" data-required="1" class="form-control" onkeyup="this.value=this.value.replace(/[^\d\\-\\-]/g,'') " /> 
                             </div>
                        </div>
			            <div class="form-group">
                            <label class="control-label col-md-3">所在门店
                                <span class="required"> * </span>
                            </label>
                            <div class="col-md-4">
	                            <select class="form-control" name="orgId" id="orgId" value="${orgId}" >
	                                <c:forEach var="OrganizationName" items="${Organization.name}">
	                                       <option value="${OrganizationName.id}">${OrganizationName.name}<${OrganizationName.prov}${OrganizationName.city}></option>
	                                 </c:forEach>
	                            </select>
                            </div> 
                         </div>
              <!--           <div class="form-group">
                            <label class="control-label col-md-3">进店日期</label>
                            <div class="col-md-4">
                                <div class="input-group date date-picker" data-date-format="dd-mm-yyyy">
                                    <input type="text" class="form-control" readonly name="datepicker">
                                    <span class="input-group-btn">
                                                            <button class="btn default" type="button">
                                                                <i class="fa fa-calendar"></i>
                                                            </button>
                                                        </span>
                                </div>
                                /input-group
                                <span class="help-block"> select a date </span>
                            </div>
                        </div> -->
                        <div class="form-group">
                            <label class="control-label col-md-3">身份
                                <span class="required"> * </span>
                            </label>
                            <div class="col-md-4">
                                <div class="radio-list" data-error-container="#form_2_membership_error">
                                    <select class="form-control" name="orgRole" id="orgRole">
	                                    <option value="">选择身份.</option><!-- z这里name对应后台实体的名称 -->
	                                    <option value="1">店长</option>
	                                    <option value="2">店员</option>
	                                    <option value="3">加盟店长</option>
	                                    <option value="0">加盟店员</option>
	                                </select>
                                </div>
                                <div id="form_2_membership_error"> </div>
                            </div>
                        </div>
                    </div>
                    <div class="form-actions">
                        <div class="row">
                            <div class="col-md-offset-3 col-md-9">
                                <button type="submit" class="btn green">提交</button>
                                <button type="button" class="btn default" id="cancel">取消</button>
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

    <script src="${staticBase}/assets/global/plugins/bootstrap-select/js/bootstrap-select.min.js" type="text/javascript"></script>
    <script src="${staticBase}/assets/global/plugins/jquery-multi-select/js/jquery.multi-select.js" type="text/javascript"></script>
    <script src="${staticBase}/assets/global/plugins/jquery.quicksearch.js" type="text/javascript"></script>
    
    
    <!-- END PAGE LEVEL PLUGINS -->
    <!-- BEGIN PAGE LEVEL SCRIPTS -->
    <script src="${base}/assets/pages/system/staff/edit.js" type="text/javascript"></script>
    <!-- END PAGE LEVEL SCRIPTS -->
     <script>
        jQuery(document).ready(function() {
        	 $("#form_sample_3 #orgId").select2().val('${user.orgId}').trigger('change'); 
   /*      	$("#form_sample_3 #orgId").select2().val('${user.orgId}').trigger('change'); */
            $("#form_sample_3 #orgRole").select().val('${user.orgRole}').trigger('change');
        });
    </script>
</content>
</html>