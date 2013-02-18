<%@include file="/WEB-INF/views/includes/taglibs.jsp"%>
<div class="navbar navbar-inverse navbar-fixed-top">
	<div class="navbar-inner">
		<div class="container-fluid">
			<a class="btn btn-navbar" data-toggle="collapse"
				data-target=".nav-collapse"> <span class="icon-bar"></span> <span
				class="icon-bar"></span> <span class="icon-bar"></span>
			</a> <a class="brand" href="${pageContext.request.contextPath}/"><fmt:message key="header.webapp.name"/>&nbsp;<span class="label label-info"><fmt:message key="header.webapp.version"/></span></a>
			<security:authorize access="isAuthenticated()">
				<div class="btn-group pull-right">
					<a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
						<i class="icon-user"></i> ${userContext.username} <span
						class="caret"></span>
					</a>
					<ul class="dropdown-menu">
						<li><a href="${pageContext.request.contextPath}/domain/accounts/update/${userContext.userId}"><fmt:message key="domain.account.edit.legend"/></a></li>
						<li><a href="${pageContext.request.contextPath}/resources/j_spring_security_logout"><fmt:message key="navbar.menu.logout"/></a></li>
					</ul>
				</div>
			</security:authorize>
			<security:authorize access="isAuthenticated()">
				<div class="nav-collapse">
					<ul class="nav">
						<li id="homePageLink"><a
							href="${pageContext.request.contextPath}/"><fmt:message key="navbar.menu.home"/></a></li>
						<c:if test="${fn:contains(userContext.getRoles(), 'ROLE_ADMIN')}">
							<li id="domainPageLink" class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown"><fmt:message key="navbar.menu.domain"/><b class="caret"></b></a>
								<ul class="dropdown-menu">
									<li><a href="${pageContext.request.contextPath}/domain/accounts/list"><fmt:message key="navbar.menu.domain.account"/></a></li>
									<li><a href="${pageContext.request.contextPath}/domain/roles/list"><fmt:message key="navbar.menu.domain.role"/></a></li>								
								</ul>
							</li>
						</c:if>
						<li id="wmcPageLink" class="dropdown movieListPage"><a href="#" class="dropdown-toggle" data-toggle="dropdown"><fmt:message key="navbar.menu.wmc"/><b class="caret"></b></a>
							<ul class="dropdown-menu">
								<li><a href="${pageContext.request.contextPath}/movieLand"><fmt:message key="navbar.menu.wmc.searchMovies"/></a></li>								
								<li><a href="${pageContext.request.contextPath}/domain/movies/list"><fmt:message key="navbar.menu.wmc.myMovies"/></a></li>								
							</ul>
						</li>
					</ul>	
				</div>
			</security:authorize>
		</div>
	</div>
</div>