jQuery(document).ready(function(jQuery){
	jQuery.ajax({
		type:"POST",
		url:"/orderEmployee/getQuantityOfRecallAndTimeLimit",
		success: function(msg){
			if(msg.numbersOfTimeLimit == null || msg.numbersOfTimeLimit == 0){
				jQuery("#numberOfTimeLimit").html(0);
			} else {
				jQuery("#numberOfTimeLimit").html(msg.numbersOfTimeLimit);
				var userTimeLimit = jQuery("body").find("#user-timelimit a")
				blinkColor(userTimeLimit)
				blinkColor("#numberOfTimeLimit")
			}
			if(msg.numbersOfRecall == null || msg.numbersOfRecall == 0){
				jQuery("#numberOfRecall").html(0);
			} else {
				jQuery("#numberOfRecall").html(msg.numbersOfRecall);
				var userRecall = jQuery("body").find("#user-recall a")
				blinkColor(userRecall)
				blinkColor("#numberOfRecall")
			}
		}
	})
	
	//Check new recall and time limit each 1 minute
	setInterval(function(){
		jQuery.ajax({
			type:"POST",
			url:"/orderEmployee/getQuantityOfRecallAndTimeLimit",
			success: function(msg){
				if(msg.numbersOfTimeLimit == null || msg.numbersOfTimeLimit == 0){
					jQuery("#numberOfTimeLimit").html(0);
				} else {
					jQuery("#numberOfTimeLimit").html(msg.numbersOfTimeLimit);
					var userTimeLimit = jQuery("body").find("#user-timelimit a")
					blinkColor(userTimeLimit)
					blinkColor("#numberOfTimeLimit")
				}
				if(msg.numbersOfRecall == null || msg.numbersOfRecall == 0){
					jQuery("#numberOfRecall").html(0);
				} else {
					jQuery("#numberOfRecall").html(msg.numbersOfRecall);
					var userRecall = jQuery("body").find("#user-recall a")
					blinkColor(userRecall)
					blinkColor("#numberOfRecall")
				}
			}
		})
	}, 60000)
	
	var currentTime = new Date()
	jQuery("#recallAndTimeLimit tbody tr").each(function(){
		//Time limit variables
		var timeLimit = jQuery(this).find(".timeLimit").val()
		//Recall variables
		var notification = jQuery(this).find(".notification").val()
		//Find the closet row to add notification color
		var currentRecallRow = jQuery(this).find(".re-call").closest("tr")
		var currentTimeLimitRow = jQuery(this).find(".timeLimit").closest("tr")
		
		var timeLimitOverDue = -1
		var recallOverDue = -1
		if(timeLimit != null && timeLimit != ""){
			timeLimitOverDue = compareDates(timeLimit)
		}
		if(notification != null && notification != ""){
			recallOverDue = compareDates(notification)
		}
		
		//Check time limit and recall
		if(timeLimitOverDue >= 0 || recallOverDue >= 0){
			currentTimeLimitRow.addClass("overDueRow")
			currentRecallRow.addClass("overDueRow")
		} else if (timeLimitOverDue < 0 && recallOverDue < 0){
			currentTimeLimitRow.removeClass("overDueRow")
			currentRecallRow.removeClass("overDueRow")
		}
	})
})

/**
 * 
 * Compare date, month and year with current date<br>
 * Split date: index[0] - year, index[1] - month, index[2] - day<br>
 * Split time: index[0] - hour, index[1] - minute
 * @param 
 * Date executeDate : the execution date which is used to compare with current date
 * 
 */
function compareDates(dateInput){
	//Get the current date, month, year
	var currentDate = new Date()
	var currentDay = currentDate.getDate()
	var currentMonth = currentDate.getMonth()+1			//getMonth() always start with 0 so we have to add 1 to get the actual current row
	var currentYear = currentDate.getFullYear()
	//Get the current hour, minute
	var currentHour = currentDate.getHours()
	var currentMinute = currentDate.getMinutes()

	//Define the last index of year-month-date
	var dateIndex = dateInput.indexOf(" ")
	//Define the last index of hour-minute
	var timeIndex = dateInput.lastIndexOf(":")
	//Get the year, month, date and hour, minute 
	var date = dateInput.substring(0, dateIndex)
	var time = dateInput.substring(dateIndex, timeIndex)
	//Split the date and time
	var splitDate = date.split("-")
	var splitTime = time.split(":")
	
	//Compare current time with execute time
	var cmpCurrentTime = new Date(currentYear, currentMonth, currentDay, currentHour, currentMinute)
	var cmpInputTime = new Date(splitDate[0], splitDate[1], splitDate[2], splitTime[0], splitTime[1])
	//Return true if time limit is overdue, otherwise return false
	var period = cmpCurrentTime - cmpInputTime
	return period
}

function blinkColor(element){
	setInterval(function(){
		jQuery(element).animate({ color:"red"}, 30).animate({ color:"green"},30).animate({ color:"blue"},30).animate({ color:"white"}, 30)
	},100)
}