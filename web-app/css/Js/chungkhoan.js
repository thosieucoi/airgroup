var selectCode = '';
var current_session = '';
var TTTT = 'OPEN';
/* Create float Header */
$(document).ready(function() {
        current_session = $('#current_session').val();
    showFixHeader();
    checkTimeReload();
    $('#txtCodeSearch').keypress(function(event) {
      if (event.which == '13') {
        searchCode();
       }
    });
    var search_size = getCookie('SELECTED_SIZE');
    if(search_size!=''){
        var codes = $('#cboSize').val(search_size);
        selectSize();
    }
});

function checkTimeReload(){
    setTimeout("checkTimeReload()", 30000);
    var d = new Date();
    var h = d.getHours();
    var m = d.getMinutes();
    
    if(h==8 && m==35 && TTTT=='CLOSE') window.location.reload();
}

function removeSort(){
    $('#sltSort').val('code-asc');
    document.frmSort.submit();
}
String.prototype.trim = function() {
	return this.replace(/^\s+|\s+$/g,"");
}
String.prototype.contains = function(it) { 
    return this.indexOf(it) != -1; 
};

function themClass(o, cls){
    if(o=='undefined' || o==null) return ;
    o.className += " "+cls;
}
function xoaClass(o){
    if(o=='undefined' || o==null) return ;
    o.className='';
}
/*
Cap nhat bang du lieu
*/
function getText(id){
    o = document.getElementById(id);
    if(o=='undefined' || o==null) return '';
    return o.innerHTML;
}
function setText(id, text){
    o = document.getElementById(id);
    if(o=='undefined' || o==null) return;
    o.innerHTML = text; 
}
function updateCell(text)
{
    if(String(text).trim()=='reload') {
        window.location.reload();
        return;
    } 
	var fBrw=(navigator.userAgent.indexOf('MSIE')!= -1 && navigator.userAgent.indexOf('Windows')!= -1);
    var t1 = String(text).split('#');
    current_session = String(t1[0]).trim();
    text = t1[1];
	var t = String(text).split('|');
    var alertNum=0;
	for(i=0; i<t.length; ++i) {
        var trOnScreen = false;
		var d = String(t[i]).split(';');
        var tr = document.getElementById('tr'+d[0]);
        if(tr=='undefined' || tr==null) continue ;
        
        if(!fBrw){
            var ceilingPrice = parseFloat(String(tr.childNodes[3].innerHTML).replace(",","."));
            var floorPrice = parseFloat(String(tr.childNodes[5].innerHTML).replace(",","."));
            var basicPrice = parseFloat(String(tr.childNodes[7].innerHTML).replace(",","."));
        }else{
            var ceilingPrice = parseFloat(String(tr.childNodes[1].innerHTML).replace(",","."));
            var floorPrice = parseFloat(String(tr.childNodes[2].innerHTML).replace(",","."));
            var basicPrice = parseFloat(String(tr.childNodes[3].innerHTML).replace(",","."));
        }
        var val = '';
		for(j=1; j<d.length-1; ++j) {
            if(!fBrw){
                var key = ((j+1)*2)-1;
            }else{
                key = j;
            }
            var bgColor = 'black';
			var o = tr.childNodes[key];
            if(o=='undefined' || o==null) continue ;
            var j_val = String(d[j]).trim();
            if(j_val != "") {
                var cell_val = String(tr.childNodes[key].innerHTML).trim();
                // var cell_val = tr.childNodes[key].innerHTML;
                if( j_val != cell_val){
                    if(!fBrw){
                        var key_congtru = 25;
                        var key_firstCol = 1;
                    }else{
                        var key_congtru = 12;
                        var key_firstCol = 0;
                    }
                    
                    if(j_val=='_'){
                        tr.childNodes[key].innerHTML = '';
                    }else{
                        tr.childNodes[key].innerHTML = j_val;
                    }
                    if(j>=10 && j<=13){
                            bgColor='#000080';
                    }
                    // if(trOnScreen){
                    // if(!fBrw) //IE
                        // _faceIn(o, d[j], bgColor);
                    // else if(trOnScreen)
                    _faceIn(o, d[j], bgColor);
                    
                    
                    if(!fBrw){
                        var ceil_node_index = 3;
                        var floor_node_index = 5;
                    }else{
                        var ceil_node_index = 1;
                        var floor_node_index = 2 ;
                    }
                    if(j==12 && d[j]!=''){
                        var val_text = d[10];
                        val  = parseFloat(val_text.replace(",","."));
                        if(val==ceilingPrice || val_text == tr.childNodes[ceil_node_index].innerHTML){
                            xoaClass(tr.childNodes[key_congtru]);
                            themClass(tr.childNodes[key_congtru], 'cellCeiling');
                        }
                        else if(val==floorPrice || val_text == tr.childNodes[floor_node_index].innerHTML){
                            xoaClass(tr.childNodes[key_congtru]);
                            themClass(tr.childNodes[key_congtru], 'cellFloor');
                        }
                        else if(String(j_val).contains('btup')){
                            xoaClass(tr.childNodes[key_congtru]);
                            themClass(tr.childNodes[key_congtru], 'upPrice');
                            tr.childNodes[key_firstCol].style.color = '#00CC00';
                            var c12_class = 'upPrice';
                        }else if(String(j_val).contains('btdown')){
                            xoaClass(tr.childNodes[key_congtru]);
                            themClass(tr.childNodes[key_congtru], 'downPrice');
                            tr.childNodes[key_firstCol].style.color = '#FF0000';
                        }else if(String(j_val).contains('btnochange')){
                            xoaClass(tr.childNodes[key_congtru]);
                            themClass(tr.childNodes[key_congtru], 'unchangePrice');
                            tr.childNodes[key_firstCol].style.color = '#FFFF00';
                        }
                    }
                    if(j==4 || j==6 || j==8 || j==14 || j==10 || j==16 || j==18 || j==13){   // set color for cell
                        var val_text = d[j];
                        val  = parseFloat(val_text.replace(",","."));
                        xoaClass(tr.childNodes[key]);
                        if(val==ceilingPrice || val_text == tr.childNodes[ceil_node_index].innerHTML){
                        // if(val_text == tr.childNodes[ceil_node_index].innerHTML){
                            // if(j==4) alert(d[0]);
                            themClass(tr.childNodes[key], 'cellCeiling');
                            if(j==10){
                                xoaClass(tr.childNodes[key_congtru]);
                                themClass(tr.childNodes[key_congtru], 'cellCeiling');
                            }
                        }else if(val==floorPrice || val_text == tr.childNodes[floor_node_index].innerHTML){
                            themClass(tr.childNodes[key], 'cellFloor');
                            if(j==10){
                                xoaClass(tr.childNodes[key_congtru]);
                                themClass(tr.childNodes[key_congtru], 'cellFloor');
                            }
                        }else if(val>basicPrice){
                            themClass(tr.childNodes[key], 'upPrice');
                        }else if(val<basicPrice){
                            themClass(tr.childNodes[key], 'downPrice');
                        }else{
                            themClass(tr.childNodes[key], 'unchangePrice');
                        }
                        
                    }
                    if(j==8 || j==14){   // set color for Qty cell
                        if(String(d[j]).trim()=='ATC' || String(d[j]).trim()=='ATO'){
                            xoaClass(tr.childNodes[key]);
                            themClass(tr.childNodes[key], 'whitePriceCode');
                        }
                    }
                    if(j==4 || j==6 || j==8 || j==10 || j==14 || j==16 || j==18){   // set color for Qty cell
                        clss = tr.childNodes[key].className;
                        if(!fBrw){
                            xoaClass(tr.childNodes[((j+2)*2)-1]);
                            themClass(tr.childNodes[((j+2)*2)-1], clss);
                        }else{
                            xoaClass(tr.childNodes[j+1]);
                            themClass(tr.childNodes[j+1], clss);
                        }
                    }
                    /* 
                    */
                }
			}
		}
	}
}

