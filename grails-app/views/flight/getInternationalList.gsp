<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="responsivemasterpage" />
<g:set var="entityName"
	value="${message(code: 'internationalList.label', default: 'Chuyen Quoc Te')}" />
<title><g:message code="title.homepage" args="[entityName]" /></title>

</head>
<body>
	<section id="break-crumb" class="row title-b hidden-xs">
			<a href='javascript:history.go(-1)'>Tìm vé</a> > <a  class="selected">Lựa
				chọn vé</a> > <span style="color:#333 ;font-weight: normal;">Thông tin chuyến bay</span> > <span style="color:#333 ;font-weight: normal;">Thanh
				toán</span> > <span style="color:#333 ;font-weight: normal;">Xác nhận</span>
		</section>
		<section class="row">
			<article id="chon-chuyen-bay-1" class="col-md-8 col-sm-8 col-xs-12">
				<h1>GIÁ ĐÃ BAO GỒM TOÀN BỘ THUẾ VÀ PHÍ, CAM KẾT ĐÚNG GIÁ HÃNG</h1>
			<div class="ule">
				<div style="float: left; width:330px">
					<h6 class="lt">
						<a > ${session.parameters.departureCode}
						</a> đi <a > ${session.parameters.arrivalCode}
						</a>
					</h6>
					<BR />
					
				</div>
				<ul class="hdb bg-type-red">
					<li><span>Bấm <a>Chọn</a> để chọn chuyến bay
					</span></li>
					<li><span>Bấm <a>Tiếp tục</a> để nhập thông tin
							đặt vé
					</span></li>
				</ul>
				<table class="qt-list">
						<tr>
							<td style="width:120px" ><span>Loại vé : </span></td>
							<td><a > <g:if
										test="${session.parameters.iday!='0' && session.parameters.imonth!='0'}">Khứ hồi</g:if>
									<g:else>Một chiều</g:else>
							</a></td>
							<td style="width:120px"><span> Số Lượng Khách: </span></td>
							<td><a > ${ session.parameters.adults+' Người lớn'} ${ session.parameters.kids.toInteger()>0?(',' +session.parameters.kids+' Trẻ em'):''}
									${ session.parameters.infants.toInteger()>0?(',' +session.parameters.infants+' Trẻ sơ sinh'):''}
							</a></td>
						</tr>
						<tr>
							<td><span>Ngày xuất phát:</td>
							<td><a ><b> ${session.parameters.oday+"/"+session.parameters.omonth}/${(session.parameters.omonth.toInteger()<(new Date().getMonth()+1))?(new Date().getYear()+1900+1):(new Date().getYear()+1900)}</b></a></span></td>
						<g:if test="${session.parameters.iday.toInteger()>0&&session.parameters.imonth.toInteger()>0}">
						<td><span>Ngày về:</td>
						<td> <a ><b>${session.parameters.iday+"/"+session.parameters.imonth}/${(session.parameters.imonth.toInteger()<(new Date().getMonth()+1))?(new Date().getYear()+1900+1):(new Date().getYear()+1900)}
								</b></a></span></td>
						</g:if>
						</tr>
					</table>
			</div>
			<div id="loaddata">
				<img src="${resource(dir:'images',file:'ajax-loader.gif')}" align="middle" />
				</br>
				<span style="font-size: 14px">Đang tìm kết quả các chuyến bay...</span>
			</div>
			<div id="departure"></div>
		</article>
		<article class="col-md-4 col-sm-4 col-xs-12" style="padding: 0">
			<div id="tim-chuyen-bay" class="tour-box bg-type-red box-border-radius-5 box-padding-20 box-small">
				<div class="title title-b title-upper">
					<span class="title">SẮP XẾP</span>
				</div>
				<div class="line line-horizontal"></div>
				<div class="space-5"></div>
				<div class="line linehd">
				<ul>
						<li><input type="radio" onClick="sortByPrice(this)" checked="checked" value="tb" name="check"  />
							Giá (Từ thấp đến cao)</li>
						<li><input type="radio" onClick="sortByHour(this)" value="gb" name="check" /> Giờ cất cánh (Sớm
							đến muộn)</li>
						<li><input type="radio" onClick="sortByAirline(this)" value="hb" name="check" /> Hãng Hàng Không</li>
					</ul>
					</div>
					<br />
					<div class="title title-b title-upper">
					<span class="title">TIÊU CHÍ LỌC</span>
				</div>
				<div class="line line-horizontal"></div>
				<div class="line linehd">
					<ul id="transit">
					<li class="vnair"><input type="checkbox" value="oneway" id="one" checked onClick="filterTransit(this)"  /> Bay thẳng
					<li class="vnair"><input type="checkbox" value="twoway" id="two" checked onClick="filterTransit(this)"  /> Bay chuyển chặng 
					</ul>
				</div>
				<br />
				<div class="line line-horizontal"></div>
				<div class="space-5"></div>
				<div class="line linehd">
					<ul id="airlines">
					</ul>
				</div>
			</div>
			<div style="clear: both"></div>
			<div class="space-10"></div>
			</article>
			<g:include view="layouts/responsivewebpart/_searchFlightForm.gsp" />
			</section>
			
		<script type="text/javascript">
			function filterTransit(el){
				if($(el).attr("id")=='one'){
					if($(el).attr("checked")){
						$(".one").each(
								function(i,el){
								$(el).show()
								}
						)
					}else{
						$(".one").each(
								function(i,el){
								$(el).hide()
								}
						)
					}
				}else{
					if($(el).attr("checked")){
						$(".two").each(
								function(i,el){
								$(el).show()
								}
						)
					}else{
						$(".two").each(
								function(i,el){
								$(el).hide()
								}
						)
					}

				}
				
				
			}
			jQuery(document).ready(function(){
				if($("input[id='oneWay']").is(':checked') == true){
					$("#departureField").hide();
				}
			})
			$("input[name='flightType']").change(function(){
				if($(this).attr("id")=="oneWay"){
					$("#departureField").hide();
					$("#departureField select").val(0);
				} else {
					var iday = jQuery("#hdnIday").val()
					var imonth = jQuery("#hdnImonth").val()
					jQuery('#iday').val(iday)
					jQuery('#imonth').val(imonth)
					$("#departureField").show();
				}
			});
			var checkSearch=new Array()
			var array=new Array()
			function initArray(){
				for(i=1;i<=32;i++){
						array[i]=true
				}
			}
			function checkFlightNotfound(){
				var flag=true
				for(i=1;i<=array.length;i++){
					if(array[i]){
						flag=false
						break;
					}
				}
				if(flag){
					var count=0;
					for(var i=0;i<checkSearch.length;i++){
						if(checkSearch[i]==1){
						count++;
						}
					}
					if(count==32){
				
								$.ajax({
								async : false,
				 			    type : "POST",
								url : "/flight/couldNotFound",
								success : function(jsonData) {
								}
								});
					}							
				}
			}
			function setCurrentItem(item){
				var obj=jQuery.parseJSON(unescape(item));
				$.ajax({
					async : false,
					parseRequest: false,
					type : "POST",
					contentType : "text/plain",
					url : "/flight/setcurrentitem",
					data:unescape(item),
					dataType : "json",
					success : function(jsonData) {
					}
				});

				parent.location='internationalflightinfo'
				
				
				
			}
			function viewDetails(el){
				$(el).toggleClass("active")
				$(el).closest('table').find('.seclect-two').toggle()
			}
			$(function(){
			    $(window).scroll(function() { 
			        if ($(this).scrollTop() > 75) {
			            $("div#next").hide();  
			        } 
			        else {     
			            $("div#next").show(); 
			        }  
			    });
			});
			function viewPrice(el){
				$(el).toggleClass("active");
				$(el).closest('table').find('.qt-price').toggle();
			}
			Number.prototype.format = function() {
				 var rx=  /(\d+)(\d{3})/;
				    return this.toFixed(0).replace(/^\d+/, function(w){
				        while(rx.test(w)){
				            w= w.replace(rx, '$1,$2');
				        }
				        return w;
				 });
			}
				var inboundCode='${session.parameters.departureCode+session.parameters.departureCode}'
				function appendQTEnd(numberOfTicket,totalPrice,pricePerAdult,pricePerChild,currencyCode,airlineCode,adults,kids,infants,item,airlineIndex){
					var itemString=new String(JSON.stringify(item))	
					var str=""
					if(infants > 0){
					var infantPrice = (totalPrice - (pricePerAdult * adults) - (pricePerChild * kids))/infants
						if(infantPrice <= 50000){
							pricePerAdult =  pricePerAdult + ((infantPrice*infants)/adults)
							infantPrice = 0
						}
					}
				   	var count=0 
					 str+='<table class="select heasd">'
	                   str+='<tr class="qt-end">'
	                    	str+='<td width="150px"><img src="/images/sm'+airlineCode+'.gif" /></td>'
	                        str+='<td width="300px" class="ct-list prices" onclick="viewPrice(this)"><a>Chi tiết giá</a> </td>'
	                        str+='<td class="price big" style="width:250px"><a >'+pricePerAdult.format()+' '+currencyCode+'</a></td>'
	                        str+='<td width="100px"><input type="submit" value="Tiếp tục" name="Next"  onClick="setCurrentItem(\''+escape(itemString)+'\')" class="qt-sub" /></td>'
	                   str+='</tr>'
	                    str+='<tr class="qt-price">'
	                    	str+='<td colspan="4">'
	                        	str+='<table>'
		                        	str+='<tr>'
	                                str+='<td><span>Loại hành khách</span></td>'
	                                str+='<td><span>Số lượng vé</span></td>'
	                                str+='<td><span>Giá mỗi vé</span></td>'
	                                str+='<td><span>Tổng</span></td>'
		                            str+='</tr>'
			                           
			                       	if(adults>0){
				                       	count++
			                       		str+='<tr>'
			                                str+='<td><a>Người lớn</a></td>'
			                                str+='<td><a>'+adults+'</a></td>'
			                                str+='<td><a>'+pricePerAdult.format()+' '+currencyCode+'</a></td>'
			                                str+='<td><a>'+(pricePerAdult*adults).format()+' '+currencyCode+'</a></td>'
				                       str+='</tr>'
					                }    
									if(kids>0){
										count++
                   						 str+='<tr>'
                          				 str+='<td><a>Trẻ con</a></td>'
                          				 str+='<td><a>'+kids+'</a></td>'
                           				 str+='<td><a>'+pricePerChild.format()+' '+currencyCode+'</a></td>'
                           				 str+='<td><a>'+(pricePerChild*kids).format()+' '+currencyCode+'</a></td>'
                      					 str+='</tr>'
	              				    }
	              				    
									if(infants>0){
										count++
		                   				 str+='<tr>'
		                          		 str+='<td><a>Trẻ sơ sinh</a></td>'
		                          		 str+='<td><a>'+infants+'</a></td>'
		                           		 str+='<td><a>'+infantPrice.format()+' '+currencyCode+'</a></td>'
		                           		 str+='<td><a>'+(infantPrice*infants).format()+' '+currencyCode+'</a></td>'
		                      			 str+='</tr>'
			              		   }            
									     str+='<tr>'
										 str+='<td></td>'
										 str+='<td></td>'
		                          		 str+='<td><a>Tổng</a></td>'
		                          		 //str+='<td><a href>'+numberOfTicket+'</a></td>'
		                           		 //str+='<td><a href>'+(basePrice*count).format()+' '+currencyCode+'</a></td>'
		                           		 str+='<td><a>'+(totalPrice).format()+' '+currencyCode+'</a></td>'
		                      			 str+='</tr>'   
	                           str+='</table>'
	                        str+='</td>'
	                    str+='</tr>'
	                str+='</table>'	
		                return str
			    }
				function appendDetail(flights,stopoverDurations,fareType){
					var str=""
					if(flights){	
					str='<tr class="seclect-two">'
                 	 str+='<td colspan="8" style="border:none">'
                      str+=   '<table>'
                          for(i in flights){
                        	  if(i>0){
                          		str+='<tr>'
                  				str+='<td colspan="3" class="qt-stop">'
                      			 str+='Đổi máy bay tại<b class="change-address"> '+flights[i-1].arrivalAirport+' </b>Thời gian chờ:'
                          		 str+='<b class="change-time">'+stopoverDurations[i-1]+'</b>'
                      	
                      			str+='</td>'
                  			    str+='</tr>'
                                }
                    str+='<tr>'
                      str+='<td width="150px">'
                      	  if(flights[i].departureCity != null && flights[i].departureCity != "null" && flights[i].departureCity != "Undefined" && flights[i].departureCity != ""){
                      		  str+='<p>Từ <b>'+flights[i].departureCity+'</b></p>'
                      	  } else {
                      		  str+='<p>Từ <b>'+flights[i].departureAirport+'</b></p>'
                      	  }
                          str+='<p>Sân bay : <b>'+flights[i].departureAirport+'</b></p>'
                          str+='<p><b>'+flights[i].departureTime+'</b>, '+flights[i].departureDate+'</p>'
                      str+='</td>'
                      str+='<td width="200px">'
                      	  if(flights[i].arrivalCity != null && flights[i].arrivalCity != "null" && flights[i].arrivalCity != "Undefined" && flights[i].arrivalCity != ""){
                      	  	str+='<p>Tới <b>'+flights[i].arrivalCity+'</b></p>'
                      	  } else {
                      	  	str+='<p>Tới <b>'+flights[i].arrivalAirport+'</b></p>'
                      	  }
                          str+='<p>Sân bay : <b>'+flights[i].arrivalAirport+'</b></p>'
                          str+='<p><b>'+flights[i].arrivalTime+'</b>, '+flights[i].arrivalDate+' </p>'
                      str+='</td>'
                      str+='<td width="200px">'
                          str+='<table>'
                          str+='<tr>'
                                  str+='<td><img src="/images/sm'+flights[i].airlineCode+'.gif"/></td>'
                                  str+='<td>'
                                	  str+=flights[i].carrier+'<br>(<b>'+flights[i].airlineCode+' '+flights[i].flightNumber+'</b>)<br>'
                                	  str+='<label> Loại vé :<b>Hạng phổ thông</b></label>'
                                	str+='</td>'
                               str+='</tr>'
                          str+='</table>'
                          str+='</td>'
                          str+='</tr>'
                             
                  
               			  }
               str+='</table>'
               str+='</td>'
               str+='</tr>'   
					}
				return str
				}	

				var airlineList=new Array()
				function listAirlineFound(prefix,airlineCode,carrier){
					$("#airlines").append('<li class="vnair"><input type="checkbox" value="mb" checked onClick="filterFlights('+prefix+',this)"  />'+' '+carrier+'<img src="/images/sm'+airlineCode+'.gif"/></li>')
					airlineList.push(airlineCode)
				}
				
				var selectedAirlines=new Array()
				function filterFlights(prefix,el){
					if($(el).attr("checked")){
						selectedAirlines[prefix]=prefix
					}	
					else{
						selectedAirlines[prefix]=0
					}
					if($(el).attr("checked")){
					for(i=0;i<selectedAirlines.length;i++){
						if(i!=0&&selectedAirlines[i]==i){
							$("div[id^='"+prefix+"_']").each(
								function(i,el){
								$(el).show()
								}
							)
						  }
						}
					}else{
						for(i=0;i<selectedAirlines.length;i++){
							if(i!=0&&selectedAirlines[i]==0){
								$("div[id^='"+prefix+"_']").each(
									function(i,el){
									$(el).hide()
									}
								)
							  }
							}
					}
				}
				
				
				function keysbyValue(O){
				    var A= [];
				    for(var p in O){
				        if(O.hasOwnProperty(p)) A.push([p, O[p]]);
				    }
				    A.sort(function(a, b){
				        var a1= a[1], b1= b[1];
				        return a1-b1;
				    });
				    for(var i= 0, L= A.length; i<L; i++){
				        A[i]= A[i][0];
				    }
				    return A;
				}
				var sortedMapOut=new Object()
				function insertItemAndSortByPrice(isOutbound,item,index,value){
						sortedMapOut[index]=value
						var keys=keysbyValue(sortedMapOut)
					  if(keys.length==1){
					      $("#departure").prepend(item)
					  }else{
					      if(keys[0]==index){
					    	  $("#departure").prepend(item)
					      }else if(keys[keys.length-1]==index){
					        $("#departure").append(item)
					      }else{
					          $(item).insertAfter("#"+keys[keys.indexOf(index)-1])
					      }
					  }
					}
				function sortAirline(selector,isAsc){
				 $(selector).children("div").sort(function(a, b) {
    			     var pr1 = $(a).data("airline");
       				 var pr2 = $(b).data("airline");
       				 if(isAsc){
       				  return pr1.toUpperCase().localeCompare(pr2.toUpperCase())
       				 }else{
       				   return pr2.toUpperCase().localeCompare(pr1.toUpperCase())
       				 }
		         }).appendTo(selector);
				}
				function sortByAirline(el){
					if($(el).attr("checked")){
					sortAirline("#departure",true)
					
					}else{
					sortAirline("#departure",false)
					}
				}
				function sortHour(selector,isAsc){
				 $(selector).children("div").sort(function(a, b) {
    			     var pr1 = new Date("2014-01-01 "+$(a).data("hour")+":00");
       				 var pr2 = new Date("2014-01-01 "+$(b).data("hour")+":00");
       				 if(isAsc){
       				  return (pr1<pr2)?-1:(pr1>pr2)? 1:0;
       				 }else{
       				  return (pr2 < pr1)?-1:(pr2 > pr1)?1:0;
       				 }
		         }).appendTo(selector);
				}
				function sortByHour(el){
					if($(el).attr("checked")){
					sortHour("#departure",true)
					
					}else{
					sortHour("#departure",false)
					}
				}
				function sortUL(selector,isAsc) {
   				 $(selector).children("div").sort(function(a, b) {
    			     var pr1 = $(a).data("price");
       				 var pr2 = $(b).data("price");
       				 if(isAsc){
       				 return (pr1 < pr2) ? -1 : (pr1 > pr2) ? 1 : 0;
       				 }else{
       				  return (pr2 < pr1) ?-1 : (pr2 > pr1) ? 1 : 0;
       				 }
		         }).appendTo(selector);
				}
				function sortByPrice(el){
					if($(el).attr("checked")){
					sortUL("#departure",true)
					}else{
					sortUL("#departure",false)
					}
				}
				
				function doCheck(value, array) {
					for (var i = 0; i < array.length; i++) {
						if (array[i] == value) {
							return true;
						}
					}
					return false;
				}
				
				function getFares(prefix,url) {
					$.ajax({
						async : true,
						type : "GET",
						contentType : "application/json; charset=utf-8",
						url : url,
						dataType : "json",
						success : function(jsonData) {
							setTimeout(function(){
							if(typeof jsonData === 'undefined'|| typeof jsonData[0]==='undefined'){
								array[prefix]=false
								checkFlightNotfound()
							}else{	
							
							var reset = prefix
							var newPrefix = 33
							
							for (i in jsonData) {	
								var item=jsonData[i]
								
								prefix = reset
								
								if(doCheck(item.airlineCode, airlineList)){
									var element = "div[data-airline=" + item.airlineCode + "]:first";
									var before = $(element).attr('id').split('_')[0]
									prefix=before
									var id=before+"_"+i
								}else{
									if(prefix == 33){
										prefix = newPrefix+1
										var id=prefix+"_"+i
										newPrefix++
									}
									else{
										var id=prefix+"_"+i
									}
								}

								if(item.oflights&&item.oflights.length>0){
								var tbl_o_id="tbl_o_"+id
								var trid=tbl_o_id+'_tr'
								var element='<div id="'+id+'" data-airline='+jsonData[i].airlineCode+' data-price='+item.price+' data-hour='+item.outboundDepartureTime+' class="qt-select">'
								if(item.oflights.length>1){
									element='<div id="'+id+'" data-airline='+jsonData[i].airlineCode+' data-price='+item.price+' data-hour='+item.outboundDepartureTime+' class="qt-select two">'
								}else{
									element='<div id="'+id+'" data-airline='+jsonData[i].airlineCode+' data-price='+item.price+' data-hour='+item.outboundDepartureTime+' class="qt-select one">'
								}
								element+='<table  id="'+ tbl_o_id+'" class="select heasd">'
								element+='<tr class="seclect-one" id="'+trid+'">'
								element+='<td width="40px"><img src="/images/From.png" /></td>'
								element+='<td class="mb" style="width:40px"><a >'+${session.parameters.oday}+'/'+${session.parameters.omonth}+'</a></td>'
								element+='<td width="100px"><a >'+'${session.parameters.departureCity}'+'-'+'${session.parameters.arrivalCity}'+'</a></td>'
								element+='<td class="time"><a >'+item.outboundDepartureTime+' - '+item.outboundArrivalTime+'</a></td>'
								element+='<td width="70px"><a >'+item.outboundDuration+'</a></td>'
								element+='<td width="70px"><a >'+item.outboundNumberOfStopover+' stops'+'</a></td>'
								element+='<td class="ct-list" onclick="viewDetails(this)"><a>Chi tiết</a></td>'
								element+='<td width="85px"><input type="radio" name="selec" value="select"><span><a class="tour-button" href="javascript:void(0)" onclick="selectFlight(this)">Chọn</a></span></td>'
								element+='</tr>'	
								element+=appendDetail(item.oflights,item.outboundStopoverDurations)
								element+='</table>'	
								   if(item.iflights&&item.iflights.length>0){
									var tbl_i_id="tbl_i_"+id
									trid=tbl_i_id+'_tr'
									element+='<table  id="'+ tbl_i_id+'" class="select heasd">'
									element+='<tr class="seclect-one" id="'+trid+'">'
									element+='<td width="40px"><img src="/images/To.png" /></td>'
									element+='<td class="mb" style="width:40px"><a >'+${session.parameters.iday}+'/'+${session.parameters.imonth}+'</a></td>'
									element+='<td width="100px"><a >'+'${session.parameters.arrivalCity}'+'-'+'${session.parameters.departureCity}'+'</a></td>'
									element+='<td class="time"><a >'+item.inboundDepartureTime+' - '+item.inboundArrivalTime+'</a></td>'
									element+='<td width="70px"><a >'+item.inboundDuration+'</a></td>'
									element+='<td width="70px"><a >'+item.inboundNumberOfStopover+' stops'+'</a></td>'
									element+='<td class="ct-list" onclick="viewDetails(this)"><a>Chi tiết</a></td>'
									element+='<td width="85px"><input type="radio" name="selec" value="select"><span><a class="tour-button" href="javascript:void(0)" onclick="selectFlight(this)">Chọn</a></span></td>'
									element+='</tr>'
									element+=appendDetail(item.iflights,item.inboundStopoverDurations)
									element+='</table>'
									}	
									var numberCustomers=${session.parameters.adults.toInteger()+session.parameters.kids.toInteger()+session.parameters.infants.toInteger()}
									element+=appendQTEnd(numberCustomers,item.price,item.pricePerAdult,item.pricePerChild,item.currencyCode,item.airlineCode,${session.parameters.adults.toInteger()},${session.parameters.kids.toInteger()},${session.parameters.infants.toInteger()},item,prefix)
									element+='</div>'	
									insertItemAndSortByPrice(true,element,id,item.price)
									}
								
									if(!doCheck(item.airlineCode, airlineList)){
										listAirlineFound(prefix,jsonData[i].airlineCode,jsonData[i].airlineName)
									}
								}
								
								//if(prefix!=2){
									//listAirlineFound(prefix,jsonData[i].airlineCode,jsonData[i].airlineName)
								//}
						     	//array[prefix]=true
							};	
							},2000);
							checkSearch[prefix]=1
							var count=0;
							for(var i=0;i<checkSearch.length;i++){
								if(checkSearch[i]==1){
								count++;
								}
							}
							if(count==33){
								$("#loaddata").remove()
							}			
						}
						
					});
						
				}

				function selectFlight(currentUrl){
					var closestInp = $(currentUrl).closest('td').find('input[type="radio"]')
					$(closestInp).prop('checked', 'checked')
				}
				
				
				
				$(document).ready(function() {
									initArray()
									var urlEdreamFares = "${createLink(controller:'flight', action:'getEdreamFares')}";
									var urlQatarFares = "${createLink(controller:'flight', action:'getQatarFares')}";
									var urlVietnamairlinesFares = "${createLink(controller:'flight', action:'getVietnamairlinesFares')}";
									var urlUnitedFares = "${createLink(controller:'flight', action:'getUnitedFares')}";
									var urlVietjetFares = "${createLink(controller:'flight', action:'getVietjetFares')}";
									var urlAeroflotFares= "${createLink(controller:'flight', action:'getAeroflotFares')}";
									var urlJetstarFares= "${createLink(controller:'flight', action:'getJetstarFares')}";
									var urlCebupacificFares= "${createLink(controller:'flight', action:'getCebupacificFares')}";
									var urlMalaysiaairlinesFares= "${createLink(controller:'flight', action:'getMalaysiaairlinesFares')}";
									var urlPhilippineairlinesFares= "${createLink(controller:'flight', action:'getPhilippineairlinesFares')}";
									var urlSouthafricanairwaysFares= "${createLink(controller:'flight', action:'getSouthafricanairwaysFares')}";
									//I 2
									var urlBritishairwaysFares= "${createLink(controller:'flight', action:'getBritishairwaysFares')}";
									var urlCathaypacificFares= "${createLink(controller:'flight', action:'getCathaypacificFares')}";
									var urlTransasiaairwaysFares= "${createLink(controller:'flight', action:'getTransasiaairwaysFares')}";
									var urlEvaFares= "${createLink(controller:'flight', action:'getEvaFares')}";
									var urlLufthansaFares= "${createLink(controller:'flight', action:'getLufthansaFares')}";
									var urlChinaeasternairlineFares= "${createLink(controller:'flight', action:'getChinaeasternairlineFares')}";
									var urlChinaairlineFares="${createLink(controller:'flight', action:'getChinaairlineFares')}";
									var urlAllnipponairwaysFares="${createLink(controller:'flight', action:'getAllnipponairwaysFares')}";
									var urlChinasouthernFares="${createLink(controller:'flight', action:'getChinasouthernFares')}";
									var urlEmiratesFares="${createLink(controller:'flight', action:'getEmiratesFares')}";
									var urlHongkongFares="${createLink(controller:'flight', action:'getHongkongFares')}";
									var urlKoreanFares="${createLink(controller:'flight', action:'getKoreanFares')}";
									var urlJapanFares="${createLink(controller:'flight', action:'getJapanFares')}";
									var urlAirchinaFares="${createLink(controller:'flight', action:'getAirchinaFares')}";
									// I3
									var urlEthiopianFares="${createLink(controller:'flight', action:'getEthiopiaFares')}";
									var urlSingaporeFares="${createLink(controller:'flight', action:'getSingaporeFares')}";
									var urlThaiFares="${createLink(controller:'flight', action:'getThaiFares')}";
								    var urlAmericanFares="${createLink(controller:'flight', action:'getAmericanFares')}";
								    var urlAirfranceFares="${createLink(controller:'flight', action:'getAirfranceFares')}";
								    var urlKenyaFares="${createLink(controller:'flight', action:'getKenyaFares')}"; 
									var urlDeltaFares="${createLink(controller:'flight', action:'getDeltaFares')}";
									var urlAirAsiaFares = "${createLink(controller:'flight', action:'getAirAsiaFares')}";								
									var urlAbacusFares="${createLink(controller:'flight', action:'getAbacusFares')}";
					    getFares(1,urlVietnamairlinesFares)
						getFares(2,urlVietjetFares)
						getFares(3,urlJetstarFares)
						getFares(4,urlQatarFares)
							
						getFares(5,urlUnitedFares)
							
						getFares(6,urlAeroflotFares)
							
						getFares(7,urlCebupacificFares)
							
						getFares(8,urlMalaysiaairlinesFares)
							
						getFares(9,urlPhilippineairlinesFares)
						getFares(10,urlSouthafricanairwaysFares)
									//I 2
						getFares(11,urlBritishairwaysFares)
						getFares(12,urlTransasiaairwaysFares)
						getFares(13,urlLufthansaFares)
						getFares(14,urlChinaeasternairlineFares)
						getFares(15,urlChinaairlineFares)
						getFares(16,urlAllnipponairwaysFares)
						getFares(17,urlChinasouthernFares)
						getFares(18,urlEmiratesFares)
						getFares(19,urlHongkongFares)
						getFares(20,urlKoreanFares)
						getFares(21,urlAirchinaFares)
						getFares(22,urlJapanFares)
									//I 3
						getFares(23,urlCathaypacificFares)
						getFares(24,urlEvaFares)
						getFares(25,urlEthiopianFares)
						getFares(26,urlSingaporeFares)
							
						getFares(27,urlThaiFares)
						getFares(28,urlAmericanFares)
						getFares(29,urlAirfranceFares)
						getFares(30,urlKenyaFares)
						getFares(31,urlDeltaFares)
						getFares(32,urlAirAsiaFares)
							
						getFares(33,urlAbacusFares)
						})
									
								
				
			</script>
</body>
</html>
