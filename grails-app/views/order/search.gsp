<%@ page import="com.airgroup.domain.Order" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="admin" />
        <g:set var="entityName" value="${message(code: 'order.label', default: 'Order')}" />
        <title><g:message code="title.homepage" args="[entityName]" /></title>
        <g:javascript library="prototype"/>
    </head>
    <body>
        <div class="nav">
            <g:message	code="order.info.list.title" />
        </div>
        <div class="body">
            <g:if test="${flash.message}">
            	<div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${orderInstance}">
				<div class="errors">
					<g:renderErrors bean="${orderInstance}" as="list" />
				</div>
			</g:hasErrors>
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'order.code.label', default: 'Order code')}" />
                        
                            <g:sortableColumn property="customer" title="${message(code: 'order.customer.label', default: 'Customer name')}" />
                        
                            <g:sortableColumn property="price" title="${message(code: 'order.price.label', default: 'Price')}" />
                        
                            <g:sortableColumn property="orderTime" title="${message(code: 'order.date.purchase.label', default: 'Date purchase')}" />
                        
                            <g:sortableColumn property="status" title="${message(code: 'common.status', default: 'Status')}" />
                        
                            <th><g:message code="user.header.actions"/></th>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${orderInstanceList}" status="i" var="orderInstance">
                        <g:if test="${orderInstance?.showStatus}">
	                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
	                        	
	                            <td><g:link action="edit" id="${orderInstance.id}" params="[user: currentUser.id]">${fieldValue(bean: orderInstance, field: "id")}</g:link></td>
	                        
	                            <td>${orderInstance?.customer?.name}</td>
	                        
	                            <td>${fieldValue(bean: orderInstance, field: "price")}</td>
	                        
	                            <td><g:formatDate formatName="date.time.format.full"
										date="${orderInstance.orderTime}" /></td>
	                        
	                            <td>
	                            	<g:if test="${orderInstance.status == 1}">
	                            		<g:message code="order.status.1"/>
	                            	</g:if>
	                            	<g:elseif test="${orderInstance.status == 2}">
	                            		<g:message code="order.status.2"/>
	                            	</g:elseif>
	                            	<g:else>
	                            		<g:message code="order.status.3"/>
	                            	</g:else>
	                            </td>
	                        
	                            <td>
		                            <g:if test="${orderInstance?.status == 2}">
										<g:message code="order.completed.label" />
									</g:if>
									<g:else>
		                            	<g:if test="${orderInstance?.orderEmployee?.status == 1 && orderInstance?.orderEmployee?.processEmp?.id == currentUser.id}">
		                            		<g:link action="processCancel" params="[user: currentUser.id, orderEmpId:orderInstance?.orderEmployee?.id, type:'list']"><g:message code="order.processing.cancel.label"/></g:link>
		                            	</g:if>
		                            	<g:elseif test="${orderInstance?.orderEmployee?.status == 1 && orderInstance?.orderEmployee?.processEmp?.id}">
		                            		<g:message code="order.processing.label"/>
		                            	</g:elseif>
		                            	<g:else>
			                            	<g:if test="${orderInstance?.orderEmployee?.status == null || orderInstance?.orderEmployee?.status == 0}">
			                            		<g:link action="book" params="[user: currentUser.id, orderEmpId:orderInstance?.orderEmployee?.id, orderId: orderInstance.id, type:'list']"><g:message code="order.booking.label"/></g:link> 
			                            	</g:if>
			                            	<g:elseif test="${orderInstance?.orderEmployee?.status == 1 && orderInstance?.orderEmployee?.bookEmp?.id == currentUser.id}">
			                            		<g:link action="bookCancel" params="[user: currentUser?.id, orderEmpId:orderInstance?.orderEmployee?.id, orderId: orderInstance.id, type:'list']"><g:message code="order.booking.cancel.label"/></g:link> 
			                            	</g:elseif>
			                            	<g:else>
			                            		<g:message code="order.booked.label"/>
			                            	</g:else>
			                            	|
			                            	<g:if test="${orderInstance?.orderEmployee?.status == 1 && orderInstance?.orderEmployee?.processEmp?.id == null}">
			                            		<g:link action="process" id="${orderInstance.id}" params="[user: currentUser.id, orderEmpId:orderInstance?.orderEmployee?.id, type:'list']"><g:message code="order.process.label"/></g:link>
			                            	</g:if>
			                            	<g:else>
			                            		<g:message code="order.process.label"/>
			                            	</g:else>
		                            	</g:else>
	                            	</g:else>
	                            </td>
	                        </tr>
                        </g:if>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate params="[info:info]" total="${orderInstanceTotal}" />
            </div>
    </body>
</html>
