
<%@ page import="com.airgroup.domain.Rate"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="admin" />
<g:set var="entityName"
	value="${message(code: 'rate.label', default: 'Rate')}" />
<title><g:message code="title.homepage" args="[entityName]" /></title>
</head>
<body>
	<div class="body tbody">
		<h1>
			<g:message code="rate.exchange.label" args="[entityName]" />
		</h1>
		<g:if test="${flash.message}">
			<div class="message">
				<g:message code="${flash.message}" encodeAs="HTML" />
			</div>
		</g:if>
		<g:hasErrors bean="${rateInstance}">
			<div class="errors">
				<g:renderErrors bean="${rateInstance}" as="list" />
			</div>
		</g:hasErrors>
		<g:form method="post" action="save">

			<div class="list">
				<table>
					<thead>
						<tr>
							<th><g:message code="rate.code.label" /></th>
							<th><g:message code="rate.exchange.label" /></th>
						</tr>
					</thead>
					<tbody>
						<g:each var="rateInstance" in="${rateInstanceList.sort{ it.id }}">
								<tr>
									<g:hiddenField name="currencies.${rateInstance?.id}.id"
										value="${rateInstance.id}" />
									<g:hiddenField name="currencies.${rateInstance?.id}.code"
										value="${rateInstance.code}" />
									<td><b>${rateInstance?.code} (${rateInstance?.description})</b></td>
	
									<td><g:textField name="currencies.${rateInstance.id}.rate"
											value="${formatNumber(number: rateInstance.rate, formatName: 'exchange.format')}" maxlength="10" /></td>
								</tr>
						</g:each>
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