function updateMarketCell(text)
{
    // return;
    if(text=='') return;
    // var Row = $('#trMarketInfo');
	var t = String(text).split('#');
	for(i=0; i<t.length; ++i) {
		var d = String(t[i]).split(';');
        document.getElementById('tdDate').innerHTML = d[0];
        document.getElementById('tdTime').innerHTML = d[1];
        // document.getElementById('tdIndex').innerHTML = d[2];
        // document.getElementById('tdChange').innerHTML = d[3];
        document.getElementById('tdGainersLosers').innerHTML = d[4];
        document.getElementById('tdTotalMarketVolume').innerHTML = d[5];
        document.getElementById('tdTotalValue').innerHTML = d[6];
        document.getElementById('tdMarketStatus').innerHTML = d[7];
        if( d[2] != document.getElementById('tdIndex').innerHTML)  {
            document.getElementById('tdIndex').innerHTML = d[2];
            document.getElementById('tdChange').innerHTML = d[3];
            _faceIn(document.getElementById('tdIndex'), d[2], 'black');
            _faceIn(document.getElementById('tdChange'), d[3], 'black');
            $('#tdIndex').removeClass();
            $('#tdChange').removeClass();
            /* if(d[8]=='UP'){
                $('#tdIndex').addClass('headerInfoValueUp');
                $('#tdChange').addClass('headerInfoValueUp');
            }else if(d[8]=='DOWN'){
                $('#tdIndex').addClass('headerInfoValueDown');
                $('#tdChange').addClass('headerInfoValueDown');
            }else{
                $('#tdIndex').addClass('headerInfoValue');
                $('#tdChange').addClass('headerInfoValue');
            } */
            if(String(d[3]).contains('btup')){
                $('#tdIndex').addClass('headerInfoValueUp');
                $('#tdChange').addClass('headerInfoValueUp');
            }else if(String(d[3]).contains('btdown')){
                $('#tdIndex').addClass('headerInfoValueDown');
                $('#tdChange').addClass('headerInfoValueDown');
            }else{
                $('#tdIndex').addClass('headerInfoValue');
                $('#tdChange').addClass('headerInfoValue');
            }
        }
        TTTT = d[9]; //Tinh trang san chung khoan (OPEN/CLOSE);
        // TTTT = 'OPEN'; //Tinh trang san chung khoan (OPEN/CLOSE);
        // alert(TTTT);
        if(TTTT=='CLOSE'){
            // clearInterval(refreshIdMarket);
            // var refreshIdMarket = setInterval("updateMarketInfo('"+FLOOR_CODE+"')", 30000);
            // setTimeout("updateMarketInfo('"+FLOOR_CODE+"')", 30000);
        }else{
            // clearInterval(refreshIdMarket);
            setTimeout("updateMarketInfo('"+FLOOR_CODE+"')", 10000);
        }
	}
}

function _faceIn(o, v, bgColor)
{
	var g_thbu = 100;
	var g_thau = 1000;	
    if(bgColor=='') bgColor = 'black';
    if(o=='undefined' || o==null) return;
    o.style.backgroundColor='gray';
    setTimeout(function(){
                o.style.backgroundColor=bgColor
                }, 1000);
    return;
    // if (typeof o != "undefined") {
        setTimeout(function(){
            o.style.backgroundColor='gray';
            setTimeout(function(){
                o.style.backgroundColor=bgColor
                }, g_thau)
            }, g_thbu);
    // }
    // o.style.backgroundColor='red';

	
}
function _faceIn2(o, v, bgColor)
{
	var g_thbu = 100;
	var g_thau = 1000;	
    if(bgColor=='') bgColor = 'black';
    if(o=='undefined' || o==null) return;
    // if (typeof o != "undefined") {
        setTimeout(function(){
            o.style.backgroundColor='gray';
            setTimeout(function(){
                o.style.backgroundColor=bgColor
                }, g_thau)
            }, g_thbu);
    // }
    // o.style.backgroundColor='red';

	
}

