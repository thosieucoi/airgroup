
<%@ page import="org.weceem.auth.CMSUser"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="admin" />
<g:set var="entityName"
	value="${message(code: 'CMSUser.label', default: 'CMSUser')}" />
<title><g:message code="navigation.admin.employeeCall" args="[entityName]" /></title>
	
</head>
<body>
	<div class="nav">
		<g:message	code="navigation.admin.employeeCall" />
	</div>
	<div class="body">
		<g:form name="listEmpCallForm" action="listEmpCall">
			<div class="list">
				<table>
					<thead>
						<tr>
							<th><g:message code="user.header.name" /></th>
							<th><g:message code="user.header.phone" /></th>
							<th><g:message code="user.header.skype" /></th>
							<th><g:message code="user.header.yahoo" /></th>
						</tr>
					</thead>
					<tbody>
						<g:each in="${EmpCallInstanceList}" status="i" var="user">
							<g:if test="${user.skype} || ${user.yahoo}">
									<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
										<td>
											${user.name}
										</td>
										<td>
											${user.phoneNumber}
										</td>
										
										<td>
											${user.skype}
										</td>
										<td>
											${user.yahoo}
										</td>
									</tr>
							</g:if>
						</g:each>
					</tbody>
				</table>
			</div>
		</g:form>
	</div>
</body>
</html>
