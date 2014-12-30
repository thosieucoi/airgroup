<%@ page import="com.airgroup.domain.Tour"%>
<html>
<head>
	<meta name="layout" content="main" />
	<title>
		<g:message code="tour.label.header"/>
		<g:if test="${category=='Asia'}">
			<g:message code="tour.category.header.asia"/>
		</g:if>
		<g:elseif test="${category=='Europe'}">
			<g:message code="tour.category.header.europe"/>
		</g:elseif>
		<g:elseif test="${category=='America'}">
			<g:message code="tour.category.header.america"/>
		</g:elseif>
		<g:elseif test="${category=='South East Asia'}">
			<g:message code="tour.category.header.south.east.asia"/>
		</g:elseif>
		<g:elseif test="${category=='Domestic'}">
			<g:message code="tour.category.header.domestic"/>
		</g:elseif>
	</title>
</head>
<body>

    	<div class="nav hone">
    		<h1 class="listpage">
	    		<g:link controller="tour"><g:message code="tour.label.nav"/></g:link>
	    		<b>></b>
	    		<g:link url="javascript:void(0)" class="selected">
	    			<g:message code="tour.label.header"/>
	    			<g:if test="${category=='Asia'}">
						<g:message code="tour.category.nav.asia"/>
					</g:if>
					<g:elseif test="${category=='Europe'}">
						<g:message code="tour.category.nav.europe"/>
					</g:elseif>
					<g:elseif test="${category=='America'}">
						<g:message code="tour.category.nav.america"/>
					</g:elseif>
					<g:elseif test="${category=='South East Asia'}">
						<g:message code="tour.category.nav.south.east.asia"/>
					</g:elseif>
					<g:elseif test="${category=='Domestic'}">
						<g:message code="tour.category.nav.domestic"/>
					</g:elseif>
	    		</g:link>
    		</h1>
        	<div class="left tour">
	            		<h1><p>
	            			<g:message code="tour.label.header"/> 		
		            		<g:if test="${category=='Asia'}">
								<g:message code="tour.category.header.asia"/>
							</g:if>
							<g:elseif test="${category=='Europe'}">
								<g:message code="tour.category.header.europe"/>
							</g:elseif>
							<g:elseif test="${category=='America'}">
								<g:message code="tour.category.header.america"/>
							</g:elseif>
							<g:elseif test="${category=='South East Asia'}">
								<g:message code="tour.category.header.south.east.asia"/>
							</g:elseif>
							<g:elseif test="${category=='Domestic'}">
								<g:message code="tour.category.header.domestic"/>
							</g:elseif>
						</p></h1>
		                <div class="touris">
		                	<h2>
		                		<g:message code="tour.label.header"/>
		                		<g:if test="${category=='Asia'}">
									<g:message code="tour.category.nav.asia"/>
								</g:if>
								<g:elseif test="${category=='Europe'}">
									<g:message code="tour.category.nav.europe"/>
								</g:elseif>
								<g:elseif test="${category=='America'}">
									<g:message code="tour.category.nav.america"/>
								</g:elseif>
								<g:elseif test="${category=='South East Asia'}">
									<g:message code="tour.category.nav.south.east.asia"/>
								</g:elseif>
								<g:elseif test="${category=='Domestic'}">
									<g:message code="tour.category.nav.domestic"/>
								</g:elseif>
		                	</h2>
							<p style="font-size: 35px; font-weight: bold; color: red;"><g:message code="tour.message.not.found"/></p>
							<a href="${createLink(uri: '/tour/index')}" style="color: #19A4DC;text-decoration: underline;"><g:message code="tour.message.back"/></a>

		                    <g:if test="${category=='Asia'}">
								<img src="${resource(dir:'images', file:'dlnhatban.jpg') }" class="tourlist" />
							</g:if>
							<g:elseif test="${category=='Europe'}">
								<img src="${resource(dir:'images', file:'dlcau.jpg') }" class="tourlist" />
							</g:elseif>
							<g:elseif test="${category=='America'}">
								<img src="${resource(dir:'images', file:'dlcmy.jpg') }" class="tourlist" />
							</g:elseif>
							<g:elseif test="${category=='South East Asia'}">
								<img src="${resource(dir:'images', file:'dldnama.jpg') }" class="tourlist" />
							</g:elseif>
							<g:elseif test="${category=='Domestic'}">
								<img src="${resource(dir:'images', file:'dlnoidia.jpg') }" class="tourlist" />
							</g:elseif>
		                </div>
		                <g:if test="${introduction != null && introduction.size() > 0}">
		                	<g:if test="${introduction.get(0) != null}">
				                <div class="debook">
				                	 <g:if test="${category=='Asia'}">
										${introduction.get(0).introductionAsia}
									 </g:if>
									 <g:elseif test="${category=='Europe'}">
										${introduction.get(0).introductionEurope}
									 </g:elseif>
									 <g:elseif test="${category=='America'}">
										${introduction.get(0).introductionAmerica}
									 </g:elseif>
									 <g:elseif test="${category=='South East Asia'}">
										${introduction.get(0).introductionSouthEastAsia}
									 </g:elseif>
									 <g:elseif test="${category=='Domestic'}">
										${introduction.get(0).introductionDomestic}
									 </g:elseif>
				                </div>
			                </g:if>
		                </g:if>
      		</div>          
       </div>
</body>
</html>
