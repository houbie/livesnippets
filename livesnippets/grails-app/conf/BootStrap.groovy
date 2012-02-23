import org.livesnippets.webflow.ProjectDataCleanerService

class BootStrap {
    ProjectDataCleanerService projectDataCleanerService

    def init = { servletContext ->
        projectDataCleanerService.resetData()
    }

    def destroy = {
    }
}
