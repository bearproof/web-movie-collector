<%@include file="/WEB-INF/views/includes/taglibs.jsp"%>
<c:if test="${not empty param}">
	<c:choose>
		<c:when test="${param.badCredentials eq true}">
			<div class="alert alert-error">
				<fmt:message key="page.login.error.badCredentials" />
			</div>		
		</c:when>
		<c:when test="${param.accountDisabled eq true}">
			<div class="alert alert-error">
				<fmt:message key="page.login.error.accountDisabled" />
			</div>		
		</c:when>							
	</c:choose>
</c:if>

<!-- DIV shown only after a redirect from the Register page, to tell user to confirm account via e-mail -->
<div id="inactive" class="alert alert-success hidden">
	<fmt:message key="page.login.message.activateAccountByMail" />
</div>
			
<spring:url value="/resources/j_spring_security_check" var="form_url" />
<div class="content">
	<div class="row">
		<div class="span6 offset2">
			<div class="login-form span3">
				<h2>
					<fmt:message key="page.login.form.title" />
				</h2>
				<form name="loginForm" action="${fn:escapeXml(form_url)}"
					method="POST">
					<fieldset>
						<div class="clearfix">
							<input id="j_username" name="j_username" type="text"
								placeholder="<fmt:message key="domain.account.column.email"/>">
						</div>
						<div class="clearfix">
							<input id="j_password" name="j_password" type="password"
								placeholder="<fmt:message key="domain.account.column.password"/>">
						</div>
						<button class="btn btn-primary" type="submit">
							<fmt:message key="page.login.form.signIn" />
						</button>
					</fieldset>
				</form>								
			</div>
			
			<div id="socialSignInMessage" class="span3">
				<b><fmt:message key="page.login.form.socialSignIn" /></b>
			</div> 
			
			<div class="span3 offset1">						
					<div>
						<!-- Login with Facebook account -->
						<form id="fb_signin" class="social tiptip" action="<c:url value="/signin/facebook"/>"
							method="POST">
							<input type="hidden" name="scope"
								value="publish_stream,user_photos,offline_access,email" />
							<button type="submit" title="<fmt:message key="page.login.form.tooltip.facebook" />">
							</button>
						</form>
						<!-- Login with Google account -->
						<form id="go_signin" class="social tiptip" action="<c:url value="/signin/google"/>"
							method="POST">
							<input type="hidden" name="scope"
								value="https://www.googleapis.com/auth/calendar  https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo#email"/>
							<button type="submit" title="<fmt:message key="page.login.form.tooltip.google" />">			
							</button>
						</form>
					</div>								
			</div>					
		</div>
		
		<div class="span4">
			<!-- Sign Up section-->
			<div class="register-form">
				<b><fmt:message key="page.register.form.wantAccount" /></b>
				<h2>
					<fmt:message key="page.register.form.signUp" />
				</h2>
				<form id="account_register" action="<c:url value="/register"/>"
					method="GET">
					<button type="submit" class="btn btn-primary">
						<i class="icon-group"></i>
						<fmt:message key="page.register.form.signUp" />
					</button>
				</form>
			</div>
		</div>
		
		<div class="span4">
			<div>
				<legend>
					<fmt:message key="page.login.demo.accounts.title" />
					:
				</legend>
				<ul>
					<li><b><fmt:message key="page.login.demo.accounts.Email" />:</b>
						admin@mail.com <b><fmt:message
								key="page.login.demo.accounts.password" />:</b> admin</li>
					<li><b><fmt:message key="page.login.demo.accounts.Email" />:</b>
						user@mail.com <b><fmt:message
								key="page.login.demo.accounts.password" />:</b> user</li>
				</ul>
			</div>
		</div>
	</div>
</div>