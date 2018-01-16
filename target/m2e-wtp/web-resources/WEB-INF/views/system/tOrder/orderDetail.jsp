<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layout/comtag.jsp" %>
<%@ include file="/WEB-INF/views/layout/comtag.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html lang="en">
<head>
     <link href="http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700&subset=all" rel="stylesheet" type="text/css" />
	 <script src="${staticBase}/assets/global/plugins/jquery.min.js" type="text/javascript"></script>
     <link href="${staticBase}/assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
     <link href="${staticBase}/assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css" />
     <link href="${staticBase}/assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
     <link href="${staticBase}/assets/global/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css" />
     <link href="${staticBase}/assets/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet" type="text/css" />
     <!-- END GLOBAL MANDATORY STYLES -->
     <!-- BEGIN THEME GLOBAL STYLES -->
     <link href="${staticBase}/assets/global/css/components.min.css" rel="stylesheet" id="style_components" type="text/css" />
     <link href="${staticBase}/assets/global/css/plugins.min.css" rel="stylesheet" type="text/css" />
     <!-- END THEME GLOBAL STYLES -->
     <!-- BEGIN THEME LAYOUT STYLES -->
     <link href="${staticBase}/assets/layouts/layout/css/layout.min.css" rel="stylesheet" type="text/css" />
     <link href="${staticBase}/assets/layouts/layout/css/custom.min.css" rel="stylesheet" type="text/css" id="style_color" />
    <!-- END GLOBAL MANDATORY STYLES -->
    <link rel="shortcut icon" href="favicon.ico" /> 
