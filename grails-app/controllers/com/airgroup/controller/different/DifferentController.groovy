/**
 * 
 */
package com.airgroup.controller.different
import org.weceem.content.*
import com.airgroup.domain.*;
/**
 * @author linhnd1
 *
 */
class DifferentController {
	def index = {
		if(!WcmContent.findByTitle('Different')){
			redirect(uri:"/admin/editor/create?parent.id=&type=com.airgroup.domain.DifferentContent&space.id=" + WcmSpace.findByName("Default").id)
		}
		else{
			redirect(uri:"/admin/editor/edit/" + WcmContent.findWhere(title:'Different').id)
		}
	}
	
	def policyFrontEnd = {
		redirect(uri:'/index')
	}
}
