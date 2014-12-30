package com.airgroup.service.tips

import com.airgroup.domain.Order
import com.airgroup.domain.OrderEmployee
import org.weceem.auth.CMSUser;
import com.airgroup.domain.Tips;

class TipsService {
	
	static final CANCELED = (short)3
	static final DEACTIVE = (short)0
	static transactional = true
	
	def getTipsList(params, Date startDate, Date endDate){
		//Get all employee
		params.max = 20
		params.offset = params.offset? params.offset.toInteger():0
		def lstEmployeeWithOrder = CMSUser.createCriteria().list(max: params.max, offset: params.offset){
			and{
				order("id", "desc")
			}
		}
		
		//Get the last tips' value
		def lastTips = Tips.getAll()
		
		if(lstEmployeeWithOrder != null){
			List<CMSUser> lstUser = lstEmployeeWithOrder.toList()
			
			//Get the domestic and international order of each user
			List<List<Object>> numsOfDomesticOrder = new ArrayList<List<Object>>()
			List<Object> numsOfDomestic
			List<List<Integer>> numsOfInternationalOrder = new ArrayList<List<Integer>>()
			List<Integer> numsOfInternational = new ArrayList<Integer>()
			
			//Get the domestic order
			def lstDomesticOrderId = Order.executeQuery('SELECT id FROM Order WHERE isDomestic=0')
			//Get the international order
			def lstInternationalOrderId = Order.executeQuery('SELECT id FROM Order WHERE isDomestic=1')
			
			for(int i=0; i < lstUser.size(); i++){
				//Add domestic order info to list
				numsOfDomestic = new ArrayList<Integer>()
				def lstBookDomesticOrder
				def lstProcessDomesticOrder
				if(lstDomesticOrderId != null && !lstDomesticOrderId.isEmpty()){
					lstBookDomesticOrder = OrderEmployee.findAll('FROM OrderEmployee '+
																 'WHERE bookEmp='+lstUser.get(i).id+
																 ' AND id IN (:lstDomesticOrderId) '+
																 ' AND executeTime BETWEEN :startSearchDate AND :endSearchDate'+
																 ' AND status <> :canceled AND status <> :deactive'
																 , [startSearchDate : startDate, endSearchDate : endDate, lstDomesticOrderId : lstDomesticOrderId,
																	canceled : CANCELED, deactive : DEACTIVE
																   ]
																 )
					lstProcessDomesticOrder = OrderEmployee.findAll('FROM OrderEmployee '+
																    'WHERE processEmp='+lstUser.get(i).id+
																    ' AND id IN (:lstDomesticOrderId) '+
																	' AND executeTime BETWEEN :startSearchDate AND :endSearchDate'+
																	' AND status <> :canceled AND status <> :deactive'
																	, [startSearchDate : startDate, endSearchDate : endDate, lstDomesticOrderId : lstDomesticOrderId,
																	   canceled : CANCELED, deactive : DEACTIVE
																	  ]
																    )
					numsOfDomestic.add(lstBookDomesticOrder.size())
					numsOfDomestic.add(lstProcessDomesticOrder.size())
					numsOfDomestic.add(lstUser.get(i).id)
					numsOfDomesticOrder.add(numsOfDomestic)
				}
				
				
				//Add international order info to list
				numsOfInternational = new ArrayList<Integer>()
				def lstBooknumsInternationalOrder
				def lstProcessnumsInternationalOrder
				if(lstInternationalOrderId != null && !lstInternationalOrderId.isEmpty()){
					lstBooknumsInternationalOrder = OrderEmployee.findAll('FROM OrderEmployee '+
																		  'WHERE bookEmp='+lstUser.get(i).id+
																		  ' AND id IN (:lstInternationalOrderId)'+
																		  ' AND executeTime BETWEEN :startSearchDate AND :endSearchDate'+
																		  ' AND status <> :canceled  AND status <> :deactive'
																		  ,[startSearchDate : startDate, endSearchDate : endDate, lstInternationalOrderId : lstInternationalOrderId,
																			canceled : CANCELED, deactive : DEACTIVE
																		   ]
																		  )
					lstProcessnumsInternationalOrder = OrderEmployee.findAll('FROM OrderEmployee '+
																		     'WHERE processEmp='+lstUser.get(i).id+
																		     ' AND id IN (:lstInternationalOrderId)'+
																			 ' AND executeTime BETWEEN :startSearchDate AND :endSearchDate'+
																			 ' AND status <> :canceled  AND status <> :deactive'
																			 , [startSearchDate : startDate, endSearchDate : endDate, lstInternationalOrderId : lstInternationalOrderId,
																				canceled : CANCELED, deactive : DEACTIVE
																			   ]
																		     )
					numsOfInternational.add(lstBooknumsInternationalOrder.size())
					numsOfInternational.add(lstProcessnumsInternationalOrder.size())
					numsOfInternational.add(lstUser.get(i).id)
					numsOfInternationalOrder.add(numsOfInternational)
				}
				
			}
			
			//Total record for pagination
			int total = CMSUser.createCriteria().list{
				order("id", "desc")
			}.size()
			[lstUser : lstUser, numsOfDomesticOrder : numsOfDomesticOrder, numsOfInternationalOrder : numsOfInternationalOrder, lastTips : lastTips, total : total]
		}
	}
	
	def saveLastTips(Tips tips){
		def tipsInstance = Tips.getAll()
		if(tipsInstance == null || tipsInstance.toList().isEmpty()){
			tips.save(flush:true)
		} else if(tipsInstance != null && !tipsInstance.toList().isEmpty()){
			tipsInstance.get(0).lastDomesticBookedTips = tips.lastDomesticBookedTips
			tipsInstance.get(0).lastDomesticProcessTips = tips.lastDomesticProcessTips
			tipsInstance.get(0).lastInternationalBookedTips = tips.lastInternationalBookedTips
			tipsInstance.get(0).lastInternationalProcessTips = tips.lastInternationalProcessTips
			tipsInstance.get(0).save(flush:true)
		}
	}
	
}