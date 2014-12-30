package com.airgroup.controller.order

import grails.validation.ValidationException

import java.text.SimpleDateFormat

import org.hibernate.criterion.CriteriaSpecification
import org.joda.time.DateTime
import org.joda.time.Hours
import org.joda.time.Minutes
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import org.weceem.auth.CMSUser

import com.airgroup.domain.Location
import com.airgroup.domain.Luggage
import com.airgroup.domain.Order
import com.airgroup.domain.OrderDetail
import com.airgroup.domain.OrderEmployee
import com.airgroup.domain.OrderPayment
import com.airgroup.domain.Passenger
import com.airgroup.utils.Email

class OrderController {
	def springSecurityService
	def orderService
	def dupList
	static allowedMethods = [save: "POST", update: "POST", delete: "POST", filter: "GET"]
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormat.forPattern("dd/MM/yyyy");
	def dateFormat = DateTimeFormat.forPattern("yyyy-mm-dd")

	def index = {
		redirect(action: "list", params: params)
	}

	def list = {
		def startDate
		def endDate
		def user=CMSUser.findByUsername(springSecurityService.authentication.name)

		if(params.day != null){
			if(params.day != ""){
				def searchDay = new Date() - params.day.toInteger()
				def today = new Date()
				def ymdFmt = new java.text.SimpleDateFormat("yyyy-MM-dd")
				def dateYmd = ymdFmt.format(searchDay)
				def endDay = ymdFmt.format(today)
				def dateTimeFormat = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				startDate = dateTimeFormat.parse("${dateYmd} 00:00:00");
				endDate = dateTimeFormat.parse("${endDay} 23:59:59");
			}
		}else{
			params.day = ""
		}

		if(params.status == null){
			params.status = ""
		}

		if(params.myorder == null){
			params.myorder = "1"
		}

		if(params.active == null){
			params.active = "1"
		}

		if(params.fltype == null){
			params.fltype = ""
		}

		params.max = Math.min(params.max ? params.int('max') : 20, 100)
		params.offset = params.offset? params.offset.toInteger():0
		params.sort = params.sort?:'id'
		params.order = params.order?:'desc'

		def c = Order.createCriteria()

		def results = c.list (max: params.max, offset: params.offset){
			createAlias ('orderEmployee', 'orderEmployee', CriteriaSpecification.LEFT_JOIN)
			and{
				if(params.status != ""){
					if(params.status == "4"){
						or{
							isNull("orderEmployee.bookEmp.id")
							isNull("orderEmployee.processEmp.id")
						}
					}else{
						eq("status", Short.parseShort(params.status))
					}
				}
				if(params.day != ""){
					between("orderTime", startDate, endDate)
				}
				if(params.active != ""){
					eq("showStatus", Short.parseShort(params.active))
				}
				if(params.fltype != ""){
					eq("isDomestic", Short.parseShort(params.fltype))
				}
				if(params.myorder != "0"){
					if(params.myorder == '1'){
						or{
							eq("orderEmployee.bookEmp.id" , user.id)
							eq("orderEmployee.processEmp.id" , user.id)
						}
					}
					else if(params.myorder == '2'){
						and{
							eq("orderEmployee.bookEmp.id" , user.id)
							or{
								ne("orderEmployee.processEmp.id" , user.id)
								isNull("orderEmployee.processEmp.id")
							}
							ne("status", (Short) (2));
						}
					}
					else if(params.myorder == '3'){
						eq("orderEmployee.processEmp.id" , user.id)
						ne("status", (Short) (2));
					}
				}
			}
			if(params.order == "customer"){
				order('customer.name', params.order)
			}
			else{
				order(params.sort, params.order)
			}
		}

		[orderInstanceList: results,
			orderInstanceTotal: results.totalCount, currentUser: user, status: params.status, day: params.day, myorder: params.myorder, active:params.active, fltype:params.fltype]
	}

