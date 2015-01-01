<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<meta name="layout" content="responsivemasterpage" />
<title>Insert title here</title>
</head>
<body>
	<div class="body">
		<div id="tour-section" class="row">
			<section id="break-crumb" class="row title-b">
				<article class="col-md-12 col-sm-12 col-xs-12">
					<g:link controller="home">
						<img
							src="${resource(dir:'images/newuiimg',file:'icon-home-black.png') } "
							alt="home" />
                        Trang chủ
                    </g:link>
					<span class="">&gt;</span> <a href="tour-cate.html" class="current">Tìm
						kiếm hóa đơn</a>
				</article>
			</section>
			<section class="row">
				<article id="tim-kiem-don-hang"
					class="col-md-12 col-sm-12 col-xs-12">
					<div class="bg-type-2 region-type-2 box-padding-20">
						<div class="title title-upper title-b">
							<span>Tìm kiếm hóa đơn</span>
						</div>
						<div class="line line-horizontal "></div>
						<div class="space-10"></div>

						<div class="box-margin-10">
							<g:form name="so" controller="home" action="searchOrder">
								<div class="control-collection">
									<div class="control-row space-10">
										<label for="personname" class="control-label">Mã đơn
											hàng<span style="color:red">*</span>:</label> <br /> <span class="description title-i">(VD:
											Nguyen Vam A)</span>
										<div class="">
											<input id="personname" name="orderCode" type="text"
												class="control-input" />
										</div>
										<div class="clearfix"></div>
									</div>
									<div class="control-row space-10">
										<label for="personphone" class="control-label">Điện
											thoại<span style="color:red">*</span>:</label> <br /> <span class="description title-i">(VD:
											0924884757)</span>
										<div class="">
											<input id="personphone" name="phoneNumber" type="text"
												class="control-input" />
										</div>
										<div class="clearfix"></div>
									</div>
									<div id="showerror" style="color: red; text-align: center"></div>
									<div class="command control-row space-10">
										<span></span>
										<div class="">
											<button type="button" onclick="revalidate()" class="tour-button">
												<span>Tìm kiếm</span>
											</button>
										</div>
										<div class="clearfix"></div>
									</div>
								</div>
							</g:form>
						</div>

						<div class="title title-upper title-b">
							<span>Kết quả tìm kiếm</span>
						</div>
						<div class="line line-horizontal "></div>
						<div class="space-10"></div>
						
						<g:if test="${order}">
							<div id="mail" style="width: 680px; margin: auto; padding: 0">
								<h1
									style="color: #023557; font-size: 18px; font-weight: bold; margin: 10px 0; text-align: right">
									ĐẶT VÉ MỌI LÚC MỌI NƠI<br /> <label
										style="color: #F00; font-size: 25px; padding: 10px">04-3512-2266</label>
								</h1>
								<div class="maildetail"
									style="margin: 5px 0; border: 1px solid #ccc">
									<ul class="mail-one"
										style="height: 32px; background: #007CC3; padding: 0; margin: 0">
										<li
											style="list-style: none; float: left; padding: 0 5px; width: 150px"><a
											href="http://igo.vn/flight/search"
											style="color: #f0f0f0; text-decoration: none; display: block; line-height: 32px">Tìm
												và đặt vé</a></li>
										<li
											style="list-style: none; float: left; padding: 0 5px; width: 150px"><a
											href="http://igo.vn/guide/searchGuide"
											style="color: #f0f0f0; text-decoration: none; display: block; line-height: 32px">Hướng
												dẫn đặt vé</a></li>
										<li
											style="list-style: none; float: left; padding: 0 5px; width: 150px"><a
											href="http://igo.vn/feedback/list"
											style="color: #f0f0f0; text-decoration: none; display: block; line-height: 32px">Ý
												kiến khách hàng</a></li>
										<li
											style="list-style: none; float: left; padding: 0 5px; width: 150px"><a
											href="http://igo.vn/contactUs/lienhe"
											style="color: #f0f0f0; text-decoration: none; display: block; line-height: 32px">Liên
												hệ</a></li>
									</ul>
									<br />
									<p style="padding: 0 5px">
										Kính chào ông <a href=""
											style="text-decoration: none; color: #0E70A5"> ${order.get(0).customer.name}
										</a>
									</p>
									<p style="padding: 0 5px">
										Xin cảm ơn Quý khách hàng đã sử dụng dịch vụ của<a href=""
											style="text-decoration: none; color: #0E70A5;"> igo.vn</a>
									</p>
									<div class="mail-list" style="padding: 0 5px 0 5px">
										<h2
											style="color: #007CC3; font-size: 22px; font-weight: bold; border-bottom: 2px solid #007CC3">Chi
											tiết đơn hàng</h2>
										<table style="width: 100%">
											<tr>
												<td style="padding: 5px 0"><label
													style="font-weight: bold">Mã đơn hàng: </label></td>
												<td class="mdh" style="padding: 5px 0"><a href=""
													style="text-decoration: none; font-size: 20px; font-weight: bold; color: #0E70A5">
														${order.get(0).id}
												</a></td>
												<td style="padding: 5px 0"></td>
												<td></td>
											</tr>
											<tr>
												<td style="padding: 5px 0"><label
													style="font-weight: bold">Trạng thái: </label></td>
												<td style="padding: 5px 0"><a href=""
													style="text-decoration: none; font-style: italic; color: #333">
														${order.get(0).status==1?"Đang chờ xử lý":"Đã xử lý"}
												</a></td>
												<td style="padding: 5px 0"><label
													style="font-weight: bold">Tổng giá: </label></td>
												<td class="mdh" style="padding: 5px 0"><a href=""
													style="text-decoration: none; font-size: 20px; font-weight: bold; color: #0E70A5">
														${price}
												</a></td>
											</tr>
											<tr>
												<td style="padding: 5px 0"><label
													style="font-weight: bold">Loại vé: </label></td>
												<td style="padding: 5px 0"><a href=""
													style="text-decoration: none; color: #333"> ${inboundItem.size()>0?"Khứ hồi":"Một chiều"}
												</a></td>
												<td style="padding: 5px 0"></td>
												<td></td>
											</tr>
										</table>
									</div>
									<div class="mail-list" style="padding: 0 5px 0 5px">
										<h2
											style="color: #007CC3; font-size: 22px; font-weight: bold; border-bottom: 2px solid #007CC3">Thông
											tin hành khách</h2>
										<table>
											<g:each in="${order.get(0).passengers}" var="passenger">
												<tr>
													<td style="padding: 5px 0"><label
														style="font-weight: bold"> <% if(passenger.gender.toInteger()==0){ %>
						${message(code: 'email.mr')}:
					<% } else if(passenger.gender.toInterger()==1){%>
					${message(code: 'email.ms')}:
					<% }else if(passenger.gender.toInterger()==2){%>
					${message(code: 'email.boy')}:
					<% }else if(passenger.gender.toInterger()==3){%>
					${message(code: 'email.girl')}:
					<% }else if(passenger.gender.toInterger()==4){%>
					${message(code: 'email.infantboy')}:
					<% }else if(passenger.gender.toInterger()==5){%>
					${message(code: 'email.infantgirl')}:
					<% }%>


													</label></td>
													<td class="mdh" style="padding: 5px 0"><a href=""
														style="font-size: 20px; color: #0E70A5; font-weight: bold; text-decoration: none;">
															${passenger.name.toString() }
													</a></td>
												</tr>
											</g:each>
										</table>
									</div>
									<div class="mail-list" style="padding: 0 5px 0;">
										<h2
											style="color: #007CC3; font-size: 22px; font-weight: bold; border-bottom: 2px solid #007CC3">Chi
											tiết hành trình</h2>
										<table class="find" style="margin: 15px 0">
											<tr class="from" style="background: #D3D3D3;">
												<td colspan="3" class="go" style="padding: 5px">Điểm
													xuất phát <a href=""
													style="text-decoration: none; color: #0E70A5;"><b>
															${order.get(0).goingDescription}
													</b></a>
												</td>
												<td style="padding: 5px">Thời gian bay:<a href=""
													style="text-decoration: none; color: #333"><b>
															${outboundDuration}
													</b></a>
												</td>
											</tr>
											<g:each in="${outboundItem}" var="${item}" status="index">

												<g:if test="${index>0 }">
													<tr>
														<td colspan="4" class="qt-stop"
															style="background: none repeat scroll 0 0 #FEF4EB; border-bottom: 1px solid #FCDABF; border-top: 1px solid #FCDABF; padding: 8px 0; text-align: center;">
															Đổi máy bay tại<b class="change-address"> ${outboundItem.get(index-1).arrival}</b>Thời
															gian chờ: <b class="change-time"> ${outboundOverStopDuration.get(index-1)}
														</b>

														</td>
													</tr>
												</g:if>
												<tr>
													<td style="padding: 5px" rowspan="2" width="70px"><img
														src="http://igo.vn/images/sm${item.airline}.gif" /></td>
													<td style="padding: 5px" width="170px">Từ<a href=""
														style="text-decoration: none; color: #333"> <b> ${item.departure}
														</b></a></td>
													<td style="padding: 5px" width="170px">Tới<a href=""
														style="text-decoration: none; color: #333"> <b> ${item.arrival}
														</b></a></td>
													<td style="padding: 5px" rowspan="2" width="180px">
														${item.airline}<br>(<b> ${item.airline} ${item.flightNumber}
													</b>)<br> <span>Hạng Vé :<b>Hạng phổ thông</b></span>
													</td>

												</tr>
												<tr>
													<td style="padding: 5px"><a href=""
														style="text-decoration: none; color: #333"> <g:formatDate
																format="HH:mm, dd/MM/yyyy" date="${item.outboundDate}" />

													</a></td>
													<td style="padding: 5px"><a href=""
														style="text-decoration: none; color: #333"><b><g:formatDate
																format="HH:mm, dd/MM/yyyy" date="${item.inboundDate}" />
															</b> </a></td>
												</tr>

											</g:each>
										</table>
										<g:if test="${inboundItem.size()>0}">
											<table class="find" style="margin: 15px 0">

												<tr class="from" style="background: #D3D3D3;">
													<td style="padding: 5px" colspan="3" class="to">Điểm
														xuất phát <a href=""
														style="text-decoration: none; color: #333"><b>
																${order.get(0).returnDescription}
														</b></a>
													</td>
													<td style="padding: 5px">Thời gian bay:<a href=""
														style="text-decoration: none; color: #333"><b> ${inboundDuration}</b></a>
													</td>
												</tr>
												<g:each in="${inboundItem}" var="${item}" status="index">

													<g:if test="${index>0}">
														<tr>
															<td colspan="4" class="qt-stop"
																style="background: none repeat scroll 0 0 #FEF4EB; border-bottom: 1px solid #FCDABF; border-top: 1px solid #FCDABF; padding: 8px 0; text-align: center;">
																Đổi máy bay tại<b class="change-address"> ${inboundItem.get(index-1).arrival}
															</b>Thời gian chờ: <b class="change-time">
																	${inboundOverStopDuration.get(index-1)}
															</b>

															</td>
														</tr>
													</g:if>
													<tr>
														<td style="padding: 5px" rowspan="2" width="70px"><img
															src="http://igo.vn/images/sm${item.airline}.gif" /></td>
														<td style="padding: 5px" width="170px">Từ<a href=""
															style="text-decoration: none; color: #333"> <b>
																	${item.departure}
															</b></a></td>
														<td style="padding: 5px" width="170px">Tới<a href=""
															style="text-decoration: none; color: #333"> <b>
																	${item.arrival}
															</b></a></td>
														<td style="padding: 5px" rowspan="2" width="180px">
															${item.airline}<br>(<b>
																${item.airline} ${item.flightNumber}
														</b>)<br> <span>Hạng Vé :<b>Hạng phổ thông</b></span>
														</td>

													</tr>
													<tr>
														<td style="padding: 5px 0"><a href=""
															style="text-decoration: none; color: #333"> <g:formatDate
																	format="HH:mm, dd/MM/yyyy" date="${item.outboundDate}" />
														</a></td>
														<td style="padding: 5px 0"><a href=""
															style="text-decoration: none; color: #333"> <g:formatDate
																	format="HH:mm, dd/MM/yyyy" date="${item.inboundDate}" />
														</a></td>
													</tr>

												</g:each>
											</table>
										</g:if>
									</div>
									<div class="mail-list" style="padding: 0 5px 0;">
										<h2
											style="color: #007CC3; font-size: 22px; font-weight: bold; border-bottom: 2px solid #007CC3">Hình
											thức thanh toán</h2>
										<p style="padding: 5px 0">
											Hình thức thanh toán của quý khách đã lựa chọn : <a href=""
												style="text-decoration: none; color: #0E70A5;"> ${order.get(0).payment.paymentName}
											</a>
										</p>
										<p style="padding: 5px 0">
											<a href="" style="text-decoration: none; color: #0E70A5;"><b>Văn
													phòng Hà Nội</b></a><br /> <a href=""
												style="text-decoration: none; color: #0E70A5;"><b>
													Địa chỉ:</b> Số 17 ngõ 189, ngách 189/2 Giảng Võ, P.Cát Linh,
												Đống Đa, Hà Nội.</a><br /> <a href=""
												style="text-decoration: none; color: #0E70A5;"><b>Điện
													thoại : </b>04-3512-2266</a><br />

										</p>
								</div>
									<ul class="mail-one"
										style="height: 32px; background: #007CC3; padding: 0; margin: 0">
										<li
											style="list-style: none; float: left; padding: 0 5px; width: 150px"><a
											href="http://igo.vn/flight/search"
											style="color: #f0f0f0; text-decoration: none; display: block; line-height: 32px">Tìm
												và đặt vé</a></li>
										<li
											style="list-style: none; float: left; padding: 0 5px; width: 150px"><a
											href="http://igo.vn/guide/searchGuide"
											style="color: #f0f0f0; text-decoration: none; display: block; line-height: 32px">Hướng
												dẫn đặt vé</a></li>
										<li
											style="list-style: none; float: left; padding: 0 5px; width: 150px"><a
											href="http://igo.vn/feedback/list"
											style="color: #f0f0f0; text-decoration: none; display: block; line-height: 32px">Ý
												kiến khách hàng</a></li>
										<li
											style="list-style: none; float: left; padding: 0 5px; width: 150px"><a
											href="http://igo.vn/contactUs/lienhe"
											style="color: #f0f0f0; text-decoration: none; display: block; line-height: 32px">Liên
												hệ</a></li>
									</ul>
								</div>

							</div>
						</g:if>
						<g:else>
						<center><span style="color:red;font-size: 20px;margin: 20px 0">Không tìm thấy hóa đơn</span></center>
						</g:else>
					</div>
				</article>
			</section>

			<div class="space-10"></div>

			<div class="space-20"></div>
		</div>
	</div>
</body>
</html>