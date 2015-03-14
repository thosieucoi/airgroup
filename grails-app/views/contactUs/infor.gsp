<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="com.airgroup.domain.Location"%>

<html>
<head>
<meta name="layout" content="responsivemasterpage" />
<title>Liên hệ để được phục vụ Vé máy bay, đại lý máy bay ahotua</title>
</head>
<body>
	<section id="break-crumb" class="row title-b">
		<article class="col-md-12 col-sm-12 col-xs-12">
			<g:link controller="home" action="index"><img
					src="${resource(dir:'images/img',file:'icon-home-black.png')}"
					alt="home" />Trang chủ</g:link>
			<span class="">&gt;</span> <a href="#" class="current">Liên Hệ</a>
		</article>
	</section>

	<section class="row">
		<article class="col-md-8 col-sm-8 col-xs-12">
			<div class="bg-type-2 region-type-2 box-padding-20">
				
				<g:if test="${contact != null }">
					<wcm:find id="${contact.id}" var="node">
						<div class="debook">
							${node.content}
						</div>
					</wcm:find>
				</g:if>
			</div>
		</article>

		<g:include view="layouts/responsivewebpart/_searchFlightForm.gsp" />
	</section>
</body>
</html>