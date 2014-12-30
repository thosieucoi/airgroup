	<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="admin" />
	<script type="text/javascript" src="${resource(dir:'js', file:'tips-management.js') }"></script>
	<title><g:message code="title.homepage" args="[entityName]" /></title>
	</head>
	<body>
		<div class="nav">
			<g:message code="tips.header.label"/>
		</div>
		<div class="body">
			<div class="list">
				<div class="nav">
					
					<span>
						Từ ngày <input id="fromDate" readonly="readonly" type="text" style="margin:0 10px 0 0;"/>
					
						Đến ngày <input id="toDate" readonly="readonly" type="text" style="margin:0 10px 0 0;"/>
					</span>
					<div id="errorMessage"></div>
					<table id="tips-list">
						<thead>
							<tr>
								<td colspan="2"></td>
								<td colspan="3" class="tips"><g:message code="tips.table.header.domestic"/></td>
								<td colspan="3" class="tips"><g:message code="tips.table.header.international"/></td>
							</tr>
							<tr>
								<g:each in="${lastTips}" var="lastTips">
									<td class="tips"><g:message code="tips.table.header.employee.id"/></td>
									<td class="tips"><g:message code="tips.table.header.employee.full.name"/></td>
									<td rowspan="2" class="tips">
										<g:message code="tips.table.header.booked"/>
										<div>
											<g:if test="${lastTips.lastDomesticBookedTips > 0}">
												<input id="domesticBookedTips" type="text" value="${lastTips.lastDomesticBookedTips}"> VNĐ
											</g:if>
											<g:else>
												<input id="domesticBookedTips" type="text"> VNĐ
											</g:else>
										</div>
									</td>
									<td rowspan="2" class="tips">
										<g:message code="tips.table.header.processed"/>
										<div>
											<g:if test="${lastTips.lastDomesticProcessTips > 0}">
												<input id="domesticProcessedTips" type="text" value="${lastTips.lastDomesticProcessTips}"> VNĐ
											</g:if>
											<g:else>
												<input id="domesticProcessedTips" type="text"> VNĐ
											</g:else>
										</div>
									</td>
									<td class="tips">
										<g:message code="tips.table.header.total"/>
										<div>(VNĐ)</div>
									</td>
									<td rowspan="2" class="tips">
										<g:message code="tips.table.header.booked"/>
										<div>
											<g:if test="${lastTips.lastInternationalBookedTips > 0}">
												<input id="internationalBookedTips" type="text" value="${lastTips.lastInternationalBookedTips}"> VNĐ
											</g:if>
											<g:else>
												<input id="internationalBookedTips" type="text"> VNĐ
											</g:else>
										</div>
									</td>
									<td rowspan="2" class="tips">
										<g:message code="tips.table.header.processed"/>
										<div>
											<g:if test="${lastTips.lastInternationalProcessTips > 0}">
												<input id="internationalProcessedTips" type="text" value="${lastTips.lastInternationalProcessTips}"> VNĐ
											</g:if>
											<g:else>
												<input id="internationalProcessedTips" type="text"> VNĐ
											</g:else>
										</div>
									</td>
									<td class="tips">
										<g:message code="tips.table.header.total"/>
										<div>(VNĐ)</div>
									</td>
									<td class="tips">
										<g:message code="tips.table.header.total"/>
										<div>(VNĐ)</div>
									</td>
								</g:each>
							</tr>
						</thead>
						<tbody>
							<g:each in="${lstUser}" var="user" status="i">
								<g:each in="${numsOfDomesticOrder}" var="numOfDomesticOrder">
									<g:each in="${numsOfInternationalOrder}" var="numsOfInternationalOrder">
										<g:if test="${numOfDomesticOrder.get(2) == user.id && numsOfInternationalOrder.get(2) == user.id}">
											<tr class="${(i % 2) == 0 ? 'odd' : 'even'} tips">
												<td>${user.id}</td>
												<td>${user.name}</td>
												
												<%--Domestic--%>
												<td id="bookDomesticOrder" class="orders">${numOfDomesticOrder.get(0)}</td>
												<td id="processDomesticOrder" class="orders">${numOfDomesticOrder.get(1)}</td>
												<td id="tipsDomesticOrder" class="orders"></td>
											
												<%--International--%>
												<td id="bookInternationalOrder" class="orders">${numsOfInternationalOrder.get(0)}</td>
												<td id="processInternationalOrder" class="orders">${numsOfInternationalOrder.get(1)}</td>
												<td id="tipsInternationalOrder" class="orders"></td>
												
												<%--Total --%>
												<td id="tipsTotal" class="orders"></td>
											</tr>
										</g:if>
									</g:each>
								</g:each>
							</g:each>
						</tbody>
					</table>
				</div>
			</div>
			<div class="paginateButtons">
				<g:paginate	next="Forward" prev="Back" maxsteps="10"
					controller="tips" action="list"
					total="${total}" />
			</div>
		</div>
	</body>
	</html>