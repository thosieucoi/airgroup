<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="com.airgroup.domain.Location"%>

<html>
<head>
<meta name="layout" content="responsivemasterpage" />
<title><g:message code="title.homepage" args="[entityName]" /></title>
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
		<article class="col-md-9 col-sm-9 col-xs-9">
			<div class="bg-type-2 region-type-2 box-padding-20">
				<div class="title title-b">
					<span class="title">Thông tin liên hệ</span>
				</div>
				<div class="line line-horizontal"></div>
				<div class="space-10"></div>
				<g:if test="${contact != null }">
					<wcm:find id="${contact.id}" var="node">
						<div>
							<strong>Địa chỉ:</strong>
							${node.address}
							<br /> <strong>Hotline:</strong>
							${node.hotline}
							<br /> <strong>Skype:</strong><a href="skype:${node.skype}?chat">
								${node.skype}
							</a> <br /> <strong>Yahoo:</strong> <a
								href="ymsgr:sendIM?${node.yahoo}"> ${node.yahoo}</a> <br /> <strong>Email:</strong>
							<a href="mailto:${node.yahoo}"> ${node.email}</a> <br /> <br />
							<br />
						</div>
					</wcm:find>
				</g:if>
				<div class="title title-b">
					<span class="title">Thông tin</span>
				</div>
				<div class="line line-horizontal"></div>
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