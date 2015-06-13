<%@ page import="com.airgroup.domain.Advert"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="admin" />
 <title><g:message code="title.advert" args="[entityName]" /></title>
</head>
<body>
	<div class="nav">
		<span class="menuButton"><a class="home"
			href="${createLink(uri: '/')}"><g:message
					code="default.home.label" /></a></span> <span class="menuButton"><g:link
				class="list" action="list">
				<g:message code="advert.list.label" args="[entityName]" />
			</g:link></span>
	</div>
	<div class="body">
		<h1>
			<g:message code="advert.create.label" args="[entityName]" />
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
		<g:form method="post" controller="advert" action="save" enctype="multipart/form-data">
			<table id="avrtico">
					<tr id="img_slide">
						<td><label>Ảnh slide: </label></td>
						<td><input type="file" name="slidePic" id="slidePic" /></td>
					</tr>
					<tr id="img_slide">
						<td><label>Link quảng cáo: </label></td>
						<td><input type="text" name="linkAdvert" id="linkAdvert" /></td>
					</tr>
					<tr>
						<td></td>
						<td><lable>Bức ảnh có dung lượng tối đa là 2Mb</lable></td>
					</tr>
			</table>
			<div class="buttons" style="width:500px; margin:0 auto; padding:30px 0 0 300px">
				<span class="button"><g:actionSubmit name="create" action="save"
						class="save"
						value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
			</div>
		</g:form>
	</div>
</body>
</html>
