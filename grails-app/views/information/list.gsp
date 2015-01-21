<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@ page import="com.airgroup.domain.Tour"%>
<html>
<head>
	<meta name="layout" content="responsivemasterpage" />
	<g:set var="entityName" value="${message(code: 'Tour', default: 'Tour')}" />
	<title><g:message code="title.homepage" args="[entityName]" /></title>
</head>
<body class="">
    <div id="tour-page">
        <div id="tour-section" class="row">
            <section id="break-crumb" class="row title-b">
                <article class="col-md-12 col-sm-12 col-xs-12">
                    <a href="index.html">
                        <img src="${resource(dir:'images/newuiimg',file:'icon-home-black.png') }" alt="home" />
                        Trang chủ
                    </a>
                    <span class="">&gt;</span>
                    <a href="tour-cate.html" class="current">Du lịch</a>
                </article>
            </section>
            <section class="row">
              <g:include view="layouts/responsivewebpart/_tourCategory.gsp"/>
                <article id="tour-cate-details" class="col-md-9 col-sm-9 col-xs-9">
                    <div class="bg-type-2 region-type-2">
                        <img src="${resource(dir:'images/newuiimg',file:'quoc-te.png') }" alt="" width="32" height="32" />
                        <span class="title title-upper title-b">${bigCategory}</span>
                        <div class="line line-horizontal box-margin-10"></div>

                        <ul class="tree-img-130">
                         <g:each in="${listCategory}" status="i" var="listCategory">
	                            <li>
	                                <div class="img-tours">
	                                    <a href="tourDetail?id=${listCategory.id }">${StringUtils.substringBetween(listCategory.image, '<p>', '</p>')}</a>
	                                </div>
	                                <div class="detail-news">
	                                    <a href="tourDetail?id=${listCategory.id }" class="title title-b">${listCategory.name}</a>
	                                    <div class="description">
	                                        <div class="tour-address">${listCategory.duration?"Thời gian:":"" }  ${listCategory.duration}</div>
	                                        <div class="tour-time"></div>
	                                        <div class="tour-start">${listCategory.startLocation?"Khởi hành:":"" }  ${listCategory.startLocation}</div>
	                                        <div>${listCategory.price?"Giá:":""}<span class="tour-price">${listCategory.price }</span></div>
	                                    </div>
	                                </div>
	                                <div class="clearfix"></div>
	                            </li>
                            </g:each>
                        </ul>
                    </div>
                </article>
            <div class="paginateButtons" align="right">
				<g:paginate	
					next="Forward" prev="Back"
					max="8" maxsteps="8"
					controller="tour" action="list"
					total="${total}" params="[category:cat]" />
				</div>
            </section>
    </div>
    </div>
</body>
</html>
