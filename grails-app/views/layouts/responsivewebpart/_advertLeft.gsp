<sec:ifNotLoggedIn>
	<g:if test="${!params['controller'].equals('login')}">
	<div id="customercareform">
		<aside id="adv-left" class="hidden-xs hotlineRight">
			<div id="tour-contacts" class="bg-type-2 contentHotline">
				<h5>Đăng nhập hoặc đăng ký</h5>
				<g:link controller="login"><input type="button" class="_6wh _6j _3ma mvm _58mi"  value="Đăng nhập"/></g:link>
				<g:link controller="register"><input type="button" class="_6wl _6j _3ma mvm _58mi" value="Đăng ký"/></g:link>
			</div>
		</aside>
	</div>
	</g:if>
</sec:ifNotLoggedIn>
<script>
	$("#customercareclose").click(function(){$("#customercareform").hide();});
</script>
<style>
._6wh {
	background: -webkit-gradient(linear, center top, center bottom, from(#354c8c), to(#354c8c));
	background: -webkit-linear-gradient(#354c8c, #354c8c);
	background-color: #354c8c;
	-webkit-box-shadow: inset 0 1px 1px #354c8c;
	border-color: #354c8c #354c8c #354c8c;
}

._6wl {
	background: -webkit-gradient(linear, center top, center bottom, from(#67ae55), to(#578843));
	background: -webkit-linear-gradient(#67ae55, #578843);
	background-color: #69a74e;
	-webkit-box-shadow: inset 0 1px 1px #a4e388;
	border-color: #3b6e22 #3b6e22 #2c5115;
}

._3ma {
	font-family: 'Freight Sans Bold', 'lucida grande',tahoma,verdana,arial,sans-serif !important;
	font-weight: normal !important;
	text-rendering: optimizelegibility;
}

._6j {
	border: 1px solid;
	-webkit-border-radius: 5px;
	color: #fff;
	cursor: pointer;
	display: inline-block;
	letter-spacing: 1px;
	position: relative;
	text-shadow: 0 1px 2px rgba(0,0,0,.5);
}

.mvm {
	margin-top: 10px;
	margin-bottom: 10px;
}

._58mi {
	min-width: 150px;
	padding: 7px 20px;
	text-align: center;
}

</style>