function updateBoard(floor)
{
    if(current_session=='') return;
    if(TTTT=='CLOSE') {
        setTimeout("updateBoard('"+FLOOR_CODE+"')", 10000);
        return;
    }
    var listid = '';
    // listid = getTrInScreen();
	$.ajax({
		url: "/update_code.php?floor="+floor+"&sessionid="+current_session+"&code="+listid,
		cache: false,
		success: function(html){
            // alert(html);
            setTimeout("updateBoard('"+FLOOR_CODE+"')", 10000);
			updateCell(html);
		}
	});
}
function updateMarketInfo(floor)
{
    // return;
	$.ajax({
		url: "/market_info.php?floor="+floor,
		cache: false,
		success: function(html){
            // alert(html);
			updateMarketCell(html);
		}
	});
}
function getTrInScreen(){
    var listid = '';
    $('table#tableQuote').each(function() {
        var $table = $(this);
        $('tr', $table).filter(":onScreen").each(function(column) {
            listid = listid + ReplaceAll($(this).attr('id'), 'tr', '')+';';
        });
    });    
    return listid;
}
function UpdateTableHeaders() {
    $("div.divTableWithFloatingHeader").each(function() {
        var originalHeaderRow = $(".tableFloatingHeaderOriginal", this);
        var floatingHeaderRow = $(".tableFloatingHeader", this);
        var offset = $(this).offset();
        var scrollTop = $(window).scrollTop();
        if ((scrollTop > offset.top) && (scrollTop < offset.top + $(this).height())) {
            floatingHeaderRow.css("visibility", "visible");
            floatingHeaderRow.css("top", Math.min(scrollTop - offset.top, $(this).height() - floatingHeaderRow.height()) + "px");

            // Copy cell widths from original header
            $("th", floatingHeaderRow).each(function(index) {
                var cellWidth = $("th", originalHeaderRow).eq(index).css('width');
                $(this).css('width', cellWidth);
            });

            // Copy row width from whole table
            floatingHeaderRow.css("width", $(this).css("width"));
        }
        else {
            floatingHeaderRow.css("visibility", "hidden");
            floatingHeaderRow.css("top", "0px");
        }
    });
}
function ToggleTR(codeCK){
    if($('#chk'+codeCK).attr('checked')){
        var oRow = $('#tr'+codeCK);
        oRow.contents('td').css({'border':'none', 'background-color':'#000000'});
        oRow.contents('td:#cell_blue').css({'border':'none', 'background-color':'#000080'});
        jQuery('#tr'+codeCK).remove();
        jQuery('#tableQuote').prepend(oRow);
        // jQuery('#tr'+codeCK).css('background-color', '#ffffff');
        $('#chk'+codeCK).attr('checked', 'checked');
        // SET hover style
            
        jQuery('#tr'+codeCK).hover(function() {
            $(this).contents('td').css({'background-color':'#333333'});
        },
        function() {
            $(this).contents('td').css({'background-color':'#000000'});
            // $(this).contents('td:#cell_blue').css({'background-color': '#000080'});
            $(this).contents('td:eq(10)').css({'background-color': '#000080'});
            $(this).contents('td:eq(11)').css({'background-color': '#000080'});
            $(this).contents('td:eq(12)').css({'background-color': '#000080'});
            $(this).contents('td:eq(13)').css({'background-color': '#000080'});
        });
        /* Add to cookie */
        var selected_code = getCookie("SELECTED_CODE");
        selected_code = selected_code + ',' + codeCK;
        setCookie("SELECTED_CODE", selected_code, 365);
    }else{
        var oRow = $('#tr'+codeCK);
        var oRow_class = oRow.attr('class');
        // alert(oRow_class);
        // oRow.attr('class', 'quoteRow');
        oRow.contents('td').css({'border':'none', 'background-color':'#000000'});
        // oRow.contents('td:#cell_blue').css({'border':'none', 'background-color':'#000080'});
        oRow.contents('td:eq(10)').css({'background-color': '#000080'});
        oRow.contents('td:eq(11)').css({'background-color': '#000080'});
        oRow.contents('td:eq(12)').css({'background-color': '#000080'});
        oRow.contents('td:eq(13)').css({'background-color': '#000080'});
        jQuery('#tr'+codeCK).remove();
        
        /* Tìm mã ch?ng khoán d?ng sau */

        var tr_quote = $('tr.quoteRow');
        var removed = false;
        tr_quote.each(function(){
             var a_code = $(this).find('td > a');
             var near_code = a_code.text();
             if(codeCK <= near_code && !($('#chk'+near_code).attr('checked'))){
                $(this).before(oRow);
                removed = true;
                return false;
             }
        })
        if(!removed) jQuery('#tableQuote').append(oRow); /* N?u không d?ng tru?c cái nào thì move xu?ng cu?i b?ng */
        
        // }
        jQuery('#tr'+codeCK).css('background-color', '#ffffff');
        
        var selected_code = getCookie("SELECTED_CODE");
        selected_code = ReplaceAll(selected_code, codeCK, '');
        selected_code = ReplaceAll(selected_code, ',,', ',');
        setCookie("SELECTED_CODE", selected_code, 365);
    }
    $('#tr'+codeCK).removeClass('quoteRowSeparator').addClass('quoteRow');
    $('#tr'+codeCK).contents('td').css({'border-bottom':'0px solid #AAA'});
    $('tr.quoteRowSeparator').attr('class', 'quoteRow');
    createRowSeparator();
}
function createRowSeparator(){
    var lastRow = '';
    $('table#tableQuote').each(function() {
        var $table = $(this);
        var i = 0;
        $('tr', $table).each(function(column) {
            if ($(this).is('.quoteRow') || $(this).is('.quoteRowSeparator')) {  
                // alert($(this).attr('id'));
                var a_code = $(this).find('td > a');
                var near_code = a_code.text();
                if($('#chk'+near_code)){
                    if($('#chk'+near_code).attr('checked')){
                        lastRow = $(this).attr('id');
                        $('#'+lastRow).contents('td').css({'border-bottom':'0px solid #AAA'});
                        $('#'+lastRow).contents('td').css({'border':'none'});
                        $('#'+lastRow).attr('class', 'quoteRow');
                    }else{
                        // alert(lastRow);
                        $('#'+lastRow).contents('td').css({'border-bottom':'2px solid #AAA'});
                        $('#'+lastRow).attr('class', 'quoteRowSeparator');
                        if(i==0){
                            $(this).contents('td').css({'border-bottom':'0px solid #AAA'});
                            $(this).contents('td').css({'border':'none'});
                            $(this).attr('class', 'quoteRow');
                        }
                        return false;
                    }
                }
                // alert(i);
            }
            i++;
        });
    });
}
function selectSize(){
    var size = $("#cboSize").val();
    if(size=='0'){
        $("table.tableQuote").attr('width', '100%');
        $("table.tableQuote td").css({'font-size':'12px', 'font-weight':'bold'});
        $("table.tableQuote td a").css({'font-size':'12px', 'font-weight':'bold'});
        $("table.tableQuote th").css({'font-size':'12px', 'font-weight':'bold'});
    }else{
        if(size=='800'){
            $("table.tableQuote td").css({'font-size':'9px', 'font-weight':'none'});
            $("table.tableQuote td a").css({'font-size':'9px', 'font-weight':'none'});
            $("table.tableQuote th").css({'font-size':'9px', 'font-weight':'none'});
        }else if(size=='900'){
            $("table.tableQuote td").css({'font-size':'10px', 'font-weight':'none'});
            $("table.tableQuote td a").css({'font-size':'10px', 'font-weight':'none'});
            $("table.tableQuote th").css({'font-size':'10px', 'font-weight':'none'});
        }else if(size=='1000'){
            $("table.tableQuote td").css({'font-size':'11px', 'font-weight':'none'});
            $("table.tableQuote td a").css({'font-size':'11px', 'font-weight':'none'});
            $("table.tableQuote th").css({'font-size':'11px', 'font-weight':'none'});
        }else{
            $("table.tableQuote td").css({'font-size':'12px', 'font-weight':'bold'});
            $("table.tableQuote td a").css({'font-size':'12px', 'font-weight':'bold'});
            $("table.tableQuote th").css({'font-size':'12px', 'font-weight':'bold'});
        }
        $("table.tableQuote").attr('width', size+'px');
    }
    setCookie('SELECTED_SIZE', size, 365);
}
function sortby(sortby, type){
    document.frmSort.txtSortBy.value=sortby;
    document.frmSort.txtSortType.value=type;
    document.frmSort.submit();
}
function changeSearchText(){
    var codes = $('#txtCodeSearch').val();
    if(codes=='')
        searchCode();
}
function removeSearchCode(){
    document.frmSearch.txtCodeSearch.value='';
    document.frmSearch.noSearch.value='1';
    searchCode();
}
function searchCode(){
    var codes = $('#txtCodeSearch').val().toUpperCase();
    $('#txtCodeSearch').val(codes);
    // setCookie('SEARCH_CODE', codes, 365);
    document.frmSearch.submit();
    // return;
    // $('table#tableQuote').each(function() {
        // var $table = $(this);
        // $('tr', $table).each(function(column) {
            // if($(this).is('.quoteRow') || $(this).is('.quoteRowSeparator')){
                    // $(this).show();
            // }
        // });
    // });
    // if(codes==''){
        // return;
    // }
    
    // codes = ReplaceAll(codes, ' ', '');
    // var arr_code = new Array();
    // arr_code=codes.split(",");
    // $('table#tableQuote').each(function() {
        // var $table = $(this);
        // $('tr', $table).each(function(column) {
            /* alert($(this).attr('id')); */
            // if($(this).is('.quoteRow') || $(this).is('.quoteRowSeparator')){
                // var a_code = $(this).find('td > a');
                // var code = a_code.text();
                // if(jQuery.inArray(code, arr_code)==-1){
                    // $(this).hide();
                // }
            // }
        // });
    // });
    // updateBangGia();
}
function ReplaceAll(Source,stringToFind,stringToReplace){
  var temp = Source;
    var index = temp.indexOf(stringToFind);
        while(index != -1){
            temp = temp.replace(stringToFind,stringToReplace);
            index = temp.indexOf(stringToFind); 
        }
        return temp;
}
/* Update selected code to cookie */
function updateSelectCode(){ 
    var selected_code = '';
    var arr_selected_code = new Array();
    $('table#tableQuote').each(function() {
        var $table = $(this);
        $('tr', $table).each(function(column) {
            // alert($(this).attr('id'));
            if($(this).is('.quoteRow') || $(this).is('.quoteRowSeparator')){
                var a_code = $(this).find('td > a');
                var code = a_code.text();
                if($('#chk'+code)){
                    if($('#chk'+code).attr('checked')){
                        arr_selected_code[arr_selected_code.length] = code;
                    }
                }
            }
        });
    });
    selected_code = arr_selected_code.join();
    setCookie("SELECTED_CODE", selected_code, 365);
}
function updateBangGia(){
    /* Check chon checkbox, tao vach phan cach cho cac Code da chon tu cookie */
    var selected_code = getCookie("SELECTED_CODE");
    var codes = new Array();
    arr_codes = selected_code.split(',');
    
    $('table#tableQuote').each(function() {
        var $table = $(this);
        $('tr', $table).each(function(column) {
            // alert($(this).attr('id'));
            if($(this).is('.quoteRow') || $(this).is('.quoteRowSeparator')){
                var a_code = $(this).find('td > a');
                var code = a_code.text();
                var oRow = $(this);
                oRow.contents('td').css({'border':'none', 'background-color':'#000000'});
                oRow.contents('td:#cell_blue').css({'border':'none', 'background-color':'#000080'});
                if(jQuery.inArray(code, arr_codes)>-1){
                    $('#chk'+code).attr('checked', true);
                    oRow.remove();
                    jQuery('#tableQuote').prepend(oRow);
                }else{
                    $('#chk'+code).attr('checked', false);
                }
            }
        });
    });
    createRowSeparator();
}

