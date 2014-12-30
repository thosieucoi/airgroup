<div style="height:200px;">
	<g:form name="forwardForm" >
	  	<g:select name="user" from="${employees}" optionKey="id" optionValue="name"/>
	  	<g:hiddenField name="orderEmpId" value="${currentOrder?.id }"/>
		<g:hiddenField name="type" value="form"/>							
		<g:actionSubmit action="process" value="${message(code: 'button.forward.order')}"/>							
	</g:form>	
</div>