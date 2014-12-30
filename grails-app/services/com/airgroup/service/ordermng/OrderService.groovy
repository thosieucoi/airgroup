package com.airgroup.service.ordermng

import grails.validation.ValidationException

import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import org.weceem.auth.CMSUser

import com.airgroup.domain.Bill
import com.airgroup.domain.Customer
import com.airgroup.domain.Luggage
import com.airgroup.domain.Order
import com.airgroup.domain.OrderDetail
import com.airgroup.domain.OrderEmployee
import com.airgroup.domain.OrderPayment
import com.airgroup.domain.Passenger
import com.airgroup.domain.Remark

class OrderService {
	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm");
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormat.forPattern("dd/MM/yyyy");
	static transactional = true
	def springSecurityService

	def updateRemark(String content, Order order, CMSUser user) {
		Date remarkDate = new Date()
		def Remark remark = addRemark(content, remarkDate, order, user)
	}

	def updateOrInsert(params) {
		def CMSUser user = CMSUser.findById(params.employee.id)

		def Order order = updateOrder(params)
		def OrderEmployee orderEmployee = updateOrderEmployee(params)
		if(order.payment.id == 3){
			def OrderPayment orderPayment = updateOrderPayment(params)
		}
		//insert or update passenger and luggage
		def passenger
		def luggage
		params.passenger.each { k,v ->
			if(v instanceof Map ) {
				passenger = Passenger.get(v.id)
				Date dateOfBirth
				if(Integer.parseInt(v.gender) > 1) {
					if(v.dateOfBirth!=""){
						dateOfBirth = DATE_FORMATTER.parseDateTime(v.dateOfBirth).toDate()
					}else{
						dateOfBirth=null
					}

				} else {
					dateOfBirth = null
				}
				Integer passengerId = v?.id ? Integer.parseInt(v.id) : 0
				passenger = addOrUpdatePassenger(passengerId,v.name,v.gender.toShort(),dateOfBirth,order)
				if(v.isDeparture != null){
					if(v.isDeparture.size() > 1){
						luggage = addOrUpdateLuggage(passenger, v.obairline, v.outboundluggage.toLong(), ('1').toShort())
						luggage = addOrUpdateLuggage(passenger, v.ibairline, v.inboundluggage.toLong(), ('0').toShort())
					}else if(v.isDeparture == '1'){
						luggage = addOrUpdateLuggage(passenger, v.obairline, v.outboundluggage.toLong(), ('1').toShort())
					}else if(v.isDeparture == '0'){
						luggage = addOrUpdateLuggage(passenger, v.ibairline, v.inboundluggage.toLong(), ('0').toShort())
					}
				}
			}

		}

		//Save order detail
		def orderDetail
		params.orderDetail.each { k, v ->
			if (v instanceof Map) {
				orderDetail = OrderDetail.get(v.id)
				Date inboundDate
				Date outboundDate
				if (v?.inboundDate) {
					inboundDate = DATE_TIME_FORMATTER.parseDateTime(v.inboundDate).toDate()
				}
				if (v?.outboundDate) {
					outboundDate = DATE_TIME_FORMATTER.parseDateTime(v.outboundDate).toDate()
				}
				orderDetail = updateOrderDetail(Integer.parseInt(v.id), v.departure,v.arrival,v.flightNumber,
						v.airline,outboundDate,inboundDate,order)
			}
		}
		//Save remark
		Date remarkDate = new Date()
		def Remark remark = addRemark(params.remark.content, remarkDate, order, user)
		//Save customer
		Integer customerId = params?.customerId ? Integer.parseInt(params.customerId) : 0
		def Customer customer = updateCustomer(customerId, params.customerName, params.customerEmail, params.customerPhoneNumber,
				params.customerAddress, params.customerCity)
		//Save bill
		Integer billId = params?.billId ? Integer.parseInt(params.billId) : 0
		def Bill bill = updateBill(billId, params.companyName, params.companyAddress, params.taxSerial, params.address, order)
	}


	def addOrUpdateLuggage(Passenger passenger, String airlineCode, Long baggage, Short isDeparture){
		def luggage
		if(Luggage.findByPassengerAndIsDeparture(passenger,isDeparture)){
			luggage = Luggage.findByPassengerAndIsDeparture(passenger,isDeparture)
			luggage.airlineCode = airlineCode
			luggage.baggage = baggage
		}

		luggage.save(flush:true)
	}

	def addOrUpdatePassenger(Integer id,String pName,Short pGender,Date pDateOfBirth,Order pOrder) {
		def passenger
		if (id > 0) {
			passenger = Passenger.get(id)
			passenger.name = pName
			passenger.gender = pGender
			passenger.dateOfBirth = pDateOfBirth
			passenger.order = pOrder
		}
		else {
			passenger = new Passenger(name:pName,gender:pGender,dateOfBirth:pDateOfBirth,order:pOrder)
		}
		if (!passenger.validate()) {
			throw new ValidationException("passenger is not valid", passenger.errors)
		} else {
			passenger.save(flush:true)
		}
		return passenger
	}

