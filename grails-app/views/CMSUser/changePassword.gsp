<%@ page import="org.weceem.auth.CMSUser"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="admin" />
<g:set var="entityName"
	value="${message(code: 'CMSUser.label', default: 'CMSUser')}" />
<title><g:message code="user.header.changepass" args="[entityName]" /></title>
</head>
<body>
	<div class="nav">
		<span class="menuButton"><a class="home"
			href="${createLink(uri: '/')}"><g:message
					code="default.home.label" /></a></span> 
	</div>
	<div class="body">
		<h1>
			<g:message code="user.header.changepass" args="[entityName]" />
		</h1>
		<g:if test="${flash.message}">
			<div class="message">
				<span style="color: red">${flash.message}</span>
			</div>
		</g:if>
		<g:hasErrors bean="${CMSUserInstance}">
			<div class="errors">
				<g:renderErrors bean="${CMSUserInstance}" as="list" />
			</div>
		</g:hasErrors>
		
		<g:form method="post" action="editpass">
			<div class="dialog">
				<fieldset>
					<legend>
						<g:message code="user.header.changepass" />
					</legend>
					<table>
						<tbody>
							<tr class="prop">
								<g:hiddenField name="checkValidate" id = "checkValidate" />
								<td valign="top" class="name"><label for="username"><g:message
											code="user.header.username" default="Username" /></label></td>
								<td valign="top"
									class="value ${hasErrors(bean: CMSUserInstance, field: 'username', 'errors')}">
									<g:textField name="username" maxlength="45"
										value="${user?.username}" />
								</td>
							</tr>

							<tr class="prop">
								<td valign="top" class="name"><label for="password"><g:message
											code="user.header.current.password"
											default="Current password" /></label></td>
								<td valign="top"
									class="value ${hasErrors(bean: CMSUserInstance, field: 'password', 'errors')}">
									<g:passwordField name="oldPassword" maxlength="45" />
									<label id = "oldPasswordValidate"></label>
								</td>
							</tr>

							<tr class="prop">
								<td valign="top" class="name"><label for="newPassword"><g:message
											code="user.header.newpassword" default="Password" /></label></td>
								<td valign="top"
									class="value ${hasErrors(bean: CMSUserInstance, field: 'newPassword', 'errors')}">
									<g:passwordField name="newPassword" maxlength="45" />
								</td>
							</tr>

							<tr class="prop">
								<td valign="top" class="name"><label for="verifiedPassword"><g:message
											code="user.header.password.verify" default="Verify password" /></label>
								</td>
								<td valign="top"
									class="value ${hasErrors(bean: CMSUserInstance, field: 'verifiedPassword', 'errors')}">
									<g:passwordField name="verifiedPassword" maxlength="45" />
								</td>
							</tr>
						</tbody>
					</table>
				</fieldset>
			</div>
				<div class="buttons">
					<span class="button"><g:actionSubmit class="save" id="btnSubmit"  action="editpass"
					value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
							<!-- onClick = "validateInput()" -->
							
				</div>
		</g:form>
	</div>
	<script>
	function validateInput(){
	        var blnIsValid = true;
	        var oldPass = $("#oldPassword").val();
	        var newPass = $("#newPassword").val();
	        var confirmPass = $("#verifiedPassword").val();
            if(oldPass == "") {
                alert("Mật khẩu không được trống");
                blnIsValid = false;
            }else if(newPass == "") {
                alert("Mật khẩu mới không được trống");
                blnIsValid = false;
            }else if(confirmPass == "") {
                alert("Mật khẩu xác nhận không được trống");
                blnIsValid = false;
            }else if(newPass != confirmPass) {
                alert("Mật khẩu không khớp");
                blnIsValid = false;
            }
            else{
            	document.getElementById('editpass').action;
            }
            return blnIsValid;
	};
	</script>
</body>
</html>

