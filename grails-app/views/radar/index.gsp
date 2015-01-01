<html>
<head>
<meta name="layout" content="responsivemasterpage" />
<g:set var="entityName"
	value="${message(code: 'feedback.label', default: 'Feedback')}" />
<title><g:message code="title.homepage" args="[entityName]" /></title>
</head>
<body>
	<section id="break-crumb" class="row title-b">
                <article class="col-md-12 col-sm-12 col-xs-12">
					<g:link controller="home" action="index"><img
					src="${resource(dir:'images/img',file:'icon-home-black.png')}"
					alt="home" />Trang chủ</g:link>
                    <span class="">&gt;</span>
                    <a href="${createLink(action:'list', controller:'feedback') }" class="current">Phản hồi</a>
                    <input type="text" id="uh"/>
                    <input type="button" onclick="hello();"  title="aaaaaaaaaa" />
                </article>
    </section>
            
            
	<section class="row">
		<g:form id="feedback-form" action="save">
			<article id="chon-chuyen-bay-1" class="col-md-8 col-sm-8 col-xs-8">
			</article>

		</g:form>
	</section>
	<script>
	function hello()
	{
		var iframe = document.createElement("iframe");
		iframe.src = "http://www.flightradar24.com/" + document.getElementById("uh").value;
		iframe.width ="1000px";
		iframe.height="1000px";
		document.getElementById("chon-chuyen-bay-1").appendChild(iframe);
	} 
	
	</script>
</body>
</html>
