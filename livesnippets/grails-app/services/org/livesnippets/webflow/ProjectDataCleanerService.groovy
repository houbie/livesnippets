package org.livesnippets.webflow

import groovy.util.logging.Slf4j

@Slf4j
class ProjectDataCleanerService {
    void resetData() {
        try {
            Project.list().each {it.delete()}
            UserStory.list().each {it.delete()}
            Developer.list().each {it.delete(flush: true)}

            def joe = save(new Developer(name: 'Joe', experience: Developer.Experience.JUNIOR))
            def jack = save(new Developer(name: 'Jack', experience: Developer.Experience.JUNIOR))
            def john = save(new Developer(name: 'John', experience: Developer.Experience.JUNIOR))
            def marc = save(new Developer(name: 'Marc', experience: Developer.Experience.MEDIOR))
            def maurice = save(new Developer(name: 'Maurice', experience: Developer.Experience.MEDIOR))
            def sean = save(new Developer(name: 'Sean', experience: Developer.Experience.SENIOR))
            def sebastian = save(new Developer(name: 'Sebastian', experience: Developer.Experience.SENIOR))

            def customerStory = save(new UserStory(name: 'Customer CRUD', description: 'Screens for customer management', owner: jack))
            def productStory = save(new UserStory(name: 'Product CRUD', description: 'Screens for product management', owner: jack))

            def project = new Project(name: 'Webshop', description: 'Create a simple webshop', lead: sebastian)
            project.addToStories(customerStory).addToStories(productStory)
            project.addToTeam(joe).addToTeam(jack).addToTeam(marc)
            save(project)
        } catch (e) {
            log.error('exception in BootStrap', e)
        }
    }

    private def save(object) {
        if (object.validate()) {
            return object.save()
        }
        object.errors.each {
            log.error("could not save object: $it")
        }
        return null
    }
}
