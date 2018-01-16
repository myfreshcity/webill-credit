<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layout/comtag.jsp" %>
<!-- BEGIN SIDEBAR -->
<div class="page-sidebar-wrapper">
    <!-- BEGIN SIDEBAR -->
    <!-- DOC: Set data-auto-scroll="false" to disable the sidebar from auto scrolling/focusing -->
    <!-- DOC: Change data-auto-speed="200" to adjust the sub menu slide up/down speed -->
    <div class="page-sidebar navbar-collapse collapse">
        <!-- BEGIN SIDEBAR MENU -->
        <!-- DOC: Apply "page-sidebar-menu-light" class right after "page-sidebar-menu" to enable light sidebar menu style(without borders) -->
        <!-- DOC: Apply "page-sidebar-menu-hover-submenu" class right after "page-sidebar-menu" to enable hoverable(hover vs accordion) sub menu mode -->
        <!-- DOC: Apply "page-sidebar-menu-closed" class right after "page-sidebar-menu" to collapse("page-sidebar-closed" class must be applied to the body element) the sidebar sub menu mode -->
        <!-- DOC: Set data-auto-scroll="false" to disable the sidebar from auto scrolling/focusing -->
        <!-- DOC: Set data-keep-expand="true" to keep the submenues expanded -->
        <!-- DOC: Set data-auto-speed="200" to adjust the sub menu slide up/down speed -->
        <ul class="page-sidebar-menu  page-header-fixed " data-keep-expanded="false" data-auto-scroll="true" data-slide-speed="200" style="padding-top: 20px">
            <!-- DOC: To remove the sidebar toggler from the sidebar you just need to completely remove the below "sidebar-toggler-wrapper" LI element -->
            <li class="sidebar-toggler-wrapper hide">
                <!-- BEGIN SIDEBAR TOGGLER BUTTON -->
                <div class="sidebar-toggler"> </div>
                <!-- END SIDEBAR TOGGLER BUTTON -->
            </li>
            
            <li class="nav-item start">
                <a href="javascript:;" class="nav-link nav-toggle">
                    <i class="icon-basket"></i>
                    <span class="title">系统管理</span>
                    <span class="arrow"></span>
                </a>
                <ul class="sub-menu">
		             <li class="nav-item  ">
	                       <a href='<c:url value="/user/"/>' class="nav-link ">
	                           <i class="icon-basket"></i>
	                           <span class="title">用户管理</span>
	                       </a>
                    </li>
                </ul>
            </li>
            
            <li class="nav-item">
            	<a href='<c:url value="/product/"/>' class="nav-link nav-toggle">
                    <i class="icon-user"></i>
                    <span class="title">商品管理</span>
                </a>
                <ul class="sub-menu">
		             <li class="nav-item  ">
	                       <a href='<c:url value="/product/"/>' class="nav-link nav-toggle">
	                           <i class="icon-basket"></i>
	                           <span class="title">商品列表</span>
	                       </a>
							<ul class="sub-menu">
					             <li class="nav-item  ">
				                       <a href='<c:url value="/product/"/>' class="nav-link ">
				                           <i class="icon-basket"></i>
				                           <span class="title">商品列表</span>
				                       </a>
			                    </li>
			                </ul>	                       
                     </li>
		             <li class="nav-item  ">
	                       <a href='<c:url value="/category/"/>' class="nav-link nav-toggle">
	                           <i class="icon-basket"></i>
	                           <span class="title">商品分类</span>
	                       </a>
							<ul class="sub-menu">
					             <li class="nav-item  ">
				                       <a href='<c:url value="/category/"/>' class="nav-link ">
				                           <i class="icon-basket"></i>
				                           <span class="title">商品分类</span>
				                       </a>
				                       <a href='<c:url value="/category/addCate"/>' class="nav-link ">
				                           <i class="icon-basket"></i>
				                           <span class="title">添加分类</span>
				                       </a>
			                    </li>
			                </ul>	                       
                     </li>
		             <li class="nav-item  ">
	                       <a href='<c:url value="/product/"/>' class="nav-link nav-toggle">
	                           <i class="icon-basket"></i>
	                           <span class="title">增加商品</span>
	                       </a>
							<ul class="sub-menu">
					             <li class="nav-item  ">
				                       <a href='<c:url value="/product/thirdPartList"/>' class="nav-link ">
				                           <i class="icon-basket"></i>
				                           <span class="title">商品渠道查询列表</span>
				                       </a>
			                    </li>
			                </ul>	                       
                     </li>
                </ul>                
            </li>
            <li class="nav-item">
            	<a href='<c:url value="/tOrder/"/>' class="nav-link nav-toggle">
                    <i class="icon-user"></i>
                    <span class="title">订单管理</span>
                </a>
                <ul class="sub-menu">
		             <li class="nav-item  ">
	                       <a href='<c:url value="/tOrder/"/>' class="nav-link ">
	                           <i class="icon-basket"></i>
	                           <span class="title">订单列表</span>
	                       </a>
                    </li>
		             <li class="nav-item  ">
	                       <a href='<c:url value="/tOrder/"/>' class="nav-link ">
	                           <i class="icon-basket"></i>
	                           <span class="title">订单详情——待付款</span>
	                       </a>
                    </li>
                    <li class="nav-item  ">
	                       <a href='<c:url value="/tOrder/"/>' class="nav-link ">
	                           <i class="icon-basket"></i>
	                           <span class="title">订单详情——已完成</span>
	                       </a>
                    </li>
		             <li class="nav-item  ">
	                       <a href='<c:url value="/tOrder/"/>' class="nav-link ">
	                           <i class="icon-basket"></i>
	                           <span class="title">订单详情——已关闭</span>
	                       </a>
                    </li>                    
                </ul>                
            </li>            
            <li class="nav-item">
            	<a href='<c:url value="/coupon/"/>' class="nav-link ">
                    <i class="icon-user"></i>
                    <span class="title">优惠券管理</span>
                </a>
            </li>
            <li class="nav-item">
            	<a href='<c:url value="/promoRule/"/>' class="nav-link ">
                    <i class="icon-user"></i>
                    <span class="title">规则管理</span>
                </a>
            </li>
        </ul>
        <!-- END SIDEBAR MENU -->
    </div>
    <!-- END SIDEBAR -->
</div>
<!-- END SIDEBAR -->