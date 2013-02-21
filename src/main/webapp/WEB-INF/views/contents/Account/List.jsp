<%@include file="/WEB-INF/views/includes/taglibs.jsp"%>
<table class="table table-bordered"
	id="accountable">
	<thead>
		<tr>
			<th><fmt:message key="domain.account.column.id"/></th>
			<th><fmt:message key="domain.account.column.firstName"/></th>
			<th><fmt:message key="domain.account.column.lastName"/></th>
			<th><fmt:message key="domain.account.column.email"/></th>
			<th><fmt:message key="domain.account.column.isEnabled"/></th>
		</tr>
	</thead>
	<tbody>
	</tbody>
</table>
<jsp:include page="../../sections/modal.jsp" />
