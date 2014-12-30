package com.airgroup.controller.cheapflight

import grails.converters.JSON

import java.math.MathContext;
import java.text.DecimalFormat

import org.codehaus.groovy.grails.web.json.JSONObject
import org.weceem.auth.CMSUser

import com.airgroup.domain.Airline
import com.airgroup.domain.Bill
import com.airgroup.domain.CheapFlight
import com.airgroup.domain.Location
import com.airgroup.domain.Order
import com.airgroup.domain.OrderPayment
import com.airgroup.domain.Payment
import com.airgroup.utils.Email

class CheapFlightController {
	static final String LOCATION_TYPE="city"
	static String a
	static String b
	DecimalFormat df = new DecimalFormat("#,###");
	private Email email=new Email()
	def flightService
	def flightInfoService
	def notFound = new JSONObject().put("JSON", "Not Found")
	def searchParams;
	def getLocalList={ session.parameters=params }
	def getInternationalList={ session.parameters=params }
	def outboundFare=null
	def inboundFare=null
	BigDecimal price=new BigDecimal(0)
	BigDecimal luggablePrice=new BigDecimal(0)
	BigDecimal totalPrice=new BigDecimal(0)
	def domesticCalendar = 
	{
		 a = params.departureCode
		 b = params.arrivalCode
		 render(view: "domesticCal")
	}
	
    def listCheapFlightDeparture = {
		def lstCheapFlight = CheapFlight.executeQuery("SELECT departure, inbound_date, outbound_date, arrival, min(price), id, flight_number, trip_type, airline, flight_number "
												  	  +"FROM CheapFlight WHERE departure = '"+ a + "' AND arrival = '" + b + "' GROUP BY date(outbound_date)")
		def list_cheap_flight = new ArrayList<CheapFlight>()
		for(CheapFlight cf : lstCheapFlight){
			CheapFlight newCheapFlight = new CheapFlight()
			newCheapFlight.departure = Location.findByLocation_typeAndCode(LOCATION_TYPE, cf[0]).getName()
			newCheapFlight.inbound_date = cf[1]
			newCheapFlight.outbound_date = cf[2]
			newCheapFlight.arrival = Location.findByLocation_typeAndCode(LOCATION_TYPE, cf[3]).getName()
			newCheapFlight.price = cf[4]
			newCheapFlight.id = cf[5]
			newCheapFlight.flight_number = cf[6]
			newCheapFlight.trip_type = cf[7]
			newCheapFlight.airline = cf[8]
			newCheapFlight.flight_number = cf[9]
			list_cheap_flight.add(newCheapFlight)
		}
		render list_cheap_flight as JSON
	}
	
