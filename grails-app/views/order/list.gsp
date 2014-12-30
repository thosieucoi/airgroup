
<%@ page import="com.airgroup.domain.Order" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="admin" />
        <g:set var="entityName" value="${message(code: 'order.label', default: 'Order')}" />
        <title><g:message code="title.homepage" args="[entityName]" /></title>
        <g:javascript library="prototype"/>
    </head>
    <body>
        <div class="nav">
            <g:message	code="order.info.list.title" />
        </div>
        <div class="body">
            <g:if test="${flash.message}">
            	<div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${orderInstance}">
				<div class="errors">
					<g:renderErrors bean="${orderInstance}" as="list" />
				</div>
			</g:hasErrors>
				<div class="fitter">
					<g:if test="${fltype == '0'}">
						<span>Trạng thái</span><g:select id="status" value="${status}" name="order.status" from="${['1', '2', '4' ,'3']}" noSelection="['':'Tất cả']" valueMessagePrefix="order.status" 
						onchange="${remoteFunction(action:'filter', controller:'order', update:'list', params:'\'status=\' + this.value + \'&day=\' + document.getElementById(\'date\').value + \'&myorder=\' + document.getElementById(\'myorder\').value + \'&active=\' + document.getElementById(\'active\').value + \'&fltype=\' + \'0\'', options:[method:'GET'])}"/>
						
						<span>Thời gian</span><g:select id="date" value="${day}" name="order.date" from="${['0', '1', '2']}" noSelection="['':'Tất cả']" valueMessagePrefix="order.date" 
						onchange="${remoteFunction(action:'filter', controller:'order', update:'list', params:'\'status=\'+ document.getElementById(\'status\').value + \'&day=\' + this.value + \'&myorder=\' + document.getElementById(\'myorder\').value + \'&active=\' + document.getElementById(\'active\').value + \'&fltype=\' + \'0\'', options:[method:'GET'])}"/>
					<span>Trước</span>
					
					<input name="field" id="field"/><span>Ngày</span>
					<input id="find" type="submit" onclick="${remoteFunction(action:'filter', controller:'order', update:'list', params:'\'status=\'+ document.getElementById(\'status\').value + \'&day=\' + document.getElementById(\'field\').value + \'&myorder=\' + document.getElementById(\'myorder\').value + \'&active=\' + document.getElementById(\'active\').value + \'&fltype=\' + \'0\'', options:[method:'GET'])}" value="Tìm"/>
					
					<span>Đơn hàng của tôi</span><g:select id="myorder" value="${myorder}" name="order.myorder" from="${['0', '1', '2', '3']}" valueMessagePrefix="order.myorder"
						onchange="${remoteFunction(action:'filter', controller:'order', update:'list', params:'\'status=\' + document.getElementById(\'status\').value + \'&day=\' + document.getElementById(\'date\').value + \'&myorder=\' + this.value + \'&active=\' + document.getElementById(\'active\').value + \'&fltype=\' + \'0\'', options:[method:'GET'])}"/>
						
					<g:select id="active" value="${active}" name="order.active" from="${['1', '0']}" valueMessagePrefix="order.active" 
						onchange="${remoteFunction(action:'filter', controller:'order', update:'list', params:'\'status=\' + document.getElementById(\'status\').value + \'&day=\' + document.getElementById(\'date\').value + \'&myorder=\' + document.getElementById(\'myorder\').value + \'&active=\' + this.value + \'&fltype=\' + \'0\'', options:[method:'GET'])}"/>
					</g:if>
					
					<g:elseif test="${fltype == '1'}">
						<span>Trạng thái</span><g:select id="status" value="${status}" name="order.status" from="${['1', '2', '4' ,'3']}" noSelection="['':'Tất cả']" valueMessagePrefix="order.status" 
						onchange="${remoteFunction(action:'filter', controller:'order', update:'list', params:'\'status=\' + this.value + \'&day=\' + document.getElementById(\'date\').value + \'&myorder=\' + document.getElementById(\'myorder\').value + \'&active=\' + document.getElementById(\'active\').value + \'&fltype=\' + \'1\'', options:[method:'GET'])}"/>
						
						<span>Thời gian</span><g:select id="date" value="${day}" name="order.date" from="${['0', '1', '2']}" noSelection="['':'Tất cả']" valueMessagePrefix="order.date" 
						onchange="${remoteFunction(action:'filter', controller:'order', update:'list', params:'\'status=\'+ document.getElementById(\'status\').value + \'&day=\' + this.value + \'&myorder=\' + document.getElementById(\'myorder\').value + \'&active=\' + document.getElementById(\'active\').value + \'&fltype=\' + \'1\'', options:[method:'GET'])}"/>
					<span>Trước</span>
					
					<input name="field" id="field"/><span>Ngày</span>
					<input id="find" type="submit" onclick="${remoteFunction(action:'filter', controller:'order', update:'list', params:'\'status=\'+ document.getElementById(\'status\').value + \'&day=\' + document.getElementById(\'field\').value + \'&myorder=\' + document.getElementById(\'myorder\').value + \'&active=\' + document.getElementById(\'active\').value + \'&fltype=\' + \'1\'', options:[method:'GET'])}" value="Tìm"/>
					
					<span>Đơn hàng của tôi</span><g:select id="myorder" value="${myorder}" name="order.myorder" from="${['0', '1', '2', '3']}" valueMessagePrefix="order.myorder"
						onchange="${remoteFunction(action:'filter', controller:'order', update:'list', params:'\'status=\' + document.getElementById(\'status\').value + \'&day=\' + document.getElementById(\'date\').value + \'&myorder=\' + this.value + \'&active=\' + document.getElementById(\'active\').value + \'&fltype=\' + \'1\'', options:[method:'GET'])}"/>
						
					<g:select id="active" value="${active}" name="order.active" from="${['1', '0']}" valueMessagePrefix="order.active" 
						onchange="${remoteFunction(action:'filter', controller:'order', update:'list', params:'\'status=\' + document.getElementById(\'status\').value + \'&day=\' + document.getElementById(\'date\').value + \'&myorder=\' + document.getElementById(\'myorder\').value + \'&active=\' + this.value + \'&fltype=\' + \'1\'', options:[method:'GET'])}"/>
					</g:elseif>
					
					<g:else>
						<span>Trạng thái</span><g:select id="status" value="${status}" name="order.status" from="${['1', '2', '4' ,'3']}" noSelection="['':'Tất cả']" valueMessagePrefix="order.status" 
						onchange="${remoteFunction(action:'filter', controller:'order', update:'list', params:'\'status=\' + this.value + \'&day=\' + document.getElementById(\'date\').value + \'&myorder=\' + document.getElementById(\'myorder\').value + \'&active=\' + document.getElementById(\'active\').value', options:[method:'GET'])}"/>
						
						<span>Thời gian</span><g:select id="date" value="${day}" name="order.date" from="${['0', '1', '2']}" noSelection="['':'Tất cả']" valueMessagePrefix="order.date" 
						onchange="${remoteFunction(action:'filter', controller:'order', update:'list', params:'\'status=\'+ document.getElementById(\'status\').value + \'&day=\' + this.value + \'&myorder=\' + document.getElementById(\'myorder\').value + \'&active=\' + document.getElementById(\'active\').value', options:[method:'GET'])}"/>
					<span>Trước</span>
					
					<input name="field" id="field"/><span>Ngày</span>
					<input id="find" type="submit" onclick="${remoteFunction(action:'filter', controller:'order', update:'list', params:'\'status=\'+ document.getElementById(\'status\').value + \'&day=\' + document.getElementById(\'field\').value + \'&myorder=\' + document.getElementById(\'myorder\').value + \'&active=\' + document.getElementById(\'active\').value', options:[method:'GET'])}" value="Tìm"/>
					
					<span>Đơn hàng của tôi</span><g:select id="myorder" value="${myorder}" name="order.myorder" from="${['0', '1', '2', '3']}" valueMessagePrefix="order.myorder"
						onchange="${remoteFunction(action:'filter', controller:'order', update:'list', params:'\'status=\' + document.getElementById(\'status\').value + \'&day=\' + document.getElementById(\'date\').value + \'&myorder=\' + this.value + \'&active=\' + document.getElementById(\'active\').value', options:[method:'GET'])}"/>
						
					<g:select id="active" value="${active}" name="order.active" from="${['1', '0']}" valueMessagePrefix="order.active" 
						onchange="${remoteFunction(action:'filter', controller:'order', update:'list', params:'\'status=\' + document.getElementById(\'status\').value + \'&day=\' + document.getElementById(\'date\').value + \'&myorder=\' + document.getElementById(\'myorder\').value + \'&active=\' + this.value', options:[method:'GET'])}"/>
					</g:else>
					
					</div>
					

			<div id="list">
				<div style=" background: #007CC3;padding: 5px 0; text-align: center; width: 150px; margin-bottom:10px; ">
					<g:link id="domestic" action="list" params="[status: status, day: day, myorder:myorder, active:active, fltype:'0']" style="color:#fff; margin-right:10px">Nội địa</g:link>
					<g:link id="international" action="list" params="[status: status, day: day, myorder:myorder, active:active, fltype:'1']"  style="color:#fff;">Quốc tế</g:link></div>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" params="[status: status, day: day, myorder:myorder, active:active, fltype:fltype]" title="${message(code: 'order.code.label', default: 'Order code')}" />
                        
                            <g:sortableColumn property="customer" params="[status: status, day: day, myorder:myorder, active:active, fltype:fltype]" title="${message(code: 'order.customer.label', default: 'Customer name')}" />
                        
                            <g:sortableColumn property="price" params="[status: status, day: day, myorder:myorder, active:active, fltype:fltype]" title="${message(code: 'order.price.label', default: 'Price')}" />
                        
                            <g:sortableColumn property="orderTime" params="[status: status, day: day, myorder:myorder, active:active, fltype:fltype]" title="${message(code: 'order.date.purchase.label', default: 'Date purchase')}" />
                        
                            <g:sortableColumn property="status" params="[status: status, day: day, myorder:myorder, active:active, fltype:fltype]" title="${message(code: 'common.status', default: 'Status')}" />
                        
                            <th style="width:220px;color:#000099"><g:message code="user.header.actions"/></th>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${orderInstanceList}" status="i" var="orderInstance">
	                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
	                        	
	                            <td><g:link action="edit" id="${orderInstance.id}" params="[user: currentUser.id]">${fieldValue(bean: orderInstance, field: "id")}</g:link></td>
	                        
	                            <td>${orderInstance?.customer?.name}</td>
	                        
	                            <!-- <td>${fieldValue(bean: orderInstance, field: "price")}</td> -->
	                             <td><g:formatNumber formatName="price.format"
										number="${orderInstance.price}" /></td>
	                        
	                            <td>
	                            
	                        
	                            <td><g:formatDate formatName="date.time.format.full"
										date="${orderInstance.orderTime}" /></td>
	                        
	                            <td>
	                            	<g:if test="${orderInstance.status == 1}">
	                            		<g:message code="order.status.1"/>
	                            	</g:if>
	                            	<g:elseif test="${orderInstance.status == 2}">
	                            		<span style="background-color: #2E64FE; color: white; font-weight: bold;"><g:message code="order.status.2"/></span>
	                            	</g:elseif>
	                            	<g:else>
	                            		<g:message code="order.status.3"/>
	                            	</g:else>
	                            </td>
	                        
	                            <td>
		                            <g:if test="${orderInstance?.status == 2}">
										<g:message code="order.completed.label" />
									</g:if>
									<g:else>
		                            	<g:if test="${orderInstance?.orderEmployee?.status == 1 && orderInstance?.orderEmployee?.processEmp?.id == currentUser.id}">
		                            		<g:link class="button-pro" action="processCancel" params="[user: currentUser.id, orderEmpId:orderInstance?.orderEmployee?.id, type:'list', status: status, day: day, myorder:myorder, active:active, fltype:fltype]"><g:message code="order.processing.cancel.label"/></g:link>
		                            	</g:if>
		                            	<g:elseif test="${orderInstance?.orderEmployee?.status == 1 && orderInstance?.orderEmployee?.processEmp?.id}">
		                            		<p class="button-pro"><g:message code="order.processing.label"/></p>
		                            	</g:elseif>
		                            	<g:else>
			                            	<g:if test="${orderInstance?.orderEmployee?.status == null || orderInstance?.orderEmployee?.status == 0}">
			                            		<g:link class="button-pro" action="book" params="[user: currentUser.id, orderEmpId:orderInstance?.orderEmployee?.id, orderId: orderInstance.id, type:'list', status: status, day: day, myorder:myorder, active:active, fltype:fltype]"><g:message code="order.booking.label"/></g:link> 
			                            	</g:if>
			                            	<g:elseif test="${orderInstance?.orderEmployee?.status == 1 && orderInstance?.orderEmployee?.bookEmp?.id == currentUser.id}">
			                            		<g:link class="button-pro" action="bookCancel" params="[user: currentUser?.id, orderEmpId:orderInstance?.orderEmployee?.id, orderId: orderInstance.id, type:'list', status: status, day: day, myorder:myorder, active:active, fltype:fltype]"><g:message code="order.booking.cancel.label"/></g:link> 
			                            	</g:elseif>
			                            	<g:else>
			                            		<p class="button-pro"><g:message code="order.booked.label"/></p>
			                            	</g:else>
			                            	
			                            	<g:if test="${orderInstance?.orderEmployee?.status == 1 && orderInstance?.orderEmployee?.processEmp?.id == null}">
			                            		<g:link class="button-pro" action="process" id="${orderInstance.id}" params="[user: currentUser.id, orderEmpId:orderInstance?.orderEmployee?.id, type:'list', status: status, day: day, myorder:myorder, active:active, fltype:fltype]"><g:message code="order.process.label"/></g:link>
			                            	</g:if>
			                            	<g:else>
			                            		<p class="button-pro"><g:message code="order.process.label"/></p>
			                            	</g:else>
		                            	</g:else>
	                            	</g:else>
	                            </td>
	                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate params="[status: status, day: day, myorder:myorder, active:active, fltype:fltype]" total="${orderInstanceTotal}" />
            </div>
            </div>
        </div>
        <script type="text/javascript">
        (function($) {			
        	$("#find").click(function(){
        		$("#date").val("");
            });
            
	    	$(document).ready(function() {
	        	$("#field").keydown(function(event) {
	            // Allow: backspace, delete, tab, escape, and enter
	            if ( event.keyCode == 46 || event.keyCode == 8 || event.keyCode == 9 || event.keyCode == 27 || event.keyCode == 13 || 
	                 // Allow: Ctrl+A
	                (event.keyCode == 65 && event.ctrlKey === true) || 
	                 // Allow: home, end, left, right
	                (event.keyCode >= 35 && event.keyCode <= 39)) {
	                     // let it happen, don't do anything
	                     return;
	            }
	            else {
	                // Ensure that it is a number and stop the keypress
	                if (event.shiftKey || (event.keyCode < 48 || event.keyCode > 57) && (event.keyCode < 96 || event.keyCode > 105 )) {
	                    event.preventDefault(); 
	                }   
	            }
	        });
   	});
        })(jQuery);
    </script>
    </body>
</html>
