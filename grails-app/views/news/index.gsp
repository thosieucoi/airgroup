<!DOCTYPE html>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="com.airgroup.domain.Location"%>
<html>
<head>
<meta name="layout" content="responsivemasterpage" />
<g:set var="entityName"
	value="${message(code: 'News', default: 'News')}" />
<title><g:message code="title.homepage" args="[entityName]" /></title>
</head>
<body class="">
	<div id="tour-section" class="row">
		<section id="break-crumb" class="row title-b">
			<article class="col-md-12 col-sm-12 col-xs-12">
				<g:link controller="home" action="index">
					<img src="${resource(dir:'images/img',file:'icon-home-black.png')}"
						alt="home" />Trang chủ</g:link>
				<span class="">&gt;</span> <a href="#" class="current">Tin
					tức khuyến mãi</a>
			</article>
		</section>
		<section class="row">
			<article id="tin-khuyen-mai" class="col-md-6 col-sm-6 col-xs-6">
				<div class="bg-type-2 region-type-2 box-padding-20">
					<div class="title title-upper title-b">
						<span><img
							src="${resource(dir:'images/newuiimg',file:'news.png') }"
							width="20" heigh="16" /></span> <span><font color="black">
								<b>Tin Tức</b>
						</font></span>
					</div>
					<div class="line line-horizontal "></div>
					<div class="space-10"></div>
					<ul class="list-news">
						<g:each in="${newsListTT}" var="news" status="index">
							<li>
								<div class="img-news-margin-right-10">
									<g:link action="detailsInfo" controller="news"
										params="[infoid:news.id]">
										${StringUtils.substringBetween(news.image, '<p>', '</p>')}
									</g:link>
								</div>
								<div class="box-padding-10">
									<span><img
										src="${resource(dir:'images/newuiimg',file:'news.png') }"
										width="20" heigh="16" /></span> <span class="title title-b"><g:link
											action="detailsInfo" controller="news"
											params="[infoid:news.id]">
											${news.title }
										</g:link> </span>
									<p class="description">
										${news.introduction }
									</p>
									<g:form
											controller="flight" action="search">
									<g:link controller="news" action="detailsInfo"
										params="[infoid:news.id]" class="more">Xem chi tiết</g:link>
									 <span class="box-margin-left-250">
											<button name="search" type="submit" class="tour-button">
												<span>Đặt vé </span>
											</button>
											</span>
										</g:form>
								</div>

								<div class="space-5"></div>
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
					<%--
					<g:paginate next="Forward" prev="Back" max="2" maxsteps="2"
						controller="news" action="index"  params="[type:0]"  total="${totalTT ? totalTT:0}" />
				--%>
					<g:paginate total="${totalTT}" max="8"
						offset="${session.newsPagination?.offset}" params="[type:0]" />
				</div>
			</article>
			<article id="tin-khuyen-mai" class="col-md-6 col-sm-6 col-xs-6">
				<div class="bg-type-2 region-type-2 box-padding-20">
					<div class="title title-upper title-b">
						<span><img
							src="${resource(dir:'images/newuiimg',file:'sale.png') }"
							width="20" heigh="16" /></span> <span><font color="black">
								<b>Khuyến mại</b>
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
									<span><img
										src="${resource(dir:'images/newuiimg',file:'sale.png') }"
										width="20" heigh="16" /></span> <span class="title title-b"><g:link
											action="detailsInfo" controller="news"
											params="[infoid:news.id]">
											${news.title }
										</g:link> </span>

									<p class="description">
										${news.introduction }
									</p>
									
									<g:form
											controller="flight" action="search">
									<g:link controller="news" action="detailsInfo"
										params="[infoid:news.id]" class="more">Xem chi tiết</g:link>
									 <span class="box-margin-left-120">
											<button name="search" type="submit" class="tour-button">
												<span>Đặt vé </span>
											</button>
											</span>
										</g:form>
									
								</div>
								<div class="space-5"></div>
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
					<%--<g:paginate next="Forward" prev="Back" max="2" maxsteps="2"
						controller="news" action="index" params="[type:1]" total="${totalKM ? totalKM:0}" />
				--%>
					<g:paginate total="${totalKM}" max="8"
						offset="${session.promotionPagination?.offset}" params="[type:1]" />
				</div>
			</article>
		</section>

	</div>
</body>
</html>