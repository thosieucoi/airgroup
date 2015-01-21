<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="com.airgroup.domain.Location"%>
<%@ page import="com.airgroup.domain.Tour"%>
<html>
<head>
	<meta name="layout" content="responsivemasterpage" />
	<g:set var="entityName" value="${message(code: 'Tour', default: 'Tour')}" />
</head>
<body>
    	<div class="nav hone">
        	<div class="left tour">
            	<h1><p><g:message code="tour.label.header"/></p></h1>
                <div class="touris">
		           <section class="row">
		                <article id="tour-cate" class="col-md-3 col-sm-3 col-xs-3">
		                    <ul id="list-tour" class="menu-cate">
		                        <li>
		                            <span class="title">Trong nước</span>
		                            <ul class="sub-list">
		                                <li>
		                                    <a href="tour-cate.html">Các tour đặc biệt</a>
		                                </li>
		                                <li>
		                                    <a href="tour-cate.html">Miền Bắc</a>
		                                </li>
		                                <li>
		                                    <a href="tour-cate.html">Miền Trung</a>
		                                </li>
		                                <li>
		                                    <a href="tour-cate.html">Miền Nam</a>
		                                </li>
		                            </ul>
		                        </li>
		                        <li>
		                            <span class="title">Quốc tế</span>
		                            <ul class="sub-list">
		                                <li>
		                                    <a href="tour-cate.html">Đông Nam Á</a>
		                                </li>
		                                <li>
		                                    <a href="tour-cate.html">Châu Á</a>
		                                </li>
		                                <li>
		                                    <a href="tour-cate.html">Châu Âu</a>
		                                </li>
		                                <li>
		                                    <a href="tour-cate.html">Châu Mỹ</a>
		                                </li>
		                                <li>
		                                    <a href="tour-cate.html">Châu Phi</a>
		                                </li>
		                            </ul>
		                        </li>
		                        <li>
		                            <span class="title">Tin du lịch</span>
		                            <ul class="sub-list">
		                                <li>
		                                    <a href="#">Tin trong nước</a>
		                                </li>
		                                <li>
		                                    <a href="#">Tin quốc tế</a>
		                                </li>
		                            </ul>
		                        </li>
		                    </ul>
		                </article>
		
		                <article id="tour-cate-details" class="col-md-9 col-sm-9 col-xs-9">
		                    <div class="bg-type-2 region-type-2">
		                        <img src="img/icon-tour-quoc-te.png" alt="" width="32" height="32" />
		                        <span class="title title-upper title-b">Tour đặc biệt</span>
		                        <div class="line line-horizontal box-margin-10"></div>
		                        <ul class="tree-img-130">
		                        	<g:each in="${tourInstanceList}" status="i" var="tourInstance">
			                            <li>
			                                <div class="img-news">
			                                    <img src="img/tour-news.jpg" width="130" height="80" alt="" />
			                                </div>
			                                <div class="detail-news">
			                                    <a href="tour-details.html" class="title title-b">${tourInstance.name}</a>
			                                    <div class="description">
			                                        <div class="tour-address">Mã Tour: ${tourInstance.id}</div>
			                                        <div class="tour-time">Thời gian: ${tourInstance.duration}</div>
			                                        <div>Giá: <span class="tour-price">${tourInstance.price} VNĐ</span></div>
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
                <div class="debook">
                	<g:each in="${tourIntroInstanceList}" var="tourIntroInstance">
                		${tourIntroInstance.introductionGeneral }
                	</g:each>
                </div>
      		</div>
       </div>
</body>
</html>