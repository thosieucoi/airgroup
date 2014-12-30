
<%@ page import="org.weceem.auth.CMSUser"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="admin" />
<g:set var="entityName"
	value="${message(code: 'CMSUser.label', default: 'CMSUser')}" />
<title><g:message code="default.show.label" args="[entityName]" /></title>
</head>
<body>
	<div class="nav">
		<g:link action="list">
			<g:message code="user.info.employee.title" />
		</g:link>
		 &gt; ${CMSUserInstance?.name }
	</div>
	<div class="body">
		<g:if test="${flash.message}">
			<div class="message">
				${flash.message}
			</div>
		</g:if>
		<div class="dialog infors">
			<fieldset>
				<legend>
					<g:message code="user.info.employee.title" />
				</legend>
				<table>
					<tbody>
						<tr class="prop">
							<td valign="top" class="name"><g:message
									code="user.header.code" default="Code" /></td>

							<td valign="top" class="value">
								${fieldValue(bean: CMSUserInstance, field: "code")}
							</td>

						</tr>

						<tr class="prop">
							<td valign="top" class="name"><g:message
									code="user.header.name" default="Name" /></td>

							<td valign="top" class="value">
								${fieldValue(bean: CMSUserInstance, field: "name")}
							</td>

						</tr>

						<tr class="prop">
							<td valign="top" class="name"><g:message
									code="user.header.phone" default="Phone Number" /></td>

							<td valign="top" class="value">
								${fieldValue(bean: CMSUserInstance, field: "phoneNumber")}
							</td>

						</tr>

						<tr class="prop">
							<td valign="top" class="name"><g:message
									code="user.header.skype" default="Skype" /></td>

							<td valign="top" class="value">
								${fieldValue(bean: CMSUserInstance, field: "skype")}
							</td>

						</tr>

						<tr class="prop">
							<td valign="top" class="name"><g:message
									code="user.header.yahoo" default="Yahoo" /></td>

							<td valign="top" class="value">
								${fieldValue(bean: CMSUserInstance, field: "yahoo")}
							</td>

						</tr>

						<tr class="prop">
							<td valign="top" class="name"><g:message
									code="user.header.call.center" default="Call center" /></td>

							<td valign="top" style="text-align: left;" class="value"><g:checkBox
									name="callSenter" checked="${CMSUserInstance?.callCenterStatus > 0 }"
									disabled="true" /></td>
						</tr>
					</tbody>
				</table>
			</fieldset>
		</div>
		<div class="dialog right">
			<fieldset>
				<legend>
					<g:message code="order.info.title" />
				</legend>
				<table>
					<thead>
						<tr>
							<g:sortableColumn property="order" action="show"
								title="${message(code: 'order.code.label', default: 'Code')}" />
							<g:sortableColumn property="order"  action="show"
								title="${message(code: 'order.customer.label', default: 'Customer name')}" />
							<g:sortableColumn property="order"  action="show"
								title="${message(code: 'order.date.purchase.label', default: 'Purchase date')}" />
							<g:sortableColumn property="order"  action="show"
								title="${message(code: 'common.status', default: 'Status')}" />
						</tr>
					</thead>
					<tbody>
						<g:each in="${orderInstanceList}" status="i" var="orderEmp">
							<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
								<td>
									${orderEmp?.order?.id?.encodeAsHTML()}
								</td>
								<td>
									${orderEmp?.order?.customer?.name?.encodeAsHTML()}
								</td>
								<td>
									<g:formatDate formatName="date.time.format.full"
										date="${orderEmp?.order?.orderTime}" />
								</td>
								<td>
									<g:if test="${orderEmp?.order?.status == 1}">
	                            		<g:message code="order.status.1"/>
	                            	</g:if>
	                            	<g:elseif test="${orderEmp?.order?.status == 2}">
	                            		<g:message code="order.status.2"/>
	                            	</g:elseif>
	                            	<g:else>
	                            		<g:message code="order.status.3"/>
	                            	</g:else>
                            	</td>
							</tr>
						</g:each>
					</tbody>
				</table>
			</fieldset>
		</div>
		<div class="paginateButtons">
			<g:paginate action="show" total="${orderTotal}" params="[id: CMSUserInstance.id]"/>
		</div>
	</div>
</body>
</html>
