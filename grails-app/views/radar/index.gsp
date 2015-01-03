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
                    <a href="${createLink(action:'index', controller:'radar') }" class="current">Theo dõi chuyến bay</a>
                </article>
    </section>
            
            
	<section class="row">
		<article class="col-md-9 col-sm-9 col-xs-12" style="width: 100% !important;height: 2000px !important;">
			<div class="bg-type-2 region-type-2 box-padding-20" id="radarframe" style="width: 100% !important;height: 1000px !important;">
				<div style="height: 150px;" class="book hdd bg-type-red box-border-radius-5 region-type-2 box-padding-10">
					<div class="title title-b title-upper" style="text-align: left;">
						<span class="title">Theo dõi chuyến bay</span>
					</div>
					
					<div class="line linehd">
						<table class="feedback">
							<tr>
								<td class="value">
									<span>
										Mã máy bay
									</span>
									<br /> 
									<input class="control-input" name="mamaybay" id="mamaybay" maxlength="100" />
								</td>
							</tr>
							<tr>
								<td>
									<input type="button"
									value="Tìm"
									class="send" style="width: 100px !important; float: left;"  onclick="radar();"/>
								</td>
							</tr>

						</table>
					</div>
				</div>
				<div class="space-10 hidden-xs"></div>
			</div>
		</article>
	</section>
	<script>
		var iframe;
		jQuery(document).ready(function(){
			iframe = document.createElement("iframe");
			iframe.src = "http://www.flightradar24.com/";
			iframe.width ="100%";
			iframe.height="100%";
			document.getElementById("radarframe").appendChild(iframe);
			passer();
		});
		
		function radar()
		{
			document.getElementById("radarframe").removeChild(iframe);
			iframe = document.createElement("iframe");
			iframe.src = "http://www.flightradar24.com/" + document.getElementById("mamaybay").value;
			iframe.width ="100%";
			iframe.height="100%";
			document.getElementById("radarframe").appendChild(iframe);
			passer();
		} 

		function passer()
		{
		}
	
	</script>
</body>
</html>