function setCookie(c_name,value,expiredays)
{
    var exdate=new Date();
    exdate.setDate(exdate.getDate()+expiredays);
    document.cookie=c_name+ "=" +escape(value)+ ((expiredays==null) ? "" : ";expires="+exdate.toUTCString());
}
function getCookie(c_name)
{
if (document.cookie.length>0)
  {
  var c_start=document.cookie.indexOf(c_name + "=");
  if (c_start!=-1)
    {
    c_start=c_start + c_name.length+1;
    var c_end=document.cookie.indexOf(";",c_start);
    if (c_end==-1) c_end=document.cookie.length;
    return unescape(document.cookie.substring(c_start,c_end));
    }
  }
return "";
}
function showFixHeader2(){
    if (document.documentElement && document.documentElement.scrollTop){
        var docViewTop = document.documentElement.scrollTop;
    }else{
    var docViewTop = $(window).scrollTop();
    }
    // alert(docViewTop);
    tofs = $('#tableQuote').offset().top;
    // tofs = document.getElementById('tableQuote').offsetTop;
    // aler`
    // alert(tofs);
    if(docViewTop>=tofs && $('#tableFixedHeader').is(':hidden')){
        alert(docViewTop+' - '+tofs);
        $('#btnOffset').attr('value', 'show:'+docViewTop);
        $('#tableFixedHeader').show();
        var table_left = $('table#tableQuote').offset().left;
        $('#tableFixedHeader').css('left', table_left);
    }else if(docViewTop<tofs && !$('#tableFixedHeader').is(':hidden')){
        $('#btnOffset').attr('value', 'hidden:'+docViewTop);
        $('#tableFixedHeader').hide();
    }
}
function showFixHeader(){
    // return false;
/*     if (document.documentElement && document.documentElement.scrollTop){
        var docViewTop = document.documentElement.scrollTop;
    }else{
        var docViewTop = $(window).scrollTop();
    } */
	var fBrw=(navigator.userAgent.indexOf('MSIE')!= -1 && navigator.userAgent.indexOf('Windows')!= -1);
    if(fBrw) //IE
    {
        var docViewTop = document.documentElement.scrollTop;
        document.getElementById('tableFixedHeader').style.position='absolute';
        // alert(docViewTop);
        // tofs = $('#tableQuote').offset().top;
        // var tofs = document.getElementById('tableQuote').offsetTop;
        // alert(tofs);
        var tofs = 367;
        if(docViewTop>=tofs){
            // alert(docViewTop+' - '+tofs);
            // alert(docViewTop+' - '+tofs);
            // $('#btnOffset').attr('value', 'show:'+docViewTop);
            // ftlObj.style.display = '';			
            document.getElementById('tableFixedHeader').style.top= docViewTop+'px';
            // alert(document.getElementById('tableFixedHeader').style.top);
            $('#tableFixedHeader').show();
            // return false;
            // var table_left = $('table#tableQuote').offset().left;
            // $('#tableFixedHeader').css('left', table_left);
        }else if(docViewTop<tofs){
            $('#btnOffset').attr('value', 'hidden:'+docViewTop);
            $('#tableFixedHeader').hide();
        } 
    }else{
        var docViewTop = $(window).scrollTop();
        tofs = $('#tableQuote').offset().top;
        if(docViewTop>=tofs){
            $('#btnOffset').attr('value', 'show:'+docViewTop);
            $('#tableFixedHeader').show();
            var table_left = $('table#tableQuote').offset().left;
            $('#tableFixedHeader').css('left', table_left);
        }else if(docViewTop<tofs){
            $('#btnOffset').attr('value', 'hidden:'+docViewTop);
            $('#tableFixedHeader').hide();
        }
    }
    setTimeout('showFixHeader()', 500);
}
function setWith(){
    var row = $('tr#row1fixHeader');
    var t_d = $('th:eq(3)', row);
    // alert(t_d.get());
    alert(t_d.attr('width'));
    // alert(t_d.width('200'));
    // t_d.width('200');
    // t_d.css({'width': '200px', 'color': '#000000'});
}

