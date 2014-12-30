	jQuery(document).ready(function(){
		jQuery("#date").datepicker({
			changeYear: true,
			changeMonth: true,
			buttonImage: "../images/calendar-icon.png",
			buttonImageOnly: true,
			showOn:'both',
			dateFormat:'dd/mm/yy',
			defaultDate:new Date(),
			onSelect:function(){
				jQuery.ajax({
				}).done(function(repsonse){	
				})
			}
		})
		jQuery("#fromDate").datepicker({
			changeYear: true,
			changeMonth: true,
			buttonImage: "../images/calendar-icon.png",
			buttonImageOnly: true,
			showOn:'both',
			dateFormat:'dd/mm/yy',
			defaultDate:new Date(),
			onSelect:function(){
			//Validate the date
			var date = jQuery("#date").val()
			var fromDate = jQuery("#fromDate").val()
			var dateSplit = fromDate.split("/")
			var fromDateSplit = fromDate.split("/")
			var fromDateFormatted = new Date(dateSplit[0], dateSplit[1], dateSplit[2])
//				jQuery("#errorMessage").html("").removeClass("notification")
//				//Pass the start date and end date to search
//				jQuery.ajax({
//					type:"POST",
//					dataType:"html",
//					url:"/moneycode/create",
//					data:{'date' : jQuery("#date").val()}
//				}).done(function(repsonse){
//					var jQueryresult = jQuery(repsonse).find("#tblIncome tbody")
//					jQuery("#tblIncome tbody").replaceWith(jQueryresult)
//				})
				
//				if(fromDateFormatted > endDateFormatted){
//					isValid = 0
//					jQuery("#errorMessage").html(errorMsg).addClass("notification")
//				} else {
//					isValid = 1
//				}
//				if(isValid == 1){
//			if(fromDateFormatted!=null){
					jQuery("#errorMessage").html("").removeClass("notification")
					//Pass the start date and end date to search
					jQuery.ajax({
						type:"POST",
						dataType:"html",
						url:"/moneycode/list",
						data:{'fromDate' : jQuery("#fromDate").val()}
					}).done(function(repsonse){
						var jQueryresult = jQuery(repsonse).find("#tblMoneyCode tbody")
						jQuery("#tblMoneyCode tbody").replaceWith(jQueryresult)
					})
//				}
			}
		})
		var currentTime= new Date()
		var currentDate = currentTime.getDate()
		var currentMonth = currentTime.getMonth()+1
		var currentYear = currentTime.getFullYear()
		var firstDateOfMonth = currentYear+"/"+currentMonth+"/"+"1"
		var currentDateOfMonth = currentYear+"/"+currentMonth+"/"+currentDate
		jQuery("#date").datepicker("setDate", currentDateOfMonth)
		jQuery("#fromDate").datepicker("setDate", null)
		
	})