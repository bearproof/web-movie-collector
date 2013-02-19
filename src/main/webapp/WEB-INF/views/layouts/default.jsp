<%@include file="/WEB-INF/views/includes/taglibs.jsp"%>

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
<link rel="shortcut icon" href="resources/img/favicon.png" type="image/png" />

<!-- Le styles -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/common/css/bootstrap-base.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/common/css/commons.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/common/css/bootstrap-responsive.css">

<tiles:insertAttribute name="custom-css" />

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
		<tiles:insertAttribute name="footer-content" />
	</footer>
	<tiles:insertAttribute name="modal-content" />
	<!-- Javascript-->
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/common/js/inheritance/js/inheritance-1.0.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/common/js/jquery/jquery-1.8.3.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/common/js/jquery/atmosphere/js/jquery.atmosphere-1.0.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/common/js/bootstrap.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/common/js/base.js"></script>
	<%-- <script type="text/javascript"	src="${pageContext.request.contextPath}/resources/jquery/jqueryUI-bootstrap/js/jquery-ui-1.9.2.custom.min.js"></script> --%>
	<tiles:insertAttribute name="custom-js" />

</body>
</html>