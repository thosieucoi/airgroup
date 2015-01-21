<%@ page import="com.airgroup.domain.Tour"%>
<html>
<head>
<meta name="layout" content="responsivemasterpage" />
<g:set var="entityName"
	value="${message(code: 'Tour', default: 'Tour')}" />
	<title><g:message code="title.homepage" args="[entityName]" /></title>
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
						<g:link controller="information" action="index"  class="current">Du lịch</g:link>
				</article>
			</section>

			<section class="row">
				<article id="tour-details" class="col-md-12 col-sm-12 col-xs-12">
					<div class="bg-type-2">
						<div class="adv">
							<div class="title title-upper title-b">
								${bigCategory}
							</div>

							<div class="tour-box">
								<a class="title title-b">
									${tourDetail.name }
								</a>
								<div class="description">
									<div class="tour-time">
										${tourDetail.duration?"Thời gian:":"" }  ${tourDetail.duration}
									</div>
									<div>
										${tourDetail.startLocation?"Khởi hành:":"" }  ${tourDetail.startLocation}
									</div>
									<div>
										${tourDetail.price?"Giá:":""}
										${tourDetail.price }
									</div>
									<div>
										${tourDetail.phoneNumber?"Đặt tour:":""} <strong class="tour-phone">
											${tourDetail.phoneNumber }
										</strong>
									</div>
								</div>
							</div>
						</div>
						<div id="tour-details-content">
							${tourDetail.content }
						</div>
					</div>
				</article>
			</section>
		</div>
	</div>
</body>
</html>