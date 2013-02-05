<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div id="formsContent">
	<!-- Form container -->
	<form id="roleForm" class="form-horizontal" autocomplete="off" method="POST" action="">
	<legend><fmt:message key="domain.role.create.legend"/></legend>
		<div class="control-group">
			<label class="control-label" for="roleName"><fmt:message key="domain.role.column.roleName"/><em>*</em></label>
			<div class="controls">
				<input type="text" id="roleName" name="roleName" value="" maxlength="100" placeholder="Role Name">
				<span class="help-inline"></span>
			</div>
		</div>
		<div class="form-actions">
			<button id="saveButton" type="submit" class="btn btn-primary"><fmt:message key="domain.operation.create"/> <fmt:message key="domain.role"/></button>
			<button id="cancelButton" type="button" class="btn"><fmt:message key="button.operation.cancel"/></button>
		</div>		
	</form>
</div>