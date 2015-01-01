<!-- header -->

<header class="tour-top">
	<div class="container">
		<div class="row col-lg-10 col-lg-offset-1 col-md-10 col-md-offset-1 col-sm-10 col-sm-offset-1  col-xs-12">
			<div id="tour-logo" class="col-lg-3 col-md-3 col-sm-3 col-xs-12">
				<img src="${resource(dir:'images/newuiimg',file:'logo.png') }"
					alt="logo"class="center-block logo"/>
			</div>
			<div id="tour-services" class="col-lg-9 col-md-9 col-sm-9 hidden-xs">
				<div class="row" style="font-size:9px">
					<div
						class="col-md-11 col-md-offset-1 col-sm-11 col-sm-offset-1 col-xs-11 col-xs-offset-1">
						<ul>
							<li><a href="#"> <span class="icon-bookings"></span>
									<div class="content">
										Đặt vé <br /> máy bay điện tử
									</div>
							</a></li>
							<li><a href="#"> <span class="icon-compare"></span>
									<div class="content">
										Theo dõi<br /> hành trình bay
									</div>
							</a></li>
							<li><a href="#"> <span class="icon-payment"></span>
									<div class="content">
										Thanh toán <br /> Đơn giản, an toàn
									</div>
							</a></li>
						</ul>
					</div>
					
				</div>
			</div>
			
		</div>
	</div>
</header>

<nav id="menu" class="hidden-xs" style="font-size: 12px !important">
	<div class="container">
		<div class="row col-lg-10 col-lg-offset-1">
			<div class="col-lg-5  col-md-3 col-xs-5" style="width: 4% !important;">
				<div id="date-time">
					<g:link controller="home"><span class="icon-home"></span></g:link>
				</div>
			</div>
			<div class="col-lg-7 col-md-9 col-xs-7" style="width: 96% !important;">
				<ul class="tour-nav" >
					<g:if test="${params['controller'].equals('home')}">
						<li class="active"><g:link controller="home">Trang chủ </g:link></li>
					</g:if>
					<g:else>
						<li><g:link controller="home">Trang chủ </g:link></li>
					</g:else>
					<g:if test="${params['controller'].equals('flight')}">
						<li class="active"><g:link controller="flight"
								action="search">So sánh giá</g:link>
					</g:if>
					<g:else>
						<li><g:link controller="flight" action="search">So sánh giá</g:link>
					</g:else>
					
					<g:if test="${params['controller'].equals('radar')}">
						<li class="active"><g:link controller="radar" >Hành trình bay</g:link>
					</g:if>
					<g:else>
						<li><g:link controller="radar">Hành trình bay</g:link>
					</g:else>
					
					<g:if test="${params['controller'].equals('news')}">
						<li class="active"><g:link controller="news">Khuyến mại</g:link>
					</g:if>
					<g:else>
						<li><g:link controller="news">Khuyến Mại</g:link>
					</g:else>
					
					<g:if test="${params['controller'].equals('affiate')}">
						<li class="active"><g:link controller="affiate">Tích điểm</g:link>
					</g:if>
					<g:else>
						<li><g:link controller="affiate">Tích điểm</g:link>
					</g:else>
					
					<g:if test="${params['controller'].equals('infor')}">
						<li class="active"><g:link controller="infor"
								action="help">Hướng dẫn bay</g:link></li>
					</g:if>
					<g:else>
						<li><g:link controller="infor" action="help">Hướng dẫn bay</g:link></li>
					</g:else>
					
					
					<g:if test="${params['controller'].equals('feedback')}">
						<li class="active"><g:link controller="feedback"
								action="list">Ý kiến khách hàng</g:link></li>
					</g:if>
					<g:else>
						<li><g:link controller="feedback" action="list">Ý kiến khách hàng</g:link></li>
					</g:else>
					
					<g:if test="${params['controller'].equals('partner')}">
						<li class="active"><g:link controller="partner"
								action="list">Đối tác</g:link></li>
					</g:if>
					<g:else>
						<li><g:link controller="partner" action="list">Đối tác</g:link></li>
					</g:else>
					
					<g:if test="${params['controller'].equals('contactUs')}">
						<li class="active"><g:link controller="contactUs"
								action="infor">Liên hệ</g:link></li>
					</g:if>
					<g:else>
						<li><g:link controller="contactUs" action="infor">Liên hệ</g:link></li>
					</g:else>
				</ul>
			</div>
		</div>
	</div>
</nav>