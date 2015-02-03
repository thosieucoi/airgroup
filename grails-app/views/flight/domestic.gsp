<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="responsivemasterpage" />
<g:set var="entityName"
	value="${message(code: 'localList.label', default: 'Chuyen Bay Noi Dia')}" />
<title><g:message code="title.homepage" args="[entityName]" /></title>

</head>
<body>
<script src="${resource(dir:'js',file:'gridline.min.js')}"></script>
	<section id="break-crumb" class="row title-b hidden-xs">
		<a href='javascript:history.go(-1)'>Tìm vé</a> > <a class="selected">Lựa
			chọn vé</a> > <span style="color: #333; font-weight: normal;">Thông
			tin chuyến bay</span> > <span style="color: #333; font-weight: normal;">Thanh
			toán</span> > <span style="color: #333; font-weight: normal;">Xác
			nhận</span>
	</section>
	<section class="row">
		<article id="chon-chuyen-bay-1" class="col-md-8 col-sm-8 col-xs-12">
			<p>
			<h1>GIÁ ĐÃ BAO GỒM THUẾ VÀ PHÍ</h1>
			</p>
			<div class="ule">
				
				<h6 class="lt">
					<a> ${session.parameters.departureCode}
					</a> đi <a> ${session.parameters.arrivalCode}
					</a> ngày <a id="departureDayFinal"> ${session.parameters.oday+"/"+session.parameters.omonth}/${((session.parameters.omonth.toInteger()<(new Date().getMonth()+1))||(session.parameters.omonth.toInteger()==(new Date().getMonth()+1)&&session.parameters.oday.toInteger()<new Date().getDate()))?(new Date().getYear()+1900+1):(new Date().getYear()+1900)}
					</a>
				</h6>
				<ul class="hdb bg-type-red">
					<li><span>Bấm <a>Chọn</a> để chọn chuyến bay
					</span></li>
					<li><span>Bấm <a>Tiếp tục</a> để nhập thông tin đặt vé
					</span></li>
				</ul>
			</div>
			<div id="loaddata">
				<img src="${resource(dir:'images',file:'ajax-loader.gif')}"
					align="middle"/>
				</br>
				<span style="font-size: 14px">Đang tìm kết quả các chuyến bay...</span>
			</div>

			<div class="select">
				<div class="list-h">
					<div class="list-h">
							<div class="col" style="border: none">
								<div class="date-m row" data-columns="7" id="flightOutboundDate">
								</div>
							</div>
					</div>	
						
				</div>
			</div>

			<div id="results">
				<div id="departure"></div>
				<g:if
					test="${session.parameters.iday!='0' && session.parameters.imonth!='0'}">
					<div class="ule">
						<h6 class="lt">
							<a> ${session.parameters.arrivalCode}
							</a> đi <a> ${session.parameters.departureCode}
							</a> ngày <a> ${session.parameters.iday+"/"+session.parameters.imonth}/${((session.parameters.imonth.toInteger()<(new Date().getMonth()+1))||(session.parameters.imonth.toInteger()==(new Date().getMonth()+1)&&session.parameters.iday.toInteger()<new Date().getDate()))?(new Date().getYear()+1900+1):(new Date().getYear()+1900)}
							</a>
						</h6>
					</div>
					<div class="select">
						<div class="list-h">
							<div class="col" style="border: none">
								<div class="date-m row" data-columns="7" id="flightInboundDate">
								</div>
							</div>
						</div>
					</div>
					<div id="return"></div>
				</g:if>
			</div>
			<input type="submit" class="bottom" value="Tiếp tục" name="Next"
				onClick="parent.location='flyinfo'" />
		</article>
		<article class="col-md-4 col-sm-4 col-xs-12" style="padding: 0">
			<div id="tim-chuyen-bay"
				class="tour-box bg-type-red box-border-radius-5 box-padding-20 box-small">
				<div class="title title-b title-upper">
					<span class="title">SẮP XẾP</span>
				</div>
				<div class="line line-horizontal"></div>
				<div class="space-5"></div>
				<div class="line linehd">
					<ul>
						<li><input type="radio" onClick="sortByPrice(this)"
							checked="checked" value="tb" name="check" /> Giá (Từ thấp đến
							cao)</li>
						<li><input type="radio" onClick="sortByHour(this)" value="gb"
							name="check" /> Giờ cất cánh (Sớm đến muộn)</li>
						<li><input type="radio" onClick="sortByAirline(this)"
							value="hb" name="check" /> Hãng Hàng Không</li>
					</ul>
				</div>
				<br />
				<div class="title title-b title-upper">
					<span class="title">TIÊU CHÍ LỌC</span>
				</div>
				<div class="line line-horizontal"></div>
				<div class="space-5"></div>
				<div class="line linehd">
					<ul id="transit">
						<li class="vnair"><input type="checkbox" value="oneway"
							id="one" checked onClick="filterTransit(this)" /> Bay thẳng
						<li class="vnair"><input type="checkbox" value="twoway"
							id="two" checked onClick="filterTransit(this)" /> Bay chuyển
							chặng
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
			<div id="next">
				<input type="submit" value="Tiếp tục" name="Next"
					onClick="parent.location='flyinfo'" />
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
			var checkSearch=new Array()
			jQuery(document).ready(function(){
				if($("input[id='oneWay']").is(':checked') == true){
					$("#departureField").hide();
				}
				var finalDepartureDay = jQuery("#departureDayFinal").html();
				var currentDepartureYear = finalDepartureDay.substring(finalDepartureDay.lastIndexOf("/") + 1, finalDepartureDay.length);
				var oDay = jQuery('#hdnOday').val()
				var oMonth = jQuery('#hdnOmonth').val()
				var iDay = jQuery('#hdnIday').val()
				var iMonth = jQuery('#hdnImonth').val()
				var currentDate = new Date()
				var currentMonth = currentDate.getMonth() + 1
				var currentYear = currentDate.getFullYear()
				var oDayNumber = parseInt(oDay)
				var iDayNumber = parseInt(iDay)
				var startLoopOutDate = oDayNumber -3
				var endLoopOutDate = oDayNumber + 3
				var startLoopInDate = iDayNumber -3
				var endLoopInDate = iDayNumber + 3
				var currentSearchDate
				var currentDay
				var changeSearchOutDate
				var changeSearchOutMonth
				var changeSearchInDate
				var changeSearchInMonth
				for(i = startLoopOutDate; i < endLoopOutDate + 1; i++){
					if(oMonth >= currentMonth){
						currentSearchDate = new Date(currentYear, oMonth-1, i)
					} else if(oMonth < currentMonth){
						currentSearchDate = new Date(currentYear + 1, oMonth-1, i)
					}
					currentDay= currentSearchDate.getDay()
					changeSearchOutDate = currentSearchDate.getDate()
					changeSearchOutMonth = currentSearchDate.getMonth()+1
					
					//Set hour to 0 for ignoring hour comparing 
					currentSearchDate.setHours(0,0,0,0);
					currentDate.setHours(0,0,0,0);
					
					if(i == oDayNumber){
						jQuery('#flightOutboundDate').append('<div style="font-width:bold" class="atv-v col-lg-1 col-md-1 col-sm-1 col-xs-1"><a href="javascript:void(0)" id="outBound'+i+'" onclick="switchDateSearch(document.URL, this.id,'+
								changeSearchOutDate+','+changeSearchOutMonth+' ,0)">'+
								replaceDay(parseInt(currentDay))+' '+changeSearchOutDate+'/'+changeSearchOutMonth+'</a></div>')
					} else if(i == endLoopOutDate){
						jQuery('#flightOutboundDate').append('<div class="col-lg-1 col-md-1 col-sm-1 col-xs-1" style="border-right: none"><a href="javascript:void(0)" id="outBound'+i+'" onclick="switchDateSearch(document.URL, this.id,'+
								changeSearchOutDate+','+changeSearchOutMonth+' ,0)">'+
								replaceDay(parseInt(currentDay))+' '+changeSearchOutDate+'/'+changeSearchOutMonth+'</a></div>')
					} else if(currentSearchDate < currentDate) {
						jQuery('#flightOutboundDate').append('<div class="col-lg-1 col-md-1 col-sm-1 col-xs-1"><a style="pointer-events: none; cusor: default;" href="javascript:void(0)" id="outBound'+i+'">'+
								replaceDay(parseInt(currentDay))+' '+changeSearchOutDate+'/'+changeSearchOutMonth+'</a></div>')
					} else {
						jQuery('#flightOutboundDate').append('<div class="col-lg-1 col-md-1 col-sm-1 col-xs-1"><a href="javascript:void(0)" id="outBound'+i+'" onclick="switchDateSearch(document.URL, this.id,'+
								changeSearchOutDate+','+changeSearchOutMonth+' ,0)">'+
								replaceDay(parseInt(currentDay))+' '+changeSearchOutDate+'/'+changeSearchOutMonth+'</a></div>')
					}
				}
				for(i = startLoopInDate; i < endLoopInDate + 1; i++){
					if(iMonth >= currentMonth){
						currentSearchDate = new Date(currentYear, iMonth-1, i)
					} else if(iMonth < currentMonth){
						currentSearchDate = new Date(currentYear + 1, iMonth-1, i)
					}
					currentDay= currentSearchDate.getDay()
					changeSearchInDate = currentSearchDate.getDate()
					changeSearchInMonth = currentSearchDate.getMonth()+1

					//Set hour to 0 for ignoring hour comparing 
					currentSearchDate.setHours(0,0,0,0);
					currentDate.setHours(0,0,0,0);
					
					if(i == iDayNumber){
						jQuery('#flightInboundDate').append('<li class="atv-v"><a href="javascript:void(0)" id="inBound'+i+'" onclick="switchDateSearch(document.URL, this.id,'+
								changeSearchInDate+','+changeSearchInMonth+' ,1)">'+
								replaceDay(parseInt(currentDay))+' '+changeSearchInDate+'/'+changeSearchInMonth+'</a></li>')
					} else if(i == endLoopInDate){
						jQuery('#flightInboundDate').append('<li style="border-right: none"><a href="javascript:void(0)" id="inBound'+i+'" onclick="switchDateSearch(document.URL, this.id,'+
								changeSearchInDate+','+changeSearchInMonth+' ,1)">'+
								replaceDay(parseInt(currentDay))+' '+changeSearchInDate+'/'+changeSearchInMonth+'</a></li>')
					} else if(currentSearchDate < currentDate) {
						jQuery('#flightInboundDate').append('<li><a style="pointer-events: none; cusor: default;" href="javascript:void(0)" id="inBound'+i+'">'+
								replaceDay(parseInt(currentDay))+' '+changeSearchInDate+'/'+changeSearchInMonth+'</a></li>')
					} else {
						jQuery('#flightInboundDate').append('<li><a href="javascript:void(0)" id="inBound'+i+'" onclick="switchDateSearch(document.URL, this.id,'+
								changeSearchInDate+','+changeSearchInMonth+' ,1)">'+
								replaceDay(parseInt(currentDay))+' '+changeSearchInDate+'/'+changeSearchInMonth+'</a></li>')
					}
				}
			})
			
			function switchDateSearch(url, id, newSearchDate, newSearchMonth, flightType){
					if(newSearchDate < 10){
						newSearchDate = '0' + newSearchDate
					}
					if(newSearchMonth < 10){
						newSearchMonth = '0' + newSearchMonth
					}
					var ulParent = jQuery('#'+id+'').parent().closest('ul').prop('id')
					jQuery('#'+ulParent+' li').each(function(){
						jQuery(this).removeClass('atv-v')
					})
					jQuery('#'+id+'').parent().addClass('atv-v')
					var path = url.substring(0, url.indexOf('?'))
					var paramsPath = url.substring(url.indexOf('?')+1, url.length)
					var matchedParamsOutboundDate = paramsPath.match(/oday=[0-9][0-9]/)
					if(matchedParamsOutboundDate == null){
						matchedParamsOutboundDate = paramsPath.match(/oday=[0-9]/)
					}
					var matchedParamsOutboundMonth = paramsPath.match(/omonth=[0-9][0-9]/)
					var matchedParamsInboundDate = paramsPath.match(/iday=[0-9][0-9]/)
					if(matchedParamsInboundDate == null){
						matchedParamsInboundDate = paramsPath.match(/iday=[0-9]/)
					}
					var matchedParamsInboundMonth = paramsPath.match(/imonth=[0-9][0-9]/)
					var newParamsPath
					if(flightType == 0){
						newParamsPath = paramsPath.replace(matchedParamsOutboundDate, 'oday='+newSearchDate)
						newParamsPath = newParamsPath.replace(matchedParamsOutboundMonth, 'omonth='+newSearchMonth)
						
					} else if(flightType == 1){
						newParamsPath = paramsPath.replace(matchedParamsInboundDate, 'iday='+newSearchDate)
						newParamsPath = newParamsPath.replace(matchedParamsInboundMonth, 'imonth='+newSearchMonth)
						
					}
					var newPath = path +'?' + newParamsPath
					window.location = newPath
				}
				
				function replaceDay(numOfDay){
					var SUNDAY = 0
					var MONDAY = 1
					var TUESDAY = 2
					var WEDNESDAY = 3
					var THURSDAY = 4
					var FRIDAY = 5
					var SATURDAY = 6
					if(numOfDay == SUNDAY){
						return 'C.Nhật'
					} else if(numOfDay == MONDAY){
						return 'T.Hai'
					} else if(numOfDay == TUESDAY){
						return 'T.Ba'
					} else if(numOfDay == WEDNESDAY){
						return 'T.Tư'
					} else if(numOfDay == THURSDAY){
						return 'T.Năm'
					} else if(numOfDay == FRIDAY){
						return 'T.Sáu'
					} else if(numOfDay == SATURDAY){
						return 'T.Bảy'
					}
				}
			
			var array=new Array()
			function initArray(){
				for(i=1;i<=3;i++){
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
				if(flag){var count=0;
				for(var i=0;i<checkSearch.length;i++){
					if(checkSearch[i]==1){
					count++;
					}
				}
				if(count==3){
			
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
			function setLocalItem(item,action){
				$.ajax({
					async : false,
					parseRequest: false,
					type : "POST",
					contentType : "text/plain",
					url : "/flight/"+action,
					data:unescape(item),
					dataType : "json",
					success : function(jsonData) {
					}
				})
				
				
			}
			function setInbound(status,item){
				if(status){
				setLocalItem(item,'setInboundItem')
				}else{
				setLocalItem(item,'setOutboundItem')
				}
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

			$("input[name='flightType']").change(function(){
				if($(this).attr("id")=="oneWay"){
					$("#departureField").hide();
					$("#departureField select").val(0)
				} else {
					var iday = jQuery("#hdnIday").val()
					var imonth = jQuery("#hdnImonth").val()
					jQuery('#iday').val(iday)
					jQuery('#imonth').val(imonth)
					$("#departureField").show();
				}
			});
				var inboundCode='${session.parameters.departureCode+session.parameters.departureCode}'
					function appendQTEnd(numberOfTicket,totalPrice,pricePerAdult,pricePerChild,currencyCode,airlineCode,adults,kids,infants){
					var str=""
				   	var count=0 
					var infants=${session.parameters.infants}
					var adults=${session.parameters.adults}
					var kids=${session.parameters.kids}
					var infantPrice = (totalPrice - (pricePerAdult * adults) - (pricePerChild * kids))/infants;
					 str+='<table class="price-break">'
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
		                return str
			    }
				function appendDetail(flights,stopoverDurations){
					var str=""
					if(flights){	

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
                          str+='<p>Từ <b>'+flights[i].departureCity+'</b></p>'
                          str+='<p>Sân bay : <b>'+flights[i].departureAirport+'</b></p>'
                          str+='<p><b>'+flights[i].departureTime+'</b>, '+flights[i].departureDate+'</p>'
                      str+='</td>'
                      str+='<td width="200px">'
                          str+='<p>Tới <b>'+flights[i].arrivalCity+'</b></p>'
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
					}
				return str
				}	

				function listAirlineFound(prefix,airlineCode,carrier){
					
					
					$("#airlines").append('<li class="vnair"><input type="checkbox" value="mb" checked onClick="filterFlights('+prefix+',this)"  />'+' '+carrier+'<img src="/images/sm'+airlineCode+'.gif"/></li>')
				
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
							$("table[id^='"+prefix+"_']").each(
								function(i,el){
								$(el).show()
								}
							)
						  }
						}
					}else{
						for(i=0;i<selectedAirlines.length;i++){
							if(i!=0&&selectedAirlines[i]==0){
								$("table[id^='"+prefix+"_']").each(
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
				var sortedMapIn=new Object()
				function insertItemAndSortByPrice(isOutbound,item,index,value){
					
					if(isOutbound){
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
					}else{
						sortedMapIn[index]=value
						var keys2=keysbyValue(sortedMapIn)
					  if(keys2.length==1){
					      $("#return").prepend(item)
					  }else{
					      if(keys2[0]==index){
					    	  $("#return").prepend(item)
					      }else if(keys2[keys2.length-1]==index){
					        $("#return").append(item)
					      }else{
					          $(item).insertAfter("#"+keys2[keys2.indexOf(index)-1])
					      }
					  }
					}
					
						
				}
				function sortAirline(selector,isAsc){
				 $(selector).children("table").sort(function(a, b) {
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
					sortAirline("#return",true)
					
					}else{
					sortAirline("#departure",false)
					sortAirline("#return",false)
					}
				}
				function sortHour(selector,isAsc){
				 $(selector).children("table").sort(function(a, b) {
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
					sortHour("#return",true)
					
					}else{
					sortHour("#departure",false)
					sortHour("#return",false)
					}
				}
				function sortUL(selector,isAsc) {
   				 $(selector).children("table").sort(function(a, b) {
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
					sortUL("#return",true)
					
					}else{
					sortUL("#departure",false)
					sortUL("#return",false)
					}
				}
				function getFares(prefix,url) {
					$.ajax({
						async : true,
						type : "GET",
						contentType : "application/json; charset=utf-8",
						url : url,
						dataType : "json",
						success : function(jsonData) {
							checkSearch[prefix]=1
							var numberCustomers=${session.parameters.adults.toInteger()+session.parameters.kids.toInteger()+session.parameters.infants.toInteger()}
							if(typeof jsonData === 'undefined'||typeof jsonData[0]==='undefined'){
								array[prefix]=false
								checkFlightNotfound()
							}else{
								listAirlineFound(prefix,jsonData[0].airlineCode,jsonData[0].airlineName)
								array[prefix]=true
							for (i in jsonData) {
								
								var item=jsonData[i]
								var itemString=new String(JSON.stringify(jsonData[i]))	
								if(item.iflights&&item.iflights.length>0){
									var id=prefix+"_i"+i
									var image='sm'+item.iflights[0].airlineCode+'.gif'
								    var trid=id+'_tr'
								    var element='<table id="'+id+'" class="select" data-airline='+jsonData[0].airlineCode+' data-price='+item.price+' data-hour='+item.inboundDepartureTime+'>'
								    if(item.iflights.length>1){
									element='<table id="'+id+'" class="select two" data-airline='+jsonData[0].airlineCode+' data-price='+item.price+' data-hour='+item.inboundDepartureTime+'>'
									}else{
									element='<table id="'+id+'" class="select one" data-airline='+jsonData[0].airlineCode+' data-price='+item.price+' data-hour='+item.inboundDepartureTime+'>'
									}
								   
								    element+='<tr class="seclect-one" id="'+trid+'">'
								    element+='<td width="70px">'+'<img src="/images/'+image+'"/>'+'</td>'
								    element+='<td class="mb"><a >'+item.iflights[0].airlineCode+" "+item.iflights[0].flightNumber+'</a></td>'
								    element+='<td class="time"><a >'+item.inboundDepartureTime+' - '+item.inboundArrivalTime+'</a></td>'
								    element+='<td class="price"><a >'+item.pricePerAdult.format()+' '+item.currencyCode+'</a></td>'
								    element+='<td class="ct-list"  onclick="viewDetails(this)"><a>Chi tiết</a></td>'
								    element+='<td width="85px"><input type="radio" onClick="setInbound(true,\''+escape(itemString)+'\')"  name="iselect" value="select"><span><a href="javascript:void(0)" onclick="selectFlight(this, true, \''+escape(itemString)+'\')">Chọn</a></span></td>'
								    element+='</tr>'
								    element+='<tr class="seclect-two" style="display:none">'
								    element+='<td colspan="6" style="border:none">'
								    element+=appendDetail(item.iflights,item.inboundStopoverDurations)
								    element+=appendQTEnd(numberCustomers,item.price,item.pricePerAdult,item.pricePerChild,item.currencyCode,${session.parameters.adults.toInteger()},${session.parameters.kids.toInteger()},${session.parameters.infants.toInteger()})
								    element+='</td>'
								    element+='</tr>'
								    element+='</table>'
									insertItemAndSortByPrice(false,element,id,item.price)
								}
								if(item.oflights&&item.oflights.length>0){
								
								var id=prefix+"_o"+i
								var oimage='sm'+item.oflights[0].airlineCode+'.gif'
								var trid=id+'_tr'	
								 var element='<table id="'+id+'" class="select" data-airline='+jsonData[0].airlineCode+' data-price='+item.price+' data-hour='+item.outboundDepartureTime+'>'
								   if(item.oflights.length>1){
									element='<table id="'+id+'" class="select two" data-airline='+jsonData[0].airlineCode+' data-price='+item.price+' data-hour='+item.outboundDepartureTime+'>'
									}else{
									element='<table id="'+id+'" class="select one" data-airline='+jsonData[0].airlineCode+' data-price='+item.price+' data-hour='+item.outboundDepartureTime+'>'
									}
								element+='<tr class="seclect-one" id="'+trid+'">'
								element+='<td width="70px">'+'<img src="/images/'+oimage+'"/>'+'</td>'
								element+='<td class="mb"><a >'+item.oflights[0].airlineCode+" "+item.oflights[0].flightNumber+'</a></td>'
								element+='<td class="time"><a >'+item.outboundDepartureTime+' - '+item.outboundArrivalTime+'</a></td>'
								element+='<td class="price"><a >'+item.pricePerAdult.format()+' '+item.currencyCode+'</a></td>'
								element+='<td class="ct-list" onclick="viewDetails(this)"><a>Chi tiết</a></td>'
								element+='<td width="85px"><input type="radio" onClick="setInbound(false,\''+escape(itemString)+'\')" name="oselect" value="select"><span><a class="tour-button" href="javascript:void(0)" onclick="selectFlight(this, false, \''+escape(itemString)+'\')">Chọn</a></span></td>'
								element+='<tr class="seclect-two" style="display:none">'
								element+='<td colspan="6" style="border:none">'
								element+=appendDetail(item.oflights,item.outboundStopoverDurations)
								element+=appendQTEnd(numberCustomers,item.price,item.pricePerAdult,item.pricePerChild,item.currencyCode,${session.parameters.adults.toInteger()},${session.parameters.kids.toInteger()},${session.parameters.infants.toInteger()})
								element+='</td>'
								element+='</tr>'
								element+='</table>'
								insertItemAndSortByPrice(true,element,id,item.price)
								}
									
								
							
							}
							
						}
					     	var count=0;
							for(var i=0;i<checkSearch.length;i++){
								if(checkSearch[i]==1){
								count++;
								}
							}
							if(count==3){
							$("#loaddata").remove()
							}
						}
					});
				}
				
				function selectFlight(currentUrl, flightType, itemString){
					var closestInp = $(currentUrl).closest('td').find('input[type="radio"]')
					$(closestInp).prop('checked', 'checked')
					setInbound(flightType, itemString)
				}
				
				function viewDetails(el){
					$(el).toggleClass("active");
					$(el).closest('table').find('.seclect-two').toggle();
				}
				
				$(function(){
				   $(window).scroll(function () {
						var heightScroll = $("#results").height() + $("#results").offset().top - $(window).height();
						if ($(window).scrollTop() - 100 >= heightScroll) {
							$("div#next").hide()
							$("input[class=bottom]").show();  
					} else {
							$("div#next").show();
							$("input[class=bottom]").hide();  
								}
							});
				});
				
				$(document).ready(function() {
						var urlVietnamairlinesFares = "${createLink(controller:'flight', action:'getVietnamairlinesFares')}";
						var urlVietjetFares = "${createLink(controller:'flight', action:'getVietjetFares')}";
						var urlJetstarFares= "${createLink(controller:'flight', action:'getJetstarFares')}";
						getFares(1,urlVietnamairlinesFares)
						getFares(2,urlVietjetFares)
						getFares(3,urlJetstarFares)
						$("input[class=bottom]").hide();
				});
				
			</script>
</body>
</html>
