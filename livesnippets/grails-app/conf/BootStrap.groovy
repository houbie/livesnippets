import org.livesnippets.webflow.Developer
import org.livesnippets.webflow.Developer.Experience
import org.livesnippets.webflow.Project
import org.livesnippets.webflow.UserStory

class BootStrap {

    def init = { servletContext ->
        def joe = new Developer(name: 'Joe', experience: Experience.JUNIOR).save()
        def jack = new Developer(name: 'Jack', experience: Experience.JUNIOR).save()
        def john = new Developer(name: 'John', experience: Experience.JUNIOR).save()
        def marc = new Developer(name: 'Marc', experience: Experience.MEDIOR).save()
        def maurice = new Developer(name: 'Maurice', experience: Experience.MEDIOR).save()
        def sean = new Developer(name: 'Sean', experience: Experience.SENIOR).save()
        def sebastian = new Developer(name: 'Sebastian', experience: Experience.SENIOR).save()

        def customerStory = new UserStory(name: 'Customer CRUD', description: 'Screens for customer management', owner: jack)
        def productStory = new UserStory(name: 'Product CRUD', description: 'Screens for product management', owner: jack)

        def project = new Project(name: 'Webshop', description: 'Create a simple webshop', lead: sebastian)
        project.addToStories(customerStory).addToStories(productStory)
        project.addToTeam(joe).addToTeam(jack).addToTeam(marc)
        if (!project.validate()) {
            project.errors.each {println it}
        }
        project.save()

    }

    def destroy = {
    }
}
