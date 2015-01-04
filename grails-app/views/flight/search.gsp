<%@ page import="org.apache.commons.lang.StringUtils"%>
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
				<article id="tim-chuyen-bay" class="col-lg-5 col-md-6 col-sm-7 col-xs-12">
					<div class="tour-box">
						<g:form controller="flight" action="switchSearchPage"
							method="POST">
							<div class="title">
								<img src="${resource(dir:'images/img',file:'icon-tim-chuyen-bay.png')}" alt="" /> <span
									class="title title-upper title-b">Tìm chuyến bay</span>

								<div class="line line-horizontal"></div>
							</div>
							<div class="control-collection">
								<div class="space-5"></div>
								<div class="row">
									<div class="col-md-6 col-sm-6 col-xs-6">
										<label id="diem-di-label" for="diem-di-input"
											class="control-label">Điểm đi</label> <input id="gog"
											onclick="select()" class="control-input" type="text"
											value="Hanoi (HAN)" name="departureCode" />
									</div>

									<div class="col-md-6 col-sm-6 col-xs-6">
										<label id="diem-den-label" for="diem-den-input"
											class="control-label">Điểm đến</label> <input id="tog"
											onclick="select()" class="control-input" type="text"
											value="Ho Chi Minh (SGN)" name="arrivalCode" />
									</div>
								</div>
								<div class="space-5"></div>

								<div class="row">
									<div class="col-md-6 col-sm-6 col-xs-6">
										<label id="ngay-di-label" for="ngay-di-input"
											class="control-label">Ngày đi</label> <br /> <select
											name="oday" id="oday" class="control-input"
											style="width: 45px; height: 25px;"></select> <select
											name="omonth" id="omonth" class="control-input"
											style="width: 120px; margin-left: 0px; height: 25px; margin-top: 5px"></select>
									</div>

									<div class="col-md-6 col-sm-6 col-xs-6">
										<label id="ngay-ve-label" for="ngay-ve-input"
											class="control-label">Ngày về</label> <br /> <select
											name="iday" id="iday" class="control-input"
											style="width: 45px; height: 25px;"><option value="0"></option></select>
										<select id="imonth" name="imonth" class="control-input"
											style="width: 120px; margin-left: 0px; height: 25px; margin-top: 5px">
											<option value="00" selected="selected"></option>
										</select>
									</div>
								</div>
								<div class="space-10"></div>

								<div class="row">
									<div class="col-md-12 col-sm-12 col-xs-12">
										<span class="title title-upper title-b">Số lượng</span>
										<div class="line line-horizontal"></div>
									</div>
								</div>
								<div class="space-5"></div>

								<div class="row">
									<div class="col-md-3 col-sm-3 col-xs-3">
										<label for="so-luong-nguoi-lon" class="control-label">Người
											lớn</label><br /> <select name="adults" id="adults"
											class="control-input" style="height: 25px;"></select>
										<div class="description title-while">(&gt; 12 tuổi)</div>
									</div>
									<div class="col-md-3 col-sm-3 col-xs-3">
										<label for="so-luong-tre-em" class="control-label">Trẻ
											em</label> <br /> <select name="kids" id="kids"
											class="control-input" style="height: 25px;"></select>
										<div class="description title-while">(2 - 12 tuổi)</div>
									</div>
									<div class="col-md-3 col-sm-3 col-xs-3">
										<label for="so-luong-em-be" class="control-label">Em
											bé</label><br /> <select name="infants" id="infants"
											class="control-input" style="height: 25px;"></select>
										<div class="description title-while">(&lt; 2 tuổi)</div>
									</div>
								</div>

								<div class="space-10"></div>

								<div class="row">
									<div class="col-md-12 col-sm-12 col-xs-12">
										<div class="center-block">
											<button id="tim-lich-bay" class="tour-button" type="submit">
												<img src="${resource(dir:'images/img',file:'icon-search.png') }" alt="search"
													width="16px" height="16px" /> <span>TÌM KIẾM</span>
											</button>
										</div>
									</div>
								</div>
								<g:include view="flight/includes/_searchflight.gsp" />
							</div>

						</g:form>
					</div>
				</article>

				<article class="col-lg-7 col-md-6 col-sm-5 col-xs-12">
						<div class="tree-img-130 bg-type-2">
							<div class="img-news">
								<img
									src="${resource(dir:'images/newuiimg',file:'tiep-vien.png') }"
									alt="" />
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
