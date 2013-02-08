<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<table class="table table-bordered"
	id="movietable">
	<thead>
		<tr>
			<th><fmt:message key="domain.movie.column.id"/></th>
			<th><fmt:message key="domain.movie.column.title"/></th>
			<th><fmt:message key="domain.movie.column.year"/></th>
			<th><fmt:message key="domain.movie.column.genre"/></th>
			<th><fmt:message key="domain.movie.column.director"/></th>
			<th><fmt:message key="domain.movie.column.cast"/></th>
		</tr>
	</thead>
	<tbody>
	</tbody>
</table>
<jsp:include page="../../sections/modal.jsp" />
