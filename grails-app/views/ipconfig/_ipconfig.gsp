<%@ page import="com.airgroup.domain.Ipconfig"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="admin" />
<g:set var="entityName"
	value="${message(code: 'ipconfig.label', default: 'Ipconfig')}" />
<title><g:message code="title.ipconfig" args="[entityName]" /></title>
</head>
<body>
	<div class="body tbody">
		<h1>
			<g:message code="title.ipconfig" args="[entityName]" />
		</h1>
		<g:if test="${flash.message}">
			<div class="message">
				${flash.message}
			</div>
		</g:if>
		<g:hasErrors bean="${ipconfigInstance}">
			<div class="errors">
				<g:renderErrors bean="${ipconfigInstance}" as="list" />
			</div>
		</g:hasErrors>
		<g:form method="post" action="save">
			<g:hiddenField name="id" value="${ipconfigInstance?.id}" />
			<g:hiddenField name="version" value="${ipconfigInstance?.version}" />
			<div class="dialog">
				<table style="width:300px; margin:auto">
					<tbody>

						<tr class="prop">
							<td valign="top" class="name ipconfig"><label for="ip"><g:message
										code="ipconfig.ip.label" default="Ip" /></label></td>
							<td valign="top"
								class="value ${hasErrors(bean: ipconfigInstance, field: 'ip', 'errors')}">
								<g:textField name="ip" value="${ipconfigInstance?.ip}" />
							</td>
						</tr>

						<tr class="prop">
							<td valign="top" class="name ipconfig"><label for="code"><g:message
										code="ipconfig.code.label" default="Code" /></label></td>
							<td valign="top"
								class="value ${hasErrors(bean: ipconfigInstance, field: 'code', 'errors')}">
								<g:textField name="code" maxlength="100"
									value="${ipconfigInstance?.code}" />
							</td>
						</tr>

						<tr class="prop">
							<td valign="top" class="name ipconfig"><label for="statusIp"><g:message
										code="ipconfig.status.label" default="Status" /></label></td>
							<td valign="top"
								class="value ${hasErrors(bean: ipconfigInstance, field: 'status', 'errors')}">
								<g:select name="status" from="${['0', '1']}"
									valueMessagePrefix="common.status"
									value="${ipconfigInstance?.status}" />
							</td>
						</tr>

					</tbody>
				</table>
			</div>
			<div class="buttons">
				<span class="button"><g:submitButton name="save" class="save"
						value="${message(code: 'button.save', default: 'Save')}" /></span>
			</div>
		</g:form>
	</div>
</body>
</html>
