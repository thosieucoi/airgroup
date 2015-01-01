<html>
  <head>
    <meta name="description" content="login">
    <meta name="layout" content="login">

    <title><g:message code="login.title.login"/></title>
    
    <style>
    	body{  
    		background: none repeat scroll 0 0 #F0F0F0;
    	}
    	.append-bottom {
		    margin-bottom: 1.5em;
		    text-align: center;
		}
		#loginSubmit{
			width: 145px;
		}
		.span-3{
			margin-top: 7px;
		}
		.span-5{
			margin-left: -10px;
		}
		.box{
			-webkit-border-radius:0 0 7px 7px;
			border-radius:0 0 7px 7px;
			-webkit-box-shadow: 1px 1px 3px rgba(50, 50, 50, 0.56);
			-moz-box-shadow:    1px 1px 3px rgba(50, 50, 50, 0.56);
			box-shadow:         1px 1px 3px rgba(50, 50, 50, 0.56);
		}
		#loginName,#loginPassword{padding:3px 0}
		#loginSubmit{  
			background: none repeat scroll 0 0 #007CC2;
		    border: 1px solid #08547C;
		    border-radius: 3px 3px 3px 3px;color:#fff; padding:3px 0; font-weight:bold
		}
    </style>
    
    <script type="text/javascript" src=""></script>
    <script type="application/javascript">
     $( function() {
        $('#loginName')[0].focus();
     })
    </script>

  </head>
  <body>
    <div class="container" style="margin-top:200px">
        <div class="prepend-8 span-8 append-8 last">
            <div class="container span-8 last box ui-corner-all">
                <div class="span-8 append-bottom last">
                    <img src="${resource(dir: 'images', file:'logo.png')}" width="70px" height="70px"/>
                </div>
			
                <div class="span-8 prepend-top last">
                    <g:if test="${flash.message}">
                         <div class="append-bottom ui-state-highlight">
                               ${flash.message}
                         </div>
                    </g:if>
	                    <form action="${createLinkTo(dir: 'j_spring_security_check')}" method="post">
		                    	<div class="span-3">
		                            <label for="loginName"><g:message code="login.label.userName"/></label>
		                        </div>
		                        <div class="span-5 last">
		                            <input type="text" name="j_username" id="loginName"/>
		                        </div>
		
		                        <div class="span-3">
		                            <label for="loginPassword"><g:message code="login.label.password"/></label>
		                        </div>
		                        <div class="span-5 last">
		                            <input type="password" name="j_password" id="loginPassword"/>
		                        </div>
		
		                        <div class="prepend-3 span-5 last">
		                            <input type="checkbox" name="_spring_security_remember_me" id="rememberMe"><label for="rememberMe"><g:message code="login.label.rememberMe"/></label>
		                        </div>
		                        
		                        <div class="prepend-3 prepend-top span-5 last">
	                            	<input type="submit" id="loginSubmit" class="button ui-corner-all ui-state-default ui-priority-primary ui-widget" value="${message(code: 'login.label.login', encodeAs:'HTML')}"/>
	                        	</div>
	                    </form>
                </div>
            </div>
        </div>
    </div>
  </body>
</html>