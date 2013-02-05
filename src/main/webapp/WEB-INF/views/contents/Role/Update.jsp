<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div id="formsContent">
	<!-- Form container -->
	<form id="roleEditForm" class="form-horizontal" autocomplete="off" method="POST" action="">
	<legend><fmt:message key="domain.role.edit.legend"/></legend>
		<div class="control-group">
			<label class="control-label" for="id"><fmt:message key="domain.role.column.id"/><em>*</em></label>
			<div class="controls">
				<input type="text" id="id" name="id" readOnly="true" value="${crudObj.id}" maxlength="100" data-reset="${crudObj.id}">
				<span class="help-inline"></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="roleName"><fmt:message key="domain.role.column.roleName"/><em>*</em></label>
			<div class="controls">
				<input type="text" id="roleName" name="roleName" value="${crudObj.roleName}" maxlength="100" placeholder="Role Name"  data-reset="${crudObj.roleName}">
				<span class="help-inline"></span>
			</div>
		</div>
		<div class="form-actions">
			<button id="saveButton" type="submit" class="btn btn-primary"><fmt:message key="button.operation.save"/> <fmt:message key="domain.role"/></button>
			<a href="${pageContext.request.contextPath}/domain/role/update/${crudObj.id}" id="resetButton" class="btn"><fmt:message key="button.operation.reset"/></a>
			<a href="${pageContext.request.contextPath}/domain/roles/list" id="cancelButton" class="btn"><fmt:message key="button.operation.cancel"/></a>
		</div>		
	</form>
</div>