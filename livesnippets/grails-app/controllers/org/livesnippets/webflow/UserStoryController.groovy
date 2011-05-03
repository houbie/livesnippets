package org.livesnippets.webflow

class UserStoryController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [userStoryInstanceList: UserStory.list(params), userStoryInstanceTotal: UserStory.count()]
    }

    def create = {
        def userStoryInstance = new UserStory()
        userStoryInstance.properties = params
        return [userStoryInstance: userStoryInstance]
    }

    def save = {
        def userStoryInstance = new UserStory(params)
        if (userStoryInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'userStory.label', default: 'UserStory'), userStoryInstance.id])}"
            redirect(action: "show", id: userStoryInstance.id)
        }
        else {
            render(view: "create", model: [userStoryInstance: userStoryInstance])
        }
    }

    def show = {
        def userStoryInstance = UserStory.get(params.id)
        if (!userStoryInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'userStory.label', default: 'UserStory'), params.id])}"
            redirect(action: "list")
        }
        else {
            [userStoryInstance: userStoryInstance]
        }
    }

    def edit = {
        def userStoryInstance = UserStory.get(params.id)
        if (!userStoryInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'userStory.label', default: 'UserStory'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [userStoryInstance: userStoryInstance]
        }
    }

    def update = {
        def userStoryInstance = UserStory.get(params.id)
        if (userStoryInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (userStoryInstance.version > version) {

                    userStoryInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'userStory.label', default: 'UserStory')] as Object[], "Another user has updated this UserStory while you were editing")
                    render(view: "edit", model: [userStoryInstance: userStoryInstance])
                    return
                }
            }
            userStoryInstance.properties = params
            if (!userStoryInstance.hasErrors() && userStoryInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'userStory.label', default: 'UserStory'), userStoryInstance.id])}"
                redirect(action: "show", id: userStoryInstance.id)
            }
            else {
                render(view: "edit", model: [userStoryInstance: userStoryInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'userStory.label', default: 'UserStory'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def userStoryInstance = UserStory.get(params.id)
        if (userStoryInstance) {
            try {
                userStoryInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'userStory.label', default: 'UserStory'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'userStory.label', default: 'UserStory'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'userStory.label', default: 'UserStory'), params.id])}"
            redirect(action: "list")
        }
    }
}
