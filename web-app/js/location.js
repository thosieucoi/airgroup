$(document).ready(function() {
    $.ajax({
        type: "GET",
        url: "/location/getAllLocation",
        success : function(response) {

            //$("div.toen > div.se-ticket > div.auto-segg > input#tabs").autocomplete({
        	$("div.ticketone > div.se-ticket > div.auto-segg > input#tabs").autocomplete({
                source: response,
                select: function( event, ui ) {
                	$("#gog").val(ui.item.label);
                	$(".ticketone").hide();
                	}
            });
        	
        	$("div.toen > div.se-ticket > div.auto-segg > input#tabs").autocomplete({
                source: response,
                select: function( event, ui ) {
                	$("#tog").val(ui.item.label);
                	$(".toen").hide();
                	}
            });
        }
    });

});