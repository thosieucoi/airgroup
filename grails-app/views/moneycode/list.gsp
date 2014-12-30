
<%@ page import="com.airgroup.domain.MoneyCode" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="admin" />
        <g:set var="entityName" value="${message(code: 'advert.label', default: 'Advert')}" />
        <title><g:message code="title.moneyCode" args="[entityName]" /></title>
        <g:javascript library="prototype"/>
        <script type="text/javascript" src="${resource(dir:'js', file:'moneyCode.js') }"></script>
<%--        <link rel="stylesheet" type="text/css" href="${resource(dir:'css/css_pirobox/style_1',file:'style.css')}" />--%>
<%--        <script src="${resource(dir:'js',file:'jquery.min.js')}"></script>--%>
<%--        <script src="${resource(dir:'js',file:'jquery-ui-1.8.2.custom.min.js')}"></script>--%>
<%--        <script src="${resource(dir:'js',file:'pirobox_extended.js')}"></script>--%>
        
        
        <script type="text/javascript">
        (function($) {	
			$(document).ready(function() {
			    $().piroBox_ext({
			        piro_speed : 900,
			        bg_alpha : 0.1,
			        piro_scroll : true //pirobox always positioned at the center of the page
			    });
			});
        })(jQuery);
		</script>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <sec:ifAnyGranted roles="ROLE_ADMIN, ROLE_ACCOUNTING">
            <span class="menuButton"><g:link class="create" action="create"><g:message code="moneyCode.add.label" args="[entityName]" /></g:link></span>
       		</sec:ifAnyGranted>
        </div>
        <div class="body">
            <h1><g:message code="moneyCode.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            	<div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${moneyCodeInstance}">
				<div class="errors">
					<g:renderErrors bean="${moneyCodeInstance}" as="list" />
				</div>
			</g:hasErrors>
			<div class="nav">
				<span>
					Chọn ngày <input id="fromDate" readonly="readonly" type="text" style="margin:0 10px 0 0;"/>
					<g:link controller="moneycode" action="list" class="hideOrder" >Xem tất cả</g:link>	
					
				</span>
			</div>
            <div class="list" id="list">
                <table id="tblMoneyCode">
                    <thead>
                        <tr>
                            <g:sortableColumn property="fileName" title="${message(code: 'moneyCode.fileName.lable', default: 'File Name')}" />
                        
                            <g:sortableColumn property="createDate" title="${message(code: 'moneyCode.createDate.label', default: 'Create Date')}" />
                        <sec:ifAnyGranted roles="ROLE_ADMIN, ROLE_ACCOUNTING">
                            <th><g:message code="user.header.actions"/></th>
                        
                            <th><g:message code="user.header.actions"/></th>
                        </sec:ifAnyGranted>
                        </tr>
                    </thead>
                    <tbody>
	                    <g:each in="${moneyCodeInstanceList}" status="i" var="moneyCodeInstance">
	                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
	                            <td>
	                               <g:link controller="moneycode" action="downloadFile" params="[fileName: moneyCodeInstance?.fileName]">${fieldValue(bean: moneyCodeInstance, field: "fileName")}</g:link><br>
	                            </td>
	                        	<td><g:formatDate formatName="date.time.format.short"
											date="${moneyCodeInstance.createDate}" />
								</td>
								<sec:ifAnyGranted roles="ROLE_ADMIN, ROLE_ACCOUNTING">
								<td>
	                            	<g:link controller="moneycode" action="edit" class="hideOrder" params="[id: moneyCodeInstance?.id]">Sửa</g:link>	
	                            </td>
	      						<td>
	                            	<g:link id="xoaLink" onclick="myFunction(${moneyCodeInstance?.id})" class="hideOrder"
											value="[advertId: advertInstance?.id]">Xóa</g:link>	
	                            </td>
	                              </sec:ifAnyGranted>
	                        </tr>
	                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${moneyCodeInstanceTotal}" />
            </div>
        </div>
        <script>
			function myFunction(id1)
			{
			var aId = id1;
			var r=confirm("Bạn có chắc chắn muốn xóa không?");
			if (r==true)
			  {
				${remoteFunction(controller:"moneycode", action:"delete", params:"'id=' + aId")}
			  }
			else
			  {
			  }
			}
		</script>
    </body>
</html>
