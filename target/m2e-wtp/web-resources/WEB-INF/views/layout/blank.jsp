<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sitemesh"
           uri="http://www.opensymphony.com/sitemesh/decorator" %>

<!DOCTYPE html>
<html>
<head>
    <title><sitemesh:title/></title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@ include file="/WEB-INF/views/layout/page-head.jsp" %>
    <%@ include file="/WEB-INF/views/layout/page-foot.jsp" %>
    <sitemesh:head/>
</head>
<body>
<div id="wrapper">
    <!-- Sidebar begin-->
    <sitemesh:usePage id="thisPage"/>
    <sitemesh:body/>
</div>
</body>
</html>