package com.airgroup.service.order.employee

import com.airgroup.domain.OrderEmployee;
import com.airgroup.domain.Order;
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormatter
import org.joda.time.format.DateTimeFormat
import org.weceem.auth.CMSRole;
import org.weceem.auth.CMSUser;

class OrderEmployeeService {

    static transactional = true
	def springSecurityService
	
	/**
	 * Order status<br>
	 * 9 = Done for Recall<br>
	 * 10 = Done for Time-limit<br>
	 * 2 = Done for both<br>
	 *
	 **/
	static final IS_DONE_RECALL = (short)9
	static final IS_DONE_TIME_LIMIT = (short)10
	static final IS_DONE_ALL = (short)2
	/**
	 * 
	 * OrderEmployee Status<br>
	 * 1 = Booking or Processing
	 * 0 = Cancel book
	 * 
	 * */
	static final BOOKED = (short)1
	//Decide when to allow recall or time limit to display
	static final getAll = 0
	static final getRecallOnly = 1
	static final getTimeLimitOnly = 2

	
	/**
	 * 
	 * Return the list of orders which are owned by the current user and the current user's id
	 * 
	 * */
	def getCurrentEmployeeInfo(){
		//Get the current employee
		if(!springSecurityService.principal.equals("anonymousUser") && springSecurityService.principal != null){
			if(springSecurityService.principal.id != null){
				def currentUser = CMSUser.get(springSecurityService.principal.id)
				//Get the list of orders of current employee
				def lstOrderEmpId = OrderEmployee.executeQuery('SELECT id FROM OrderEmployee WHERE processEmp = ' + currentUser.id)
				
				//Get the list of order details which are owned by current employee
				def lstOrder
				def currentUserRole = currentUser.getRoleAuthorities()
				if(!lstOrderEmpId.isEmpty() && lstOrderEmpId != null){
					if(currentUserRole.size() > 1){
						lstOrder = Order.getAll()
					} else if(currentUserRole.size() == 1){
						lstOrder = Order.getAll(lstOrderEmpId)
					}
				} else {
					lstOrder= null
				}
				[currentUser : currentUser, lstOrder : lstOrder]
			}
		}
	}
	
    def getRecallAndTimeLimitList(params) {
		def currentEmpInfo = getCurrentEmployeeInfo()
		Date currentDate = new Date()
		if(currentEmpInfo != null && currentEmpInfo.currentUser != null){
			//Get role of the current user
			def currentUserRole = currentEmpInfo.currentUser.getRoleAuthorities()
			//If the user is an admin, show all Recall and Time limit of others user for him
			def lstRecallAndTimeLimit
			int option = getAll
			if(currentUserRole.size() > 1){
				//Return list of both recall and time limit information
				lstRecallAndTimeLimit = OrderEmployee.executeQuery('SELECT id,notification,timeLimit '+
																   'FROM OrderEmployee '+
																   'WHERE (status = :isBooked OR status = :isDoneRecall OR status = :isDoneTimeLimit )' +
																   ' AND (notification <= :currentDate OR timeLimit <= :currentDate)',
																   [isBooked : BOOKED, isDoneRecall : IS_DONE_RECALL , isDoneTimeLimit : IS_DONE_TIME_LIMIT , currentDate : currentDate]
																   )
				//Decide when to display Recall column or TimeLimit column
				if(params.id == "recall"){
					option = getRecallOnly
					lstRecallAndTimeLimit = OrderEmployee.executeQuery('SELECT id,notification,timeLimit '+
																	   'FROM OrderEmployee '+
																	   'WHERE notification <= :currentDate' +
																	   ' AND (status= :isBooked OR status = :isDoneTimeLimit)' +
																	   ' AND notification <> NULL AND notification > 0',
																	   [currentDate : currentDate, isBooked : BOOKED, isDoneTimeLimit : IS_DONE_TIME_LIMIT]
																	  )
				} else if(params.id == "timeLimit"){
					option = getTimeLimitOnly
					//Clear the list before add new time limit element
					lstRecallAndTimeLimit = OrderEmployee.executeQuery('SELECT id,notification,timeLimit '+
																	   'FROM OrderEmployee '+
																	   'WHERE timeLimit <= :currentDate' +
																	   ' AND (status= :isBooked OR status = :isDoneRecall)' +
																	   ' AND timeLimit <> NULL AND timeLimit > 0',
																	   [currentDate : currentDate, isBooked : BOOKED, isDoneRecall : IS_DONE_RECALL]
																	  )
				}
			} else if(currentUserRole.size() == 1){
				//Return list of both recall and time limit information
				lstRecallAndTimeLimit = OrderEmployee.executeQuery('SELECT id,notification,timeLimit '+
																   'FROM OrderEmployee '+
																   'WHERE processEmp=' + currentEmpInfo.currentUser.id +
																   ' AND (status = :isBooked OR status = :isDoneRecall OR status = :isDoneTimeLimit )' +
																   ' AND (notification <= :currentDate OR timeLimit <= :currentDate)',
																   [isBooked : BOOKED, isDoneRecall : IS_DONE_RECALL , isDoneTimeLimit : IS_DONE_TIME_LIMIT , currentDate : currentDate]
																   )
				//Decide when to display Recall column or TimeLimit column
				if(params.id == "recall"){
					option = getRecallOnly
					lstRecallAndTimeLimit = OrderEmployee.executeQuery('SELECT id,notification,timeLimit '+
																	   'FROM OrderEmployee '+
																	   'WHERE processEmp=' + currentEmpInfo.currentUser.id +
																	   ' AND notification <= :currentDate' +
																	   ' AND (status= :isBooked OR status = :isDoneTimeLimit)' +
																	   ' AND notification <> NULL AND notification > 0',
																	   [currentDate : currentDate, isBooked : BOOKED, isDoneTimeLimit : IS_DONE_TIME_LIMIT]
																	  )
				} else if(params.id == "timeLimit"){
					option = getTimeLimitOnly
					//Clear the list before add new time limit element
					lstRecallAndTimeLimit = OrderEmployee.executeQuery('SELECT id,notification,timeLimit '+
																	   'FROM OrderEmployee '+
																	   'WHERE processEmp=' + currentEmpInfo.currentUser.id +
																	   ' AND timeLimit <= :currentDate' +
																	   ' AND (status= :isBooked OR status = :isDoneRecall)' +
																	   ' AND timeLimit <> NULL AND timeLimit > 0',
																	   [currentDate : currentDate, isBooked : BOOKED, isDoneRecall : IS_DONE_RECALL]
																	  )
				}
			}
			
			
			//Add recall and time limit to a list of OrderEmployee object
			List<OrderEmployee> lstOrderEmployee = new ArrayList<OrderEmployee>()
			for(int i=0; i<lstRecallAndTimeLimit.size(); i++){
				OrderEmployee orderEmp = new OrderEmployee()
				orderEmp.id = (long)lstRecallAndTimeLimit.get(i)[0]
				orderEmp.notification = lstRecallAndTimeLimit.get(i)[1]
				orderEmp.timeLimit = lstRecallAndTimeLimit.get(i)[2]
				lstOrderEmployee.add(orderEmp)
			}
			[lstRecallAndTimeLimit : lstOrderEmployee, lstOrder : currentEmpInfo.lstOrder, option : option]
		}
    }
	
