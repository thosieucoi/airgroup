<head>
    <meta name='layout' content='admin' />
    <r:require modules="bootstrap"/>
    <title><g:message code="springSecurity.denied.title" /></title>
</head>

<body>
    <div class='container'>
        <div class='alert alert-block alert-error'>
            <g:message code="springSecurity.denied.message" />
            <br/><br/>
            <g:link class="btn btn-inverse" uri="/CMSFeedback/list">Return Home</g:link>
        </div>
    </div>
</body>