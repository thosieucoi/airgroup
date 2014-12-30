<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<meta name="layout" content="responsivemasterpage" />
<title><g:message code="title.homepage" args="[entityName]" /></title>
</head>
<body>
	<div id="tour-page">
		<div id="tour-section" class="row">
			<section id="break-crumb" class="row title-b">
				<article class="col-md-12 col-sm-12 col-xs-12">
					<g:link controller="home" action="index">
						<img
							src="${resource(dir:'images/img',file:'icon-home-black.png')}"
							alt="home" />Trang chủ</g:link>
					<span class="">&gt;</span>
					<g:link controller="news">Tin tức khuyến mãi</g:link>
					<span class="">&gt;</span>
					<a class="current" href="#">Chi tiết tin tức</a>
				</article>
			</section>

			<section class="row">
				<article id="tour-details" class="col-md-12 col-sm-12 col-xs-12">
					<div class="bg-type-2">
						<div class="adv">
							<div class="title title-upper title-b">
								<font color="black"> <b> ${bigCategory}
								</b></font>
							</div>

							<div class="tour-box">
								<a href="#" class="title title-b"> ${content.title }
								</a>
							</div>
						</div>
						<div id="tour-details-content">
							${content.content }
						</div>
					</div>

				</article>
			</section>
			<p class="box-padding-10"></p>
			<section class="row">
				<article id="tour-details" class="col-md-12 col-sm-12 col-xs-12">
					<div class="bg-type-2">
						<div class="adv">
							<div class="title title-upper title-b">
								<font color="black"> <b>Các tin liên quan</b></font>
							</div>


							<g:each in="${newsList}" var="news" status="detailsInfo">
								<li>
									<div class="box-margin-left-10">
										<g:link action="detailsInfo" controller="news"
											params="[infoid:news.id]">
											${news.title }
											<g:formatDate format="(dd/MM)" date="${news.createdOn}" />
										</g:link>
									</div>

									<div class="space-5"></div>
								</li>
							</g:each>
						</div>
					</div>
				</article>
			</section>
		</div>
	</div>
</body>
</html>