<div id="tour-services" class="col-lg-9 col-md-9 col-sm-9 hidden-xs">
				<div class="row" style="font-size:9px">
					<div
						class="col-md-11 col-md-offset-1 col-sm-11 col-sm-offset-1 col-xs-11 col-xs-offset-1">
						<form  method='POST' id='ajaxLoginFormHeader' name='ajaxLoginFormHeader' >
						<ul>
							<li>
									<div class="content">
										Email <br />
										 <input type="text" name='j_username' id='username' class="inputtext"/> <br />
										 <input type='checkbox' value="1" checked="1" id='remember_me' name='_spring_security_remember_me' /> Duy trì đăng nhập
									</div>
							</li>
							<li>
									<div class="content">
										Mật khẩu <!-- (<a href="/forgotpassword" style="text-decoration: underline;">Quên mật khẩu?</a>) -->
										<br /> 
										<input type='password' name='j_password' id='password' class="inputpassword"/> <br />
										<ul class="social">
											<li class="faceloginheader" id="facebookButton" onclick="fbLoginClick()">Đăng nhập qua Facebook</li>
										</ul>
									</div>
							</li>
							<li>
									<div class="content">
										<br /> <input type="button" onclick='authAjaxHeader(); return false;' value="Đăng nhập" class="login"/>
									</div>
							</li>
						</ul>
						</form>
					</div>
					
				</div>
			</div>
			
	<script type='text/javascript'>
		function authAjaxHeader()
		{
			var formdata = $('#ajaxLoginFormHeader').serialize();
			var dataUrl = "${createLinkTo(dir: 'j_spring_security_check')}"      
			jQuery.ajax({
				type : 'POST',
				url :  dataUrl ,
				data : formdata,
				dataType : "json",
				success : function(response,textStatus)
				{
					emptyForm();
				   	if(response.success)
				   	{
				   		location.reload();
				   	}
				   	else
				   	{
				   		var redirectUrl="${ createLink(action:'auth' ,controller:'login') }" + '?login_attempt=1';
				        window.location.assign(redirectUrl);
				   	}
				},
				error : function(
				XMLHttpRequest,
				textStatus,
				errorThrown) {
					
				}
			});
		}

		function emptyForm()
		{
			$('#username').val('');
			$('#password').val('');
			$('#remember_me').val('');
		}
	</script>