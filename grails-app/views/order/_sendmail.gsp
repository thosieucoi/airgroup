<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <title>Send mail</title>
  	<style>
		.send_mail{ background:#D9D9D9; width:400px; min-height:300px; max-height: 500px; overflow-y: scroll;
		border-radius:5px; box-shadow:3px #333333; border:1px solid #999}
		.send_mail h1{ font-size:20px; text-align:center; background:#484848; padding:10px 0; margin:0;border-radius:5px 5px 0 0; color:#fff}
		.fom_mail{ width:95%; margin:20px 10px}
		.fom_mail tr td{ padding:5px}
		.fom_mail tr td a{ text-decoration:none; color:#333;}
		.fom_mail tr td span{ font-size:20px; color:#007CC3}
		.error{color:red;}
	</style>
	<g:javascript>
			
		jQuery( document ).ready(function() {
			jQuery(".error").hide();
			jQuery("#send").on('click', function(){
				var $reservationCode = jQuery("#reservationCode");
				//var reg = /^[a-zA-Z0-9]*$/;
				//!reg.test($reservationCode.val())
				if ($reservationCode.val() == '') {
				    jQuery(".error").html('Mã đặt vé không hợp lệ!');
				    jQuery(".error").show();
				    return false;
				}
				jQuery("#send_mail").submit();
			});
		});
	</g:javascript>
</head>
<body>
    <div class="send_mail">
    	<g:form name="send_mail">
	    	<h1><g:message code="order.mail.information.title"/></h1>
	        <table class="fom_mail">
	        	<g:hiddenField name="orderId" value="${order?.id }"/>
	        	<g:hiddenField name="customerEmail" value="${mail}"/>
	        	<tr>
		        	<td colspan="2">
		        		<p class="error"></p>
		        	</td>
	        	</tr>
	        	<tr>
	            	<td><label><g:message code="order.reservation.code"/></label></td>
	                <td><g:textField value="${order?.reservationCode}" name="reservationCode" size="30px" maxlength="30"/></td>
	            </tr>
	            <tr><td colspan="2" style="border-bottom:1px dotted #999"></td></tr>
	            <tr><td></td></tr>
	            <tr>
	            	<td colspan="2"><span><g:message code="order.number.seat.passenger"/></span></td>
	            </tr>
	            <tr><td></td></tr>
	            <g:each in="${passengers}" status="i" var="passenger">
		            <tr>
		            	<td><b>${passenger?.name}</b></td>
		            </tr>
		            <tr>
	            		<td>Chiều đi:</td>
	            	</tr>
		            <tr>
		            	<td><g:message code="order.number.seat" default="Seat number"/></td>
		                <td><g:textField value="${oseats[i]}" name="outBoundSeatNumber.${passenger?.id}.number" size="30px" maxlength="20"/></td>
		            </tr>
		            <tr>
		            	<td><g:message code="order.number.ticket" default="Ticket number"/></td>
		                <td><g:textField value="${otickets[i]}" name="outBoundTicketNumber.${passenger?.id}.number" size="30px" maxlength="20"/></td>
		            </tr>
		            <g:if test="${tripType.size() > 0}">
			            <tr>
			            	<td>Chiều về:</td>
			            </tr>
			            <tr>
			            	<td><g:message code="order.number.seat" default="Seat number"/></td>
			                <td><g:textField value="${iseats[i]}" name="inBoundSeatNumber.${passenger?.id}.number" size="30px" maxlength="20"/></td>
			            </tr>
			            <tr>
		            	<td><g:message code="order.number.ticket" default="Ticket number"/></td>
		                <td><g:textField value="${itickets[i]}" name="inBoundTicketNumber.${passenger?.id}.number" size="30px" maxlength="20"/></td>
		            </tr>
		            </g:if>
	            	<tr><td></td></tr>
	            </g:each>
	            <tr>
	            	<td></td>
	            	<td>
	            		<g:actionSubmit action="saveMail" value="Save" id="save"/>
	            		<g:actionSubmit action="sendMail" value="Send" id="send" />
	            	</td>
	            	<td></td>
	            </tr>
	        </table>
        </g:form>
    </div>
</body>
</html>
