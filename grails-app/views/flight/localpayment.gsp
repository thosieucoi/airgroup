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
			<a href='javascript:history.go(-4)'>Tìm vé</a> > <a href='javascript:history.go(-3)'>Lựa chọn vé</a>
			> <a href='javascript:history.go(-2)'>Thông tin chuyến bay</a> > <a href='javascript:history.go(-1)'
				>Thanh toán</a> > <a class="selected">Xác
				nhận</a>
		</div>
		</section>
		<section class="row">
		<article id="chon-chuyen-bay-1" class="col-md-8 col-sm-8 col-xs-12">
			<h1>
				<p>XÁC NHẬN THANH TOÁN</p>
			</h1>
			<div class="infor-t pay">
				<h3>Yêu cầu đặt vé của Quý khách đã được thực hiện thành công !</h3>
				<p class="tt-pay">
					Mã đơn hàng:<a>
						${orderId}
					</a><br /> Hình thức thanh toán:<a> ${payment.paymentName }
					</a><BR /> Tình trạng:<a> ${status }</a>
									
				</p>
				<fieldset class="payment-a">
					<legend>Qúy Khách Lưu Ý</legend>
					<span>Thông tin đơn hàng của quý khách sẽ được gửi tới địa
						chỉ email <a>
							${email }
					</a>
					</span><br /> <span>Trong thời gian sớm nhất (trong vòng 12h tới)
						nhân viên HPV sẽ liên hệ với Quý khách theo số điện thoại <a
						 style="color: red">${phoneNumber}</a> để thông báo kết quả đặt
						vé và hướng dẫn Quý khách cách thức thanh toán.
					</span><br /> <label>(<span class="require">*</span>Quý khách vui
						lòng kiểm tra lại thông tin email và số điện thoại để tránh xảy ra
						sai sót.)
					</label><br /> <span>Hãy để chúng tôi phục vụ Quý khách ! </span>
				</fieldset>

				<p>
					<b>Chúc quý khách thượng lộ bình an & có những chuyến bay thật
						thoải mái!</b>
				</p>
			</div>


		</article>
		<article class="col-md-4 col-sm-4 col-xs-12">
			<div class="book hdd ss bg-type-red box-border-radius-5 region-type-2 box-padding-10">
				<div class="title title-b title-upper">
					<span class="title">THÔNG TIN ĐẶT VÉ</span>
				</div>
				<div class="line line-horizontal"></div>
				<div class="space-5"></div>
				<div class="line linehd">
					<table class="pay-right">
						<tr>
							<td><span>Mã đơn hàng</span></td>
							<td><a>
									${orderId }
							</a></td>
						</tr>
						<tr>
							<td><span>Trạng thái</span></td>
							<td><a>
									${status}
							</a></td>
						</tr>
						<tr>
							<td><span>Tổng giá</span></td>
							<td><a style="color: #C60; font-size: 16px"><b>
										${totalPrice }
								</b></a></td>
						</tr>
						<tr style="border-bottom: 1px solid #fff"></tr>
						<tr>
							<td><span>Điểm xuất phát</span></td>
							<td><a>
									${session.parameters.departureCode }
							</a></td>
						</tr>
						<tr>
							<td><span>Điểm đến</span></td>
							<td><a>
									${session.parameters.arrivalCode }
							</a></td>
						</tr>
						<tr>
							<td><span>Loại Vé</span></td>
							<td><a>
									${(session.parameters.iday.toInteger()==0||session.parameters.imonth.toInteger()==0)?"Một Chiều":"Khứ Hồi"}
							</a></td>
						</tr>
						<tr>
							<td></td>
							<td><a> ${ session.parameters.adults+' Người lớn'}
									${ session.parameters.kids.toInteger()>0?(',' +session.parameters.kids+' Tre em'):''}
									${ session.parameters.infants.toInteger()>0?(',' +session.parameters.infants+' Tre so sinh'):''}
							</a></td>
						</tr>
						<tr>
							<td><span>Ngày Đi</span></td>
							<td><a> ${session.parameters.oday+"/"+session.parameters.omonth+"/"+(new Date().getYear()+1900)}</a></td>
						</tr>
						<g:if test="${iairline!=null }">
							<tr>
								<td><span>Ngày Về</span></td>
								<td>
									<a>${session.parameters.iday+"/"+session.parameters.imonth}/${(session.parameters.imonth.toInteger()<session.parameters.omonth.toInteger())?(new Date().getYear()+1900+1):(new Date().getYear()+1900)}</a>
								</td>
							</tr>
						</g:if>
						<tr>
							<td><span>Hãng Bay</span></td>
							<td><a> <img
									src="${resource(dir:'images',file:'sm'+oairline.code+".gif")}" /><span>
										${oairline.name }
								</span><br /> 
								<g:if test="${iairline!=null }">
										<img
											src="${resource(dir:'images',file:'sm'+iairline.code+".gif")}" />
										<span>
											${iairline.name }
										</span>
									</g:if>

							</a></td>
						</tr>
						<tr>
							<td colspan="2"><span>
									${customerName }
							</span></td>
						</tr>
					</table>
					<br />

				</div>
			</div>
			<div style="clear: both"></div>

		</article>
		</section>
	<script type="text/javascript">
		Number.prototype.format = function() {
			var rx = /(\d+)(\d{3})/;
			return this.toFixed(0).replace(/^\d+/, function(w) {
				while (rx.test(w)) {
					w = w.replace(rx, '$1,$2');
				}
				return w;
			});
		}
	</script>
</body>
</html>