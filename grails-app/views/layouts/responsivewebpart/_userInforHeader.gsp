<div class="rfloat _ohf">
	<ul class="_2exj clearfix" role="navigation">
		<li class="_4fn6 _3zm-">
			<a class="_2dpe _1ayn"
				href="javascript:void(0)" title="Profile" accesskey="2">
				<img class="_s0 _2dpc _rw img"
					src="${resource(dir:'images/newuiimg',file:'icon-account.png')}" alt="Profile">
				<span class="_2dpb"><sec:loggedInUserInfo field="username" /></span>
			</a>
		</li>
		<li class="_2pdh _3zm- _55bi _3zm- _55bh" id="u_0_f">
			<g:link controller="home" class="_1ayn" >Trang chủ
					<div class="_5ah- _5ahy">
						<div class="_5ahz"></div>
					</div>
			</g:link>
		</li>
		<li class="_3tmp openToggler" id="u_0_k">
			<a class="_1ayn" href="javascript:void(0)" id="userNavigationLabel">Settings</a>
					
			<div class="navigation" id="userNavigation" role="menu navigation"
					aria-label="Account Menu">
					<div class="jewelBeeperHeader">
						<div class="beeperNubWrapper">
							<div class="beeperNub"></div>
						</div>
					</div>
					<div id="userNavigationMenu" data-referrer="userNavigationMenu">
						<ul>
							<li id="u_0_37"><div class="pagesNavMenuTitle fsm fwn fcg">Ahotua:</div></li>
							<li class="menuDivider"></li>
							<li><a class="navSubmenu" href="javascript:void(0)" id="logoutLink">Đăng xuất</a></li>
							<li class="menuDivider"></li>
						</ul>
					</div>
				</div>
			<button class="hideToggler" type="button" style="right: 0px;"></button>
		</li>
	</ul>
</div>
<input type="hidden" id="displaySetting" value="none" />

<script type='text/javascript'>
	$("#userNavigationLabel").click(function(e) {
		if($("#displaySetting").val() == 'none')
		{
			$(".navigation").css('display', 'block');
			$("#displaySetting").val('block')
		}
		else 
		{
			$(".navigation").css('display', 'none');
			$("#displaySetting").val('none')
		}
		 e.stopPropagation();
	});


	$(".navigation").click(function(e){
	    e.stopPropagation();
	});

	$(document).click(function(){
		$(".navigation").css('display', 'none');
		$("#displaySetting").val('none')
	});
	
	
	$("#logoutLink").click(function() {
			var dataUrl = "${createLink(action:'index' ,controller:'logout')}";  
			jQuery.ajax({
				type : 'POST',
				url :  dataUrl ,
				success : function(response, textStatus)
				{
					location.reload();
				},
				error : function(
				XMLHttpRequest,
				textStatus,
				errorThrown) {
				}
			});
		});
</script>

<style>
._ohf {
	float: right;
}

._2exj {
	height: 22px;
	margin: 10px 0 10px 5px;
}

.clearfix {
	zoom: 1;
}

._4fn6 {
	height: 22px;
	line-height: 22px;
	padding: 0 6px 0 0;
}

._3zm-, ._2wnm, ._3tmp {
	float: left;
}

._3zm-, ._2wnm {
	display: block;
	height: 22px;
}

a._2dpe {
	padding-left: 4px;
}

._3zm- a {
	border-right: none;
	font-family: 'Helvetica Neue', Helvetica, Arial, 'lucida grande',tahoma,verdana,arial,sans-serif;
	-webkit-font-smoothing: antialiased;
	margin-left: 2px;
	padding-left: 9px;
	padding-right: 1px;
	text-shadow: 0 -1px rgba(0, 0, 0, .5);
}

._1ayn {
	background-color: transparent;
	color: #fff;
	display: inline-block;
	font-size: 12px;
	font-weight: bold;
	height: 27px;
	line-height: 27px;
	margin-top: -3px;
	position: relative;
	text-decoration: none;
	vertical-align: top;
}

html ._2dpc {
	border: 0;
	-webkit-border-radius: 0;
	height: 19px;
	margin: 4px 0 0;
	width: 19px;
}

