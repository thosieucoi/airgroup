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
			<a href='javascript:history.go(-2)'>Tìm vé</a> > <a onClick='javascript:history.go(-1)'>Lựa chọn vé</a> >
			<a href="" class="selected">Thông tin chuyến bay</a> > <span
				style="color:#333 ;font-weight: normal;">Thanh toán</span> > <span style="color:#333 ;font-weight: normal;">Xác nhận</span>
		</div>
		</section>
		<section class="row">
		<article id="chon-chuyen-bay-1" class="col-md-8 col-sm-12 col-xs-12">
			<h1>
				<p>THÔNG TIN CHUYẾN BAY</p>
			</h1>
			<g:form method="POST" onSubmit="return validate()" action="internationalinfopayment">
			<div class="infor-t">
				<h6>Thông tin vé</h6>
				<table class="fare-information">
					<tr>
						<td><span>Hành trình:<a href=""><b>
										${session.parameters.departureCode} - ${session.parameters.arrivalCode}
								</b></a></span></td>
						<td><span>Loại vé: <a href=""><b>Phổ
										thông</b></a></span></td>
						<td><span>Số lượng khách:<a href="">
										${ session.parameters.adults+' Người lớn'} ${ session.parameters.kids.toInteger()>0?(',' +session.parameters.kids+' Tre em'):''}
									${ session.parameters.infants.toInteger()>0?(',' +session.parameters.infants+' Trẻ sơ sinh'):''}
							</a></span></td>
					</tr>
					<tr>
						<td><span>Ngày xuất phát:<a href=""><b>${session.parameters.oday+"/"+session.parameters.omonth}/${(session.parameters.omonth.toInteger()<new Date().getMonth())?(new Date().getYear()+1900+1):(new Date().getYear()+1900)}</b></a></span></td>
						<g:if test="${session.parameters.iday.toInteger()>0&&session.parameters.imonth.toInteger()>0}">
						<td><span>Ngày về: <a href=""><b> ${session.parameters.iday+"/"+session.parameters.imonth}/${(session.parameters.imonth.toInteger()<new Date().getMonth())?(new Date().getYear()+1900+1):(new Date().getYear()+1900)}
								</b></a></span></td>
						</g:if>	
					</tr>
				</table>
				<div id="departure"></div>
			</div>
			
			
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
					<tr>
						<tr>
							<td><span>Hành lý ký gửi </span></td>
							<td>
								<g:set var="i" value="${0}"/>
								<g:while test="${i < session.parameters.adults.toInteger()}">
									<%i++ %>
									<span> Hành khách ${i}:</span>
									<span>Liên hệ nhân viên phòng vé</span>
								</g:while>
							</td>
						</tr>
						<g:if test="${session.parameters.iday.toInteger()!=0&&session.parameters.imonth.toInteger()}">
						<tr>
							<td colspan="2"><b>Hành lý chiều về </b></td>
						</tr>
						<tr>
							<td><span>Hành lý xách tay </span></td>
							<td><span> Mỗi hành khách được mang theo tối đa 7kg
									hành lý xách tay. </span></td>
						</tr>
						<tr>
							<td><span>Hành lý ký gửi </span></td>
							<td>
								<g:set var="i" value="${0}"/>
								<g:while test="${i < session.parameters.adults.toInteger()}">
									<%i++ %>
									<span> Hành khách ${i}:</span>
									<span>Liên hệ nhân viên phòng vé</span>
								</g:while>
							</td>
						</tr>
						</g:if>
				</table>
			</div>
			<div class="infor-t">
				<h6>Thông tin hành khách</h6>
				<table class="passenger-information">
					<tr class="hidden-xs">
							<td></td>
							<td></td>
							<td><b>Họ và tên*</b><br> <i
								style="font-size: 11px; color: #999; font-style: normal">(ví
									dụ: Nguyen Van A)</i></td>
							<td></td>
						</tr>
						<% for(i=0;i<new Integer(session.parameters.adults+"");i++){ %>
					<tr>
						<td class="hidden-xs"><span>Người lớn</span></td>
						<td><select name="${'cbx_a_'+i}">
								<option value='0'>Ông</option>
								<option value='1'>Bà</option>
						</select></td>
						<td><input type="text" name="${'a_'+i+'_name'}" placeholder="Họ và tên" /></td>
						<td class="hidden-xs"></td>
					</tr>
					<% } %>
						<tr class="hidden-xs">
							<td></td>
							<td>
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
						<td><input type="text" name="${'i_'+i+'_name'}" placeholder="Họ và tên"/></td>
						<td><label class="visible-xs">Ngày sinh: <select name="${'i_'+i+'_day'}">
								<% for(d in 1..31){%>
								<option value="${d}">${d}</option>
								<% }%>
						</select> <select name="${'i_'+i+'_month'}">
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
					với bạn * Thông tin bắt buộc
				</p>
				<table class="contact-v">
					<tr>
						<td colspan="2"><span class="hidden-xs">Họ và tên*</span> <input name="fullname" type="text" placeholder="Họ và tên" value ='<sec:loggedInUserInfo field="name" />' 
							/></td>
					</tr>
					<tr>
						<td><span class="hidden-xs">Email<i
								style="font-size: 11px; color: #999; font-style: normal">Ví
									dụ: booker@ahotua.vn </i></span><br class="hidden-xs"/> 
									<input name="email" type="text" placeholder="Email" value ='<sec:loggedInUserInfo field="username" />' /></td>
						<td><span class="hidden-xs">Điện thoại*</span><br class="hidden-xs"/> 
						<g:if test="${session.parameters.phoneNumber}" >	
							<input name="phoneNumber" type="text" value="${session.parameters.phoneNumber}" placeholder="Điện thoại"/>
						</g:if>
						<g:else>
							<input name="phoneNumber" type="text" value ='<sec:loggedInUserInfo field="phoneNumber" />'  placeholder="Điện thoại"/>
						</g:else>
						</td>
					</tr>
					<tr>
						<td><span class="hidden-xs">Địa chỉ</span><br class="hidden-xs"/>
							<input name="address" type="text" placeholder="Địa chỉ" /></td>
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
							<g:if test="${session.parameters.iday.toInteger() !=0 && session.parameters.imonth.toInteger()!=0}">
								<td colspan="5"><span><b>Giá (Khứ hồi)</b></span></td>
							</g:if>
							<g:else>
								<td colspan="5"><span><b>Giá (1 chiều)</b></span></td>
							</g:else>							
						</tr>
						<tr class="dow">
							<td><span id="numberOfAdults">${session.parameters.adults} </span><span>người lớn</span></td>
							<td><span>x</span></td>
							<g:if test="${pricePerAdult > 0}">
								<td><span id="basePricePerAdult">${pricePerAdult} </span><span>VND</span></td>
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
								<g:if test="${pricePerChild > 0}">
									<td><span id="basePricePerKid">${pricePerChild} </span><span>VND</span></td>
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
						
						<tr style="border-bottom: 1px solid #fff"></tr>
						<tr class="up">
							<td colspan="3">
								<b><span>Tổng</span></b>
							</td>
							<td>
								<b><span>=</span></b>
							</td>
							<td>
								<b><span id="totalFinalPrice">${price}</span><span> VND</span></b>
							</td>
						</tr>
					</table>
					<br /> <br /> <input class="tour-button" type="submit"
						value="<< Quay về trang kết quả" style="margin-left: 10px"
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
								var totalPrice = parseInt(jQuery('#totalFinalPrice').html())
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
								var pricePerAdult = parseInt(jQuery('#basePricePerAdult').html())
								var pricePerKid
								if(jQuery('#basePricePerKid').html() != "" && jQuery('#basePricePerKid').html() != null){
									pricePerKid = parseInt(jQuery('#basePricePerKid').html())
								} else {
									pricePerKid = 0
								}
								
								jQuery('#basePricePerAdult').html(pricePerAdult.format())
								jQuery('#basePricePerKid').html(pricePerKid.format())
								jQuery('#basePriceTotalAdults').html((numsOfAdults*pricePerAdult).format())
								jQuery('#basePriceTotalKids').html((numsOfKids*pricePerKid).format())
								var priceTotalInfants
								if(totalPrice > (numsOfAdults*pricePerAdult + numsOfKids*pricePerKid)){
									priceTotalInfants = totalPrice - (numsOfAdults*pricePerAdult + numsOfKids*pricePerKid)
									jQuery('#basePriceTotalInfants').html(priceTotalInfants.format())
								} else{
									jQuery('#basePriceTotalInfants').html('0')
									priceTotalInfants = 0
								}
								
								var totalFinalPrice = numsOfAdults*pricePerAdult + numsOfKids*pricePerKid + priceTotalInfants
								jQuery('#totalFinalPrice').html(totalFinalPrice.format())
							})
						</script>
			<div style="clear: both"></div>

		</div>
	</div>
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
		if($("input[name='city']").val().length>100){
			alert('Ten thanh pho khong duoc dai qua 100 ky tu')
			$("input[name='city']").focus()
			return false
		}
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
		function appendDetail(flights, stopoverDurations, fareType) {
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
		      			    str+='<input type="hidden" name="transitAirport" value="' + flights[i-1].arrivalAirport + '"/>'
		                    }
		       	  str+='<tr>'
		          str+='<td  rowspan="2"><img src="/images/sm'+flights[i].airlineCode+'.gif"/></td>'
		          str+='<td width="170px">'
		          	  if(flights[i].departureCity != null && flights[i].departureCity != "null" && flights[i].departureCity != "Undefined" && flights[i].departureCity != ""){
		              	str+='<a href="">Từ <b>'+flights[i].departureCity+'</b></a>'
		              } else {
		              	str+='<p>Từ <b>'+flights[i].departureAirport+'</b></p>'
		              }
		          str+='</td>'
		          str+='<td>'
		              if(flights[i].arrivalCity != null && flights[i].arrivalCity != "null" && flights[i].arrivalCity != "Undefined" && flights[i].arrivalCity != ""){
                      	  	str+='<p>Tới <b>'+flights[i].arrivalCity+'</b></p>'
                      	  } else {
                      	  	str+='<p>Tới <b>'+flights[i].arrivalAirport+'</b></p>'
                      	  }
		          str+='</td>'
		          str+='<td rowspan="2">'
		              str+=flights[i].carrier+'<br>(<b>'+flights[i].airlineCode+' '+flights[i].flightNumber+'</b>)<br>'
		              str+='<span> Loại vé :<b>Hạng phổ thông</b></span>'
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
			var item=jQuery.parseJSON(unescape('${session.currentItem}'))
			var id="tbl"
			if(item.oflights&&item.oflights.length>0){
			$("#departure").append('<div id="'+id+'" class="qt-select">')		
					var tbl_o_id="tbl_o_"+id
					$("#"+id).append('<table  id="'+ tbl_o_id+'" class="find">')
					var trid=tbl_o_id+'_tr'
					$("#"+id+" #"+tbl_o_id).append('<tr class="from" id="'+trid+'">')
					$("#"+id+" #"+tbl_o_id+' #'+trid).append('<td colspan="3" class="go">Điểm xuất phát <a href=""><b>'+'${session.parameters.departureCode}'+'</b></a> </td>')
                    $("#"+id+" #"+tbl_o_id+' #'+trid).append('<td>Thời gian bay:<a href=""><b>'+item.outboundDuration+'</b></a> </td>')
					$("#"+id+" #"+tbl_o_id).append('</tr>')
					$("#"+id+" #"+tbl_o_id).append(appendDetail(item.oflights,item.outboundStopoverDurations))
					$("#"+id).append('</table>')
					
			if(item.iflights&&item.iflights.length>0){
				var tbl_i_id="tbl_i_"+id
				$("#"+id).append('<table  id="'+ tbl_i_id+'" class="find">')
				var trid=tbl_i_id+'_tr'
				$("#"+id+" #"+tbl_i_id).append('<tr class="from" id="'+trid+'">'+
				'<td colspan="3" class="to">Điểm xuất phát <a href=""><b>'+'${session.parameters.arrivalCode}'+'</b></a></td>'
															+ '<td>Thời gian bay:<a href=""><b>'
															+ item.inboundDuration
															+ '</b></a> </td>'
															+ '</tr>')
									$("#" + id + " #" + tbl_i_id)
											.append(
													appendDetail(
															item.iflights,
															item.inboundStopoverDurations))
									$("#" + id).append('</table>')
								}
								var numberCustomers =
	${session.parameters.adults.toInteger()+session.parameters.kids.toInteger()+session.parameters.infants.toInteger()}
		$("#departure").append('</div>')

							}
						})
	</script>
	
</body>
</html>