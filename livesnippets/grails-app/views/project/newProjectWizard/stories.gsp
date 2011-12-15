<%@ page import="org.livesnippets.webflow.Project" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'project.label', default: 'Project')}"/>
    <title>Stories</title>
    <r:require module="jquery"/>
</head>
<body>
<div id="create-project" class="content scaffold-create" role="main">
    <flow:breadCrumbs/>    
    <h1>Stories</h1>
    <div id="editStories">
        <g:render template="newProjectWizard/editStories"/>
    </div>
</div>
<div>
    <g:link controller="project" action="newProjectWizard" event="finish">Finish</g:link>
    <g:link controller="project" action="newProjectWizard" event="cancel">Cancel</g:link>
</div>
</body>
</html>
