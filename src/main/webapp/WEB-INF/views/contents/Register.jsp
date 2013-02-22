<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div id="formsContent">
	<!-- Form container -->
	<form id="registerForm" class="form-horizontal" autocomplete="off" method="POST" action="">
	<legend><fmt:message key="domain.account.create.legend"/></legend>
		<div class="control-group">
			<label class="control-label" for="firstName"><fmt:message key="domain.account.column.firstName"/><em>*</em></label>
			<div class="controls">
				<input type="text" id="firstName" name="firstName" value="" maxlength="100" placeholder="<fmt:message key="domain.account.column.firstName"/>">
				<span class="help-inline"></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="lastName"><fmt:message key="domain.account.column.lastName"/><em>*</em></label>
			<div class="controls">
				<input type="text" id="lastName" name="lastName" value="" maxlength="100" placeholder="<fmt:message key="domain.account.column.lastName"/>">
				<span class="help-inline"></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="password"><fmt:message key="domain.account.column.password"/><em>*</em></label>
			<div class="controls">
				<input type="password" id="password" name="password" maxlength="50" value=""  placeholder="<fmt:message key="domain.account.column.password"/>">
				<span class="help-inline"></span>
			</div>
			<div class="password-meter hide">
				<div class="meter-wrapper">
					<div class="meter-content state-0"></div>
				</div>
			</div>			
		</div>
		<div class="control-group">
			<label class="control-label" for="password_confirm"><fmt:message key="domain.account.column.passwordConfirmation"/><em>*</em></label>
			<div class="controls">
				<input type="password" id="password_confirm" name="password_confirm" maxlength="50" value=""  placeholder="<fmt:message key="domain.account.column.passwordConfirmation"/>">
				<span class="help-inline"></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="email"><fmt:message key="domain.account.column.email"/><em>*</em></label>
			<div class="controls">
				<input type="text" id="email" name="email" placeholder="<fmt:message key="domain.account.column.email"/>" value="" maxlength="100">
				<span class="help-inline"></span>
			</div>
		</div>
		
		<div class="form-actions">
			<button id="saveButton" type="submit" class="btn btn-primary"><fmt:message key="domain.operation.create"/> <fmt:message key="domain.account"/></button>
			<button id="cancelButton" type="button" class="btn"><fmt:message key="button.operation.cancel"/></button>
		</div>		
	</form>
</div>