	def updateOrderDetail(Integer orderDetailId,String pDeparture,String pArrival,String pFlightNumber,
	String pAirline,Date pOutboundDate,Date pInboundDate,Order pOrder) {
		def orderDetail = OrderDetail.get(orderDetailId)
		orderDetail.departure = pDeparture
		orderDetail.arrival = pArrival
		orderDetail.flightNumber = pFlightNumber
		orderDetail.airline = pAirline
		orderDetail.outboundDate = pOutboundDate
		orderDetail.inboundDate = pInboundDate
		orderDetail.order = pOrder

		if (!orderDetail.validate()) {
			throw new ValidationException("orderDetail is not valid", orderDetail.errors)
		} else {
			orderDetail.save(flush:true)
		}

		return orderDetail
	}

	def addRemark(String pContent,Date pRemarkBirth,Order pOrder,CMSUser pUser) {
		def remark = new Remark(content:pContent,remarkDate:pRemarkBirth,order:pOrder, employee: pUser)

		if (!remark.validate()) {
			throw new ValidationException("orderDetail is not valid", remark.errors)
		} else {
			remark.save(flush:true)
		}

		return remark
	}

	def updateCustomer(Integer customerId, String name, String email, String phoneNumber,
	String address,	String city) {
		def Customer customer = Customer.get(customerId)
		customer.name = name
		customer.email = email
		customer.phoneNumber = phoneNumber
		customer.address = address
		customer.city = city
		if (!customer.validate()) {
			throw new ValidationException("customer is not valid", customer.errors)
		} else {
			customer.save(flush:true)
		}

		return customer
	}

	def updateBill(Integer billId, String companyName, String companyAddress, String taxSerial, String address, Order pOrder) {
		def bill
		if (billId > 0) {
			bill = Bill.get(billId)
		} else {
			bill = new Bill()
		}
		bill.companyName = companyName
		bill.companyAddress = companyAddress
		bill.taxSerial = taxSerial
		bill.address = address
		bill.order = pOrder
		if (!bill.validate()) {
			throw new ValidationException("bill is not valid", bill.errors)
		} else {
			bill.save(flush:true)
		}

		return bill
	}

	def updateOrder(params) {
		def order = Order.get(params.int('id'))

		//order parameters
		order.price = new BigDecimal(params.price.replaceAll(",", "").replaceAll("\\.", ""))
		order.adultNumber = params.adultNumber.toShort()
		order.kidNumber = params.kidNumber.toShort()
		order.infantNumber = params.infantNumber.toShort()
		order.orderTime = new Date()
		order.status = params.status.toShort()
		order.goingDescription = params.goingDescription
		order.returnDescription = params.returnDescription
		order.specialRequire = params.specialRequire
		order.properties = params
		if (!order.validate()) {
			throw new ValidationException("Order is not valid", order.errors)
		} else {
			order.save(flush:true)
		}
		return order
	}

	def updateOrderPayment(params) {
		def orderPayment = OrderPayment.findByOrderId(params.long('id'))

		if(orderPayment == null){
			orderPayment = new OrderPayment()
			orderPayment.orderId = params.id.toLong()
		}

		orderPayment.address = params.paymentAddress
		orderPayment.person = params.paymentPerson
		orderPayment.phoneNumber = params.paymentPhoneNumber
		orderPayment.city = params.paymentCity
		orderPayment.paymentId = (params.payment.id).toLong()

		if(!orderPayment.validate()){
			throw new ValidationException("Order Payment is not valid", orderPayment.errors)
		}else{
			orderPayment.save(flush:true)
		}
		return orderPayment
	}

	def updateOrderEmployee(params) {
		def OrderEmployee orderEmp
		def logginUser = CMSUser.findByUsername(springSecurityService.authentication.name)
		if(("[ROLE_ADMIN]").equals(String.valueOf(logginUser.authorities)) && (("").equals(params.bookId) || params.int('bookId') == null)){
			if(OrderEmployee.findById(params.id) == null){
				orderEmp = new OrderEmployee()
				orderEmp.order=Order.findById(params.id.toLong())
				orderEmp.status = Short.valueOf("0")
			}else{
				orderEmp = OrderEmployee.findById(params.id.toLong())
				orderEmp.status = Short.valueOf("0")
			}
		} else {
			orderEmp = OrderEmployee.get(params.int('orderEmpId'))
			orderEmp.status = Short.valueOf(params.status)
		}

		orderEmp.notification = params.notification.equals("") ? null : DATE_TIME_FORMATTER.parseDateTime(params.notification).toDate()
		orderEmp.timeLimit = params.timeLimit.equals("") ? null : DATE_TIME_FORMATTER.parseDateTime(params.timeLimit).toDate()
		orderEmp.moneyCode = params.moneyCode

		if (!orderEmp.validate()) {
			throw new ValidationException("Order employee is not valid", orderEmp.errors)
		} else {
			orderEmp.save(flush:true)
		}
		return orderEmp
	}
}
