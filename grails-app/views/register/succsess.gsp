<!DOCTYPE html>
<html>
<head>
<meta name="description" content="login">
<meta name="layout" content="responsivemasterpage" />
<title><g:message code="login.title.login" /> Ahotua</title>
</head>
<body>

	<section id="break-crumb" class="row title-b">
		<article class="col-md-12 col-sm-12 col-xs-12">
			<g:link controller="home" action="index">
				<img src="${resource(dir:'images/img',file:'icon-home-black.png')}"
					alt="home" />Trang chủ</g:link>
			<span class="">&gt;</span> <a
				href="javascript(0)"
				class="current">Đăng ký thành công</a>
		</article>
	</section>

	<section class="row">
		<article class="col-md-9 col-sm-9 col-xs-12" style ="width: 100% !important">
		<div id="globalContainer" class="uiContextualLayerParent">
			<div class="fb_content clearfix " id="content" role="main">
				<div class="UIFullPage_Container">
					<div
						class="mvl ptm uiInterstitial login_page_interstitial uiInterstitialLarge uiBoxWhite">
						<div
							class="uiHeader uiHeaderBottomBorder mhl mts uiHeaderPage interstitialHeader">
							<div class="clearfix uiHeaderTop">
								<div>
									<h2 class="uiHeaderTitle" aria-hidden="true">Cảm ơn quý khách đã đăng ký thành công tài khoản với công ty cổ phần Ahotua!</h2>
									<h2>Ahotua luôn cam kết mang giá trị tốt nhất cho khách hàng</h2>
									<h2>Link nhanh đến <a href="https://accounts.google.com" style="color: #ff9900">Gmail</a> | <a href="https://login.yahoo.com" style="color: #ff9900">Yahoo mail</a></h2>
								</div>
							</div>
						</div>
						<!-- <div class="phl ptm uiInterstitialContent" style="display:none;">
							<div class="login_form_container">
								<div id="WFItem9272103" class="wf-formTpl">
										    <form accept-charset="utf-8" action="https://app.getresponse.com/add_contact_webform.html?u=pCKl"
										    method="post" id="getResponseForm">
										        <div class="wf-box">
										            <div id="WFIheader" class="wf-header el" style="height: 22px; display:  none !important;">
										                <div class="actTinyMceElBodyContent">
										                    <p>
										                        <span style="font-size: 24px !important;">Headline</span>
										                    </p>
										                </div>
										                <em class="clearfix clearer"></em>
										            </div>
										            <div id="WFIcenter" class="wf-body" >
										                <ul class="wf-sortable" id="wf-sort-id">
										                    <li class="wf-privacy" rel="undefined" style="display:  block !important; text-align: center;">
										                        <div class="wf-contbox">
										                            <div>
										                                <a class="wf-privacy wf-privacyico" href="http://www.getresponse.com/permission-seal?lang=en"
										                                target="_blank" style="height: 61px !important; display: inline !important;">Ahotua mang giá trị tốt nhất cho khách hàng, đăng ký tham gia để nhận những món quà ý nghĩa của chúng tôi</a>
										                            </div>
										                            <em class="clearfix clearer"></em>
										                        </div>
										                    </li>
										                    <li class="wf-name" rel="undefined" style="display:  block !important;">
										                        <div class="wf-contbox">
										                            <div class="wf-labelpos">
										                                <label class="wf-label">Họ và Tên</label>
										                            </div>
										                            <div class="wf-inputpos">
										                                <input class="wf-input wf-req wf-valid__required" type="text" name="name"
										                                data-placeholder="no" value="${CMSUserInstance?.name}"></input>
										                            </div>
										                            <em class="clearfix clearer"></em>
										                        </div>
										                    </li>
										                    <li class="wf-email" rel="undefined" style="display:  block !important;">
										                        <div class="wf-contbox">
										                            <div class="wf-labelpos">
										                                <label class="wf-label">Email:</label>
										                            </div>
										                            <div class="wf-inputpos">
										                                <input class="wf-input wf-req wf-valid__email" type="text" name="email" value="${CMSUserInstance?.username}"></input>
										                            </div>
										                            <em class="clearfix clearer"></em>
										                        </div>
										                    </li>
										                    <li class="wf-field__0" rel="text" style="display:  block !important;">
										                        <div class="wf-contbox">
										                            <div class="wf-labelpos">
										                                <label class="wf-label" id="4925703">Điện Thoại</label>
										                            </div>
										                            <div class="wf-inputpos">
										                                <input type="text" name="custom_Phone" class="wf-input" value="${CMSUserInstance?.phoneNumber}"></input>
										                            </div>
										                            <em class="clearfix clearer"></em>
										                        </div>
										                    </li>
										                    <li class="wf-submit" rel="undefined" style="display:  block !important;">
										                        <div class="wf-contbox">
										                            <div class="wf-inputpos">
										                                <input type="submit" class="wf-button" id="submitGetResponse" name="submit" value="Đăng ký" style="display:  inline !important; width: 94px !important;"></input>
										                            </div>
										                            <em class="clearfix clearer"></em>
										                        </div>
										                    </li>
										                    <li class="wf-counter" rel="undefined" style="display:  none !important;">
										                        <div class="wf-contbox">
										                            <div>
										                                <span style="padding: 4px 6px 8px 24px; background: url(https://app.getresponse.com/images/core/webforms/countertemplates.png) 0% 0px no-repeat;"
										                                class="wf-counterbox">
										                                    <span class="wf-counterboxbg" style="padding: 4px 12px 8px 5px; background: url(https://app.getresponse.com/images/core/webforms/countertemplates.png) 100% -36px no-repeat;">
										                                        <span class="wf-counterbox0" style="padding: 5px 0px;">subscribed:</span>
										                                        <span style="padding: 5px;" name="https://app.getresponse.com/display_subscribers_count.js?campaign_name=ahotua_customer_care&var=0"
										                                        class="wf-counterbox1 wf-counterq">0</span>
										                                        <span style="padding: 5px 0px;" class="wf-counterbox2"></span>
										                                    </span>
										                                </span>
										                            </div>
										                        </div>
										                    </li>
										                    <li class="wf-captcha" rel="undefined temporary" style="display:  none !important;">
										                        <div class="wf-contbox wf-captcha-1" id="wf-captcha-1" wf-captchaword="Enter the words above:"
										                        wf-captchasound="Enter the numbers you hear:" wf-captchaerror="Incorrect please try again"
										                        style="display:  block !important;"></div>
										                        <em class="clearfix clearer"></em>
										                    </li>
										                    <li class="wf-poweredby" rel="undefined temporary" style="display:  none !important; text-align: center;">
										                        <div class="wf-contbox">
										                            <div>
										                                <span class="wf-poweredby wf-poweredbyico" style="display:  none !important;">
										                                    <a class="wf-poweredbylink wf-poweredby" href="http://www.getresponse.com/"
										                                    style="display:  inline !important; color: inherit !important;" target="_blank">Email Marketing</a>by GetResponse</span>
										                            </div>
										                        </div>
										                    </li>
										                </ul>
										            </div>
										            <div id="WFIfooter" class="wf-footer el" style="height: 0px; display:  none !important;">
										                <div class="actTinyMceElBodyContent"></div>
										                <em class="clearfix clearer"></em>
										            </div>
										        </div>
										        <input type="hidden" name="webform_id" value="9272103" />
										    </form>
										</div>
										<script type="text/javascript" src="http://app.getresponse.com/view_webform.js?wid=9272103&mg_param1=1&u=pCKl"></script>							
							</div>
						</div>
				
				 -->
				
					</div>
				</div>
			</div>
		</div>
		</article>
	</section>

	<script type='text/javascript'>
		//$("#submitGetResponse").trigger("click");
	</script>
		
