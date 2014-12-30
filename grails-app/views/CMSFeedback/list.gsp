<%@ page import="com.airgroup.domain.Feedback"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="admin" />
<title><g:message code="title.homepage" args="[entityName]" /></title>
</head>
<body>
	<div class="nav">
		<g:message	code="feedback.list.label" /> <span class="menuButton"><g:link
				class="create" action="create">
				<g:message code="title.feedback" args="[entityName]" />
			</g:link></span>
	</div>
	<div class="body">
		<g:if test="${flash.message}">
			<div class="message">
				${flash.message}
			</div>
		</g:if>
		<div class="list">
			<table>
				<thead>
					<tr>
						<g:sortableColumn property="id"
							title="${message(code: 'feedback.id.label', default: 'Id')}" />

						<g:sortableColumn property="name"
							title="${message(code: 'feedback.name.label', default: 'Name')}" />

						<g:sortableColumn property="phoneNumber"
							title="${message(code: 'feedback.phoneNumber.label', default: 'Phone Number')}" />

						<g:sortableColumn property="content"
							title="${message(code: 'feedback.content.label', default: 'Content')}" />

						<g:sortableColumn property="address"
							title="${message(code: 'feedback.address.label', default: 'Address')}" />

						<g:sortableColumn property="sendDate"
							title="${message(code: 'feedback.sendDate.label', default: 'Send Date')}" />

						<g:sortableColumn property="status"
							title="${message(code: 'feedback.sendDate.status', default: 'Status')}" />
					</tr>
				</thead>
				<tbody>
					<g:each in="${feedbackInstanceList}" status="i"
						var="feedbackInstance">
						<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

							<td style="width:20px"><g:link action="show" id="${feedbackInstance.id}">
									${fieldValue(bean: feedbackInstance, field: "id")}
								</g:link></td>

							<td style="width:100px">
								${fieldValue(bean: feedbackInstance, field: "name")}
							</td>

							<td class="color" style="width:100px">
								${fieldValue(bean: feedbackInstance, field: "phoneNumber")}
							</td>

							<td style="width:350px">
								${fieldValue(bean: feedbackInstance, field: "content")}
							</td>

							<td class="color" style="width:120px">
								${fieldValue(bean: feedbackInstance, field: "address")}
							</td>

							<td style="width:150px">
								<g:formatDate formatName="date.time.format.full"
										date="${feedbackInstance.sendDate}" />
							</td>

							<td class="color under" style="width:50px">
								<g:message
								code="${feedbackInstance?.status == 1 ? 'common.status.1' : 'common.status.0'}"
								default="Status" />
							</td>

						</tr>
					</g:each>
				</tbody>
			</table>
		</div>

		<div class="paginateButtons">
			<g:paginate total="${feedbackInstanceTotal}" />
		</div>
	</div>
</body>
</html>
