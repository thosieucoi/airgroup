<%@ page import="com.airgroup.domain.Feedback"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="admin" />
<title><g:message code="title.homepage" args="[entityName]" /></title>
</head>
<body>
	<div class="nav">
		<span class="menuButton"><g:link
				class="list" action="list">
				<g:message code="feedback.list.label" args="[entityName]" />
			</g:link></span> &gt;  <g:message code="feedback.show.label" />
	</div>
	<div class="body">
		<g:if test="${flash.message}">
			<div class="message">
				${flash.message}
			</div>
		</g:if>
		<div class="dialog">
			<table style="margin:10px 0">
				<tbody>

					<tr class="prop">
						<td valign="top" class="name"><g:message
								code="feedback.id.label" default="Id" /></td>

						<td valign="top" class="value">
							${fieldValue(bean: feedbackInstance, field: "id")}
						</td>

					</tr>

					<tr class="prop">
						<td valign="top" class="name"><g:message
								code="feedback.name.label" default="Name" /></td>

						<td valign="top" class="value">
							${fieldValue(bean: feedbackInstance, field: "name")}
						</td>

					</tr>

					<tr class="prop">
						<td valign="top" class="name"><g:message
								code="feedback.phoneNumber.label" default="Phone Number" /></td>

						<td valign="top" class="value">
							${fieldValue(bean: feedbackInstance, field: "phoneNumber")}
						</td>

					</tr>

					<tr class="prop">
						<td valign="top" class="name"><g:message
								code="feedback.content.label" default="Content" /></td>

						<td valign="top" class="value">
							${fieldValue(bean: feedbackInstance, field: "content")}
						</td>

					</tr>

					<tr class="prop">
						<td valign="top" class="name"><g:message
								code="feedback.address.label" default="Address" /></td>

						<td valign="top" class="value">
							${fieldValue(bean: feedbackInstance, field: "address")}
						</td>

					</tr>

					<tr class="prop">
						<td valign="top" class="name"><g:message
								code="feedback.sendDate.label" default="Send Date" /></td>

						<td valign="top" class="value"><g:formatDate formatName="date.time.format.full"
										date="${feedbackInstance.sendDate}" /></td>

					</tr>

					<tr class="prop">
						<td valign="top" class="name"><g:message
								code="feedback.status.label" default="Status" /></td>

						<td valign="top" class="value"><g:message
								code="${feedbackInstance?.status == 1 ? 'common.status.1' : 'common.status.0'}"
								default="Status" /></td>

					</tr>

				</tbody>
			</table>
		</div>
		<div class="buttons">
			<g:form>
				<g:hiddenField name="id" value="${feedbackInstance?.id}" />
				<span class="button"><g:actionSubmit class="edit"
						action="edit"
						value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
				<span class="button"><g:actionSubmit class="delete"
						action="delete"
						value="${message(code: 'default.button.delete.label', default: 'Delete')}"
						onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
			</g:form>
		</div>
	</div>
</body>
</html>
