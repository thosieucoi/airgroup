<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Mail</title>
	<meta name="viewport" content="width=device-width; initial-scale=1.0; 	maximum-scale=1.0; user-scalable=0;">
	<meta name="keywords" content="">
	<meta name="description" content="{TITLE}">

</head>

<body>
	<div id="mail" style="width:680px; margin:auto; padding:0">
    	<h1 style="color:#023557; font-size:18px; font-weight:bold; margin:10px 0; text-align:right">ĐẶT VÉ MỌI LÚC MỌI NƠI<br /><label style="color:#F00; font-size:25px; padding:10px">04 66813026</label></h1>
        <div class="maildetail" style="margin:5px 0; border:1px solid #ccc">
        	<ul class="mail-one" style="height:32px; background:#007CC3; padding:0; margin:0">
            	<li style=" list-style:none; float:left; padding:0 5px;width:150px"><a href="http://ahotua.vn/flight/search" style="color:#f0f0f0; text-decoration:none; display:block; line-height:32px">Tìm và đặt vé</a></li>
                <li style=" list-style:none; float:left; padding:0 5px;width:150px"><a href="http://ahotua.vn/feedback/list" style="color:#f0f0f0; text-decoration:none; display:block; line-height:32px">Ý kiến khách hàng</a></li>
                <li style=" list-style:none; float:left; padding:0 5px;width:150px"><a href="http://ahotua.vn/contactUs/infor" style="color:#f0f0f0; text-decoration:none; display:block; line-height:32px">Liên hệ</a></li>
            </ul><br />
            <p style="padding:0 5px">Kính chào ông <a href="" style="text-decoration:none; color:#0E70A5">${customerName}</a></p>
            <p style="padding:0 5px">Xin cảm ơn Quý khách hàng đã sử dụng dịch vụ của<a href="" style="text-decoration:none ; color: #0E70A5;"> ahotua.vn</a></p>
            <div class="mail-list" style=" padding:0 5px 0 5px">
            	<h2 style="color:#007CC3; font-size:22px; font-weight:bold; border-bottom:2px solid #007CC3">Chi tiết đơn hàng</h2>
                <table style="width:100%">
                	<tr>
                    	<td style="padding:5px 0"><label style=" font-weight:bold">Mã đơn hàng: </label></td>
                        <td class="mdh" style="padding:5px 0"><a href="" style="text-decoration:none; font-size: 20px;
    font-weight: bold; color:#0E70A5">${orderCode}</a></td>
                        <td style="padding:5px 0"></td style="padding:5px 0"><td></td>
                    </tr>
                    <tr>
                    	<td style="padding:5px 0"><label  style=" font-weight:bold">Trạng thái: </label></td>
                        <td style="padding:5px 0"><a href="" style="text-decoration:none;font-style:italic;color:#333">${orderStatus}</a></td>
                        <td style="padding:5px 0"><label  style=" font-weight:bold">Tổng giá: </label></td>
                        <td  class="mdh" style="padding:5px 0"><a href="" style="text-decoration:none; font-size: 20px;
    font-weight: bold; color:#0E70A5">${totalPrice}</a></td>
                    </tr>
                    <tr>
                    	<td style="padding:5px 0"><label  style=" font-weight:bold">Loại vé: </label></td>
                        <td style="padding:5px 0"><a href="" style="text-decoration:none;color:#333">${fareType}</a></td>
                        <td style="padding:5px 0"></td style="padding:5px 0"><td></td>
                    </tr>
                </table>
                <p class="note" style="padding:5px 0;color:red; background:#F7E496">Đây là email thông báo về yêu cầu đặt vé của quý khách. Chúng tôi sẽ xử lý yêu cầu này và xác nhận lại với quý khách trong thời gian sớm nhất. Xin chân thành cảm ơn quý khách đã sử dụng dịch vụ</p>
            </div>
             <div class="mail-list" style=" padding:0 5px 0 5px">
            	<h2 style="color:#007CC3; font-size:22px; font-weight:bold; border-bottom:2px solid #007CC3">Thông tin hành khách</h2>
				 ${passengers}
            </div>
             <div class="mail-list" style=" padding: 0 5px 0;">
            	<h2 style="color:#007CC3; font-size:22px; font-weight:bold; border-bottom:2px solid #007CC3">Chi tiết hành trình</h2>
                    <table class="find" style=" margin:15px 0">
                    	<tr class="from" style="background:#D3D3D3;">
                        	<td colspan="3" class="go" style="padding:5px">Điểm xuất phát <a href="" style="text-decoration:none;color: #0E70A5;"><b>${outboundFromLocation}</b></a> </td>
                            <td style="padding:5px">Thời gian bay:<a href="" style="text-decoration:none;color:#333"><b>${outboundDuration}</b></a> </td>
                        </tr>
						<#list outboundFlights as item>
						<#if item_index &gt; 0>
						<tr>
                                	<td colspan="4" class="qt-stop" style="background: none repeat scroll 0 0 #FEF4EB; border-bottom: 1px solid #FCDABF;border-top: 1px solid #FCDABF;padding: 8px 0;text-align: center;">
                                    	 Đổi máy bay tại<b class="change-address"> ${outboundFlights[item_index-1].arrivalAirport} </b>Thời gian chờ:
                                         <b class="change-time"> ${outbound.outboundStopoverDurations[item_index-1]} </b>
                                    	
                                    </td>
                         </tr>
						</#if>
                    	<tr>
                        	<td  style="padding:5px" rowspan="2" width="70px"><img src="http://ahotua.vn/images/sm${item.airlineCode}.gif" /></td>
                            <td style="padding:5px" width="170px">Từ<a href="" style="text-decoration:none;color:#333"> <b>${item.departureAirport}</b></a></td>
                            <td style="padding:5px" width="170px">Tới<a href="" style="text-decoration:none;color:#333"> <b>${item.arrivalAirport}</b></a></td>
                            <td style="padding:5px" rowspan="2" width="180px" >${item.carrier}<br>(<b>${item.airlineCode}
									${item.flightNumber}</b>)<br>
                            	<span>Hạng Vé :<b>Hạng phổ thông</b></span>
                            </td>
                            
                        </tr>
                        <tr>
                        	<td style="padding:5px"><a href="" style="text-decoration:none;color:#333"> ${item.departureTime}, <b>${item.departureDate}</b></a></td>
                            <td style="padding:5px"><a href="" style="text-decoration:none;color:#333">${item.arrivalTime}, <b>${item.arrivalDate}</b></a></td>
                        </tr>
						</#list>
                    </table>
					<#if hasInbound>
                     <table class="find" style=" margin:15px 0">
                    	<tr class="from" style="background:#D3D3D3;">
                        	<td style="padding:5px" colspan="3" class="to">Điểm xuất phát <a href="" style="text-decoration:none;color:#333"><b>${inboundFromLocation}</b></a> </td>
                            <td style="padding:5px">Thời gian bay:<a href="" style="text-decoration:none;color:#333"><b> ${inboundDuration}</b></a> </td>
                        </tr>
						<#list inboundFlights as item>
						<#if item_index &gt; 0>
						<tr>
                                	<td colspan="4" class="qt-stop" style="background: none repeat scroll 0 0 #FEF4EB; border-bottom: 1px solid #FCDABF;border-top: 1px solid #FCDABF;padding: 8px 0;text-align: center;">
                                    	 Đổi máy bay tại<b class="change-address"> ${inboundFlights[item_index-1].arrivalAirport} </b>Thời gian chờ:
                                         <b class="change-time"> ${inbound.inboundStopoverDurations[item_index-1]} </b>
                                    	
                                    </td>
                         </tr>
						</#if>
                    	<tr>
                        	<td style="padding:5px" rowspan="2" width="70px"><img src="http://ahotua.vn/images/sm${item.airlineCode}.gif" /></td>
                            <td style="padding:5px" width="170px">Từ<a href="" style="text-decoration:none;color:#333"> <b>${item.departureAirport}</b></a></td>
                            <td style="padding:5px" width="170px">Tới<a href="" style="text-decoration:none;color:#333"> <b>${item.arrivalAirport}</b></a></td>
                            <td style="padding:5px" rowspan="2" width="180px" >${item.carrier}<br>(<b>${item.airlineCode}
									${item.flightNumber}</b>)<br>
                            	<span>Hạng Vé :<b>Hạng phổ thông</b></span>
                            </td>
                            
                        </tr>
                        <tr>
                        	<td style="padding:5px 0"><a href="" style="text-decoration:none;color:#333"> ${item.departureTime}, <b>${item.departureDate}</b></a></td>
                            <td style="padding:5px 0"><a href="" style="text-decoration:none;color:#333">${item.arrivalTime}, <b>${item.arrivalDate}</b></a></td>
                        </tr>
						</#list>
                    </table>
					</#if>
            </div>
            <div class="mail-list" style=" padding: 0 5px 0;">
            	<h2 style="color:#007CC3; font-size:22px; font-weight:bold; border-bottom:2px solid #007CC3">Hình thức thanh toán</h2>
                <p style="padding:5px 0">Hình thức thanh toán của quý khách đã lựa chọn : <a href="" style="text-decoration:none; color: #0E70A5;">${paymentType}</a></p>
                <p style="padding:5px 0">
                	<a href="" style="text-decoration:none; color: #0E70A5;"><b>Văn phòng Hà Nội</b></a><br />
                    <a href="" style="text-decoration:none; color: #0E70A5;"><b> Địa chỉ:</b> Số nhà 21, ngách 100/24, ngõ 100, đường Hoàng Quốc Việt, phường Nghĩa Đô, quận Cầu Giấy, Thành phố Hà Nội, Việt Nam</a><br />
   					 <a href="" style="text-decoration:none; color: #0E70A5;"><b>Điện thoại : </b>04 66813026</a><br />

                </p>
                <fieldset style="border: 1px solid #ccc; background: #FFEDED">
                <legend style="text-align: center;color: #007CC3;font-size: 18px;font-weight: bold;">Thông tin giao dịch</legend>
                <table style="width: 100%;table-layout: fixed; font-family: arial; font-size: 13px;">
			   
			   <tr>
			     <td valign="top">
				 <div class="bank-logo">
                                    <img src="http://ahotua.vn/images/img/TechcomBank.png" width="90" height="50" alt="Techcombank" />
                                </div>
                                <div class="bank-detail">
                                    <span class="title title-b" style="font-weight: bold; line-height: 30px;">Ngân hàng Techcombank </span>
                                    <br />
                                    <span>Tên tài khoản: Công ty cổ phần Ahotua Việt Nam</span>
                                    <br />
                                    <span>Số tài khoản: 19129041129011</span>
                                    <br />
                                    <span>Địa chỉ: Ngân hàng TMCP Kỹ Thương Việt Nam -  chi nhánh Hoàng Quốc Việt</span>
                                </div>
                                <div class="clearfix"></div>
								
				 </td>
			   </tr>
			   
			   <tr>
			     <td valign="top">
				 <div class="bank-logo">
                                    <img src="http://ahotua.vn/images/img/VietcomBank.png" width="90" height="50" alt="VietcomBank" />
                                </div>
                                <div class="bank-detail">
                                    <span class="title title-b" style="font-weight: bold; line-height: 30px;">Ngân hàng Vietcombank </span>
                                    <br />
                                    <span>Tên tài khoản: Công ty cổ phần Ahotua Việt Nam</span>
                                    <br />
                                    <span>Số tài khoản: 0491000056160</span>
                                    <br />
                                    <span>Địa chỉ: Ngân hàng TMCP Ngoại thương Việt Nam -  chi nhánh Thăng Long</span>
                                </div>
                                <div class="clearfix"></div>
								
				 </td>
			   </tr>
			   
			   <tr>
			     <td valign="top">
				 <div class="bank-logo">
                                    <img src="http://ahotua.vn/images/img/BIDV.jpg" width="90" height="50" alt="BIDV" />
                                </div>
                                <div class="bank-detail">
                                    <span class="title title-b" style="font-weight: bold; line-height: 30px;">Ngân hàng BIDV </span>
                                    <br />
                                    <span>Tên tài khoản: Công ty cổ phần Ahotua Việt Nam</span>
                                    <br />
                                    <span>Số tài khoản: 21510001634267</span>
                                    <br />
                                    <span>Địa chỉ: Ngân Hàng Đầu Tư Và Phát Triển Việt Nam -  chi nhánh Cầu Giấy</span>
                                </div>
                                <div class="clearfix"></div>
								
				 </td>
			   </tr>
			   
			   <tr>
			     <td valign="top">
				 <div class="bank-logo">
                                    <img src="http://ahotua.vn/images/img/vietin.jpg" width="90" height="50" alt="Vietinbank" />
                                </div>
                                <div class="bank-detail">
                                    <span class="title title-b" style="font-weight: bold; line-height: 30px;">Ngân hàng Vietinbank </span>
                                    <br />
                                    <span>Tên tài khoản: Công ty cổ phần Ahotua Việt Nam</span>
                                    <br />
                                    <span>Số tài khoản: 10201-000216255-5</span>
                                    <br />
                                    <span>Địa chỉ: Ngân Hàng TMCP Công Thương Việt Nam -  chi nhánh Nam Thăng Long</span>
                                </div>
                                <div class="clearfix"></div>
								
				 </td>
			   </tr>
			</table>
                </fieldset>
                 <p class="note" style=" color:red; background:#F7E496;padding:5px 0"><b>Chú ý:</b><br /> Email này được gửi tự động bởi hệ thống của Ahotua. Vui lòng không trả lời email này.</p><br />
            </div>
			
            <ul class="mail-one" style=" height:32px; background:#007CC3; padding:0; margin:0">
            	<li style=" list-style:none; float:left; padding:0 5px;width:150px"><a href="http://ahotua.vn/flight/search" style="color:#f0f0f0; text-decoration:none; display:block; line-height:32px">Tìm và đặt vé</a></li>
                <li style=" list-style:none; float:left; padding:0 5px;width:150px"><a href="http://ahotua.vn/feedback/index" style="color:#f0f0f0; text-decoration:none; display:block; line-height:32px">Ý kiến khách hàng</a></li>
                <li style=" list-style:none; float:left; padding:0 5px;width:150px"><a href="http://ahotua.vn/contactUs/infor" style="color:#f0f0f0; text-decoration:none; display:block; line-height:32px">Liên hệ</a></li>
            </ul>
        </div>
        
    </div>
                            
    
</body>
</html>

