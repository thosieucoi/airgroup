package com.airgroup.service.flight

import grails.converters.JSON

import java.text.SimpleDateFormat

import org.joda.time.DateTime

import com.airgroup.domain.Customer
import com.airgroup.domain.Luggage
import com.airgroup.domain.Order
import com.airgroup.domain.OrderDetail
import com.airgroup.domain.Passenger
import com.airgroup.domain.Payment
import org.weceem.auth.CMSUser;

class FlightInfoService {

	static transactional = true
	
	def springSecurityService

	def insertCustomer(String name,String email,String phoneNumber,String address,Short gender,Short status,String city){
		def customerInstance=new Customer()
		customerInstance.name=name
		customerInstance.email=email
		customerInstance.phoneNumber=phoneNumber
		customerInstance.city=city
		customerInstance.address=address
		customerInstance.gender=gender
		customerInstance.status=status
		customerInstance.save(flush:true)
		return customerInstance
	}
	def insertOrder(CMSUser userCreated, Customer customer,BigDecimal price,Date orderTime,Short kidNumber,Short adultNumber,Short infantNumber,Short billStatus,Short status,String goingDescription,String returnDescription,String specialRequirement,Payment payment, Short isDomestic, String departureLocation,String arrivalLocation){
		def order=new Order()
		order.userCreated = userCreated;
		order.customer=customer
		order.price=price
		order.orderTime=orderTime
		order.kidNumber=kidNumber
		order.adultNumber=adultNumber
		order.infantNumber=infantNumber
		order.billStatus=billStatus
		order.status=status
		order.goingDescription=goingDescription
		order.returnDescription=returnDescription
		order.departureLocation=departureLocation;
		order.arrivalLocation=arrivalLocation;
		order.specialRequire=specialRequirement
		order.payment=payment
		order.showStatus=(short)1
		order.isDomestic=isDomestic
		assert order.save(flush:true)
		return order
	}
	
