<!-- header -->

<header class="tour-top">
	<div class="container">
		<div class="row col-lg-10 col-lg-offset-1 col-md-10 col-md-offset-1 col-sm-10 col-sm-offset-1  col-xs-12">
			<div id="tour-logo" class="col-lg-3 col-md-3 col-sm-3 col-xs-12">
				<g:link controller="home"><img src="${resource(dir:'images/newuiimg',file:'logo.png') }"
					class="center-block logo"/></g:link>
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

<nav id="menu" class="hidden-xs" style="font-size: 12.5px !important">
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
								action="search">Đặt vé máy bay</g:link>
					</g:if>
					<g:else>
						<li><g:link controller="flight" action="search">Đặt vé máy bay</g:link>
					</g:else>
					
					<g:if test="${params['controller'].equals('radar')}">
						<li class="active"><g:link controller="radar" >Hành trình bay</g:link>
					</g:if>
					<g:else>
						<li><g:link controller="radar">Hành trình bay</g:link>
					</g:else>
					
					<g:if test="${params['controller'].equals('news') && params['action'].equals('index')}">
						<li class="active"><g:link controller="news" action="index">Tin tức</g:link>
					</g:if>
					<g:else>
						<li><g:link controller="news" action="index">Tin tức</g:link>
					</g:else>
					
					<g:if test="${params['controller'].equals('news') && params['action'].equals('saleoff')}">
						<li class="active"><g:link controller="news" action="saleoff">Khuyến mại</g:link>
					</g:if>
					<g:else>
						<li><g:link controller="news" action="saleoff">Khuyến Mại</g:link>
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
						<li><g:link controller="contactUs" action="infor">Liên hệ </g:link></li>
					</g:else>
				</ul>
			</div>
		</div>
	</div>
</nav>