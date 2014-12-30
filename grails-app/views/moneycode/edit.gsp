
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script type="text/javascript" src="${resource(dir:'js', file:'moneyCode.js') }"></script>
<meta name="layout" content="admin" />
 <title><g:message code="title.advert" args="[entityName]" /></title>
</head>
<body>
	<div class="nav">
		<span class="menuButton"><a class="home"
			href="${createLink(uri: '/')}"><g:message
					code="default.home.label" /></a></span> <span class="menuButton"><g:link
				class="list" action="list">
				<g:message code="moneyCode.list.label" args="[entityName]" />
			</g:link></span>
	</div>
	<div class="body">
		<h1>
			<g:message code="moneyCode.edit.label" args="[entityName]" />
		</h1>
		<g:if test="${flash.message}">
			<div class="message">
				${flash.message}
			</div>
		</g:if>
		<g:hasErrors bean="${feedbackInstance}">
			<div class="errors">
				<g:renderErrors bean="${feedbackInstance}" as="list" />
			</div>
		</g:hasErrors>
		<g:form method="post" controller="moneycode" action="update" enctype="multipart/form-data">
		<g:hiddenField name="id" value="${moneyCodeId}" />
			<table id="avrtico">
					<tr id="img_slide">
						<td><label>Chọn file: </label></td>
						<td><input type="file" name="file" id="file" /></td>
					</tr>
					<tr>
						<td><label>Thời gian: </label></td>
						<td><g:formatDate formatName="date.time.format.short"
											date="${moneyCodeDate}" /></td>
					</tr>
			</table>
			<div class="buttons" style="width:500px; margin:0 auto; padding:30px 0 0 300px">
				<span class="button"><g:actionSubmit name="create" action="update"
						class="update"
						value="${message(code: 'default.button.create.label', default: 'Update')}" /></span>
			</div>
		</g:form>
	</div>
</body>
</html>
