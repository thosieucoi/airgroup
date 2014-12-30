<%@ page import="com.airgroup.domain.Order" %>
<html>
    <body>
    	<div style=" background: #007CC3;padding: 5px 0; text-align: center; width: 150px; margin-bottom:10px; ">
					<g:link id="domestic" action="list" params="[status: status, day: day, myorder:myorder, active:active, fltype:'0']" style="color:#fff; margin-right:10px">Nội địa</g:link>
					<g:link id="international" action="list" params="[status: status, day: day, myorder:myorder, active:active, fltype:'1']"  style="color:#fff;">Quốc tế</g:link></div>
        <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" action="list" params="[status: status, day: day, myorder:myorder, active:active, fltype:fltype]" title="${message(code: 'order.code.label', default: 'Order code')}" />
               
                            <g:sortableColumn property="customer" action="list" params="[status: status, day: day, myorder:myorder, active:active, fltype:fltype]" title="${message(code: 'order.customer.label', default: 'Customer name')}" />
                        
                            <g:sortableColumn property="price" action="list" params="[status: status, day: day, myorder:myorder, active:active, fltype:fltype]" title="${message(code: 'order.price.label', default: 'Price')}" />
                        
                            <g:sortableColumn property="orderTime" action="list" params="[status: status, day: day, myorder:myorder, active:active, fltype:fltype]" title="${message(code: 'order.date.purchase.label', default: 'Date purchase')}" />
                        
                            <g:sortableColumn property="status" action="list" params="[status: status, day: day, myorder:myorder, active:active, fltype:fltype]" title="${message(code: 'common.status', default: 'Status')}" />
                        
                            <th style="width:220px;color:#000099"><g:message code="user.header.actions"/></th>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${orderInstanceList}" status="i" var="orderInstance">
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
	                            		<span style="background-color: #2E64FE; color: white; font-weight: bold;"><g:message code="order.status.2"/></span>
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
		                            		<g:link class="button-pro" action="processCancel" params="[user: currentUser.id, orderEmpId:orderInstance?.orderEmployee?.id, type:'list', status: status, day: day, myorder:myorder, active:active, fltype:fltype]"><g:message code="order.processing.cancel.label"/></g:link>
		                            	</g:if>
		                            	<g:elseif test="${orderInstance?.orderEmployee?.status == 1 && orderInstance?.orderEmployee?.processEmp?.id}">
		                            		<p class="button-pro"><g:message code="order.processing.label"/></p>
		                            	</g:elseif>
		                            	<g:else>
			                            	<g:if test="${orderInstance?.orderEmployee?.status == null || orderInstance?.orderEmployee?.status == 0}">
			                            		<g:link class="button-pro" action="book" params="[user: currentUser.id, orderEmpId:orderInstance?.orderEmployee?.id, orderId: orderInstance.id, type:'list', status: status, day: day, myorder:myorder, active:active, fltype:fltype]"><g:message code="order.booking.label"/></g:link> 
			                            	</g:if>
			                            	<g:elseif test="${orderInstance?.orderEmployee?.status == 1 && orderInstance?.orderEmployee?.bookEmp?.id == currentUser.id}">
			                            		<g:link class="button-pro" action="bookCancel" params="[user: currentUser?.id, orderEmpId:orderInstance?.orderEmployee?.id, orderId: orderInstance.id, type:'list', status: status, day: day, myorder:myorder, active:active, fltype:fltype]"><g:message code="order.booking.cancel.label"/></g:link> 
			                            	</g:elseif>
			                            	<g:else>
			                            		<p class="button-pro"><g:message code="order.booked.label"/></p>
			                            	</g:else>
			                            	
			                            	<g:if test="${orderInstance?.orderEmployee?.status == 1 && orderInstance?.orderEmployee?.processEmp?.id == null}">
			                            		<g:link class="button-pro" action="process" id="${orderInstance.id}" params="[user: currentUser.id, orderEmpId:orderInstance?.orderEmployee?.id, type:'list', status: status, day: day, myorder:myorder, active:active, fltype:fltype]"><g:message code="order.process.label"/></g:link>
			                            	</g:if>
			                            	<g:else>
			                            		<p class="button-pro"><g:message code="order.process.label"/></p>
			                            	</g:else>
		                            	</g:else>
	                            	</g:else>
	                            </td>
	                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate action="list" params="[status: status, day: day, myorder:myorder, active:active, fltype:fltype]" total="${orderInstanceTotal}" />
            </div>
    </body>
</html>
