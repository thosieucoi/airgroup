jQuery(document).ready(function(e){
	
	//Prevent user from entering non-number into tips value text-box
	jQuery("#domesticBookedTips, #domesticProcessedTips, #internationalBookedTips, #internationalProcessedTips").keydown(function(event) {
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
	
	var errorMsg = "Ngày đến phải lớn hơn ngày bắt đầu!"
	var isValid = 1
	//Create calendar
	jQuery("#fromDate, #toDate").datepicker({
		changeYear: true,
		changeMonth: true,
		buttonImage: "../images/calendar-icon.png",
		buttonImageOnly: true,
		showOn:'both',
		dateFormat:'dd/mm/yy',
		defaultDate:new Date(),
		onSelect:function(){
			//Validate the date
			var fromDate = jQuery("#fromDate").val()
			var endDate = jQuery("#toDate").val()
			var fromDateSplit = fromDate.split("/")
			var endDateSplit = endDate.split("/")
			var fromDateFormatted = new Date(fromDateSplit[2], fromDateSplit[1], fromDateSplit[0])
			var endDateFormatted = new Date(endDateSplit[2], endDateSplit[1], endDateSplit[0])
			if(fromDateFormatted > endDateFormatted){
				isValid = 0
				jQuery("#errorMessage").html(errorMsg).addClass("notification")
			} else {
				isValid = 1
			}
			if(isValid == 1){
				jQuery("#errorMessage").html("").removeClass("notification")
				//Pass the start date and end date to search
				jQuery.ajax({
					type:"POST",
					dataType:"html",
					url:"/tips/list",
					data:{'startDate' : jQuery("#fromDate").val(), 'endDate' : jQuery("#toDate").val()}
				}).done(function(repsonse){
					var jQueryresult = jQuery(repsonse).find("#tips-list tbody")
					jQuery("#tips-list tbody").replaceWith(jQueryresult)
					calculateTotalTips(repsonse)
				})
			}
		}
	})
	//Set default date
	var currentTime= new Date()
	var currentDate = currentTime.getDate()
	var currentMonth = currentTime.getMonth()+1
	var currentYear = currentTime.getFullYear()
	var firstDateOfMonth = currentYear+"/"+currentMonth+"/"+"1"
	var currentDateOfMonth = currentYear+"/"+currentMonth+"/"+currentDate
	jQuery("#fromDate").datepicker("setDate", firstDateOfMonth)
	jQuery("#toDate").datepicker("setDate", currentDateOfMonth)

	//Auto calculate the tips if there are tips already
	calculateTotalTips(e)
	
	
	jQuery('body').mouseup(function(e){
		if(!(jQuery(e.target).is('#domesticBookedTips')) && !(jQuery(e.target).is('#domesticProcessedTips')) && !(jQuery(e.target).is('#internationalBookedTips')) && !(jQuery(e.target).is('#internationalProcessedTips'))){
			calculateTotalTips(e)
		}
	})
	
	jQuery('#domesticBookedTips, #domesticProcessedTips, #internationalBookedTips, #internationalProcessedTips').keyup(function(e){
		if(e.keyCode == 13){
			calculateTotalTips(e)
		}
	})
})

function calculateTotalTips(e){
	var jQuerydomesticBookedTips = jQuery('#domesticBookedTips')
	   var jQuerydomesticProcessedTips = jQuery('#domesticProcessedTips')
	   var jQueryinternationalBookedTips = jQuery('#internationalBookedTips')
	   var jQueryinternationalProcessedTips = jQuery('#internationalProcessedTips')
	   //Get the tips value
	   var tipsOfDomesticBook
	   var tipsOfDomesticProcess
	   var tipsOfInternationalBook
	   var tipsOfInternationalProcess
	   if(jQuery('#domesticBookedTips').val() != null && jQuery('#domesticBookedTips').val() != ""){
		   tipsOfDomesticBook = jQuery('#domesticBookedTips').val()
	   } else {
		   tipsOfDomesticBook = 0
	   }
	   if(jQuery('#domesticProcessedTips').val() != null && jQuery('#domesticProcessedTips').val() != ""){
		   tipsOfDomesticProcess = jQuery('#domesticProcessedTips').val()
	   } else {
		   tipsOfDomesticProcess = 0
	   }
	   if(jQuery('#internationalBookedTips').val() != null && jQuery('#internationalBookedTips').val() != ""){
		   tipsOfInternationalBook = jQuery('#internationalBookedTips').val()
	   } else {
		   tipsOfInternationalBook = 0
	   }
	   if(jQuery('#internationalProcessedTips').val() != null && jQuery('#internationalProcessedTips').val() != ""){
		   tipsOfInternationalProcess = jQuery('#internationalProcessedTips').val()
	   } else {
		   tipsOfInternationalProcess = 0 
	   }
	   
	   //Set the total tips for each employee
	   jQuery("#tips-list tr").each(function(){
		   var bookDomesticOrder = jQuery(this).find("#bookDomesticOrder").html()
		   var processDomesticOrder = jQuery(this).find("#processDomesticOrder").html()
		   var totalDomesticTips = bookDomesticOrder*tipsOfDomesticBook + processDomesticOrder*tipsOfDomesticProcess
		   jQuery(this).find("#tipsDomesticOrder").html(totalDomesticTips)
		   
		   var bookInternationalOrder = jQuery(this).find("#bookInternationalOrder").html()
		   var processInternationalOrder = jQuery(this).find("#processInternationalOrder").html()
		   var totalInternationalTips = bookInternationalOrder*tipsOfInternationalBook + processInternationalOrder*tipsOfInternationalProcess
		   jQuery(this).find("#tipsInternationalOrder").html(totalInternationalTips)
		   
		   jQuery(this).find("#tipsTotal").html(totalDomesticTips + totalInternationalTips)
	   })
	   
	   //Send the value of tips for saving last tips
	   jQuery.ajax({
		   type:"POST",
		   url:"/tips/saveLastTips",
		   data:{"tipsOfDomesticBook" : tipsOfDomesticBook, "tipsOfDomesticProcess" : tipsOfDomesticProcess, "tipsOfInternationalBook" : tipsOfInternationalBook, "tipsOfInternationalProcess" : tipsOfInternationalProcess}
	   }).done(function(){
		   alert("Ajax done")
	   })
}