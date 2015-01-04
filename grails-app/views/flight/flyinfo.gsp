<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<meta name="layout" content="responsivemasterpage" />
<title><g:message code="title.homepage" args="[entityName]" /></title>
</head>
<body>
	<section id="break-crumb" class="row title-b">
		<div class="col-sm-12 hidden-xs">
			<a href='javascript:history.go(-2)'>Tìm vé</a> > <a
				href='javascript:history.go(-1)'>Lựa chọn vé</a> > <a href=""
				class="selected">Thông tin chuyến bay</a> > <span
				style="color: #333; font-weight: normal;">Thanh toán</span> > <span
				style="color: #333; font-weight: normal;">Xác nhận</span>
		</div>
	</section>
	
	<section class="row">
		<article id="chon-chuyen-bay-1" class="col-md-8 col-sm-12 col-xs-12">
			<p class="lead text-center title-b">THÔNG TIN CHUYẾN BAY</p>
			<div class="infor-t">
				<h6>Thông tin vé</h6>
				<table class="fare-information">
					<tr>
						<td><span>Hành trình:<strong class="text-info">
									${session.parameters.departureCode} - ${session.parameters.arrivalCode}
							</strong></span></td>
						<td><span>Loại vé: <strong class="text-info">Phổ
									thông</strong></span></td>
						<td><span>Số lượng khách:<strong class="text-info">
									${ session.parameters.adults+' Người lớn'} ${ session.parameters.kids.toInteger()>0?(',' +session.parameters.kids+' Tre em'):''}
									${ session.parameters.infants.toInteger()>0?(',' +session.parameters.infants+' Tre so sinh'):''}
							</strong></span></td>
					</tr>
					<tr>
						<td><span>Ngày xuất phát:<strong class="text-info">
									${session.parameters.oday+"/"+session.parameters.omonth}/${(session.parameters.omonth.toInteger()<new Date().getMonth())?(new Date().getYear()+1900+1):(new Date().getYear()+1900)}
									</strong></span></td>
						<g:if
							test="${session.parameters.iday.toInteger()>0&&session.parameters.imonth.toInteger()>0}">
							<td><span>Ngày về: <strong class="text-info">
										${session.parameters.iday+"/"+session.parameters.imonth}/${(session.parameters.imonth.toInteger()<new Date().getMonth())?(new Date().getYear()+1900+1):(new Date().getYear()+1900)}
								</strong></span></td>
						</g:if>
					</tr>
				</table>
				<div id="fullfare"></div>
			</div>
			<g:if test="${session.parameters.isDomestic == 'true'}">
				<g:if
					test="${(airlineCodeOutbound.equals('VN') &&  airlineCodeInbound == null) || (airlineCodeOutbound.equals('VN') && airlineCodeInbound.equals('VN'))}">
					<div class="infor-t">
						<h6>Điều kiện giá vé</h6>
						<table class="fare-condition">
							<tr>
								<td><b>Hoàn Vé</b></td>
								<td><span> Trước ngày khởi hành: 300.000 VNĐ (Giai
										đoạn Tết Nguyên đán: 600.000 VNĐ). Trong và sau ngày khởi hành: 600.000
										VNĐ.</span></td>
							</tr>
							<tr>
								<td><b>Đổi Hành Trình</b></td>
								<td><span> Trước ngày bay , phí :300.000/vé
										(Giai đoạn Tết Nguyên đán: 600.000 VNĐ cộng chênh lệch giá vé). Trong và sau ngày
										khởi hành,phí :600.000/vé cộng chênh lệch giá vé.</span></td>
							</tr>
							<tr>
								<td><b>Đổi Ngày Giờ Chuyến Bay</b></td>
								<td><span> Trước ngày khởi hành , phí :300.000/vé
										(Giai đoạn Tết Nguyên đán: 600.000 VNĐ). Trong và sau ngày
										khởi hành,phí :600.000/vé cộng chênh lệch giá vé.</span></td>
							</tr>
							<tr>
								<td><b>Thời Hạn Dừng Tối Đa</b></td>
								<td><span>12 tháng.</span></td>
							</tr>
							<tr>
								<td><b>Điểm Cộng Dặm</b></td>
								<td><span> Không được cộng.</span></td>
							</tr>
							<tr>
								<td colspan="2"><span>Điều kiện trên  áp dụng cho các hạng đặt chỗ: M,K,L,Q,N,R). Các hạng: T, E khuyến mại không được phép hoàn, hủy, thay đổi. </span></td>
							</tr>
						</table>
					</div>
				</g:if>
				<g:elseif
					test="${(airlineCodeOutbound.equals('VJ') &&  airlineCodeInbound == null) || (airlineCodeOutbound.equals('VJ') && airlineCodeInbound.equals('VJ'))}">
					<div class="infor-t">
						<h6>Điều kiện giá vé</h6>
						<table class="fare-condition">
							<tr>
								<td><strong>Hoàn Vé</strong></td>
								<td><span> Không được phép.</span></td>
							</tr>
							<tr>
								<td><strong>Đổi Tên Hành Khách</strong></td>
								<td><span> Được phép - Thu phí: 330.000 VND.</span></td>
							</tr>
							<tr>
								<td><strong>Đổi Hành Trình</strong></td>
								<td><span> Được phép - Thu phí 330,000 cộng chênh lệch giá vé.</span></td>
							</tr>
							<tr>
								<td><strong>Đổi Ngày Giờ Chuyến Bay</strong></td>
								<td><span> Được phép - Thu phí: 330.000 VND cộng chênh lệch giá vé</span></td>
							</tr>
							<tr>
								<td><strong>Bảo lưu</strong></td>
								<td><span> Không được phép.</span></td>
							</tr>
							<tr>
								<td><strong>Thời hạn thay đổi (bao gồm thay đổi
										tên, ngày/chuyến bay, hành trình)</strong></td>
								<td><span> Trước  6 giờ so với giờ khởi hành.</span></td>
							</tr>
							<tr>
								<td><strong>Thay đổi chuyến bay</strong></td>
								<td><span> Được phép - Thu phí: 275.000 VND.</span></td>
							</tr>
						</table>
					</div>
				</g:elseif>
				<g:elseif
					test="${(airlineCodeOutbound.equals('BL') &&  airlineCodeInbound == null) || (airlineCodeOutbound.equals('BL') && airlineCodeInbound.equals('BL'))}">
					<div class="infor-t">
						<h6>Điều kiện giá vé</h6>
						<table class="fare-condition">
							<tr>
								<td><strong>Hoàn Vé</strong></td>
								<td><span> Không được phép.</span></td>
							</tr>
							<tr>
								<td><strong>Đổi Tên Hành Khách</strong></td>
								<td><span> Được phép - Thu phí: 330.000 VND.</span></td>
							</tr>
							<tr>
								<td><strong>Đổi Hành Trình</strong></td>
								<td><span> Được phép - Thu phí 330,000 cộng chênh lệch giá vé.</span></td>
							</tr>
							<tr>
								<td><strong>Đổi Ngày Giờ Chuyến Bay</strong></td>
								<td><span> Được phép - Thu phí: 330.000 VND cộng chênh lệch giá vé</span></td>
							</tr>
							<tr>
								<td><strong>Bảo lưu</strong></td>
								<td><span> Không được phép.</span></td>
							</tr>
							<tr>
								<td><strong>Thời hạn thay đổi (bao gồm thay đổi
										tên, ngày/chuyến bay, hành trình)</strong></td>
								<td><span> Trước  6 giờ so với giờ khởi hành.</span></td>
							</tr>
							<tr>
								<td><strong>Thay đổi chuyến bay</strong></td>
								<td><span> Được phép - Thu phí: 275.000 VND.</span></td>
							</tr>
						</table>
						<!-- <table class="fare-condition">
							<tr>
								<td><b>Thay đổi chuyến bay</b></td>
								<td><span> Được phép thay đổi trước giờ mở quầy làm
										thủ tục check-in, thu phí đổi + chênh lệch giá(nếu có).</span></td>
							</tr>
							<tr>
								<td><b>Nâng Hạng</b></td>
								<td><span> Được phép thay đổi trước giờ mở quầy làm
										thủ tục check-in, thu phí đổi + chênh lệch giá.</span></td>
							</tr>
							<tr>
								<td><b>Đổi Hành Trình</b></td>
								<td><span> Không được phép.</span></td>
							</tr>
							<tr>
								<td><b>Đổi Tên Hành Khách</b></td>
								<td><span> Được phép thay đổi trước giờ mở quầy làm
										thủ tục check-in, thu phí đổi + chênh lệch giá(nếu có).</span></td>
							</tr>
							<tr>
								<td><b>Bảo lưu</b></td>
								<td><span> Không được phép.</span></td>
							</tr>
							<tr>
								<td><b>Thời hạn thay đổi (bao gồm thay
										đổi tên, ngày/chuyến bay, hành trình)</b></td>
								<td><span> Trước giờ mở quầy làm thủ tục check-in.</span></td>
							</tr>
						</table> -->
					</div>
				</g:elseif>
			</g:if>
			<g:form method="POST" onSubmit="return validate()"
				action="localinfopayment">
				<div class="infor-t">
					<h6>Thông tin hành lý</h6>
					<table class="luggage-information">
						<tr>
							<td colspan="2"><b>Hành lý chiều đi </b></td>
						</tr>
						<tr>
							<td><span>Hành lý xách tay </span></td>
							<td><span> Mỗi hành khách được mang theo tối đa 7kg
									hành lý xách tay. </span></td>
						</tr>
						<g:if
							test="${(airlineNameOutbound.equals('Jetstar') || airlineCodeOutbound.equals('BL')) && session.parameters.isDomestic.equals('true')}">
							<g:set var="i" value="${0}" />
							<tr>
								<td><span>Hành lý ký gửi </span></td>
								<td><g:while
										test="${i < (session.parameters.adults.toInteger() + session.parameters.kids.toInteger())}">
										<%i++ %>
										<div style="margin-bottom: 3px;">
											<span> Hành khách ${i}</span> <select name="outboundLuggable"
												class="outboundLuggable">
												<%--<option selected="selected" value="0">Không mang
													theo hành lý</option>
												<option value="130000">Thêm 15Kg hành lý (130,000
													VND)</option>
												<option value="160000">Thêm 20Kg hành lý (160,000
													VND)</option>
												<option value="220000">Thêm 25Kg hành lý (220,000
													VND)</option>
												<option value="270000">Thêm 30Kg hành lý (270,000
													VND)</option>
												<option value="320000">Thêm 35Kg hành lý (320,000
													VND)</option>
												<option value="370000">Thêm 40Kg hành lý (370,000
													VND)</option>--%>
													<option selected="selected" value="0">Không mang theo
															hành lý</option>
														<option value="132000">Thêm 15Kg hành lý (132,000 VND)</option>
														<option value="165000">Thêm 20Kg hành lý (165,000 VND)</option>
														<option value="220000">Thêm 25Kg hành lý (220,000 VND)</option>
														<option value="330000">Thêm 30Kg hành lý (330,000 VND)</option>
											</select>
										</div>
									</g:while></td>
							</tr>
						</g:if>
						<g:elseif
							test="${(airlineNameOutbound.equals('Vietjet Air') || airlineCodeOutbound.equals('VJ')) && session.parameters.isDomestic.equals('true')}">
							<g:set var="i" value="${0}" />
							<tr>
								<td><span>Hành lý ký gửi </span></td>
								<td><g:while
										test="${i < (session.parameters.adults.toInteger() + session.parameters.kids.toInteger())}">
										<%i++ %>
										<div style="margin-bottom: 3px;">
												<span> Hành khách ${i}</span>
												<select
													name="outboundLuggable" class="outboundLuggable">
														<option selected="selected" value="0">Không mang theo
															hành lý</option>
														<option value="132000">Thêm 15Kg hành lý (132,000 VND)</option>
														<option value="165000">Thêm 20Kg hành lý (165,000 VND)</option>
														<option value="220000">Thêm 25Kg hành lý (220,000 VND)</option>
														<option value="330000">Thêm 30Kg hành lý (330,000 VND)</option>
												</select>
											</div>
										</g:while>
									</td>
								</tr>
							
						</g:elseif>
						<g:elseif test="${(airlineNameOutbound.equals('Vietnam Airlines') || airlineCodeOutbound.equals('VN')) && session.parameters.isDomestic == 'true'}">
							<g:set var="i" value="${0}"/>
							<tr>
								<td><span>Hành lý ký gửi </span></td>
								<td>
									<g:while test="${i < (session.parameters.adults.toInteger() + session.parameters.kids.toInteger())}">
										<%i++ %>
										<div style="margin-bottom: 3px;">
											<span> Hành khách ${i}: </span>
											<span>Liên hệ nhân viên phòng vé</span>
										</div>
									</g:while>
								</td>
									
								
							</tr>
						</g:elseif>
						<g:if test="${session.parameters.iday.toInteger()!=0&&session.parameters.imonth.toInteger()}">
							<tr>
								<td colspan="2"><b>Hành lý chiều về </b></td>
							</tr>
							<tr>
								<td><span>Hành lý xách tay </span></td>
								<td><span> Mỗi hành khách được mang theo tối đa 7kg
										hành lý xách tay. </span></td>
							</tr>
							<g:if test="${(airlineNameInbound.equals('Jetstar') || airlineCodeInbound.equals('BL')) && session.parameters.isDomestic.equals('true')}">
								<g:set var="i" value="${0}"/>
								<tr>
									<td width="200px"><span>Hành lý ký gửi </span></td>
									<td>
										<g:while test="${i < (session.parameters.adults.toInteger() + session.parameters.kids.toInteger())}">
											<%i++ %>
											<div style="margin-bottom: 3px;">
												<span> Hành khách ${i}</span> <select
													name="inboundLuggable" class="inboundLuggable">
														<%--<option selected="selected" value="0">Không mang theo
															hành lý</option>
														<option value="130000">Thêm 15Kg hành lý (130,000 VND)</option>
														<option value="160000">Thêm 20Kg hành lý (160,000 VND)</option>
														<option value="220000">Thêm 25Kg hành lý (220,000 VND)</option>
														<option value="270000">Thêm 30Kg hành lý (270,000 VND)</option>
														<option value="320000">Thêm 35Kg hành lý (320,000 VND)</option>
														<option value="370000">Thêm 40Kg hành lý (370,000 VND)</option>
												--%>
												<option selected="selected" value="0">Không mang theo
															hành lý</option>
														<option value="132000">Thêm 15Kg hành lý (132,000 VND)</option>
														<option value="165000">Thêm 20Kg hành lý (165,000 VND)</option>
														<option value="220000">Thêm 25Kg hành lý (220,000 VND)</option>
														<option value="330000">Thêm 30Kg hành lý (330,000 VND)</option>
												</select>
											</div>
										</g:while>
									</td>
								</tr>
							</g:if>
							<g:elseif test="${(airlineNameInbound.equals('Vietjet Air') || airlineCodeInbound.equals('VJ')) && session.parameters.isDomestic.equals('true')}">
								<g:set var="i" value="${0}"/>
								<tr>
									<td><span>Hành lý ký gửi </span></td>
									<td>
										<g:while test="${i < (session.parameters.adults.toInteger() + session.parameters.kids.toInteger())}">
											<%i++ %>
											<div style="margin-bottom: 3px;">
												<span> Hành khách ${i}</span> <select
													name="inboundLuggable" class="inboundLuggable">
														<option selected="selected" value="0">Không mang theo
															hành lý</option>
														<option value="132000">Thêm 15Kg hành lý (132,000 VND)</option>
														<option value="165000">Thêm 20Kg hành lý (165,000 VND)</option>
														<option value="220000">Thêm 25Kg hành lý (220,000 VND)</option>
														<option value="330000">Thêm 30Kg hành lý (330,000 VND)</option>
												</select>
											</div>
										</g:while>
									</td>
								</tr>
							</g:elseif>
							<g:elseif test="${(airlineNameInbound.equals('Vietnam Airlines') || airlineCodeInbound.equals('VN')) && session.parameters.isDomestic == 'true'}">
								<g:set var="i" value="${0}"/>
								<tr>
									<td><span>Hành lý ký gửi </span></td>
									<td>
										<g:while test="${i < (session.parameters.adults.toInteger() + session.parameters.kids.toInteger())}">
											<%i++ %>
											<div style="margin-bottom: 3px;">
												<span> Hành khách ${i}: </span>
												<span>Vui lòng liên hệ nhân viên phòng vé</span>
											</div>
										</g:while>
									</td>
								</tr>
							</g:elseif>
						</g:if>
					</table>
				</div>
				<div class="infor-t">
					<h6>Thông tin hành khách</h6>
					<table class="passenger-information">
						<tr class="hidden-xs">
							<td></td>
							<td></td>
							<td><b>Họ và tên</b><br> <i
								style="font-size: 11px; color: #999; font-style: normal">(ví
									dụ: Nguyen Van A)</i></td>
							<td><b>Ngày sinh</b><br> <i
								style="font-size: 11px; color: #999; font-style: normal">(ví
									dụ: 27/09/2013)</i></td>
						</tr>
						<% for(i=0;i<new Integer(session.parameters.adults+"");i++){ %>
					<tr>
						<td class="hidden-xs"><span>Người lớn</span></td>
						<td><select name="${'cbx_a_'+i}">
								<option value='0'>Ông</option>
								<option value='1'>Bà</option>
						</select></td>
						<td><input type="text" name="${'a_'+i+'_name'}" placeholder="Họ và tên"/></td>
						<td class="hidden-xs"></td>
					</tr>
					<% } %>
						<tr class="hidden-xs">
							<td></td>
							<td></td>
						</tr>
						<% for(i=0;i<new Integer(session.parameters.kids+"");i++){ %>
					<tr>
						<td class="hidden-xs"><span>Trẻ Em</span></td>
						<td><select name="${'cbx_k_'+i}">
								<option value='2'>Trẻ Em Trai</option>
								<option value='3'>Trẻ Em Gái</option>
						</select></td>
						<td><input type="text" name="${'k_'+i+'_name'}" placeholder="Họ và tên" /></td>
						<td><label class="visible-xs">Ngày sinh: </label><select name="${'k_'+i+'_day'}">
								<% for(d in 1..31){%>
								<option value="${d}">${d}</option>
								<% }%>
						</select> <select name="${'k_'+i+'_month'}">
							<% for(m in 1..12){%>
								<option value="${m}">${m}</option>
							<%}%>
						</select> <select name="${'k_'+i+'_year'}">
							<% int currentYear=(new Date()).year+1900; for(y in (currentYear-12)..(currentYear-2)){%>
								<option value="${y}">${y}</option>
							<%}%>
						</select></td>
					</tr>
					<% }%>
						<% for(i=0;i<new Integer(session.parameters.infants+"");i++){ %>
					<tr>
						<td class="hidden-xs"><span>Trẻ sơ sinh</span></td>
						<td><select name="${'cbx_i_'+i}">
								<option value='4'>Em Bé Trai</option>
								<option value='5'>Em Bé Gái</option>
						</select></td>
						<td><input type="text" name="${'i_'+i+'_name'}" placeholder="Họ và tên" /></td>
						<td><label class="visible-xs">Ngày sinh: </label><select name="${'i_'+i+'_day'}">
								<% for(d in 1..31){%>
								<option value="${d}">${d}</option>
								<% }%>
						</select><select name="${'i_'+i+'_month'}">
							<% for(m in 1..12){%>
								<option value="${m}">${m}</option>
							<%}%>
						</select> <select name="${'i_'+i+'_year'}">
							<% int currentYear=(new Date()).year+1900; for(y in (currentYear-2)..(currentYear)){%>
								<option value="${y}">${y}</option>
							<%}%>
						</select></td>
					</tr>
					<% } %>
					</table>

				</div>
				<div class="infor-t">
					<h6>Thông tin liên hệ</h6>
					<p>
						Xin vui lòng điền vào tất cả các thông tin. Chúng tôi sẽ liên hệ
						với bạn <label style="color: red"> *</label> Thông tin bắt buộc
					</p>
					<table class="contact-v">
						<tr>
							<td colspan="2"><span class="hidden-xs">Họ và tên<label
									style="color: red"> *</label></span> <input name="fullname"
								type="text" placeholder="Họ và tên"/></td>
						</tr>
						<tr>
							<td><span class="hidden-xs">Email<i class="hidden-xs"
									style="font-size: 11px; color: #999; font-style: normal">Ví
										dụ: booking@elines.vn </i></span><br class="hidden-xs" /> <input name="email" type="text" placeholder="Email"
								 /></td>
							<td><span class="hidden-xs">Điện thoại<label style="color: red">
										*</label></span><br class="hidden-xs"/> <input type="text" name="phoneNumber"  value="${session.parameters.phoneNumber}" placeholder="Điện thoại"
								/>
								</td>
						</tr>
						<tr>
							<td><span class="hidden-xs">Địa chỉ</span><br class="hidden-xs"/>
								<input name="address" type="text" placeholder="Địa chỉ"/></td>
							<td><span class="hidden-xs">Thành phố</span><br class="hidden-xs"/> <input name="city" type="text" placeholder="Thành phố"/></td>
						</tr>
					</table>
				</div>
				<div class="infor-t">
					<h6>Yêu cầu đặc biệt</h6>
					<br />
					<p>Nhập vào yêu cầu của bạn tại đây</p>
					<textarea id="specialRequirement" name="specialRequirement"></textarea>
				</div>
				<input type="submit" value="Tiếp tục" name="Next" />
			</g:form>
		</article>

		<article class="col-md-4 col-sm-8 col-xs-12">
			<div class="book hdd ss bg-type-red box-border-radius-5 region-type-2 box-padding-10">
				<div class="title title-b title-upper">
					<span class="title">CHI TIẾT GIÁ</span>
				</div>
				<div class="line line-horizontal"></div>
				<div class="space-5"></div>
				<div class="">				
					<table>
						<tr>
							<td colspan="5"><span><b>Giá (Chiều đi)</b></span></td>
						</tr>
						<tr class="dow">
							<td><span id="numberOfAdults">${session.parameters.adults} </span><span>người lớn</span></td>
							<td><span>x</span></td>
							<g:if test="${pricePerAdultOutbound > 0}">
								<td><span id="basePricePerAdult">${pricePerAdultOutbound} </span><span>VND</span></td>
							</g:if>
							<g:else>
								<td><span id="basePricePerAdults">0 </span><span>VND</span></td>
							</g:else>
							<td><span>=</span></td>
							<td><span id="basePriceTotalAdults"></span><span> VND</span></td>
						</tr>
						<g:if test="${Integer.valueOf(session.parameters.kids) > 0}">
							<tr class="dow">
								<td><span id="numberOfKids">${session.parameters.kids} </span><span>trẻ em</span></td>
								<td><span>x</span></td>
								<g:if test="${pricePerChildOutbound > 0}">
									<td><span id="basePricePerKid">${pricePerChildOutbound} </span><span>VND</span></td>
								</g:if>
								<g:else>
									<td><span id="basePricePerKid">0 </span><span>VND</span></td>
								</g:else>
								<td><span>=</span></td>
								<td><span id="basePriceTotalKids"></span><span> VND</span></td>
							</tr>
						</g:if>
						<g:if test="${Integer.valueOf(session.parameters.infants) > 0}">
							<tr class="dow">
								<td><span id="numberOfInfants">${session.parameters.infants} </span><span>em bé</span></td>
								<td></td>
								<td></td>
								<td><span>=</span></td>
								<td><span id="basePriceTotalInfants"></span><span> VND</span></td>
							</tr>
						</g:if>
						<tr class="up">
							<td colspan="3"><b><span>Tổng giá (Chiều đi)</span></b></td>
							<td><b><span>=</span></b></td>
							<td>
								<b><span id="totalFarePrice">${totalPriceOutbound}</span><span> VND</span></b>
							</td>
						</tr>
						<tr class="dow">
							<td colspan="3">
								<b><span>Hành lý (Chiều đi)</span></b>
							</td>
							<td>
								<b><span>=</span></b>
							</td>
							<td>
								<b><span id="luggagePrice">0</span><span> VND</span></b>
							</td>
						</tr>
						
						<g:if test="${('roundtrip').equals(fareType.name)}">
							<tr style="border-bottom: 1px solid #fff"></tr>
							<tr>
								<td colspan="5"><span><b>Giá (Chiều về)</b></span></td>
							</tr>
							<tr class="dow">
								<td><span class="numberOfAdults">${session.parameters.adults} </span><span>người lớn</span></td>
								<td><span>x</span></td>
								<g:if test="${pricePerAdultInbound > 0}">
									<td><span id="basePricePerAdultInbound">${pricePerAdultInbound} </span><span>VND</span></td>
								</g:if>
								<g:else>
									<td><span id="basePricePerAdultInbound">0 </span><span>VND</span></td>
								</g:else>
								<td><span>=</span></td>
								<td><span id="basePriceTotalAdultsInbound"></span><span> VND</span></td>
							</tr>
							<g:if test="${Integer.valueOf(session.parameters.kids) > 0}">
								<tr class="dow">
									<td><span class="numberOfKids">${session.parameters.kids} </span><span>trẻ em</span></td>
									<td><span>x</span></td>
									<g:if test="${pricePerChildInbound > 0}">
										<td><span id="basePricePerKidInbound">${pricePerChildInbound} </span><span>VND</span></td>
									</g:if>
									<g:else>
										<td><span id="basePricePerKidInbound">0 </span><span>VND</span></td>
									</g:else>
									<td><span>=</span></td>
									<td><span id="basePriceTotalKidsInbound"></span><span> VND</span></td>
								</tr>
							</g:if>
							<g:if test="${Integer.valueOf(session.parameters.infants) > 0}">
								<tr class="dow">
									<td><span class="numberOfInfantsInbound">${session.parameters.infants} </span><span>em bé</span></td>
									<td></td>
									<td></td>
									<td><span>=</span></td>
									<td><span id="basePriceTotalInfantsInbound"></span><span> VND</span></td>
								</tr>
							</g:if>
							<tr class="up">
								<td colspan="3"><b><span>Tổng giá (Chiều về)</span></b></td>
								<td><b><span>=</span></b></td>
								<td>
									<b><span id="totalFarePriceInbound">${totalPriceInbound}</span><span> VND</span></b>
								</td>
							</tr>
							<tr class="dow">
								<td colspan="3">
									<b><span>Hành lý (Chiều về)</span></b>
								</td>
								<td>
									<b><span>=</span></b>
								</td>
								<td>
									<b><span id="luggagePriceInbound">0</span><span> VND</span></b>
								</td>
							</tr>
						</g:if>
						
						
						<tr style="border-bottom: 1px solid #fff"></tr>
						<tr class="up">
							<td colspan="3">
								<b><span>Tổng</span></b>
							</td>
							<td>
								<b><span>=</span></b>
							</td>
							<td>
								<b><span id="totalFinalPrice"></span><span> VND</span></b>
							</td>
						</tr>
					</table>
					<br /> <br /> <input class="tour-button" type="submit"
						value="<< Quay về trang kết quả" 
						onClick="javascript:history.back()"><br /> <br />
						<script type="text/javascript">
							jQuery(document).ready(function(){
								Number.prototype.format = function() {
									 var rx=  /(\d+)(\d{3})/;
									    return this.toFixed(0).replace(/^\d+/, function(w){
									        while(rx.test(w)){
									            w= w.replace(rx, '$1,$2');
									        }
									        return w;
									 });
								}
								//Caculate the price and total price
								var totalPriceOutbound = parseInt(jQuery('#totalFarePrice').html())
								jQuery('#totalFarePrice').html(totalPriceOutbound.format())
								var numsOfAdults = parseInt(jQuery('#numberOfAdults').html())
								var numsOfKids
								var numsOfInfants
								if(jQuery('#numberOfKids').html() != "" && jQuery('#numberOfKids').html() != null){
									numsOfKids = parseInt(jQuery('#numberOfKids').html())
								} else {
									numsOfKids = 0
								}
								if(jQuery('#numberOfInfants').html() != "" && jQuery('#numberOfInfants').html() != null){
									numsOfInfants = parseInt(jQuery('#numberOfInfants').html())
								} else {
									numsOfInfants = 0
								}
								var pricePerAdultsOutbound = parseInt(jQuery('#basePricePerAdult').html())
								var pricePerKidsOutbound
								if(jQuery('#basePricePerKid').html() != "" && jQuery('#basePricePerKid').html() != null){
									pricePerKidsOutbound = parseInt(jQuery('#basePricePerKid').html())
								} else {
									pricePerKidsOutbound = 0
								}
								
								
								jQuery('#basePricePerAdult').html(pricePerAdultsOutbound.format())
								jQuery('#basePricePerKid').html(pricePerKidsOutbound.format())
								jQuery('#basePriceTotalAdults').html((numsOfAdults*pricePerAdultsOutbound).format())
								jQuery('#basePriceTotalKids').html((numsOfKids*pricePerKidsOutbound).format())
								if(totalPriceOutbound > (numsOfAdults*pricePerAdultsOutbound + numsOfKids*pricePerKidsOutbound)){
									var priceTotalInfants = totalPriceOutbound - (numsOfAdults*pricePerAdultsOutbound + numsOfKids*pricePerKidsOutbound)
									jQuery('#basePriceTotalInfants').html(priceTotalInfants.format())
								} else{
									jQuery('#basePriceTotalInfants').html('0')
								}
								var totalPriceInfantsOutbound
								if(jQuery('#basePriceTotalInfantsInbound').html() != "" && jQuery('#basePriceTotalInfantsInbound').html() != null){
									totalPriceInfantsOutbound = parseInt(jQuery('#basePriceTotalInfantsInbound').html())
								} else{
									totalPriceInfantsOutbound = 0
								}
								
								//Get the inbound price
								var totalPriceInbound
								if(jQuery('#totalFarePriceInbound').html() != "" && jQuery('#totalFarePriceInbound').html() != null){
									totalPriceInbound = parseInt(jQuery('#totalFarePriceInbound').html())
								} else {
									totalPriceInbound = 0
								}
								jQuery('#totalFarePriceInbound').html(totalPriceInbound.format())
								jQuery('#totalFarePriceInbound').html(totalPriceInbound.format())
								var pricePerAdultsInbound = parseInt(jQuery('#basePricePerAdultInbound').html())
								jQuery('#basePricePerAdultInbound').html(pricePerAdultsInbound.format())
								var pricePerKidInbound
								if(jQuery('#basePricePerKidInbound').html() != "" && jQuery('#basePricePerKidInbound').html() != null){
									pricePerKidInbound = parseInt(jQuery('#basePricePerKidInbound').html())
								} else {
									pricePerKidInbound = 0
								}
								jQuery('#basePricePerKidInbound').html(pricePerKidInbound.format())
								if(totalPriceInbound > (numsOfAdults*pricePerAdultsInbound + numsOfKids*pricePerKidInbound)){
									var priceTotalInfants = totalPriceInbound - (numsOfAdults*pricePerAdultsInbound + numsOfKids*pricePerKidInbound)
									jQuery('#basePriceTotalInfantsInbound').html(priceTotalInfants.format())
								} else{
									jQuery('#basePriceTotalInfantsInbound').html('0')
								}
								var totalPriceInfantsInbound
								if(jQuery('#basePriceTotalInfantsInbound').html() != "" && jQuery('#basePriceTotalInfantsInbound').html() != null){
									totalPriceInfantsInbound = parseInt(jQuery('#basePriceTotalInfantsInbound').html())
								} else{
									totalPriceInfantsInbound = 0
								}
								
								var totalPriceInboundAdults = pricePerAdultsInbound*numsOfAdults
								jQuery('#basePriceTotalAdultsInbound').html(totalPriceInboundAdults.format())
								var totalPriceInboundKids = pricePerKidInbound*numsOfKids
								jQuery('#basePriceTotalKidsInbound').html(totalPriceInboundKids.format())
	
								var totalLuggagePrice
								if(jQuery('#luggagePriceInbound').html() != "" && jQuery('#luggagePriceInbound').html() != null){
									totalLuggagePrice = parseInt(jQuery('#luggagePrice').html()) + parseInt(jQuery('#luggagePriceInbound').html())
								} else {
									totalLuggagePrice = parseInt(jQuery('#luggagePrice').html())
								}
								var totalFinalPrice = totalPriceOutbound + totalPriceInbound + totalLuggagePrice
								jQuery('#totalFinalPrice').html(totalFinalPrice.format())
								
								//Outbound luggage fare
								jQuery('.outboundLuggable').on('change', function(){
									var totalLuggagePriceOutbound = 0
									var totalLuggagePrice = 0
									jQuery('.outboundLuggable').each(function(){
										//Calculate the total price of total luggage
										totalLuggagePriceOutbound += parseInt(jQuery(this).val())
										jQuery('#luggagePrice').html(totalLuggagePriceOutbound.format())
										
										//Set the final total price
										totalLuggagePrice += parseInt(jQuery(this).val())
									})
									jQuery('.inboundLuggable').each(function(){
										totalLuggagePrice += parseInt(jQuery(this).val())
									})
									totalFinalPrice = totalPriceOutbound + totalPriceInbound + totalLuggagePrice
									jQuery('#totalFinalPrice').html(totalFinalPrice.format())
								})
								//Inbound luggage fare
								jQuery('.inboundLuggable').on('change', function(){
									var totalLuggagePriceInbound = 0
									var totalLuggagePrice = 0
									jQuery('.inboundLuggable').each(function(){
										//Calculate the total price of total luggage
										totalLuggagePriceInbound += parseInt(jQuery(this).val())
										jQuery('#luggagePriceInbound').html(totalLuggagePriceInbound.format())
										
										//Set the final total price
										totalLuggagePrice += parseInt(jQuery(this).val())
									})
									jQuery('.outboundLuggable').each(function(){
										totalLuggagePrice += parseInt(jQuery(this).val())
									})
									totalFinalPrice = totalPriceOutbound + totalPriceInbound + totalLuggagePrice
									jQuery('#totalFinalPrice').html(totalFinalPrice.format())
								})
								
							})
						</script>
				</div>
			</div>
			<div style="clear: both"></div>
		</article>
		</section>
	
	<script type="text/javascript">
	function validate(){
		var flag=true
		$("input[name$='_name']").each(
		function(i,el){
			if($(el).val()==""||$(el).val().trim().length==0){
				alert('Ten hanh khach khong duoc de trong')
				$(el).focus()
				flag=false
				return false
			}
			if($(el).val().length>100){
				alert('Ten hanh khach khong duoc dai qua 100 ky tu')
				$(el).focus()
				flag=false
				return false
			}
		})
		if(!flag){
		return false
		}
		if($("input[name='fullname']").val()=="" || $("input[name='fullname']").val().trim().length==0){
			alert('Ten khach hang khong duoc de trong')
			$("input[name='fullname']").focus()
			return false
		}
		if($("input[name='fullname']").val().length>100){
		alert('Ten khach hang khong duoc dai qua 100 ky tu')
		$("input[name='fullname']").focus()
		return false
		}
<%--		if($("input[name='email']").val()=="" || $("input[name='email']").val().trim().length==0){--%>
<%--			alert('Email khach hang khong duoc de trong')--%>
<%--			$("input[name='email']").focus()--%>
<%--			return false--%>
<%--		}--%>

		var emailPattern = new RegExp("[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@" +
				  "(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?")

		if($("input[name='email']").val()!=""){
			if($("input[name='email']").val().match(emailPattern)==null){
				alert('Email khach hang khong dung dinh dang')
				$("input[name='email']").focus()
				return false
			}
		}
		
		if($("input[name='email']").val().length>50){
			alert('Email khach hang khong duoc dai qua 50 ky tu')
			$("input[name='email']").focus()
			return false
		}
<%--		if($("input[name='city']").val()=="" ||$("input[name='city']").val().trim().length==0){--%>
<%--			alert('Thanh pho khong duoc de trong')--%>
<%--			$("input[name='city']").focus()--%>
<%--			return false--%>
<%--		}--%>
		if($("input[name='city']").val().length>100){
			alert('Ten thanh pho khong duoc dai qua 100 ky tu')
			$("input[name='city']").focus()
			return false
		}
<%--		if($("input[name='address']").val()=="" ||$("input[name='address']").val().trim().length==0){--%>
<%--			alert('Dia chi khach hang khong duoc de trong')--%>
<%--			$("input[name='address']").focus()--%>
<%--			return false--%>
<%--		}--%>
		if($("input[name='address']").val().length>100){
			alert('Dia chi khach hang khong duoc qua 100 ky tu')
			$("input[name='address']").focus()
			return false
		}
		if($("input[name='phoneNumber']").val()=="" || $("input[name='phoneNumber']").val().trim().length==0){
			alert('So dien thoai khach hang khong duoc de trong')
			$("input[name='phoneNumber']").focus()
			return false
		}
		if($("input[name='phoneNumber']").val().match(/^\d{8,15}$/g)==null){
			alert('So dien thoai khach hang phai la so tu 8 toi 15 ky tu')
			$("input[name='phoneNumber']").focus()
			return false
		}
		if($("#specialRequirement").val()!=""&&$("#specialRequirement").val().length>500){
			alert('Yeu cau dac biet khong viet qua 500 ky tu')
			$("#specialRequirement").focus()
			return false
		}
	}
	Number.prototype.format = function() {
		 var rx=  /(\d+)(\d{3})/;
		    return this.toFixed(0).replace(/^\d+/, function(w){
		        while(rx.test(w)){
		            w= w.replace(rx, '$1,$2');
		        }
		        return w;
		 });
	}
	function appendDetail(flights,stopoverDurations){
		var str=""
		if(flights){	
              for(i in flights){
            	  if(i>0){
              		str+='<tr>'
      				str+='<td colspan="4" class="qt-stop">'
          			 str+='Đổi máy bay tại<b class="change-address"> '+flights[i-1].arrivalAirport+' </b>Thời gian chờ:'
              		 str+='<b class="change-time">'+stopoverDurations[i-1]+'</b>'
          	
          			str+='</td>'
      			    str+='</tr>'
                    }
       	  str+='<tr>'
          str+='<td  rowspan="2" width="70px"><img src="/images/sm'+flights[i].airlineCode+'.gif"/></td>'
          str+='<td width="170px">'
              str+='<a href="">Từ <b>'+flights[i].departureCity+'</b></a>'
          str+='</td>'
          str+='<td width="170px">'
              str+='<a href="">Đến <b>'+flights[i].arrivalCity+'</b></a>'
          str+='</td>'
          str+='<td rowspan="2" width="180px">'
              str+=flights[i].carrier+'<br>(<b>'+flights[i].airlineCode+' '+flights[i].flightNumber+'</b>)<br>'
              str+='<span> Loại vé:<b> Hạng phổ thông</b></span>'
          str+='</td>'
          str+='</tr>'        
          str+='<tr>'
          str+="<td>"
       	  str+='<a href="">'+flights[i].departureTime+', <b>'+flights[i].departureDate+'</b></a>'    
          str+="</td>"  
          str+="<td>"
        	  str+='<a href="">'+flights[i].arrivalTime+', <b>'+flights[i].arrivalDate+' </b></a>'    
          str+="</td>"              		
          str+='</tr>'       
   			  }
		}
	return str
	}	
	$(document).ready(function() {
		var inboundItem=jQuery.parseJSON(unescape('${session.inboundItem}'))
		
		var outboundItem=jQuery.parseJSON(unescape('${session.outboundItem}'))
			var id="tbl_o"
			$("#fullfare").append('<table id="'+id+'" class="find">')
			var oimage='sm'+outboundItem.oflights[0].airlineCode+'.gif'
			var trid=id+'_tr'	
			$("#fullfare #"+id).append('<tr class="from" id="'+trid+'">')
			$("#fullfare #"+id+' #'+trid).append('<td colspan="3" class="go">Điểm xuất phát <a href=""><b>${session.parameters.departureCode}</b></a> </td>')
			$("#fullfare #" + id + ' #' + trid).append('<td>Thời gian bay: <a href=""><b>'+ outboundItem.outboundDuration+ '</b></a> </td>')
			$("#fullfare #" + id).append('</tr>')
			$("#fullfare #" + id).append(appendDetail(outboundItem.oflights,outboundItem.outboundStopoverDurations))
			$("#fullfare").append('</table>')
			var iday=${session.parameters.iday}
			var imonth=${session.parameters.imonth}
		if(iday!=0&&imonth!=0){
		var id="tbl_i"
			if(inboundItem){
		$("#fullfare").append('<table id="'+id+'" class="find">')
				var image='sm'+inboundItem.iflights[0].airlineCode+'.gif'
				var trid=id+'_tr'	
				$("#fullfare #"+id).append('<tr class="from" id="'+trid+'">')
				$("#fullfare #"+id+' #'+trid).append('<td colspan="3" class="to">Điểm xuất phát <a href=""><b>${session.parameters.arrivalCode}</b></a></td>')
				$("#fullfare #"+id+' #'+trid).append('<td>Thời gian bay: <a href=""><b>'+inboundItem.inboundDuration+'</b></a> </td>')
				$("#fullfare #"+id).append('</tr>')
				$("#fullfare #"+id ).append(appendDetail(inboundItem.iflights,inboundItem.inboundStopoverDurations))
			$("#fullfare").append('</table>')
			}
		}
		

						})
		</script>
</body>
</html>