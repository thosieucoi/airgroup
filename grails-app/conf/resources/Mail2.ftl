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
	<div id="mail" style="width:780px; margin:auto; padding:0">
	<div class="maildetail" style="margin:5px 0; border:1px solid #ccc">
    	<table style="float:left">
        	<tr>
            	<td><label>PREPARED FOR</label></td>
            </tr>
			<#list passengers as passenger>
            <tr>
            	<td><a href="" style=" text-decoration:none; color:#333; font-size:20px; font-weight:bold">${passenger.name}</a></td>
            </tr>
			</#list>
        </table>
    	<img src="http://ahotua.vn/static/images/newuiimg/logo.png" style="float:right; margin:5px; width:115px;"/>
        <div style=" clear:both"></div>
    
            <div class="mail-list" style=" padding:0 5px 0 5px">
            	<h2 style="color:#007CC3; font-size:20px; border-bottom:2px solid #007CC3">RESERVATION CODE: <a href="" style="text-decoration:none; font-size:22px; font-weight:bold; color:#007CC3">${reservationCode}</a></h2>
                <table style="width:100%">
                	<tr>
                    	<td colspan="4" style="padding:0 0 30px 0"><label style="font-size:18px">DEPARTURE :</label> <a href="" style="color:#333; text-decoration:none; font-weight:bold; font-size:18px; margin:0 20px 0 0">${odepartureDate}</a> <label style="text-align:right; color:#A0A0A0"> Please verify flight times prior to departure</label></td>
                    </tr>

                	<tr>
                    	<td style="padding:5px 0"><a href="" style="text-decoration:none; color:#333; font-weight:bold">${oairlineName}</a><BR />
                        	<a href="" style="text-decoration:none; color:#333">${oflightNumber}</a>
                        </td>
                        <td class="mdh" style="padding:5px 0"><a href="" style="text-decoration:none; color:#333; font-weight:bold;color:#0E70A5">${odepartureCityCode}</a><BR />
                        	<a href="" style="text-decoration:none; color:#333">${odepartureCityName}</a></td>
                       <td class="mdh" style="padding:5px 0"><a href="" style="text-decoration:none; color:#333;font-weight:bold;color:#0E70A5">${oarrivalCityCode}</a><BR />
                        	<a href="" style="text-decoration:none; color:#333">${oarrivalCityName}</a></td>
                    </tr>
                    <tr>
                    	<td style="padding:5px 0"><label>Duration: </label><br />
                        	<a href="" style="text-decoration:none; color:#333">${oduration}</a>
                        </td>
                        <td style="padding:5px 0"><label>Departing At:</label><br /><a href="" style="text-decoration:none;color:#333;font-size:30px;color:#0E70A5">${odepartureTime}</a></td>
                        <td style="padding:5px 0"><label>Arriving At:</label><br /><a href="" style="text-decoration:none;color:#333;font-size:30px;color:#0E70A5">${oarrivalTime}</a></td>
                        <td  class="mdh" style="padding:5px 0"><label>Stop(s):</label><a href="" style="text-decoration:none;
   color:#333">${ostops}</a></td>
                    </tr>
                </table>
                <table style="width:100%">
                	<tr>
                    	<td><label> Passenger Name:</label></td>
                    	<td><label> Ticket Number:</label></td>
                        <td><label> Code:</label></td>
                        <td><label> Status:</label></td>
                    </tr>
					<#list passengers as passenger>
                    <tr>
                    	<td><a href="" style="text-decoration:none; color:#666">${passenger.name}</a></td>
                    	<td><a href="" style="text-decoration:none; color:#666">${passenger.ticketNumber?split(",")[0]}</a></td>
                        <td><a href="" style="text-decoration:none; color:#666">${passenger.seatNumber?split(",")[0]}</a></td>
                        <td><a href="" style="text-decoration:none; color:#666">Confirmed</a></td>
                    </tr>
					</#list>
                </table>
                <#if hasInbound>
                <table style="width:100%; border-top:1px dotted #ccc; margin-top:15px; padding-top:15px">
                	<tr>
                    	<td colspan="4" style="padding:0 0 30px 0"><label style="font-size:18px">DEPARTURE :</label> <a href="" style="color:#333; text-decoration:none; font-weight:bold; font-size:18px; margin:0 20px 0 0">${idepartureDate}</a> <label style="text-align:right; color:#A0A0A0"> Please verify flight times prior to departure</label></td>
                    </tr>
                	<tr>
                    	<td style="padding:5px 0"><a href="" style="text-decoration:none; color:#333; font-weight:bold">${iairlineName}</a><BR />
                        	<a href="" style="text-decoration:none; color:#333">${iflightNumber}</a>
                        </td>
                        <td class="mdh" style="padding:5px 0"><a href="" style="text-decoration:none; color:#333; font-weight:bold;color:#0E70A5">${idepartureCityCode}</a><BR />
                        	<a href="" style="text-decoration:none; color:#333">${idepartureCityName}</a></td>
                       <td class="mdh" style="padding:5px 0"><a href="" style="text-decoration:none; color:#333;font-weight:bold;color:#0E70A5">${iarrivalCityCode}</a><BR />
                        	<a href="" style="text-decoration:none; color:#333">${iarrivalCityName}</a></td>
                    </tr>
                    <tr>
                    	<td style="padding:5px 0"><label>Duration: </label><br />
                        	<a href="" style="text-decoration:none; color:#333">${iduration}</a>
                        </td>
                        <td style="padding:5px 0"><label>Departing At:</label><br /><a href="" style="text-decoration:none;color:#333;font-size:30px;color:#0E70A5">${idepartureTime}</a></td>
                        <td style="padding:5px 0"><label>Arriving At:</label><br /><a href="" style="text-decoration:none;color:#333;font-size:30px;color:#0E70A5">${iarrivalTime}</a></td>
                        <td  class="mdh" style="padding:5px 0"><label>Stop(s):</label><a href="" style="text-decoration:none;
   color:#333">${istops}</a></td>
                    </tr>
                </table>
                 <table style="width:100%">
                	<tr>
                    	<td><label> Passenger Name:</label></td>
                    	<td><label> Ticket Number:</label></td>
                        <td><label> Code:</label></td>
                        <td><label> Status:</label></td>
                    </tr>
					<#list passengers as passenger>
                    <tr>
                    	<td><a href="" style="text-decoration:none; color:#666">${passenger.name}</a></td>
                    	<td><a href="" style="text-decoration:none; color:#666">${passenger.ticketNumber?split(",")[1]}</a></td>
                        <td><a href="" style="text-decoration:none; color:#666">${passenger.seatNumber?split(",")[1]}</a></td>
                        <td><a href="" style="text-decoration:none; color:#666">Confirmed</a></td>
                    </tr>
					</#list>
                </table>
                </#if>
        </div>
        
    </div>
  </div>                          
    
</body>
</html>

