<article class="col-md-4 col-sm-4 col-xs-12" style="padding: 0">
	<div id="tim-chuyen-bay"
		class="tour-box bg-type-red box-border-radius-5 box-padding-20 box-small">
		<g:form name="submitForm" controller="flight"
			action="switchSearchPage" method="POST">
			<div
				class="book hdd bg-type-red box-border-radius-5 region-type-2 box-padding-10">
				<div class="title">
					<img
						src="${resource(dir:'images/img',file:'icon-tim-chuyen-bay.png') }"
						alt="Tìm chuyến bay" /> <span class="title title-upper title-b">Tìm
						chuyến bay</span>

					<div class="line line-horizontal"></div>
				</div>
				<div class="space-5"></div>
				<div class="line linehd">
					<table>
						<tr>
							<g:if test="${iday.equals('0') || imonth.equals('00')}">
								<td><input type="radio" name="flightType" id="twoWays" />
									<span> Khứ hồi</span></td>
								<td><input type="radio" name="flightType" id="oneWay"
									checked="checked" /> <span> Một chiều</span></td>
							</g:if>
							<g:elseif test="${!iday.equals('0') || !imonth.equals('00')}">
								<td><input type="radio" name="flightType" id="twoWays"
									checked="checked" /> <span> Khứ hồi</span> <input type="radio"
									name="flightType" id="oneWay" /> <span> Một chiều</span></td>
							</g:elseif>
						</tr>
					</table>

					<div class="control-collection">
						<div class="row">
							<div class="col-md-12 col-sm-12 col-xs-12">
								<label id="diem-di-label" for="diem-di-input"
									class="control-label">Điểm đi</label> <br /> <input id="gog"
									class="control-input" type="text" name="departureCode"
									value="${departureCode}" width="170px" />
							</div>

							<div class="col-md-12 col-sm-12 col-xs-12">
								<label id="diem-den-label" for="diem-den-input"
									class="control-label">Điểm đến</label> <br /> <input id="tog"
									class="control-input" type="text" name="arrivalCode"
									value="${arrivalCode}" width="170px" />
							</div>
						</div>

						<div class="row">
							<div class="col-md-12 col-sm-12 col-xs-12">
								<label id="ngay-di-label" for="ngay-di-input"
									class="control-label">Ngày đi</label> <br /> <select
									name="oday" id="oday" class="control-input" style="width: 58px">
								</select> <input id="hdnOday" type="hidden" value="${oday}"> <select
									name="omonth" id="omonth" class="control-input"
									style="width: 105px">
								</select> <input id="hdnOmonth" type="hidden" value="${omonth}">
							</div>

							<div id="departureField" class="col-md-12 col-sm-12 col-xs-12">
								<label id="ngay-ve-label" for="ngay-ve-input"
									class="control-label">Ngày về</label> <br /> <select
									name="iday" id="iday" class="control-input" style="width: 58px">
									<option value="0"></option>
								</select> <input id="hdnIday" type="hidden" value="${iday}"> <select
									id="imonth" name="imonth" class="control-input"
									style="width: 105px">
									<option value="0"></option>
								</select> <input id="hdnImonth" type="hidden" value="${imonth}">
							</div>
						</div>
						<div class="row">
							<div class="col-md-12 col-sm-12 col-xs-12">
								<span class="title title-upper title-b">Số lượng</span>
								<div class="line line-horizontal"></div>
							</div>
						</div>

						<div class="row">
							<div class="col-md-4 col-sm-4 col-xs-4">
								<label for="so-luong-nguoi-lon" class="control-label">Người
									lớn</label> <select name="adults" id="adults" class="control-input">
								</select>
								<div class="description title-while">(>12 tuổi)</div>
								<input id="hdnAdults" type="hidden" value="${adults}" />
							</div>
							<div class="col-md-4 col-sm-4 col-xs-4">
								<label for="so-luong-tre-em" class="control-label">Trẻ
									em</label> <select name="kids" id="kids" class="control-input">
								</select>
								<div class="description title-while">(2-12 tuổi)</div>
								<input id="hdnKids" type="hidden" value="${kids}">
							</div>
							<div class="col-md-4 col-sm-4 col-xs-4">
								<label for="so-luong-em-be" class="control-label">Em bé</label>
								<select name="infants" id="infants" class="control-input">
								</select> <input id="hdnInfants" type="hidden" value="${infants}">
								<div class="description title-while">(<2 tuổi)</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-4 col-sm-4 col-xs-4">
								<label for="customerPhoneNumber" class="control-label" style="width: 80px">Điện thoại:</label> <input id="customerPhoneNumber" class="control-input"
									style="width:185px" value="${phoneNumber}" />
							</div>
						</div>
						<div class="row">
							<div class="col-md-12 col-sm-12 col-xs-12">
								<div class="center-block">
									<button id="tim-lich-bay" type="submit" class="tour-button">
										<img
											src="${resource(dir:'images/newuiimg',file:'icon-search.png') }"
											alt="search" width="px" height="16px" alt="Tìm chuyến bay"/> <span>Tìm kiếm</span>
									</button>
								</div>
							</div>
						</div>
						<div id="validateMessage"></div>
					</div>
					<g:include view="flight/includes/_smallsearch.gsp" />
				</div>
			</div>
		</g:form>
	</div>
	<script type="text/javascript">
		$("input[name='flightType']").change(function() {
			if ($(this).attr("id") == "oneWay") {
				$("#departureField").hide();
				$("#departureField select").val(0)
			} else {
				var iday = jQuery("#hdnIday").val()
				var imonth = jQuery("#hdnImonth").val()
				jQuery('#iday').val(iday)
				jQuery('#imonth').val(imonth)
				$("#departureField").show();
			}
		});
	</script>
</article>