<style type="text/css">

.fbx #globalContainer {
	width: 981px;
}

#globalContainer {
	margin: 0 auto;
	position: relative;
	zoom: 1;
}

#content {
	margin: 0;
	outline: none;
	padding: 0;
	width: auto;
}

.UIFullPage_Container {
	width: 100%;
	margin: 0 auto;
}


div.login_page_interstitial {
	margin-bottom: 0;
}

.uiInterstitial {
	-webkit-border-radius: 4px;
	margin-left: auto;
	margin-right: auto;
}

.mvl {
	margin-bottom: 20px;
}

.ptm {
	padding-top: 10px;
}

.uiBoxWhite {
	background-color: #fff;
	border: 1px solid #ccc;
}

.uiInterstitial .interstitialHeader {
	border-color: #ccc;
	padding-bottom: .5em;
}

.uiHeaderPage {
}

.uiHeaderBottomBorder {
	border-bottom: 1px solid #aaa;
	padding-bottom: .5em;
}

.mhl {
	margin-left: 20px;
	margin-right: 20px;
}

.mts {
}

.clearfix {
	zoom: 1;
}

._ohf {
	float: right;
}

#facebook .accessible_elem {
	clip: rect(1px 1px 1px 1px);
	clip: rect(1px, 1px, 1px, 1px);
	height: 1px;
	overflow: hidden;
	position: absolute;
	width: 1px;
}

