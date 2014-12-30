jQuery(document).ready(function(){
	var d = new Date();
	var currentDate = d.getDate();
	var currentMonth = d.getMonth()+1;
	//Get the sorted month list
	var sortedMonthList = sortMonthListByCurrentMonth(currentMonth);

	//Initiate dates
	for(i=1;i<32;i++){
		if(i<10){
			jQuery('#oday, #iday').append('<option value="0'+i+'">'+i+'</option>');
		} else if(i>9){
			jQuery('#oday, #iday').append('<option value="'+i+'">'+i+'</option>');
		}
	}
	
	//Initiate months
	for(i=1;i<sortedMonthList.length+1;i++){
		if(sortedMonthList[i-1]<10){
			jQuery('#omonth, #imonth').append('<option value="0'+sortedMonthList[i-1]+'">Tháng '+sortedMonthList[i-1]+'</option>');
		} else if(sortedMonthList[i-1]>9){
			jQuery('#omonth, #imonth').append('<option value="'+sortedMonthList[i-1]+'">Tháng '+sortedMonthList[i-1]+'</option>');
		}
	}
	
	//Number of infants 0->5
	for(i=0;i<11;i++){
		jQuery('#infants').append('<option value="'+i+'">'+i+'</option>');
	}
	//Number of kids 0->9
	for(i=0;i<16;i++){
		jQuery('#kids').append('<option value="'+i+'">'+i+'</option>');
	}
	//Number of adults 1->50
	for(i=1;i<51;i++){
		jQuery('#adults').append('<option value="'+i+'">'+i+'</option>');
	}
	
	//Display current date on select box
	if(currentDate<10){
		jQuery('#oday').val('0'+currentDate);
	} else if (currentDate>9) {
		jQuery('#oday').val(currentDate);
	}
	

	//Prevent user from entering non-number characters into phone number field
	jQuery("#customerPhoneNumber").keydown(function(event) {
        // Allow: backspace, delete, tab, escape, and enter
        if ( event.keyCode == 46 || event.keyCode == 8 || event.keyCode == 9 || event.keyCode == 27 || event.keyCode == 13 || 
             // Allow: Ctrl+A
            (event.keyCode == 65 && event.ctrlKey === true) || 
             // Allow: home, end, left, right
            (event.keyCode >= 35 && event.keyCode <= 39)) {
                 // let it happen, don't do anything
                 return;
        }
        else {
            // Ensure that it is a number and stop the key press
            if (event.shiftKey || (event.keyCode < 48 || event.keyCode > 57) && (event.keyCode < 96 || event.keyCode > 105 )) {
                event.preventDefault(); 
            }   
        }
    });
	
	
	//Get data from previous form
	var currentPage = window.location.href
	var currentUrl = currentPage.substring(0, currentPage.indexOf("?"))
	if(currentUrl != null && currentUrl != ""){
		var iday = jQuery("#hdnIday").val()
		var oday = jQuery("#hdnOday").val()
		var imonth = jQuery("#hdnImonth").val()
		var omonth = jQuery("#hdnOmonth").val()
		var numsOfAdults = jQuery("#hdnAdults").val()
		var numsOfKids = jQuery("#hdnKids").val()
		var numsOfInfants = jQuery("#hdnInfants").val()
		jQuery('#iday').val(iday)
		jQuery('#imonth').val(imonth)
		
		jQuery('#oday').val(oday)
		jQuery('#omonth').val(omonth)
		
		jQuery("#adults").val(numsOfAdults)
		jQuery("#kids").val(numsOfKids)
		jQuery("#infants").val(numsOfInfants)
	}
	
	//Call this function when submit information to find a flight
	jQuery('#submitForm').submit(function(){
		//Get current date, month and year
		var currentDay = new Date();
		var currentDate = currentDay.getDate();
		var currentMonth = currentDay.getMonth() + 1;
		var currentYear = currentDay.getFullYear();

		//Get time from selected value of select box
		var startSelectedDate = parseInt(jQuery('#oday').val());
		var desSelectedDate = parseInt(jQuery('#iday').val());
		var startSelectedMonth = parseInt(jQuery('#omonth').val());
		var desSelectedMonth = parseInt(jQuery('#imonth').val());
		
		//Get location on selected box
		var arriveLocation = jQuery("#gog").val();
	    var departureLocation = jQuery("#tog").val();

		//Check if current year is leap year
		var isLeapYear = currentYear%4;

		//Get number days in month
		var startDateNum = getDatesInMonth(startSelectedMonth, currentYear);
		var desDateNum = getDatesInMonth(desSelectedMonth, currentYear);

		//Error message
		var message;
		//Validation flag
		var isValid=1;

		//Check if startLocation is the same as departureLocation
		if(arriveLocation == departureLocation){
			message = "Điểm đến không được trùng với điểm xuất phát!";
			isValid = 0;
        }
		
		//Check if user choose Month = February
		if(startSelectedMonth == 2){
			if(isLeapYear==0){
				if(startSelectedDate>29){
					message='Tháng 2 năm nhuận chỉ có 29 ngày!';
					isValid = 0;
				}
			} else if(isLeapYear!=0){
				if(startSelectedDate>28){
					message='Tháng 2 chỉ có 28 ngày!';
					isValid = 0;
				}
			}
		}
		if(desSelectedMonth == 2){
			if(isLeapYear==0){
				if(desSelectedDate>29){
					message='Tháng 2 năm nhuận chỉ có 29 ngày!';
					isValid = 0;
				}
			} else if(isLeapYear!=0){
				if(desSelectedDate>28){
					message='Tháng 2 chỉ có 28 ngày!';
					isValid = 0;
				}
			}
		}
		
		//Check 30 or 31 days
		if(startDateNum == 30){
			if(startSelectedDate == 31){
				message='Tháng '+startSelectedMonth+' chỉ có 30 ngày!';
				isValid = 0;
			}
		}
		if(desDateNum == 30){
			if(desSelectedDate == 31){
				message='Tháng '+desSelectedMonth+' chỉ có 30 ngày!';
				isValid = 0;
			}
		}
		
		//Check if user choose day in the past
//		if(startSelectedMonth < currentMonth){
//			message = 'Ngày đi không được là ngày trong quá khứ!'
//			isValid = 0
//		} else if(startSelectedMonth == currentMonth && startSelectedDate < currentDate){
//			message = 'Ngày đi không được là ngày trong quá khứ!'
//			isValid = 0
//		}
		
		//Check if user choose month == current month
		if(startSelectedMonth == currentMonth){
			if(startSelectedDate < currentDate){
				message = 'Ngày đi không được là ngày trong quá khứ!'
				isValid = 0
			}
		}
		
		if(desSelectedMonth == currentMonth){
			if(desSelectedDate <= currentDate){
				message = 'Ngày về phải lớn hơn ngày hiện tại!'
				isValid = 0
			}
		}
		
		//Check if user choose day in future
		if(startSelectedMonth < currentMonth && desSelectedMonth < currentMonth && desSelectedMonth > 0 && desSelectedDate > 0){
			if(desSelectedMonth < startSelectedMonth){
				message = 'Ngày về phải lớn hơn ngày đến!'
				isValid = 0
			} else if(desSelectedMonth == startSelectedMonth && desSelectedDate <= startSelectedDate){
				message = 'Ngày về phải lớn hơn ngày đến!'
				isValid = 0
			}
		}
		
		//Check if user choose day in the past of the current month
		if(startSelectedMonth == desSelectedMonth){
			if (desSelectedDate <= startSelectedDate) {
				message = 'Ngày về phải lớn hơn ngày đến!'
				isValid = 0
			}
		}
		
		//Check if customer's phone number is shorter than 8 characters
		var customerPhoneNumber = jQuery('#customerPhoneNumber').val()
		if(customerPhoneNumber != null && customerPhoneNumber != ""){
			if(customerPhoneNumber.length < 8){
				message = 'Độ dài số điện thoại ít nhất phải là 8 ký tự!'
				isValid = 0
			}
		}
		
		//Show error message if date is invalid
		if(isValid==1){
			return true;
		}
		
		jQuery('#validateMessage').html(message);
		return false;
	});
});

function getDatesInMonth(month, year){
	return new Date(year, month, 0).getDate();
}

function sortMonthListByCurrentMonth(currentMonth){
	//Initiate 12 month in year
	var monthList = [1,2,3,4,5,6,7,8,9,10,11,12];
	//Month list : slice from current month -> month 12
	var subMonthList1 = [];
	//Month list : slice from month 1 -> current month
	var subMonthList2 = [];
	//List of sorted months
	var sortedMonthList = [];
	//Slice the month list
	for(i=0;i<monthList.length;i++){
		if(monthList[i]==currentMonth){
			subMonthList1 = monthList.slice(i, monthList.length);
			subMonthList2 = monthList.slice(0, i);
			sortedMonthList = subMonthList1.concat(subMonthList2);
		}
	}
	return sortedMonthList;
}