;(function($) {
  $.expr[":"].onScreen = function(elem) {
    var $window = $(window)
    var scroll_top = $window.scrollTop()
    var scroll_bottom = scroll_top + $window.height()
    var top = $(elem).offset().top
    return top > scroll_top && top < scroll_bottom
  }
})(jQuery);

function isOnScreen(ele){
    if(ele=='undefined' || ele==null) return false ;
    // var $window = $(window)
    // var docViewTop = $window.scrollTop();
    var docViewTop = 550;
    // var top = offset_top(ele);
    var top = 500;
    // var scroll_bottom = docViewTop + $window.height();
    var scroll_bottom = 1100;
    // alert('top: '+top+' --- docViewTop: '+docViewTop+' ---- bottom: '+scroll_bottom);
    return top > docViewTop && top < scroll_bottom;
}

function offset_top(elem) {
        if(!elem) elem = this;
        // var x = elem.offsetLeft;
        var y = elem.offsetTop;
        while (elem = elem.offsetParent) {
                // x += elem.offsetLeft;
                y += elem.offsetTop;
        }
        return y ;
}
/* Banner.js */
function Banner(objName){
        this.obj = objName;
        this.aNodes = [];
        this.bNodes = [];
        this.currentBanner = 0;//Math.floor(Math.random()*3);
		this.intLoopCount = 0;
		this.intBannerFix = -1;
};

// ADD NEW BANNER
Banner.prototype.add = function(bannerType, bannerPath, bannerDuration, height, width, hyperlink) {
        this.aNodes[this.aNodes.length] = new Node(this.obj +"_"+ this.aNodes.length, bannerType, bannerPath, bannerDuration, height, width, hyperlink);
};
// Add2
Banner.prototype.add2 = function(bannerType, bannerPath, bannerDuration, height, width, hyperlink, position) {
        this.bNodes[this.bNodes.length] = new Node(this.obj +"_"+ this.bNodes.length, bannerType, bannerPath, bannerDuration, height, width, hyperlink, position);
};

// Node object
function Node(name, bannerType, bannerPath, bannerDuration, height, width, hyperlink) {
        this.name = name;
        this.bannerType = bannerType;
        this.bannerPath = bannerPath;
        this.bannerDuration = bannerDuration;
        this.height = height
        this.width = width;
        this.hyperlink= hyperlink;
};

function Node2(name, bannerType, bannerPath, bannerDuration, height, width, hyperlink, position) {
        this.name = name;
        this.bannerType = bannerType;
        this.bannerPath = bannerPath;
        this.bannerDuration = bannerDuration;
        this.height = height
        this.width = width;
        this.hyperlink= hyperlink;
        this.position= position;
};

