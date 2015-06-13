<%@ page import="com.airgroup.domain.Feedback"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="admin" />
<script type="text/javascript" src="${resource(dir:'js',file:'tour-management.js') }"></script>
<title><g:message code="title.homepage" args="[entityName]" /></title>
</head>
<body>
	<div class="nav">
		<g:link controller="information" action="listBackEnd" class="menuButton">Quản lý Tour</g:link>
	</div>
	<div class="body">
		<div class="list">
			<g:form name="multiDeleteForm" action="delete">
				<div class="nav">
					<a href="#" onclick="deleteAll()" class="ui-widget ui-state-default ui-corner-all" style="margin-right:8px;">
						<g:message code="Xóa" encodeAs="HTML"/>
					</a>
					<g:link action="create"	class="ui-widget ui-state-default ui-corner-all"  style="margin-right:8px;">
						<g:message code="Thêm mới" encodeAs="HTML"/>
					</g:link>
				</div>
				<table>
					<thead>
						<tr>
							<th><input id="chkHead" type="checkbox"/></th>
							
							<g:sortableColumn property="id"
								title="${message(code: 'tour.id.label', default: 'Mã tour')}" />
							
							<g:sortableColumn property="title"
								title="${message(code: 'tour.name.label', default: 'Tên tour')}" />
								
							<g:sortableColumn property="category"
								title="${message(code: 'tour.name.label', default: 'Mục')}" />
	
							<g:sortableColumn property="createdOn"
								title="${message(code: 'tour.createdDate.label', default: 'Ngày đăng')}" />
								
							<th><g:message code="user.header.actions"/></th>
						</tr>
					</thead>
					<tbody>
						<g:each in="${tourBackEndList}" status="i"
							var="tourInstance">
							<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
							
								<td>
									<input class="tourId" type="checkbox" name="tour" value="${tourInstance.id}"/>
								</td>
							
								<td>
									${tourInstance.title}
								</td>
	
								<td>
									${tourInstance.title}
								</td>
								
								<td>
									${tourInstance.category}
								</td>
	
								<td><g:formatDate formatName="date.time.format.full" date="${tourInstance.createdOn}" /></td>
								
								<td style="width: 200px;">
									<g:link action="show" id="${tourInstance.id}" class="button ui-corner-all">
										<g:message code="common.command.view" encodeAs="HTML" />
									</g:link>
									<g:link id="${tourInstance.id}" controller="information" action="delete" class="button ui-corner-all"
										onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')} [${tourInstance.title.encodeAsJavaScript()}]?');">
										<g:message code="command.delete" encodeAs="HTML"/>
									</g:link>
									<g:link id="${tourInstance.id}" controller="information" action="edit" class="button ui-corner-all">
										<g:message code="command.edit" encodeAs="HTML"/>
									</g:link>
								</td>
	
							</tr>
						</g:each>
					</tbody>
				</table>
			</g:form>
		</div>
		<div class="paginateButtons">
			<g:paginate	
				next="Forward" prev="Back"
				max="20" maxsteps="10"
				controller="tour" action="listBackEnd"
				total="${total}" />
		</div>
	</div>
</body>
</html>
