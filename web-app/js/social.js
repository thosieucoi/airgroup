var fbBtnLoginClicked = false;
	window.fbAsyncInit = function () {
		FB.init({
		appId : '1535352726726454', //ahotua :1535352726726454 localhost 795469940527393
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
			   		var redirectUrl="/home";
			        window.location.assign(redirectUrl);
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
