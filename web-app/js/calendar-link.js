jQuery(document).ready(function(){
	jQuery.ajax({
		type:"POST",
		url:"/flightPath/listFlightPath",
		dataType:"json",
		success: function(result){
			var splFlightPath;
			var flightPathType;
			var departureCode;
			var destinationCode;
			var destinationName;
			var price;
			for(var i=0; i<result.length; i++){
				// Only append link if result has both domesticPath and internationalPath (string length == 9)
				if(result[i].length > 9){
					splFlightPath = result[i].split(";");
					flightPathType = splFlightPath[0];
					departureCode = splFlightPath[1];
					destinationCode = splFlightPath[2];
					destinationName = splFlightPath[3];
					price = splFlightPath[4];
					if(destinationName != null && destinationName != "" && price != null && price > 0){
						if(flightPathType == 0){
							jQuery("#ve-may-bay-gia-re-noi-dia").after('<li>'
																	      +'<span>'
																	      + 	'<a onclick="redirectToCalendar('+"'"+departureCode+"'"+','+"'"+destinationCode+"'"+');" href="javascript:void(0);" departure="'+ departureCode +'" destination="' + destinationCode + '">' + destinationName + '</a>'
																	      +'</span>'
																	      +'<span style="float:right;">' + price + '<small>đ</small></span>'
																	      +'<div class="clearfix"></div>'
																	  +'</li>')
						} else if(flightPathType == 1){
							jQuery("#ve-may-bay-gia-re-quoc-te").after('<li>'
																	      +'<span>'
																	      + 	'<a onclick="redirectToCalendar('+"'"+departureCode+"'"+','+"'"+destinationCode+"'"+');" href="javascript:void(0);" departure="'+ departureCode +'" destination="' + destinationCode + '">' + destinationName + '</a>'
																	      +'</span>'
																	      +'<span style="float:right;">' + price + '<small>đ</small></span>'
																	      +'<div class="clearfix"></div>'
																	  +'</li>')
						}
					}
				}
			}					  
		},
		error: function(){
		}
	});
});

function redirectToCalendar(a,b){
	window.location.replace("/cheapFlight/domesticCalendar?departureCode="+a+"&arrivalCode="+b+"");
}