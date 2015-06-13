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
       <div id="break-crumb" class="row title-b">
            <article class="col-md-12 col-sm-12 col-xs-12">
					<g:link controller="home" action="index"><img
						src="${resource(dir:'images/img',file:'icon-home-black.png')}"
						alt="home" />Trang chủ</g:link>
                    <span class="">&gt;</span>
                    <a href="#" class="current">Thông tin tiện ích</a>
            </article>
        </div>
            
        <div class="row">
                <article id="tour-cate-details" class="col-md-12 col-sm-12 col-xs-12">
                    <div class="bg-type-2 region-type-2">
                    <g:form controller="information" action="search" method="POST">
                    	<div class="search-box">
							<div class="search-box-left">
							    <select class="search-box-type" id="search-product-type" name="category">
							        <option value="Destination" selected="">Các địa điểm nổi tiếng</option>
							        <option value="Hotel">Khách sạn</option>
							        <option value="Taxi">Các hãng Taxi</option>
							        <option value="Tour">Các tour du lịch</option>
							    </select>
							    <span role="status" aria-live="polite" class="ui-helper-hidden-accessible"></span>
							    <input type="text" onclick="select()" class="search-box-text ui-autocomplete-input" id="tog" autocomplete="off" placeholder="Tên thành phố" name="city">
							    <input type="submit" class="search-box-button" value="Tìm kiếm">
							</div>
							<g:include view="flight/includes/_searchflight.gsp" />
                    	</div>
                    </g:form>
                        <img src="${resource(dir:'images/newuiimg',file:'quoc-te.png') }" alt="" width="32" height="32" />
                        <span class="title title-upper title-b"><a href="javascript:void(0);" >Các địa điểm nổi tiếng</a></span>
                        <div class="line line-horizontal box-margin-10"></div>

                        <ul class="tree-img-130 tree-two-cols">
                        	<g:each in="${destinations}" status="i" var="tourInstance">
	                            <li>
	                                <div class="img-tours">
	                                    <g:link controller="information" action="details" params="[id:tourInstance.id]" class="title title-b">${StringUtils.substringBetween(tourInstance.image, '<p>', '</p>')}</g:link>
	                                </div>
	                                <div class="detail-news">
	                                    <g:link controller="information" action="details" params="[id:tourInstance.id]" class="title title-b">${tourInstance.title}</g:link>
	                                    <div class="description">
	                                    <div class="tour-address">${tourInstance.introduction}</div>
	                                    </div>
	                                </div>
	                                <div class="clearfix"></div>
	                            </li>
                            </g:each>
                        </ul>

                        <img src="${resource(dir:'images/newuiimg',file:'quoc-te.png') }" alt="" width="32" height="32" />
                        <span class="title title-upper title-b"><a href="javascript:void(0);" >Khách sạn</a></span>
                        <div class="line line-horizontal box-margin-10"></div>

                        <ul class="tree-img-130 tree-two-cols">
	                        <g:each in="${hotels}" status="i" var="tourInstance">
		                            <li>
		                                <div class="img-tours">
		                                    <g:link controller="information" action="details" params="[id:tourInstance.id]" class="title title-b">${StringUtils.substringBetween(tourInstance.image, '<p>', '</p>')}</g:link>
		                                </div>
		                                <div class="detail-news">
		                                    <g:link controller="information" action="details" params="[id:tourInstance.id]" class="title title-b">${tourInstance.title}</g:link>
		                                    <div class="description">
		                                    <div class="tour-address">${tourInstance.introduction}</div>
		                                    </div>
		                                </div>
		                                <div class="clearfix"></div>
		                            </li>
	                           </g:each>
                        </ul>

                        <img src="${resource(dir:'images/newuiimg',file:'quoc-te.png') }" alt="" width="32" height="32" />
                        <span class="title title-upper title-b"><a href="javascript:void(0);" >Các hãng Taxi</a></span>
                        <div class="line line-horizontal box-margin-10"></div>

                        <ul class="tree-img-130 tree-two-cols">
	                        <g:each in="${taxis}" status="i" var="tourInstance">
		                            <li>
		                                <div class="img-tours">
		                                	<g:link controller="information" action="details" params="[id:tourInstance.id]" class="title title-b">${StringUtils.substringBetween(tourInstance.image, '<p>', '</p>')}</g:link>
		                                </div>
		                                <div class="detail-news">
		                                    <g:link controller="information" action="details" params="[id:tourInstance.id]" class="title title-b">${tourInstance.title}</g:link>
		                                    <div class="description">
		                                    <div class="tour-address">${tourInstance.introduction}</div>
		                                    </div>
		                                </div>
		                                <div class="clearfix"></div>
		                            </li>
	                           </g:each>
                        </ul>
                    
                        <img src="${resource(dir:'images/newuiimg',file:'quoc-te.png') }" alt="" width="32" height="32" />
                        <span class="title title-upper title-b"><a href="javascript:void(0);" >Các Tour Du lịch</a></span>
                        <div class="line line-horizontal box-margin-10"></div>

                        <ul class="tree-img-130 tree-two-cols">
	                        <g:each in="${tours}" status="i" var="tourInstance">
		                            <li>
		                                <div class="img-tours">
		                                   <g:link controller="information" action="details" params="[id:tourInstance.id]" class="title title-b">${StringUtils.substringBetween(tourInstance.image, '<p>', '</p>')}</g:link>
		                                </div>
		                                <div class="detail-news">
		                                    <g:link controller="information" action="details" params="[id:tourInstance.id]" class="title title-b">${tourInstance.title}</g:link>
		                                    <div class="description">
		                                    <div class="tour-address">${tourInstance.introduction}</div>
		                                    </div>
		                                </div>
		                                <div class="clearfix"></div>
		                            </li>
	                           </g:each>
                        </ul>
                        <div class="space-40"></div>
                    </div>
                </article>
		  </div>
</body>
</html>