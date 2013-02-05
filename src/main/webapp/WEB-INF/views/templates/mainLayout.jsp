<%-- <%@include file="/WEB-INF/views/includes/taglibs.jsp"%> --%>
<!DOCTYPE html>
<html class="no-js" lang="en">
<head>
	<tiles:importAttribute/>
	<tiles:useAttribute id="key" name="title" scope="request"/>
	<title><spring:message htmlEscape="false" code="${key}"/></title>
	<meta charset="utf-8" /> 	
	<!--[if IE]><meta http-equiv="X-UA-Compatible" content="IE=edge"><![endif]-->
	<meta name="description" content="Web movie collector application" />
	<link rel="shortcut icon" href="resources/images/favicon.png" type="image/png" />
	<link rel="stylesheet" href="${wroCtx}/commons.css" media="all" />
	<!--  
	<c:if test="${not empty pageName}">
		<link rel="stylesheet" type="text/css" href="${wroCtx}/${pageName}.css" media="all" />
	</c:if>
	-->
	<link rel="stylesheet" href="resources/searchPage/css/searchPage.css" media="all" />
	<link rel="stylesheet" href="resources/common/css/easyui.css" />
	<link rel="stylesheet" href="resources/common/css/icon.css" />
	
</head>

<body id="${pageName}" data-search-url='<c:url value='/search'/>'>
	<tiles:insertAttribute name="body"/> 
	<!--JavaScripts-->
	
	<script type="text/javascript" src="${wroCtx}/commons.js"></script>	  
	<script type="text/javascript" src="resources/searchPage/js/searchPage.js"></script>	     
	
	<%-- 
	<c:if test="${not empty pageName}">
		<script type="text/javascript" src="${wroCtx}/${pageName}.js"></script>
	</c:if>	
	--%>
</body>
</html>