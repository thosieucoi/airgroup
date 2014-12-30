function submitForm(){
	var isValid = true;
	jQuery(".tblFlightPath tr").each(function(){
		var departure = jQuery(this).find("td").find("input[class='departure']").val();
		var destination = jQuery(this).find("td").find("input[class='destination']").val();
		var departureInp = jQuery(this).find("td").find("input[class='departure']");
		var destinationInp = jQuery(this).find("td").find("input[class='destination']");
		if(departure != "" || destination != ""){
			if(departure == "" || destination == ""){
				alert("Bạn phải điền cả điểm đến và điểm đi!");
				jQuery(departureInp).addClass("errorFlightPathInp");
				jQuery(destinationInp).addClass("errorFlightPathInp");
				isValid = false;
			}
		}
	});
	if(isValid == true){
		jQuery("form[name='addFlightPathForm']").submit();
	}
	
}

function cancel(){
	window.location.replace("/CMSFeedback/list");
}