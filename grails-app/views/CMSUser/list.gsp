
<%@ page import="org.weceem.auth.CMSUser"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="admin" />
<g:set var="entityName"
	value="${message(code: 'CMSUser.label', default: 'CMSUser')}" />
<title><g:message code="employee.label" args="[entityName]" /></title>
	

</head>
<body>
	<div class="nav">
		<g:message	code="user.info.employee.list.title" />
	</div>
	<div class="body">
		<div id="checkedEmp"></div>
		<g:if test="${flash.message}">
			<div class="message">
				${flash.message}
			</div>
		</g:if>
		<g:form name="deleteForm">
			<sec:ifAllGranted roles="ROLE_ADMIN">
				<div class="nav">
					<g:actionSubmit action="create" 
						value="${message(code: 'default.button.add.label')}"
						class="ui-widget ui-state-default ui-corner-all" />
				</div>
			</sec:ifAllGranted>
			<div class="list">
				<g:hiddenField id="currentUID" name="currentUID"
					value="${currentUID}" />
				<table id="example">
					<thead>
						<tr>
							<g:sortableColumn property="code"
								title="${message(code: 'user.header.code', default: 'Code')}" />
							<g:sortableColumn property="username"
								title="${message(code: 'user.header.username', default: 'Username')}" />
							<th><g:message code="user.header.name" /></th>
							<th><g:message code="user.header.roles" /></th>
							<th><g:message code="user.header.last.access" /></th>
							<th><g:message code="user.header.reg.date" /></th>
							<g:sortableColumn property="status"
								title="${message(code: 'user.header.status', default: 'Status')}" />
							<th><g:message code="user.header.actions" /></th>
						</tr>
					</thead>
					<tbody>
						<g:each in="${CMSUserInstanceList}" status="i" var="user">

							<tr class="${(i % 2) == 0 ? 'odd' : 'even'}" style="${user.status == 0 ? 'color:red;' : 'color:black;'}">
								<td>
									${user.code?.encodeAsHTML()}
								</td>
								<td>
									${user.username?.encodeAsHTML()}
								</td>
								<td>
									${user.name?.encodeAsHTML()}
								</td>
								<td>
									${user.roleAuthorities.join(', ').encodeAsHTML()}
								</td>
								<td><g:formatDate formatName="date.time.format.full"
										date="${user.lastAccessTime}" /></td>
								<td><g:formatDate formatName="date.time.format.full"
										date="${user.regDate}" /></td>
								<td>
									<g:message code="${user.status == 1 ? 'common.status.1' : 'common.status.0' }" encodeAs="HTML" />
								</td>		
								<td><g:link action="show" id="${user.id}"
										class="button ui-corner-all">
										<g:message code="common.command.view" encodeAs="HTML" />
									</g:link> <sec:ifAllGranted roles="ROLE_ADMIN">
										<g:link action="edit" id="${user.id}"
											class="button ui-corner-all">
											<g:message code="default.button.edit.label" encodeAs="HTML" />
										</g:link>
										<g:if test="${currentUID != user.id}">
											<g:if test="${user.status}">
												<g:link action="delete" id="${user.id}" params="[status: 0]"
													class="button ui-corner-all"
													onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')} [${user.username.encodeAsJavaScript()}]?');">
													<g:message code="Ẩn" encodeAs="HTML" />
												</g:link>
											</g:if>
											<g:else>
												<g:link action="delete" id="${user.id}" params="[status: 1]"
													class="button ui-corner-all"
													onclick="return confirm('${message(code: 'default.button.revert.confirm.message', default: 'Are you sure?')} [${user.username.encodeAsJavaScript()}]?');">
													<g:message code="Hiện" encodeAs="HTML" />
												</g:link>
											</g:else>	
										</g:if>
									</sec:ifAllGranted></td>
							</tr>
						</g:each>
					</tbody>
				</table>
			</div>
			<div class="paginateButtons">
				<g:paginate total="${CMSUserInstanceTotal}" />
			</div>
		</g:form>
	</div>	
</body>
</html>
