<header class="tour-top">
	<div class="container">
		<div class="row col-lg-10 col-lg-offset-1 col-md-10 col-md-offset-1 col-sm-10 col-sm-offset-1  col-xs-12">
			<div id="tour-logo" class="col-lg-3 col-md-3 col-sm-3 col-xs-12">
				<g:link controller="home"><img src="${resource(dir:'images/newuiimg',file:'logo.png') }"
					class="center-block logo"/></g:link>
			</div>
			<sec:ifLoggedIn>
				<g:include view="layouts/responsivewebpart/_userInforHeader.gsp" />
			</sec:ifLoggedIn>
			
			<sec:ifNotLoggedIn>
				<g:if test="${!params['controller'].equals('login')}">
						<g:include view="layouts/responsivewebpart/_loginFormHeader.gsp" />
				</g:if>
			</sec:ifNotLoggedIn>
			
		</div>
	</div>
</header>

<nav id="menu" class="hidden-xs" style="font-size: 12.5px !important">
	<div class="container">
		<div class="row col-lg-10 col-lg-offset-1" style ="margin-left: 12% !important">
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
						<li class="active"><g:link controller="radar" >Theo dõi chuyến bay</g:link>
					</g:if>
					<g:else>
						<li><g:link controller="radar">Theo dõi chuyến bay</g:link>
					</g:else>
					
					<g:if test="${params['controller'].equals('news') && params['action'].equals('index')}">
						<li class="active"><g:link controller="news" action="index">Tin tức</g:link>
					</g:if>
					<g:else>
						<li><g:link controller="news" action="index">Tin tức</g:link>
					</g:else>
					
					<!--<g:if test="${params['controller'].equals('information') && params['action'].equals('index')}">
						<li class="active"><g:link controller="information" action="index">Thông tin & Tiện ích</g:link>
					</g:if>
					<g:else>
						<li><g:link controller="information" action="index">Thông tin & Tiện ích</g:link>
					</g:else>-->
					
					<g:if test="${params['controller'].equals('news') && params['action'].equals('saleoff')}">
						<li class="active"><g:link controller="news" action="saleoff">Khuyến mại</g:link>
					</g:if>
					<g:else>
						<li><g:link controller="news" action="saleoff">Khuyến Mại</g:link>
					</g:else>
					
					<g:if test="${params['controller'].equals('feedback')}">
						<li class="active"><g:link controller="feedback"
								action="index">Ý kiến khách hàng</g:link></li>
					</g:if>
					<g:else>
						<li><g:link controller="feedback" action="index">Ý kiến khách hàng</g:link></li>
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