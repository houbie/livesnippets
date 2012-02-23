package org.livesnippets.webflow

class DeveloperController {

    def scaffold = true

    ProjectDataCleanerService projectDataCleanerService

    def developerSelectionFlow = {
        input {
            title(required: true)
            experience()
        }

        search {
            onEntry {
                params.max = Math.min(params.max ? params.int("max") : 10, 100)
                if (flow.experience) {
                    [developerInstanceList: Developer.findAllByExperience(flow.experience, params),
                            developerInstanceTotal: Developer.countByExperience(flow.experience)]
                } else {
                    [developerInstanceList: Developer.list(), developerInstanceTotal: Developer.count()]
//                    we should take pagination and sorting into account, but following line throws an exception
//                    see http://jira.grails.org/browse/GRAILS-8470
//                    [developerInstanceList: Developer.list(params), developerInstanceTotal: Developer.count()]
                }
            }
            onExit {
                log.info("leaving search state")
            }
            onRender {
                log.info("about to render search page, the model is already build: developerInstanceList ${flow.developerInstanceList}")
            }

            on("select") {
                [developer: Developer.get(params.id)]
            }.to("selected")
            on("cancel").to("cancel")
            on("create").to("createDeveloper")
        }

        createDeveloper {
            render(view: "/developer/create")
            on("create") {
                Developer developer = new Developer(params)
                if (!developer.validate()) {
                    error()
                } else {
                    [developer: developer]
                }
            }.to("selected")
            on("cancel").to("cancel")
        }

        selected {
            output {
                developer {flow.developer}
            }
        }
        cancel()
    }
}
