//var fareType = $('input[name="fareType"]').val();
jQuery(document).ready(function($){
	var fareType = $('input[name="fareType"]').val();
	var _fareType;
	var myEventsArrival = [];
    $.ajax({
		async : false,
		type : "GET",
		contentType : "application/json; charset=utf-8",
		url : "/cheapFlight/listCheapFlightArrival",
		dataType : "json",
		success : function(jsonData) {
			for (var i=0; i<jsonData.length; i++) {
				arrival = jsonData[i].arrival;
				departure = jsonData[i].departure;
				inbound_date = new Date(jsonData[i].inbound_date);
				outbound_date = new Date(jsonData[i].outbound_date);
				price = setPriceOnCalendar(jsonData[i].price);
				airline = jsonData[i].airline;
				flight_number = jsonData[i].flight_number;
				myEventsArrival.push( {
				    title : price,
				    start : outbound_date,
				    departure: departure,
				    arrival: arrival,
				    inbound_date: inbound_date,
				    outbound_date: outbound_date,
				    airline: airline,
				    flight_number: flight_number,
				    price: jsonData[i].price.toLocaleString("vi-VN", {style: "currency", currency: "VND", minimumFractionDigits: 0}),
				    priceNumber: jsonData[i].price
			  } );  
			}
		}
	});
	
	// Add round-trip calendar if customer selects round-trip
    $('input[name="fareType"]').change(function(){
    	fareType = $(this).val();
    	_fareType = $(this).val();
    	if($(this).val() == "roundtrip"){
    		var fullcalendarRoundTrip = $('#fullcalendarRoundTrip').html();
    		if(fullcalendarRoundTrip == "" || fullcalendarRoundTrip == null){
    			$('#fullcalendarRoundTrip').html("");
        		$('#fullcalendarRoundTrip').fullCalendar({
        	        editable: false,
        	        height: 300,
        	        header: {
        	            left: '',
        	            center: 'prev, title next',
        	            right: ''
        	        },
        	        eventMouseover: function (data, event, view) {
        	            

        	        	tooltip = '<div class="tooltiptopicevent" style="border:2px solid;border-color:#E02F2F;width:auto;height:auto;background:white;position:absolute;z-index:10001; line-height: 140%;-moz-border-radius-topleft:10px;-webkit-border-top-left-radius:10px;-moz-border-radius-topright:10px;-webkit-border-top-right-radius:10px;-moz-border-radius-bottomleft:10px;-webkit-border-bottom-left-radius:10px;-moz-border-radius-bottomright:10px;-webkit-border-bottom-right-radius:10px;">'+
        	        	'<div style="-moz-border-radius-topleft:10px;-webkit-border-top-left-radius:10px;-moz-border-radius-topright:10px;-webkit-border-top-right-radius:10px;text-align:center;font-size:15px;color:white;background:#E02F2F;width:100%:;height:40px;padding-top:10px"><strong>Nhấp chuột để xem thông tin chi tiết</strong></div><div style="padding:10px 10px 10px 10px;"><table width="400px"><tr><td style="margin-right:10px">'+ '<img src="/images/sm'+data.airline+'.gif"/></td><td style="border-right:1px solid gray;"><span style="font-weight: bold;">' + data.departure + '</span></br>' + 'Ngày đi: ' + getDateMonth(data.start) +
        	        	'</td></tr></br><tr><td><hr style="border-color:gray;color:gray;background-color:gray;"/></td><td style="border-right:1px solid gray;"><hr style="border-color:gray;color:gray;background-color:gray;"/></td><td style="text-align:center;"><span style="font-size:20px;color:#E02F2F;"><strong>'+ data.price.toLocaleString("vi-VN", {style: "currency", currency: "VND", minimumFractionDigits: 0}) +'</strong></span></td></tr><tr><td style="margin-right:10px">'+ '<img src="/images/sm'+data.airline+'.gif"/></td><td style="border-right:1px solid gray;"><span style="font-weight: bold;">' + data.arrival + '</span></br>' + 'Ngày về: ' + getDateMonth(data.inbound_date) + '</td></tr></table></div></div>';

        	            $("body").append(tooltip);
        	            $(this).mouseover(function (e) {
        	                $(this).css('z-index', 10000);
        	                $('.tooltiptopicevent').fadeIn('500');
        	                $('.tooltiptopicevent').fadeTo('10', 1.9);
        	            }).mousemove(function (e) {
        	                $('.tooltiptopicevent').css('top', e.pageY + 10);
        	                $('.tooltiptopicevent').css('left', e.pageX + 20);
        	            });


        	        },
        	        eventMouseout: function (data, event, view) {
        	            $(this).css('z-index', 8);

        	            $('.tooltiptopicevent').remove();

        	        },
        	        
        	        eventClick: function (data, event, view) {
        	        	var submitForm = $('body').find('form[name="submitCalendarFlightForm"] table[id="1"]');
        	        	var cheapFareBookingRoundTrip = $('body').find('form[name="submitCalendarFlightForm"] table tbody tr[id="4"]').html();
        	        	var cheapFareBookingRoundTripObj = $('body').find('form[name="submitCalendarFlightForm"] table[id="1"] tbody tr[id="6"]');
        	        	var select_oneNode = $('body').find('form[name="submitCalendarFlightForm"] table[id="1"] tbody tr[id="4"]');
        	        	
        	        	var element='<tr class="seclect-one1" id="'+4+'">';
        	        	element+='<td width="60px"><span style="font-size:10px;font-weight:bold;">Chuyến về:</span></td>';
        			    element+='<td width="70px">'+'<img src="/images/sm' + data.airline + '.gif"/>'+'</td>';
        			    element+='<td class="mb"><a>'+ data.airline +" "+ data.flight_number +'</a></td>';
        			    element+='<td class="time"><a>'+ data.outbound_date.getHours() + ":" + data.outbound_date.getMinutes() + " - "+ data.inbound_date.getHours() + ":" + data.inbound_date.getMinutes() +'</a></td>';
        			    element+='<td class="price"><a>'+data.price.toLocaleString("vi-VN", {style: "currency", currency: "VND", minimumFractionDigits: 0})+'</a></td>';
        			    element+='<td class="ct-list" onclick="viewDetailsIb(this)"><a>Chi tiết</a></td>';
        			    element+='</tr>';
        			    
        				element+='<tr class="seclect-three" style="display:none"  id="'+6+'">'
        				element+='<td colspan="6" style="border:none">'
        					element+='<table><tr>'
        	                element+='<td style="text-align:center" width="200px" colspan="2">'
        	                	
        	                    element+='<p>Từ <b>'+data.departure+'</b></p>'
        	     
        	                    element+='<p><b>'+ data.outbound_date.getHours() + ":" + data.outbound_date.getMinutes() +'</b>, '+data.outbound_date.getDate()+'/'+(data.outbound_date.getMonth()+1)+'/'+data.outbound_date.getFullYear() +'</p>'
        	                element+='</td>'
        	                element+='<td style="text-align:center" width="200px" colspan="2">'
        	                    element+='<p>Tới <b>'+data.arrival+'</b></p>'

        	                    element+='<p><b>'+ data.inbound_date.getHours() + ":" + data.inbound_date.getMinutes() +'</b>, '+data.inbound_date.getDate()+'/'+(data.inbound_date.getMonth()+1)+'/'+data.inbound_date.getFullYear() +' </p>'
        	                element+='</td>'
        	                element+='<td width="200px">'
        	                    element+='<table>'
        		                    element+='<tr>'
        		                            element+='<td><img src="/images/sm'+data.airline+'.gif"/></td>'
        		                            element+='<td>'
        		                          	  element+='<br>(<b>'+data.airline+' '+data.flight_number+'</b>)<br>'
        		                          	  element+='<label> Loại vé :<b>Hạng phổ thông</b></label>'
        		                          	element+='</td>'
        		                    element+='</tr>'                  	 
        		                element+='</table>'
        		             element+='</tr></table>'
        	            		 element+='<table class="price-break">'
        	                        	element+='<tr>'
        	                         element+='<td><span>Loại hành khách</span></td>'
        	                         element+='<td><span>Số lượng vé</span></td>'
        	                         element+='<td><span>Giá mỗi vé</span></td>'
        	                         element+='<td><span>Tổng</span></td>'
        	                            element+='</tr>'
        		                           
        		                       		element+='<tr>'
        		                                element+='<td><a>Người lớn</a></td>'
        		                                element+='<td><a>1</a></td>'
        		                                element+='<td><a>'+data.price.toLocaleString("vi-VN", {style: "currency", currency: "VND", minimumFractionDigits: 0})+'</a></td>'
        		                                element+='<td><a>'+data.price.toLocaleString("vi-VN", {style: "currency", currency: "VND", minimumFractionDigits: 0})+'</a></td>'
        			                       element+='</tr>'
        								     element+='<tr>'
        									 element+='<td></td>'
        									 element+='<td></td>'
        	                          		 element+='<td><a>Tổng</a></td>'
        	                          		 //element+='<td><a href>'+numberOfTicket+'</a></td>'
        	                           		 //element+='<td><a href>'+(basePrice*count).format()+' '+currencyCode+'</a></td>'
        	                           		 element+='<td><a>'+data.price.toLocaleString("vi-VN", {style: "currency", currency: "VND", minimumFractionDigits: 0})+'</a></td>'
        	                      			 element+='</tr>'   
        	        				 element+='</table>'
        		      element+='</td>'
                  element+='</tr>'
                	  	element+='<input type="hidden" name="_fareType" value="roundtrip"/>';
        			    element+='<input type="hidden" name="iday" value="'+ data.inbound_date.getDate() +'"/>';
        			    element+='<input type="hidden" name="imonth" value="'+ (data.inbound_date.getMonth() + 1) +'"/>';
        			    element+='<input type="hidden" name="priceInbound" value="'+ data.priceNumber +'"/>';
        			    element+='<input type="hidden" name="airlineCodeInbound" value="'+ data.airline +'"/>';
        			    element+='<input type="hidden" name="iDepartureCode" value="'+ data.departure +'"/>';
        			    element+='<input type="hidden" name="iArrivalCode" value="'+ data.arrival +'"/>';
        			    element+='<input type="hidden" name="iOutBoundDate" value="'+ data.outbound_date +'"/>';
        			    element+='<input type="hidden" name="iInBoundDate" value="'+ data.inbound_date +'"/>';
        			    element+='<input type="hidden" name="iFlightNumber" value="'+ data.flight_number +'"/>';
        			   
        			    if(cheapFareBookingRoundTrip == "" || cheapFareBookingRoundTrip == null){
        			    	$(submitForm).append(element);
        			    } else {
        			    	$(select_oneNode).replaceWith("");
        			    	$(cheapFareBookingRoundTripObj).replaceWith(element);
        			    }
//        			    $("#cheapFareBookingOneWay").append(element);
        	        },
        	        
        	        
        	        events: myEventsArrival
        	    });
    		}
    	} else if($(this).val() == "oneway"){
			$('#fullcalendarRoundTrip').html("");
    		$('#cheapFareBookingOneWay').html("");
		}
    });
	// Setup FullCalendar
    var myEventsDeparture = [];
    $.ajax({
		async : false,
		type : "GET",
		contentType : "application/json; charset=utf-8",
		url : "/cheapFlight/listCheapFlightDeparture",
		dataType : "json",
		success : function(jsonData) {
			for (var i=0; i<jsonData.length; i++) {
				arrival = jsonData[i].arrival;
				departure = jsonData[i].departure;
				inbound_date = new Date(jsonData[i].inbound_date);
				outbound_date = new Date(jsonData[i].outbound_date);
				price = setPriceOnCalendar(jsonData[i].price);
				airline = jsonData[i].airline;
				flight_number = jsonData[i].flight_number;
				myEventsDeparture.push( {
				    title : price,
				    start : outbound_date,
				    departure: departure,
				    arrival: arrival,
				    inbound_date: inbound_date,
				    outbound_date: outbound_date,
				    airline: airline,
				    flight_number: flight_number,
				    price: jsonData[i].price.toLocaleString("vi-VN", {style: "currency", currency: "VND", minimumFractionDigits: 0}),
				    priceNumber: jsonData[i].price
			  } );  
			}
		}
	});

    $('#fullcalendarOneway').fullCalendar({
        editable: false,
        height: 300,
        header: {
            left: '',
            center: 'prev, title next',
            right: ''
        },
        eventMouseover: function (data, event, view) {
            

        	tooltip = '<div class="tooltiptopicevent" style="border:2px solid;border-color:#E02F2F;width:auto;height:auto;background:white;position:absolute;z-index:10001; line-height: 140%;-moz-border-radius-topleft:10px;-webkit-border-top-left-radius:10px;-moz-border-radius-topright:10px;-webkit-border-top-right-radius:10px;-moz-border-radius-bottomleft:10px;-webkit-border-bottom-left-radius:10px;-moz-border-radius-bottomright:10px;-webkit-border-bottom-right-radius:10px;">'+
        	'<div style="-moz-border-radius-topleft:10px;-webkit-border-top-left-radius:10px;-moz-border-radius-topright:10px;-webkit-border-top-right-radius:10px;text-align:center;font-size:15px;color:white;background:#E02F2F;width:100%:;height:40px;padding-top:10px"><strong>Nhấp chuột để xem thông tin chi tiết</strong></div><div style="padding:10px 10px 10px 10px;"><table width="400px"><tr><td style="margin-right:10px">'+ '<img src="/images/sm'+data.airline+'.gif"/></td><td style="border-right:1px solid gray;"><span style="font-weight: bold;">' + data.departure + '</span></br>' + 'Ngày đi: ' + getDateMonth(data.start) +
        	'</td></tr></br><tr><td><hr style="border-color:gray;color:gray;background-color:gray;"/></td><td style="border-right:1px solid gray;"><hr style="border-color:gray;color:gray;background-color:gray;"/></td><td style="text-align:center;"><span style="font-size:20px;color:#E02F2F;"><strong>'+ data.price.toLocaleString("vi-VN", {style: "currency", currency: "VND", minimumFractionDigits: 0}) +'</strong></span></td></tr><tr><td style="margin-right:10px">'+ '<img src="/images/sm'+data.airline+'.gif"/></td><td style="border-right:1px solid gray;"><span style="font-weight: bold;">' + data.arrival + '</span></br>' + 'Ngày về: ' + getDateMonth(data.inbound_date) + '</td></tr></table></div></div>';

            $("body").append(tooltip);
            $(this).mouseover(function (e) {
                $(this).css('z-index', 10000);
                $('.tooltiptopicevent').fadeIn('500');
                $('.tooltiptopicevent').fadeTo('10', 1.9);
            }).mousemove(function (e) {
                $('.tooltiptopicevent').css('top', e.pageY + 10);
                $('.tooltiptopicevent').css('left', e.pageX + 20);
            });


        },
        eventMouseout: function (data, event, view) {
            $(this).css('z-index', 8);

            $('.tooltiptopicevent').remove();

        },
        
        eventClick: function (data, event, view) {
        	var element='<form name="submitCalendarFlightForm" method="post" action="/cheapFlight/flyInfoFromCalendar">';
        	element+='<input type="hidden" name="_fareType" value="oneway"/>';
        	element+='<input type="hidden" name="airlineCode" value="'+ data.airline +'"/>';
		    element+='<input type="hidden" name="priceOutbound" value="'+ data.priceNumber +'"/>';
		    element+='<input type="hidden" name="oday" value="'+ data.outbound_date.getDate() +'"/>';
		    element+='<input type="hidden" name="omonth" value="'+ (data.outbound_date.getMonth() + 1) +'"/>';
		    element+='<input type="hidden" name="oDepartureCode" value="'+ data.departure +'"/>';
		    element+='<input type="hidden" name="oArrivalCode" value="'+ data.arrival +'"/>';
		    element+='<input type="hidden" name="fareType" value="'+ fareType +'"/>';
		    element+='<input type="hidden" name="airlineCodeOutBound" value="'+ data.airline +'"/>';
		    element+='<input type="hidden" name="oFlightNumber" value="'+ data.flight_number +'"/>';
		    element+='<input type="hidden" name="oOutBoundDate" value="'+ data.outbound_date +'"/>';
		    element+='<input type="hidden" name="oInBoundDate" value="'+ data.inbound_date +'"/>';
        	element+='<table id="'+1+'" class="select" data-airline='+data.airline+' data-price='+data.price+' data-hour='+data.inbound_date+'>';
        	element+='<tr class="seclect-one" id="'+2+'">';
        	element+='<td width="60px"><span style="font-size:10px;font-weight:bold;">Chuyến đi:</span></td>';
		    element+='<td width="70px">'+'<img src="/images/sm' + data.airline + '.gif"/>'+'</td>';
		    element+='<td class="mb"><a>'+ data.airline +" "+ data.flight_number +'</a></td>';
		    element+='<td class="time"><a>'+ data.outbound_date.getHours() + ":" + data.outbound_date.getMinutes() + " - "+ data.inbound_date.getHours() + ":" + data.inbound_date.getMinutes() +'</a></td>';
		    element+='<td class="price"><a>'+data.price.toLocaleString("vi-VN", {style: "currency", currency: "VND", minimumFractionDigits: 0})+'</a></td>';
		    element+='<td class="ct-list" onclick="viewDetails(this);"><a>Chi tiết</a></td>';
		    element+='</tr>';
		    
		    ///////////////////////////////////////////////////////////////
		    //element+='<tr class="seclect-two" style="display:none"  id="'+4+'">'
			//element+='<td colspan="6" style="border:none">'
		    
				element+='<tr class="seclect-two" style="display:none"  id="'+5+'">'
				element+='<td colspan="6" style="border:none">'
					
					element+='<table><tr>'
	                element+='<td style="text-align:center" width="200px" colspan="2">'
	                	
	                    element+='<p>Từ <b>'+data.departure+'</b></p>'
	     
	                    element+='<p><b>'+ data.outbound_date.getHours() + ":" + data.outbound_date.getMinutes() +'</b>, '+data.outbound_date.getDate()+'/'+(data.outbound_date.getMonth()+1)+'/'+data.outbound_date.getFullYear() +'</p>'
	                element+='</td>'
	                element+='<td style="text-align:center" width="200px" colspan="2">'
	                    element+='<p>Tới <b>'+data.arrival+'</b></p>'

	                    element+='<p><b>'+ data.inbound_date.getHours() + ":" + data.inbound_date.getMinutes() +'</b>, '+data.inbound_date.getDate()+'/'+(data.inbound_date.getMonth()+1)+'/'+data.inbound_date.getFullYear() +' </p>'
	                element+='</td>'
	                element+='<td width="200px">'
	                    element+='<table>'
		                    element+='<tr>'
		                            element+='<td><img src="/images/sm'+data.airline+'.gif"/></td>'
		                            element+='<td>'
		                          	  element+='<br>(<b>'+data.airline+' '+data.flight_number+'</b>)<br>'
		                          	  element+='<label> Loại vé :<b>Hạng phổ thông</b></label>'
		                          	element+='</td>'
		                    element+='</tr>'                  	 
		                element+='</table>'
		             element+='</tr></table>'
	            		 element+='<table class="price-break">'
	                        	element+='<tr>'
	                         element+='<td><span>Loại hành khách</span></td>'
	                         element+='<td><span>Số lượng vé</span></td>'
	                         element+='<td><span>Giá mỗi vé</span></td>'
	                         element+='<td><span>Tổng</span></td>'
	                            element+='</tr>'
		                           
		                       		element+='<tr>'
		                       			element+='<td><a>Người lớn</a></td>'
    		                                element+='<td><a>1</a></td>'
    		                                element+='<td><a>'+data.price.toLocaleString("vi-VN", {style: "currency", currency: "VND", minimumFractionDigits: 0})+'</a></td>'
    		                                element+='<td><a>'+data.price.toLocaleString("vi-VN", {style: "currency", currency: "VND", minimumFractionDigits: 0})+'</a></td>'
			                       element+='</tr>'
								     element+='<tr>'
									 element+='<td></td>'
									 element+='<td></td>'
	                          		 element+='<td><a>Tổng</a></td>'
	                          		 //element+='<td><a href>'+numberOfTicket+'</a></td>'
	                           		 //element+='<td><a href>'+(basePrice*count).format()+' '+currencyCode+'</a></td>'
	                           		 element+='<td><a>'+data.price.toLocaleString("vi-VN", {style: "currency", currency: "VND", minimumFractionDigits: 0})+'</a></td>'
	                      			 element+='</tr>'   
	        				 element+='</table>'
		      element+='</td>'
          element+='</tr>'
			///////////////////////////////////////////////////////////////
		    element+='</table>';
				
		    element+='<input class="bottom" type="submit" onclick="redirectCheapFlight();" name="Next" value="Tiếp tục" style="display: block;">'
		    
		    element+='</form>';
        	var selectedFlight = '<img src="/images/sm' + data.airline + '.gif"/>';
        	$("#cheapFareBookingOneWay").html(element);
        },
        
        
        events: myEventsDeparture
    });
});

