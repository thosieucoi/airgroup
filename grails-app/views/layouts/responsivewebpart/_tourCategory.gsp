<article id="tour-cate" class="col-md-3 col-sm-3 col-xs-12">
	<ul id="list-tour" class="menu-cate">
		<li><span class="title">Thông Tin</span>
			<ul class="sub-list">
				<li><g:link controller="information" action="search"
						params="[category:'Hotel']">Khách sạn</g:link></li>
				<li><g:link controller="information" action="search"
						params="[category:'Taxi']">Hãng Xe Taxi</g:link></li>
				<li><g:link controller="information" action="search"
						params="[category:'Destination']">Các Điểm Đến Hàng Đầu</g:link></li>
			</ul></li>
		<li><span class="title">Tiện Ích</span>
			<ul class="sub-list">
				<li><a href="http://www.agoda.com/">Đặt khách sạn</a></li>
				<li><g:link controller="information" action="search" params="[category:'PreprareForFly']">Chuẩn bị trước khi bay</g:link></li>
				<li><g:link controller="information" action="search" params="[category:'Guide']" >Hướng dẫn làm thủ tục bay</g:link></li>
				<li><g:link controller="information" action="search" params="[category:'MapOfAirport']">Sơ đồ Sân Bay</g:link></li>
			</ul>
		</li>
		
		<li><span class="title">Du Lịch</span>
			<ul class="sub-list">
				<li><a href="http://www.hanhtrinh-phuongdong.com/">Du lịch trong nước và quốc tế</a></li>
			</ul>
		</li>
	</ul>

	<div class="space-10"></div>

	<!-- <div class="adv-left">
		<a href="http://agoda.com"> <img
			src="${resource(dir:'images/newuiimg',file:'agoda.com.jpg') }" alt=""
			title="agoda.com" />
		</a>
	</div> -->
</article>