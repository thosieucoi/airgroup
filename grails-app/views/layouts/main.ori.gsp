<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<g:javascript library="application" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, 	maximum-scale=1.0, user-scalable=0">
<meta name="keywords" content="">
<meta name="description" content="{TITLE}">
<link rel="stylesheet" type="text/css"
	href="${resource(dir:'css',file:'reset.css')}">
<link rel="stylesheet" type="text/css"
	href="${resource(dir:'css',file:'style.css')}">
<link rel="stylesheet" type="text/css"
	href="${resource(dir:'css',file:'bjqs.css')}">
<link rel="stylesheet" type="text/css"
	href="${resource(dir:'css',file:'slide.css')}">
<!-- load jQuery and the plugin -->
<script src="http://code.jquery.com/jquery-1.7.1.min.js"></script>
<script src="${resource(dir:'js',file:'bjqs-1.3.min.js')}"></script>
</head>
<body>
	<div id="main">
		<div class="fix">
			<div class="header">
				<h1 class="logo">
					<a href="index.html"></a>
				</h1>
				<ul class="menu">
					<li><a href="index.html">TRANG CHỦ</a></li>
					<li><a href="BookingTicket.html">TÌM VÀ ĐẶT VÉ</a></li>
					<li><a href="BookingGuide.html">HƯỚNG DẪN ĐẶT VÉ</a></li>
					<li><a href="TourLogistic.html">TOUR & LOGITIC</a></li>
					<li><a href="Contact.html">LIÊN HỆ</a></li>
				</ul>

			</div>
			<div class="content">
				<g:layoutBody />
			</div>
			<div id="onsite">
				<img src="${resource(dir:'images',file:'Khong-hai-long.gif')}" />
			</div>
		</div>
		<div style="clear: both"></div>
		<div id="footer">
			<div class="menulist">
				<ul class="menufooter">
					<li><a href="">TRANG CHỦ</a></li>
					<li><a href="#">TÌM VÀ ĐẶT VÉ</a></li>
					<li><a href="">HƯỚNG DẪN ĐẶT VÉ</a></li>
					<li><a href="">TOUR & LOGITIC</a></li>
					<li><a href="">LIÊN HỆ</a></li>
				</ul>
			</div>
			<div class="tl">
				<img src="${resource(dir:'images',file:'logo.png')}" />
				<h6>
					Copy right 2012 - Công ty cổ phần Elines - Số ĐKKD: 0104511457 - Mã
					số thuế: 0104511457 <br> Văn phòng Hà Nội: Tầng 5, 110 Bà
					Triệu, Q. Hai Bà Trưng, Hà Nội
				</h6>
			</div>



		</div>

		<script>
			jQuery(document).ready(function($) {

				$('#banner-fade').bjqs({
					height : 385,
					width : 530,
					responsive : true
				});

			});
		</script>
</body>
</html>