._rw {
	height: 50px;
	width: 50px;
}

._2dpb {
	display: inline-block;
	max-width: 175px;
	overflow: hidden;
	padding-left: 8px;
	padding-right: 0;
	text-overflow: ellipsis;
	vertical-align: top;
	white-space: nowrap;
}

._55bh {
	position: relative;
}

._2pdh {
	padding-right: 6px;
}


._1ayn {
	background-color: transparent;
	color: #fff;
	display: inline-block;
	font-size: 12px;
	font-weight: bold;
	height: 27px;
	line-height: 27px;
	margin-top: -3px;
	position: relative;
	text-decoration: none;
	vertical-align: top;
}


._2pdh a {
	padding: 0 1px 0 11px;
}

._3zm- a {
	border-right: none;
	font-family: 'Helvetica Neue', Helvetica, Arial, 'lucida grande',tahoma,verdana,arial,sans-serif;
	-webkit-font-smoothing: antialiased;
	margin-left: 2px;
	padding-left: 9px;
	padding-right: 1px;
	text-shadow: 0 -1px rgba(0, 0, 0, .5);
}

._56lq {
	float: left;
	width: 12px;
}

._3t_z {
	height: 40px;
	margin-top: -5px;
	padding: 0 7px;
}

._4962 {
	float: left;
	margin-right: -1px;
	position: relative;
}

._3nzl a.jewelButton {
	background-size: auto;
	background-position: 0 -577px;
}

._4962 a.jewelButton {
	height: 27px;
	margin: 0;
	padding: 2px;
	width: 27px;
}

._3tmp {
	position: relative;
}

._1ayn {
	background-color: transparent;
	color: #fff;
	display: inline-block;
	font-size: 12px;
	font-weight: bold;
	height: 27px;
	line-height: 27px;
	margin-top: -3px;
	position: relative;
	text-decoration: none;
	vertical-align: top;
}

._2exj #userNavigation {
	background: rgba(255,255,255,.98);
	-webkit-background-clip: padding-box;
	background-color: #fff;
	border: 1px solid #bdc1c9;
	border: 1px solid rgba(100, 100, 100, .4);
	-webkit-border-radius: 3px;
	-webkit-box-shadow: 0 3px 8px rgba(0, 0, 0, .25);
	top: 22px;
}

._3tmp .navigation {
	display: none;
	max-width: 400px;
	min-width: 200px;
}

._3tmp .contextualHelp, ._3tmp .navigation {
	background: #fff;
	border: 1px solid rgba(100, 100, 100, .4);
	border-bottom: 2px solid #333;
	-webkit-border-radius: 3px;
	-webkit-box-shadow: 0 3px 8px rgba(0, 0, 0, .25);
	padding: 6px 0;
	position: absolute;
	right: 0;
	top: 34px;
	z-index: 1;
}

._2exj #userNavigation .beeperNub {
	background-size: auto;
	background-position: -191px -101px;
	height: 11px;
	position: absolute;
	right: 3px;
	top: -11px;
	width: 20px;
}

ul {
	list-style-type: none;
	margin: 0;
	padding: 0;
}

._3tmp .pagesNavMenuTitle {
	padding: 0 22px 6px 22px;
}

._3tmp .navSubmenu {
	border-bottom: 1px solid #fff;
	border-top: 1px solid #fff;
	display: block;
	font-family: Helvetica, Arial, 'lucida grande',tahoma,verdana,arial,sans-serif;
	height: 20px;
	line-height: 20px;
	overflow: hidden;
	padding: 0 22px;
	text-overflow: ellipsis;
	white-space: nowrap;
}

.hideToggler {
	border: 0;
	height: 0;
	opacity: 0;
	overflow: hidden;
	pointer-events: none;
	position: absolute;
	width: 0;
}

._3tmp.openToggler {
}

.openToggler {
	z-index: 100;
}


.openToggler ._50__ {
	background-size: auto;
	background-position: -273px -116px;
}

#userNavigationMenu ul li:hover {
	background-color: #d51317;
}

.rfloat ul li a:hover, a:active, a:focus {
	color: #fff;
	background-color: #d51317;
}


.ellipsis {
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
}
</style>