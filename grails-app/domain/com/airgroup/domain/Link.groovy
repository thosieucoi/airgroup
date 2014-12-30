package com.airgroup.domain

import org.weceem.content.WcmContent
import org.weceem.util.ContentUtils

/**
 * This is your Weceem content domain class. See the documentation at http://weceem.org for full details
 *
 * Summary: Add normal GORM properties for your custom content fields, including any relationships you need.
 * For non-standard types or associations you will need to implement custom wcm:editorXXXX tags to provide
 * editors for these. See the Weceem Plugin source for details (EditorTagLib.groovy)
 */

class Link extends WcmContent{

	/**
	 * Define the g.resource URL parameters to create a link to the icon for this content type
	 */
	static icon = [plugin: "weceem", dir: "_weceem/images/weceem/content-icons", file: "html-file-32.png"]

	/* Add your custom content fields here */
	//String title = "Link Title"
	String fromCode
	String toCode
	String category
	String content
	String aliasURI = UUID.randomUUID().toString();
	/**
	 * Standard Grails constraints for your properties
	 */
    static constraints = {
		title (matches: "^[A-Za-z](.)*")
		fromCode (maxSize:3, blank: false,  matches: "^[A-Z]{3}+")
		toCode (maxSize:3, blank: false,  matches: "^[A-Z]{3}+")
		category (inList:["International","Local","Company", "Type"])
		content (maxSize:10000)
    }

	static editors = {
		title(editor:'String')
		fromCode(editor:'String')
		toCode(editor:'String')
		category(editor:'SelectInList')
		content(editor:'HTMLContent')
		parent(hidden:true)
		aliasURI(hidden:true)
		orderIndex hidden:true
		createdBy hidden:true
		createdOn hidden: true
		changedBy hidden:true
		changedOn hidden: true
		publishFrom hidden:true
		publishUntil hidden:true
		validFor hidden:true   // cache maxAge
		contentDependencies hidden:true
		metaCreator hidden:true
		metaPublisher hidden:true
		description hidden:true
		identifier hidden:true
		metaSource hidden:true
		metaCopyright hidden:true
		language(hidden: true)
	}

	/**
	 * You must define any properties that are not to be saved, remembering to add in all the inherited
	 * ones as not all versions of Grails have correctly implemented this.
	 */
	static transients = WcmContent.transients + [ 'summary']

	/**
	 * Must be overriden by content types that can represent their content as text.
	 * Used for search results and versioning
	 */
	public String getContentAsText() {
		ContentUtils.htmlToText(content)
	}

	/**
	 * Should be overriden by content types that can represent their content as HTML.
	 * Used for wcm:content tag (content rendering)
	 */
	public String getContentAsHTML() {
		content
	}

	/**
	 * Return the map of properties to be stored with a content revision
	 */
	Map getVersioningProperties() {
		def r = super.getVersioningProperties() + [:] /* map of property name -> value */
		return r
	}

	/**
	 * Return the mime type to send to the client when this content is rendered
	 */
	String getMimeType() {
		"text/html; charset=UTF-8"
	}

	/**
	 * Overriden to return caption for menu items
	 */
	@Override
	public String getTitleForMenu() {
		title
	}

	/**
	 * Overriden to return title for HTML page meta
	 */
	@Override
	public String getTitleForHTML() {
		title
	}

	/**
	 * Return a plain text summary of this content node
	 */
	@Override
	public String getSummary() {
		ContentUtils.summarise(content, 100, '...')
	}

}