	def filter = {
		def startDate
		def endDate
		def user=CMSUser.findByUsername(springSecurityService.authentication.name)

		if(params.day != null){
			if(params.day!=""){
				def searchDay = new Date() - params.day.toInteger()
				def today = new Date()
				def ymdFmt = new java.text.SimpleDateFormat("yyyy-MM-dd")
				def dateYmd = ymdFmt.format(searchDay)
				def endDay = ymdFmt.format(today)
				def dateTimeFormat = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				startDate = dateTimeFormat.parse("${dateYmd} 00:00:00");
				endDate = dateTimeFormat.parse("${endDay} 23:59:59");
			}
		}

		if(params.fltype == null){
			params.fltype = ""
		}

		params.max = Math.min(params.max ? params.int('max') : 20, 100)
		params.offset = params.offset? params.offset.toInteger():0
		params.sort = params.sort?:'id'
		params.order = params.order?:'desc'

		def c = Order.createCriteria()

		def results = c.list (max: params.max, offset: params.offset){
			createAlias ('orderEmployee', 'orderEmployee', CriteriaSpecification.LEFT_JOIN)
			and{
				if(params.status != ""){
					if(params.status == "4"){
						or{
							isNull("orderEmployee.bookEmp.id")
							isNull("orderEmployee.processEmp.id")
						}
					}else{
						eq("status", Short.parseShort(params.status))
					}
				}
				if(params.day != ""){
					between("orderTime", startDate, endDate)
				}
				if(params.active != ""){
					eq("showStatus", Short.parseShort(params.active))
				}
				if(params.fltype != ""){
					eq("isDomestic", Short.parseShort(params.fltype))
				}
				if(params.myorder != "0"){
					if(params.myorder == '1'){
						or{
							eq("orderEmployee.bookEmp.id" , user.id)
							eq("orderEmployee.processEmp.id" , user.id)
						}
					}
					else if(params.myorder == '2'){
						and{
							eq("orderEmployee.bookEmp.id" , user.id)
							or{
								ne("orderEmployee.processEmp.id" , user.id)
								isNull("orderEmployee.processEmp.id")
							}
							ne("status", (Short) (2));
						}
					}
					else if(params.myorder == '3'){
						eq("orderEmployee.processEmp.id" , user.id)
						ne("status", (Short) (2));
					}
				}
			}
			if(params.order == "customer"){
				order('customer.name', params.order)
			}
			else{
				order(params.sort, params.order)
			}
		}

		[orderInstanceList: results,
			orderInstanceTotal: results.totalCount, currentUser: user, status: params.status, day: params.day, myorder: params.myorder, active:params.active, fltype:params.fltype]
	}

	boolean checkLong(String str){
		try{
			Long.parseLong(str);
			return true;
		}catch(NumberFormatException e){
			return false;
		}
	}

	boolean checkDate(String str){
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		try{
			Date date = formatter.parse(str);
			return true;
		}catch(Exception e){
			return false;
		}
	}

	def search = {
		def c = Order.createCriteria()
		def user=CMSUser.findByUsername(springSecurityService.authentication.name)

		params.max = Math.min(params.max ? params.int('max') : 20, 100)
		params.offset = params.offset? params.offset.toInteger():0
		params.sort = params.sort?:'id'
		params.order = params.order?:'desc'

		def results = c.listDistinct {
			if(params.info != ""){
				createAlias ('customer', 'customer', CriteriaSpecification.LEFT_JOIN)
				createAlias ('passengers', 'passengers', CriteriaSpecification.LEFT_JOIN)
				createAlias ('bill', 'bill', CriteriaSpecification.LEFT_JOIN)
				createAlias ('orderDetails', 'orderDetails', CriteriaSpecification.LEFT_JOIN)

				or{
					if(checkLong(params.info)){
						eq('id', Long.parseLong(params.info))
					}

					if(checkDate(params.info)){
						def dateTimeFormat = new java.text.SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
						def startDate = dateTimeFormat.parse(params.info + " 00:00:00");
						def endDate = dateTimeFormat.parse(params.info + " 23:59:59");
						between('orderDetails.outboundDate', startDate, endDate)
						between('orderDetails.inboundDate', startDate, endDate)
					}

					like('customer.name', '%' + params.info + '%')
					like('customer.email', '%' + params.info + '%')
					like('customer.phoneNumber', '%' + params.info + '%')
					like('customer.address', '%' + params.info + '%')
					like('customer.city', '%' + params.info + '%')

					like('passengers.name', '%' + params.info + '%')

					like('bill.companyName', '%' + params.info + '%')
					like('bill.companyAddress', '%' + params.info + '%')
					like('bill.taxSerial', '%' + params.info + '%')
					like('bill.address', '%' + params.info + '%')

					like('orderDetails.departure', '%' + params.info + '%')
					like('orderDetails.arrival', '%' + params.info + '%')
					like('orderDetails.flightNumber', '%' + params.info + '%')
					like('orderDetails.airline', '%' + params.info + '%')

					like('specialRequire', '%' + params.info + '%')
				}
			}
			if(params.order == "customer"){
				order('customer.name', params.order)
			}
			else{
				order(params.sort, params.order)
			}
		}

		if( results ) {
			// define range startIndex..usedMax
			int listSize = results.size();
			int usedMax = params.offset + params.max - 1
			if( usedMax >= listSize ) {
				usedMax = listSize - 1
			}
			// select only IDs we want to fetch
			def res = results[ params.offset..usedMax]
			// fetch all Responses based on selected IDs
			[orderInstanceList: res, orderInstanceTotal: listSize, currentUser: user, searchParam: params.info]
		}
		else{
			[orderInstanceList: results, orderInstanceTotal: results.size(), currentUser: user, searchParam: params.info]
		}
	}

