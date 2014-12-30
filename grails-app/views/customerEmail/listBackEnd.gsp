<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta name="layout" content="admin" />
		<title><g:message code="title.homepage" args="[entityName]" /></title>
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
		</style>
	</head>
	<body>
		<div class="body">
			<fieldset>
				<legend>Email</legend>
				<span>Email: &nbsp;</span>
				<textarea rows="10" cols="150" readonly="readonly" style="text-align: left;"><g:each in="${listEmail}" var="emailInstance">${emailInstance.email};</g:each>
				</textarea>
			</fieldset>
		</div>
	</body>
</html>
