package org.livesnippets.webflow

import grails.test.WebFlowTestCase
import org.livesnippets.webflow.Developer.Experience

class ProjectControllerTests extends WebFlowTestCase {
    def controller = new ProjectController()

    protected void setUp() {
        super.setUp()
        //register all subflows
        registerFlow("developer/getDeveloper", new DeveloperController().getDeveloperFlow)
    }


    @Override
    Object getFlow() {
        return controller.newProjectWizardFlow
    }

    void testHappyFlow() {
        //prepare lead developer
        Developer lead = new Developer(name: "x", experience: Experience.SENIOR).save()

        startFlow()
        assert "projectInfo" == flowExecution.activeSession.state.id

        controller.params.name = "project name"
        controller.params.description = "project description"
        signalEvent("next")
        assert "search" == flowExecution.activeSession.state.id

        controller.params.id = lead.id
        signalEvent("select")
        assert "team" == flowExecution.activeSession.state.id
        assert "x" == flowScope.projectInstance.lead.name

        signalEvent("next")
        assert "stories" == flowExecution.activeSession.state.id

        signalEvent("finish")
        assertFlowExecutionEnded()

        Project project = Project.findByName("project name")
        assert project
        assert project.lead.name == "x"
    }

    void testRequiredFields() {
        startFlow()
        assert "projectInfo" == flowExecution.activeSession.state.id

        signalEvent("next")
        assert "projectInfo" == flowExecution.activeSession.state.id
        assert flowScope.projectInstance.errors.hasFieldErrors("name")
        assert flowScope.projectInstance.errors.hasFieldErrors("description")
    }

}
