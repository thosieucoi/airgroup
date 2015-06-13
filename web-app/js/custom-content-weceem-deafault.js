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
	var title = "Tiêu đề*"
	var location = "Thành phố*"
	var category = "Danh mục*"
	var status = "Trạng thái*"
	var content = "Nội dung*"
	if(domain == tourDomain){
		jQuery("label[for='aliasURI']").parent().css("display","none")
		jQuery("label[for='aliasURI']").css("display","none")
		jQuery("input[name='aliasURI']").css("display","none")
		jQuery("input[name='aliasURI']").parent().css("display","none")
		jQuery("input[name='aliasURI']").val('abc')
		
		//Prevent user from entering non-number into tips value text-box
		jQuery("body form[enctype='multipart/form-data'] label").each(function(){
			if(jQuery(this).prop('for') == 'title'){
				jQuery(this).html(title)
			}
			if(jQuery(this).prop('for') == 'location'){
				jQuery(this).html(location)
			}
			if(jQuery(this).prop('for') == 'category'){
				jQuery(this).html(category)
			}
			if(jQuery(this).prop('for') == 'informationStatus'){
				jQuery(this).html(status)
			}
			if(jQuery(this).prop('for') == 'content'){
				jQuery(this).html(content)
			}
		})
	}
	
	jQuery("#informationStatus option").each(function(){
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
		jQuery('body h1').html('<a href="/tour/listBackEnd" class="menuButton">Quản lý tour</a> > <a href="/tour/create" class="menuButton">Thêm mới tour</a>')
	}
})