	def book = {
		def orderEmployee
		if (params.orderEmpId) {
			orderEmployee = OrderEmployee.get(params.orderEmpId)
		} else {
			orderEmployee = new OrderEmployee()
		}
		CMSUser empBooking = CMSUser.findById(params.user)
		Order order = Order.findById(params.orderId)
		//		orderEmployee.properties = params
		orderEmployee.executeTime = new Date()
		orderEmployee.status = (Short)1
		orderEmployee.order = order
		orderEmployee.bookEmp = empBooking
		orderEmployee.processEmp = null

		if (orderEmployee.save(flush: true)) {
			if (params.type.equals("list"))
			chain(action: "list", params:[status: params.status, day: params.day, myorder: params.myorder, active:params.active, fltype:params.fltype])
			else
			chain(action: "edit", params: [user: empBooking.id, id: order.id])
		}
		else {
			render(view: "list", model: [orderInstance: orderEmployee])
		}
	}

	def bookCancel = {
		def orderEmployee = OrderEmployee.get(params.orderEmpId)
		CMSUser currentUser = CMSUser.findById(params.user)
		Order order = Order.findById(params.orderId)
		//		orderEmployee.properties = params
		orderEmployee.executeTime = new Date()
		orderEmployee.status = (Short)0
		orderEmployee.order = order
		orderEmployee.bookEmp = null

		if (orderEmployee.save(flush: true)) {
			if (params.type.equals("list"))
			chain(action: "list", params:[status: params.status, day: params.day, myorder: params.myorder, active:params.active, fltype:params.fltype])
			else
			chain(action: "edit", params: [user: currentUser.id, id: order.id])
			//render view: "edit", model: [currentUser: currentUser, orderInstance: order]
		}
		else {
			render(view: "list", model: [orderInstance: orderEmployee])
		}
	}

	def process = {
		def orderEmployee = OrderEmployee.get(params.orderEmpId)
		def orderInstance = Order.findById(params.orderEmpId)
		CMSUser empProcess = CMSUser.findById(params.user)
		//orderEmployee.properties = params
		orderEmployee.executeTime = new Date()
		orderEmployee.processEmp = empProcess
		if (orderEmployee.save(flush: true)) {
			if (params.type.equals("list"))
			chain(action: "list", params:[status: params.status, day: params.day, myorder: params.myorder, active:params.active, fltype:params.fltype])
			else
			chain(action: "edit", params: [user: empProcess.id, id: orderEmployee.id])
			//render view: "edit", model: [currentUser: empProcess, orderInstance: orderInstance]
		}
		else {
			render(view: "list", model: [orderInstance: orderEmployee])
		}
	}

	def processCancel = {
		def orderEmployee = OrderEmployee.get(params.orderEmpId)
		def orderInstance = Order.findById(params.orderEmpId)
		def currentUser = CMSUser.findById(params.user)
		//		orderEmployee.properties = params
		orderEmployee.processEmp = null
		orderEmployee.notification = null
		orderEmployee.timeLimit = null
		orderEmployee.moneyCode = null
		if (orderEmployee.save(flush: true)) {
			if (params.type.equals("list"))
			chain(action: "list", params:[status: params.status, day: params.day, myorder: params.myorder, active:params.active, fltype:params.fltype])
			else
			chain(action: "edit", params: [user: currentUser.id, id: orderEmployee.id])
			//render view: "edit", model: [currentUser: currentUser, orderInstance: orderInstance]
		}
		else {
			render(view: "list", model: [orderInstance: orderEmployee])
		}
	}

	def create = {
		def orderInstance = new Order()
		orderInstance.properties = params
		return [orderInstance: orderInstance]
	}

	def save = {
		def orderInstance = new Order(params)
		if (orderInstance.save(flush: true)) {
			flash.message = "${message(code: 'default.created.message', args: [message(code: 'order.label', default: 'Order'), orderInstance.id])}"
			redirect(action: "show", id: orderInstance.id)
		}
		else {
			render(view: "create", model: [orderInstance: orderInstance])
		}
	}