	def finishOrder(params){
		short option = Short.valueOf(params.option)
		def orderEmployeeInstance = OrderEmployee.get(params.id)
		if(option == getAll){
			orderEmployeeInstance.status = IS_DONE_ALL
			orderEmployeeInstance.save(flush:true)
		} else if(option == getRecallOnly){
			if(orderEmployeeInstance.status == IS_DONE_TIME_LIMIT){
				orderEmployeeInstance.status = IS_DONE_ALL
				orderEmployeeInstance.save(flush:true)
			} else {
				orderEmployeeInstance.status = IS_DONE_RECALL
				orderEmployeeInstance.save(flush:true)
			}
		} else if (option == getTimeLimitOnly) {
			if(orderEmployeeInstance.status == IS_DONE_RECALL){
				orderEmployeeInstance.status = IS_DONE_ALL
				orderEmployeeInstance.save(flush:true)
			} else {
				orderEmployeeInstance.status = IS_DONE_TIME_LIMIT
				orderEmployeeInstance.save(flush:true)
			}
		}
		
	}
	
	def getRecallAndTimeLimitNumber(){
		def currentEmpInfo = getCurrentEmployeeInfo()
		Date currentDate = new Date()
		//Get role of the current user
		if(currentEmpInfo != null && currentEmpInfo.currentUser != null){
			//If the user is an admin, show all Recall and Time limit of others user for him
			def currentUserRole = currentEmpInfo.currentUser.getRoleAuthorities()
			def numbersOfRecallObj
			def numbersOfTimeLimitObj
			if(currentUserRole.size() > 1){
				//Get the number of recall
				numbersOfRecallObj = OrderEmployee.executeQuery('SELECT count(notification) '+
																'FROM OrderEmployee '+
																'WHERE notification <= :currentDate' +
																' AND (status= :isBooked OR status = :isDoneTimeLimit)',
																[currentDate : currentDate, isBooked : BOOKED, isDoneTimeLimit : IS_DONE_TIME_LIMIT]
															   )
				//Get the number of time limit
				numbersOfTimeLimitObj = OrderEmployee.executeQuery('SELECT count(timeLimit) '+
																   'FROM OrderEmployee '+
																   'WHERE timeLimit <= :currentDate' +
																   ' AND (status= :isBooked OR status = :isDoneRecall)',
																   [currentDate : currentDate, isBooked : BOOKED, isDoneRecall : IS_DONE_RECALL]
																  )
			} else if(currentUserRole.size() == 1){
				//Get the number of recall
				numbersOfRecallObj = OrderEmployee.executeQuery('SELECT count(notification) '+
																'FROM OrderEmployee '+
																'WHERE processEmp=' + currentEmpInfo.currentUser.id +
																' AND notification <= :currentDate' +
																' AND (status= :isBooked OR status = :isDoneTimeLimit)',
																[currentDate : currentDate, isBooked : BOOKED, isDoneTimeLimit : IS_DONE_TIME_LIMIT]
															   )
				//Get the number of time limit
				numbersOfTimeLimitObj = OrderEmployee.executeQuery('SELECT count(timeLimit) '+
																   'FROM OrderEmployee '+
																   'WHERE processEmp=' + currentEmpInfo.currentUser.id +
																   ' AND timeLimit <= :currentDate' +
																   ' AND (status= :isBooked OR status = :isDoneRecall)',
																   [currentDate : currentDate, isBooked : BOOKED, isDoneRecall : IS_DONE_RECALL]
																  )
			}
			int numbersOfRecall = (int)numbersOfRecallObj.get(0)
			int numbersOfTimeLimit = (int)numbersOfTimeLimitObj.get(0)
			[numbersOfRecall : numbersOfRecall, numbersOfTimeLimit : numbersOfTimeLimit]
		}
	}
	
}