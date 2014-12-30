<%@ page import="com.airgroup.domain.Feedback"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="admin" />
<script type="text/javascript" src="${resource(dir:'js',file:'link-management.js') }"></script>
<title><g:message code="title.homepage" args="[entityName]" /></title>
</head>
<body>
	<div class="nav">
		<g:link controller="link" action="listBackEnd" class="menuButton">Quản lý đường bay</g:link>
	</div>
	<div class="body">
		<div class="list">
			<g:form name="multiDeleteLinkForm" action="delete">
				<div class="nav">
					<a href="#" onclick="deleteAllLink()" class="ui-widget ui-state-default ui-corner-all" style="margin-right:8px;">
						<g:message code="Xóa" encodeAs="HTML"/>
					</a>
					<g:link action="create"	class="ui-widget ui-state-default ui-corner-all"  style="margin-right:8px;">
						<g:message code="Thêm mới" encodeAs="HTML"/>
					</g:link>
				</div>
				<table>
					<thead>
						<tr>
							<th><input id="chkHeadLink" type="checkbox"/></th>
														
							<g:sortableColumn property="title"
								title="${message(code: 'link.title.label', default: 'Tiêu đề')}" />
								
							<g:sortableColumn property="category"
								title="${message(code: 'link.category.label', default: 'Loại')}" />
								
							<g:sortableColumn property="fromCode"
								title="${message(code: 'link.fromCode.label', default: 'Điểm đi')}" />
								
							<g:sortableColumn property="toCode"
								title="${message(code: 'link.toCode.label', default: 'Điểm đến')}" />		
	
							<g:sortableColumn property="createdOn"
								title="${message(code: 'link.createdDate.label', default: 'Ngày đăng')}" />
								
							<th><g:message code="user.header.actions"/></th>
						</tr>
					</thead>
					<tbody>
						<g:each in="${linkBackEndList}" status="i"
							var="linkInstance">
							<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
							
								<td>
									<input class="linkId" type="checkbox" name="link" value="${linkInstance.id}"/>
								</td>
							
								<td>
									${linkInstance.title}
								</td>
	
								<td>
									<g:if test="${linkInstance.category == 'International'}">
     								Vé máy bay quốc tế
									</g:if>
									<g:if test="${linkInstance.category == 'Local'}">
     								Vé máy bay nội địa
									</g:if>
									<g:if test="${linkInstance.category == 'Company'}">
     								Vé máy bay theo hãng
									</g:if>
									<g:if test="${linkInstance.category == 'Type'}">
     								Vé máy bay theo loại
									</g:if>
								</td>
								
								<td>
									${linkInstance.fromCode}
								</td>
								
								<td>
									${linkInstance.toCode}
								</td>
	
								<td><g:formatDate formatName="date.time.format.full" date="${linkInstance.createdOn}" /></td>
								
								<td style="width: 200px;">
									<g:link id="${linkInstance.id}" controller="link" action="delete" class="button ui-corner-all"
										onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')} [${linkInstance.title.encodeAsJavaScript()}]?');">
										<g:message code="command.delete" encodeAs="HTML"/>
									</g:link>
									<g:link id="${linkInstance.id}" controller="link" action="edit" class="button ui-corner-all">
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
				controller="link" action="listBackEnd"
				total="${total}" />
		</div>
	</div>
</body>
</html>
