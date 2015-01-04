<!DOCTYPE html>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="com.airgroup.domain.Location"%>
<html>
<head>
<meta name="layout" content="responsivemasterpage" />
<g:set var="entityName"
	value="${message(code: 'News', default: 'News')}" />
<title>Khuyến mại Vé máy bay, đại lý máy bay ahotua</title>
</head>
<body class="">
	<div id="tour-section" class="row">
		<section id="break-crumb" class="row title-b">
			<article class="col-md-12 col-sm-12 col-xs-12">
				<g:link controller="home" action="index">
					<img src="${resource(dir:'images/img',file:'icon-home-black.png')}"
						alt="home" />Trang chủ</g:link>
				<span class="">&gt;</span> <a href="#" class="current">Khyến Mại</a>
			</article>
		</section>
		<section class="row">
			<article id="tin-khuyen-mai" class="col-md-8 col-sm-8 col-xs-12">
				<div class="bg-type-2 region-type-2 box-padding-20">
					<div class="title title-upper title-b" style="text-align: left">
						<span><img
							src="${resource(dir:'images/newuiimg',file:'sale.png') }"
							width="20" heigh="16" /></span> <span><font color="black">
								<b>Khuyến Mãi</b>
						</font></span>
					</div>
					<div class="line line-horizontal "></div>
					<div class="space-10"></div>
					<ul class="list-news">
						<g:each in="${newsListKM}" var="news" status="index">
							<li>
								<div class="img-news-margin-right-10">
									<g:link action="detailsInfo" controller="news"
										params="[infoid:news.id]">
										${StringUtils.substringBetween(news.image, '<p>', '</p>')}
									</g:link>
								</div>
								<div class="box-padding-10">
									<span class="title title-b" style="font-size: 1.5em;"><g:link
											action="detailsInfo" controller="news"
											params="[infoid:news.id]">
											${news.title }
										</g:link> </span>
									<p class="description">
										${news.introduction }
									</p>
								</div>

								<div class="space-10"></div>
								<div class="line line-horizontal "></div>
								<div class="space-10"></div>
							</li>
						</g:each>
					</ul>
				</div>
				<div style="clear: both"></div>

				<div class="paginateButtons">
					<g:hiddenField controller="news" action="index" name="offset"
						value="${offset}" />
					<g:hiddenField controller="news" action="index" name="max"
						value="${max}" />
					<g:paginate total="${totalKM}" max="8"
						offset="${session.newsPagination?.offset}" params="[type:0]" />
				</div>
			</article>
			
			<g:include view="layouts/responsivewebpart/_searchFlightForm.gsp" />
		</section>
	</div>
</body>
</html>