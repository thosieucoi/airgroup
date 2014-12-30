<%@ page import="com.airgroup.domain.Feedback"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="admin" />
<title><g:message code="title.feedback" args="[entityName]" /></title>
</head>
<body>
	<div class="nav">
		<span class="menuButton"><g:link
				class="list" action="list">
				<g:message code="feedback.list.label" args="[entityName]" />
			</g:link></span> &gt;  <g:message code="feedback.edit.label" />
	</div>
	<div class="body">
		<g:if test="${flash.message}">
			<div class="message">
				${flash.message}
			</div>
		</g:if>
		<g:hasErrors bean="${feedbackInstance}">
			<div class="errors"  style="color:red;">
				<g:renderErrors bean="${feedbackInstance}" as="list" />
			</div>
		</g:hasErrors>
		<g:form method="post">
			<g:hiddenField name="id" value="${feedbackInstance?.id}" />
			<g:hiddenField name="version" value="${feedbackInstance?.version}" />
			<div class="dialog">
				<fieldset style="margin:10px">
				<legend>Cập nhật phản hồi</legend>
				<table>
					<tbody>

						<tr class="prop">
							<td valign="top" class="name"><label for="name"><g:message
										code="feedback.name.label" default="Name" /></label></td>
							<td valign="top"
								class="value ${hasErrors(bean: feedbackInstance, field: 'name', 'errors')}">
								<g:textField name="name" maxlength="100"
									value="${feedbackInstance?.name}" />
							</td>
						</tr>

						<tr class="prop">
							<td valign="top" class="name"><label for="phoneNumber"><g:message
										code="feedback.phoneNumber.label" default="Phone Number" /></label></td>
							<td valign="top"
								class="value ${hasErrors(bean: feedbackInstance, field: 'phoneNumber', 'errors')}">
								<g:textField name="phoneNumber"
									value="${feedbackInstance?.phoneNumber}" />
							</td>
						</tr>

						<tr class="prop">
							<td valign="top" class="name"><label for="content"><g:message
										code="feedback.content.label" default="Content" /></label></td>
							<td valign="top"
								class="value ${hasErrors(bean: feedbackInstance, field: 'content', 'errors')}">
								<g:textArea name="content" cols="40" rows="5"
									value="${feedbackInstance?.content}" />
							</td>
						</tr>

						<tr class="prop">
							<td valign="top" class="name"><label for="address"><g:message
										code="feedback.address.label" default="Address" /></label></td>
							<td valign="top"
								class="value ${hasErrors(bean: feedbackInstance, field: 'address', 'errors')}">
								<g:textField name="address" maxlength="100"
									value="${feedbackInstance?.address}" />
							</td>
						</tr>

						<tr class="prop">
							<td valign="top" class="name"><label for="sendDate"><g:message
										code="feedback.sendDate.label" default="Send Date" /></label></td>
							<td valign="top"
								class="value ${hasErrors(bean: feedbackInstance, field: 'sendDate', 'errors')}">
								<g:datePicker name="sendDate"
									value="${feedbackInstance?.sendDate}" />
							</td>
						</tr>

						<tr class="prop">
							<td valign="top" class="name"><label for="feedbackStatus"><g:message
										code="common.status" default="Status" /></label></td>
							<td valign="top"
								class="value ${hasErrors(bean: feedbackInstance, field: 'status', 'errors')}">
								<g:select name="status" from="${['0', '1']}"
									valueMessagePrefix="common.status"
									value="${feedbackInstance?.status}" />
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
				<span class="button"><g:actionSubmit class="delete"
						action="delete"
						value="${message(code: 'default.button.delete.label', default: 'Delete')}"
						onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
			</div>
		</g:form>
	</div>
</body>
</html>
