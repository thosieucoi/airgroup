<footer>
<div id="footer">
	<ul class="menu row col-lg-10 col-lg-offset-1">
		<li class="item8"><g:link controller="contactUs" action="infor"><span style="color: #fff;">Liên hệ</span></g:link></li>
		<sec:ifNotLoggedIn>
			<li class="item8"><g:link controller="login"><span style="color: #fff;">Đăng nhập</span></g:link></li>
			<li class="item8"><g:link controller="register"><span style="color: #fff;">Đăng ký</span></g:link></li>
		</sec:ifNotLoggedIn>
		<br/>
		<li class="item8"><span style="color: #fff;">Copyright © 2015. Công ty Cổ Phần Ahotua Việt Nam</span></li>
		<li>
			<a href="https://www.facebook.com/pages/V%C3%A9-m%C3%A1y-bay-tr%E1%BB%B1c-tuy%E1%BA%BFn-Ahotua/1518289555126754">
				<img src="${resource(dir:'images/newuiimg',file:'Facebook-icon.png')}">
			</a>
		</li>
	</ul>
</div>
</footer>