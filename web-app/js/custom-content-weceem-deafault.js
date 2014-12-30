jQuery(document).ready(function(){
	var tourDomain = "com.airgroup.domain.Tour"
	var contactDomain = "com.airgroup.domain.ContactUs"
	var domain = jQuery("input:hidden[name='type']").val()
	
	//Hide fields that are not exist in SRS
	jQuery("label[for='space']").css("display","none")
	jQuery("label[for='space']").parent().css("display","none")
	jQuery("label[for='status']").css("display","none")
	jQuery("label[for='status']").parent().css("display","none")
	jQuery("label[for='template']").css("display","none")
	jQuery("label[for='template']").parent().css("display","none")
	jQuery("input[name='space.id']").css("display","none")
	jQuery("input[name='space.id']").parent().css("display","none")
	jQuery("select[name='status.id']").css("display","none")
	jQuery("select[name='status.id']").parent().css("display","none")
	jQuery("select[name='template.id']").css("display","none")
	jQuery("select[name='template.id']").parent().css("display","none")
	jQuery("input[name='_action_preview']").css("display","none")
	jQuery("input[name='_action_saveContinue']").css("display","none")
	jQuery("input[name='_action_updateContinue']").css("display","none")
	
	
	//Validate the min size and max size of phone number input
	jQuery('#phoneNumber').attr('maxlength', '15')
	
	//Rename field from English to Vietnamese
	var name = "TÃªn tour*"
	var duration = "Thá»�i gian*"
	var startLocation = "Khá»Ÿi hÃ nh*"
	var price = "GiÃ¡*"
	var phoneNumber = "Sá»‘ Ä‘iá»‡n thoáº¡i*"
	var category = "Danh má»¥c*"
	var status = "Tráº¡ng thÃ¡i*"
	var content = "Ná»™i dung"
	if(domain == tourDomain){
		jQuery("label[for='aliasURI']").parent().css("display","none")
		jQuery("label[for='aliasURI']").css("display","none")
		jQuery("input[name='aliasURI']").css("display","none")
		jQuery("input[name='aliasURI']").parent().css("display","none")
		jQuery("input[name='aliasURI']").val('abc')
		
//		jQuery("#aliasURI").val("tour")
		//Prevent user from entering non-number into tips value text-box
		jQuery("#phoneNumber").keydown(function(event) {
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
	    })
		jQuery("body form[enctype='multipart/form-data'] label").each(function(){
			if(jQuery(this).prop('for') == 'name'){
				jQuery(this).html(name)
			}
			if(jQuery(this).prop('for') == 'duration'){
				jQuery(this).html(duration)
			}
			if(jQuery(this).prop('for') == 'startLocation'){
				jQuery(this).html(startLocation)
			}
			if(jQuery(this).prop('for') == 'price'){
				jQuery(this).html(price)
			}
			if(jQuery(this).prop('for') == 'phoneNumber'){
				jQuery(this).html(phoneNumber)
			}
			if(jQuery(this).prop('for') == 'category'){
				jQuery(this).html(category)
			}
			if(jQuery(this).prop('for') == 'status'){
				jQuery(this).html(status)
			}
			if(jQuery(this).prop('for') == 'content'){
				jQuery(this).html(content)
			}
		})
	}
	
	//Convert from English -> Vietnamese
	var asiaCategory = "ChÃ¢u Ã�"
	var americaCategory = "ChÃ¢u Má»¹"
	var domesticCateogory = "Ná»™i Ä�á»‹a"
	var europeCateogory = "ChÃ¢u Ã‚u"
	var southEastAsia = "Ä�Ã´ng Nam Ã�"
	jQuery("#category option").each(function(){
		var recentCategory = jQuery(this).val()
		if(recentCategory == "Asia"){
			jQuery(this).html(asiaCategory)
		} else if(recentCategory == "America"){
			jQuery(this).html(americaCategory)
		} else if(recentCategory == "Domestic"){
			jQuery(this).html(domesticCateogory)
		} else if(recentCategory == "Europe"){
			jQuery(this).html(europeCateogory)
		} else if(recentCategory == "South East Asia"){
			jQuery(this).html(southEastAsia)
		}
	})
	jQuery("#tourStatus option").each(function(){
		var status = jQuery(this).val()
		if(status == 1){
			jQuery(this).html("Active")
		} else if(status == 0){
			jQuery(this).html("Inactive")
		}
	})
	
	//Remove all default style
	jQuery("body h1").addClass('custom-weceem-h1')
	jQuery("body form[enctype='multipart/form-data']").closest('div').removeAttr('class').addClass('body')
	jQuery("body form[enctype='multipart/form-data'] div").each(function(){
		jQuery(this).removeAttr('class')
		if(jQuery(this).css('display')!='none' && jQuery(this).children('div').length !=0){
			jQuery(this).addClass('custom-weceem-input-wrapper')
		}
		if(jQuery(this).css('display')!='none' && jQuery(this).children('label').length !=0){
			jQuery(this).addClass('custom-weceem-label')
		}
		if(jQuery(this).css('display')!='none' && jQuery(this).children('input[type="text"]').length !=0){
			jQuery(this).addClass('custom-weceem-input')
		}
		if(jQuery(this).css('display')!='none' && jQuery(this).children('textarea').length !=0){
			jQuery(this).addClass('custom-weceem-textarea')
		}
	})
	
	//Remove default header and add custom header
	if(domain == tourDomain){
		jQuery('body h1').html('<a href="/tour/listBackEnd" class="menuButton">Quáº£n lÃ½ tour</a> > <a href="/tour/create" class="menuButton">ThÃªm má»›i tour</a>')
	}
})