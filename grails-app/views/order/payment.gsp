<g:if test="${selectedPayment == '3'}">
									<tr class="prop">
										<td valign="top" class="name"><label for="person"><g:message
													code="order.payment.person" default="Person" /></label></td>
										<td valign="top"
														class="value ${hasErrors(bean: orderPayment, field: 'person', 'errors')}"
														style="width: 80px" colspan="2"><g:textField
															name="paymentPerson"
															value="${orderPayment?.person}"
															style="width:150px"
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
															style="width:150px"
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
															style="width:150px"
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
															style="width:150px"
															/></td>
										</tr>
								</g:if>