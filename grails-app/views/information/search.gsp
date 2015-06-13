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
                    <g:link controller="information">Thông tin tiện ích</g:link>
                </article>
            </section>
            <section class="row">
                <article id="tour-cate-details" class="col-md-12 col-sm-12 col-xs-12">
                    <div class="bg-type-2 region-type-2">
                    <g:form controller="information" action="search" method="POST">
                    	<div class="search-box">
							<div class="search-box-left">
							    <select class="search-box-type" id="search-product-type" name="category">
									<g:if test="${cat.equals('Destination')}">
										<option value="Destination" selected="">Các địa điểm nổi tiếng</option>
									</g:if>
									<g:else>
										<option value="Destination">Các địa điểm nổi tiếng</option>
									</g:else>
									
									<g:if test="${cat.equals('Hotel')}">
										<option value="Hotel" selected="">Khách sạn</option>
									</g:if>
									<g:else>
										<option value="Hotel">Khách sạn</option>
									</g:else>
									
									<g:if test="${cat.equals('Taxi')}">
										<option value="Taxi" selected="">Các hãng Taxi</option>
									</g:if>
									<g:else>
										<option value="Taxi">Các hãng Taxi</option>
									</g:else>
									
									
									<g:if test="${cat.equals('Tour')}">
										<option value="Tour" selected="">Các tour du lịch</option>
									</g:if>
									<g:else>
										<option value="Tour">Các tour du lịch</option>
									</g:else>
									
							    </select>
							    <span role="status" aria-live="polite" class="ui-helper-hidden-accessible"></span>
							    <input type="text" onclick="select()" class="search-box-text ui-autocomplete-input" id="tog" autocomplete="off" placeholder="Tên thành phố" name="city" value="${city}">
							    <input type="submit" class="search-box-button" value="Tìm kiếm">
							</div>
							<g:include view="flight/includes/_searchflight.gsp" />
                    	</div>
                    </g:form>
                    
                        <img src="${resource(dir:'images/newuiimg',file:'quoc-te.png') }" alt="" width="32" height="32" />
                        <span class="title title-upper title-b">
	                        <g:if test="${cat.equals('Destination')}">
								Các địa điểm nổi tiếng
							</g:if>
							<g:if test="${cat.equals('Hotel')}">
								Khách sạn
							</g:if>
							<g:if test="${cat.equals('Taxi')}">
								Các hãng Taxi
							</g:if>
							<g:if test="${cat.equals('Tour')}">
								Các Tour du lịch
							</g:if>
	                         <g:if test="${city}" >
	                         : ${city}
	                         </g:if>
                        </span>
                        <div class="line line-horizontal box-margin-10"></div>

                        <ul class="tree-img-130">
                        	<g:if test="${listCategory.size != 0}">
	                        	<g:each in="${listCategory}" status="i" var="listCategory">
		                            <li>
		                                <div class="img-tours">
		                                    <g:link controller="information" action="details" params="[id:listCategory.id]" class="title title-b">${StringUtils.substringBetween(listCategory.image, '<p>', '</p>')}</g:link>
		                                </div>
		                                <div class="detail-news">
		                                    <g:link controller="information" action="details" params="[id:listCategory.id]" class="title title-b">${listCategory.title}</g:link>
		                                    <div class="description">
		                                        <div class="tour-address">${listCategory.introduction}</div>
		                                    </div>
		                                </div>
		                                <div class="clearfix"></div>
		                            </li>
	                        	</g:each>
	                        </g:if>
	                        <g:else>
	                        	<div class="detail-news">
	                        	 	<h5>Hiện tại chưa có dữ liệu phù hợp, mong quý khách vui lòng trở lại sau.</h5>
	                        	 </div>
	                        </g:else>
                        </ul>
                    </div>
                    
                    <div class="paginateButtons" align="right">
						<g:paginate	
							next="Forward" prev="Back"
							max="8" maxsteps="8"
							controller="information" action="search"
							total="${total}" params="[category:cat, city:city]" />
					</div>
					<div class="space-40"></div>	
                </article>
         </section>
    </div>
    </div>
</body>
</html>
