<%@include file="/WEB-INF/views/includes/taglibs.jsp"%>
<!DOCTYPE html>
<html class="no-js" lang="en">
<head>
<tiles:importAttribute />
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<title><tiles:insertAttribute name="title-content" /></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<meta name="description" content="Web movie collector application" />
<link rel="shortcut icon" href="resources/img/favicon.png" type="image/png" />

<!-- Le styles -->
<link rel="stylesheet" href="${wroCtx}/commons.css" media="all" />
<c:if test="${not empty pageName}">
	<link rel="stylesheet" type="text/css" href="${wroCtx}/${pageName}.css" media="all" />
</c:if>
	

<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

<!-- Le favicon and touch icons -->
<link rel="shortcut icon" href="http://twitter.github.com/bootstrap/assets/ico/favicon.ico">
<link rel="apple-touch-icon-precomposed" sizes="144x144" href="http://twitter.github.com/bootstrap/assets/ico/apple-touch-icon-144-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="114x114" href="http://twitter.github.com/bootstrap/assets/ico/apple-touch-icon-114-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="72x72" href="http://twitter.github.com/bootstrap/assets/ico/apple-touch-icon-72-precomposed.png">
<link rel="apple-touch-icon-precomposed" href="http://twitter.github.com/bootstrap/assets/ico/apple-touch-icon-57-precomposed.png">
</head>
<body id="${pageName}" 
	data-search-url='<c:url value='/search'/>' 
	data-required='<spring:message code="required"/>' 
	data-email='<spring:message code="email"/>'
	data-remote='<spring:message code="remote"/>' 
	data-equalto='<spring:message code="equalto"/>' 
	data-minlength='<spring:message code="minlength"/>'
	data-maxlength='<spring:message code="maxlength"/>' 
	data-firstname='<spring:message code="firstname"/>' 
	data-lastname='<spring:message code="lastname"/>'
	data-password='<spring:message code="password"/>' 
	data-passwordconfirm='<spring:message code="passwordconfirm"/>'
	data-noneselected='<spring:message code="domain.account.column.roles.noneselected"/>' 
	data-selected='<spring:message code="domain.account.column.roles.selected"/>'
	data-movietitle='<spring:message code="domain.movie.column.title.validation"/>' 
	data-movieyear='<spring:message code="domain.movie.column.year.validation"/>'
	data-movieyear='<spring:message code="domain.movie.column.year.validation"/>' 
	data-enterrolename='<spring:message code="domain.role.column.enterrolename"/>'
	data-accountcreate='<spring:message code="domain.account.create.legend"/>' 
	data-accountedit='<spring:message code="domain.account.edit.legend"/>'
	data-accountdelete='<spring:message code="domain.account.delete.legend"/>' 
	data-moviecreate='<spring:message code="domain.movie.create.legend"/>'
	data-movieedit='<spring:message code="domain.movie.edit.legend"/>' 
	data-moviedelete='<spring:message code="domain.movie.delete.legend"/>'
	data-rolecreate='<spring:message code="domain.role.create.legend"/>' 
	data-roleedit='<spring:message code="domain.role.edit.legend"/>'
	data-roledelete='<spring:message code="domain.role.delete.legend"/>'
	data-choosetemplate='<spring:message code="navbar.menu.choosetemplate"/>'
	data-noinfosourceselected='<spring:message code="searchPage.no.infosource.selected"/>'
	data-movierequired='<spring:message code="searchPage.movie.required"/>'	
	<c:choose>
	<c:when test="${fn:contains(userContext.getRoles(), 'ROLE_ADMIN')}">
	data-role='admin' 
	</c:when>
	<c:otherwise>
	data-role='user'
	</c:otherwise>
	</c:choose>	
	<c:if test="${not empty defaultTheme}">
		 class="${defaultTheme}"
	</c:if>
	>

	<tiles:insertAttribute name="navbar-content" />
	<div class="container">
		<tiles:insertAttribute name="main-content" />
	</div>
	<footer>
		<tiles:insertAttribute name="footer-content" />
	</footer>
	<tiles:insertAttribute name="modal-content" />
	<!-- Javascript-->	
	<script type="text/javascript" src="${wroCtx}/commons.js"></script>	  		
	<c:if test="${not empty pageName}">
		<script type="text/javascript" src="${wroCtx}/${pageName}.js"></script>
	</c:if>		
	<c:if test="${not empty noCss}">
		<script type="text/javascript" src="${wroCtx}/${noCss}.js"></script>
	</c:if>		
</body>
</html>
