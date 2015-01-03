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
		
		
		<div class="row">
			<div class="col-lg-5 col-md-6 col-sm-7 col-xs-12">
				<article id="tim-chuyen-bay">
					<div class="tour-box">
						<g:form controller="flight" action="switchSearchPage"
							method="POST">
							<div class="title">
								<img src="images/img/icon-tim-chuyen-bay.png" alt="" /> <span
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
												<img src="images/img/icon-search.png" alt="search"
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
			</div>

			<div class="row col-md-4 col-sm-4 col-xs-4">
				<div>
					<strong>Vé Nội địa</strong> <br />
					<p class="description">
						Để tìm chuyến bay Nội địa quý khách chỉ cần chon: <br /> Điểm đi,
						Điểm đến, Ngày đi, Ngày về (khứ hồi). Chương trình sẽ tìm kiếm tất
						cả các chuyến bay của 3 hãng hàng không đang hoạt động tại Việt
						Nam: VietnamAirlines, JetStar, VietJet Air.
					</p>
					<strong>Vé Quốc Tế</strong> <br />
					<p class="description">Quý khách nhập tên nước, tên thành phố
						hoặc mã sân bay. Hệ thống sẽ tìm kiếm tất cả những chuyến bay bay
						thẳng, bay chuyển chặng của tất cả các hãng hàng không trên thế
						giới.</p>
				</div>
			</div>
		</div>

		<div class="space-10 hidden-xs"></div>
		<div class="row">
			<div id="dich-vu-bay" class="col-md-5 col-sm-5 col-xs-5">
				<div class="tree-img-130 bg-type-2">
					<div class="img-news">
						<img
							src="${resource(dir:'images/newuiimg',file:'tiep-vien.png') }"
							alt="" />
					</div>
					<div class="detail-news region-type-2">
						<span class="title title-upper title-b">Dịch vụ bay</span>
						<div class="line line-horizontal box-margin-10"></div>
						<div class="space-10"></div>
						<ul class="tour-tree-circle">
							<li><a class="title-b" href="/policy/dieukien#C1">Đón
									tiễn sân bay</a></li>
							<li><a class="title-b" href="/policy/dieukien#C2">Taxi</a></li>
							<li><a class="title-b" href="/policy/dieukien#C3">Hành
									lý</a></li>
							<li><a class="title-b" href="/policy/dieukien#C4">Visa,
									Hộ chiếu</a></li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