function viewDetails(el){
	$(el).toggleClass("active")
	$(el).closest('table').find('.seclect-two').toggle()
}
function viewDetailsIb(el){
	$(el).toggleClass("active")
	$(el).closest('table').find('.seclect-three').toggle()
}

function redirectCheapFlight(){
	window.location.href='/cheapFlight/flyInfoFromCalendar';
	document.forms["myForm"].reset();
}

function getDateMonth(dateInp){
	var date = dateInp.getDate();
	var month = dateInp.getMonth() + 1;
	var year = dateInp.getFullYear();
	var dayNum = dateInp.getDay();
	var dayName;
	if(dayNum == 0) {
		dayName = "Chủ Nhật";
	} else if (dayNum == 1) {
		dayName = "Thứ Hai";
	} else if (dayNum == 2) {
		dayName = "Thứ Ba";
	} else if (dayNum == 3) {
		dayName = "Thứ Tư";
	} else if (dayNum == 4) {
		dayName = "Thứ Năm";
	} else if (dayNum == 5) {
		dayName = "Thứ Sáu";
	} else if (dayNum == 6) {
		dayName = "Thứ Bảy";
	}
	return dayName + " " + date + "/" + month + "/" + year;
}

function setPriceOnCalendar(price){
	var priceString = price.toString();
	var newPrice = priceString.substring(0,1);
	var modulusPrice = priceString.substring(1,2);
	if(modulusPrice > 0){
		return newPrice + "." + modulusPrice + "tr"
	} else if(modulusPrice < 1){
		return newPrice + "tr"
	}
	
	
	
}