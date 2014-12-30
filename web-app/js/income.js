	jQuery(document).ready(function(){
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
						url:"/income/totalIncome",
						data:{'fromDate' : jQuery("#fromDate").val(), 'toDate' : jQuery("#toDate").val()}
					}).done(function(repsonse){
						var jQueryresult = jQuery(repsonse).find("#tblIncome tbody")
						jQuery("#tblIncome tbody").replaceWith(jQueryresult)
					})
				}
			}
		})
		var currentTime= new Date()
		var currentDate = currentTime.getDate()
		var currentMonth = currentTime.getMonth()+1
		var currentYear = currentTime.getFullYear()
		var firstDateOfMonth = currentYear+"/"+currentMonth+"/"+"1"
		var currentDateOfMonth = currentYear+"/"+currentMonth+"/"+currentDate
		jQuery("#fromDate").datepicker("setDate", firstDateOfMonth)
		jQuery("#toDate").datepicker("setDate", currentDateOfMonth)
	})