<sec:ifNotLoggedIn>
	<g:if test="${!params['controller'].equals('login')}">
	<div id="customercareform">
		<div id ="customercareclose" data-close="x"></div>
		<div id="WFItem9272103" class="wf-formTpl">
		    <form accept-charset="utf-8" action="https://app.getresponse.com/add_contact_webform.html?u=pCKl"
		    method="post">
		        <div class="wf-box">
		            <div id="WFIheader" class="wf-header el" style="height: 22px; display:  none !important;">
		                <div class="actTinyMceElBodyContent">
		                    <p>
		                        <span style="font-size: 24px !important;">Headline</span>
		                    </p>
		                </div>
		                <em class="clearfix clearer"></em>
		            </div>
		            <div id="WFIcenter" class="wf-body">
		                <ul class="wf-sortable" id="wf-sort-id">
		                    <li class="wf-privacy" rel="undefined" style="display:  block !important; text-align: center;">
		                        <div class="wf-contbox">
		                            <div>
		                                <a class="wf-privacy wf-privacyico" href="#">Ahotua mang giá trị tốt nhất cho khách hàng, đăng ký tham gia để nhận những món quà ý nghĩa của chúng tôi</a>
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
		                                <input class="wf-input" type="text" name="name" data-placeholder="no"></input>
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
		                                <input class="wf-input wf-req wf-valid__email" type="text" name="email"></input>
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
		                                <input type="text" name="custom_Phone" class="wf-input wf-req wf-valid__required"></input>
		                            </div>
		                            <em class="clearfix clearer"></em>
		                        </div>
		                    </li>
		                    <li class="wf-submit" rel="undefined" style="display:  block !important;">
		                        <div class="wf-contbox">
		                            <div class="wf-inputpos">
		                                <input type="submit" class="wf-button" name="submit" value="Đăng ký" style="display:  inline !important; width: 94px !important;"></input>
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
		                                <span class="wf-poweredby wf-poweredbyico" value="temporary" style="display:  none !important;">
		                                    <a class="wf-poweredbylink wf-poweredby" href="http://www.getresponse.com/"
		                                    style="display:  inline !important; color: inherit !important;" target="_blank"
		                                    value="temporary">Email Marketing</a>by GetResponse</span>
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
		<p class="box-padding-10"></p>
	</div>
	</g:if>
</sec:ifNotLoggedIn>
<script>
	$("#customercareclose").click(function(){$("#customercareform").hide();});
</script>
