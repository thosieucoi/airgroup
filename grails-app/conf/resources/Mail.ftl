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
    	<h1 style="color:#023557; font-size:18px; font-weight:bold; margin:10px 0; text-align:right">Ä�áº¶T VÃ‰ Má»ŒI LÃšC Má»ŒI NÆ I<br /><label style="color:#F00; font-size:25px; padding:10px">04-3512-2266</label></h1>
        <div class="maildetail" style="margin:5px 0; border:1px solid #ccc">
        	<ul class="mail-one" style="height:32px; background:#007CC3; padding:0; margin:0">
            	<li style=" list-style:none; float:left; padding:0 5px;width:150px"><a href="http://ahotua.com/flight/search" style="color:#f0f0f0; text-decoration:none; display:block; line-height:32px">TÃ¬m vÃ  Ä‘áº·t vÃ©</a></li>
                <li style=" list-style:none; float:left; padding:0 5px;width:150px"><a href="http://ahotua.com/feedback/list" style="color:#f0f0f0; text-decoration:none; display:block; line-height:32px">Ã� kiáº¿n khÃ¡ch hÃ ng</a></li>
                <li style=" list-style:none; float:left; padding:0 5px;width:150px"><a href="http://ahotua.com/contactUs/infor" style="color:#f0f0f0; text-decoration:none; display:block; line-height:32px">LiÃªn há»‡</a></li>
            </ul><br />
            <p style="padding:0 5px">KÃ­nh chÃ o Ã´ng <a href="" style="text-decoration:none; color:#0E70A5">${customerName}</a></p>
            <p style="padding:0 5px">Xin cáº£m Æ¡n QuÃ½ khÃ¡ch hÃ ng Ä‘Ã£ sá»­ dá»¥ng dá»‹ch vá»¥ cá»§a<a href="" style="text-decoration:none ; color: #0E70A5;"> ahotua</a></p>
            <div class="mail-list" style=" padding:0 5px 0 5px">
            	<h2 style="color:#007CC3; font-size:22px; font-weight:bold; border-bottom:2px solid #007CC3">Chi tiáº¿t Ä‘Æ¡n hÃ ng</h2>
                <table style="width:100%">
                	<tr>
                    	<td style="padding:5px 0"><label style=" font-weight:bold">MÃ£ Ä‘Æ¡n hÃ ng: </label></td>
                        <td class="mdh" style="padding:5px 0"><a href="" style="text-decoration:none; font-size: 20px;
    font-weight: bold; color:#0E70A5">${orderCode}</a></td>
                        <td style="padding:5px 0"></td style="padding:5px 0"><td></td>
                    </tr>
                    <tr>
                    	<td style="padding:5px 0"><label  style=" font-weight:bold">Tráº¡ng thÃ¡i: </label></td>
                        <td style="padding:5px 0"><a href="" style="text-decoration:none;font-style:italic;color:#333">${orderStatus}</a></td>
                        <td style="padding:5px 0"><label  style=" font-weight:bold">Tá»•ng giÃ¡: </label></td>
                        <td  class="mdh" style="padding:5px 0"><a href="" style="text-decoration:none; font-size: 20px;
    font-weight: bold; color:#0E70A5">${totalPrice}</a></td>
                    </tr>
                    <tr>
                    	<td style="padding:5px 0"><label  style=" font-weight:bold">Loáº¡i vÃ©: </label></td>
                        <td style="padding:5px 0"><a href="" style="text-decoration:none;color:#333">${fareType}</a></td>
                        <td style="padding:5px 0"></td style="padding:5px 0"><td></td>
                    </tr>
                </table>
                <p class="note" style="padding:5px 0;color:red; background:#F7E496">Ä�Ã¢y lÃ  email thÃ´ng bÃ¡o vá»� yÃªu cáº§u Ä‘áº·t vÃ© cá»§a quÃ½ khÃ¡ch chá»© chÆ°a pháº£i xÃ¡c nháº­n vá»� giÃ¡ vÃ  chá»—. ChÃºng tÃ´i sáº½ xá»­ lÃ½ yÃªu cáº§u nÃ y vÃ  xÃ¡c nháº­n láº¡i vá»›i quÃ½ khÃ¡ch trong thá»�i gian sá»›m nháº¥t.</p>
            </div>
             <div class="mail-list" style=" padding:0 5px 0 5px">
            	<h2 style="color:#007CC3; font-size:22px; font-weight:bold; border-bottom:2px solid #007CC3">ThÃ´ng tin hÃ nh khÃ¡ch</h2>
				 ${passengers}
            </div>
             <div class="mail-list" style=" padding: 0 5px 0;">
            	<h2 style="color:#007CC3; font-size:22px; font-weight:bold; border-bottom:2px solid #007CC3">Chi tiáº¿t hÃ nh trÃ¬nh</h2>
                    <table class="find" style=" margin:15px 0">
                    	<tr class="from" style="background:#D3D3D3;">
                        	<td colspan="3" class="go" style="padding:5px">Ä�iá»ƒm xuáº¥t phÃ¡t <a href="" style="text-decoration:none;color: #0E70A5;"><b>${outboundFromLocation}</b></a> </td>
                            <td style="padding:5px">Thá»�i gian bay:<a href="" style="text-decoration:none;color:#333"><b>${outboundDuration}</b></a> </td>
                        </tr>
						<#list outboundFlights as item>
						<#if item_index &gt; 0>
						<tr>
                                	<td colspan="4" class="qt-stop" style="background: none repeat scroll 0 0 #FEF4EB; border-bottom: 1px solid #FCDABF;border-top: 1px solid #FCDABF;padding: 8px 0;text-align: center;">
                                    	 Ä�á»•i mÃ¡y bay táº¡i<b class="change-address"> ${outboundFlights[item_index-1].arrivalAirport} </b>Thá»�i gian chá»�:
                                         <b class="change-time"> ${outbound.outboundStopoverDurations[item_index-1]} </b>
                                    	
                                    </td>
                         </tr>
						</#if>
                    	<tr>
                        	<td  style="padding:5px" rowspan="2" width="70px"><img src="http://ahotua.com/images/sm${item.airlineCode}.gif" /></td>
                            <td style="padding:5px" width="170px">Tá»«<a href="" style="text-decoration:none;color:#333"> <b>${item.departureAirport}</b></a></td>
                            <td style="padding:5px" width="170px">Tá»›i<a href="" style="text-decoration:none;color:#333"> <b>${item.arrivalAirport}</b></a></td>
                            <td style="padding:5px" rowspan="2" width="180px" >${item.carrier}<br>(<b>${item.airlineCode}
									${item.flightNumber}</b>)<br>
                            	<span>Háº¡ng VÃ© :<b>Háº¡ng phá»• thÃ´ng</b></span>
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
                        	<td style="padding:5px" colspan="3" class="to">Ä�iá»ƒm xuáº¥t phÃ¡t <a href="" style="text-decoration:none;color:#333"><b>${inboundFromLocation}</b></a> </td>
                            <td style="padding:5px">Thá»�i gian bay:<a href="" style="text-decoration:none;color:#333"><b> ${inboundDuration}</b></a> </td>
                        </tr>
						<#list inboundFlights as item>
						<#if item_index &gt; 0>
						<tr>
                                	<td colspan="4" class="qt-stop" style="background: none repeat scroll 0 0 #FEF4EB; border-bottom: 1px solid #FCDABF;border-top: 1px solid #FCDABF;padding: 8px 0;text-align: center;">
                                    	 Ä�á»•i mÃ¡y bay táº¡i<b class="change-address"> ${inboundFlights[item_index-1].arrivalAirport} </b>Thá»�i gian chá»�:
                                         <b class="change-time"> ${inbound.inboundStopoverDurations[item_index-1]} </b>
                                    	
                                    </td>
                         </tr>
						</#if>
                    	<tr>
                        	<td style="padding:5px" rowspan="2" width="70px"><img src="http://ahotua.com/images/sm${item.airlineCode}.gif" /></td>
                            <td style="padding:5px" width="170px">Tá»«<a href="" style="text-decoration:none;color:#333"> <b>${item.departureAirport}</b></a></td>
                            <td style="padding:5px" width="170px">Tá»›i<a href="" style="text-decoration:none;color:#333"> <b>${item.arrivalAirport}</b></a></td>
                            <td style="padding:5px" rowspan="2" width="180px" >${item.carrier}<br>(<b>${item.airlineCode}
									${item.flightNumber}</b>)<br>
                            	<span>Háº¡ng VÃ© :<b>Háº¡ng phá»• thÃ´ng</b></span>
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
            	<h2 style="color:#007CC3; font-size:22px; font-weight:bold; border-bottom:2px solid #007CC3">HÃ¬nh thá»©c thanh toÃ¡n</h2>
                <p style="padding:5px 0">HÃ¬nh thá»©c thanh toÃ¡n cá»§a quÃ½ khÃ¡ch Ä‘Ã£ lá»±a chá»�n : <a href="" style="text-decoration:none; color: #0E70A5;">${paymentType}</a></p>
                <p style="padding:5px 0">
                	<a href="" style="text-decoration:none; color: #0E70A5;"><b>VÄƒn phÃ²ng HÃ  Ná»™i</b></a><br />
                    <a href="" style="text-decoration:none; color: #0E70A5;"><b> Ä�á»‹a chá»‰:</b> Sá»‘ 19F Nguyá»…n Khang-Trung HÃ²a- Cáº§u Giáº¥y- HÃ  Ná»™i</a><br />
   					 <a href="" style="text-decoration:none; color: #0E70A5;"><b>Ä�iá»‡n thoáº¡i : </b>04-3512-2266</a><br />

                </p>
                <fieldset style="border: 1px solid #ccc; background: #FFEDED">
                <legend style="text-align: center;color: #007CC3;font-size: 18px;font-weight: bold;">ThÃ´ng tin giao dá»‹ch</legend>
                <table style="width: 100%;table-layout: fixed; font-family: arial; font-size: 13px;">
			   <tr>
			     <td valign="top">
				 <div class="bank-logo">
                                    <img src="http://ahotua.com/images/img/VietcomBank.png" width="90" height="50" alt="TechcomBank" />
                                </div>
                                <div class="bank-detail">
                                    <span class="title title-b" style="font-weight: bold; line-height: 30px;">NgÃ¢n hÃ ng Vietcombank </span>
                                    <br />
                                    <span>TÃªn TK: Cty TNHH ThÆ°Æ¡ng Máº¡i vÃ  Dá»‹ch Vá»¥ Du lá»‹ch Ahotua Viá»‡t Nam</span>
                                    <br />
                                    <span>Sá»‘ TK: 0021-0002-93700</span>
                                </div>
                                <div class="clearfix"></div>
								
				 </td>
				 <td valign="top">
				 <div class="bank-logo">
                                    <img src="http://ahotua.com/images/img/TechcomBank.png" width="90" height="50" alt="VietcomBank" />
                                </div>
                                <div class="bank-detail">
                                    <span class="title title-b" style="font-weight: bold; line-height: 30px;">NgÃ¢n hÃ ng Techcombank</span>
                                    <br />
                                    <span>TÃªn TK: Cty THNN ThÆ°Æ¡ng Máº¡i vÃ  Dá»‹ch Vá»¥ Du Lá»‹ch Ahotua Viá»‡t Nam</span>
                                    <br />
                                    <span>Sá»‘ TK: 19128392003883</span>
                                </div>
                                <div class="clearfix"></div>
				 </td>
				 <td valign="top">
				 <div class="bank-logo">
                                    <img src="http://ahotua.com/images/img/MBBank.png" width="90" height="50" alt="MBBank" />
                                </div>
                                <div class="bank-detail">
                                    <span class="title title-b" style="font-weight: bold; line-height: 30px;">NgÃ¢n hÃ ng QUÃ‚N Ä�á»˜I</span>
                                    <br />
                                    <span>TÃªn TK: VÅ© Thá»‹ Há»“ng VÃ¢n</span>
                                    <br />
                                    <span>Sá»‘ TK: 0580107012008</span>
                                </div>
                                <div class="clearfix"></div>
				 
				 </td>
			   </tr>
			   <tr>
				<td valign="top">
				<div class="bank-logo" style="margin-top: 27px;">
                                    <img src="http://ahotua.com/images/img/BIDV.jpg" width="90" height="50" alt="BIDV" />
                                </div>
                                <div class="bank-detail">
                                    <span class="title title-b" style="font-weight: bold; line-height: 30px;">NgÃ¢n hÃ ng BIDV</span>
                                    <br />
                                    <span>TÃªn TK: Cty THNN ThÆ°Æ¡ng Máº¡i vÃ  Dá»‹ch Vá»¥ Du Lá»‹ch Ahotua Viá»‡t Nam</span>
                                    <br />
                                    <span>Sá»‘ TK: 12510000635331</span>
                                </div>
                                <div class="clearfix"></div>
				</td>
				<td valign="top">
				<div class="bank-logo" style="margin-top: 27px;">
                                    <img src="http://ahotua.com/images/img/AgriBank.jpg" width="90" height="50" alt="AgriBank" />
                                </div>
                                <div class="bank-detail">
                                    <span class="title title-b" style="font-weight: bold; line-height: 30px;">NgÃ¢n hÃ ng nÃ´ng nghiá»‡p</span>
                                    <br />
                                    <span>TÃªn TK:  Cty THNN ThÆ°Æ¡ng Máº¡i vÃ  Dá»‹ch Vá»¥ Du Lá»‹ch Ahotua Viá»‡t Nam</span>
                                    <br />
                                    <span>Sá»‘ TK:  1400206026412</span>
                                </div>
                                <div class="clearfix"></div>
				</td>
				<td valign="top">
				
				<div class="bank-logo" style="margin-top: 27px;">
                                    <img src="http://ahotua.com/images/img/VietcomBank.png" width="90" height="50" alt="MBBank" />
                                </div>
                                <div class="bank-detail">
                                    <span class="title title-b" style="font-weight: bold; line-height: 30px;">NgÃ¢n hÃ ng Vietcombank </span>
                                    <br />
                                    <span>TÃªn TK:  Cty TNHH ThÆ°Æ¡ng Máº¡i vÃ  Dá»‹ch Vá»¥ Du lá»‹ch Ahotua Viá»‡t Nam</span>
                                    <br />
                                    <span>Sá»‘ TK:  	0021-0002-93700</span>
                                </div>
                                <div class="clearfix"></div>
                                <div class="bank-logo" style="margin-top: 27px;">
                                    <img src="http://ahotua.com/images/bank-donga-logo.gif" width="90" height="50" alt="MBBank" />
                                </div>
                                <div class="bank-detail">
                                    <span class="title title-b" style="font-weight: bold; line-height: 30px;">NgÃ¢n HÃ ng Ä�Ã´ng Ã� </span>
                                    <br />
                                    <span>TÃªn TK:  VÅ© Thá»‹ Há»“ng VÃ¢n </span>
                                    <br />
                                    <span>Sá»‘ TK:  	0108817986</span>
                                </div>
                                <div class="clearfix"></div>
                         <div class="bank-logo" style="margin-top: 27px;">
                                    <img src="http://ahotua.com/images/bank-acb-logo.gif" width="90" height="50" alt="MBBank" />
                                </div>
                                <div class="bank-detail">
                                    <span class="title title-b" style="font-weight: bold; line-height: 30px;">NgÃ¢n HÃ ng ACB </span>
                                    <br />
                                    <span>TÃªn TK:  VÅ© Thá»‹ Há»“ng VÃ¢n </span>
                                    <br />
                                    <span>Sá»‘ TK:  	183916659</span>
                                </div>
                                <div class="clearfix"></div>       
								
				</td>
			   </tr>
			</table>
                </fieldset>
                 <p class="note" style=" color:red; background:#F7E496;padding:5px 0"><b>ChÃº Ã½:</b><br /> Email nÃ y Ä‘Æ°á»£c gá»­i tá»± Ä‘á»™ng bá»Ÿi há»‡ thá»‘ng cá»§a Ahotua. Vui lÃ²ng khÃ´ng tráº£ lá»�i email nÃ y.</p><br />
            </div>
			
            <ul class="mail-one" style=" height:32px; background:#007CC3; padding:0; margin:0">
            	<li style=" list-style:none; float:left; padding:0 5px;width:150px"><a href="http://ahotua.com/flight/search" style="color:#f0f0f0; text-decoration:none; display:block; line-height:32px">TÃ¬m vÃ  Ä‘áº·t vÃ©</a></li>
                <li style=" list-style:none; float:left; padding:0 5px;width:150px"><a href="http://ahotua.com/feedback/list" style="color:#f0f0f0; text-decoration:none; display:block; line-height:32px">Ã� kiáº¿n khÃ¡ch hÃ ng</a></li>
                <li style=" list-style:none; float:left; padding:0 5px;width:150px"><a href="http://ahotua.com/contactUs/infor" style="color:#f0f0f0; text-decoration:none; display:block; line-height:32px">LiÃªn há»‡</a></li>
            </ul>
        </div>
        
    </div>
                            
    
</body>
</html>

