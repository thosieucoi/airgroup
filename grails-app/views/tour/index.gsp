<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@ page import="com.airgroup.domain.Location"%>
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
				<g:link controller="home" action="index"><img
					src="${resource(dir:'images/img',file:'icon-home-black.png')}"
					alt="home" />Trang chủ</g:link>
                    <span class="">&gt;</span>
                    <a href="#" class="current">Du lịch</a>
                </article>
            </section>
            <section class="row">
                <g:include view="layouts/responsivewebpart/_tourCategory.gsp"/>
                <article id="tour-cate-details" class="col-md-9 col-sm-9 col-xs-9">
                    <div class="bg-type-2 region-type-2">
                        <img src="${resource(dir:'images/newuiimg',file:'quoc-te.png') }" alt="" width="32" height="32" />
                        <span class="title title-upper title-b"><g:link controller="tour" action="list" params="[category:'DomesticNews']">Tin trong nước</g:link></span>
                        <div class="line line-horizontal box-margin-10"></div>

                        <ul class="tree-img-130 tree-two-cols">
                        	<g:each in="${domesticNews}" status="i" var="tourInstance">
	                            <li>
	                                <div class="img-tours">
	                                    <a href="tourDetail?id=${tourInstance.id }">${StringUtils.substringBetween(tourInstance.image, '<p>', '</p>')}</a>
	                                </div>
	                                <div class="detail-news">
	                                    <g:link controller="tour" action="tourDetail" params="[id:tourInstance.id]" class="title title-b">${tourInstance.name}</g:link>
	                                    <div class="description">
	                                        <div class="tour-address">${tourInstance.code?"Mã Tour:":"" } ${tourInstance.code}</div>
	                                        <div class="tour-time">${tourInstance.duration?"Thời gian:":"" }  ${tourInstance.duration}</div>
	                                        <div>${tourInstance.price?"Giá:":""} <span class="tour-price">${tourInstance.price}</span></div>
	                                    </div>
	                                </div>
	                                <div class="clearfix"></div>
	                            </li>
                            </g:each>
                        </ul>

                        <div class="space-40"></div>

                        <img src="${resource(dir:'images/newuiimg',file:'quoc-te.png') }" alt="" width="32" height="32" />
                        <span class="title title-upper title-b"><g:link controller="tour" action="list" params="[category:'InternationalNews']">Tin quốc tế</g:link></span>
                        <div class="line line-horizontal box-margin-10"></div>

                        <ul class="tree-img-130 tree-two-cols">
	                        <g:each in="${internationalNews}" status="i" var="tourInstance">
		                            <li>
		                                <div class="img-tours">
		                                    <a href="tourDetail?id=${tourInstance.id }">${StringUtils.substringBetween(tourInstance.image, '<p>', '</p>')}</a>
		                                </div>
		                                <div class="detail-news">
		                                    <g:link controller="tour" action="tourDetail" params="[id:tourInstance.id]" class="title title-b">${tourInstance.name}</g:link>
		                                    <div class="description">
		                                        <div class="tour-address">${tourInstance.code?"Mã Tour:":"" } ${tourInstance.code}</div>
	                                        <div class="tour-time">${tourInstance.duration?"Thời gian:":"" }  ${tourInstance.duration}</div>
	                                        <div>${tourInstance.price?"Giá:":""} <span class="tour-price">${tourInstance.price}</span></div>
		                                    </div>
		                                </div>
		                                <div class="clearfix"></div>
		                            </li>
	                           </g:each>
                        </ul>

                        <div class="space-20"></div>
                    </div>
                    <div class="space-10"></div>

                    <div class="bg-type-2 region-type-2">
                        <img src="${resource(dir:'images/newuiimg',file:'quoc-te.png') }" alt="" width="32" height="32" />
                        <span class="title title-upper title-b"><g:link controller="tour" action="list" params="[category:'Special']">Tour đặc biệt</g:link></span>
                        <div class="line line-horizontal box-margin-10"></div>

                        <ul class="tree-img-130 tree-two-cols">
	                        <g:each in="${special}" status="i" var="tourInstance">
		                            <li>
		                                <div class="img-tours">
		                                	<a href="tourDetail?id=${tourInstance.id }">${StringUtils.substringBetween(tourInstance.image, '<p>', '</p>')}</a>
		                                </div>
		                                <div class="detail-news">
		                                    <g:link controller="tour" action="tourDetail" params="[id:tourInstance.id]" class="title title-b">${tourInstance.name}</g:link>
		                                    <div class="description">
		                                        <div class="tour-address">${tourInstance.code?"Mã Tour:":"" } ${tourInstance.code}</div>
	                                        <div class="tour-time">${tourInstance.duration?"Thời gian:":"" }  ${tourInstance.duration}</div>
	                                        <div>${tourInstance.price?"Giá:":""} <span class="tour-price">${tourInstance.price}</span></div>
		                                    </div>
		                                </div>
		                                <div class="clearfix"></div>
		                            </li>
	                           </g:each>
                        </ul>
                    </div>
                    <div class="space-10"></div>
                    <div class="bg-type-2 region-type-2">
                        <img src="${resource(dir:'images/newuiimg',file:'quoc-te.png') }" alt="" width="32" height="32" />
                        <span class="title title-upper title-b"><g:link controller="tour" action="list" params="[category:'TourList']">Trong nước & quốc tế</g:link></span>
                        <div class="line line-horizontal box-margin-10"></div>

                        <ul class="tree-img-130 tree-two-cols">
	                        <g:each in="${tourInstanceList}" status="i" var="tourInstance">
		                            <li>
		                                <div class="img-tours">
		                                   <a href="tourDetail?id=${tourInstance.id }">${StringUtils.substringBetween(tourInstance.image, '<p>', '</p>')}</a>
		                                </div>
		                                <div class="detail-news">
		                                    <g:link controller="tour" action="tourDetail" params="[id:tourInstance.id]" class="title title-b">${tourInstance.name}</g:link>
		                                    <div class="description">
		                                        <div class="tour-address">${tourInstance.code?"Mã Tour:":"" } ${tourInstance.code}</div>
	                                        <div class="tour-time">${tourInstance.duration?"Thời gian:":"" }  ${tourInstance.duration}</div>
	                                        <div>${tourInstance.price?"Giá:":""} <span class="tour-price">${tourInstance.price}</span></div>
		                                    </div>
		                                </div>
		                                <div class="clearfix"></div>
		                            </li>
	                           </g:each>
                        </ul>
                    </div>
                </article>
		            </section>
                </div>
      		</div>
</body>
</html>