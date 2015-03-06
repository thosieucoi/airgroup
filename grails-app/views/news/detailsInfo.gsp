<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<meta name="layout" content="responsivemasterpage" />
<title>${content.title } Vé máy bay, đại lý máy bay ahotua</title>
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
					
					<g:if test="${bigCategory.equals('Tin tức')}">
						<g:link controller="news" action="index">Tin Tức</g:link>
					</g:if>
					<g:else>
						<g:link controller="share" action="index">Chia sẻ kinh nghiệm</g:link>
					</g:else>
					
					
					<span class="">&gt;</span>
					<a class="current" href="#">Chi tiết bài viết</a>
				</article>
			</section>

			<section class="row">
				<article id="tour-details" class="col-md-8 col-sm-8 col-xs-12" style="width:100%;">
					<div class="bg-type-2">
						<div class="adv">
							<div class="title title-upper title-b" style="text-align: left; padding: 10px;">
								<font color="black"> <b> ${bigCategory}
								</b></font>
							</div>

							<div class="tour-box">
								<h1><a href="#" class="title title-b"> ${content.title }
								</a></h1>
							</div>
						</div>
						<div id="tour-details-content">
							${content.content }
						</div>
					</div>
				</article>
				
			</section>
			<p class="box-padding-10"></p>
		</div>
	</div>
</body>
</html>