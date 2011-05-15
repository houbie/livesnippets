<%@ page import="org.livesnippets.webflow.Project" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'project.label', default: 'Project')}" />
		<title>Project info</title>
	</head>
	<body>
		<a href="#create-project" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="create-project" class="content scaffold-create" role="main">
            <flow:breadCrumbs/>
			<h1>Project info</h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${projectInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${projectInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<g:form action="newProjectWizard" >
				<fieldset class="form">

					<div class="fieldcontain ${hasErrors(bean: projectInstance, field: 'name', 'error')} required">
						<label for="name"><g:message code="project.name.label" default="Name" /><span class="required-indicator">*</span></label>
						<g:textField name="name" required="" value="${projectInstance?.name}"/>
					</div>

					<div class="fieldcontain ${hasErrors(bean: projectInstance, field: 'description', 'error')} required">
						<label for="description"><g:message code="project.description.label" default="Description" /><span class="required-indicator">*</span></label>
						<g:textArea name="description" cols="40" rows="5" required="" value="${projectInstance?.description}"/>
					</div>
				</fieldset>
				<fieldset class="buttons">
					<g:submitButton name="next" value="Next" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
