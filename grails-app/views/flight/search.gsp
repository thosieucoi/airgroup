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
		<section id="break-crumb" class="row title-b">
			<article class="col-md-12 col-sm-12 col-xs-12">
			<g:link controller="home" action="index"><img
					src="${resource(dir:'images/img',file:'icon-home-black.png')}"
					alt="home" />Trang chủ</g:link>
				<span class="">&gt;</span> <a href="/flight/search"
					class="current">Vé máy bay</a>
			</article>
		</section>
		<section class="row">
			<article id="tour-cate" class="col-md-3 col-sm-3 col-xs-3">
				<ul id="list-tour" class="menu-cate"
					style="height: 350px; background: #eee;">
					<li>
                            <span class="title">Vé máy bay</span>
                            <ul class="sub-list">
                                <li>
                                	<g:link controller="flight" action="search">Tìm và đặt vé</g:link>
                                </li>
                                <li>
                                    <g:link controller="policy" action="dieukien">Dịch vụ</g:link>
                                </li>
                                <li>
                                    <a href="#" id="thamkhao">Tham khảo</a>
                                </li>
                                <li>
                                    <g:link controller="guide" action="searchGuide">Hướng dẫn</g:link>
                                </li>
                            </ul>
                        </li>
                        <li>
                            <span class="title">Airline Support</span>
                            <ul class="sub-list">
                                <li>
                                    <a href="/policy/dieukien#C5">Vietnam Airline</a>
                                </li>
                                <li>
                                    <a href="/policy/dieukien#C6">JetStar</a>
                                </li>
                                <li>
                                    <a href="/policy/dieukien#C7">VietJet</a>
                                </li>
                            </ul>
                        </li>
				</ul>
			</article>

			<article id="tim-chuyen-bay" class="col-md-5 col-sm-5 col-xs-5">
				<div class="tour-box">
					<g:form controller="flight" action="switchSearchPage" method="POST">
						<div class="title">
							<img
								src="${resource(dir:'images/img',file:'icon-tim-chuyen-bay.png') }"
								alt="" /> <span class="title title-upper title-b">Tìm
								chuyến bay</span>

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
										style="width: 100px; margin-left: 10px; height: 25px;"></select>
								</div>

								<div class="col-md-6 col-sm-6 col-xs-6">
									<label id="ngay-ve-label" for="ngay-ve-input"
										class="control-label">Ngày về</label> <br /> <select
										name="iday" id="iday" class="control-input"
										style="width: 45px; height: 25px;"><option value="0"></option></select>
									<select id="imonth" name="imonth" class="control-input"
										style="width: 100px; margin-left: 10px; height: 25px;">
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
										lớn</label> <select name="adults" id="adults" class="control-input"
										style="height: 25px;"></select>
									<div class="description title-while">(&gt; 12 tuổi)</div>
								</div>
								<div class="col-md-3 col-sm-3 col-xs-3">
									<label for="so-luong-tre-em" class="control-label">Trẻ
										em</label> <br /> <select name="kids" id="kids" class="control-input"
										style="height: 25px;"></select>
									<div class="description title-while">(2 - 12 tuổi)</div>
								</div>
								<div class="col-md-3 col-sm-3 col-xs-3">
									<label for="so-luong-em-be" class="control-label">Em bé</label>
									<select name="infants" id="infants" class="control-input"
										style="height: 25px;"></select>
									<div class="description title-while">(&lt; 2 tuổi)</div>
								</div>
							</div>
							
							<div class="space-10"></div>

							<div class="row">
								<div class="col-md-12 col-sm-12 col-xs-12">
									<div class="center-block">
										<button id="tim-lich-bay" class="tour-button" type="submit">
											<img
												src="${resource(dir:'images/newuiimg',file:'icon-search.png') }"
												alt="search" width="16px" height="16px" /> <span>TÌM KIẾM</span>
										</button>
									</div>
								</div>
							</div>
							<g:include view="flight/includes/_searchflight.gsp" />
						</div>

					</g:form>
				</div>
			</article>

			<article class="col-md-4 col-sm-4 col-xs-4">
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
			</article>
		</section>

		<div class="space-10"></div>

		<section class="row">
			<article class="col-md-3 col-sm-3 col-xs-3">
				<div class="adv-left">
					<img src="${resource(dir:'images',file:'bano.jpg')}" />
				</div>
			</article>

			<article id="dich-vu-bay" class="col-md-5 col-sm-5 col-xs-5">
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
			</article>

			<article id="y-kien-khach-hang" class="col-md-4 col-sm-4 col-xs-4">
				<div class="bg-type-2 region-type-2">
					<div class="title title-upper title-b">
						<g:link controller="feedback" action="list">Ý KIẾN KHÁCH HÀNG</g:link>
					</div>
					<div class="line line-horizontal box-margin-10"></div>
					<ul class="tour-tree-img tree-img-32 separate-line">
						<g:each var="feedbackInstance" in="${lastThreeFeedback}">
							<li>
								<div class="img-news">
									<img
										src="${resource(dir:'images/newuiimg',file:'icon-message.jpg') }"
										width="32" height="32" alt="">
								</div>
								<div class="detail-news">
									<p class="description title-i">
										"${StringUtils.abbreviate(feedbackInstance.content, 45)}”
									</p>
									<strong>
										${StringUtils.abbreviate(feedbackInstance.name, 25)} - <g:if
											test="${feedbackInstance.phoneNumber}">
											${feedbackInstance.phoneNumber}
										</g:if>
									</strong>
								</div>
								<div class="clearfix"></div>
							</li>
						</g:each>
					</ul>

					<div class="command">
						<a style="text-decoration: none"
							href="${createLink(action: 'list', controller:'feedback') }">
							<input type="button" class="tour-button" value="GỬI PHẢN HỒI" />
						</a>
					</div>
				</div>
			</article>
		</section>
	</div>

	<div class="space-20"></div>
	<div class="space-20"></div>
	<%--<g:form name="submitForm" controller="flight" action="switchSearchPage"  method="POST">
		<div class="nav hone">
			<div class="book det">
				<h1>
					<a href="">ĐẶT VÉ MÁY BAY</a>
				</h1>
				<div class="line">
					<table>
						<tr>
							<td style="width: 190px"><span>Điểm khởi hành:</span><br />
								<input id="gog" onclick="select()" type="text"
								value="Hanoi (HAN)" name="departureCode" /></td>
							<td style="width: 190px"><span>Điểm đến:</span><br /> <input
								id="tog" onclick="select()" type="text" value="Ho Chi Minh (SGN)"
								name="arrivalCode" /></td>
						</tr>
						<tr>
							<td><span>Ngày đi:</span><br /> <select name="oday" id="oday"
								style="width: 60px">
									<option value="1">1</option>
							</select> <select name="omonth" id="omonth" style="width: 100px">
							</select></td>
							<td><span>Ngày về:</span><br /> <select name="iday" id="iday"
								style="width: 60px">
								<option value="0"></option>
							</select> <select name="imonth" id="imonth" style="width: 100px">
									<option value="0"></option>
							</select></td>
						</tr>

					</table>
					<table class="nun">
						<tr>
							<td class="tit"><span>Số lượng:</span></td>
						</tr>
						<tr>
							<td class="tit"><label>Người lớn(>=12 tuổi)</label><br /> <select
								name="adults" id="adults">
							</select></td>
							<td class="tit"><label>Trẻ em(2-11 tuổi)</label><br /> <select
								name="kids" id="kids">
							</select></td>
							<td class="tit"><label>Em bé(<2 tuổi)</label><br /> <select
								name="infants" id="infants">
							</select>
							</td>
						</tr>
						<tr>
							<td><span>Số điện thoại khách hàng:</span><br /> <input name="phoneNumber" id="customerPhoneNumber" maxlength="15"
								style="width: 100px; height: 22px" /></td>
							<td colspan="2">
								<g:link controller="guide" action="searchGuide" class="hd">
									<span><g:message code="navigation.guide" /></span>
									<em></em>
								</g:link>
							</td>
						</tr>
					</table>

					<input type="submit" value="Tìm chuyến bay" />
					<div id="validateMessage"></div>

				</div>
				<img src="${resource(dir:'images',file:'bano.jpg')}" />
				<g:include view="flight/includes/_searchflight.gsp" />

			</div>

		</div>
	</g:form>--%>

	<%-- <script>
		jQuery(document).ready(function($) {

			$('#banner-fade').bjqs({
				height : 385,
				width : 530,
				responsive : true
			});

		});
	</script>
	<script>
		$("#gog").click(function() {
			$(".toen").hide();
			$(".ticketone").show();
			return false;
		});

		$("#close").click(function() {
			$(".ticketone").hide();
		});
	</script>
	<script>
		$("#tog").click(function() {
			$(".ticketone").hide();
			$(".toen").show();
			return false;
		});

		$("#close").click(function() {
			$(".toen").hide();
		});
	</script> --%>
		<script>
		var link = $('#thamkhao');
		link.click(function()
		{
		    $(this).dialog({
		        buttons: 
		        {
		            "Chức năng đang được xây dựng, vui lòng quay lại sau": function()
		            {
		                 $(this).dialog('close');
		                 window.location.href = link.attr('href');
		            }
		        }
		    });
	
		    return false;
		});
	</script>
</body>
</html>
