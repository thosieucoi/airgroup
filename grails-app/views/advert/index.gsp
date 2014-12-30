<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="com.airgroup.domain.Location" %>
<html>  
	<head>
		<meta name="layout" content="main" />
 		<title><g:message code="title.advert" args="[entityName]" /></title>
	</head>
	<body>
		<div class="nav hone">
			<div class="left">
				<h1><p>THÔNG TIN LIÊN HỆ</p></h1>
			  
				<div class="debook">
					<wcm:ifContentIs type="com.airgroup.domain.ContactUs">
						<div class="debook">
							<h2>Liên Hệ</h2>
							<p style="padding-left:15px"><b>Địa chỉ:</b> ${node.address}<BR />
								<span class="hotline"><b>Hotline:</b> 1900 1250</span><br />
								<span class="skype"><b>Skype:</b><a href="skype:${node.skype}?chat">${node.skype}</a></span><br />
								<span class="yahoo"><b>Yahoo:</b> <a href="ymsgr:sendim?${node.yahoo}">${node.yahoo}</a></span><br />
								<span class="email"><b>Email:</b><a href="mailto:${node.email}">${node.email}</a></span><br />
							</p>
							<h6>Bản đồ văn phòng công ty:</h6>
							<center>
								<iframe width="630" height="500" frameborder="0"
									scrolling="no"
									marginheight="0"
									style="margin-top: 25px;"
									marginwidth="0" src="https://maps.google.com/maps?f=q&amp;source=s_q&amp;hl=vi&amp;geocode=&amp;q=VietSoftware+International,+15+Ph%E1%BA%A1m+H%C3%B9ng,+T%E1%BB%AB+Li%C3%AAm,+Hanoi,+Vi%E1%BB%87t+Nam&amp;aq=0&amp;oq=Viet&amp;sll=37.0625,-95.677068&amp;sspn=37.136668,56.513672&amp;ie=UTF8&amp;hq=VietSoftware+International,+15+Ph%E1%BA%A1m+H%C3%B9ng,&amp;hnear=T%E1%BB%AB+Li%C3%AAm,+H%C3%A0+N%E1%BB%99i,+Vi%E1%BB%87t+Nam&amp;t=m&amp;ll=21.028911,105.779843&amp;spn=0.050914,0.072577&amp;output=embed">
								</iframe>
								<small>
								  <a href="https://maps.google.com/maps?f=q&amp;source=s_q&amp;hl=vi&amp;geocode=&amp;q=VietSoftware+International,+15+Ph%E1%BA%A1m+H%C3%B9ng,+T%E1%BB%AB+Li%C3%AAm,+Hanoi,+Vi%E1%BB%87t+Nam&amp;aq=0&amp;oq=Viet&amp;sll=37.0625,-95.677068&amp;sspn=37.136668,56.513672&amp;ie=UTF8&amp;hq=VietSoftware+International,+15+Ph%E1%BA%A1m+H%C3%B9ng,&amp;hnear=T%E1%BB%AB+Li%C3%AAm,+H%C3%A0+N%E1%BB%99i,+Vi%E1%BB%87t+Nam&amp;t=m&amp;ll=21.028911,105.779843&amp;spn=0.050914,0.072577&amp;output=embed"
									style="color:#0000FF;
									text-align:left">
								  </a>
								</small>
							</center>
						</div>
						<div class="debook">
							${node.content}
						</div>
					</wcm:ifContentIs>
				</div>
			</div>
		

			<div class="right">
				<div class="book hdd">
					<g:form name="submitForm" controller="flight" action="switchSearchPage" method="POST">
						<h1>
							<a href="#"><g:message code="book.flight" /></a>
						</h1>
						<div class="line linehd">
						<table>
						  <tr>
							<td>
								<span>Điểm khởi hành:</span>
							</td>
							<td>
								<input id="gog" style="width:168px" type="text"  value="Hà Nội (HAN)" name="departureCode"/>
							</td>
						   </tr>
						   <tr>
								<td>
									<span>Điểm đến:</span>
								</td>
								<td>
									<input id="tog" style="width:168px" type="text"  value="Hà Nội (HAN)" name="arrivalCode" />
								</td>
							</tr>
							<tr>
								<td>
									<span><g:message code="book.date.departure" />:</span>
								</td>
								<td>
									<select name="oday" id="oday"
										style="width: 60px">
									</select>
									<select name="omonth" id="omonth" style="width: 105px">
									</select>
								</td>
							</tr>
							<tr>
								<td>
									<span><g:message code="book.date.arrive" />:</span>
								</td>
								<td>
									<select name="iday" id="iday"
									style="width: 60px">
									  <option value="0"></option>
									</select> 
									<select id="imonth" name="imonth" style="width: 105px">
										<option value="0"></option>
									</select>
								</td>
							</tr>
						  </table>
							<table class="nun">
								<tr>
									<td class="tit"><span>Số lượng:</span></td>
								</tr>
								<tr>
									<td class="tit">
										<label>Người lớn(>12 tuổi)</label>
										<br />
										<select	name="adults" id="adults">
										</select>
									</td>
									<td class="tit">
										<label>Trẻ em(2-12 tuổi)</label>
										<br />
										<select name="kids" id="kids">
										</select>
									</td>
								</tr>
								<tr>
									<td class="tit">
										<label>Em bé(<2 tuổi)</label>
										<br />
										<select	name="infants" id="infants">
										</select>
									</td>
									<td>
										<span>Số điện thoại:</span>
										<br />
										<input type="tel" style="width: 100px; height: 22px" />
									</td>
								</tr>
								<tr>
									<td colspan="2"><a href="" class="hd deta">Hướng dẫn đặt vé</a></td>
								</tr>
							</table>
				  
							<input type="submit" value="Tìm chuyến bay"/>
							<div id="validateMessage" style="color: white; font-size: 14px; font-weight: bold;  margin-top: 10px;">
							</div>
							<g:include view="flight/includes/_searchflight.gsp"></g:include>
						</div>
					</g:form>
				</div>
			</div>
		</div>
	</body>
</html>