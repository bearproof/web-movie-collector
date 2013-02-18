<%@include file="/WEB-INF/views/includes/taglibs.jsp"%>
<script src="${pageContext.request.contextPath}/resources/common/js/jquery.validate.js"></script>
<script src="${pageContext.request.contextPath}/resources/common/js/bootstrap-multiselect.js"></script>
<c:choose>
<c:when test="${fn:contains(userContext.getRoles(), 'ROLE_ADMIN')}">
	<script src="${pageContext.request.contextPath}/resources/custom/js/Account/accountUpdate.js"></script>
</c:when>
<c:otherwise>
	<script src="${pageContext.request.contextPath}/resources/custom/js/Account/userAccountUpdate.js"></script>
</c:otherwise>
</c:choose>
