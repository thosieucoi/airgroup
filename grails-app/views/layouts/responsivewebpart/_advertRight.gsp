	<aside id="adv-right" class="hidden-xs hotlineRight">
		<div id="tour-contacts" class="bg-type-2 contentHotline">
			<h5>Hotline Đặt Vé</h5>
			<p id="hotline-tree-contacts" class="hotlineNumber callCenter"></p>
			<p id="skype-tree-contacts" class="hotlineNumber skypeCenter"></p>
			
			<div class="FBG" >
				<p style="font-size: 11px;text-align: center;border-top: 1px dotted #d7d7d7; padding-top: 5px; margin: 0 10px;">Nhấn Like để nhận tin vé rẻ</p>
				<div style="padding:5px 0 0 40px;">
					<iframe src="//www.facebook.com/plugins/like.php?href=https%3A%2F%2Fwww.facebook.com%2Fpages%2FV%25C3%25A9-m%25C3%25A1y-bay-tr%25E1%25BB%25B1c-tuy%25E1%25BA%25BFn-Ahotua%2F1518289555126754&amp;width&amp;layout=button_count&amp;action=like&amp;show_faces=false&amp;share=false&amp;height=21" scrolling="no" frameborder="0" style="border:none; overflow:hidden; height:21px;" allowTransparency="true"></iframe>
				</div>
			</div>
		</div>
	</aside>
	
	<script>
		jQuery(document)
				.ready(
						function($) {

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
											var strHotline = "";
											var strSkype = "";
											var list = new Array();
											var j = 0;
											for (i in jsonData) {
												j++;
												if (!((jsonData[i].skype == null && jsonData[i].phoneNumber == null) || (jsonData[i].yahoo == ''
														&& jsonData[i].skype == '' && jsonData[i].phoneNumber == ''))) {
													if (jsonData[i].phoneNumber != null) {
														strHotline += "<span class='title-b'>"
																+ jsonData[i].phoneNumber
																+ " </span>";
													}
													if (jsonData[i].skype != null
															&& jsonData[i].skype != '') {
														strSkype += "<a href='skype: "
																+ jsonData[i].skype
																+ "?chat' title='Gọi skype'>Hỗ trợ " + j + "</a> <br/>";
													}
												}
											}
											$("#hotline-tree-contacts")
													.append(strHotline);
											$("#skype-tree-contacts")
											.append(strSkype);
										}
									});

						});
	</script>
