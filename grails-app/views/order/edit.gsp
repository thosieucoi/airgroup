

<%@ page import="com.airgroup.domain.Order"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="admin" />
<g:set var="entityName"
	value="${message(code: 'order.label', default: 'Order')}" />
<title><g:message code="default.edit.label" args="[entityName]" /></title>
<style type="text/css">
.hidden {
	display: none;
}

.show {
	display: block;
}
.error
{
    color: red;
    font-family : Verdana;
    font-size : 8pt;
}
</style>
<style>
#slider {
	margin: 10px;
}
</style>

<script type="text/javascript" src="${resource(dir:'js',file:'orderInfo.js') }"></script>
</head>
<body>
	<script type="text/javascript">
	jQuery(document).ready(function(){
        window.scrollTo(0,0);
        jQuery('#update').click(function() {
        	jQuery("html, body").animate({ scrollTop: 0 }, "slow");
        	  return false;
        	});
	});
	</script>
	<div class="nav">
		<span class="menuButton"><g:link class="list" action="list">
				<g:message code="order.list.label" args="[entityName]" />
			</g:link></span> &gt;
		<g:message code="order.detail.label" />
	</div>
	<div class="body">
		<h1>
			<g:message code="order.edit.label" args="[entityName]" />
		</h1>
		<div class="errors" style="color:red;">
				
		</div>
		<g:if test="${flash.message}">
			<div class="message">
				<g:message code="${flash.message}" encodeAs="HTML" />
			</div>
		</g:if>
		<g:hasErrors bean="${orderInstance}">
			<div class="errors">
				<g:renderErrors bean="${orderInstance}" as="list" />
			</div>
		</g:hasErrors>

		<g:form name="doAdd" action="update">
			<g:hiddenField name="id" value="${orderInstance?.id}" />
			<g:hiddenField id="currentUserId" name="employee.id"
				value="${currentUser?.id}" />
			<g:hiddenField id="processId" name="processId"
				value="${orderInstance?.orderEmployee?.processEmp?.id}" />
			<g:hiddenField name="bookId" 
				value="${orderInstance?.orderEmployee?.bookEmp?.id}" />
			<g:hiddenField name="orderEmpId"
				value="${orderInstance?.orderEmployee?.id }" />
			<g:hiddenField id="currentUserRole" name="role"
				value="${currentUser?.authorities}" />
			<g:hiddenField id="domestic" name="domestic"
				value="${orderInstance?.isDomestic}" />
			<g:if test="${orderInstance?.status == 2}">
				<g:set var="readonlyValue" value="true" />
			</g:if>
			<g:else>
				<g:if
					test="${orderInstance?.orderEmployee?.processEmp?.id != currentUser?.id }">
					<g:set var="readonlyValue" value="true" />
				</g:if>
				<g:else>
					<g:set var="readonlyValue" value="false" />
				</g:else>
			</g:else>
			<div style="clear: both"></div>
			<table class="paylist-ad" id="paylist-ad">
				<g:if test="${orderInstance?.status == 2}">
					<tr>
						<td><g:message code="order.completed.label" /></td>
					</tr>
				</g:if>
				<g:else>
					<tr>
						<g:if
							test="${(orderInstance?.orderEmployee?.status == 1 && orderInstance?.orderEmployee?.processEmp?.id == currentUser?.id)}">
							<td>
								<g:if test="${orderInstance?.showStatus}">
									<g:link action="activeDeactiveOrder" class="hideOrder"
										params="[orderId: orderInstance?.id, status: '0']"><g:message code="common.status.0" /></g:link>
								</g:if>
								<g:else>
									<g:link action="activeDeactiveOrder" class="hideOrder"
										params="[orderId: orderInstance?.id, status: '1']"><g:message code="common.status.1" /></g:link>
								</g:else>
							</td>
							<td><g:link action="processCancel" class="hideOrder"
									params="[user: currentUser.id, orderEmpId:orderInstance?.orderEmployee?.id, type:'form']">
									<g:message code="order.processing.cancel.label" />
								</g:link></td>
							<g:if test="${orderInstance?.status == 1}">
								<td><modalbox:createLink controller="order"
										action="forwardOrder"
										params="[orderEmpId:orderInstance?.orderEmployee?.id]"
										title="List employee" width="350"><span class="hideSpanOrder"><g:message code="order.forward.label" /></span></modalbox:createLink>
								</td>
							</g:if>
						</g:if>
						<g:elseif test="${orderInstance?.orderEmployee?.status == 1 && currentUser?.authorities?.id.getAt(0) == 2
										 && orderInstance?.orderEmployee?.processEmp?.id}">
							<td>
								<g:if test="${orderInstance?.showStatus}">
									<g:link action="activeDeactiveOrder" class="hideOrder"
										params="[orderId: orderInstance?.id, status: '0']"><g:message code="common.status.0" /></g:link>
								</g:if>
								<g:else>
									<g:link action="activeDeactiveOrder" class="hideOrder"
										params="[orderId: orderInstance?.id, status: '1']"><g:message code="common.status.1" /></g:link>
								</g:else>
							</td>
							<td><g:link action="processCancel" class="hideOrder"
									params="[user: currentUser.id, orderEmpId:orderInstance?.orderEmployee?.id, type:'form']">
									<g:message code="order.processing.cancel.label" />
								</g:link></td>
							<g:if test="${orderInstance?.status == 1}">
								<td><modalbox:createLink controller="order"
										action="forwardOrder"
										params="[orderEmpId:orderInstance?.orderEmployee?.id]"
										title="List employee" width="350"><span class="hideSpanOrder"><g:message code="order.forward.label" /></span></modalbox:createLink>
								</td>
							</g:if>
						</g:elseif>
						<g:elseif
							test="${orderInstance?.orderEmployee?.status == 1 && orderInstance?.orderEmployee?.processEmp?.id}">
							<td><span class="button"><g:message
										code="order.processing.label" /></span></td>
						</g:elseif>
						<g:else>
							<g:if
								test="${orderInstance?.orderEmployee?.status == null || orderInstance?.orderEmployee?.status == 0}">
								<sec:ifAnyGranted roles="ROLE_ADMIN">
									<td>
									<g:if test="${orderInstance?.showStatus}">
										<g:link action="activeDeactiveOrder" class="hideOrder"
											params="[orderId: orderInstance?.id, status: '0']"><g:message code="common.status.0" /></g:link>
									</g:if>
									<g:else>
										<g:link action="activeDeactiveOrder" class="hideOrder"
											params="[orderId: orderInstance?.id, status: '1']"><g:message code="common.status.1" /></g:link>
									</g:else>
								</td>
								</sec:ifAnyGranted>
								<td><g:link class="hideOrder" action="book"
										params="[user: currentUser?.id, orderEmpId:orderInstance?.orderEmployee?.id, type:'form', orderId: orderInstance?.id]">
										<g:message code="order.booking.label" />
									</g:link></td>
							</g:if>
							<g:elseif
								test="${orderInstance?.orderEmployee?.status == 1 && (orderInstance?.orderEmployee?.bookEmp?.id == currentUser.id || currentUser?.authorities?.id.getAt(0) == 2)}">
								<td>
									<g:if test="${orderInstance?.showStatus}">
										<g:link action="activeDeactiveOrder" class="hideOrder"
											params="[orderId: orderInstance?.id, status: '0']"><g:message code="common.status.0" /></g:link>
									</g:if>
									<g:else>
										<g:link action="activeDeactiveOrder" class="hideOrder"
											params="[orderId: orderInstance?.id, status: '1']"><g:message code="common.status.1" /></g:link>
									</g:else>
								</td>
								<td><g:link class="hideOrder" action="bookCancel"
										params="[user: currentUser?.id, orderEmpId:orderInstance?.orderEmployee?.id, orderId: orderInstance?.id, type:'form']">
										<g:message code="order.booking.cancel.label" />
									</g:link></td>
							</g:elseif>
							<g:else>
								<td><span class="button"><g:message
											code="order.booked.label" /></span></td>
							</g:else>

							<g:if
								test="${orderInstance?.orderEmployee?.status == 1 && orderInstance?.orderEmployee?.processEmp?.id == null}">
								<td><g:link class="hideOrder" action="process"
										params="[user: currentUser?.id, orderEmpId:orderInstance?.orderEmployee?.id, type:'form']">
										<g:message code="order.process.label" />
									</g:link></td>
							</g:if>
							<g:else>
								<td><span class="button"><g:message
											code="order.process.label" /></span></td>
							</g:else>
						</g:else>
					</tr>
				</g:else>
			</table>
			
			<g:if test="${dupList?.size() > 0}" >
				<g:link action="duplicate" id="${orderInstance?.id}" style=" padding-left:10px; color:red;"
					params="[user : currentUser?.id]">
					<g:message code="order.duplicate" />
				</g:link>
			</g:if>
			<div style="clear: both"></div>

			<div class="dialog infors" id="customerInfo">
				<fieldset>
					<legend>
						<g:message code="customer.contact.info.title" />
					</legend>
					<table>
						<tbody>
							<g:hiddenField name="customerId"
								value="${orderInstance?.customer?.id}" />
							<tr class="prop">
								<td valign="top" class="name"><label for="customer.name"><g:message
											code="customer.fullname.label" default="Full name" /></label></td>
								<td valign="top"
									class="value ${hasErrors(bean: customer, field: 'name', 'errors')}">
									<g:textField name="customerName"
										value="${orderInstance?.customer?.name}"
										readonlyValue="${readonlyValue}" maxlength="100" />
								</td>
							</tr>

							<tr class="prop">
								<td valign="top" class="name"><label for="email"><g:message
											code="common.email" default="Email" /></label></td>
								<td valign="top"
									class="value ${hasErrors(bean: customer, field: 'email', 'errors')}">
									<g:textField name="customerEmail"
										value="${orderInstance?.customer?.email}"
										readonlyValue="${readonlyValue}" maxlength="100" />
								</td>
							</tr>
							<tr class="prop">
								<td valign="top" class="name"><label for="phoneNumber"><g:message
											code="common.phone" default="Phone" /></label></td>
								<td valign="top"
									class="value ${hasErrors(bean: customer, field: 'phoneNumber', 'errors')}">
									<g:textField name="customerPhoneNumber"
										value="${orderInstance?.customer?.phoneNumber}"
										readonlyValue="${readonlyValue}" maxlength="15" />
								</td>
							</tr>
							<tr class="prop">
								<td valign="top" class="name"><label for="address"><g:message
											code="common.address" default="Address" /></label></td>
								<td valign="top"
									class="value ${hasErrors(bean: customer, field: 'address', 'errors')}">
									<g:textField name="customerAddress"
										value="${orderInstance?.customer?.address}"
										readonlyValue="${readonlyValue}" maxlength="100" />
								</td>
							</tr>
							<tr class="prop">
								<td valign="top" class="name"><label for="city"><g:message
											code="common.city" default="City" /></label></td>
								<td valign="top"
									class="value ${hasErrors(bean: customer, field: 'city', 'errors')}">
									<g:textField name="customerCity"
										value="${orderInstance?.customer?.city}"
										readonlyValue="${readonlyValue}" maxlength="100" />
								</td>
							</tr>
							
							<g:hiddenField name="orderTime"
								value="${orderInstance?.orderTime}" />
						</tbody>
					</table>
					<table>
						<tr class="prop">
							<td valign="top" class="name" colspan="4"><label
								for="billInfo"><g:message code="order.bill.info.title"
										default="Bill information" /></label></td>

						</tr>
						<tr class="prop">
							<g:hiddenField name="billId" value="${orderInstance?.bill?.id}" />
							<td valign="top" class="name"><label for="companyName"><g:message
										code="company.name.label" default="Company" /></label></td>
							<td valign="top"
								class="value ${hasErrors(bean: bill, field: 'companyName', 'errors')}">
								<g:textField name="companyName"
									value="${orderInstance?.bill?.companyName}"
									readonlyValue="${readonlyValue}" maxlength="100" />
							</td>
							<td valign="top" class="name"><label for="companyAddress"><g:message
										code="company.address.label" default="Company Address" /></label></td>
							<td valign="top"
								class="value ${hasErrors(bean: bill, field: 'companyAddress', 'errors')}">
								<g:textField name="companyAddress"
									value="${orderInstance?.bill?.companyAddress}"
									readonlyValue="${readonlyValue}" maxlength="100" />
							</td>
						</tr>
						<tr class="prop">
							<td valign="top" class="name"><label for="taxSerial"><g:message
										code="company.tax.code.label" default="Tax code" /></label></td>
							<td valign="top"
								class="value ${hasErrors(bean: bill, field: 'taxSerial', 'errors')}">
								<g:textField name="taxSerial"
									value="${orderInstance?.bill?.taxSerial}"
									readonlyValue="${readonlyValue}" maxlength="15" />
							</td>
							<td valign="top" class="name"><label for="address"><g:message
										code="company.address.receive.bill.label" default="Address" /></label>
							</td>
							<td valign="top"
								class="value ${hasErrors(bean: bill, field: 'address', 'errors')}">
								<g:textField name="address"
									value="${orderInstance?.bill?.address}"
									readonlyValue="${readonlyValue}" maxlength="100" />
							</td>
						</tr>
					</table>
				</fieldset>
				<fieldset>
					<legend>
						<g:message code="passenger.info.title" />
					</legend>
					<table id="tbl_container">
						<tr class="prop" id="container">
							<td valign="top" class="name" colspan="3">
								<p id="add_field">
									<span class="button"><input type="button"
										id="addPassenger" value="${message(code: 'button.create')}"
										readonlyValue="${readonlyValue}"></span>
								</p>
							</td>
						</tr>
						<g:each in="${orderInstance?.passengers.sort{ it.id }}" var="p"
							status="i">
							<tr class="prop" id="${++i}">
								<g:hiddenField name="passenger.${i}.id" value="${p?.id }" />
								<td valign="top" class="name"><label for="name"> <g:message
											code="customer.fullname.label" default="Full name" /> ${i}</label></td>
								<td valign="top"
									class="value ${hasErrors(bean: passenger, field: 'name', 'errors')}">
									<g:textField name="passenger.${i}.name" value="${p?.name}"
										readonlyValue="${readonlyValue}" maxlength="100"/>
								</td>
								<td valign="top"
									class="value ${hasErrors(bean: passenger, field: 'gender', 'errors')}">
									<g:select name="passenger.${i}.gender"
										onchange="showHideDOB(this); return false;"
										from="${['0','1','2','3','4','5']}"
										valueMessagePrefix="common.gender" value="${p?.gender}"
										readonlyValue="${readonlyValue}" />
								</td>
								<td>
									<input type="button" class="hideOrder" id="deletePassenger"
										value="${message(code: 'button.delete')}" onclick="removePassenger(this); return false;"/>
								</td>
							</tr>
							<g:if test="${p?.gender > 1 }">
								<tr class="prop">
									<td valign="top" class="name"><g:message code="passenger.dob.title" default="Date of Birth"/></td>
									<td valign="top"
										class="value ${hasErrors(bean: passenger, field: 'dateOfBirth', 'errors')}">
										<g:textField id="datepicker.${i}" onmouseover="DoBClick(this);"
											name="passenger.${i}.dateOfBirth"
											value="${formatDate(format:'dd/MM/yyyy',date:p?.dateOfBirth)}"
											readonlyValue="${readonlyValue}" readonly="true"/>
									</td>
								</tr>
								<g:if test="${p.luggage.baggage.size() > 0}">
								<tr>
								<g:if test="${p.luggage.sort{it.id}.isDeparture.get(0) == 1}">
									<td><g:message code="passenger.outboundluggage.title" default="Outbound Luggage"/></td>
									<g:if test="${p.luggage.sort{it.id}.airlineCode.get(0) == 'VJ'}">
										<td>
											<g:select name="passenger.${i}.outboundluggage" valueMessagePrefix="vietjet.luggage" value="${p.luggage.sort{it.id}.baggage.get(0)}" from="${['0', '143000', '165000', '220000', '330000', '385000', '440000']}"/>
											<g:hiddenField name="passenger.${i}.obairline" value="VJ" />
											<g:hiddenField name="passenger.${i}.isDeparture" value="1" />
										</td>
									</g:if>
									<g:elseif test="${p.luggage.sort{it.id}.airlineCode.get(0) == 'BL'}">
										<td>
											<g:select name="passenger.${i}.outboundluggage" valueMessagePrefix="jetstar.luggage" value="${p.luggage.sort{it.id}.baggage.get(0)}" from="${['0', '143000', '165000', '220000', '270000', '320000', '370000']}"/>
											<g:hiddenField name="passenger.${i}.obairline" value="BL" />
											<g:hiddenField name="passenger.${i}.isDeparture" value="1" />
										</td>
									</g:elseif>
									</g:if>
									<g:elseif test="${p.luggage.sort{it.id}.isDeparture.get(0) == 0}">
										<td><g:message code="passenger.inboundluggage.title" default="Inbound Luggage"/></td>
										<g:if test="${p.luggage.sort{it.id}.airlineCode.get(0) == 'VJ'}">
										<td>
											<g:select name="passenger.${i}.inboundluggage" valueMessagePrefix="vietjet.luggage" value="${p.luggage.sort{it.id}.baggage.get(0)}" from="${['0', '143000', '165000', '220000', '330000', '385000', '440000']}"/>
											<g:hiddenField name="passenger.${i}.ibairline" value="VJ" />
											<g:hiddenField name="passenger.${i}.isDeparture" value="0" />
										</td>
										</g:if>
										<g:elseif test="${p.luggage.sort{it.id}.airlineCode.get(0) == 'BL'}">
										<td>
											<g:select name="passenger.${i}.inboundluggage" valueMessagePrefix="jetstar.luggage" value="${p.luggage.sort{it.id}.baggage.get(0)}" from="${['0', '143000', '165000', '220000', '270000', '320000', '370000']}"/>
											<g:hiddenField name="passenger.${i}.ibairline" value="BL" />
											<g:hiddenField name="passenger.${i}.isDeparture" value="0" />
										</td>
									</g:elseif>
									</g:elseif>
								</tr>
								<g:if test="${p.luggage.baggage.size() > 1}">
									<td><g:message code="passenger.inboundluggage.title" default="Inbound Luggage"/></td>
									<g:if test="${p.luggage.sort{it.id}.airlineCode.get(1) == 'VJ'}">
										<td>
											<g:select name="passenger.${i}.inboundluggage" valueMessagePrefix="vietjet.luggage" value="${p.luggage.sort{it.id}.baggage.get(1)}" from="${['0', '143000', '165000', '220000', '330000', '385000', '440000']}"/>
											<g:hiddenField name="passenger.${i}.ibairline" value="VJ" />
											<g:hiddenField name="passenger.${i}.isDeparture" value="0" />
										</td>
									</g:if>
									<g:elseif test="${p.luggage.sort{it.id}.airlineCode.get(1) == 'BL'}">
										<td>
											<g:select name="passenger.${i}.inboundluggage" valueMessagePrefix="jetstar.luggage" value="${p.luggage.sort{it.id}.baggage.get(1)}" from="${['0', '143000', '165000', '220000', '270000', '320000', '370000']}"/>
											<g:hiddenField name="passenger.${i}.ibairline" value="BL" />
											<g:hiddenField name="passenger.${i}.isDeparture" value="0" />
										</td>
									</g:elseif>
								</g:if>	
							</g:if>
							</g:if>
							<g:else>
								<tr class="prop" style="display: none;">
									<td valign="top" class="name"></td>
									<td valign="top"
										class="value ${hasErrors(bean: passenger, field: 'dateOfBirth', 'errors')}">
										<g:textField id="datepicker.${i}"
											name="passenger.${i}.dateOfBirth" onmouseover="DoBClick(this);"
											value="${formatDate(format:'dd/MM/yyyy',date:p?.dateOfBirth)}"
											readonlyValue="${readonlyValue}"  readonly="true"/>
									</td>
								</tr>
								<g:if test="${p.luggage.baggage.size() > 0}">
								<tr>
								<g:if test="${p.luggage.sort{it.id}.isDeparture.get(0) == 1}">
									<td><g:message code="passenger.outboundluggage.title" default="Outbound Luggage"/></td>
									<g:if test="${p.luggage.sort{it.id}.airlineCode.get(0) == 'VJ'}">
										<td>
											<g:select name="passenger.${i}.outboundluggage" valueMessagePrefix="vietjet.luggage" value="${p.luggage.sort{it.id}.baggage.get(0)}" from="${['0', '143000', '165000', '220000', '330000', '385000', '440000']}"/>
											<g:hiddenField name="passenger.${i}.obairline" value="VJ" />
											<g:hiddenField name="passenger.${i}.isDeparture" value="1" />
										</td>
									</g:if>
									<g:elseif test="${p.luggage.sort{it.id}.airlineCode.get(0) == 'BL'}">
										<td>
											<g:select name="passenger.${i}.outboundluggage" valueMessagePrefix="jetstar.luggage" value="${p.luggage.sort{it.id}.baggage.get(0)}" from="${['0', '143000', '165000', '220000', '270000', '320000', '370000']}"/>
											<g:hiddenField name="passenger.${i}.obairline" value="BL" />
											<g:hiddenField name="passenger.${i}.isDeparture" value="1" />
										</td>
									</g:elseif>
									</g:if>
									<g:elseif test="${p.luggage.sort{it.id}.isDeparture.get(0) == 0}">
										<td><g:message code="passenger.inboundluggage.title" default="Inbound Luggage"/></td>
										<g:if test="${p.luggage.sort{it.id}.airlineCode.get(0) == 'VJ'}">
										<td>
											<g:select name="passenger.${i}.inboundluggage" valueMessagePrefix="vietjet.luggage" value="${p.luggage.sort{it.id}.baggage.get(0)}" from="${['0', '143000', '165000', '220000', '330000', '385000', '440000']}"/>
											<g:hiddenField name="passenger.${i}.ibairline" value="VJ" />
											<g:hiddenField name="passenger.${i}.isDeparture" value="0" />
										</td>
										</g:if>
										<g:elseif test="${p.luggage.sort{it.id}.airlineCode.get(0) == 'BL'}">
										<td>
											<g:select name="passenger.${i}.inboundluggage" valueMessagePrefix="jetstar.luggage" value="${p.luggage.sort{it.id}.baggage.get(0)}" from="${['0', '143000', '165000', '220000', '270000', '320000', '370000']}"/>
											<g:hiddenField name="passenger.${i}.ibairline" value="BL" />
											<g:hiddenField name="passenger.${i}.isDeparture" value="0" />
										</td>
									</g:elseif>
									</g:elseif>
								</tr>
								<g:if test="${p.luggage.baggage.size() > 1}">
									<td><g:message code="passenger.inboundluggage.title" default="Inbound Luggage"/></td>
									<g:if test="${p.luggage.sort{it.id}.airlineCode.get(1) == 'VJ'}">
										<td>
											<g:select name="passenger.${i}.inboundluggage" valueMessagePrefix="vietjet.luggage" value="${p.luggage.sort{it.id}.baggage.get(1)}" from="${['0', '143000', '165000', '220000', '330000', '385000', '440000']}"/>
											<g:hiddenField name="passenger.${i}.ibairline" value="VJ" />
											<g:hiddenField name="passenger.${i}.isDeparture" value="0" />
										</td>
									</g:if>
									<g:elseif test="${p.luggage.sort{it.id}.airlineCode.get(1) == 'BL'}">
										<td>
											<g:select name="passenger.${i}.inboundluggage" valueMessagePrefix="jetstar.luggage" value="${p.luggage.sort{it.id}.baggage.get(1)}" from="${['0', '143000', '165000', '220000', '270000', '320000', '370000']}"/>
											<g:hiddenField name="passenger.${i}.ibairline" value="BL" />
											<g:hiddenField name="passenger.${i}.isDeparture" value="0" />
										</td>
									</g:elseif>
								</g:if>	
							</g:if>
							</g:else>
							<script>
								counter =
							${i}
								;
							</script>
						</g:each>

					</table>
				</fieldset>
				<p class="nvedit">
					
					<g:message code="order.employee.booking.label" />
					<b> ${orderInstance?.orderEmployee?.bookEmp?.username }
					</b>
					</br>
					<g:message code="order.employee.process.label" />
					<b> ${orderInstance?.orderEmployee?.processEmp?.username }
					</b>
				</p>
			</div>

			<div class="dialog right" id="orderInfo">
				<fieldset>
					<legend>
						<g:message code="order.info.title" />
					</legend>
					<table>
						<tbody>

							<tr class="prop">
								<td valign="top" class="name"><label for="code"><g:message
											code="order.code.label" default="Order code" /></label></td>
								<td valign="top"
									class="value ${hasErrors(bean: orderInstance, field: 'id', 'errors')}">
									<g:textField
										name="code" value="${orderInstance?.id}" disabled="disabled" />
								</td>
							</tr>
							<tr class="prop">
								<td colspan="2">
									<fieldset id="tripGoing">
										<legend>
											<g:message code="order.trip.going" />
										</legend>
										<table id="going" style="width: 100%">
											<g:each in="${orderInstance?.orderDetails.sort{ it.id }}" var="orderDetails" status="i">
												<g:if test="${orderDetails?.tripType == 1}">
													<g:hiddenField name="orderDetail.${orderDetails?.id}.id"
															value="${orderDetails?.id}" />
													<g:if test="${orderInstance?.orderDetails.size() > 1 && i > 0 }">
														<tr class="stops">
															<td colspan="2"><g:message code="order.transit.location"/><a href=""> ${locations} </a>
																<g:message code="order.transit.time"/><a href=""> ${durations[i-1] } </a>
															</td>
														</tr>
													</g:if>
													<tr class="prop">
														<td valign="top" class="flightNumber"><label
															for="flightNumber"><g:message
																	code="order.flight.number" default="Flight number" /></label></td>
														<td valign="top"
															class="value ${hasErrors(bean: orderDetail, field: 'flightNumber', 'errors')}">
															<g:textField
																name="orderDetail.${orderDetails?.id}.airline"
																value="${orderDetails?.airline}" style="width:25px"
																readonlyValue="${readonlyValue}" maxlength="5" /> <g:textField
																name="orderDetail.${orderDetails?.id}.flightNumber"
																value="${orderDetails?.flightNumber}" style="width:100px"
																readonlyValue="${readonlyValue}" maxlength="50" />
														</td>
													</tr>
													<tr class="prop">
														<td valign="top" class="name"><label for="departure"><g:message
																	code="book.origin" default="Origin" /></label></td>
														<td valign="top"
															class="value ${hasErrors(bean: orderDetail, field: 'departure', 'errors')}">
															<g:textField
																name="orderDetail.${orderDetails?.id}.departure"
																value="${orderDetails?.departure}" style="width:30px"
																readonlyValue="${readonlyValue}" maxlength="50" /> <g:textField
																name="orderDetail.${orderDetails?.id}.arrival"
																value="${orderDetails?.arrival}" style="width:30px"
																readonlyValue="${readonlyValue}" maxlength="50" />
														</td>
													</tr>
													<tr class="prop">
														<td valign="top" class="name"><label
															for="outboundDate"><g:message
																	code="book.date.departure" default="Departure date" /></label></td>
														<td valign="top"
															class="value ${hasErrors(bean: orderDetail, field: 'outboundDate', 'errors')}">
															<g:textField id="outboundDate.${orderDetails?.id}"
																name="orderDetail.${orderDetails?.id}.outboundDate"
																value="${formatDate(formatName:'date.time.format.full',date:orderDetails?.outboundDate)}"
																readonlyValue="${readonlyValue}" readonly="true"/> <g:textField
																id="inboundDate.${orderDetails?.id}"
																name="orderDetail.${orderDetails?.id}.inboundDate"
																value="${formatDate(formatName:'date.time.format.full',date:orderDetails?.inboundDate)}"
																readonlyValue="${readonlyValue}"  readonly="true"/>
														</td>
													</tr>
													
												</g:if>
											</g:each>
										</table>
									</fieldset>
									<fieldset id="tripComing">
										<legend>
											<g:message code="order.trip.coming" />
										</legend>
										<table id="coming" style="width: 100%">
											<g:each in="${orderInstance?.orderDetails.sort{ it.id }}" var="orderDetails" status="j">
												<g:if test="${orderDetails?.tripType == 2}">
													<g:hiddenField name="orderDetail.${orderDetails?.id}.id"
															value="${orderDetails?.id}" />
														
													<tr class="prop">
														<td valign="top" class="flightNumber"><label
															for="flightNumber"><g:message
																	code="order.flight.number" default="Flight number" /></label></td>
														<td valign="top"
															class="value ${hasErrors(bean: orderDetail, field: 'flightNumber', 'errors')}">
															<g:textField
																name="orderDetail.${orderDetails?.id}.airline"
																value="${orderDetails?.airline}" style="width:25px"
																readonlyValue="${readonlyValue}" maxlength="5" /> <g:textField
																name="orderDetail.${orderDetails?.id}.flightNumber"
																value="${orderDetails?.flightNumber}" style="width:100px"
																readonlyValue="${readonlyValue}" maxlength="50" />
														</td>
													</tr>
													<tr class="prop">
														<td valign="top" class="name"><label for="departure"><g:message
																	code="book.origin" default="Origin" /></label></td>
														<td valign="top"
															class="value ${hasErrors(bean: orderDetail, field: 'departure', 'errors')}">
															<g:textField
																name="orderDetail.${orderDetails?.id}.departure"
																value="${orderDetails?.departure}" style="width:30px"
																readonlyValue="${readonlyValue}" maxlength="50" /> <g:textField
																name="orderDetail.${orderDetails?.id}.arrival"
																value="${orderDetails?.arrival}" style="width:30px"
																readonlyValue="${readonlyValue}" maxlength="50" />
														</td>
													</tr>
													<tr class="prop">
														<td valign="top" class="name"><label
															for="outboundDate"><g:message
																	code="book.date.departure" default="Departure date" /></label></td>
														<td valign="top"
															class="value ${hasErrors(bean: orderDetail, field: 'outboundDate', 'errors')}">
															<g:textField id="outboundDate.${orderDetails?.id}"
																name="orderDetail.${orderDetails?.id}.outboundDate"
																value="${formatDate(formatName:'date.time.format.full',date:orderDetails?.outboundDate)}"
																readonlyValue="${readonlyValue}" readonly="true"/> <g:textField
																id="inboundDate.${orderDetails?.id}"
																name="orderDetail.${orderDetails?.id}.inboundDate"
																value="${formatDate(formatName:'date.time.format.full',date:orderDetails?.inboundDate)}"
																readonlyValue="${readonlyValue}"  readonly="true"/>
														</td>
													</tr>
													<g:if test="${orderInstance?.orderDetails.size() > 1 && (orderInstance?.orderDetails.size()-j > 1) }">
														<tr class="stops">
															<td colspan="2"><g:message code="order.transit.location"/><a href=""> ${locations[j-1]} </a>
																<g:message code="order.transit.time"/><a href=""> ${durations[j-1]} </a>
															</td>
														</tr>
													</g:if>	
												</g:if>
											</g:each>
										</table>
									</fieldset>
								</td>
							</tr>

							<tr class="prop">
								<td valign="top" class="name"><label for="price"><g:message
											code="order.amount.label" default="Amount" /></label></td>
								<td valign="top"
									class="value ${hasErrors(bean: orderInstance, field: 'price', 'errors')}">
									<g:textField name="price"
										value="${formatNumber(number: orderInstance?.price, formatName: 'price.format')}"
										readonlyValue="${readonlyValue}" maxlength="14" />
								</td>
							</tr>
							
							<tr class="prop">
								<td valign="top" class="name"><label for="orderStatus"><g:message
											code="common.status" default="Status" /></label></td>
								<td valign="top"
									class="value ${hasErrors(bean: orderInstance, field: 'status', 'errors')}">
									<g:hiddenField name="paidStatus" value="${orderInstance?.status}"/>
									<g:select name="status" from="${['1', '2', '3']}"
										valueMessagePrefix="order.status"
										value="${orderInstance?.status }"
										readonlyValue="${readonlyValue}" />
									<g:if
										test="${orderInstance?.status == 2 }">
										<g:message code="order.paid.status" />
									</g:if>
									<g:else>
										<g:checkBox id="completeStatus" name="completeStatus" value=""
											readonlyValue="${readonlyValue}" />
											<g:message code="order.complete.process.label" />
									</g:else>
								</td>
							</tr>
							<tr class="prop">
								<td valign="top" class="name"><label for="quantity"><g:message
											code="order.quantity.label" default="Quantity" /></label></td>
								<td>
									<table class="dates">
										<tr>
											<td valign="top"
												class="value ${hasErrors(bean: orderInstance, field: 'adultNumber', 'errors')}">
												<g:hiddenField name="adultNumber" value="${orderInstance?.adultNumber}"/>
												<g:select name="adultNumberTemp"
													from="${['0','1','2','3','4','5']}"
													value="${orderInstance?.adultNumber}" style="width:50px"
													readonlyValue="${readonlyValue}" />
											</td>
											<td valign="top" class="name"><label for="adultNumber"><g:message
														code="order.adult.label" default="Adult" /></label></td>
											<td valign="top"
												class="value ${hasErrors(bean: orderInstance, field: 'kidNumber', 'errors')}">
												<g:hiddenField name="kidNumber" value="${orderInstance?.kidNumber}"/>
												<g:select name="kidNumberTemp"
													from="${['0','1','2','3','4','5']}"
													value="${orderInstance?.kidNumber}" style="width:50px"
													readonlyValue="${readonlyValue}" />
											</td>
											<td valign="top" class="name"><label for="kidNumber"><g:message
														code="order.kid.label" default="Kid" /></label></td>
											<td valign="top"
												class="value ${hasErrors(bean: orderInstance, field: 'payment', 'errors')}">
												<g:hiddenField name="infantNumber" value="${orderInstance?.infantNumber}"/>
												<g:select name="infantNumberTemp"
													from="${['0','1','2','3','4','5']}"
													value="${orderInstance?.infantNumber}" style="width:50px"
													readonlyValue="${readonlyValue}" />
											</td>
											<td valign="top" class="name"><label for="infantNumber"><g:message
														code="order.infant.label" default="Infant" /></label></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr class="prop">
								<td valign="top" class="name"><label for="specialRequire"><g:message
											code="order.special.require.label" default="Special require" /></label>
								</td>
								<td valign="top"
									class="value ${hasErrors(bean: orderInstance, field: 'specialRequire', 'errors')}">
									<g:textArea name="specialRequire"
										value="${orderInstance?.specialRequire}"
										readonlyValue="${readonlyValue}" maxlength="500" />
								</td>
							</tr>
							<tr class="prop">
								<td valign="top" class="name"><label for="payment"><g:message
											code="order.remark.label" default="Remark" /></label></td>
								<td valign="top">
									<table class="dates">
										<g:hiddenField name="remark.employee.id"
											value="${currentUser?.id}" />
										<g:hiddenField name="remark.order.id"
											value="${orderInstance?.id}" />
										<g:each in="${orderInstance?.remarks.sort{ it.id }}"
											var="remark">
											<g:if test="${remark?.content}">
												<tr>
													<g:hiddenField name="remark.id" value="${remark?.id}" />
													<td valign="top" class="value"><label for="orderTime">
															${remark?.content}
													</label></td>
													<td valign="top" class="value"><label for="orderTime"><g:message
																code="order.remark.added.label" default="Add by:" /> ${remark?.employee?.name}
															<g:formatDate formatName="date.time.format.short"
																date="${remark?.remarkDate }" /></label></td>
												</tr>
											</g:if>
										</g:each>
										<tr>
											<td valign="top"
												class="value ${hasErrors(bean: remark, field: 'content', 'errors')}">
												<g:textArea id="remark" name="remark.content" value=""
													readonlyValue="${readonlyValue}" maxlength="500" />
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr class="prop">
								<td valign="top" class="name"><label for="notification"><g:message
											code="order.notification.label" default="Notification" /></label></td>
								<td valign="top" class="name">
									<g:textField name="notification"
										value="${formatDate(formatName:'date.time.format.full',date:orderInstance?.orderEmployee?.notification)}"
										readonlyValue="${readonlyValue}" readonly="true"/>
								</td>
							</tr>
							<tr class="prop">
								<td valign="top" class="name"><label for="timeLimit"><g:message
											code="order.time.limit.label" default="Time limit" /></label></td>
								<td>
									<table class="dates">
										<tr>
											<td valign="top"
												class="value ${hasErrors(bean: orderEmployee, field: 'timeLimit', 'errors')}"
												style="width: 100px"><g:textField name="timeLimit"
													value="${formatDate(formatName:'date.time.format.full',date:orderInstance?.orderEmployee?.timeLimit)}"
													style="width:100px" readonlyValue="${readonlyValue}"
													/></td>
											<td valign="top" class="name" style="width: 50px"><label
												for="curencyCode"><g:message
														code="order.currency.code.lable" default="Currency code" /></label>
											</td>
											<td valign="top"
												class="value ${hasErrors(bean: orderEmployee, field: 'moneyCode', 'errors')}"
												style="width: 80px" colspan="2"><g:textField
													name="moneyCode"
													value="${orderInstance?.orderEmployee?.moneyCode}"
													style="width:80px" readonlyValue="${readonlyValue}"
													/></td>
										</tr>
									</table>
								</td>
							</tr>

							<tr class="prop">
								<td valign="top" class="name"><label for="payment"><g:message
											code="order.payment.label" default="Payment" /></label></td>
								<td valign="top"
									class="value ${hasErrors(bean: orderInstance, field: 'payment', 'errors')}">
									<g:select name="payment.id"
										from="${com.airgroup.domain.Payment.list()}" optionKey="id"
										value="${orderInstance?.payment?.id}" 
										onchange="${remoteFunction(action:'payment', controller:'order', update:'orderPayment', params:'\'id=\' + document.getElementById(\'code\').value + \'&payment=\' + this.value', options:[method:'GET'])}"
										readonlyValue="${readonlyValue}" />
								</td>
							</tr>
							
							<tr class="prop">
								<table id="orderPayment">
								<g:if test="${orderInstance?.payment?.id == 3}">
									<tr class="prop">
										<td valign="top" class="name"><label for="person"><g:message
													code="order.payment.person" default="Person" /></label></td>
										<td valign="top"
														class="value ${hasErrors(bean: orderPayment, field: 'person', 'errors')}"
														style="width: 80px" colspan="2"><g:textField
															name="paymentPerson"
															value="${orderPayment?.person}"
															style="width:150px" readonlyValue="${readonlyValue}"
															/></td>
										</tr>
									<tr class="prop">
										<td valign="top" class="name"><label for="address"><g:message
													code="order.payment.address" default="Address" /></label></td>
										<td valign="top"
														class="value ${hasErrors(bean: orderPayment, field: 'address', 'errors')}"
														style="width: 80px" colspan="2"><g:textField
															name="paymentAddress"
															value="${orderPayment?.address}"
															style="width:150px" readonlyValue="${readonlyValue}"
															/></td>
										</tr>
									<tr class="prop">
										<td valign="top" class="name"><label for="phoneNumber"><g:message
													code="order.payment.phoneNumber" default="Phone Number" /></label></td>
										<td valign="top"
														class="value ${hasErrors(bean: orderPayment, field: 'phoneNumber', 'errors')}"
														style="width: 80px" colspan="2"><g:textField
															name="paymentPhoneNumber"
															value="${orderPayment?.phoneNumber}"
															style="width:150px" readonlyValue="${readonlyValue}"
															/></td>
										</tr>
									<tr class="prop">
										<td valign="top" class="name"><label for="city"><g:message
													code="order.payment.city" default="City" /></label></td>
										<td valign="top"
														class="value ${hasErrors(bean: orderPayment, field: 'city', 'errors')}"
														style="width: 80px" colspan="2"><g:textField
															name="paymentCity"
															value="${orderPayment?.city}"
															style="width:150px" readonlyValue="${readonlyValue}"
															/></td>
										</tr>
								</g:if>
								</table>
							</tr>
							
							<tr class="prop">
								<td valign="top" class="name"><label for="payment"><g:message
											code="order.booking.label" default="Booking:" /></label></td>
								<td>
									<table class="dates">
										<tr>
											<td valign="top"
												class="value ${hasErrors(bean: orderInstance, field: 'price', 'errors')}">
												<label for="going"><g:message
														code="order.going.label" default="Going" /></label> <g:textArea
													name="goingDescription" id="going"
													value="${orderInstance?.goingDescription}"
													readonlyValue="${readonlyValue}" maxlength="2500" rows="25" cols="30"/>
											</td>
											<td valign="top"
												class="value ${hasErrors(bean: orderInstance, field: 'price', 'errors')}">
												<label for="return"><g:message
														code="order.return.label" default="Return" /></label> <g:textArea
													name="returnDescription" id="back"
													value="${orderInstance?.returnDescription}"
													readonlyValue="${readonlyValue}" maxlength="2500" rows="25" cols="30"/>
											</td>
										</tr>
									</table>
								</td>
							</tr>
							
						</tbody>
					</table>
				</fieldset>
			</div>
			<div style="clear: both"></div>
			<div class="buttons infight" id="actionButton">
				<span class="button"><g:actionSubmit class="save" id="back"
						action="list"
						value="${message(code: 'button.back', default: 'Back')}" /></span>
				<span id="sendmail">
					<modalbox:createLink controller="order" readonlyValue="${readonlyValue}"
									action="mailInfo"
									params="[orderId:orderInstance?.id]"
									title="Send mail" width="420">
						<g:message code="button.send.mail" />
					</modalbox:createLink>
				</span>	
				<span class="button">
					<g:if test="${currentUser?.id != orderInstance?.orderEmployee?.processEmp?.id && orderInstance?.orderEmployee?.processEmp?.id > 0 }">
						<g:actionSubmit class="save" id="addRemark"
						readonlyValue="${readonlyValue}" action="update"
						value="${message(code: 'button.update', default: 'Update')}" />
					</g:if>
					<g:else>
						<input type="button" class="save" id="update"
						readonlyValue="${readonlyValue}" 
						value="${message(code: 'button.update', default: 'Update')}" />
					</g:else>	
				</span>
			</div>
		</g:form>
	</div>
</body>
</html>