	def listCheapFlightArrival = {
		def lstCheapFlight = CheapFlight.executeQuery("SELECT departure, inbound_date, outbound_date, arrival, min(price), id, flight_number, trip_type, airline, flight_number "
													 +"FROM CheapFlight WHERE departure = '"+ b + "' AND arrival = '" + a + "' GROUP BY date(outbound_date)")
		def list_cheap_flight = new ArrayList<CheapFlight>()
		for(CheapFlight cf : lstCheapFlight){
			CheapFlight newCheapFlight = new CheapFlight()
			newCheapFlight.departure = Location.findByLocation_typeAndCode(LOCATION_TYPE, cf[0]).getName()
			newCheapFlight.inbound_date = cf[1]
			newCheapFlight.outbound_date = cf[2]
			newCheapFlight.arrival = Location.findByLocation_typeAndCode(LOCATION_TYPE, cf[3]).getName()
			newCheapFlight.price = cf[4]
			newCheapFlight.id = cf[5]
			newCheapFlight.flight_number = cf[6]
			newCheapFlight.trip_type = cf[7]
			newCheapFlight.airline = cf[8]
			newCheapFlight.flight_number = cf[9]
			list_cheap_flight.add(newCheapFlight)
		}
		render list_cheap_flight as JSON
	}
	def switchSearchPage={
		
				String departureCode=params.departureCode
				String arrivalCode=params.arrivalCode
				String departureCity=departureCode.substring(0,departureCode.lastIndexOf("("))
				String arrivalCity=arrivalCode.substring(0,arrivalCode.lastIndexOf("("))
				if(params.kids.toInteger() > 2){
					params.kids = 2
				}
				if(params.infants.toInteger() > 1){
					params.infants = 1
				}
				params.departureCity=departureCity
				params.arrivalCity=arrivalCity
				String departCodeShortcut=flightService.getCode(departureCode)
				String arrivalCodeShorcut=flightService.getCode(arrivalCode)
				Location departure=Location.findByCode(departCodeShortcut)
				Location arrival=Location.findByCode(arrivalCodeShorcut)
				if(departure!=null&&arrival!=null){
					if(departure.country_code!='VN'||arrival.country_code!='VN') {
						params.isDomestic=false
						redirect(action: "getInternationalList", params: params)
					}else{
						params.isDomestic=true
						redirect(action: "getLocalList", params: params)
					}
				}else if(departure==null && arrival!=null){
					if(arrival!=null&&arrival.country_code=='VN'){
						params.isDomestic=true
						redirect(action: "getLocalList", params: params)
					}else
					{
						params.isDomestic=false
						redirect(action: "getInternationalList", params: params)
					}
		
				}
				[departureCode : departureCode, arrivalCode : arrivalCode,
							iday : params.iday, imonth : params.imonth, oday : params.oday, omonth : params.omonth,
							adults : params.adults, kids : params.kids, infants : params.infants,
							phoneNumber : params.phoneNumber, isDomestic: params.isDomestic]
	}
	def flyinfo={
		JSON.use("deep")
		//Fare price out-bound
		def pricePerAdultOutbound
		def pricePerChildOutbound
		def airlineNameOutbound
		def airlineCodeOutbound
		def totalPriceOutbound
		//Fare price in-bound
		def pricePerAdultInbound
		def pricePerChildInbound
		def airlineNameInbound
		def airlineCodeInbound
		def totalPriceInbound
		//Fare type
		def fareType
		
		if(session.parameters.iday.toString()=='0'||session.parameters.imonth.toString()=='0'){
			if(session.outboundItem==null){
				flightService.separateLocalInboundOutboundFares(session.parameters)

				if(flightService.getLocalOutboundFare().size()>0){
					outboundFare=flightService.getOutboundRandom()
					session.outboundItem=(outboundFare as JSON)
				}
			}
			session.inboundItem=null
		}else{

			if(session.inboundItem==null&&session.outboundItem==null){
				flightService.separateLocalInboundOutboundFares(session.parameters)
				flightService.separateLocalInboundOutboundFares(session.parameters)
				if(flightService.getLocalOutboundFare().size()>0){
					outboundFare=flightService.getOutboundRandom()
					inboundFare=flightService.getInboundRandom()
					session.outboundItem=(outboundFare as JSON)
					session.inboundItem=(inboundFare as JSON)
				}
			}else if(session.outboundItem!=null&&session.inboundItem==null){
				flightService.separateLocalInboundOutboundFares(session.parameters)
				inboundFare=flightService.getInboundRandom()
				session.inboundItem=(inboundFare as JSON)
			}else if(session.inboundItem!=null && session.outboundItem==null){
				flightService.separateLocalInboundOutboundFares(session.parameters)
				outboundFare=flightService.getOutboundRandom()
				session.outboundItem=(outboundFare as JSON)
			}else{
				inboundFare=session.inboundItem
				outboundFare=session.outboundItem
			}
		}
		
		if(session.outboundItem!=null){
			def outboundArray=JSON.parse(session.outboundItem.toString())
			price=new BigDecimal(outboundArray.price.toString())
			pricePerAdultOutbound = outboundArray.pricePerAdult
			pricePerChildOutbound = outboundArray.pricePerChild
			airlineNameOutbound = outboundArray.airlineName
			airlineCodeOutbound = outboundArray.airlineCode
			totalPriceOutbound = outboundArray.price
			fareType = outboundArray.fareType
			if(session.inboundItem!=null){
				def inboundArray=JSON.parse(session.inboundItem.toString())
				price.add(new BigDecimal(inboundArray.price.toString()))
				pricePerAdultInbound = inboundArray.pricePerAdult
				pricePerChildInbound = inboundArray.pricePerChild
				airlineNameInbound = inboundArray.airlineName
				airlineCodeInbound = inboundArray.airlineCode
				totalPriceInbound = inboundArray.price
				fareType = inboundArray.fareType
			}
		}
		session.totalPrice=price
		def users = CMSUser.findAllByCallCenterStatusAndStatus((short)1,(short)1)
		render(view:"flyinfo",model:[price:price,users: users, phoneNumber: session.parameters.phoneNumber,
					pricePerAdultOutbound : pricePerAdultOutbound, pricePerChildOutbound : pricePerChildOutbound, airlineNameOutbound : airlineNameOutbound, airlineCodeOutbound : airlineCodeOutbound,
					pricePerAdultInbound : pricePerAdultInbound, pricePerChildInbound : pricePerChildInbound, airlineNameInbound : airlineNameInbound, airlineCodeInbound : airlineCodeInbound,
					totalPriceOutbound : totalPriceOutbound, totalPriceInbound : totalPriceInbound,
					fareType : fareType])
	}
	def flyInfoFromCalendar = {
		session.parameters=params
		//Outbound
		def oDepartureCode
		def oArrivalCode
		def oFlightNumber
		def oday
		def omonth
		def airlineCodeOutBound
		def priceOutbound
		def oOutBoundDate
		def oInBoundDate
		
		//Inbound
		def iFlightNumber
		def iDepartureCode
		def iArrivalCode
		def airlineCodeInbound
		def iday = 0
		def imonth = 0
		def priceInbound
		def iOutBoundDate
		def iInBoundDate
		
		def _fareType
		def isDomestic

		if(params.airlineCode != null && !"".equals(params.airlineCode)){
			if("BL".equals(params.airlineCode) || "VJ".equals(params.airlineCode) || "VN".equals(params.airlineCode)) {
				isDomestic = 'true'
			} else {
				isDomestic = 'false'
			}

			if(!params._fareType.toString().contains("["))
			{
				_fareType = params._fareType
			}else
			{
				_fareType = params._fareType[1]
			}
			
					
				if(!params.oday.toString().contains("[")){
					oDepartureCode = params.oDepartureCode
					oArrivalCode = params.oArrivalCode
					oFlightNumber = params.oFlightNumber
					oOutBoundDate = params.oOutBoundDate
					oInBoundDate = params.oInBoundDate
					priceOutbound = params.priceOutbound
					airlineCodeOutBound = params.airlineCodeOutBound
					oday = params.oday
					omonth = params.omonth
				}else{
					oDepartureCode = params.oDepartureCode[0]
					oArrivalCode = params.oArrivalCode[0]
					oFlightNumber = params.oFlightNumber[0]
					oOutBoundDate = params.oOutBoundDate[0]
					oInBoundDate = params.oInBoundDate[0]
					priceOutbound = params.priceOutbound[0]
					airlineCodeOutBound = params.airlineCodeOutBound[0]
					oday = params.oday[0]
					omonth = params.omonth[0]
				}
			
			
			if(params.iday!=null){
				if(!params.iday.toString().contains("[")){
					iDepartureCode = params.iDepartureCode
					iArrivalCode = params.iArrivalCode
					iFlightNumber = params.iFlightNumber
					iOutBoundDate = params.iOutBoundDate
					iInBoundDate = params.iInBoundDate
					priceInbound = params.priceInbound
					airlineCodeInbound = params.airlineCodeInbound
					iday = params.iday
					imonth = params.imonth
				}else{
					iDepartureCode = params.iDepartureCode[0]
					iArrivalCode = params.iArrivalCode[0]
					iFlightNumber = params.iFlightNumber[0]
					iOutBoundDate = params.iOutBoundDate[0]
					iInBoundDate = params.iInBoundDate[0]
					priceInbound = params.priceInbound[0]
					airlineCodeInbound = params.airlineCodeInbound[0]
					iday = params.iday[0]
					imonth = params.imonth[0]
				}
			}
			else
			{
				iDepartureCode = ""
				iArrivalCode = ""
				iFlightNumber = ""
				iOutBoundDate = ""
				iInBoundDate = ""
				priceInbound = 0
				airlineCodeInbound = ""
				iday = 0
				imonth = 0
			}
		}
		
		[oDepartureCode:oDepartureCode, oArrivalCode:oArrivalCode, iDepartureCode:iDepartureCode,iArrivalCode:iArrivalCode, adults:1, kids:0, infants:0,
		 oday:oday, omonth:omonth, iday:iday, imonth:imonth, fareType:_fareType, priceOutbound:priceOutbound, priceInbound:priceInbound,
		 airlineCodeOutBound:airlineCodeOutBound, airlineCodeInbound:airlineCodeInbound, isDomestic:isDomestic,
		 oFlightNumber:oFlightNumber, iFlightNumber:iFlightNumber, oOutBoundDate:oOutBoundDate, oInBoundDate:oInBoundDate, iOutBoundDate:iOutBoundDate,
		 iInBoundDate:iInBoundDate,departureCode:oDepartureCode,arrivalCode:oArrivalCode]
	}
	//Clone for Calendar
	def localinfopaymentCalendar={
		session.parameters.departureCode = params.departureCode
		session.parameters.arrivalCode = params.arrivalCode
		session.parameters.phoneNumber = params.phoneNumber
		session.localCustomerFare=params
		if(session.parameters.iInBoundDate.toString().contains("[")){
			session.parameters.iInBoundDate = session.parameters.iInBoundDate[0]
			session.parameters.iOutBoundDate = session.parameters.iOutBoundDate[0]
			session.parameters.priceInbound = session.parameters.priceInbound[0]
		}
		JSON.use("deep")
		def inbound=null
		def outbound=null
		def outboundArray=null
		def inboundArray = null
		def deparTime = null
		def deparDate = null
		def arrTime = null
		def arrDate = null
		if(session.parameters.oFlightNumber!=null){
			deparTime = session.parameters.oOutBoundDate
			deparDate = session.parameters.oOutBoundDate
			if(params.outboundLuggable == null){
				params.outboundLuggable = 0;
			}
			if(params.inboundLuggable == null){
				params.inboundLuggable = 0;
			}
			price=new BigDecimal((session.parameters.priceOutbound.toString()))
			if(session.parameters.iFlightNumber!=null){
				def getPriceInbound = new BigDecimal(session.parameters.priceInbound.toString())
				def getPriceInboundLuggable = new BigDecimal(params.inboundLuggable.toString())
				def getPriceOutboundLuggable = new BigDecimal(params.outboundLuggable.toString())
				price=price.add(getPriceInbound + getPriceInboundLuggable + getPriceOutboundLuggable)
				arrTime = session.parameters.iInBoundDate
				arrDate = session.parameters.iInBoundDate
			}
		}
		try{
			luggablePrice=new BigDecimal(0)
			if(session.parameters.outboundLuggable!=null){
				String[] outboundLuggablePrice = session.parameters.outboundLuggable.toString().replace("[", "").replace("]", "").split(",")
				for(int i=0; i< outboundLuggablePrice.length ; i++){
					luggablePrice=luggablePrice.add(new BigDecimal(outboundLuggablePrice[i].trim()))
				}
			}

			if(session.parameters.inboundLuggable!=null){
				String[] inboundLuggablePrice = session.parameters.inboundLuggable.toString().replace("[", "").replace("]", "").split(",")
				for(int i=0; i< inboundLuggablePrice.length ; i++){
					luggablePrice=luggablePrice.add(new BigDecimal(inboundLuggablePrice[i].trim()))
				}
			}
		}catch(Exception e){
			luggablePrice=new BigDecimal(0)
		}
		totalPrice=price.add(luggablePrice)
		session.totalPrice=totalPrice
		List<Payment> ls=Payment.executeQuery("from Payment")
		Airline oairline=Airline.findByCode(params.airlineCodeOutBound)
		Airline iairline = null;
		if (session.inboundItem!=null)
			iairline=Airline.findByCode(params.airlineCodeInBound)
		String customerName=params.fullname.toString()
		def users = CMSUser.findAllByCallCenterStatusAndStatus((short)1,(short)1)

		params.paymentId=1
		params.billStatus=(short)0
		session.parameters.adults = 1+""
		session.parameters.kids = 0+""
		session.parameters.infants = 0+""
		def order=flightInfoService.insertCheapFlightInfo(params, session,true)
		Long orderId=order.id
		session.localorder=order

		render(view:"localinfopayment", model:[customerName:customerName,price:price,luggablePrice:luggablePrice,totalPrice:totalPrice,payments:ls,users: users,oairline:oairline, iairline:iairline,
					deparTime:deparTime, deparDate:deparDate, arrTime:arrTime, arrDate:arrDate])
	}
	def localpayment={
		String phoneNumber = session.parameters.phoneNumber
		String revPhone = params.revphone
		String revPerson=params.revperson
		String revAddress=params.revaddress
		String revCity=params.revcity

		Long paymentId=params.paymentType.toLong()


		if(paymentId==3){
			session.parameters.phoneNumber = revPhone
			phoneNumber = revPhone

			OrderPayment op=new OrderPayment();
			op.setPaymentId(paymentId)
			op.setOrderId(session.localorder.id.toLong())
			op.setPhoneNumber(revPhone)
			op.setPerson(revPerson)
			op.setAddress(revAddress)
			op.setCity(revCity)
			op.save(flush:true)

		}
		boolean bill=params.bill.toBoolean()
		String companyName=null
		String address=null
		String taxCode=null
		String recieverAddress=null
		if(bill){
			companyName=params.companyName.toString()
			address=params.address.toString()
			taxCode=params.taxCode.toString()
			recieverAddress=params.recieverAddress.toString()
		}

		def params=session.localCustomerFare
		params.paymentId=paymentId
		params.billStatus=bill?(short)1:(short)0
		params.orderId=session.localorder.id
		Order order=Order.findById(session.localorder.id.toLong())
		order.payment=Payment.findById(paymentId)
		order.billStatus=bill?(short)1:(short)0
		order.save(flush:true)

		totalPrice=session.totalPrice.toBigDecimal()
		//Send Email
		JSON.use("deep")
		def inbound=null
		def outbound=null
		String customerName=params.fullname.toString()
		String orderCode=params.orderId.toString()
		String orderStatus="${message(code: 'email.send.orderStatus')}"
		String tp=totalPrice.toString()
		String fareType=(session.parameters.oday==null||session.parameters.iday==null)?"${message(code: 'email.send.oneway')}":"${message(code: 'email.send.twoway')}"
		StringBuilder passengers=new StringBuilder("")
		passengers.append("<table>")
		for(int i=0;i<session.parameters.adults.toInteger();i++){
			String name=params['a_'+i+'_name'].toString()
			Short gender=new Short(params['cbx_a_'+i].toString())
			passengers.append("<tr>")
			passengers.append("<td style=\"padding:5px 0\">")
			passengers.append("<label  style=\" font-weight:bold\">")
			passengers.append(gender==0?"${message(code: 'email.mr')}:":"${message(code: 'email.ms')}:")
			passengers.append("</label>")
			passengers.append("</td>")
			passengers.append(" <td class=\"mdh\" style=\"padding:5px 0\"><a href=\"\" style=\" font-size:20px; color:#0E70A5; font-weight:bold; text-decoration: none;\">")
			passengers.append(name)
			passengers.append("</a></td>")
			passengers.append("</tr>")

		}
		for(int i=0;i<session.parameters.kids.toInteger();i++){
			String name=params['k_'+i+'_name'].toString()
			Short gender=new Short(params['cbx_k_'+i].toString())
			passengers.append("<tr>")
			passengers.append("<td style=\"padding:5px 0\">")
			passengers.append("<label  style=\" font-weight:bold\">")
			passengers.append(gender==2?"${message(code: 'email.boy')}:":"${message(code: 'email.girl')}:")
			passengers.append("</label>")
			passengers.append(" <td class=\"mdh\" style=\"padding:5px 0\"><a href=\"\" style=\" font-size:20px; color:#0E70A5; font-weight:bold; text-decoration: none;\">")
			passengers.append(name)
			passengers.append("</a></td>")
			passengers.append("</tr>")

		}
		for(int i=0;i<session.parameters.infants.toInteger();i++){
			String name=params['i_'+i+'_name'].toString()
			Short gender=new Short(params['cbx_i_'+i].toString())
			passengers.append("<tr>")
			passengers.append("<td style=\"padding:5px 0\">")
			passengers.append("<label  style=\" font-weight:bold\">")
			passengers.append(gender==4?"${message(code: 'email.infantboy')}:":"${message(code: 'email.infantgirl')}:")
			passengers.append("</label>")
			passengers.append(" <td class=\"mdh\" style=\"padding:5px 0\"><a href=\"\" style=\" font-size:20px; color:#0E70A5; font-weight:bold; text-decoration: none;\">")
			passengers.append(name)
			passengers.append("</a></td>")
			passengers.append("</tr>")

		}
		passengers.append("</table>")
		String outboundFromLocation=""
		String outboundDuration=""
		List outboundFlights=null
		StringBuilder flightDetails=new StringBuilder("")
		if(session.parameters.oFlightNumber!=null){
			outboundFromLocation=session.parameters.oDepartureCode.toString()
			outboundDuration=session.parameters.iOutBoundDate.toString()
//			outboundFlights=session.parameters.oflights.toList()
		}
		String inboundFromLocation=""
		String inboundDuration=""
		List inboundFlights=null
		if(session.parameters.iFlightNumber!=null){
			inboundFromLocation=session.parameters.arrivalCode.toString()
			inboundDuration=session.parameters.oInBoundDate.toString()

		}
		String paymentName="N/A"
		String paymentDescription="N/A"
		Payment payment=order.payment
		if(payment!=null){
			paymentName=payment.paymentName
			paymentDescription=payment.description
		}
		if(bill){
			Bill b=new Bill()
			b.order=order
			b.companyAddress=address
			b.companyName=companyName
			b.address=recieverAddress
			b.taxSerial=taxCode
			b.save(flush:true)
		}
		if(params.email.toString()!=""){
			email.send("${message(code: 'email.mail1.title')}",
					"nghiata87@gmail.com",
					params.email.toString(),
					customerName,
					orderCode,
					orderStatus,
					tp,
					fareType,
					passengers.toString(),
					flightDetails.toString(),
					paymentName,
					paymentDescription,
					inboundFromLocation,
					inboundDuration,
					outboundFromLocation,
					outboundDuration,
					outboundFlights,
					inboundFlights,
					outbound,
					inbound)
		}

		//		ends
//		def oflight=outboundFlights[0]
//		def iflight=inboundFlights==null?null:inboundFlights[0]
		Airline oairline=Airline.findByCode(session.parameters.airlineCodeOutBound)
		Airline iairline=null
		if(inbound!=null){
			iairline=Airline.findByCode(session.parameters.airlineCodeInBound)
		}
		def users = CMSUser.findAllByCallCenterStatusAndStatus((short)1,(short)1)

		[users: users,orderId:order.id,payment:payment,status:"${message(code: 'email.orderStatus')}",email:params.email.toString(), phoneNumber:phoneNumber,totalPrice:(df.format(new Double(tp))+" VND"),customerName:customerName,oairline:oairline,iairline:iairline]
	}
}
