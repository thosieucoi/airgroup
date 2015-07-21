<%@ page import="com.airgroup.domain.Tour"%>
<html>
<head>
<meta name="layout" content="responsivemasterpage" />
<g:set var="entityName"
	value="${message(code: 'Tour', default: 'Tour')}" />
	<title>${detail.title} Vé máy bay, đại lý máy bay ahotua</title>
</head>
<body class="">
	<div id="tour-page">
		<div id="tour-section" class="row">
			<section id="break-crumb" class="row title-b">
				<article class="col-md-12 col-sm-12 col-xs-12">
				<g:link controller="home" action="index"><img
					src="${resource(dir:'images/img',file:'icon-home-black.png')}"
					alt="home" />Trang chủ</g:link>
					<span class="">&gt;</span>
                    <g:link controller="information">Thông tin tiện ích</g:link>
				</article>
			</section>

			<section class="row">
				<article id="tour-details" class="col-md-12 col-sm-12 col-xs-12">
					<div class="bg-type-2">
						<div class="adv">
							<div class="title title-upper title-b">
								${detail.title }
							</div>

							<div class="tour-box">
								<div class="description">
									<div class="tour-time">
										${detail.introduction}
									</div>
								</div>
							</div>
						</div>
						<div id="tour-details-content">
							${detail.content }
						</div>
					</div>
					
					<div class="space-40"></div>
				</article>
			</section>
		</div>
	</div>
</body>
</html>