</head>
<body>
<!-- BEGIN PAGE TITLE-->
<!-- END PAGE TITLE-->
<form action="#" id="form_sample_3" class="form-horizontal" >
<div class="row">
    <div class="col-md-12">
        <!-- BEGIN VALIDATION STATES-->
        <div class="portlet light portlet-fit portlet-form bordered">
            <div class="portlet-title">
                <div class="caption">
                    <i class="icon-settings font-dark"></i>
                    <span class="caption-subject font-dark sbold uppercase">订单详情-待付款</span>
                </div>
            </div>
            <div class="portlet-body">
                <div class="mt-element-step">
                    <div class="row step-line">
                        <div class="col-md-3 mt-step-col first done">
                            <div class="mt-step-number bg-white">1</div>
                            <div class="mt-step-title uppercase font-grey-cascade">创建订单</div>
                            <div class="mt-step-content font-grey-cascade"><fmt:formatDate value='${po.createdTime}' pattern='yyyy-MM-dd hh:mm:ss'/></div>
                        </div>

                        <div class="col-md-3 mt-step-col<c:choose><c:when test="${po.tStatus==20||po.tStatus lt 20 }"> active</c:when><c:otherwise> done</c:otherwise></c:choose>">
                            <div class="mt-step-number bg-white">2</div>
                            <div class="mt-step-title uppercase font-grey-cascade">支付订单</div>
                            <div class="mt-step-content font-grey-cascade"><fmt:formatDate value='${po.payTime}' pattern='yyyy-MM-dd'/></div>
                        </div>
                        <div class="col-md-3 mt-step-col<c:choose><c:when test="${po.tStatus==30||po.tStatus lt 30}"> active</c:when><c:otherwise> done</c:otherwise></c:choose>">
                            <div class="mt-step-number bg-white">3</div>
                            <div class="mt-step-title uppercase font-grey-cascade">出单中</div>
                            <div class="mt-step-content font-grey-cascade"><fmt:formatDate value='${po.orderIssueTime}' pattern='yyyy-MM-dd'/></div>
                        </div>
                        <div class="col-md-3 mt-step-col<c:choose><c:when test="${po.tStatus==40||po.tStatus lt 40}"> last</c:when><c:otherwise> last done</c:otherwise></c:choose>">
                            <div class="mt-step-number bg-white">4</div>
                            <div class="mt-step-title uppercase font-grey-cascade">已出单</div>
                            <div class="mt-step-content font-grey-cascade"><fmt:formatDate value='${po.insureIssueTime}' pattern='yyyy-MM-dd'/></div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="portlet-title">
                <div class="caption">
                    <i class=" icon-layers font-green"></i>
                    <span class="caption-subject font-green bold uppercase">当前订单状态：
	                    <c:choose>
		                    <c:when test="${po.tStatus==-1}">已删除</c:when>
		                    <c:when test="${po.tStatus==0}">新建</c:when>
		                    <c:when test="${po.tStatus==10}">待付款</c:when>
		                    <c:when test="${po.tStatus==20}">已支付</c:when>
		                    <c:when test="${po.tStatus==30}">出单中</c:when>
		                    <c:when test="${po.tStatus==40}">已出单</c:when>
		                    <c:when test="${po.tStatus==50}">退保中</c:when>
		                    <c:when test="${po.tStatus==60}">已退保</c:when>
		                    <c:when test="${po.tStatus==80}">已关闭</c:when>
		                    <c:when test="${po.tStatus==90}">已失效</c:when>
	                    </c:choose>
                    </span>
                </div>
            </div>             
            <div class="portlet-title">
                <div class="caption">
                    <i class=" icon-layers font-green"></i>
                    <span class="caption-subject font-dark bold uppercase">基本信息</span>
                </div>
            </div>            
            <div class="portlet-body">
                <!-- BEGIN FORM-->
					<table class="table table-striped table-bordered table-hover table-checkable" id="datatable_ajax">
						<thead>
							<tr role="row" class="heading">
								<th width="20%">订单编号</th>
								<th width="20%">出单状态</th>
								<th width="20%">手机号</th>
								<th width="20%">支付方式</th>
								<th width="20%">订单来源</th>
							</tr>
						</thead>
						<tbody>
							<tr role="row">
								<td width="20%">${po.transNo}</td>
								<td width="20%">
									<c:if test="${po.issueStatus==0}">未出单</c:if>
									<c:if test="${po.issueStatus==1}">已出单</c:if>
									<c:if test="${po.issueStatus==2}">延时出单</c:if>
									<c:if test="${po.issueStatus==3}">取消出单</c:if>
								</td>
								<td width="20%">${po.mobile}</td>
								<td width="20%">
									<c:if test="${po.gateWay==1}">支付宝</c:if>
									<c:if test="${po.gateWay==2}">银联</c:if>
									<c:if test="${po.gateWay==3}">微信</c:if>
									<c:if test="${po.gateWay==0}">默认</c:if>
								</td>
								<td width="20%">微商城订单</td>
							</tr>							
						</tbody>
					</table>					
                <!-- END FORM-->            
            </div>
            <div class="portlet-title">
                <div class="caption">
                    <i class=" icon-layers font-green"></i>
                    <span class="caption-subject font-dark bold uppercase">投保人信息</span>
                </div>
            </div>            
            <div class="portlet-body">
                <!-- BEGIN FORM-->
					<table class="table table-striped table-bordered table-hover table-checkable" id="datatable_ajax">
						<thead>
							<tr role="row" class="heading">
								<th width="25%">投保人</th>
								<th width="25%">证件类型</th>
								<th width="25%">身份证号</th>
								<th width="25%">联系方式</th>
							</tr>
						</thead>
						<tbody>
							<tr role="row">
								<td width="25%">${po.cName}</td>
								<td width="25%">${po.cardName}</td>
								<td width="25%">${po.applicant.cardCode}</td>
								<td width="25%">${po.mobile}</td>
								
							</tr>						
						</tbody>
						<thead>
							<tr role="row" class="heading">
								<th width="25%">电子邮箱</th>
								<th width="25%">购买份数</th>
								<th width="25%">起保日期</th>
								<th width="25%">联系地址</th>
							</tr>
						</thead>
						<tbody>
							<tr role="row">
								<td width="25%">${po.applicant.email}</td>
								<td width="25%">${po.totalNum}</td>
								<td width="25%"><fmt:formatDate value='${po.startDate}' pattern='yyyy-MM-dd'/></td>
								<td width="25%">${po.applicant.contactAddress}</td>
							</tr>						
						</tbody>						
					</table>					
                <!-- END FORM-->            
            </div>
            <div class="portlet-title">
                <div class="caption">
                    <i class=" icon-layers font-green"></i>
                    <span class="caption-subject font-dark bold uppercase">被保人信息</span>
                </div>
            </div>            
            <div class="portlet-body">
                <!-- BEGIN FORM-->
					<table class="table table-striped table-bordered table-hover table-checkable" id="datatable_ajax">
						<thead>
							<tr role="row" class="heading">
								<th width="25%">被保人</th>
								<th width="25%">证件类型</th>
								<th width="25%">身份证号码</th>
								<th width="25%">我是被保人的</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${insurantList}" var="insurant">
								<tr role="row">
									<td width="25%">${insurant.cName}</td>
									<td width="25%">${insurant.cardName}</td>
									<td width="25%">${insurant.cardNumber}</td>
									<td width="25%">${insurant.relationName}</td>
								</tr>							
							</c:forEach>
						</tbody>
					</table>					
                <!-- END FORM-->            
            </div>
            <div class="portlet-title">
                <div class="caption">
                    <i class=" icon-layers font-green"></i>
                    <span class="caption-subject font-dark bold uppercase">保单信息</span>
                </div>
            </div>            
            <div class="portlet-body">
                <!-- BEGIN FORM-->
					<table class="table table-striped table-bordered table-hover table-checkable" id="datatable_ajax">
						<thead>
							<tr role="row" class="heading">
								<th width="10%">商品图片</th>
								<th>商品名称</th>
								<th width="15%">所属品牌</th>
								<th width="15%">商品分类</th>
								<th width="10%">数量</th>
								<th width="10%">投保金额</th>
								<th width="15%">保单价格</th>
							</tr>
						</thead>
						<tbody>
							<tr role="row">
								<td width="10%"><img alt="${po.product.prodName}" src="${po.product.imgUrlShow}" width="50px" height="50px"></td>
								<td>${po.product.prodName}</td>
								<td width="15%">${po.product.companyName}</td>
								<td width="15%">${po.product.fristCategory}</td>
								<td width="10%">${po.totalNum}份</td>
								<td width="10%">${po.payAmount}</td>
								<td width="15%">${po.payAmount}</td>
							</tr>							
						</tbody>
					</table>					
                <!-- END FORM-->            
            </div>
            <div class="portlet-title">
                <div class="caption">
                    <i class=" icon-layers font-green"></i>
                    <span class="caption-subject font-dark bold uppercase">费用信息</span>
                </div>
            </div>            
            <div class="portlet-body">
                <!-- BEGIN FORM-->
					<table class="table table-striped table-bordered table-hover table-checkable" id="datatable_ajax">
						<thead>
							<tr role="row" class="heading">
								<th width="50%">保单合计</th>
								<th width="50%">应付金额</th>
							</tr>
						</thead>
						<tbody>
							<tr role="row">
								<td width="50%">${po.payAmount}</td>
								<td>${po.payAmount}</th>
							</tr>							
						</tbody>
					</table>					
                <!-- END FORM-->            
            </div>
            <div class="portlet-title">
                <div class="caption">
                    <i class=" icon-layers font-green"></i>
                    <span class="caption-subject font-dark bold uppercase">操作信息</span>
                </div>
            </div>            
            <div class="portlet-body">
                <!-- BEGIN FORM-->
					<table class="table table-striped table-bordered table-hover table-checkable" id="datatable_ajax">
						<thead>
							<tr role="row" class="heading">
								<th width="10%">操作者</th>
								<th width="20%">操作时间</th>
								<th width="10%">订单状态</th>
								<th width="10%">付款状态</th>
								<th width="10%">保单状态</th>
								<th>备注</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${po.orderLogList}" var="orderLog">
								<tr role="row">
									<td width="10%">${orderLog.operatorId}</td>
									<td width="20%"><fmt:formatDate value='${orderLog.createdTime }' pattern='yyyy-MM-dd hh:mm:ss'/></td>
									<td width="10%">
										<c:if test="${orderLog.orderTStatus==-1}">已删除</c:if>
										<c:if test="${orderLog.orderTStatus==0}">默认状态</c:if>
										<c:if test="${orderLog.orderTStatus==10}">待付款</c:if>
										<c:if test="${orderLog.orderTStatus==15}">待发货</c:if>
										<c:if test="${orderLog.orderTStatus==20}">已出单</c:if>
										<c:if test="${orderLog.orderTStatus==30}">已完成</c:if>
										<c:if test="${orderLog.orderTStatus==40}">已关闭</c:if>
										<c:if test="${orderLog.orderTStatus==90}">已失效</c:if>
									</td>
									<td width="10%">
										<c:if test="${orderLog.payStatus==0}">未支付</c:if>
										<c:if test="${orderLog.payStatus==1}">已支付</c:if>
										<c:if test="${orderLog.payStatus==2}">不能支付</c:if>
										<c:if test="${orderLog.payStatus==3}">扣款中</c:if>
										<c:if test="${orderLog.payStatus==4}">扣款失败</c:if>
										<c:if test="${orderLog.payStatus==5}">扣款成功</c:if>
									</td>
									<td width="10%">
										<c:if test="${orderLog.issueStatus==0}">未出单</c:if>
										<c:if test="${orderLog.issueStatus==1}">已出单</c:if>
										<c:if test="${orderLog.issueStatus==2}">延时出单</c:if>
										<c:if test="${orderLog.issueStatus==3}">取消出单</c:if>
									</td>
									<td>${orderLog.remark}</td>
								</tr>
							</c:forEach>							
						</tbody>
					</table>					
                <!-- END FORM-->            
            </div>
        </div>
    </div>
