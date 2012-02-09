<g:if test="${flash.message}">
    <div class="message" role="status">${flash.message}</div>
</g:if>
<g:hasErrors bean="${developerInstance}">
    <ul class="errors" role="alert">
        <g:eachError bean="${developerInstance}" var="error">
            <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
        </g:eachError>
    </ul>
</g:hasErrors>
<flow:formRemote name="userStoryForm" event="addAjax" update="editStories">
    <fieldset class="form">

        <div class="fieldcontain ${hasErrors(bean: userStoryInstance, field: 'name', 'error')} required">
            <label for="name"><g:message code="userStory.name.label" default="Name"/><span class="required-indicator">*</span></label>
            <g:textField name="name" required="" value="${userStoryInstance?.name}"/>
        </div>

        <div class="fieldcontain ${hasErrors(bean: userStoryInstance, field: 'description', 'error')} ">
            <label for="description"><g:message code="userStory.description.label" default="Description"/></label>
            <g:textArea name="description" cols="40" rows="5" value="${userStoryInstance?.description}"/>
        </div>

        <div class="fieldcontain ${hasErrors(bean: userStoryInstance, field: 'owner', 'error')} required">
            <label for="owner"><g:message code="userStory.owner.label" default="Owner"/><span class="required-indicator">*</span></label>
            <g:select id="owner" name="owner.id" from="${org.livesnippets.webflow.Developer.list()}" optionKey="id" required="" value="${userStoryInstance?.owner?.id}" class="many-to-one"/>
        </div>

    </fieldset>
    <fieldset class="buttons">
        <g:submitButton class="save" name="addAjax" value="Add"/>
    </fieldset>
</flow:formRemote>


<div id="list-userStory" class="content scaffold-list" role="main">
    <table>
        <thead>
        <tr>
            <th><g:message code="userStory.name" default="Name"/></th>
            <th><g:message code="userStory.owner" default="Owner"/></th>
            <th/>
        </tr>
        </thead>
        <tbody>
        <g:each in="${projectInstance.stories}" status="i" var="userStory">
            <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                <td>${fieldValue(bean: userStory, field: "name")}</td>
                <td>${fieldValue(bean: userStory, field: "owner")}</td>
                <td><flow:remoteLink update="editStories" event="removeAjax"
                        params="${[name: userStory.name]}">Remove</flow:remoteLink></td>
            </tr>
        </g:each>
        </tbody>
    </table>
</div>