	//Insert luggage
	def insertLuggage(def session, def params, def passenger, def passengerIndex){
		JSON.use("deep")
		def oairlineCode
		def iairlineCode
		if(session.outboundItem!=null){
			def oboundItemJson = JSON.parse(session.outboundItem.toString())
			oairlineCode = oboundItemJson.airlineCode
			if(session.inboundItem!=null){
				def iboundItemJson = JSON.parse(session.inboundItem.toString())
				iairlineCode = iboundItemJson.airlineCode
			}
		}
		if(params.outboundLuggable!=null){
			String[] outboundLuggablePrice = params.outboundLuggable.toString().replace("[", "").replace("]", "").split(",")
			//Save information to luggage table
			if(("BL").equals(oairlineCode)){
				Luggage luggage = new Luggage()
				luggage.airlineCode = oairlineCode
				//Check luggage fare
//				if(0 == Integer.valueOf(outboundLuggablePrice[passengerIndex].trim())){
//					luggage.baggage = (short)0
//				} else if(130000 == Integer.valueOf(outboundLuggablePrice[passengerIndex].trim())){
//					luggage.baggage = (short)1
//				} else if(160000 == Integer.valueOf(outboundLuggablePrice[passengerIndex].trim())){
//					luggage.baggage = (short)2
//				} else if(220000 == Integer.valueOf(outboundLuggablePrice[passengerIndex].trim())){
//					luggage.baggage = (short)3
//				} else if(270000 == Integer.valueOf(outboundLuggablePrice[passengerIndex].trim())){
//					luggage.baggage = (short)4
//				} else if(320000 == Integer.valueOf(outboundLuggablePrice[passengerIndex].trim())){
//					luggage.baggage = (short)5
//				} else if(370000 == Integer.valueOf(outboundLuggablePrice[passengerIndex].trim())){
//					luggage.baggage = (short)6
//				}
				luggage.baggage = Long.valueOf(outboundLuggablePrice[passengerIndex].trim())
				//Check if have departure flight
				luggage.isDeparture = (short)1
				luggage.passenger = passenger
				luggage.save(flush:true)
			} else if(("VJ").equals(oairlineCode)){
					Luggage luggage = new Luggage()
					luggage.airlineCode = oairlineCode
					//Check luggage fare
//					if(0 == Integer.valueOf(outboundLuggablePrice[passengerIndex].trim())){
//						luggage.baggage = (short)0
//					} else if(110000 == Integer.valueOf(outboundLuggablePrice[passengerIndex].trim())){
//						luggage.baggage = (short)1
//					} else if(143000 == Integer.valueOf(outboundLuggablePrice[passengerIndex].trim())){
//						luggage.baggage = (short)2
//					} else if(220000 == Integer.valueOf(outboundLuggablePrice[passengerIndex].trim())){
//						luggage.baggage = (short)3
//					} else if(330000 == Integer.valueOf(outboundLuggablePrice[passengerIndex].trim())){
//						luggage.baggage = (short)4
//					}
					luggage.baggage = Long.valueOf(outboundLuggablePrice[passengerIndex].trim())
					//Check if have departure flight
					luggage.isDeparture = (short)1
					luggage.passenger = passenger
					luggage.save(flush:true)
			}
		}

		if(params.inboundLuggable!=null){
			String[] inboundLuggablePrice = params.inboundLuggable.toString().replace("[", "").replace("]", "").split(",")
			//Save information to luggage table
			if(("BL").equals(iairlineCode)){
				Luggage luggage = new Luggage()
				luggage.airlineCode = iairlineCode
				//Check luggage fare
//				if(0 == Integer.valueOf(inboundLuggablePrice[passengerIndex].trim())){
//					luggage.baggage = (short)0
//				} else if(130000 == Integer.valueOf(inboundLuggablePrice[passengerIndex].trim())){
//					luggage.baggage = (short)1
//				} else if(160000 == Integer.valueOf(inboundLuggablePrice[passengerIndex].trim())){
//					luggage.baggage = (short)2
//				} else if(220000 == Integer.valueOf(inboundLuggablePrice[passengerIndex].trim())){
//					luggage.baggage = (short)3
//				} else if(270000 == Integer.valueOf(inboundLuggablePrice[passengerIndex].trim())){
//					luggage.baggage = (short)4
//				} else if(320000 == Integer.valueOf(inboundLuggablePrice[passengerIndex].trim())){
//					luggage.baggage = (short)5
//				} else if(370000 == Integer.valueOf(inboundLuggablePrice[passengerIndex].trim())){
//					luggage.baggage = (short)6
//				}
				luggage.baggage = Long.valueOf(inboundLuggablePrice[passengerIndex].trim())
				//Check if have departure flight
				luggage.isDeparture = (short)0
				luggage.passenger = passenger
				luggage.save(flush:true)
			} else if(("VJ").equals(iairlineCode)){
				Luggage luggage = new Luggage()
				luggage.airlineCode = iairlineCode
				//Check luggage fare
//				if(0 == Integer.valueOf(inboundLuggablePrice[passengerIndex].trim())){
//					luggage.baggage = (short)0
//				} else if(110000 == Integer.valueOf(inboundLuggablePrice[passengerIndex].trim())){
//					luggage.baggage = (short)1
//				} else if(143000 == Integer.valueOf(inboundLuggablePrice[passengerIndex].trim())){
//					luggage.baggage = (short)2
//				} else if(220000 == Integer.valueOf(inboundLuggablePrice[passengerIndex].trim())){
//					luggage.baggage = (short)3
//				} else if(330000 == Integer.valueOf(inboundLuggablePrice[passengerIndex].trim())){
//					luggage.baggage = (short)4
//				}
				luggage.baggage = Long.valueOf(inboundLuggablePrice[passengerIndex].trim())
				//Check if have departure flight
				luggage.isDeparture = (short)0
				luggage.passenger = passenger
				luggage.save(flush:true)
			}
		}
	}
	
