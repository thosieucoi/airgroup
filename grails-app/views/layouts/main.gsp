<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<g:render template="/layouts/includes/head" />
<body>
	<script type="text/javascript">
		/* <![CDATA[ */
		var google_conversion_id = 1000656472;
		var google_custom_params = window.google_tag_params;
		var google_remarketing_only = true;
		/* ]]> */
	</script>
	<script type="text/javascript"
		src="//www.googleadservices.com/pagead/conversion.js">
		
	</script>
	<noscript>
		<div style="display: inline;">
			<img height="1" width="1" style="border-style: none;" alt=""
				src="//googleads.g.doubleclick.net/pagead/viewthroughconversion/1000656472/?value=0&amp;guid=ON&amp;script=0" />
		</div>
	</noscript>
	<div id="main">
		<div class="fix">
			<g:render template="/layouts/includes/header" />

			<div class="content">
				<g:layoutBody />
			</div>
			<div id="onsite">
				<h2>Hỗ trợ khách hàng</h2>
				<table class="online" id="callCenter">
				</table>
			</div>
			<div style="clear: both"></div>
		</div>
	</div>
	<g:render template="/layouts/includes/footer" />
	<script>
		jQuery(document)
				.ready(
						function($) {

							$('#banner-fade').bjqs({
								height : 416,
								width : 530,
								responsive : true
							});

							if ($("#reload").length > 0) {
								checkit = self.location.href;
								if (!checkit.match('##')) {
									window.location.replace(checkit + '#');
									document.location.reload();
								}
							}
							$
									.ajax({
										async : true,
										type : "GET",
										contentType : "application/json; charset=utf-8",
										url : "/home/listUsers",
										dataType : "json",
										success : function(jsonData) {
											var str = "";
											var list = new Array();
											for (i in jsonData) {
												var stt = Math
														.floor((Math.random() * jsonData.length));
												while (list.indexOf(stt) != -1) {
													stt = Math
															.floor((Math
																	.random() * jsonData.length))
												}
												list[i] = stt;
												if (!((jsonData[stt].yahoo == null
														&& jsonData[stt].skype == null && jsonData[stt].phoneNumber == null) || (jsonData[stt].yahoo == ''
														&& jsonData[stt].skype == '' && jsonData[stt].phoneNumber == ''))) {
													str += "<tr>";
													if (jsonData[stt].phoneNumber == null) {
														str += "<td><a href=''></a></td>";
													} else {
														str += "<td><a href=''>"
																+ jsonData[stt].phoneNumber
																+ " </a></td>";
													}
													if (jsonData[stt].skype != null
															&& jsonData[stt].skype != '') {
														str += "<td><a href='skype: "
																+ jsonData[stt].skype
																+ "?chat' title='Gọi skype'><img src='http://dulichhalong.net/wp-content/themes/halong/images/skype.png' /></a></td>";
													}
													if (jsonData[stt].yahoo != null
															&& jsonData[stt].yahoo != '') {
														str += "<td><a href='ymsgr:sendim?"
																+ jsonData[stt].yahoo
																+ " ' title='Gọi yahoo'><img src='http://opi.yahoo.com/online?u="
																+ jsonData[stt].yahoo
																+ "&m=g&t=1&l=us' /></a></td>";
													}

													str += "</tr>";
													str += "<tr><td colspan='3' style=' border-bottom:1px dotted #ccc'></td></tr>";
												}
											}
											$("#callCenter").append(str);
										}
									});

						});
	</script>
</body>
</html>
