<%@ page import="org.apache.commons.lang.StringUtils"%>
<html>
<head>
<meta name="layout" content="responsivemasterpage" />
<g:set var="entityName"
	value="${message(code: 'localList.label', default: 'Chuyen Bay Noi Dia')}" />
<title>Đặt mua vé máy bay Vé máy bay, đại lý máy bay ahotua</title>
</head>
<body>

	<div id="tour-section" class="row">
		<div id="break-crumb" class="row title-b">
			<div class="col-md-12 col-sm-12 col-xs-12">
			<g:link controller="home" action="index"><img
					src="${resource(dir:'images/img',file:'icon-home-black.png')}"
					alt="home" />Trang chủ</g:link>
				<span class="">&gt;</span> <a href="/flight/search"
					class="current">Vé máy bay</a>
			</div>
		</div>
		
		
		<section class="row">
				<g:include view="layouts/responsivewebpart/_searchFlightFormLeft.gsp" />

				<article class="col-lg-7 col-md-6 col-sm-5 col-xs-12">
						<div class="tree-img-130 bg-type-2">
							<div class="img-news">
								<img
									src="${resource(dir:'images/newuiimg',file:'tiep-vien.png') }"
									alt="Huong dan thu tuc" />
							</div>
							<div class="detail-news region-type-2">
								<span class="title title-upper title-b">Cách đặt vé máy bay</span>
								<div class="line line-horizontal box-margin-10"></div>
								<div class="space-10"></div>
								<strong>01. Trực tiếp trên website, nhanh nhất - tiện nhất</strong> <br />
									<p class="description">
										Để đặt vé máy bay trên trang web: 
										</br>
										Tìm kiếm vé máy bay -> Chọn chuyến bay -> Điền thông tin -> Thanh toán -> Xác nhận.
										</br>
										<g:link controller="policy" action="guide"><span class="location">Xem hướng dẫn chi tiết</span></g:link>
									</p>
								<strong>02. Qua chat</strong> <br />
								<strong>03. Gọi điện thoại cho Ahotua 0000.000.000</strong> <br />
								<strong>04. Đến trực tiếp văn phòng Ahotua</strong> <br />
									<p class="description">Cơ sở 1: Hoàng Quốc Việt, Cầu Giấy, Hà Nội.</p>
							</div>
							<div class="space-20"></div>
							<div class="line line-horizontal box-margin-10"></div>
						</div>
				</article>
		</section>

		<div class="space-10 hidden-xs"></div>
	</div>
</body>
</html>
