package com.airgroup.service.feedbackmng

import com.airgroup.domain.Feedback

/**
 * <p>
 * Provide business functions for controller 
 * </p>
 * @author nghiaTT
 * @version 1.0
 */
class FeedbackService {

	static transactional = true


	/**
	 * <p>
	 * Return a list of feedback from database
	 * Number of feedbacks base on input parameter. 
	 * </p>
	 * 
	 * @param params
	 * @return list
	 */
	def list(def params){
		params.max = Math.min(params.max ? params.int('max') : 5, 100)
		def ls = Feedback.createCriteria().list(
			max: params.max ?: null,
			offset: params.offset ?: null
		) {
			and {
				eq('status', params.status)
			}
			order('id', 'desc')
		}
		def totalCount = Feedback.executeQuery("from Feedback a where status = 1").size()
		[feedbackInstanceList: ls, feedbackTotal: totalCount, offset: params.offset, max: params.max]
	}

	/**
	 * <p>
	 * Save feedback to database
	 * </p>
	 * @param params
	 * @return object after inserted
	 */
	def save(def params){
		def feedbackInstance = new Feedback(params)
		feedbackInstance.sendDate=new Date()
		feedbackInstance.status=(short)0
		return feedbackInstance.save(flush:true)
	}
}
