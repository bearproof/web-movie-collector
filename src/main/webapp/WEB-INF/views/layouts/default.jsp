<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Expires", "0");
%>
<c:set var="ctx" value="${pageContext['request'].contextPath}" />

<!DOCTYPE html>
<html class="no-js" lang="en">
<head>
<tiles:importAttribute />
<%-- <tiles:useAttribute id="key" name="title" scope="request"/> --%>
<%-- <title><spring:message htmlEscape="false" code="${key}"/></title> --%>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<title><tiles:insertAttribute name="title-content" /></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<meta name="description" content="Web movie collector application" />
<link rel="shortcut icon" href="resources/images/favicon.png"
	type="image/png" />
<!-- <link rel="stylesheet" href="/resources/wro/commons.css" media="all" /> -->
<!--  
	<c:if test="${not empty pageName}">
		<link rel="stylesheet" type="text/css" href="${wroCtx}/${pageName}.css" media="all" />
	</c:if>
	-->

<!-- Le styles -->
<link
	href="${pageContext.request.contextPath}/resources/css/bootstrap.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/resources/css/commons.css"
	rel="stylesheet">
<%-- <link
	href="${pageContext.request.contextPath}/resources/jquery/jqueryUI-bootstrap/css/custom-theme/jquery-ui-1.10.0.custom.css"
	rel="stylesheet"> --%>
<tiles:insertAttribute name="custom-css" />
<style type="text/css">
body {
	padding-top: 60px;
	padding-bottom: 40px;
}
</style>
<link
	href="${pageContext.request.contextPath}/resources/css/bootstrap-responsive.css"
	rel="stylesheet">

<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

<!-- Le favicon and touch icons -->
<link rel="shortcut icon"
	href="http://twitter.github.com/bootstrap/assets/ico/favicon.ico">
<link rel="apple-touch-icon-precomposed" sizes="144x144"
	href="http://twitter.github.com/bootstrap/assets/ico/apple-touch-icon-144-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="114x114"
	href="http://twitter.github.com/bootstrap/assets/ico/apple-touch-icon-114-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="72x72"
	href="http://twitter.github.com/bootstrap/assets/ico/apple-touch-icon-72-precomposed.png">
<link rel="apple-touch-icon-precomposed"
	href="http://twitter.github.com/bootstrap/assets/ico/apple-touch-icon-57-precomposed.png">
</head>
<body id="${pageName}" data-search-url='<c:url value='/search'/>'>
	<tiles:insertAttribute name="navbar-content" />
	<div class="container">
		<tiles:insertAttribute name="main-content" />
		<hr>
	</div>
	<footer>
		<%@include file="/WEB-INF/views/footer.jsp"%>
	</footer>

	<!-- Javascript-->
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/jquery/jquery-1.8.3.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/jquery/atmosphere/js/jquery.atmosphere-1.0.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/js/bootstrap.js"></script>
	<%-- <script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/jquery/jqueryUI-bootstrap/js/jquery-ui-1.9.2.custom.min.js"></script> --%>
	<tiles:insertAttribute name="custom-js" />

</body>
</html>