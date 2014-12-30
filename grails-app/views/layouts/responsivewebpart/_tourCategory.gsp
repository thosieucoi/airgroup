<article id="tour-cate" class="col-md-3 col-sm-3 col-xs-3">
	<ul id="list-tour" class="menu-cate">
		<li><span class="title">Trong nước</span>
			<ul class="sub-list">
				<li><g:link controller="tour" action="list"
						params="[category:'Special']">Các tour đặc biệt</g:link></li>
				<li><g:link controller="tour" action="list"
						params="[category:'North']">Miền Bắc</g:link></li>
				<li><g:link controller="tour" action="list"
						params="[category:'Middle']">Miền Trung</g:link></li>
				<li><g:link controller="tour" action="list"
						params="[category:'South']">Miền Nam</g:link></li>
			</ul></li>
		<li><span class="title">Quốc tế</span>
			<ul class="sub-list">
				<li><g:link controller="tour" action="list"
						params="[category:'Asean']">Đông Nam Á</g:link></li>
				<li><g:link controller="tour" action="list"
						params="[category:'Asia']">Châu Á</g:link></li>
				<li><g:link controller="tour" action="list"
						params="[category:'Euro']">Châu Âu</g:link></li>
				<li><g:link controller="tour" action="list"
						params="[category:'America']">Châu Mỹ</g:link></li>
				<li><g:link controller="tour" action="list"
						params="[category:'Africa']">Châu Phi</g:link></li>
			</ul></li>
		<li><span class="title">Tin du lịch</span>
			<ul class="sub-list">
				<li><g:link controller="tour" action="list"
						params="[category:'Domestic news']">Tin trong nước</g:link></li>
				<li><g:link controller="tour" action="list"
						params="[category:'International']">Tin quốc tế</g:link></li>
			</ul></li>
	</ul>

	<div class="space-10"></div>

	<div class="adv-left">
		<a href="http://agoda.com"> <img
			src="${resource(dir:'images/newuiimg',file:'agoda.com.jpg') }" alt=""
			title="agoda.com" />
		</a>
	</div>
</article>