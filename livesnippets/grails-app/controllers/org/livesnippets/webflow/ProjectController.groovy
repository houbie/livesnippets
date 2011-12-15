package org.livesnippets.webflow

import org.livesnippets.webflow.Developer.Experience
import org.springframework.web.context.request.RequestContextHolder

class ProjectController {

    def scaffold = true

    def create = {
        redirect(action: "newProjectWizard")
    }

    def newProjectWizardFlow = {
        onStart {
            log.info("starting a new project wizard")
            flow.projectInstance = new Project()
        }
        onEnd {
            log.info("project wizard finished")
        }

        projectInfo {
            on("next") {
                flow.projectInstance.properties = params
                if (!flow.projectInstance.validate(["name", "description"])) {
                    error()
                }
            }.to("lead")
        }

        lead {
            subflow(controller: "developer", action: "developerSelection",
                    input: [experience: Experience.SENIOR, title: "Select project lead"])
            on("selected") {
                flow.projectInstance.lead = currentEvent.attributes.developer
            }.to {
                if (!flow.projectInstance.validate(["lead"])) {
                    flash.message = "The project lead has to be a senior developer"
                    return "lead"
                } else {
                    return "team"
                }
            }
            on("cancel") {
                flash.message = "Lead is mandatory!"
            }.to("lead")
        }

        team {
            on("add").to("addTeamMember")
            on("remove") {
                flow.projectInstance.removeFromTeam(flow.projectInstance.team.find {it.name == params.name})
            }.to("team")
            on("next").to("stories")
        }

        addTeamMember {
            subflow(controller: "developer", action: "developerSelection", input: [title: "New project wizard: Select team member"])
            on("selected") {
                flow.projectInstance.addToTeam(currentEvent.attributes.developer)
            }.to("team")
            on("cancel").to("team")
        }

        stories {
            on("addAjax") {
                UserStory userStoryInstance = new UserStory()
                userStoryInstance.properties = params
                if (userStoryInstance.validate()) {
                    flow.projectInstance.addToStories(userStoryInstance)
                    userStoryInstance = null
                }
                render(template: "newProjectWizard/editStories", model: [projectInstance: flow.projectInstance, userStoryInstance: userStoryInstance])
            }.to("stories")
            on("removeAjax") {
                flow.projectInstance.removeFromStories(flow.projectInstance.stories.find {it.name == params.name})
                render(template: "newProjectWizard/editStories", model: [projectInstance: flow.projectInstance])
            }.to("stories")
            on("finish").to("saveProject")
            on("cancel").to("cancel")
        }
        saveProject {
            action {
                if (!flow.projectInstance.lead.id) {
                    flow.projectInstance.lead.save()
                }
                if (flow.projectInstance.save()) {
                    RequestContextHolder.currentRequestAttributes().flashScope.message = "Project was successfully created"
                    end()
                } else {
                    projectInfo()
                }
            }
            on("end").to("end")
            on("projectInfo").to("projectInfo")
        }

        end {
            redirect(action: "show", id: flow.projectInstance.id)
        }
        cancel()

    }



    def newProjectWizardCleanedUpFlow = {
        onStart doOnStart

        projectInfo {
            on("next", doBindProjectInfo).to("lead")
        }

        lead {
            subflow(controller: "developer", action: "developerSelection",
                    input: [experience: Experience.SENIOR, title: "Select project lead"])
            on("selected", doSetLead).to(selectLeadOrTeam)
            on("cancel", doFlashLeadMandatoryMessage).to("lead")
        }

        team {
            on("add").to("addTeamMember")
            on("remove", doRemoveTeamMember).to("team")
            on("next").to("stories")
        }

        addTeamMember {
            subflow(controller: "developer", action: "developerSelection", input: [title: "Select team member"])
            on("selected", doAddTeamMember).to("team")
            on("cancel").to("team")
        }

        stories {
            on("addAjax", doAddStory).to("stories")
            on("removeAjax", doRemoveStory).to("stories")
            on("finish").to("saveProject")
            on("cancel").to("cancel")
        }
        saveProject {
            action(doSaveProject)
            on("end").to("end")
            on("projectInfo").to("projectInfo")
        }

        end {
            redirect(action: "show", id: flow.projectInstance.id)
        }
        cancel {
            redirect(action: "list")
        }
    }

    private def doOnStart = {
        flow.projectInstance = new Project()
    }

    private def selectLeadOrTeam = {
        if (!flow.projectInstance.validate(["lead"])) {
            flash.message = "invalid lead"
            return "lead"
        } else {
            return "team"
        }
    }

    private def doBindProjectInfo = {
        flow.projectInstance.properties = params
        if (!flow.projectInstance.validate(["name", "description"])) {
            error()
        }
    }

    private def doSetLead = {
        flow.projectInstance.lead = currentEvent.attributes.developer
    }

    private def doFlashLeadMandatoryMessage = {
        flash.message = "Lead is mandatory!"
    }

    private def doRemoveTeamMember = {
        flow.projectInstance.removeFromTeam(flow.projectInstance.team.find {it.name == params.name})
    }

    private def doAddTeamMember = {
        flow.projectInstance.addToTeam(currentEvent.attributes.developer)
    }

    private def doAddStory = {
        UserStory userStoryInstance = new UserStory()
        userStoryInstance.properties = params
        if (userStoryInstance.validate()) {
            flow.projectInstance.addToStories(userStoryInstance)
            userStoryInstance = null
        }
        render(template: "newProjectWizard/editStories", model: [projectInstance: flow.projectInstance, userStoryInstance: userStoryInstance])
    }

    private def doRemoveStory = {
        flow.projectInstance.removeFromStories(flow.projectInstance.stories.find {it.name == params.name})
        render(template: "newProjectWizard/editStories", model: [projectInstance: flow.projectInstance])
    }

    private def doSaveProject = {
        if (!flow.projectInstance.lead.id) {
            flow.projectInstance.lead.save()
        }
        if (flow.projectInstance.save()) {
            RequestContextHolder.currentRequestAttributes().flashScope.message = "Project was successfully created"
            end()
        } else {
            projectInfo()
        }
    }

}
