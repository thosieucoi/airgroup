<%@ page import="com.airgroup.domain.Fee"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="admin" />
<g:set var="entityName"
	value="${message(code: 'fee.label', default: 'Fee')}" />
<title><g:message code="title.homepage" args="[entityName]" /></title>
</head>
<body>
	<div class="body tbody">
		<h1>
			<g:message code="Phí Dịch Vụ" args="[entityName]" />
		</h1>
		<g:hasErrors bean="${feeInstance}">
			<div class="errors">
				<g:renderErrors bean="${feeInstance}" as="list" />
			</div>
		</g:hasErrors>
	    <g:if test="${flash.message}">
           <div class="message">${flash.message}</div>
           </g:if>
		<g:form method="post" action="save">
			<g:hiddenField name="version" value="${feeInstance?.version}" />
			<div class="dialog">
				<table>
					<tbody>
						<g:each in="${feeInstance}" status="i"
						var="instance">
							<tr class="prop">
								<td valign="top" align="right" class="name fee">
									<label for="domestic">
										<g:message code="${instance.description}" default="${instance.description}" />
									</label>
								</td>
								<td valign="top"
									class="value ${hasErrors(bean: feeInstance, field: 'price', 'errors')}">
									<g:field name="${instance.code}" valign="top" min="0" type="number" maxlength="100" value="${instance.price}" />
								</td>
							</tr>
						</g:each>
					</tbody>
				</table>
			</div>
			<div class="buttons">
				<span class="button"><g:submitButton name="save" class="save"
						value="${message(code: 'button.save', default: 'Cập Nhật')}" /></span>
			</div>
		</g:form>
	</div>
</body>
</html>