.uiHeaderPage .uiHeaderActions {
	margin-top: -1px;
}

.uiHeaderPage .uiHeaderTitle {
	line-height: 20px;
	min-height: 20px;
	padding-bottom: 2px;
	vertical-align: bottom;
}

.uiHeader .uiHeaderTitle {
	outline: none;
}

.uiHeader h2 {
	color: #1e2d4c;
	font-size: 16px;
}

.uiInterstitialContent {
	margin-bottom: 15px;
}

.phl {
	padding-left: 20px;
	padding-right: 20px;
}

.ptm {
	padding-top: 0px;
}

#facebook .hidden_elem {
	display: none !important;
}

.login_error_box {
	margin-top: 10px;
}

.pam {
	padding: 10px;
}

.uiBoxRed {
	background-color: #ffebe8;
	border: 1px solid #dd3c10;
}

.fcb {
	color: #333;
}

.fwb {
	font-weight: bold;
}

.fsl {
	font-size: 13px;
}

.login_page #loginform {
	clear: left;
	margin: auto;
	padding: 15px 0;
	text-align: left;
	width: 380px;
}

.form_row {
	padding: 10px 0 8px 0;
	text-align: left;
}

.form_row .login_form_label {
	display: block;
	float: left;
	padding: 3px 0;
	width: 100px;
}

.login_page #email {
	direction: ltr;
}

.form_row .inputtext, .inputpassword {
	width: 175px;
}

.persistent {
	padding: 3px 0 3px 100px;
}

.uiInputLabel {
	position: relative;
}

.uiInputLabelInput {
	margin: 0;
	padding: 0;
	position: absolute;
}

.uiInputLabel .uiInputLabelLabel {
	display: inline-block;
	margin-left: 17px;
	vertical-align: baseline;
}

.uiInputLabelLegacy label {
	color: #333;
	font-weight: normal;
}

#buttons {
	padding: 5px 0 0 100px;
	text-align: left;
}

#login_button_inline {
	float: left;
	margin-bottom: 5px;
	margin-right: 5px;
}

#buttons .uiButton {
	margin-right: 2px;
}

#buttons label {
	float: none;
	width: auto;
}

.uiButtonLarge, .uiButtonLarge .uiButtonText, .uiButtonLarge input {
	font-size: 13px;
	line-height: 16px;
}

.uiButtonLarge {
	height: 19px;
}

.uiButtonConfirm {
	background-size: auto;
	background-position: 0 -49px;
	background-color: #D80000;
	border-color: #2f477a #29447e #1a356e;
}

.uiButton {
	cursor: pointer;
	display: inline-block;
	font-size: 11px;
	font-weight: bold;
	line-height: 13px;
	padding: 2px 6px;
	text-align: center;
	text-decoration: none;
	vertical-align: top;
	white-space: nowrap;
}

#register_link {
	margin-top: 5px;
	float: left;
}

#register_link a {
	color: #3b5998;
	cursor: pointer;
	text-decoration: none;
}

.login_page #loginform p.reset_password {
	margin-bottom: 0;
	padding-bottom: 0;
}

.login_page #loginform p {
	line-height: 16px;
	margin: 10px 0;
	text-align: left;
}

.uiButtonText, .uiButton input {
	background: none;
	border: 0;
	color: #333;
	cursor: pointer;
	display: inline-block;
	font-family: 'lucida grande',tahoma,verdana,arial,sans-serif;
	font-size: 11px;
	font-weight: bold;
	line-height: 13px;
	margin: 0;
	padding: 1px 0 2px;
	white-space: nowrap;
	color: #fff;
}

</style>
</body>
</html>