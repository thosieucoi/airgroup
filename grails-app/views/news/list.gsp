
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="admin" />
        <g:set var="entityName" value="${message(code: 'News', default: 'News')}" />
        <title><g:message code="title.homepage" args="[entityName]" /></title>
    </head>
    <body>
        <div class="body">
            <h1><g:message code="navigation.admin.news" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
          	<g:link action="create"	class="ui-widget ui-state-default ui-corner-all"  style="margin-right:8px;">
				<g:message code="Thêm mới" encodeAs="HTML"/>
			</g:link>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                            <g:sortableColumn property="id" title="${message(code: 'customer.id.label', default: 'Id')}" />
                            <th>${message(code: 'news.label.title', default: 'Title')}</th>
                            <th>${message(code: 'news.label.type', default: 'Type')}</th>
                            <th>${message(code: 'news.label.createOn', default: 'CreateOn')}</th>
                            <th>${message(code: 'news.label.modifyDate', default: 'ModifyDate')}</th>
                            <th>${message(code: 'news.label.status', default: 'Status')}</th>
                            <th>${message(code: 'news.label.manage', default: 'Manage')}</th>
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${newsList}" status="i" var="news">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        	<td>${news.id}</td>
                        	<td>${news.title}</td>
                        	<td>${news.category}</td>
                        	<td>${news.createdOn}</td>
                        	<td>${news.changedOn}</td>
                        	<td>${news.active}</td>
                        	<td>
                      			<g:link action="edit" id="${news.id}"
									class="button ui-corner-all">
									<g:message code="default.button.edit.label" encodeAs="HTML" />
								</g:link>
								<g:link id="${news.id}" controller="news" action="delete" class="button ui-corner-all"
									onclick="return confirm('${message(code: 'news.confirm.delete', default: 'Are you sure?')}');">
									<g:message code="command.delete" encodeAs="HTML"/>
								</g:link>
                        	</td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
				<g:paginate	
					next="Forward" prev="Back"
					max="5" maxsteps="10"
					controller="news" action="list"
					total="${total}" />
			</div>
        </div>
    </body>
</html>
