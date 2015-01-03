package com.airgroup.domain

import java.util.Map;

import org.weceem.content.WcmContent;
import org.weceem.content.WcmTemplate;
import org.weceem.util.ContentUtils

class NewsContent extends WcmContent  {
	/**
	 * Define the g.resource URL parameters to create a link to the icon for this content type
	 */
	static icon = [plugin: "weceem", dir: "_weceem/images/weceem/content-icons", file: "html-file-32.png"]

	/* Add your custom content fields here */
	//static belongsTo=[category:Category]
	String content
	WcmTemplate template
	String aliasURI = "News"
	String name = "News"
	Integer validFor = 0;
	String category
	String introduction
	String identifier = "News"
	String active
	String image

	 /**
	 * Standard Grails constraints for your properties
	 */
	static constraints = {
		content(maxSize: WcmContent.MAX_CONTENT_SIZE)
		introduction(maxSize: WcmContent.MAX_CONTENT_SIZE)
		template(nullable: true)
		category(inList:["Tin tức","Khuyến mại"])
		active(inList:["Active","Deactive"])
	}

	/**
	 * Like constraints, but defines the editors (fields) to be shown for this content
	 * The group can be "extra", "advanced" or "meta", but is optional.
	 * Setting "editor" is not necessary, it will default to the editor for the type
	 * of the property. You can override this to use a custom editor you define.
	 */
	static editors = {
		title(editor:'String')
		introduction(editor:'HTMLContent')
		content(editor:'HTMLContent') // Forces use of the HTMLContent rich editor
		image(editor:'HTMLContent')
		template()
		aliasURI(hidden:true)
		identifier(hidden:true)
		category(editor:'SelectInList')
		active(editor:'SelectInList')
		name(hidden:true)
		parent(hidden:true)
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

	/* Override normal Searchable values here to customize search indexing */
	/*

	static searchable = {
	}

	*/

	/* If you want this content to support Templates uncomment this */
	/*

	WcmTemplate template

	*/

	/* If you depend on other nodes e.g. templates, you must return the comma-delimited list of content URIs here */
	/*
	@Override
	String getHardDependencies() {
		// A template is an implicit dependency for the node, any changes to the template or its deps
		// means we have to change too.
		def t = TemplateUtils.getTemplateForContent(this)
		return t ? t.absoluteURI : ''
	}
	*/

	/**
	 * Customize handling of the request - use controller or RenderEngine renderGSP/renderContent methods
	 */
	/*
	static handleRequest = { content ->
		if (content.allowGSP) {
			renderGSPContent(content)
		} else {
			renderContent(content)
		}
	}
	*/
}
