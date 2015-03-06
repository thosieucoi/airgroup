<sec:ifNotLoggedIn>
	<g:if test="${!params['controller'].equals('login')}">
	<div id="registerLeftPane">
		<aside id="adv-left" class="hidden-xs hotlineRight">
			<div id="registerLeft" class="contentHotline">
				<div id="registerclose" class="registerclose">
					<a href="javascript:void(0)"><h5 style="color: #d51317;" id="arrowLeft"> << </h5></a>
				</div>
				<h5 id="messageAhotua">Đăng ký để được giảm giá ngay trên mỗi chuyến bay</h5>
				<a href="javascript:void(0)"><input type="button"  onclick="fbLoginClick()" id="registerFacebook" class="_6wh _6j _3ma mvm _58mi"  value="Đăng ký qua facebook"/></a>
				<g:link controller="register"><input type="button" id="registerAhotua" class="_6wl _6j _3ma mvm _58mi" value="Đăng ký ngay"/></g:link>
			</div>
		</aside>
	</div>
	</g:if>
</sec:ifNotLoggedIn>
<script>
	j = 0;
	$("#registerclose").click(function(){
		if(j % 2 == 0)
		{	
			hideRegisterLeft();
		}
		else
		{
			showRegisterLeft();
		}
		j++;
	;});

	function hideRegisterLeft() {
		$("#registerclose").css('margin-left', '10px');
		$("#registerFacebook").css('display','none');
		$("#registerAhotua").css('display','none');
		$("#messageAhotua").css('display','none');
		$("#registerLeftPane").css('width','50px');
		$("#arrowLeft").html('>>');
	}

	function showRegisterLeft() {
		$("#registerclose").css('margin-left', '122px');
		$("#registerFacebook").css('display','block');
		$("#registerAhotua").css('display','block');
		$("#messageAhotua").css('display','block');
		$("#registerLeftPane").css('width','155px');
		$("#arrowLeft").html('<<');
	}
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
	padding: 5px 3px;
	text-align: center;
}

</style>