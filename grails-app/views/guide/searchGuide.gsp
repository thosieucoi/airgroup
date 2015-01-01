<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="responsivemasterpage" />
<g:set var="entityName"
	value="${message(code: 'localList.label', default: 'Chuyen Bay Noi Dia')}" />
<title><g:message code="title.homepage" args="[entityName]" /></title>
</head>
<body>
	<div id="tour-section" class="row">
		<section id="break-crumb" class="row title-b">
			<article class="col-md-12 col-sm-12 col-xs-12">
				<g:link controller="home">
					<img
						src="${resource(dir:'images/newuiimg',file:'icon-home-black.png')}"
						alt="home" />
                        Trang chủ
                    </g:link>
				<span class="">&gt;</span><g:link controller="flight" action="search">Vé máy bay</g:link>
				<span class="">&gt;</span> <a href="#" class="current">Du
					lịch</a>
			</article>
		</section>
		<section class="row">
			<article class="col-md-9 col-sm-9 col-xs-9">
				<div class="bg-type-2 region-type-2 box-padding-20">
					<div class="title title-b title-upper">
						<span class="title">Hướng dẫn đặt vé</span>
					</div>
					<div class="line line-horizontal"></div>
					<div class="space-10"></div>
					<div>
					<div class="debook">

							<strong><p>Quý khách hàng sử dụng Form tìm kiếm để tìm kiếm chuyến
								bay , so sánh vé giữa các hãng và đặt vé</p></strong>
							<strong>Bước 1: Điền các thông tin vào form tìm kiếm:</strong>
							<center>
								<img src="${resource(dir:'images',file:'hd1(1).png')}" />
							</center>
							<p>Chọn điểm khời hành – điểm đến . Nếu quý khách hàng muốn
								tìm vé đi quốc tế thì điền tên thành phố hoặc mã sân bay đi quốc
								tế.</p>
							<center>
								<img src="${resource(dir:'images',file:'hd2.png')}" />
							</center>
						</div>
						<div class="debook">
							<strong>Bước 2: So sánh Kết quả tìm kiếm trả về, xem thông tin
								vé và lựa chọn vé:</strong>
							<center>
								<img src="${resource(dir:'images',file:'hd3.png')}" />
							</center>
							<p>Xem thông tin chi tiết về vé và so sánh giá vé giữa các
								hãng hàng không</p>
							<center>
								<img src="${resource(dir:'images',file:'hd6.png')}" />
							</center>
							<p>Khách hàng có thể lọc vé theo các tiêu chí khác nhau như
								thời gian bay , thời gian về , lọc theo Hãng , và sắp xếp vé
								theo giá vé , thời gian khởi hành , Theo hãng.</p>
							<center>
								<img src="${resource(dir:'images',file:'hd5.png')}" />
							</center>
							<p>Sau khi chọn được vé phù hợp , khách hàng Click Chọn và
								Tiếp tục để thực hiện đặt vé</p>
							<center>
								<img src="${resource(dir:'images',file:'hd6.png')}" />
							</center>
						</div>
						<div class="debook">
							<strong>Bước 3: Điền các thông tin liên hệ</strong>
							<p>Khách hàng nhập đầy đủ thông tin hành khách và thông tin
								của người liên hệ . Sau đó Click Tiếp theo để thực hiện chọn
								Phương thức thanh toán.</p>
							<center>
								<img src="${resource(dir:'images',file:'hd7.png')}" />
							</center>
						</div>
						<div class="debook">
							<strong>Bước 4:Chọn phương thức thanh toán</strong>
							<p>Khách hàng có thể chọn các phương thức thanh toán khác
								nhau từ Thanh toán chuyển khoản , Thanh toán trức tuyến qua các
								cổng thanh toán , hoặc thanh toán tại văn phòng của IGO</p>

						</div>
						<div class="debook">
						<strong>Bước 5: Nhận thông báo Hoàn tất đơn hàng từ IGO và Email
								thông báo gửi cho khách hàng.</strong>
							<p>Sau khi khách hàng hoàn tất đơn hàng , nhân viên IGO sẽ
								liên lạc lại với quý khách hàng để hỗ trợ khách hàng trong thời
								gian sớm nhất.</p>
							<p>
								<b>Trong quá trình đặt vé nếu có gì thắc mắc hoặc gặp khó
									khăn , hãy liên hệ với nhân viên của IGO . Chúng tôi sẽ hỗ trợ
									khách hàng tận tình và chu đáo . Chúc Quý khách hàng đặt vé
									thành công và có những trải nghiệm thú vị với IGO.</b>
							</p>

						</div>
					</div>
				</div>
			</article>

			<g:include view="layouts/responsivewebpart/_searchFlightForm.gsp"/>
		</section>
	</div>


	<script>
		jQuery(document).ready(function($) {


		});
	</script>
</body>
</html>
