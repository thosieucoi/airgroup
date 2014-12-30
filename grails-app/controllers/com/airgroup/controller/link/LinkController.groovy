package com.airgroup.controller.link

import org.weceem.content.WcmSpace
import grails.converters.JSON
import com.airgroup.domain.Link

class LinkController {

	def linkService
	static allowedMethods = [delete: ["POST","GET"]]
	
   
	def listFrontEndCompany = {
		def linkNational = Link.findAllByCategory("Company");
		
		List<Link> list_link_national= new ArrayList<Link>()
		for(Link link : linkNational){
			Link newLink = new Link()
			newLink.title = link.title
			newLink.id = link.id
			list_link_national.add(newLink)
		}
		render list_link_national as JSON
	}
	
	def listFrontEndInternational = {
		def linkNational = Link.findAllByCategory("International");
		
		List<Link> list_link_national= new ArrayList<Link>()
		for(Link link : linkNational){
			Link newLink = new Link()
			newLink.title = link.title
			newLink.id = link.id
			list_link_national.add(newLink)
		}
		render list_link_national as JSON
	}
	
	def listFrontEndLocal = {
		def linkNational = Link.findAllByCategory("Local");
		
		List<Link> list_link_national= new ArrayList<Link>()
		for(Link link : linkNational){
			Link newLink = new Link()
			newLink.title = link.title
			newLink.id = link.id
			list_link_national.add(newLink)
		}
		render list_link_national as JSON
	}
	
	def listFrontEndType = {
		def linkNational = Link.findAllByCategory("Type");
		
		List<Link> list_link_national= new ArrayList<Link>()
		for(Link link : linkNational){
			Link newLink = new Link()
			newLink.title = link.title
			newLink.id = link.id
			list_link_national.add(newLink)
		}
		render list_link_national as JSON
	}
	
   def listBackEnd = {
		def linkList = linkService.getLinkListBackEnd(params)
	}
   
   def getContentDetails = {
	   int contentId = Integer.parseInt(params.contentId)
	   def contentDetails = linkService.getContentDetails(contentId).contentDetails
	   [contentDetails:contentDetails]
   }
   
   def create = {
	   redirect(uri:"/admin/editor/create?type=com.airgroup.domain.Link&space.id=" + WcmSpace.findByName("Default").id)
   }
   
   def edit  = {
	   redirect(uri:"/admin/editor/edit/" + params.id)
   }

   def delete = {
	   def delLinkList = params.list("link")
	   try{
		   if(params.id){
			   linkService.deleteLink(params.id)
		   } else{
			   for(link in delLinkList){
				   linkService.deleteLink(link)
			   }
		   }
		   if(params.id){
			   flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'CMSUser.label', default: 'Link'), params.id])}"
		   } else {
			   flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'CMSUser.label', default: 'Link'), delLinkList])}"
		   }
	   } catch(org.springframework.dao.DataIntegrityViolationException e){
		   flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'CMSUser.label', default: 'Link'), params.id])}"
	   }
	   redirect(action:"listBackEnd")
   }

   
}
