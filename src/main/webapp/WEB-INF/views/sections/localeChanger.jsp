<%@include file="/WEB-INF/views/includes/taglibs.jsp"%>
<ul id="languageChanger" class="nav">
	<li class="dropdown">
		<a class="dropdown-toggle" data-toggle="dropdown"
			href="#"> <spring:message code="localeChanger.${currentLanguage}" />
			<span class="caret"></span>
		</a>
		<ul class="dropdown-menu">
			<c:forEach var="lng" items="${supportedLanguages}" varStatus="status">
				<li> <a class="locale ${lng}" href="?lang=${lng}">
						<spring:message code="localeChanger.${lng}" />
					</a>
				</li>
			</c:forEach>
		</ul>	
	</li>
	
</ul>
