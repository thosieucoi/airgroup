<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="com.airgroup.domain.Location"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="responsivemasterpage" />
<title>Vé máy bay, đại lý máy bay ahotua</title>
</head>
<body>

<div class="row">
		<div class="row">
			<g:include view="layouts/responsivewebpart/_searchFlightFormLeft.gsp" />

			<div id="myCarousel"
				class="carousel slide col-lg-7 col-md-6 col-sm-5 col-xs-12"
				data-ride="carousel" >
				<g:if test="${advert.size > 1}">
					<ol class="carousel-indicators">
						<g:each var="advertInstance" in="${advert}" status="st">
							<g:if test="${st==0}">
								<li id="firstIndi" data-target="#myCarousel" data-slide-to="0" class="active"></li>
							</g:if>
							<g:else>
								<li data-target="#myCarousel" data-slide-to="${st}"></li>
							</g:else>
						</g:each>
					 </ol>
				 </g:if>
				<div class="carousel-inner" role="listbox">
					<g:each var="advertInstance" in="${advert}" status="st">
						<g:if test="${st==0}">
							<div class="item active">
								<a href="${advertInstance.linkAdvert}">
									<img
										src="${createLink(controller:'home', action:'slideImage', id:advertInstance.id)}" alt="ahotua"/>
								</a>
							</div>
						</g:if>
						<g:else>
							<div class="item">
								<a href="${advertInstance.linkAdvert}">
									<img
										src="${createLink(controller:'home', action:'slideImage', id:advertInstance.id)}" alt="ahotua"/>
								</a>
							</div>
						</g:else>
					</g:each>
				</div>
				
				<g:if test="${advert.size > 1}">
					<!-- Left and right controls -->
				  <a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
				    <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
				    <span class="sr-only">Previous</span>
				  </a>
				  <a class="right carousel-control" href="#myCarousel" role="button" data-slide="next" style="width: 16%;">
				    <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
				    <span class="sr-only">Next</span>
				  </a>
				</g:if>
			</div>
		</div>

		<div class="space-10 hidden-xs"></div>

		<div class="row hidden-xs">
			<article id="tin-tuc-khuyen-mai" class="col-md-4 col-sm-4 col-xs-4">
				<div class="bg-type-2 region-type-3" style="height:350px;">
					<div class="title title-upper title-b">
						<g:link controller="share" action="index">
	                        	Chia sẻ kinh nghiệm
	                        </g:link>
					</div>
					<div class="line line-horizontal"></div>
					<ul>
						<g:each in="${lastThreeShare}" var="share">
							<li>
								<span>
									<g:link action="detailsInfo" controller="news"
											params="[infoid:share.id]">
											<img src="${resource(dir:'images/newuiimg',file:'share.png') }" width="20" />
									</g:link>
								</span>
								<g:link controller="news" action="detailsInfo"
									params="[infoid:share.id]">
									<span class="location">${StringUtils.abbreviate(share.title,45)}</span>
								</g:link>
								<div class="description">
									${StringUtils.abbreviate(share.introduction,150)}
								</div>
							</li>
						</g:each>
					</ul>
				</div>
			</article>
		
			<article id="tin-tuc" class="col-md-4 col-sm-4 col-xs-4">
				<div class="bg-type-2 region-type-2" style="height:350px;">
					<div class="title title-upper title-b">
						<g:link controller="news" action="index">
                        		Tin Tức
                        	</g:link>
					</div>
					<div class="line line-horizontal"></div>
					<ul class="tour-tree-img tree-img-60 separate-box">
						<g:each var="news" status="i" in="${lastThreeNews}">
							<li>
								<div class="img-news">
									<g:link action="detailsInfo" controller="news"
										params="[infoid:news.id]">
										${StringUtils.substringBetween(news.image, '<p>', '</p>')}
									</g:link>
								</div>
								<div class="detail-news">
									<g:link
											action="detailsInfo" controller="news"
											params="[infoid:news.id]">
										<span class="location">${StringUtils.abbreviate(news.title,100)}</span>
									</g:link>
									 <br /> 
									
									<span class="more">
										<g:link
											action="detailsInfo" controller="news"
											params="[infoid:news.id]">Xem chi tiết</g:link>
									</span>
								</div>
								<div class="clearfix"></div>
							</li>
						</g:each>
					</ul>
				</div>
			</article>
			
			<article id="y-kien-khach-hang" class="col-md-4 col-sm-4 col-xs-4">
				<div class="bg-type-2 region-type-2" style="height:350px;">
					<div class="title title-upper title-b">
						<g:link controller="feedback" action="index">Ý KIẾN KHÁCH HÀNG</g:link>
					</div>
					<div class="line line-horizontal box-margin-10"></div>
					<ul class="tour-tree-img tree-img-32 separate-line">
						<g:each var="feedbackInstance" in="${lastFiveFeedback}">
							<li>
								<div class="img-feedback">
									<img
										src="${resource(dir:'images/newuiimg',file:'icon-message.jpg') }"
										width="32" height="32" alt="">
								</div>
								<div class="detail-news">
									<strong>
										"${StringUtils.abbreviate(feedbackInstance.content, 45)}”
									</strong>
									<p class="description title-i">
										${StringUtils.abbreviate(feedbackInstance.name, 25)}
									</p>
								</div>
								<div class="clearfix"></div>
							</li>
						</g:each>
					</ul>

					<div class="command">
						<a style="text-decoration: none"
							href="${createLink(action: 'index', controller:'feedback') }">
							<input type="button" class="feedback-button" value="Xem thêm" />
						</a>
					</div>
				</div>
			</article>
		</div>

		<div class="space-10 hidden-xs"></div>

		<div class="row hidden-xs">
			<article class="col-md-4 col-sm-4 col-xs-4">
				<div class="bg-type-2 region-type-3" style="height:350px;">
					<div id="myCarousel1" class="carousel slide" data-ride="carousel">
						<div class="carousel-inner">
							<div class="item active">
								<!-- <img class="tour_slide" src="images/img/du-lich.jpg" alt="Ahotua" /> -->
								<script type="text/javascript">
								   agoda_ad_client = "1717071_5025";
								   agoda_ad_width = 300;
								   agoda_ad_height = 250;
								   agoda_ad_language = 1;
								   agoda_ad_country = 38;
								   agoda_ad_city = 2758;
								</script>
								<script type="text/javascript" src="//banner.agoda.com/js/show_ads.js"></script>
							</div>
						</div>
					</div>

					<div class="line line-horizontal box-margin-10"></div>
					<ul class="tour-tree-circle">
						<li><a href="http://www.hanhtrinh-phuongdong.com/">Hành Trình Phương Dông</a></li>
						<li><a href="http://www.agoda.com/vi-vn?cid=1717071" rel='nofollow'>Đặt khách sạn và resort rẻ nhất toàn cầu Agoda</a></li>
					</ul>
				</div>
			</article>
			<article id="hinh-thuc-thanh-toan" class="col-md-4 col-sm-4 col-xs-4">
				<div class="bg-type-2 region-type-3" style="height:350px;">
					<div class="title title-upper title-b">Hình thức thanh toán</div>
					<div class="line line-horizontal box-margin-10"></div>
					<ul>
						<li>
							<div class="title-b">
								<g:link controller="contactUs" action="infor">
									<span><img
										src="${resource(dir:'images/newuiimg',file:'office.png') }"
										height="15" /></span> Thanh toán tại văn phòng ahotua Việt Nam
                                	</g:link>
							</div>
							<div class="description">Nhận hàng và thanh toán tại văn
								phòng ahotua Việt Nam</div>
						</li>
						<li>
							<div class="title-b">
								<g:link controller="contactUs" action="infor">
									<img src="${resource(dir:'images/newuiimg',file:'home.png') }"
										height="15" /> Thanh toán qua Paypal, thẻ Visa
	                                </g:link>
							</div>
							<div class="description">Giao dịch bằng paypal, thẻ tín dụng</div>
						</li>
						<li>
							<div class="title-b">
								<g:link controller="contactUs" action="infor">
									<img
										src="${resource(dir:'images/newuiimg',file:'nganhang.png') }"
										height="15" /> Thanh toán qua chuyển khoản
                                	</g:link>
							</div>
							<div class="description">Chuyển khoản tại ngân hàng, chuyển
								khoản bằng thẻ ATM</div>
						</li>
						<li>
							<div class="title-b">
								<g:link controller="contactUs" action="infor">
									<img
										src="${resource(dir:'images/newuiimg',file:'online-payment.png') }"
										height="15" width="16" /> Thanh toán qua cổng thanh toán điện tử
                                	</g:link>
							</div>
							<div class="description">Chuyển khoản qua cổng thanh toán
								điện tử Bảo Kim, Ngân Lượng,...</div>
						</li>
					</ul>
				</div>
			</article>
			<article id="giao-dich" class="col-md-4 col-sm-4 col-xs-4">
				<div class="bg-type-2 region-type-3" style="height:350px;">
					<div class="title title-upper title-b">Giao dịch</div>
					<div class="line line-horizontal box-margin-10"></div>
					<ul id="banks">
						<li>
							<div class="bank-logo">
								<img src="images/img/TechcomBank.png" width="70" height="40"
									alt="Techcombank" />
							</div>
							<div class="bank-detail">
								<span class="title title-b">Ngân Hàng Techcombank Việt Nam</span> <br />
								<span><b>Tên tài khoản:</b> Công ty cổ phần Ahotua Việt Nam</span> <br /> 
								<span><b>Số tài khoản:</b> 19129041129011</span><br /> 
								<span><b>Địa chỉ:</b> Ngân hàng TMCP Kỹ Thương Việt Nam -  chi nhánh Hoàng Quốc Việt</span>
							</div>
							<div class="clearfix"></div>
						</li>
						
						<li>
							<div class="bank-logo">
								<img src="images/img/VietcomBank.png" width="70" height="40"
									alt="Vietcombank" />
							</div>
							<div class="bank-detail">
								<span class="title title-b">Ngân hàng Vietcombank Việt Nam</span> <br />
								<span><b>Tên tài khoản:</b> Công ty cổ phần Ahotua Việt Nam</span> <br /> 
								<span><b>Số tài khoản:</b> 0491000056160</span><br /> 
								<span><b>Địa chỉ:</b> Ngân hàng TMCP Ngoại thương Việt Nam -  chi nhánh Thăng Long</span>
							</div>
							<div class="clearfix"></div>
						</li>
						
						<li>
							<div class="bank-logo">
								<img src="images/img/BIDV.jpg" width="70" height="40"
									alt="BIDV" />
							</div>
							<div class="bank-detail">
								<span class="title title-b">Ngân hàng BIDV Việt Nam</span> <br />
								<span><b>Tên tài khoản:</b> Công ty cổ phần Ahotua Việt Nam</span> <br /> 
								<span><b>Số tài khoản:</b> 21510001634267</span><br /> 
								<span><b>Địa chỉ:</b> Ngân Hàng Đầu Tư Và Phát Triển Việt Nam -  chi nhánh Cầu Giấy</span>
							</div>
							<div class="clearfix"></div>
						</li>
						
						<li>
							<div class="bank-logo">
								<img src="images/img/vietin.jpg" width="70" height="40"
									alt="Vietinbank" />
							</div>
							<div class="bank-detail">
								<span class="title title-b">Ngân hàng Vietinbank Việt Nam</span> <br />
								<span><b>Tên tài khoản:</b> Công ty cổ phần Ahotua Việt Nam</span> <br /> 
								<span><b>Số tài khoản:</b> 10201-000216255-5</span><br /> 
								<span><b>Địa chỉ:</b> Ngân Hàng TMCP Công Thương Việt Nam -  chi nhánh Nam Thăng Long</span>
							</div>
							<div class="clearfix"></div>
						</li>
					</ul>
					<div class="line line-horizontal box-margin-10"></div>
					<div class="description">
						<strong>Vé máy bay điện tử</strong> <br /> Giao dịch trên tất cả
						các ngân hàng <br /> Chuyển tiền ngay vé trao tay
					</div>
				</div>
			</article>
		</div>

		<div class="space-10 hidden-xs"></div>
	</div>
	<script>
		$("#firstIndi").trigger("click");
	</script>
</body>
</html>
