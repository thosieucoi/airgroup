package com.airgroup.controller.flight
import grails.converters.JSON

import java.text.DateFormat
import java.text.DecimalFormat
import java.text.SimpleDateFormat

import org.codehaus.groovy.grails.web.json.JSONObject
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import org.weceem.auth.CMSUser

import com.airgroup.domain.Airline
import com.airgroup.domain.Bill
import com.airgroup.domain.CustomerInfo
import com.airgroup.domain.Feedback
import com.airgroup.domain.Location
import com.airgroup.domain.Order
import com.airgroup.domain.OrderPayment
import com.airgroup.domain.Payment
import com.airgroup.utils.Email

class FlightController {
	DecimalFormat df = new DecimalFormat("#,###");
	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat.forPattern("yyyyMMdd");
	private static final DateTimeFormatter DOB_FORMAT=DateTimeFormat.forPattern("dd-MM-yyyy")
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
	def search={
		def users = CMSUser.findAllByCallCenterStatusAndStatus((short)1,(short)1)

		render view:"search", model : [users: users]
	}
	
	def flyInfoFromCalendar = {
		
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
		
		def fareType
		def isDomestic

		if(params.airlineCode != null && !"".equals(params.airlineCode)){
			if("BL".equals(params.airlineCode) || "VJ".equals(params.airlineCode) || "VN".equals(params.airlineCode)) {
				isDomestic = 'true'
			} else {
				isDomestic = 'false'
			}
			
			fareType = params.fareType
			
			oDepartureCode = params.oDepartureCode
			oArrivalCode = params.oArrivalCode
			oFlightNumber = params.oFlightNumber
			oOutBoundDate = params.oOutBoundDate
			oInBoundDate = params.oInBoundDate
			priceOutbound = params.priceOutbound
			airlineCodeOutBound = params.airlineCodeOutBound
			oday = params.oday
			omonth = params.omonth

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
				fareType = "oneway"
			}
		}
		
