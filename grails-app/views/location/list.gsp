
<%@ page import="com.airgroup.domain.Location" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'location.label', default: 'Location')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'location.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="city_id" title="${message(code: 'location.city_id.label', default: 'Cityid')}" />
                        
                            <g:sortableColumn property="code" title="${message(code: 'location.code.label', default: 'Code')}" />
                        
                            <g:sortableColumn property="country_code" title="${message(code: 'location.country_code.label', default: 'Countrycode')}" />
                        
                            <g:sortableColumn property="location_type" title="${message(code: 'location.location_type.label', default: 'Locationtype')}" />
                        
                            <g:sortableColumn property="name" title="${message(code: 'location.name.label', default: 'Name')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${locationInstanceList}" status="i" var="locationInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${locationInstance.id}">${fieldValue(bean: locationInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: locationInstance, field: "city_id")}</td>
                        
                            <td>${fieldValue(bean: locationInstance, field: "code")}</td>
                        
                            <td>${fieldValue(bean: locationInstance, field: "country_code")}</td>
                        
                            <td>${fieldValue(bean: locationInstance, field: "location_type")}</td>
                        
                            <td>${fieldValue(bean: locationInstance, field: "name")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${locationInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
