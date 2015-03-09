<div class="col-lg-5 col-md-6 col-sm-7 col-xs-12">
				<article id="tim-chuyen-bay">
					<div class="tour-box">
						<g:form controller="flight" action="switchSearchPage"
							method="POST">
							<div class="title">
								<img src="${resource(dir:'images/newuiimg',file:'icon-tim-chuyen-bay.png') }" alt="Tìm chuyến bay" /> <span
									class="title title-upper title-b">Tìm chuyến bay</span>
								<input type="radio" name="flightType" id="twoWays" checked="checked" />
													<span> Khứ hồi</span></td>
								<input type="radio" name="flightType" id="oneWay" /> <span> Một chiều</span></td>
								<div class="line line-horizontal"></div>
							</div>
							<div class="control-collection">
								<div class="space-5"></div>
								<div class="row">
									<div class="col-md-6 col-sm-6 col-xs-6">
										<label id="diem-di-label" for="diem-di-input"
											class="control-label">Điểm đi</label> <input id="gog"
											onclick="select()" class="control-input" type="text"
											value="Hanoi (HAN)" name="departureCode" />
									</div>

									<div class="col-md-6 col-sm-6 col-xs-6">
										<label id="diem-den-label" for="diem-den-input"
											class="control-label">Điểm đến</label> <input id="tog"
											onclick="select()" class="control-input" type="text"
											value="Ho Chi Minh (SGN)" name="arrivalCode" />
									</div>
								</div>
								<div class="space-5"></div>

								<div class="row">
									<div class="col-md-6 col-sm-6 col-xs-6">
										<label id="ngay-di-label" for="ngay-di-input"
											class="control-label">Ngày đi</label> <br /> <select
											name="oday" id="oday" class="control-input"
											style="width: 45px; height: 25px;"></select> <select
											name="omonth" id="omonth" class="control-input"
											style="width: 120px; margin-left: 0px; height: 25px; margin-top: 5px"></select>
									</div>

									<div class="col-md-6 col-sm-6 col-xs-6">
										<label id="ngay-ve-label" for="ngay-ve-input"
											class="control-label">Ngày về</label> <br /> <select
											name="iday" id="iday" class="control-input"
											style="width: 45px; height: 25px;"><option value="0"></option></select>
										<select id="imonth" name="imonth" class="control-input"
											style="width: 120px; margin-left: 0px; height: 25px; margin-top: 5px">
											<option value="00" selected="selected"></option>
										</select>
									</div>
								</div>
								<div class="space-10"></div>

								<div class="row">
									<div class="col-md-12 col-sm-12 col-xs-12">
										<span class="title title-upper title-b">Số lượng</span>
										<div class="line line-horizontal"></div>
									</div>
								</div>
								<div class="space-5"></div>

								<div class="row">
									<div class="col-md-3 col-sm-3 col-xs-3">
										<label for="so-luong-nguoi-lon" class="control-label">Người
											lớn</label><br /> <select name="adults" id="adults"
											class="control-input" style="height: 25px;"></select>
										<div class="description title-while">(&gt; 12 tuổi)</div>
									</div>
									<div class="col-md-3 col-sm-3 col-xs-3">
										<label for="so-luong-tre-em" class="control-label">Trẻ
											em</label> <br /> <select name="kids" id="kids"
											class="control-input" style="height: 25px;"></select>
										<div class="description title-while">(2 - 12 tuổi)</div>
									</div>
									<div class="col-md-3 col-sm-3 col-xs-3">
										<label for="so-luong-em-be" class="control-label">Em
											bé</label><br /> <select name="infants" id="infants"
											class="control-input" style="height: 25px;"></select>
										<div class="description title-while">(&lt; 2 tuổi)</div>
									</div>
									<div class="col-md-3 col-sm-3 col-xs-3" style="width:100%;">
										<g:link controller="policy" action="guide">
											<img src="${resource(dir:'images/newuiimg',file:'guide.jpg') }" alt="hướng dẫn đặt vé"
													width="22px" height="22px" />
											<span style="color:white;font-weight: bold;text-decoration: underline;">Hướng dẫn đặt vé</span>
										</g:link>
									</div>
								</div>

								<div class="space-10"></div>

								<div class="row">
									<div class="col-md-12 col-sm-12 col-xs-12">
										<div class="center-block">
											<button id="tim-lich-bay" class="tour-button" type="submit">
												<img src="${resource(dir:'images/newuiimg',file:'icon-search.png') }" alt="Tìm chuyến bay"
													width="16px" height="16px" /> <span>TÌM KIẾM</span>
											</button>
										</div>
									</div>
								</div>
								<g:include view="flight/includes/_searchflight.gsp" />
							</div>

						</g:form>
					</div>
				</article>
</div>
			
	<script type="text/javascript">
		$("input[name='flightType']").change(function() {
			if ($(this).attr("id") == "oneWay") {
				$("#ngay-ve-label").hide();
				$("#iday").hide();
				$("#imonth").hide();
				$("#iday").val(0)
				$("#imonth").val(0)
			} else {
				$("#ngay-ve-label").show();
				$("#iday").show();
				$("#imonth").show();
			}
		});
	</script>