		[oDepartureCode:oDepartureCode, oArrivalCode:oArrivalCode, iDepartureCode:iDepartureCode,iArrivalCode:iArrivalCode, adults:1, kids:0, infants:0,
		 oday:oday, omonth:omonth, iday:iday, imonth:imonth, fareType:fareType, priceOutbound:priceOutbound, priceInbound:priceInbound,
		 airlineCodeOutBound:airlineCodeOutBound, airlineCodeInbound:airlineCodeInbound, isDomestic:isDomestic, 
		 oFlightNumber:oFlightNumber, iFlightNumber:iFlightNumber, oOutBoundDate:oOutBoundDate, oInBoundDate:oInBoundDate, iOutBoundDate:iOutBoundDate,
		 iInBoundDate:iInBoundDate,departureCode:oDepartureCode,arrivalCode:oArrivalCode]
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
	def localinfopayment={
		session.parameters.phoneNumber = params.phoneNumber
		session.localCustomerFare=params
		JSON.use("deep")
		def inbound=null
		def outbound=null
		def outboundArray=null
		def inboundArray = null
		def deparTime = null
		def deparDate = null
		def arrTime = null
		def arrDate = null
		if(session.outboundItem!=null){
			outboundArray=JSON.parse(session.outboundItem.toString())
			deparTime = outboundArray.oflights[0].departureTime
			deparDate = outboundArray.oflights[0].departureDate
			price=new BigDecimal(outboundArray.price.toString())
			if(session.inboundItem!=null){

				inboundArray=JSON.parse(session.inboundItem.toString())
				price=price.add(new BigDecimal(inboundArray.price.toString()))
				int sizeOfInbound = inboundArray.iflights.size()
				arrTime = inboundArray.iflights[0].arrivalTime
				arrDate = inboundArray.iflights[0].arrivalDate
			}
		}
		try{
			luggablePrice=new BigDecimal(0)
			if(params.outboundLuggable!=null){
				String[] outboundLuggablePrice = params.outboundLuggable.toString().replace("[", "").replace("]", "").split(",")
				for(int i=0; i< outboundLuggablePrice.length ; i++){
					luggablePrice=luggablePrice.add(new BigDecimal(outboundLuggablePrice[i].trim()))
				}
			}

			if(params.inboundLuggable!=null){
				String[] inboundLuggablePrice = params.inboundLuggable.toString().replace("[", "").replace("]", "").split(",")
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
		Airline oairline=Airline.findByCode(outboundArray.airlineCode)
		Airline iairline = null;
		if (session.inboundItem!=null)
			iairline=Airline.findByCode(inboundArray.airlineCode)
		String customerName=params.fullname.toString()
		def users = CMSUser.findAllByCallCenterStatusAndStatus((short)1,(short)1)

		params.paymentId=1
		params.billStatus=(short)0
		def order=flightInfoService.insertFlightInfo(params, session,true)
		Long orderId=order.id
		session.localorder=order

		render(view:"localinfopayment", model:[customerName:customerName,price:price,luggablePrice:luggablePrice,totalPrice:totalPrice,payments:ls,users: users,oairline:oairline, iairline:iairline,
					deparTime:deparTime, deparDate:deparDate, arrTime:arrTime, arrDate:arrDate])
	}
	def internationalinfopayment={
		session.parameters.phoneNumber = params.phoneNumber
		session.interCustomerFare=params
		JSON.use("deep")
		def deparTime = null
		def deparDate = null
		def arrTime = null
		def arrDate = null
		def currentItemArray=null
		if(session.currentItem!=null){
			currentItemArray=JSON.parse(session.currentItem.toString())
			price=new BigDecimal(currentItemArray.price.toString())
			deparTime = currentItemArray.oflights[0].departureTime
			deparDate = currentItemArray.oflights[0].departureDate
			int sizeOfInbound = currentItemArray.iflights.size()
			if (sizeOfInbound > 0){
				arrTime = currentItemArray.iflights[0].arrivalTime
				arrDate = currentItemArray.iflights[0].arrivalDate
			}
		}
		def inbound=currentItemArray
		def outbound=currentItemArray
		try{
			luggablePrice=new BigDecimal(0)
			if(params.outboundLuggable!=null){
				String[] outboundLuggablePrice = params.outboundLuggable.toString().replace("[", "").replace("]", "").split(",")
				for(int i=0; i< outboundLuggablePrice.length ; i++){
					luggablePrice=luggablePrice.add(new BigDecimal(outboundLuggablePrice[i].trim()))
				}
			}

			if(params.inboundLuggable!=null){
				String[] inboundLuggablePrice = params.inboundLuggable.toString().split(",")
				for(int i=0; i< inboundLuggablePrice.length ; i++){
					luggablePrice=luggablePrice.add(new BigDecimal(inboundLuggablePrice[i].trim()))
				}
			}
		}catch(Exception e){
			luggablePrice=new BigDecimal(0)
		}
		totalPrice=price.add(luggablePrice)
		session.totalPrice=totalPrice
		Airline airline=Airline.findByCode(currentItemArray.airlineCode)
		List<Payment> ls=Payment.executeQuery("from Payment")

		String customerName=params.fullname.toString()
		def users = CMSUser.findAllByCallCenterStatusAndStatus((short)1,(short)1)

		params.paymentId=1
		params.billStatus=(short)0
		def order=flightInfoService.insertFlightInfo(params, session,false)
		Long orderId=order.id
		session.interorder=order

		render(view:"internationalinfopayment", model:[customerName:customerName,price:price,luggablePrice:luggablePrice,totalPrice:totalPrice,payments:ls,users: users,airline:airline,
					deparTime:deparTime,deparDate:deparDate, arrTime:arrTime, arrDate:arrDate ])
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
		String fareType=(session.parameters.oday.toInteger()==0||session.parameters.iday.toInteger()==0)?"${message(code: 'email.send.oneway')}":"${message(code: 'email.send.twoway')}"
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
		if(session.outboundItem!=null){
			def arr=JSON.parse(session.outboundItem.toString())
			outbound=arr
			outboundFromLocation=session.parameters.departureCode.toString()
			outboundDuration=arr.outboundDuration.toString()
			outboundFlights=arr.oflights.toList()
		}
		String inboundFromLocation=""
		String inboundDuration=""
		List inboundFlights=null
		if(session.inboundItem!=null){
			def arr=JSON.parse(session.inboundItem.toString())
			inbound=arr
			inboundFromLocation=session.parameters.arrivalCode.toString()
			inboundDuration=arr.inboundDuration.toString()
			inboundFlights=arr.iflights.toList()

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
					"service@ahotua.vn",
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
		def oflight=outboundFlights[0]
		def iflight=inboundFlights==null?null:inboundFlights[0]
		Airline oairline=Airline.findByCode(outbound.airlineCode)
		Airline iairline=null
		if(inbound!=null){
			iairline=Airline.findByCode(inbound.airlineCode)
		}
		def users = CMSUser.findAllByCallCenterStatusAndStatus((short)1,(short)1)

		[users: users,orderId:order.id,payment:payment,status:"${message(code: 'email.orderStatus')}",email:params.email.toString(), phoneNumber:phoneNumber,totalPrice:(df.format(new Double(tp))+" VND"),customerName:customerName,oairline:oairline,iairline:iairline]
	}
	def interpayment={
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
			op.setOrderId(session.interorder.id.toLong())
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

		def params=session.interCustomerFare
		params.paymentId=paymentId
		params.billStatus=bill?(short)1:(short)0
		params.orderId=session.interorder.id
		Order order=Order.findById(session.interorder.id.toLong())
		order.payment=Payment.findById(paymentId)
		order.billStatus=bill?(short)1:(short)0
		order.save(flush:true)

		totalPrice=session.totalPrice.toBigDecimal()
		//Email
		JSON.use("deep")
		def inbound=null
		def outbound=null
		String customerName=params.fullname.toString()
		def currentItemArray=JSON.parse(session.currentItem.toString())
		Payment payment=null
		if(currentItemArray!=null){
			String orderCode=params.orderId.toString()
			String orderStatus="${message(code: 'email.send.orderStatus')}"
			String tp=totalPrice.toString()
			String fareType=(session.parameters.oday.toInteger()==0||session.parameters.iday.toInteger()==0)?"${message(code: 'email.send.oneway')}":"${message(code: 'email.send.twoway')}"
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
			if(currentItemArray.oflights!=null){
				outboundFromLocation=session.parameters.departureCode.toString()
				outboundDuration=currentItemArray.outboundDuration.toString()
				outboundFlights=currentItemArray.oflights.toList()
				outbound=currentItemArray
			}
			String inboundFromLocation=""
			String inboundDuration=""
			List inboundFlights=null
			if(currentItemArray.iflights!=null){
				inboundFromLocation=session.parameters.arrivalCode.toString()
				inboundDuration=currentItemArray.inboundDuration.toString()
				inboundFlights=currentItemArray.iflights.toList()
				inbound=currentItemArray
			}
			String paymentName="N/A"
			String paymentDescription="N/A"
			payment=order.payment
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
						"service@ahotua.vn",
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
		}
		Airline oairline=Airline.findByCode(currentItemArray.airlineCode)
		def users = CMSUser.findAllByCallCenterStatusAndStatus((short)1,(short)1)
		[users: users,orderId:order.id,payment:payment,status:"${message(code: 'email.orderStatus')}",email:params.email.toString(), phoneNumber:phoneNumber,totalPrice:(df.format(new Double(totalPrice))+" VND"),customerName:customerName,airline:oairline]
	}

	def internationalflightinfo={
		JSON.use("deep")
		def pricePerAdult = new BigDecimal(0)
		def pricePerChild = new BigDecimal(0)
		if(session.currentItem!=null){
			def currentItemArray=JSON.parse(session.currentItem.toString())
			price=new BigDecimal(currentItemArray.price.toString())
			pricePerAdult = new BigDecimal(currentItemArray.pricePerAdult.toString())
			pricePerChild = new BigDecimal(currentItemArray.pricePerChild.toString())
		}
		session.totalPrice=price
		def users = CMSUser.findAllByCallCenterStatusAndStatus((short)1,(short)1)
		render (view:"internationalflightinfo",model:[price:price,pricePerAdult : pricePerAdult,pricePerChild : pricePerChild,users: users])
	}
	def setcurrentitem={
		JSON.use("deep")
		session.currentItem=request.JSON
	}
	def setInboundItem={
		JSON.use("deep")
		session.inboundItem=request.JSON
	}
	def setOutboundItem={
		JSON.use("deep")
		session.outboundItem=request.JSON
	}
	def couldNotFound={

		if(session.parameters.phoneNumber!=""){
			def customerInfo=new CustomerInfo()
			def params=session.parameters
			if(params.imonth.toInteger()!=0&&params.iday.toInteger()!=0){

				customerInfo.departure=params.departureCode.toString()
				customerInfo.arrival=params.arrivalCode.toString()
				customerInfo.outboundDate=DATE_TIME_FORMATTER.parseDateTime(Calendar.getInstance().get(Calendar.YEAR)+params.omonth+params.oday).toDate()
				customerInfo.inboundDate=DATE_TIME_FORMATTER.parseDateTime(Calendar.getInstance().get(Calendar.YEAR)+params.imonth+params.iday).toDate()
				customerInfo.phoneNumber=params.phoneNumber
				assert customerInfo.save(flush:true)
			}else{
				customerInfo.departure=params.departureCode.toString()
				customerInfo.arrival=params.arrivalCode.toString()
				customerInfo.outboundDate=DATE_TIME_FORMATTER.parseDateTime(Calendar.getInstance().get(Calendar.YEAR)+params.omonth+params.oday).toDate()
				customerInfo.phoneNumber=params.phoneNumber
				assert customerInfo.save(flush:true)
			}
		}
	}

	def switchSearchPage={

		String departureCode=params.departureCode
		String arrivalCode=params.arrivalCode
		String departureCity=departureCode.substring(0,departureCode.lastIndexOf("("))
		String arrivalCity=arrivalCode.substring(0,arrivalCode.lastIndexOf("("))
		/*if(params.adults.toInteger()>6){
			params.adults=6
		}*/
		/*if(params.kids.toInteger() > 2){
			params.kids = 2
		}
		if(params.infants.toInteger() > 1){
			params.infants = 1
		}*/
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

	def getEdreamFares={
		searchParams=session.parameters
		def responseData=flightService.getEdreamsFares(searchParams);
		def result=responseData?responseData:notFound
		render result as JSON
	}
	def getQatarFares={
		searchParams=session.parameters
		def responseData=flightService.getQatarFares(searchParams);
		def result=responseData?responseData:notFound
		render result as JSON
	}
	def getVietnamairlinesFares={
		searchParams=session.parameters
		def responseData=flightService.getVietnamairlinesFares(searchParams);
		def result=responseData?responseData:notFound
		println (result as JSON)
		render result as JSON
	}
	def getUnitedFares={
		searchParams=session.parameters
		def responseData=flightService.getUnitedFares(searchParams);
		def result=responseData?responseData:notFound
		render result as JSON
	}
	def getVietjetFares={
		searchParams=session.parameters
		def responseData=flightService.getVietjetFares(searchParams);
		def result=responseData?responseData:notFound
		render result as JSON
	}
	def getAeroflotFares={
		searchParams=session.parameters
		def responseData=flightService.getAeroflotFares(searchParams);
		def result=responseData?responseData:notFound
		render result as JSON
	}
	def getJetstarFares={
		searchParams=session.parameters
		def responseData=flightService.getJetstarFares(searchParams);
		def result=responseData?responseData:notFound
		render result as JSON
	}
	def getCebupacificFares={
		searchParams=session.parameters
		def responseData=flightService.getCebupacificFares(searchParams);
		def result=responseData?responseData:notFound
		render result as JSON
	}
	def getMalaysiaairlinesFares={
		searchParams=session.parameters
		def responseData=flightService.getMalaysiaairlinesFares(searchParams);
		def result=responseData?responseData:notFound
		render result as JSON
	}
	def getPhilippineairlinesFares={
		searchParams=session.parameters
		def responseData=flightService.getPhilippineairlinesFares(searchParams);
		def result=responseData?responseData:notFound
		render result as JSON
	}
	def  getSouthafricanairwaysFares={
		searchParams=session.parameters
		def responseData=flightService.getSouthafricanairwaysFares(searchParams);
		def result=responseData?responseData:notFound
		render result as JSON
	}
	//I 2
	def  getBritishairwaysFares={
		searchParams=session.parameters
		def responseData=flightService.getBritishairwaysFares(searchParams);
		def result=responseData?responseData:notFound
		render result as JSON
	}
	def  getCathaypacificFares={
		searchParams=session.parameters
		def responseData=flightService.getCathaypacificFares(searchParams);
		def result=responseData?responseData:notFound
		render result as JSON
	}
	def  getTransasiaairwaysFares={
		searchParams=session.parameters
		def responseData=flightService.getTransasiaairwaysFares(searchParams);
		def result=responseData?responseData:notFound
		render result as JSON
	}
	def  getEvaFares={
		searchParams=session.parameters
		def responseData=flightService.getEvaFares(searchParams);
		def result=responseData?responseData:notFound
		render result as JSON
	}
	def  getLufthansaFares={
		searchParams=session.parameters
		def responseData=flightService.getLufthansaFares(searchParams);
		def result=responseData?responseData:notFound
		render result as JSON
	}
	def  getChinaeasternairlineFares={
		searchParams=session.parameters
		def responseData=flightService.getChinaeasternairlineFares(searchParams);
		def result=responseData?responseData:notFound
		render result as JSON
	}
	def  getChinaairlineFares={
		searchParams=session.parameters
		def responseData=flightService.getChinaairlineFares(searchParams);
		def result=responseData?responseData:notFound
		render result as JSON
	}
	def getAllnipponairwaysFares={
		searchParams=session.parameters
		def responseData=flightService.getAllnipponairwaysFares(searchParams);
		def result=responseData?responseData:notFound
		render result as JSON
	}
	def getChinasouthernFares={
		searchParams=session.parameters
		def responseData=flightService.getChinasouthernFares(searchParams);
		def result=responseData?responseData:notFound
		render result as JSON
	}
	def getEmiratesFares={
		searchParams=session.parameters
		def responseData=flightService.getEmiratesFares(searchParams);
		def result=responseData?responseData:notFound
		render result as JSON
	}
	def getHongkongFares={
		searchParams=session.parameters
		def responseData=flightService.getHongkongFares(searchParams);
		def result=responseData?responseData:notFound
		render result as JSON
	}
	def getKoreanFares={
		searchParams=session.parameters
		def responseData=flightService.getKoreanFares(searchParams);
		def result=responseData?responseData:notFound
		render result as JSON
	}
	def getJapanFares={
		searchParams=session.parameters
		def responseData=flightService.getJapanFares(searchParams);
		def result=responseData?responseData:notFound
		render result as JSON
	}
	def getAirchinaFares={
		searchParams=session.parameters
		def responseData=flightService.getAirchinaFares(searchParams);
		def result=responseData?responseData:notFound
		render result as JSON
	}
	//I 3
	def  getEthiopiaFares={
		searchParams=session.parameters
		def responseData=flightService.getEthiopiaFares(searchParams);
		def result=responseData?responseData:notFound
		render result as JSON
	}

	def getSingaporeFares={
		searchParams=session.parameters
		def responseData=flightService.getSingaporeFares(searchParams);
		def result=responseData?responseData:notFound
		render result as JSON
	}
	def getThaiFares={
		searchParams=session.parameters
		def responseData=flightService.getThaiFares(searchParams);
		def result=responseData?responseData:notFound
		render result as JSON
	}

	def getAmericanFares={
		searchParams=session.parameters
		def responseData=flightService.getAmericanFares(searchParams);
		def result=responseData?responseData:notFound
		render result as JSON
	}
	def getAirfranceFares={
		searchParams=session.parameters
		def responseData=flightService.getAirfranceFares(searchParams);
		def result=responseData?responseData:notFound
		render result as JSON
	}
	def getKenyaFares={
		searchParams=session.parameters
		def responseData=flightService.getKenyaFares(searchParams);
		def result=responseData?responseData:notFound
		render result as JSON
	}
	def getDeltaFares={
		searchParams=session.parameters
		def responseData=flightService.getDeltaFares(searchParams);
		def result=responseData?responseData:notFound
		render result as JSON
	}

	def  getAbacusFares={
		searchParams=session.parameters
		def responseData=flightService.getAbacusFares(searchParams);
		def result=responseData?responseData:notFound
		render result as JSON
	}

	def  getAirAsiaFares={
		searchParams=session.parameters
		def responseData=flightService.getAirAsiaFares(searchParams);
		def result=responseData?responseData:notFound
		render result as JSON
	}
	
	def getFirstElement={
		print "IMONTH=" + params.imonth
		return "aa"
	}
}