// Outputs the banner to the page
Banner.prototype.toString = function() {
        var str = ""
        for (var iCtr=0; iCtr < this.aNodes.length; iCtr++){
				str = str + '<span name="'+this.aNodes[iCtr].name+'" '
                str = str + 'id="'+this.aNodes[iCtr].name+'" ';
                str = str + 'class="m_banner_hide" ';
                str = str + 'bgcolor="#FFFCDA" ';        // CHANGE BANNER COLOR HERE
                str = str + 'align="center" ';
                str = str + 'valign="top" >\n';
                if (this.aNodes[iCtr].hyperlink != ""){
                        str = str + '<a href="'+this.aNodes[iCtr].hyperlink+'" target="_blank">';
                }
                       
                if ( this.aNodes[iCtr].bannerType == "FLASH" ){
                        str = str + '<EMBED ';
                        str = str + 'src="'+this.aNodes[iCtr].bannerPath+'" '
                        str = str + 'quality=high '
//                        str = str + 'bgcolor=#FFFCDA '
                        str = str + 'WIDTH="'+this.aNodes[iCtr].width+'" '
                        str = str + 'HEIGHT="'+this.aNodes[iCtr].height+'" '
                        str = str + 'NAME="bnr_'+this.aNodes[iCtr].name+'" '
                        str = str + 'ALIGN="center"  wmode="transparent"  '
                        str = str + 'TYPE="application/x-shockwave-flash" '
                        str = str + 'PLUGINSPAGE="http://www.macromedia.com/go/getflashplayer">'
                        str = str + '</EMBED>'
                }else if ( this.aNodes[iCtr].bannerType == "IMAGE" ){
                        str = str + '<img src="'+this.aNodes[iCtr].bannerPath+'" ';
                        str = str + 'border="0" ';
                        str = str + 'height="'+this.aNodes[iCtr].height+'" ';
                        str = str + 'width="'+this.aNodes[iCtr].width+'">';
                }

                if (this.aNodes[iCtr].hyperlink != ""){
                        str = str + '</a>';
                }

                str += '</span>';
        }
        return str;
};

// START THE BANNER ROTATION
Banner.prototype.start = function(){
        this.changeBanner();
        var thisBannerObj = this.obj;
        // CURRENT BANNER IS ALREADY INCREMENTED IN cahngeBanner() FUNCTION
        setTimeout(thisBannerObj+".start()", this.aNodes[this.currentBanner].bannerDuration * 1000);
}

// CHANGE BANNER
Banner.prototype.changeBanner = function(){
		//
    var thisBanner;

	var prevBanner = -1;
	if (this.currentBanner < this.aNodes.length ){
		thisBanner = this.currentBanner;
		if (this.aNodes.length > 1){
			if ( thisBanner > 0 ){
				prevBanner = thisBanner - 1;
			}else{
				prevBanner = this.aNodes.length-1;
			}
		}
		if (this.currentBanner < this.aNodes.length - 1){
			this.currentBanner = this.currentBanner + 1;
		}else{
			this.currentBanner = 0;
		}
	}

	if (prevBanner >= 0){
		document.getElementById(this.aNodes[prevBanner].name).className = "m_banner_hide";
	}
	document.getElementById(this.aNodes[thisBanner].name).className = "m_banner_show";
}