	def show = {
		def orderInstance = Order.get(params.id)
		if (!orderInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'order.label', default: 'Order'), params.id])}"
			redirect(action: "list")
		}
		else {
			[orderInstance: orderInstance]
		}
	}

	def edit = {
		def orderInstance = Order.get(params.id)
		//CMSUser currentUser = CMSUser.findById(params.user)
		def currentUser=CMSUser.findByUsername(springSecurityService.authentication.name)

		//Find Passenger
		def passengers = Passenger.findAllByOrder(orderInstance)

		def psgCriteria = Passenger.createCriteria()

		def psgList = psgCriteria.list{
			'in'("name", passengers.name)
			not{
				eq("order", orderInstance)
			}
			projections {
				distinct('order')
			}
		}

		def orderPayment = OrderPayment.findByOrderId(orderInstance.id)

		Date today = new Date().clearTime()

		//Find Order Details
		def detailsCriteria = OrderDetail.createCriteria()

		def detailsList = detailsCriteria.list{
			and{
				ge("outboundDate", today)
			}
			not{
				eq("order", orderInstance)
			}
			projections {
				distinct('order')
			}
		}

		dupList = ((detailsList + psgList) - (detailsList - psgList)).unique()
		//transit information
		List<OrderDetail> oorderDetails = OrderDetail.findAllByOrderAndTripType(orderInstance,1)
		DateTime d = new DateTime()
		Location location
		def transiteDurations = []
		def transitLocations = []
		if(oorderDetails.size() > 1){
			for(int i=1;i<oorderDetails.size();i++){
				String oduration=Hours.hoursBetween(
				new DateTime(oorderDetails.get(i-1).inboundDate.getTime()),new DateTime(oorderDetails.get(i).outboundDate.getTime())).getHours()+'hr'+" + "+
				Minutes.minutesBetween(new DateTime(oorderDetails.get(i-1).inboundDate.getTime()),new DateTime(oorderDetails.get(i).outboundDate.getTime())).getMinutes()%60+"min"
				transiteDurations << oduration
				transitLocations = oorderDetails.get(i).departure
			}
		}
		String iduration=null
		List<OrderDetail> iorderDetails = OrderDetail.findAllByOrderAndTripType(orderInstance,2)

		if(iorderDetails.size() > 1){
			for(int i=1;i<iorderDetails.size();i++){
				iduration=Hours.hoursBetween(
				new DateTime(iorderDetails.get(i-1).inboundDate.getTime()),new DateTime(iorderDetails.get(i).outboundDate.getTime())).getHours()+'hr'+" + "+
				Minutes.minutesBetween(new DateTime(iorderDetails.get(i-1).inboundDate.getTime()),new DateTime(iorderDetails.get(i).outboundDate.getTime())).getMinutes()%60+"min"
				transiteDurations << iduration
				transitLocations = iorderDetails.get(i).departure
			}
		}
		if (!orderInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'order.label', default: 'Order'), params.id])}"
			redirect(action: "list")
		}
		else {
			return [orderInstance: orderInstance,
				currentUser: currentUser,
				dupList: dupList,
				locations: transitLocations,
				durations: transiteDurations,
				orderPayment : orderPayment
			]
		}
	}

	def payment = {
		def orderInstance = Order.get(params.id)
		//CMSUser currentUser = CMSUser.findById(params.user)
		def currentUser=CMSUser.findByUsername(springSecurityService.authentication.name)
		def orderPayment = OrderPayment.findByOrderId(orderInstance.id)

		if (!orderInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'order.label', default: 'Order'), params.id])}"
			redirect(action: "list")
		}
		else {
			return [orderInstance: orderInstance,
				orderPayment : orderPayment,
				selectedPayment : params.payment
			]
		}
	}

	def duplicate = {
		def orderInstance = Order.get(params.id)
		CMSUser currentUser = CMSUser.findById(params.user)


		//Find Passenger
		def passengers = Passenger.findAllByOrder(orderInstance)

		def psgCriteria = Passenger.createCriteria()

		def psgList = psgCriteria.list{
			'in'("name", passengers.name)
			projections {
				distinct('order')
			}
		}

		Date today = new Date().clearTime()

		//Find Order Details
		def detailsCriteria = OrderDetail.createCriteria()

		def detailsList = detailsCriteria.list{
			and{
				ge("outboundDate", today)
			}
			projections {
				distinct('order')
			}
		}

		dupList = ((detailsList + psgList) - (detailsList - psgList)).unique()

		def ob = []
		def ib= []
		def flight = []
		def dep = []
		def arr = []
		dupList.each { order ->
			String obDetails = OrderDetail.findByOrderAndTripType(order, 1).outboundDate.toString()
			String flightDetails = OrderDetail.findByOrder(order).airline + " " + OrderDetail.findByOrder(order).flightNumber
			def location = OrderDetail.findAllByOrderAndTripType(order, 1)

			def ibdtl = OrderDetail.findByOrderAndTripType(order, 2)

			String ibDetails = ""
			if(ibdtl!=null){
				ibDetails = OrderDetail.findByOrderAndTripType(order, 2).inboundDate.toString()
			}

			ob << obDetails
			ib << ibDetails
			flight << flightDetails
			dep << location.get(0).departure
			arr << location.get(location.size() - 1).arrival
		}

		return [orderInstanceList: dupList, currentUser: currentUser, ob: ob, ib:ib, flight:flight, dep:dep, arr:arr]
	}

	def update = {
		Boolean valid = true
		def logginUser=CMSUser.findByUsername(springSecurityService.authentication.name)
		def orderInstance = Order.get(params.id)
		def currentUser=new CMSUser()
		def processUser=new CMSUser()
		//If current user is admin then check set the employeeId = adminId
		if(("[ROLE_ADMIN]").equals(String.valueOf(logginUser.authorities))){
			if(params.employee == null || ("").equals(params.employee) || params.processId == null || ("").equals(params.processId)){
				currentUser = logginUser
				processUser = logginUser
			} else {
				currentUser = logginUser
				processUser = logginUser
			}
		} else {
			currentUser = CMSUser.findById(params.employee.id)
			processUser = CMSUser.findById(params.processId)
		}

		try {
			if (currentUser.id == processUser.id) {
				String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})"
				String priceReg = "^[+-]?[0-9]{1,3}(?:[0-9]*(?:[.,][0-9]{2})?|(?:,[0-9]{3})*(?:\\.[0-9]{2})?|(?:\\.[0-9]{3})*(?:,[0-9]{2})?)+"

				if (params.customerName.equals("")) {
					valid = false
					flash.message = "${message(code: 'customer.name.blank')}"
					render(view: "edit", model:[orderInstance: orderInstance, currentUser: currentUser])
					return
				} else if (!params.customerName.matches("^[\\p{L}0-9 .'-]+")) {
					valid = false
					flash.message = "${message(code: 'customer.name.special.characters')}"
					render(view: "edit", model:[orderInstance: orderInstance, currentUser: currentUser])
					return
				}
				/*
				 if (params.customerEmail.equals("")) {
				 valid = false
				 flash.message = "${message(code: 'customer.email.blank')}"
				 render(view: "edit", model:[orderInstance: orderInstance, currentUser: currentUser])
				 return
				 } else if (!params.customerEmail.matches(emailPattern)) {
				 valid = false
				 flash.message = "${message(code: 'customer.email.format')}"
				 render(view: "edit", model:[orderInstance: orderInstance, currentUser: currentUser])
				 return
				 }
				 */
				if (params.customerPhoneNumber.equals("")) {
					valid = false
					flash.message = "${message(code: 'customer.phone.blank')}"
					render(view: "edit", model:[orderInstance: orderInstance, currentUser: currentUser])
					return
				} else if (!params.customerPhoneNumber.matches("\\d{6,15}")) {
					valid = false
					flash.message = "${message(code: 'customer.phone.format')}"
					render(view: "edit", model:[orderInstance: orderInstance, currentUser: currentUser])
					return
				}
				/*if (params.customerAddress.equals("")) {
					valid = false
					flash.message = "${message(code: 'customer.address.blank')}"
					render(view: "edit", model:[orderInstance: orderInstance, currentUser: currentUser])
					return
				} else if (!params.customerAddress.matches("^[0-9 \\p{L}0-9 .,'-]+")) {
					valid = false
					flash.message = "customer.address.format"
					render(view: "edit", model:[orderInstance: orderInstance, currentUser: currentUser])
					return
				}
				if (params.customerCity.equals("")) {
					valid = false
					flash.message = "${message(code: 'customer.city.blank')}"
					render(view: "edit", model:[orderInstance: orderInstance, currentUser: currentUser])
					return
				} else if (!params.customerCity.matches("^[\\p{L}0-9 .'-]+")) {
					valid = false
					flash.message = "${message(code: 'customer.city.format')}"
					render(view: "edit", model:[orderInstance: orderInstance, currentUser: currentUser])
					return
				}*/
				if (!params.companyName.equals("") && !params.companyName.matches("^[\\p{L}0-9 .'-]+")) {
					valid = false
					flash.message = "${message(code: 'company.name.format')}"
					render(view: "edit", model:[orderInstance: orderInstance, currentUser: currentUser])
					return
				}
				if (!params.companyAddress.equals("") && !params.companyAddress.matches("^[0-9 \\p{L}0-9 .,'-]+")) {
					valid = false
					flash.message = "${message(code: 'company.address.format')}"
					render(view: "edit", model:[orderInstance: orderInstance, currentUser: currentUser])
					return
				}
				if (!params.taxSerial.equals("") && !params.taxSerial.matches("\\d{0,15}")) {
					valid = false
					flash.message = "${message(code: 'company.tax.format')}"
					render(view: "edit", model:[orderInstance: orderInstance, currentUser: currentUser])
					return
				}
				if (!params.address.equals("") && !params.address.matches("^[0-9 \\p{L}0-9 .,'-]+")) {
					valid = false
					flash.message = "${message(code: 'company.address.format')}"
					render(view: "edit", model:[orderInstance: orderInstance, currentUser: currentUser])
					return
				}
				if (params.price.equals("")) {
					valid = false
					flash.message = "${message(code: 'order.price.blank')}"
					render(view: "edit", model:[orderInstance: orderInstance, currentUser: currentUser])
					return
				} else if (!params.price.matches(priceReg)) {
					valid = false
					flash.message = "${message(code: 'order.price.format')}"
					render(view: "edit", model:[orderInstance: orderInstance, currentUser: currentUser])
					return
				}
				/*
				 if (params.timeLimit.equals("")) {
				 valid = false
				 flash.message = "${message(code: 'employee.timeLimit.blank')}"
				 render(view: "edit", model:[orderInstance: orderInstance, currentUser: currentUser])
				 return
				 }
				 */
				/*if (params.moneyCode.equals("")) {
				 valid = false
				 flash.message = "${message(code: 'employee.moneyCode.blank')}"
				 render(view: "edit", model:[orderInstance: orderInstance, currentUser: currentUser])
				 return
				 } else if (!params.moneyCode.matches("[A-Z]{3}")) {
				 valid = false
				 flash.message = "${message(code: 'employee.moneyCode.format')}"
				 render(view: "edit", model:[orderInstance: orderInstance, currentUser: currentUser])
				 return
				 }*/
				String nameBlank
				String specialChar
				String birth
				params.passenger.each { k,v ->
					if (v instanceof Map) {
						if(v.name.equals("")) {
							nameBlank = "nameBlank"
						}
						if (!v.name.matches("^[\\p{L}0-9 .'-]+")) {
							specialChar = "special"
						}
						if(v.dateofBirth.equals("") && v.gender > 1) {
							birth = "birth"
						}
					}
				}

				if (nameBlank.equals("nameBlank")) {
					valid = false
					flash.message = "${message(code: 'passenger.name.blank')}"
					render(view: "edit", model:[orderInstance: orderInstance, currentUser: currentUser])
					return
				}
				if (specialChar.equals("special")) {
					valid = false
					flash.message = "${message(code: 'passenger.name.special.characters')}"
					render(view: "edit", model:[orderInstance: orderInstance, currentUser: currentUser])
					return
				}
				if (birth.equals("birth")) {
					valid = false
					flash.message = "${message(code: 'passenger.birth.blank')}"
					render(view: "edit", model:[orderInstance: orderInstance, currentUser: currentUser])
					return
				}
				def messages = []
				/*params.orderDetail.each { k,v ->
					if (v instanceof Map) {
						if (v.departure.equals("")) {
							messages << "${message(code: 'order.detail.departure.blank')}"
						}
						else if (!v.departure.matches("[A-Z]{3}")) {
							messages << "${message(code: 'order.detail.departure.special.characters')}"
						}
						if (v.arrival.equals("")) {
							messages << "${message(code: 'order.detail.arrival.blank')}"
						}
						else if (!v.arrival.matches("[A-Z]{3}")) {
							messages = "${message(code: 'order.detail.arrival.special.characters')}"
						}
					}
				}
				for (message in messages) {
					//valid = false
					flash.message = message
					render(view: "edit", model:[orderInstance: orderInstance, currentUser: currentUser])
					return
				}*/
				if (valid) {
					orderService.updateOrInsert(params)
				}
			} else {
				orderService.updateRemark(params.remark.content, orderInstance, currentUser)
			}
		} catch (ValidationException e) {
			def order = Order.read(params.id)
			order.errors = e.errors
			render view: "edit", model: [orderInstance:order, currentUser: currentUser]
		}
		flash.message = "${message(code: 'default.updated.message', args: [message(code: 'order.label', default: 'Order'), orderInstance.id])}"
		chain(action: "edit", params: [user: currentUser.id, id: orderInstance.id])
	}

	def delete = {
		def orderInstance = Order.get(params.id)
		if (orderInstance) {
			try {
				orderInstance.delete(flush: true)
				flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'order.label', default: 'Order'), params.id])}"
				redirect(action: "list")
			}
			catch (org.springframework.dao.DataIntegrityViolationException e) {
				flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'order.label', default: 'Order'), params.id])}"
				redirect(action: "show", id: params.id)
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'order.label', default: 'Order'), params.id])}"
			redirect(action: "list")
		}
	}

	//Forward order to another employee
	def forwardOrder = {
		def currentUser = CMSUser.findByUsername(springSecurityService.authentication.name)
		def currentOrder = Order.findById(params.orderEmpId)
		def employee = CMSUser.createCriteria()
		def results = employee.list {
			ne("username", currentUser.username)
			order("id", "desc")
		}
		render template: 'forwardorder' , model:[employees: results, currentUser: currentUser, currentOrder: currentOrder]
	}

	//Deactive order
	def activeDeactiveOrder = {
		def currentOrder = Order.findById(params.orderId)
		def currentOrderEmployee = OrderEmployee.get(params.orderId)
		if(currentOrderEmployee == null){
			currentOrderEmployee = new OrderEmployee()
			currentOrderEmployee.order = currentOrder
		}
		def currentUser = CMSUser.findByUsername(springSecurityService.authentication.name)
		currentOrder.showStatus = (params.status).toShort()
		currentOrderEmployee.status = (params.status).toShort()
		currentOrderEmployee.save(flush:true)

		if (currentOrder.save(flush: true)) {
			if(params.int('status'))
			flash.message = "${message(code: 'order.show.message', args: [message(code: 'order.label', default: 'Order'), currentOrder.id])}"
			else
			flash.message = "${message(code: 'order.hide.message', args: [message(code: 'order.label', default: 'Order'), currentOrder.id])}"
			redirect(action: "list", id: currentOrder.id)
		}
		else {
			render(view: "edit", model: [orderInstance: currentOrder, currentUser: currentUser])
		}
	}

	def savePassenger = {
		def jsonObject = request.JSON
		Order order = Order.findById(jsonObject.order)
		def passenger = new Passenger()
		Short genderType = jsonObject.gender.toShort()
		Short kidNumber = order.kidNumber
		Short adultNumber = order.adultNumber
		Short infantNumber = order.infantNumber
		passenger.name = jsonObject.name
		passenger.gender = genderType
		passenger.dateOfBirth = genderType > (short)1 ? DATE_FORMATTER.parseDateTime(jsonObject.dateOfBirth).toDate() : null
		passenger.order = order
		Short newPassenger = (short)1
		if(passenger.save(flush:true)) {
			//insert outbound luggage
			if(jsonObject.obairline != 'undefined'){
				def luggage = new Luggage()
				luggage.passenger = passenger
				luggage.airlineCode = jsonObject.obairline
				luggage.baggage = jsonObject.outboundLuggage.toLong()
				luggage.isDeparture = (short)1
				luggage.save(flush:true)
			}

			//insert inbound luggage()
			if(jsonObject.ibairline != 'undefined'){
				def luggage = new Luggage()
				luggage.passenger = passenger
				luggage.airlineCode = jsonObject.ibairline
				luggage.baggage = jsonObject.inboundLuggage.toLong()
				luggage.isDeparture = (short)0
				luggage.save(flush:true)
			}

			if (genderType < (short)2) {
				order.adultNumber = adultNumber + newPassenger
				render '{"message":"success", "passType":"adult", "id":"'+passenger.id+'"}'
			} else if (genderType == (short)2 || genderType == (short)3) {
				order.kidNumber = kidNumber + newPassenger
				render '{"message":"success", "passType":"kid", "id":"'+passenger.id+'"}'
			} else {
				order.infantNumber = infantNumber + newPassenger
				render '{"message":"success", "passType":"infant", "id":"'+passenger.id+'"}'
			}
			order.save(flush:true)

		} else {
			render '{"message":"passenger save fail"}'
		}
	}

	def deletePassenger = {
		def jsonObject = request.JSON
		Integer id = jsonObject.id.equals("") ? 0 : jsonObject.id.toInteger()
		def passenger = Passenger.get(id)
		if (passenger) {
			Order order = passenger.order
			Short kidNumber = order.kidNumber
			Short adultNumber = order.adultNumber
			Short infantNumber = order.infantNumber
			Short gender = passenger.gender
			Short numOfPassenger = (short)1
			try {
				passenger.delete(flush: true)
				if (gender < (short)2) {
					order.adultNumber = adultNumber - numOfPassenger
					render '{"message":"success", "passType":"adult"}'
				} else if (gender == (short)2 || gender == (short)3) {
					order.kidNumber = kidNumber - numOfPassenger
					render '{"message":"success", "passType":"kid"}'
				} else {
					order.infantNumber = infantNumber - numOfPassenger
					render '{"message":"success", "passType":"infant"}'
				}
				order.save(flush:true)
			}
			catch (org.springframework.dao.DataIntegrityViolationException e) {
				render '{"message":"Error when delete passenger"}'
			}
		}
		else {
			render '{"message":"passenger not found"}'
		}
	}

	//Send mail
	def mailInfo = {
		def order = Order.findById(params.orderId)
		def passengers = Passenger.findAllByOrder(order)
		def tripType = OrderDetail.findAllByOrderAndTripType(order, 2)

		def oseats = []
		def iseats = []
		def otickets = []
		def itickets = []

		passengers.each { passenger ->
			if(passenger.seatNumber != null){
			String[] seats = passenger.seatNumber.split(",")
				if(seats.length > 0){
					oseats << seats[0]
				}else{
					oseats << ""
				}

				if(seats.length > 1){
					iseats << seats[1]
				}else{
					iseats << ""
					}
				}

			if(passenger.ticketNumber != null){
				String[] tickets = passenger.ticketNumber.split(",")

				if(tickets.length > 0){
					otickets << otickets[0]
				}else{
					otickets << ""
				}

				if(tickets.length > 1){
					itickets << tickets[1]
				}else{
					itickets << ""
					}
				}
		}

		String customerMail = params.email
		order.customer.email = customerMail
		order.save(flush:true)
		render template: 'sendmail', model:[passengers: passengers, order: order, mail: customerMail, tripType : tripType, oseats:oseats, iseats:iseats, otickets:otickets, itickets:itickets]
	}

	def sendMail = {
		def order = Order.findById(params.orderId)
		params.outBoundSeatNumber.each { k,v ->
			if (v instanceof Map) {
				def passenger = Passenger.get(k)
				if(params.inBoundSeatNumber != null && !('').equals(params.inBoundSeatNumber)){
					params.inBoundSeatNumber.each { i,j ->
						if (v instanceof Map) {
							if(i==k)
							passenger.seatNumber = v.number + ',' + j.number
						}
					}
				} else {
					passenger.seatNumber = v.number
				}
				bindData(passenger, v, ['_number'])
				passenger.save(flush: true)
			}
		}

		params.outBoundTicketNumber.each { k,v ->
			if (v instanceof Map) {
				def passenger = Passenger.get(k)
				if(params.inBoundTicketNumber != null && !('').equals(params.inBoundTicketNumber)){
					params.inBoundTicketNumber.each { i,j ->
						if (v instanceof Map) {
							if(i==k)
							passenger.ticketNumber = v.number + ',' + j.number
						}
					}
				} else {
					passenger.ticketNumber = v.number
				}
				bindData(passenger, v, ['_number'])
				passenger.save(flush: true)
			}
		}

		if(order) {
			order.reservationCode = params.reservationCode
			order.status = (short)2
			order.save(flush: true)
		}
		Long customerId = order.customer.id.toLong()
		Long orderId = order.id.toLong()
		String customerName = order.customer.name
		String customerEmail = params.customerEmail
		Email email = new Email()
		email.sendConfirmMail("${message(code: 'email.mail2.title')}",customerId, customerName, orderId, customerEmail)
		redirect(action: "list")
	}

	def saveMail = {
		def order = Order.findById(params.orderId)
		params.outBoundSeatNumber.each { k,v ->
			if (v instanceof Map) {
				def passenger = Passenger.get(k)
				if(params.inBoundSeatNumber != null && !('').equals(params.inBoundSeatNumber)){
					params.inBoundSeatNumber.each { i,j ->
						if (v instanceof Map) {
							if(i==k)
							passenger.seatNumber = v.number + ',' + j.number
						}
					}
				} else {
					passenger.seatNumber = v.number
				}
				bindData(passenger, v, ['_number'])
				passenger.save(flush: true)
			}
		}

		params.outBoundTicketNumber.each { k,v ->
			if (v instanceof Map) {
				def passenger = Passenger.get(k)
				if(params.inBoundTicketNumber != null && !('').equals(params.inBoundTicketNumber)){
					params.inBoundTicketNumber.each { i,j ->
						if (v instanceof Map) {
							if(i==k)
							passenger.ticketNumber = v.number + ',' + j.number
						}
					}
				} else {
					passenger.ticketNumber = v.number
				}
				bindData(passenger, v, ['_number'])
				passenger.save(flush: true)
			}
		}

		if(order) {
			order.reservationCode = params.reservationCode
			order.status = (short)2
			order.save(flush: true)
		}
		redirect(action: "list")
	}
}