	def insertFlightInfo(def params, def session, boolean isDomestic){
		JSON.use("deep")
		List<Passenger> passengers=[]
		for(int i=0;i<session.parameters.adults.toInteger();i++){
			Passenger p=new Passenger()
			p.name=params['a_'+i+'_name']
			p.gender=new Short(params['cbx_a_'+i].toString())
			p.dateOfBirth=null
			passengers << p
		}
		for(int i=0;i<session.parameters.kids.toInteger();i++){
			Passenger p=new Passenger()
			p.name=params['k_'+i+'_name']
			p.gender=new Short(params['cbx_k_'+i].toString())
			
			def dayOfMonth=new Integer(params['k_'+i+'_day']+"")
			def monthOfYear=new Integer(params['k_'+i+'_month']+"")
			def year=new Integer(params['k_'+i+'_year']+"")
			DateTime dob=new DateTime(year,monthOfYear,dayOfMonth,0 ,0 ,0 ,0)
			p.dateOfBirth=dob.toDate()
			passengers << p
		}
		for(int i=0;i<session.parameters.infants.toInteger();i++){
			Passenger p=new Passenger()
			p.name=params['i_'+i+'_name']
			p.gender=new Short(params['cbx_i_'+i].toString())
			def dayOfMonth=new Integer(params['i_'+i+'_day']+"")
			def monthOfYear=new Integer(params['i_'+i+'_month']+"")
			def year=new Integer(params['i_'+i+'_year']+"")
			DateTime dob=new DateTime(year,monthOfYear,dayOfMonth,0 ,0 ,0 ,0)
			p.dateOfBirth=dob.toDate()
			passengers << p
		}
		
		def customer=insertCustomer(params.fullname.toString(), params.email.toString(),params.phoneNumber.toString(),params.address.toString(), (short)1,(short)1,params.city.toString())
		Payment payment=Payment.findById(params.paymentId.toLong())
		
		CMSUser user= null;
		if (springSecurityService.isLoggedIn()) {
			user=CMSUser.findByUsername(springSecurityService.authentication.name)
		}
		
		
		def order=insertOrder(user, customer, new BigDecimal(session.totalPrice.toString()),new Date(),session.parameters.kids.toShort(), session.parameters.adults.toShort(),session.parameters.infants.toShort() , params.billStatus, (short)1,session.parameters.departureCode.toString(),session.parameters.arrivalCode.toString(),params.specialRequirement.toString(),payment,isDomestic?(short)0:(short)1,session.parameters.departureCode.toString(),session.parameters.arrivalCode.toString())
		passengers.eachWithIndex {pass,i-> 
			pass.order=order
		    pass.save(flush:true)
			insertLuggage(session, params, pass, i)
		 }
		if(isDomestic){
		if(session.outboundItem!=null){
			def arr=JSON.parse(session.outboundItem.toString())
			for(int i=0;i<arr.oflights.length();i++){
				def details=new OrderDetail()
				details.airline=arr.oflights[i].airlineCode.toString()
				details.departure=arr.oflights[i].departureCode.toString()
				details.arrival=arr.oflights[i].arrivalCode.toString()
				details.flightNumber=arr.oflights[i].flightNumber.toString()
				SimpleDateFormat sdf=new SimpleDateFormat("HH:mm dd/MM/yyyy")
				details.outboundDate=sdf.parse(arr.oflights[i].departureTime.toString()+" "+arr.oflights[i].departureDate.toString())
				details.inboundDate=sdf.parse(arr.oflights[i].arrivalTime.toString()+" "+arr.oflights[i].arrivalDate.toString())
				details.order=order
				details.tripType=(short)1
				details.save(flush:true)
			}
		}
		if(session.inboundItem!=null){
			def arr=JSON.parse(session.inboundItem.toString())
			for(int i=0;i<arr.iflights.length();i++){
				def details=new OrderDetail()
				details.airline=arr.iflights[i].airlineCode.toString()
				details.departure=arr.iflights[i].departureCode.toString()
				details.arrival=arr.iflights[i].arrivalCode.toString()
				details.flightNumber=arr.iflights[i].flightNumber.toString()
				SimpleDateFormat sdf=new SimpleDateFormat("HH:mm dd/MM/yyyy")
				details.outboundDate=sdf.parse(arr.iflights[i].departureTime.toString()+" "+arr.iflights[i].departureDate.toString())
				details.inboundDate=sdf.parse(arr.iflights[i].arrivalTime.toString()+" "+arr.iflights[i].arrivalDate.toString())
				details.order=order
				details.tripType=(short)2
				details.save(flush:true)
			}
		}
		}else{
			if(session.currentItem!=null){
				def arr=JSON.parse(session.currentItem.toString())
				for(int i=0;i<arr.oflights.length();i++){
					def details=new OrderDetail()
					details.airline=arr.oflights[i].airlineCode.toString()
					if(arr.oflights[i].departureCode != null && !("null").equals(arr.oflights[i].departureCode)){
						details.departure=arr.oflights[i].departureCity.toString()
					} else {
						details.departure = params.transitAirport[0].toString()
					}
					details.arrival=arr.oflights[i].arrivalCode.toString()
					details.flightNumber=arr.oflights[i].flightNumber.toString()
					SimpleDateFormat sdf=new SimpleDateFormat("HH:mm dd/MM/yyyy")
					details.outboundDate=sdf.parse(arr.oflights[i].departureTime.toString()+" "+arr.oflights[i].departureDate.toString())
					details.inboundDate=sdf.parse(arr.oflights[i].arrivalTime.toString()+" "+arr.oflights[i].arrivalDate.toString())
					details.order=order
					details.tripType=(short)1
					details.save(flush:true)
				}
				if(arr.iflights!=null){
				for(int i=0;i<arr.iflights.length();i++){
					def details=new OrderDetail()
					details.airline=arr.iflights[i].airlineCode.toString()
					if(arr.oflights[i].departureCode != null && !("null").equals(arr.oflights[i].departureCode)){
						details.departure=arr.oflights[i].departureCity.toString()
					} else {
						details.departure = params.transitAirport[0].toString()
					}
					details.arrival=arr.iflights[i].arrivalCode.toString()
					details.flightNumber=arr.iflights[i].flightNumber.toString()
					SimpleDateFormat sdf=new SimpleDateFormat("HH:mm dd/MM/yyyy")
					details.outboundDate=sdf.parse(arr.iflights[i].departureTime.toString()+" "+arr.iflights[i].departureDate.toString())
					details.inboundDate=sdf.parse(arr.iflights[i].arrivalTime.toString()+" "+arr.iflights[i].arrivalDate.toString())
					details.order=order
					details.tripType=(short)2
					details.save(flush:true)
				}
				}
			}
			
		}
		return order
	}
	def insertCheapFlightInfo(def params, def session, boolean isDomestic){
		JSON.use("deep")
		List<Passenger> passengers=[]
		for(int i=0;i<session.parameters.adults.toInteger();i++){
			Passenger p=new Passenger()
			p.name=params['a_'+i+'_name']
			p.gender=new Short(params['cbx_a_'+i].toString())
			p.dateOfBirth=null
			passengers << p
		}
		for(int i=0;i<session.parameters.kids.toInteger();i++){
			Passenger p=new Passenger()
			p.name=params['k_'+i+'_name']
			p.gender=new Short(params['cbx_k_'+i].toString())
			
			def dayOfMonth=new Integer(params['k_'+i+'_day']+"")
			def monthOfYear=new Integer(params['k_'+i+'_month']+"")
			def year=new Integer(params['k_'+i+'_year']+"")
			DateTime dob=new DateTime(year,monthOfYear,dayOfMonth,0 ,0 ,0 ,0)
			p.dateOfBirth=dob.toDate()
			passengers << p
		}
		for(int i=0;i<session.parameters.infants.toInteger();i++){
			Passenger p=new Passenger()
			p.name=params['i_'+i+'_name']
			p.gender=new Short(params['cbx_i_'+i].toString())
			def dayOfMonth=new Integer(params['i_'+i+'_day']+"")
			def monthOfYear=new Integer(params['i_'+i+'_month']+"")
			def year=new Integer(params['i_'+i+'_year']+"")
			DateTime dob=new DateTime(year,monthOfYear,dayOfMonth,0 ,0 ,0 ,0)
			p.dateOfBirth=dob.toDate()
			passengers << p
		}
		def customer=insertCustomer(params.fullname.toString(), params.email.toString(),params.phoneNumber.toString(),params.address.toString(), (short)1,(short)1,params.city.toString())
		
		CMSUser user= null;
		if (springSecurityService.isLoggedIn()) {
			user=CMSUser.findByUsername(springSecurityService.authentication.name)
		}
		
		
		Payment payment=Payment.findById(params.paymentId.toLong())
		def order=insertOrder(user, customer, new BigDecimal(session.totalPrice.toString()),new Date(),session.parameters.kids.toShort(), session.parameters.adults.toShort(),session.parameters.infants.toShort() , params.billStatus, (short)1,session.parameters.departureCode.toString(),session.parameters.arrivalCode.toString(),params.specialRequirement.toString(),payment,isDomestic?(short)0:(short)1,session.parameters.departureCode.toString(),session.parameters.arrivalCode.toString())
		passengers.eachWithIndex {pass,i->
			pass.order=order
			pass.save(flush:true)
			insertLuggage(session, params, pass, i)
		 }
		if(isDomestic){
			if(session.parameters.oFlightNumber!=null){
				def arr=JSON.parse(session.outboundItem.toString())
				def details=new OrderDetail()
				details.airline=session.parameters.airlineCode.toString()
				details.departure=session.parameters.departureCode.toString()
				details.arrival=session.parameters.arrivalCode.toString()
				details.flightNumber=session.parameters.oFlightNumber.toString()
//				SimpleDateFormat sdf=new SimpleDateFormat("HH:mm dd/MM/yyyy")
				
				details.outboundDate=new Date(session.parameters.oOutBoundDate)
				details.inboundDate=new Date(session.parameters.oInBoundDate)
				details.order=order
				details.tripType=(short)1
				details.save(flush:true)
			}
			if(session.parameters.iFlightNumber!=null){
				def arr=JSON.parse(session.inboundItem.toString())
				def details=new OrderDetail()
				details.airline=session.parameters.airlineCode.toString()
				details.departure=session.parameters.departureCode.toString()
				details.arrival=session.parameters.arrivalCode.toString()
				details.flightNumber=session.parameters.iFlightNumber.toString()
//				SimpleDateFormat sdf=new SimpleDateFormat("HH:mm dd/MM/yyyy")
				details.outboundDate=new Date(session.parameters.iOutBoundDate)
				details.inboundDate=new Date(session.parameters.iInBoundDate)
				details.order=order
				details.tripType=(short)2
				details.save(flush:true)
			}
		}else{
			if(session.currentItem!=null){
				def arr=JSON.parse(session.currentItem.toString())
				for(int i=0;i<arr.oflights.length();i++){
					def details=new OrderDetail()
					details.airline=arr.oflights[i].airlineCode.toString()
					if(arr.oflights[i].departureCode != null && !("null").equals(arr.oflights[i].departureCode)){
						details.departure=arr.oflights[i].departureCity.toString()
					} else {
						details.departure = params.transitAirport[0].toString()
					}
					details.arrival=arr.oflights[i].arrivalCode.toString()
					details.flightNumber=arr.oflights[i].flightNumber.toString()
					SimpleDateFormat sdf=new SimpleDateFormat("HH:mm dd/MM/yyyy")
					details.outboundDate=sdf.parse(arr.oflights[i].departureTime.toString()+" "+arr.oflights[i].departureDate.toString())
					details.inboundDate=sdf.parse(arr.oflights[i].arrivalTime.toString()+" "+arr.oflights[i].arrivalDate.toString())
					details.order=order
					details.tripType=(short)1
					details.save(flush:true)
				}
				if(arr.iflights!=null){
				for(int i=0;i<arr.iflights.length();i++){
					def details=new OrderDetail()
					details.airline=arr.iflights[i].airlineCode.toString()
					if(arr.oflights[i].departureCode != null && !("null").equals(arr.oflights[i].departureCode)){
						details.departure=arr.oflights[i].departureCity.toString()
					} else {
						details.departure = params.transitAirport[0].toString()
					}
					details.arrival=arr.iflights[i].arrivalCode.toString()
					details.flightNumber=arr.iflights[i].flightNumber.toString()
					SimpleDateFormat sdf=new SimpleDateFormat("HH:mm dd/MM/yyyy")
					details.outboundDate=sdf.parse(arr.iflights[i].departureTime.toString()+" "+arr.iflights[i].departureDate.toString())
					details.inboundDate=sdf.parse(arr.iflights[i].arrivalTime.toString()+" "+arr.iflights[i].arrivalDate.toString())
					details.order=order
					details.tripType=(short)2
					details.save(flush:true)
				}
				}
			}
		}
		return order
	}

}
