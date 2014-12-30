<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="com.airgroup.domain.Location"%>
<html>
<head>
<meta name="layout" content="responsivemasterpage" />
<title><g:message code="title.homepage" args="[entityName]" /></title>
</head>
<body>
	<div id="tour-section" class="row">
		<section id="break-crumb" class="row title-b">
			<article class="col-md-12 col-sm-12 col-xs-12">
				<g:link controller="home" action="index"><img
					src="${resource(dir:'images/img',file:'icon-home-black.png')}"
					alt="home" />Trang chủ</g:link>
				<span class="">&gt;</span> <a href="#" class="current">Dịch vụ</a>
			</article>
		</section>
		<section class="row">
			<article class="col-md-9 col-sm-9 col-xs-9">
				<div class="bg-type-2 region-type-2 box-padding-20">
					<div class="title title-b">
						<span class="title">Điều kiện, thủ tục cần thiết khi bay</span>
					</div>
					<div class="line line-horizontal"></div>
					<div class="space-10"></div>

					<div class="debook">
						<g:if test="${policy != null }">
							<wcm:find id="${policy.id}" var="node">
								<div class="debook">
									${node.content}
								</div>
							</wcm:find>
						</g:if>
					</div>
				</div>
			</article>

			<g:include view="layouts/responsivewebpart/_searchFlightForm.gsp" />
		</section>
	</div>

</body>
</html>