// d_Banner
// Written by Dungpt
function d_Banner(objName){
        this.obj = objName;
        this.aNodes = [];
        this.bNodes = [];
        this.currentBanner = 0;
       
};
// ADD NEW BANNER
d_Banner.prototype.add = function(bannerType, bannerPath, height, width, hyperlink) {
		var bannerDuration = 0;
        this.aNodes[this.aNodes.length] = new Node(this.obj +"_"+ this.aNodes.length, bannerType, bannerPath, bannerDuration, height, width, hyperlink);
};
// add2
d_Banner.prototype.add2 = function(bannerType, bannerPath, height, width, hyperlink, position) {
		var bannerDuration = 0;
        this.bNodes[this.bNodes.length] = new Node2(this.obj +"_"+ this.bNodes.length, bannerType, bannerPath, bannerDuration, height, width, hyperlink, position);
};
// Outputs the banner to the page
d_Banner.prototype.toString = function() {
        var str = "";
		var BannerPostion = Math.floor(Math.random()*12321) % this.aNodes.length;
		var i = 1;
		for (var iCtr=BannerPostion; iCtr < this.aNodes.length; iCtr++){
                // iB for loop
				//str += "I: " + i + "<HR>";
				for(var iB=0; iB < this.bNodes.length; iB++){
					if(i == this.bNodes[iB].position){
						str = str + '<span name="'+this.bNodes[iB].name+'" '
						str = str + 'id="'+this.bNodes[iB].name+'" ';
						str = str + 'class="d_banner_show" ';
						str = str + 'bgcolor="#FFFCDA" ';        // CHANGE BANNER COLOR HERE
						str = str + 'align="center" ';
						str = str + 'valign="top" >\n';
						if (this.bNodes[iB].hyperlink != ""){
								str = str + '<a href="'+this.bNodes[iB].hyperlink+'" target="_blank">';
						}
							   
						if ( this.bNodes[iB].bannerType == "FLASH" ){
								str = str + '<EMBED ';
								str = str + 'src="'+this.bNodes[iB].bannerPath+'" '
								str = str + 'quality=high '
								str = str + 'WIDTH="'+this.bNodes[iB].width+'" '
								str = str + 'HEIGHT="'+this.bNodes[iB].height+'" '
								str = str + 'NAME="bnr_'+this.bNodes[iB].name+'" '
								str = str + 'ALIGN="center" wmode="transparent" '
								str = str + 'TYPE="application/x-shockwave-flash" '
								str = str + 'PLUGINSPAGE="http://www.macromedia.com/go/getflashplayer">'
								str = str + '</EMBED>'
						}else if ( this.bNodes[iB].bannerType == "IMAGE" ){
								str = str + '<img src="'+this.bNodes[iB].bannerPath+'" ';
								str = str + 'border="0" ';
								str = str + 'height="'+this.bNodes[iB].height+'" ';
								str = str + 'width="'+this.bNodes[iB].width+'">';
						}
						if( this.bNodes[iB].bannerType == "TEXT") {
							str = str + '<iframe width="'+this.bNodes[iB].width+'" height="'+this.bNodes[iB].height+'" src="'+this.bNodes[iB].bannerPath+'" marginwidth="0" marginheight="0" scrolling="no" frameborder="0"></iframe>'
						}
		
						if (this.bNodes[iB].hyperlink != ""){
								str = str + '</a>';
						}
		
						str += '</span>';
						// str = str + 'Thao Pro '+i;
						i++; continue;
					}
				}
				// End iB for loop
				str = str + '<span name="'+this.aNodes[iCtr].name+'" '
                str = str + 'id="'+this.aNodes[iCtr].name+'" ';
                str = str + 'class="d_banner_show" ';
                str = str + 'bgcolor="#FFFCDA" ';        // CHANGE BANNER COLOR HERE
                str = str + 'align="center" ';
                str = str + 'valign="top" >\n';
                if (this.aNodes[iCtr].hyperlink != ""){
                        str = str + '<a href="'+this.aNodes[iCtr].hyperlink+'" target="_blank">';
                }
                       
                if ( this.aNodes[iCtr].bannerType == "FLASH" ){
                        str = str + '<EMBED ';
                        str = str + 'src="'+this.aNodes[iCtr].bannerPath+'" '
                        str = str + 'quality=high '
//                        str = str + 'bgcolor=#FFFCDA '
                        str = str + 'WIDTH="'+this.aNodes[iCtr].width+'" '
                        str = str + 'HEIGHT="'+this.aNodes[iCtr].height+'" '
                        str = str + 'NAME="bnr_'+this.aNodes[iCtr].name+'" '
                        str = str + 'ALIGN="center" wmode="transparent" '
                        str = str + 'TYPE="application/x-shockwave-flash" '
                        str = str + 'PLUGINSPAGE="http://www.macromedia.com/go/getflashplayer">'
                        str = str + '</EMBED>'
                }else if ( this.aNodes[iCtr].bannerType == "IMAGE" ){
                        str = str + '<img src="'+this.aNodes[iCtr].bannerPath+'" ';
                        str = str + 'border="0" ';
                        str = str + 'height="'+this.aNodes[iCtr].height+'" ';
                        str = str + 'width="'+this.aNodes[iCtr].width+'">';
                }
				if( this.aNodes[iCtr].bannerType == "TEXT") {
					str = str + '<iframe width="'+this.aNodes[iCtr].width+'" height="'+this.aNodes[iCtr].height+'" src="'+this.aNodes[iCtr].bannerPath+'" marginwidth="0" marginheight="0" scrolling="no" frameborder="0"></iframe>'
				}

                if (this.aNodes[iCtr].hyperlink != ""){
                        str = str + '</a>';
                }

                str += '</span>';
				i++;
        }
		//BannerPostion = 0;
		//return str;
		//str += "<HR>a " + BannerPostion + "  a <HR>";;
		for (var iCtr=0; iCtr < BannerPostion; iCtr++){
                // iB for loop
				for(var iB=0; iB < this.bNodes.length; iB++){
					if(i == this.bNodes[iB].position){
						str = str + '<span name="'+this.bNodes[iB].name+'" '
						str = str + 'id="'+this.bNodes[iB].name+'" ';
						str = str + 'class="d_banner_show" ';
						str = str + 'bgcolor="#FFFCDA" ';        // CHANGE BANNER COLOR HERE
						str = str + 'align="center" ';
						str = str + 'valign="top" >\n';
						if (this.bNodes[iB].hyperlink != ""){
								str = str + '<a href="'+this.bNodes[iB].hyperlink+'" target="_blank">';
						}
							   
						if ( this.bNodes[iB].bannerType == "FLASH" ){
								str = str + '<EMBED ';
								str = str + 'src="'+this.bNodes[iB].bannerPath+'" '
								str = str + 'quality=high '
								str = str + 'WIDTH="'+this.bNodes[iB].width+'" '
								str = str + 'HEIGHT="'+this.bNodes[iB].height+'" '
								str = str + 'NAME="bnr_'+this.bNodes[iB].name+'" '
								str = str + 'ALIGN="center" wmode="transparent" '
								str = str + 'TYPE="application/x-shockwave-flash" '
								str = str + 'PLUGINSPAGE="http://www.macromedia.com/go/getflashplayer">'
								str = str + '</EMBED>'
						}else if ( this.bNodes[iB].bannerType == "IMAGE" ){
								str = str + '<img src="'+this.bNodes[iB].bannerPath+'" ';
								str = str + 'border="0" ';
								str = str + 'height="'+this.bNodes[iB].height+'" ';
								str = str + 'width="'+this.bNodes[iB].width+'">';
						}
						if( this.bNodes[iB].bannerType == "TEXT") {
							str = str + '<iframe width="'+this.bNodes[iB].width+'" height="'+this.bNodes[iB].height+'" src="'+this.bNodes[iB].bannerPath+'" marginwidth="0" marginheight="0" scrolling="no" frameborder="0"></iframe>'
						}
		
						if (this.bNodes[iB].hyperlink != ""){
								str = str + '</a>';
						}
		
						str += '</span>';
						i++; continue;
					}
					else{
						//str = str + 'i: e '+i;	
					}
				}
				// End iB for loop
                str = str + '<span name="'+this.aNodes[iCtr].name+'" '
                str = str + 'id="'+this.aNodes[iCtr].name+'" ';
                //str = str + 'class="m_banner_hide" ';
                str = str + 'bgcolor="#FFFCDA" ';        // CHANGE BANNER COLOR HERE
                str = str + 'class="d_banner_show" ';
                str = str + 'align="center" ';
                str = str + 'valign="top" >\n';
                if (this.aNodes[iCtr].hyperlink != ""){
                        str = str + '<a href="'+this.aNodes[iCtr].hyperlink+'" target="_blank">';
                }
                       
                if ( this.aNodes[iCtr].bannerType == "FLASH" ){
                        str = str + '<EMBED ';
                        str = str + 'src="'+this.aNodes[iCtr].bannerPath+'" '
                        str = str + 'quality=high '
//                        str = str + 'bgcolor=#FFFCDA '
                        str = str + 'WIDTH="'+this.aNodes[iCtr].width+'" '
                        str = str + 'HEIGHT="'+this.aNodes[iCtr].height+'" '
                        str = str + 'NAME="bnr_'+this.aNodes[iCtr].name+'" '
                        str = str + 'ALIGN="center" '
                        str = str + 'TYPE="application/x-shockwave-flash" '
                        str = str + 'PLUGINSPAGE="http://www.macromedia.com/go/getflashplayer">'
                        str = str + '</EMBED>'
                }else if ( this.aNodes[iCtr].bannerType == "IMAGE" ){
                        str = str + '<img src="'+this.aNodes[iCtr].bannerPath+'" ';
                        str = str + 'border="0" ';
                        str = str + 'height="'+this.aNodes[iCtr].height+'" ';
                        str = str + 'width="'+this.aNodes[iCtr].width+'">';
                }
				if( this.aNodes[iCtr].bannerType == "TEXT") {
					str = str + '<iframe width="'+this.aNodes[iCtr].width+'" height="'+this.aNodes[iCtr].height+'" src="'+this.aNodes[iCtr].bannerPath+'" marginwidth="0" marginheight="0" scrolling="no" frameborder="0"></iframe>'
				}

                if (this.aNodes[iCtr].hyperlink != ""){
                        str = str + '</a>';
                }
                str += '</span>';
				i++;
        }
        return str;
};

