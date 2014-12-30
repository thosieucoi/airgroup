
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
			<g:message code="moneyCode.create.label" args="[entityName]" />
		</h1>
		<g:if test="${flash.message}">
			<div class="message">
				${flash.message}
			</div>
		</g:if>
		<g:form  method="POST" action="save" onSubmit="return myFunction()" enctype="multipart/form-data">
			<table id="avrtico">
					<tr id="img_slide">
						<td><label>Chọn file: </label></td>
						<td><input type="file" name="file" id="file" /></td>
					</tr>
					<tr>
						<td><label>Chọn ngày: </label></td>
						<td><input id="date" readonly="readonly" type="text" name="date" onchange="changeFromDate()" style="margin:0 10px 0 0;"/></td>
					</tr>
			</table>
			<div class="buttons" style="width:500px; margin:0 auto; padding:30px 0 0 300px">
				<span class="button">
					<input type="submit" class="hideOrder" name="Lưu" value="Lưu"/>
				</span>
			</div>
		</g:form>
	</div>
		 <script>
		 function myFunction(){
			 var flag=false
			 var date = jQuery("#date").val()
			 
				jQuery.ajax({
					async : false,
					type:"POST",
					url : "/moneycode/fileExist",
					data: {'getDate' : jQuery("#date").val()},
					success: function(responseData){
						if(responseData=="true"){
							flag=true;
						}else{
							var r=confirm("Ngày này đã có tập tin được tải lên, bạn có muốn xóa tập tin cũ và tải tập tin mới lên không?");
							if (r==true)
							{
								flag=true
							}
							else
							{
								flag=false
							}
						}
					}
				 })
			return flag
			}
		</script>
</body>
</html>
