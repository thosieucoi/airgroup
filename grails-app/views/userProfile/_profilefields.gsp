<g:each in="['username', 'name', 'phoneNumber', 'email', 'yahoo', 'skype']" var="prop">
        <div class="clear span-4">
            <bean:label beanName="CMSUserInstance" property="${prop}"/>
        </div>
        <div class="field prepend-1 span-13 last">
            <bean:field beanName="CMSUserInstance" property="${prop}" label="${false}"/>
        </div>
</g:each>