// d_Banner
// Written by Dungpt
function dFloat_Banner(objName){
        this.obj = objName;
        this.aNodes = [];
        this.currentBanner = 0;
       
};
// ADD NEW BANNER
dFloat_Banner.prototype.add = function(bannerType, bannerPath, height, width, hyperlink) {
		var bannerDuration = 0;
        this.aNodes[this.aNodes.length] = new Node(this.obj +"_"+ this.aNodes.length, bannerType, bannerPath, bannerDuration, height, width, hyperlink);
};
// Outputs the banner to the page
dFloat_Banner.prototype.toString = function() {
        var str = "";
		var BannerPostion = Math.floor(Math.random()*12321) % this.aNodes.length;
        for (var iCtr=BannerPostion; iCtr < this.aNodes.length; iCtr++){
                str = str + '<span name="'+this.aNodes[iCtr].name+'" '
                str = str + 'id="'+this.aNodes[iCtr].name+'" ';
                //str = str + 'class="m_banner_hide" ';
                str = str + 'bgcolor="#FFFCDA" ';        // CHANGE BANNER COLOR HERE
                str = str + 'class="d_banner_show" ';
                str = str + 'align="center" ';
                str = str + 'valign="top" >\n';
                if (this.aNodes[iCtr].hyperlink != ""){
                        str = str + '<a href="'+this.aNodes[iCtr].hyperlink+'" target="_blank">';
                }
                       
                if ( this.aNodes[iCtr].bannerType == "FLASH" ){
                        str = str + '<EMBED ';
                        str = str + 'src="'+this.aNodes[iCtr].bannerPath+'" '
                        str = str + 'quality=high '
//                        str = str + 'bgcolor=#FFFCDA '
                        str = str + 'WIDTH="'+this.aNodes[iCtr].width+'" '
                        str = str + 'HEIGHT="'+this.aNodes[iCtr].height+'" '
                        str = str + 'NAME="bnr_'+this.aNodes[iCtr].name+'" '
                        str = str + 'ALIGN="center" '
                        str = str + 'TYPE="application/x-shockwave-flash" '
                        str = str + 'PLUGINSPAGE="http://www.macromedia.com/go/getflashplayer">'
                        str = str + '</EMBED>'
                }else if ( this.aNodes[iCtr].bannerType == "IMAGE" ){
                        str = str + '<img src="'+this.aNodes[iCtr].bannerPath+'" ';
                        str = str + 'border="0" ';
                        str = str + 'height="'+this.aNodes[iCtr].height+'" ';
                        str = str + 'width="'+this.aNodes[iCtr].width+'">';
                }
				if( this.aNodes[iCtr].bannerType == "TEXT") {
					str = str + '<iframe width="'+this.aNodes[iCtr].width+'" height="'+this.aNodes[iCtr].height+'" src="'+this.aNodes[iCtr].bannerPath+'" marginwidth="0" marginheight="0" scrolling="no" frameborder="0"></iframe>'
				}

                if (this.aNodes[iCtr].hyperlink != ""){
                        str = str + '</a>';
                }

                str += '</span>';
        }
		for (var iCtr=0; iCtr < BannerPostion; iCtr++){
                str = str + '<span name="'+this.aNodes[iCtr].name+'" '
                str = str + 'id="'+this.aNodes[iCtr].name+'" ';
                //str = str + 'class="m_banner_hide" ';
                str = str + 'bgcolor="#FFFCDA" ';        // CHANGE BANNER COLOR HERE
                str = str + 'align="center" ';
                str = str + 'valign="top" >\n';
                if (this.aNodes[iCtr].hyperlink != ""){
                        str = str + '<a href="'+this.aNodes[iCtr].hyperlink+'" target="_blank">';
                }
                       
                if ( this.aNodes[iCtr].bannerType == "FLASH" ){
                        str = str + '<EMBED ';
                        str = str + 'src="'+this.aNodes[iCtr].bannerPath+'" '
                        str = str + 'quality=high '
//                        str = str + 'bgcolor=#FFFCDA '
                        str = str + 'WIDTH="'+this.aNodes[iCtr].width+'" '
                        str = str + 'HEIGHT="'+this.aNodes[iCtr].height+'" '
                        str = str + 'NAME="bnr_'+this.aNodes[iCtr].name+'" '
                        str = str + 'ALIGN="center" '
                        str = str + 'TYPE="application/x-shockwave-flash" '
                        str = str + 'PLUGINSPAGE="http://www.macromedia.com/go/getflashplayer">'
                        str = str + '</EMBED>'
                }else if ( this.aNodes[iCtr].bannerType == "IMAGE" ){
                        str = str + '<img src="'+this.aNodes[iCtr].bannerPath+'" ';
                        str = str + 'border="0" ';
                        str = str + 'height="'+this.aNodes[iCtr].height+'" ';
                        str = str + 'width="'+this.aNodes[iCtr].width+'">';
                }
				if( this.aNodes[iCtr].bannerType == "TEXT") {
					str = str + '<iframe width="'+this.aNodes[iCtr].width+'" height="'+this.aNodes[iCtr].height+'" src="'+this.aNodes[iCtr].bannerPath+'" marginwidth="0" marginheight="0" scrolling="no" frameborder="0"></iframe>'
				}

                if (this.aNodes[iCtr].hyperlink != ""){
                        str = str + '</a>';
                }

                str += '</span>';
        }
        return str;
};

function flashWrite(url,w,h,id,bg,vars){

     var flashStr=
    "<object classid='clsid:d27cdb6e-ae6d-11cf-96b8-444553540000' width='"+w+"' height='"+h+"' id='"+id+"' align='middle'>"+
    "<param name='allowScriptAccess' value='always' />"+
    "<param name='movie' value='"+url+"' />"+
    "<param name='FlashVars' value='"+vars+"' />"+
    "<param name='wmode' value='transparent' />"+
    "<param name='menu' value='false' />"+
    "<param name='quality' value='high' />"+
    "<embed src='"+url+"' FlashVars='"+vars+"' wmode='transparent' menu='false' quality='high' width='"+w+"' height='"+h+"' allowScriptAccess='always' type='application/x-shockwave-flash' pluginspage='http://www.macromedia.com/go/getflashplayer' />"+
    "</object>";
    document.write(flashStr);
}