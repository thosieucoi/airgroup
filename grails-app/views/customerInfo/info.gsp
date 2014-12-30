
<%@ page import="com.airgroup.domain.CustomerInfo" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="admin" />
<title><g:message code="title.homepage" args="[entityName]" /></title>
</head>
<body>
	<div class="nav">
		<g:message	code="customer.info.label" /> 
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
						<g:sortableColumn property="phoneNumber" title="${message(code: 'customerInfo.phoneNumber.label', default: 'Phone Number')}" />
						
						<g:sortableColumn property="departure" title="${message(code: 'customerInfo.departure.label', default: 'Departure')}" />
					
						<g:sortableColumn property="arrival" title="${message(code: 'customerInfo.arrival.label', default: 'Arrival')}" />
						
						<g:sortableColumn property="outboundDate" title="${message(code: 'customerInfo.outboundDate.label', default: 'Outbound Date')}" />
                        
                        <g:sortableColumn property="inboundDate" title="${message(code: 'customerInfo.inboundDate.label', default: 'Inbound Date')}" />
                        
					</tr>
				</thead>
				 <tbody>
                    <g:each in="${customerInfoInstanceList}" status="i" var="customerInfoInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td>${fieldValue(bean: customerInfoInstance, field: "phoneNumber")}</td>
                        
                            <td>${fieldValue(bean: customerInfoInstance, field: "departure")}</td>
                            
                            <td>${fieldValue(bean: customerInfoInstance, field: "arrival")}</td>
                        
                            <td><g:formatDate formatName="date.time.format.full" date="${customerInfoInstance.outboundDate}" /></td>
                            
                            <td><g:formatDate formatName="date.time.format.full" date="${customerInfoInstance.inboundDate}" /></td>
                        
                        </tr>
                    </g:each>
                    </tbody>
			</table>
		</div>

		<div class="paginateButtons">
			<%--<g:paginate controller="CMSFeedback" action="index" total="${feedbackInstanceTotal}" />
			--%>
			<g:paginate next="Forward" prev="Back" max="5" maxsteps="10"
				controller="customerInfo" action="info"
				total="${customerInfoInstanceTotal}" />
		</div>
	</div>
</body>
</html>