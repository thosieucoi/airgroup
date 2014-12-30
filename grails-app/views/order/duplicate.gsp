
<%@page import="com.google.common.base.CharMatcher.Or"%>
<%@ page import="com.airgroup.domain.Order" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="admin" />
        <g:set var="entityName" value="${message(code: 'order.label', default: 'Order')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            	<div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${orderInstance}">
				<div class="errors">
					<g:renderErrors bean="${orderInstance}" as="list" />
				</div>
			</g:hasErrors>
            <div class="list same" id="list">
            	<div style="width:12000000000000px">
                    <g:each in="${orderInstanceList}" status="i" var="orderInstanceList">
                    <table class="order_same">
                    <thead>
                        <tr class="orderone">
                        
                            <th style="width:120px;">${message(code: 'order.code.label', default: 'Order code')}</th>
                             <td><g:link action="edit" id="${orderInstanceList.id}" params="[user:currentUser.id]">${fieldValue(bean: orderInstanceList, field: "id")}</g:link></td>
                         </tr>
                        <tr>
                            <th>${message(code: 'order.customer.label', default: 'Customer name')}</th>
                            <td>${orderInstanceList?.customer?.name}</td>
                        </tr>
    					<tr>
                            <th>${message(code: 'order.customer.tel', default: 'Phone number')}</th>
                            <td>${orderInstanceList?.customer?.phoneNumber}</td>
                        </tr>
                        <tr>
                            <th>${message(code: 'order.customer.email', default: 'Email')}</th>
                            <td>${orderInstanceList?.customer?.email}</td>
                        </tr>
                        <tr>
                            <th>${message(code: 'order.orderDetails.flightNumber', default: 'Flight Number')}</th>
                            <td>${flight[i]}</td>
                        </tr>
                        <tr>
                            <th>${message(code: 'order.orderDetails.outbound', default: 'Outbound')}</th>
                            <td>${ob[i]}</td>
                        </tr>
                        <g:if test="${ib[i] != ''}">
                        	 <tr>
                            <th>${message(code: 'order.orderDetails.inbound', default: 'Inbound')}</th>
                            <td>${ib[i]}</td>
                        </tr>
                        </g:if>
                        <tr>
                            <th>${message(code: 'order.price.label', default: 'Price')}</th>
                             <td>${fieldValue(bean: orderInstanceList, field: "price")}</td>
                        </tr>
                        <tr>
                            <th>${message(code: 'order.passengers.duplicate.label', default: 'Passengers')}</th>
                             <td><g:each in="${orderInstanceList.passengers}" status="j" var="passenger"><p>${fieldValue(bean: passenger, field: "name")}</p>
                            
                            </g:each></td>
                        </tr>
                        <tr>
                        
                            <th>${message(code: 'order.going.label', default: 'Departure')}</th>
                             <td>${dep[i]}</td>
                        </tr>
                        <tr>
                            <th>${message(code: 'order.return.label', default: 'Arrival')}</th>
                             <td>${arr[i]}</td>
                        </tr>
                        <g:if test="${orderInstanceList?.showStatus == 0}">
                        	<tr>
                            	<th>${message(code: 'order.status.label', default: 'Status')}</th>
                             	<td style="color: red; font-weight: bold;">DEACTIVE</td>
                        </tr>
                        </g:if>
                    </thead>
                    
            
                        
                          <tbody>
                    </tbody>
                </table>
                    </g:each>
                    
                  </div>
            </div>
        </div>
    </body>
</html>
