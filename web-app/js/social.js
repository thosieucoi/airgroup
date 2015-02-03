var fbBtnLoginClicked = false;
	window.fbAsyncInit = function () {
		FB.init({
		appId : '1535352726726454',
		xfbml : true,
		version : 'v2.2'
	});

	FB.Event.subscribe('auth.authResponseChange', function (response) {
		if (response.status === 'connected') {
			// Logged into your app and Facebook.
			CheckFBLoginStatus();
		} else if (response.status === 'not_authorized') {
			// The person is logged into Facebook, but not your app.
			facebookLogin();
		} else {
			// The person is not logged into Facebook, so we're not sure if
		    // they are logged into this app or not.
			facebookLogin();
		}
	});
};

	function fbLoginClick() {
		fbBtnLoginClicked = true;
		CheckFBLoginStatus();
	}

	function CheckFBLoginStatus() {
		FB.getLoginStatus(function (response) {
			if (response.status === 'connected') {
				onFbloginSuccess();
			} else {
				facebookLogin();
			}
		});
	}
	
	function onFbloginSuccess() {
		FB.api('/me', function (response) {
			if (response.error != null) {
				facebookLogin();
			}
			else {
				if (fbBtnLoginClicked) {
					loginsignupface(response);
					fbBtnLoginClicked = false;
				}
			}
		});
	}

	function facebookLogin()
	{
		FB.login(function(response) {
			   // handle the response
		}, {scope: 'public_profile,email'});
	}

	function loginsignupface(response)
	{
		jQuery.ajax({
			type : 'POST',
			url :  "/register/loginsignupface" ,
			data : "sendData=" + JSON.stringify(response),
			dataType : "json",
			success : function(response)
			{
			   	if(response.success)
			   	{
			   		autoLogin(response.username, response.password);
			   	}
			   	else
			   	{
				   	if(response.username)
					{
				   		var redirectUrl="/login/auth?login_attempt=1&email=" + response.username;
				        window.location.assign(redirectUrl);
					}
					else
				   	{
				   		var redirectUrl="/login/auth";
				        window.location.assign(redirectUrl);
				   	}
			   	}
			},
			error : function(
			XMLHttpRequest,
			textStatus,
			errorThrown) {
				
			}
		});
	}

	function autoLogin(username, password)
	{
		var dataUrl = "/j_spring_security_check"      
		jQuery.ajax({
			type : 'POST',
			url :  dataUrl ,
			data : {j_username: username, j_password: password, _spring_security_remember_me: 1},
			dataType : "json",
			success : function(response,textStatus)
			{
			   	if(response.success)
			   	{
			   		var redirectUrl="/home";
			        window.location.assign(redirectUrl);
			   	}
			   	else
			   	{
			   		var redirectUrl="/login/auth";
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