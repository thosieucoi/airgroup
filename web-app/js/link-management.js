jQuery(document).ready(function(){
	//Check all link if the check box head is checked
	jQuery("#chkHeadLink").change(function(){
		if(jQuery(this).is(":checked")){
			jQuery(".linkId").prop("checked",true)
		} else {
			jQuery(".linkId").prop("checked",false)
		}
	})
	var cbHeader = jQuery('#chkHeadLink');
	var cbRowItem = jQuery(".linkId");
	cbRowItem.click(function(){
	   cbHeader.prop("checked",cbRowItem.length == jQuery(".linkId:chkHeadLink").length ? 'checked' : '');
	});
});

function deleteAllLink(){
	var isChecked = jQuery("input[type='checkbox']").is(':checked');
	//alert(isChecked);
	if (!isChecked) {
		alert('Bạn phải chọn ít nhất 1 bản ghi để xóa!');
	} else {
		var checkedLink = [];
        jQuery(".linkId:checked").each(function() {
        	checkedLink.push(jQuery(this).val());
        });
        var confirmStatus = confirm('Bạn có muốn xóa những đường bay [ '+checkedLink+' ] này?');
        if (confirmStatus) {
        	jQuery("#multiDeleteLinkForm").submit();
        }
	}
}