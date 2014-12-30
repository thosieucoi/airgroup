jQuery(document).ready(function(){
	//Check all tour if the check box head is checked
	jQuery("#chkHead").change(function(){
		if(jQuery(this).is(":checked")){
			jQuery(".tourId").prop("checked",true)
		} else {
			jQuery(".tourId").prop("checked",false)
		}
	})
	var cbHeader = jQuery('#chkHead');
	var cbRowItem = jQuery(".tourId");
	cbRowItem.click(function(){
	   cbHeader.prop("checked",cbRowItem.length == jQuery(".tourId:checked").length ? 'checked' : '');
	});
});

function deleteAll(){
	var isChecked = jQuery("input[type='checkbox']").is(':checked');
	//alert(isChecked);
	if (!isChecked) {
		alert('Bạn phải chọn ít nhất 1 bản ghi để xóa!');
	} else {
		var checkedTours = [];
        jQuery(".tourId:checked").each(function() {
			checkedTours.push(jQuery(this).val());
        });
        var confirmStatus = confirm('Bạn có muốn xóa những Tour [ '+checkedTours+' ] này?');
        if (confirmStatus) {
        	jQuery("#multiDeleteForm").submit();
        }
	}
}