
<%@ page import="com.airgroup.domain.Advert" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="admin" />
        <g:set var="entityName" value="${message(code: 'advert.label', default: 'Advert')}" />
        <title><g:message code="title.advert" args="[entityName]" /></title>
        <g:javascript library="prototype"/>
        <link rel="stylesheet" type="text/css" href="${resource(dir:'css/css_pirobox/style_1',file:'style.css')}" />
        <script src="${resource(dir:'js',file:'jquery.min.js')}"></script>
        <script src="${resource(dir:'js',file:'jquery-ui-1.8.2.custom.min.js')}"></script>
        <script src="${resource(dir:'js',file:'pirobox_extended.js')}"></script>
        
        
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
            <span class="menuButton"><g:link class="create" action="create"><g:message code="advert.add.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="advert.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            	<div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${advertInstance}">
				<div class="errors">
					<g:renderErrors bean="${advertInstance}" as="list" />
				</div>
			</g:hasErrors>
		
            <div class="list" id="list">
                <table>
                    <thead>
                        <tr>
                        
<%--                            <g:sortableColumn property="id" title="${message(code: 'order.code.label', default: 'Order code')}" />--%>
                        
                            <g:sortableColumn property="slidePicFileName" title="${message(code: 'advert.slideImage.lable', default: 'Slide Image')}" />
                        
                            <g:sortableColumn property="linkAdvert" title="${message(code: 'advert.link.label', default: 'Link')}" />
                        
                            <g:sortableColumn property="status" title="${message(code: 'common.status', default: 'Status')}" />
                            
                             <th>Action</th>
                        
                            <th>Xóa</th>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${advertInstanceList}" status="i" var="advertInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
<%--                            <td><g:link action="edit" id="${advertInstance.id}" >${fieldValue(bean: advertInstance, field: "id")}</g:link></td>--%>
                        
                        
                            <!-- <td><a href="javascript:void()" onclick="javascript:popup()">${fieldValue(bean: advertInstance, field: "slidePicFileName")}</a></td>-->
                        
                        
                        	<td><a href="${createLink(controller:'advert', action:'showSlideImage', id:advertInstance.id)}" rel="single"  class="pirobox" title="${fieldValue(bean: advertInstance, field: "slidePicFileName")}">
    							${fieldValue(bean: advertInstance, field: "slidePicFileName")}
								</a>						
							</td>
							
							<td><a href="${advertInstance.linkAdvert}" title="${fieldValue(bean: advertInstance, field: "linkAdvert")}" target="_blank">
							${fieldValue(bean: advertInstance, field: "linkAdvert")}
								</a>
							</td>
							
							<td>
                            	<g:if test="${advertInstance.status == 1}">
	                            	Deactive
                            	</g:if>
                            	<g:elseif test="${advertInstance.status == 0}">
                           			Active
                            	</g:elseif>
                            </td>
							
                            <td>
                            	<g:if test="${advertInstance.status == 1}">
	                            	<g:link action="activeDeactiveAdvert" class="bt_active"
											params="[advertId: advertInstance?.id, status: '0']">Active</g:link>
                            	</g:if>
                            	<g:elseif test="${advertInstance.status == 0}">
                           			<g:link action="activeDeactiveAdvert" class="bt_active"
									params="[advertId: advertInstance?.id, status: '1']">Deactive</g:link>
                            	</g:elseif>
                            </td>
      
      						<td>
                            	<g:link id="xoaLink" onclick="myFunction(${advertInstance?.id})" class="hideOrder"
										value="[advertId: advertInstance?.id]">Xóa</g:link>	
                            </td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${advertInstanceTotal}" />
            </div>
        </div>
        <script>
			function myFunction(id1)
			{
			var aId = id1;
			var r=confirm("Bạn có chắc chắn muốn xóa không?");
			if (r==true)
			  {
				${remoteFunction(controller:"advert", action:"delete", params:"'id=' + aId")}
			  }
			else
			  {
			  }
			}
		</script>
    </body>
</html>