</div>
</form>


<content tag="pageJs">
        <!-- BEGIN CORE PLUGINS -->
        <script src="${staticBase}/assets/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="${staticBase}/assets/global/plugins/js.cookie.min.js" type="text/javascript"></script>
        <script src="${staticBase}/assets/global/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js" type="text/javascript"></script>
        <script src="${staticBase}/assets/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
        <script src="${staticBase}/assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
        <script src="${staticBase}/assets/global/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
        <script src="${staticBase}/assets/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js" type="text/javascript"></script>
        <!-- END CORE PLUGINS -->
        <!-- BEGIN THEME GLOBAL SCRIPTS -->
        <script src="${staticBase}/assets/global/scripts/app.min.js" type="text/javascript"></script>
        <!-- END THEME GLOBAL SCRIPTS -->
        <!-- BEGIN THEME LAYOUT SCRIPTS -->
        <script src="${staticBase}/assets/layouts/layout/scripts/layout.min.js" type="text/javascript"></script>
        <script src="${staticBase}/assets/layouts/layout/scripts/demo.min.js" type="text/javascript"></script>
        <script src="${staticBase}/assets/layouts/global/scripts/quick-sidebar.min.js" type="text/javascript"></script>
        <!-- END THEME LAYOUT SCRIPTS -->
</content>
	 <!-- BEGIN PAGE LEVEL PLUGINS -->
    <!--BEGIN 引入上传JS -->
    <!--END 引入上传JS -->
     <script>
        jQuery(document).ready(function() {
        	
        });
</body>
</html>