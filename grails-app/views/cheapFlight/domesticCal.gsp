<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="responsivemasterpage" />
	<g:set var="entityName"
		value="${message(code: 'localList.label', default: 'Chuyen Bay Noi Dia')}" />
	<title><g:message code="title.homepage" args="[entityName]" /></title>
</head>
<body>

	<section id="break-crumb" class="row title-b hidden-xs">
		<a href='javascript:history.go(-1)'>Tìm vé</a> > <a class="selected">Lựa
			chọn vé</a> > <span style="color: #333; font-weight: normal;">Thông
			tin chuyến bay</span> > <span style="color: #333; font-weight: normal;">Thanh
			toán</span> > <span style="color: #333; font-weight: normal;">Xác
			nhận</span>
	</section>
	<section class="row">
		<article id="chon-chuyen-bay-1" class="col-md-8 col-sm-8 col-xs-12">
			<p>
				<h1>CHỌN CHUYẾN BAY</h1>
			</p>
			<div>
				<form name="myForm" id="1000">
				<input type="radio" name="fareType" value="oneway" checked="checked"/>&nbsp;Bay 1 chiều
				<input type="radio" name="fareType" value="roundtrip"/>&nbsp;Bay khứ hồi
				</form>
			</div>
			<br>
			<div id="fullcalendarOneway" style="width: 300px"></div>
			<span id="fullcalendarRoundTrip" style="width: 300px; float: right; margin-top: -307px; margin-right: 5px;"></span>
			<div id="cheapFareBookingOneWay"></div>
		</article>
		<g:include view="layouts/responsivewebpart/_searchFlightForm.gsp" />
	</section>
</body>
</html>
