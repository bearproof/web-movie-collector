<%-- <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> --%>
<%@include file="/WEB-INF/views/includes/taglibs.jsp"%>
<div id="errorModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">X</button>
    <h3 id="errorModalLabel"></h3>
  </div>
  <div id="errorModalBody" class="modal-body">
    <p id="errorModalMsg" ></p>
  </div>
  <div class="modal-footer">
    <button class="btn" data-dismiss="modal" aria-hidden="true"><fmt:message key="domain.table.modal.close"/></button>
  </div>
</div>

<div id="confirmModal" class="modal hide fade">
    <div class="modal-header">
      <button type="button" class="close" data-dismiss="modal" aria-hidden="true">X</button>
      <h3 id="confirmModalLabel"><fmt:message key="domain.table.modal.confirm"/></h3>
    </div>
    <div id="confirmModalBody" class="modal-body">
      <p id="confirmModalMsg"><fmt:message key="domain.table.modal.areyousure"/></p>
    </div>
    <div class="modal-footer">
      <button class="btn btn-danger" data-dismiss="modal" aria-hidden="true"><fmt:message key="domain.table.modal.delete"/></button>
      <button class="btn" data-dismiss="modal" aria-hidden="true"><fmt:message key="domain.table.modal.cancel"/></button>
    </div>
</div>

<%-- <div id="updateAccountModal" class="modal hide fade">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">X</button>
     	<h3 id="updateAccountModalLabel"></h3>
	</div>
	<div id="updateAccountModalBody" class="modal-body">
		<div id="formsContent">
			<!-- Form container -->
			<form id="accountEditForm" class="form-horizontal" autocomplete="off" method="POST" action="">
			<legend><fmt:message key="domain.account.edit.legend"/></legend>
				<div class="control-group">
					<label class="control-label" for="id"><fmt:message key="domain.account.column.id"/><em>*</em></label>
					<div class="controls">
						<input type="text" id="id" name="id" readOnly="true" value="${crudObj.id}" maxlength="100" data-reset="${crudObj.id}">
						<span class="help-inline"></span>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="firstName"><fmt:message key="domain.account.column.firstName"/><em>*</em></label>
					<div class="controls">
						<input type="text" id="firstName" name="firstName" value="${crudObj.firstName}" maxlength="100" placeholder="First Name"  data-reset="${crudObj.firstName}">
						<span class="help-inline"></span>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="lastName"><fmt:message key="domain.account.column.lastName"/><em>*</em></label>
					<div class="controls">
						<input type="text" id="lastName" name="lastName" value="${crudObj.lastName}" maxlength="100" placeholder="Last Name" data-reset="${crudObj.lastName}">
						<span class="help-inline"></span>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="password"><fmt:message key="domain.account.column.password"/><em>*</em></label>
					<div class="controls">
						<input type="password" id="password" name="password" maxlength="50" value="${crudObj.password}"  placeholder="Password" data-reset="${crudObj.password}">
						<span class="help-inline"></span>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="password_confirm"><fmt:message key="domain.account.column.passwordConfirmation"/><em>*</em></label>
					<div class="controls">
						<input type="password" id="password_confirm" name="password_confirm" maxlength="50" value="${crudObj.password}"  placeholder="Confirm Password" data-reset="">
						<span class="help-inline"></span>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="email"><fmt:message key="domain.account.column.email"/><em>*</em></label>
					<div class="controls">
						<input type="text" id="email" readOnly="true" name="email" value="${crudObj.email}" maxlength="100" data-reset="${crudObj.email}">
						<span class="help-inline"></span>
					</div>
				</div>
				<c:if test="${fn:contains(userContext.getRoles(), 'ROLE_ADMIN')}">
					<div class="control-group">
						<label class="control-label" for="isEnabled">Is enabled?</label>
						<div class="controls">
							<input type="checkbox" id="isEnabled" name="isEnabled" <c:if test="${crudObj.isEnabled=='true'}">checked</c:if>>
							<span class="help-inline"></span>
						</div>
					</div>		
					<div class="control-group">
						<label class="control-label" for="roles">Roles<em>*</em></label>
						<div class="controls">
							 <select id="rolesList" class="multiselect" multiple="multiple">
							 	<c:forEach var="role" items="${crudObj.roles}">
							 		<option value="${role.id}"  selected="selected">${role.roleName}</option>
								</c:forEach>
							 </select>	                    
			            </div>          
					</div> 			    
				</c:if>
				<div class="form-actions">
					<button id="saveButton" type="submit" class="btn btn-primary"><fmt:message key="button.operation.save"/> <fmt:message key="domain.account"/></button>
					<a href="${pageContext.request.contextPath}/domain/account/update/${crudObj.id}" id="resetButton" class="btn"><fmt:message key="button.operation.reset"/></a>
					<a href="${pageContext.request.contextPath}/" id="cancelButton" class="btn"><fmt:message key="button.operation.cancel"/></a>			
					<button class="btn" data-dismiss="modal" aria-hidden="true"><fmt:message key="domain.table.modal.cancel"/></button>
				</div>		
			</form>
		</div>
	</div><!-- end of DIV#updateAccountModalBody -->
	<div class="modal-footer"></div>	
</div> --%>
