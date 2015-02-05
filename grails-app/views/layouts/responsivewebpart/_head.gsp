<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title><g:layoutTitle default="AHOTUA" /></title>
<link rel="icon" href="${resource(dir:'images/newuiimg',file:'icon-logo.png')}" type="image/x-icon">
<link rel="shortcut icon" href="${resource(dir:'images/newuiimg',file:'icon-logo.png')}" type="image/x-icon">
<g:javascript library="application" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="keywords" content="">
<meta name="description" content="{TITLE}">
<link rel="stylesheet" type="text/css"
	href="${resource(dir:'css/newuicss',file:'bootstrap.css') }">
<link rel="stylesheet" type="text/css"
	href="${resource(dir:'css/newuicss',file:'responsivestyle.css') }">
<link rel="stylesheet" type="text/css"
	href="${resource(dir:'css',file:'jquery-ui.css')}" />
<link rel="stylesheet" type="text/css"
	href="${resource(dir:'css/newuicss',file:'bootstrap-theme.min.css') }">
<link rel="stylesheet" type="text/css"
	href="${resource(dir:'css',file:'customresponsive.css') }">
<script src="${resource(dir:'js',file:'jquery-1.7.1.min.js')}"></script>
<script src="${resource(dir:'js',file:'bootstrap.min.js')}"></script>

<script src="https://connect.facebook.net/en_US/all.js#xfbml=1&status=0&appId=1535352726726454&version=v2.2"></script>

<script src="${resource(dir:'js',file:'social.js')}"></script>

<script src="${resource(dir:'js/newuijs',file:'tour.js')}"></script>
<script src="${resource(dir:'js',file:'jquery-ui-1.8.24.min.js')}"></script>

<script
	src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.js"></script>
<script src="${resource(dir:'js',file:'location.js')}"></script>
<script src="${resource(dir:'js',file:'flight-search-validation.js')}"></script>
<script src="${resource(dir:'js',file:'calendar-link.js')}"></script>
<style type="text/css">
iframe[name=google_conversion_frame] {
	display: none
}

.debook img {
	width: 100% !important;
	height: 100% !important;
}

#tim-chuyen-bay #tog,#gog,#customerPhoneNumber {
	width: 100%
}

ul li.vnair {
	margin-bottom: 5px;
	padding: 3px
}

ul li.vnair img {
	float: right
}

.bjqs-prev,.bjqs-next {
	display: none
}
.img-responsive, .thumbnail > img, .thumbnail a > img, .carousel-inner > .item > img, .carousel-inner > .item > a > img {
display: block;
width: 100%;
height: 350px;
}

.cheap-flight-header{
	color: #3399FF;
	font-weight: bold;
}
.logo{
	width: 100%;
	max-width: 115px;
	max-height: 70px;
	-moz-appearance: ;
}
.contact{
	width: 100%;
	max-width: 28px;
	min-width: 28px;
}
.atv-v{
	font-weight: bold;
}
#flightOutboundDate > div{
	border-right: 1px solid #f0f0f0;
	text-align: center;
}
#tour-page #tour-section #chon-chuyen-bay-1 .select .date-m{
	width: 100%;
}
.tour_slide{
	height: 100% !important;
}
</style>
<script type="text/javascript">

function validate() {
	var name = document.getElementById('person-name').value
	var phoneNumber = document.getElementById('person-phone').value
	if (name.trim() == '' || phoneNumber.trim() == '') {
		document.getElementById('error').innerHTML='Các trường không được trống'
	} else {
		document.searchOrder.submit()
	}
}

function validateEmail() {
	var email = document.getElementById('person-email').value
	var emailCheck = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	if (email.trim() == '') {
		document.getElementById('errorEmail').innerHTML='Email không được trống'		
	}
	else if(emailCheck.test(email) == false)
	{
		document.getElementById('errorEmail').innerHTML='Email không hợp lệ'
	}
	else {
		document.registerEmail.submit()
	}
}

function revalidate() {
	var name = document.getElementById('personname').value
	var phoneNumber = document.getElementById('personphone').value
	if (name.trim() == '' || phoneNumber.trim() == '') {
		document.getElementById('showerror').innerHTML='Các trường không được trống'
	} else {
		document.so.submit()
	}
}
</script>

<link rel="stylesheet" type="text/css" href="${resource(dir:'css/calendar',file:'jquery.fullcalendar.css') }">
<link rel="stylesheet" type="text/css" href="${resource(dir:'css/calendar',file:'jquery.qtip.css') }">
	
<script type='text/javascript' src='http://code.jquery.com/jquery-1.8.3.js'></script>
<script src="${resource(dir:'js/calendar',file:'jquery.fullcalendar.min.js')}"></script>
<script src="${resource(dir:'js/calendar',file:'jquery-ui.js')}"></script>
<script src="${resource(dir:'js',file:'cheap-flight-booking.js')}"></script>

<r:require module="jquery-ui" />

</head>

