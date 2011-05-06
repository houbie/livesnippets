import org.livesnippets.webflow.Developer
import org.livesnippets.webflow.Developer.Experience
import org.livesnippets.webflow.Project
import org.livesnippets.webflow.UserStory

class BootStrap {

    def init = { servletContext ->
        try {
            def joe = save(new Developer(name: 'Joe', experience: Experience.JUNIOR))
            def jack = save(new Developer(name: 'Jack', experience: Experience.JUNIOR))
            def john = save(new Developer(name: 'John', experience: Experience.JUNIOR))
            def marc = save(new Developer(name: 'Marc', experience: Experience.MEDIOR))
            def maurice = save(new Developer(name: 'Maurice', experience: Experience.MEDIOR))
            def sean = save(new Developer(name: 'Sean', experience: Experience.SENIOR))
            def sebastian = save(new Developer(name: 'Sebastian', experience: Experience.SENIOR))

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

    def destroy = {
    }

    private def save(object) {
        if (object.validate()) {
            return object.save()
        }
        object.errors.each {
            println it
        }
        return null
    }

}
