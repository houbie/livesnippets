

<%@ page import="org.livesnippets.webflow.Project" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'project.label', default: 'Project')}" />
		<title><g:message code="default.edit.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#edit-project" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="edit-project" class="content scaffold-edit" role="main">
			<h1><g:message code="default.edit.label" args="[entityName]" /></h1>
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
			<g:form method="post" >
				<g:hiddenField name="id" value="${projectInstance?.id}" />
				<g:hiddenField name="version" value="${projectInstance?.version}" />
				<fieldset class="form">
				
					<div class="fieldcontain ${hasErrors(bean: projectInstance, field: 'name', 'error')} required">
						<label for="name"><g:message code="project.name.label" default="Name" /><span class="required-indicator">*</span></label>
						<g:textField name="name" required="" value="${projectInstance?.name}"/>
					</div>
				
					<div class="fieldcontain ${hasErrors(bean: projectInstance, field: 'description', 'error')} required">
						<label for="description"><g:message code="project.description.label" default="Description" /><span class="required-indicator">*</span></label>
						<g:textArea name="description" cols="40" rows="5" required="" value="${projectInstance?.description}"/>
					</div>
				
					<div class="fieldcontain ${hasErrors(bean: projectInstance, field: 'lead', 'error')} required">
						<label for="lead"><g:message code="project.lead.label" default="Lead" /><span class="required-indicator">*</span></label>
						<g:select id="lead" name="lead.id" from="${org.livesnippets.webflow.Developer.list()}" optionKey="id" required="" value="${projectInstance?.lead?.id}" class="many-to-one"/>
					</div>
				
					<div class="fieldcontain ${hasErrors(bean: projectInstance, field: 'stories', 'error')} ">
						<label for="stories"><g:message code="project.stories.label" default="Stories" /></label>
						<g:select name="stories" from="${org.livesnippets.webflow.UserStory.list()}" multiple="multiple" optionKey="id" size="5" value="${projectInstance?.stories*.id}" class="many-to-many"/>
					</div>
				
					<div class="fieldcontain ${hasErrors(bean: projectInstance, field: 'team', 'error')} ">
						<label for="team"><g:message code="project.team.label" default="Team" /></label>
						<g:select name="team" from="${org.livesnippets.webflow.Developer.list()}" multiple="multiple" optionKey="id" size="5" value="${projectInstance?.team*.id}" class="many-to-many"/>
					</div>
				
				</fieldset>
				<fieldset class="buttons">
					<g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" />
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" formnovalidate="" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
