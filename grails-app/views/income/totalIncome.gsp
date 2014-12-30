
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script type="text/javascript" src="${resource(dir:'js', file:'income.js') }"></script>
<meta name="layout" content="admin" />
<g:set var="entityName"
	value="${message(code: 'CMSUser.label', default: 'CMSUser')}" />
<title><g:message code="title.homepage" args="[entityName]" /></title>

</head>
<body>
	<div class="nav">
		<g:message	code="navigation.admin.totalincome" />
	</div>
	<div class="body">
		<div class="fitter">
		<span>
			Từ ngày <input id="fromDate" readonly="readonly" type="text" name="fromDate" onchange="changeFromDate()" style="margin:0 10px 0 0;"/>
		
			Đến ngày <input id="toDate" readonly="readonly" type="text" name="toDate" onchange="changeToDate()" style="margin:0 10px 0 0;"/>
		</span>
		</div>
		<g:form method="post" name="totalIncomeForm" action="totalIncome" id="totalIncome">
			<div class="list">
				<table id="tblIncome">
					<thead>
						<tr>
							<th><g:message code="totalincome.label.fare" /></th>
							<th><g:message code="totalincome.label.totalincome" /></th>
							<th><g:message code="totalincome.label.netincome" /></th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>
								Nội địa
							</td>
							<td>
								<g:formatNumber number="${domesticIncome}" formatName="price.number.format"/>
							</td>
							<td>
								<g:formatNumber number="${domesticProfit}" formatName="price.number.format"/>
							</td>
						</tr>
						<tr>
							<td>
								Quốc tế
							</td>
							<td>
								<g:formatNumber number="${internationalIncome}" formatName="price.number.format"/>
							</td>
							<td>
								<g:formatNumber number="${internationalProfit}" formatName="price.number.format"/>
							</td>
						</tr>						
					</tbody>
				</table>
			</div>
		</g:form>
	</div>
</body>
</html>
