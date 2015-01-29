<%@ page import="org.weceem.auth.CMSUser"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="admin" />
<g:set var="entityName"
	value="${message(code: 'CMSUser.label', default: 'CMSUser')}" />
<title><g:message code="default.edit.label" args="[entityName]" /></title>
<script>
jQuery( document ).ready(function() {
	jQuery("#roleIds").removeAttr("multiple");
	var $oldPassword = jQuery("input#newPassword");
	//alert($oldPassword.val());
	jQuery("#verifiedPassword").val($oldPassword.val());
});
</script>
</head>
<body>
	<div class="nav">
		<g:link action="list">
			<g:message code="user.info.employee.title" />
		</g:link>
		 &gt; <g:message code="user.info.employee.update.title" />
	</div>
	<div class="body">
		<g:if test="${flash.message}">
			<div class="message">
				${flash.message}
			</div>
		</g:if>
		<g:hasErrors bean="${CMSUserInstance}">
			<div class="errors">
				<g:renderErrors bean="${CMSUserInstance}" as="list" />
			</div>
		</g:hasErrors>
		
		<g:form method="post" action="update">
			<div class="dialog">
				<fieldset>
					<legend>
						<g:message code="user.info.employee.title" />
					</legend>
					<table>
						<tbody>
							<g:hiddenField name="id" value="${CMSUserInstance?.id}" />
							<tr class="prop">
								<td valign="top" class="name"><label for="code"><g:message
											code="user.header.code" default="Code" /></label></td>
								<td valign="top"
									class="value ${hasErrors(bean: CMSUserInstance, field: 'code', 'errors')}">
									<g:textField name="code" maxlength="25"
										value="${CMSUserInstance?.code}" />
								</td>
							</tr>

							<tr class="prop">
								<td valign="top" class="name"><label for="name"><g:message
											code="user.header.name" default="Name" /></label></td>
								<td valign="top"
									class="value ${hasErrors(bean: CMSUserInstance, field: 'name', 'errors')}">
									<g:textField name="name" maxlength="100"
										value="${CMSUserInstance?.name}" />
								</td>
							</tr>

							<tr class="prop">
								<td valign="top" class="name"><label for="phoneNumber"><g:message
											code="user.header.phone" default="Phone Number" /></label></td>
								<td valign="top"
									class="value ${hasErrors(bean: CMSUserInstance, field: 'phoneNumber', 'errors')}">
									<g:textField name="phoneNumber" maxlength="45"
										value="${CMSUserInstance?.phoneNumber}" />
								</td>
							</tr>

							<tr class="prop">
								<td valign="top" class="name"><label for="yahoo"><g:message
											code="user.header.yahoo" default="Yahoo" /></label></td>
								<td valign="top"
									class="value ${hasErrors(bean: CMSUserInstance, field: 'yahoo', 'errors')}">
									<g:textField name="yahoo" maxlength="45"
										value="${CMSUserInstance?.yahoo}" />
								</td>
							</tr>

							<tr class="prop">
								<td valign="top" class="name"><label for="skype"><g:message
											code="user.header.skype" default="Skype" /></label></td>
								<td valign="top"
									class="value ${hasErrors(bean: CMSUserInstance, field: 'skype', 'errors')}">
									<g:textField name="skype" maxlength="45"
										value="${CMSUserInstance?.skype}" />
								</td>
							</tr>
							
							<tr class="prop">
								<td valign="top" class="name"><label for="email"><g:message
											code="user.header.email" default="Email" /></label></td>
								<td valign="top"
									class="value ${hasErrors(bean: CMSUserInstance, field: 'email', 'errors')}">
									<g:textField name="email" maxlength="45"
										value="${CMSUserInstance?.email}" />
								</td>
							</tr>

							<tr class="prop">
								<td valign="top" class="name"><label for="callCenter"><g:message
											code="user.header.call.center" default="Call center employee" /></label></td>
								<td valign="top"
									class="value ${hasErrors(bean: CMSUserInstance, field: 'callCenterStatus', 'errors')}">
									<g:checkBox name="callCenterStatus"
										checked="${CMSUserInstance?.callCenterStatus > 0}" />
								</td>
							</tr>
						</tbody>
					</table>
				</fieldset>
				<fieldset>
					<legend>
						<g:message code="user.info.login.title" />
					</legend>
					<table>
						<tbody>
							<tr class="prop">
								<td valign="top" class="name"><label for="username"><g:message
											code="user.header.username" default="Username" /></label></td>
								<td valign="top"
									class="value ${hasErrors(bean: CMSUserInstance, field: 'username', 'errors')}">
									<g:textField name="username" maxlength="45"
										value="${CMSUserInstance?.username}" />
								</td>
							</tr>

							<tr class="prop">
								<td valign="top" class="name"><label for="password"><g:message
											code="user.header.password" default="Password" /></label></td>
								<td valign="top"
									class="value ${hasErrors(bean: CMSUserInstance, field: 'password', 'errors')}">
									<g:passwordField class="password" name="newPassword" maxlength="30"/>
								</td>
							</tr>

							<tr class="prop">
								<td valign="top" class="name"><label for="verifiedPassword"><g:message
											code="user.header.password.verify" default="Verify password" /></label>
								</td>
								<td id="verifyPass" valign="top"
									class="value ${hasErrors(bean: CMSUserInstance, field: 'confirmPassword', 'errors')}">
									<g:passwordField name="verifiedPassword" maxlength="30"/>
								</td>
							</tr>

							<tr class="prop">
								<td valign="top" class="name"><label for="authorities"><g:message
											code="user.header.roles" default="Roles" /></label></td>
								<td valign="top"
									class="value ${hasErrors(bean: CMSUserInstance, field: 'role', 'errors')}">
									<g:select name="roleIds" 
								          from="${org.weceem.auth.CMSRole.list()}"
								          value="${CMSUserInstance?.authorities?.id}"
								          optionKey="id" optionValue="authority"/>	
								</td>
							</tr>

						</tbody>
					</table>
				</fieldset>
			</div>
				<div class="buttons">
					<span class="button"><g:actionSubmit class="save"
							action="update"
							value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
				</div>
		</g:form>
	</div>	
</body>
</html>
