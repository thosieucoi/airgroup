<%@ page import="com.airgroup.domain.Feedback"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="admin" />
<g:set var="option" value="${option}"/>
<title><g:message code="title.recallAndTimeLimit" args="[entityName]" /></title>
</head>
<body>
	<div class="nav">
		<g:if test="${option == 1}">
			<g:link url="" class="menuButton">Recall</g:link>
		</g:if>
		<g:if test="${option == 2}">
			<g:link url="" class="menuButton">Time limit</g:link>
		</g:if>
		<g:if test="${option == 0}">
			<g:link url="" class="menuButton">Recall/Time limit</g:link>
		</g:if>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<g:link class="menuButton"><g:message code=""></g:message></g:link>
		<g:link class="menuButton"><g:message code=""></g:message></g:link>
	</div>
	<div class="body">
		<div class="list">
			<table id="recallAndTimeLimit">
				<thead>
					<tr>
						<g:sortableColumn property="orderId"
							title="${message(code: 'tour.name.label', default: 'Mã đơn hàng')}" />
							
						<g:sortableColumn property="customerName"
							title="${message(code: 'tour.name.label', default: 'Tên khách hàng')}" />

						<g:sortableColumn property="purchaseDate"
							title="${message(code: 'tour.createdDate.label', default: 'Ngày mua')}" />

						<g:sortableColumn property="status"
							title="${message(code: 'tour.sendDate.status', default: 'Trạng thái')}" />
						
						<%--Show or hide column recall and time limit, depending on user's option--%>
						<g:if test="${option == 1}">	
							<g:sortableColumn property="recall"
								title="${message(code: 'recall.label', default: 'Recall')}" />
						</g:if>
						<g:elseif test="${option == 2}">
							<g:sortableColumn property="timeLimit"
								title="${message(code: 'time.limit.label', default: 'Time Limit')}" />
						</g:elseif>
						<g:else>
							<g:sortableColumn property="recall"
								title="${message(code: 'recall.label', default: 'Recall')}" />
								
							<g:sortableColumn property="timeLimit"
								title="${message(code: 'time.limit.label', default: 'Time Limit')}" />
						</g:else>
						<%--End--%>
						
						<th><g:message code=""/></th>
					</tr>
				</thead>
				<tbody>
					<g:each in="${lstRecallAndTimeLimit}" status="i" var="recallAndTimeLimit">
						<g:each in="${lstOrder}" var="order">
							<g:if test="${recallAndTimeLimit.id == order.id}">
								<tr>
									<td>
										<g:link id="${recallAndTimeLimit.id}" class="overDueRow" controller="order" action="edit">${recallAndTimeLimit.id}</g:link>
									</td>
									
									<td>
										${order.customer.name}
									</td>
		
									<td><g:formatDate formatName="date.time.format.short" date="${order.orderTime}" /></td>
		
									<td class="color under">
										<g:message code="order.status.1"/>
									</td>

									<%--Show or hide column recall and time limit, depending on user's option--%>
									<g:if test="${option == 1}">
										<td class="re-call">
											<g:formatDate formatName="date.time.format.full" date="${recallAndTimeLimit.notification }"></g:formatDate>
											<input class="notification" type="hidden" value="${recallAndTimeLimit.notification }" />
										</td>
									</g:if>
									<g:elseif test="${option == 2}">
										<td class="time-limit">
											<g:formatDate formatName="date.time.format.full" date="${recallAndTimeLimit.timeLimit }"></g:formatDate>
											<input class="timeLimit" type="hidden" value="${recallAndTimeLimit.timeLimit }" />
										</td>
									</g:elseif>
									<g:elseif test="${option == 0}">
										<td class="re-call">
											<g:formatDate formatName="date.time.format.full" date="${recallAndTimeLimit.notification }"></g:formatDate>
											<input class="notification" type="hidden" value="${recallAndTimeLimit.notification }" />
										</td>
										<td class="time-limit">
											<g:formatDate formatName="date.time.format.full" date="${recallAndTimeLimit.timeLimit }"></g:formatDate>
											<input class="timeLimit" type="hidden" value="${recallAndTimeLimit.timeLimit }" />
										</td>
									</g:elseif>
									<%--End--%>
									
									<td style="width: 150px;">
										<g:link id="${recallAndTimeLimit.id}" controller="orderEmployee" action="done" class="button ui-corner-all" elementId="doneButton" params="[option : option]">
											<g:message code="default.button.done.label" encodeAs="HTML"/>
										</g:link>
										<input class="isDoneType" type="hidden" value="${option}">
									</td>
								</tr>
							</g:if>
						</g:each>
					</g:each>
				</tbody>
			</table>
		</div>
		<div class="paginateButtons">
			<g:paginate	
				next="Forward" prev="Back"
				max="5" maxsteps="10"
				controller="orderEmployee" action="list"
				total="" />
		</div>
	</div>
</body>
</html>