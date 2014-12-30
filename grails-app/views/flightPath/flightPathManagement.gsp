<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta name="layout" content="admin" />
		<title><g:message code="title.homepage" args="[entityName]" /></title>
		<script src="${resource(dir:'js',file:'flight-path.js')}"></script>
		<style type="text/css">
			.body fieldset {
				margin: 5px;
			}
			
			.body span{
				float: left;
				padding: 6px 15px 0 0;
				font: bold;
				font-size: 12px;
			}
			
			span.button{
				padding: 5px;
			}
			
			.errorFlightPathInp{
				background-color: red !important;
			}
		</style>
	</head>
	<body>
		<div class="nav">
			<span class="menuButton"><g:message code="navigation.admin.content"/></span>
			>
			<g:link controller="flightPath" action="list"><g:message code="flight.path.label.header"/></g:link>
		</div>
		<div class="body">
			<h1 class="custom-weceem-h1"><g:message code="flight.path.label.header"/></h1>
			<g:form name="addFlightPathForm" controller="flightPath" action="save" method="post">
				<fieldset>
					<legend><g:message code="flight.path.label.header"/></legend>
					<g:if test="${lstFlightPath!=null && !lstFlightPath.isEmpty()}">
						<%-- Domestic Flight Path --%>
						<div><g:message code="flight.path.label.header.domestic"/></div>
						<div>
							<table class="tblFlightPath">
								<tr>
									<g:if test="${lstFlightPath.get(0).domesticFlightPath1.length() == endIndex}">
										<td class="addLabel"><g:message code="flight.path.label.from"/>:</td>
										<td><input maxlength="3" class="departure" name="domesticDeparture1" type="text" value="${lstFlightPath.get(0).domesticFlightPath1.substring(departureIndex, endIndex)}"/></td>
										<td class="addLabel"><g:message code="flight.path.label.to"/>:</td>
										<td><input maxlength="3" class="destination" name="domesticDestination1" type="text" value="${lstFlightPath.get(0).domesticFlightPath1.substring(0, destinationIndex)}"/></td>
									</g:if>
									<g:else>
										<td class="addLabel"><g:message code="flight.path.label.from"/>:</td> <td><input maxlength="3" class="departure" name="domesticDeparture1" type="text"/></td>
										<td class="addLabel"><g:message code="flight.path.label.to"/>:</td> <td><input maxlength="3" class="destination" name="domesticDestination1" type="text"/></td>
									</g:else>
								</tr>
								<tr>
									<g:if test="${lstFlightPath.get(0).domesticFlightPath2.length() == endIndex}">
									<td class="addLabel"><g:message code="flight.path.label.from"/>:</td>
										<td><input maxlength="3" class="departure" name="domesticDeparture2" type="text" value="${lstFlightPath.get(0).domesticFlightPath2.substring(departureIndex, endIndex)}"/></td>
										<td class="addLabel"><g:message code="flight.path.label.to"/>:</td>
										<td><input maxlength="3" class="destination" name="domesticDestination2" type="text" value="${lstFlightPath.get(0).domesticFlightPath2.substring(0, destinationIndex)}"/></td>
									</g:if>
									<g:else>
										<td class="addLabel"><g:message code="flight.path.label.from"/>:</td> <td><input maxlength="3" class="departure" name="domesticDeparture2" type="text"/></td>
										<td class="addLabel"><g:message code="flight.path.label.to"/>:</td> <td><input maxlength="3" class="destination" name="domesticDestination2" type="text"/></td>
									</g:else>
								</tr>
								<tr>
									<g:if test="${lstFlightPath.get(0).domesticFlightPath3.length() == endIndex}">
										<td class="addLabel"><g:message code="flight.path.label.from"/>:</td>
										<td><input maxlength="3" class="departure" name="domesticDeparture3" type="text" value="${lstFlightPath.get(0).domesticFlightPath3.substring(departureIndex, endIndex)}"/></td>
										<td class="addLabel"><g:message code="flight.path.label.to"/>:</td>
										<td><input maxlength="3" class="destination" name="domesticDestination3" type="text" value="${lstFlightPath.get(0).domesticFlightPath3.substring(0, destinationIndex)}"/></td>
									</g:if>
									<g:else>
										<td class="addLabel"><g:message code="flight.path.label.from"/>:</td> <td><input maxlength="3" class="departure" name="domesticDeparture3" type="text"/></td>
										<td class="addLabel"><g:message code="flight.path.label.to"/>:</td> <td><input maxlength="3" class="destination" name="domesticDestination3" type="text"/></td>
									</g:else>
								</tr>
								<tr>
									<g:if test="${lstFlightPath.get(0).domesticFlightPath4.length() == endIndex}">
										<td class="addLabel"><g:message code="flight.path.label.from"/>:</td>
										<td><input maxlength="3" class="departure" name="domesticDeparture4" type="text" value="${lstFlightPath.get(0).domesticFlightPath4.substring(departureIndex, endIndex)}"/></td>
										<td class="addLabel"><g:message code="flight.path.label.to"/>:</td>
										<td><input maxlength="3" class="destination" name="domesticDestination4" type="text" value="${lstFlightPath.get(0).domesticFlightPath4.substring(0, destinationIndex)}"/></td>
									</g:if>
									<g:else>
										<td class="addLabel"><g:message code="flight.path.label.from"/>:</td> <td><input maxlength="3" class="departure" name="domesticDeparture4" type="text"/></td>
										<td class="addLabel"><g:message code="flight.path.label.to"/>:</td> <td><input maxlength="3" class="destination" name="domesticDestination4" type="text"/></td>
									</g:else>
								</tr>
								<tr>
									<g:if test="${lstFlightPath.get(0).domesticFlightPath5.length() == endIndex}">
										<td class="addLabel"><g:message code="flight.path.label.from"/>:</td>
										<td><input maxlength="3" class="departure" name="domesticDeparture5" type="text" value="${lstFlightPath.get(0).domesticFlightPath5.substring(departureIndex, endIndex)}"/></td>
										<td class="addLabel"><g:message code="flight.path.label.to"/>:</td>
										<td><input maxlength="3" class="destination" name="domesticDestination5" type="text" value="${lstFlightPath.get(0).domesticFlightPath5.substring(0, destinationIndex)}"/></td>
									</g:if>
									<g:else>
										<td class="addLabel"><g:message code="flight.path.label.from"/>:</td> <td><input maxlength="3" class="departure" name="domesticDeparture5" type="text"/></td>
										<td class="addLabel"><g:message code="flight.path.label.to"/>:</td> <td><input maxlength="3" class="destination" name="domesticDestination5" type="text"/></td>
									</g:else>
								</tr>
							</table>
						</div>
						
						<%-- International Flight Path --%>
						<div><g:message code="flight.path.label.header.international"/></div>
						<div>
							<table class="tblFlightPath">
								<tr>
									<g:if test="${lstFlightPath.get(0).internationalFlightPath1.length() == endIndex}">
										<td class="addLabel"><g:message code="flight.path.label.from"/>:</td>
										<td><input maxlength="3" class="departure" name="internationalDeparture1" type="text" value="${lstFlightPath.get(0).internationalFlightPath1.substring(departureIndex, endIndex)}"/></td>
										<td class="addLabel"><g:message code="flight.path.label.to"/>:</td>
										<td><input maxlength="3" class="destination" name="internationalDestination1" type="text" value="${lstFlightPath.get(0).internationalFlightPath1.substring(0, destinationIndex)}"/></td>
									</g:if>
									<g:else>
										<td class="addLabel"><g:message code="flight.path.label.from"/>:</td> <td><input maxlength="3" class="departure" name="internationalDeparture1" type="text"/></td>
										<td class="addLabel"><g:message code="flight.path.label.to"/>:</td> <td><input maxlength="3" class="destination" name="internationalDestination1" type="text"/></td>
									</g:else>
								</tr>
								<tr>
									<g:if test="${lstFlightPath.get(0).internationalFlightPath2.length() == endIndex}">
										<td class="addLabel"><g:message code="flight.path.label.from"/>:</td>
										<td><input maxlength="3" class="departure" name="internationalDeparture2" type="text" value="${lstFlightPath.get(0).internationalFlightPath2.substring(departureIndex, endIndex)}"/></td>
										<td class="addLabel"><g:message code="flight.path.label.to"/>:</td>
										<td><input maxlength="3" class="destination" name="internationalDestination2" type="text" value="${lstFlightPath.get(0).internationalFlightPath2.substring(0, destinationIndex)}"/></td>
									</g:if>
									<g:else>
										<td class="addLabel"><g:message code="flight.path.label.from"/>:</td> <td><input maxlength="3" class="departure" name="internationalDeparture2" type="text"/></td>
										<td class="addLabel"><g:message code="flight.path.label.to"/>:</td>	<td><input maxlength="3" class="destination" name="internationalDestination2" type="text"/></td>
									</g:else>
								</tr>
								<tr>
									<g:if test="${lstFlightPath.get(0).internationalFlightPath3.length() == endIndex}">
										<td class="addLabel"><g:message code="flight.path.label.from"/>:</td>
										<td><input maxlength="3" class="departure" name="internationalDeparture3" type="text" value="${lstFlightPath.get(0).internationalFlightPath3.substring(departureIndex, endIndex)}"/></td>
										<td class="addLabel"><g:message code="flight.path.label.to"/>:</td>
										<td><input maxlength="3" class="destination" name="internationalDestination3" type="text" value="${lstFlightPath.get(0).internationalFlightPath3.substring(0, destinationIndex)}"/></td>
									</g:if>
									<g:else>
										<td class="addLabel"><g:message code="flight.path.label.from"/>:</td> <td><input maxlength="3" class="departure" name="internationalDeparture3" type="text"/></td>
										<td class="addLabel"><g:message code="flight.path.label.to"/>:</td> <td><input maxlength="3" class="destination" name="internationalDestination3" type="text"/></td>
									</g:else>
								</tr>
								<tr>
									<g:if test="${lstFlightPath.get(0).internationalFlightPath4.length() == endIndex}">
										<td class="addLabel"><g:message code="flight.path.label.from"/>:</td>
										<td><input maxlength="3" class="departure" name="internationalDeparture4" type="text" value="${lstFlightPath.get(0).internationalFlightPath4.substring(departureIndex, endIndex)}"/></td>
										<td class="addLabel"><g:message code="flight.path.label.to"/>:</td>
										<td><input maxlength="3" class="destination" name="internationalDestination4" type="text" value="${lstFlightPath.get(0).internationalFlightPath4.substring(0, destinationIndex)}"/></td>
									</g:if>
									<g:else>
										<td class="addLabel"><g:message code="flight.path.label.from"/>:</td> <td><input maxlength="3" class="departure" name="internationalDeparture4" type="text"/></td>
										<td class="addLabel"><g:message code="flight.path.label.to"/>:</td> <td><input maxlength="3" class="destination" name="internationalDestination4" type="text"/></td>
									</g:else>
								</tr>
								<tr>
									<g:if test="${lstFlightPath.get(0).internationalFlightPath5.length() == endIndex}">
										<td class="addLabel"><g:message code="flight.path.label.from"/>:</td>
										<td><input maxlength="3" class="departure" name="internationalDeparture5" type="text" value="${lstFlightPath.get(0).internationalFlightPath5.substring(departureIndex, endIndex)}"/></td>
										<td class="addLabel"><g:message code="flight.path.label.to"/>:</td>
										<td><input maxlength="3" class="destination" name="internationalDestination5" type="text" value="${lstFlightPath.get(0).internationalFlightPath5.substring(0, destinationIndex)}"/></td>
									</g:if>
									<g:else>
										<td class="addLabel"><g:message code="flight.path.label.from"/>:</td> <td><input maxlength="3" class="departure" name="internationalDeparture5" type="text"/></td>
										<td class="addLabel"><g:message code="flight.path.label.to"/>:</td> <td><input maxlength="3" class="destination" name="internationalDestination5" type="text"/></td>
									</g:else>
								</tr>
							</table>
						</div>
					</g:if>
					
					<%-- If there are no flight path in DB --%>
					<g:else>
						<%-- Domestic Flight Path --%>
						<div><g:message code="flight.path.label.header.domestic"/></div>
						<div>
							<table class="tblFlightPath">
								<tr>
									<td class="addLabel"><g:message code="flight.path.label.from"/>:</td> <td><input maxlength="3" class="departure" name="domesticDeparture1" type="text"/></td>
									<td class="addLabel"><g:message code="flight.path.label.to"/>:</td> <td><input maxlength="3" class="destination" name="domesticDestination1" type="text"/></td>
								</tr>
								<tr>
									<td class="addLabel"><g:message code="flight.path.label.from"/>:</td> <td><input maxlength="3" class="departure" name="domesticDeparture2" type="text"/></td>
									<td class="addLabel"><g:message code="flight.path.label.to"/>:</td> <td><input maxlength="3" class="destination" name="domesticDestination2" type="text"/></td>
								</tr>
								<tr>
									<td class="addLabel"><g:message code="flight.path.label.from"/>:</td> <td><input maxlength="3" class="departure" name="domesticDeparture3" type="text"/></td>
									<td class="addLabel"><g:message code="flight.path.label.to"/>:</td> <td><input maxlength="3" class="destination" name="domesticDestination3" type="text"/></td>
								</tr>
								<tr>
									<td class="addLabel"><g:message code="flight.path.label.from"/>:</td> <td><input maxlength="3" class="departure" name="domesticDeparture4" type="text"/></td>
									<td class="addLabel"><g:message code="flight.path.label.to"/>:</td> <td><input maxlength="3" class="destination" name="domesticDestination4" type="text"/></td>
								</tr>
								<tr>
									<td class="addLabel"><g:message code="flight.path.label.from"/>:</td> <td><input maxlength="3" class="departure" name="domesticDeparture5" type="text"/></td>
									<td class="addLabel"><g:message code="flight.path.label.to"/>:</td> <td><input maxlength="3" class="destination" name="domesticDestination5" type="text"/></td>
								</tr>
							</table>
						</div>
						
						<%-- International Flight Path --%>
						<div><g:message code="flight.path.label.header.international"/></div>
						<div>
							<table class="tblFlightPath">
								<tr>
									<td class="addLabel"><g:message code="flight.path.label.from"/>:</td> <td><input maxlength="3" class="departure" name="internationalDeparture1" type="text"/></td>
									<td class="addLabel"><g:message code="flight.path.label.to"/>:</td> <td><input maxlength="3" class="destination" name="internationalDestination1" type="text"/></td>
								</tr>
								<tr>
									<td class="addLabel"><g:message code="flight.path.label.from"/>:</td> <td><input maxlength="3" class="departure" name="internationalDeparture2" type="text"/></td>
									<td class="addLabel"><g:message code="flight.path.label.to"/>:</td>	<td><input maxlength="3" class="destination" name="internationalDestination2" type="text"/></td>
								</tr>
								<tr>
									<td class="addLabel"><g:message code="flight.path.label.from"/>:</td> <td><input maxlength="3" class="departure" name="internationalDeparture3" type="text"/></td>
									<td class="addLabel"><g:message code="flight.path.label.to"/>:</td> <td><input maxlength="3" class="destination" name="internationalDestination3" type="text"/></td>
								</tr>
								<tr>
									<td class="addLabel"><g:message code="flight.path.label.from"/>:</td> <td><input maxlength="3" class="departure" name="internationalDeparture4" type="text"/></td>
									<td class="addLabel"><g:message code="flight.path.label.to"/>:</td> <td><input maxlength="3" class="destination" name="internationalDestination4" type="text"/></td>
								</tr>
								<tr>
									<td class="addLabel"><g:message code="flight.path.label.from"/>:</td> <td><input maxlength="3" class="departure" name="internationalDeparture5" type="text"/></td>
									<td class="addLabel"><g:message code="flight.path.label.to"/>:</td> <td><input maxlength="3" class="destination" name="internationalDestination5" type="text"/></td>
								</tr>
							</table>
						</div>
					</g:else>
				</fieldset>
				<div class="buttons infight" style="width: auto;">
					<span class="button"><input type="button" onclick="submitForm()" value="Save"/></span>
					<span class="button"><input type="button" onclick="cancel()" value="Cancel"></span>
				</div>
			</g:form>
		</div>
	</body>
</html>
