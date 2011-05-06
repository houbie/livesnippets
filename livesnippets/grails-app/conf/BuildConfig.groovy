grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.docs.output.dir = "web-app/docs"

grails.compile.artefacts.closures.convert = false

grails.project.dependency.resolution = {
    inherits("global")

    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'

    repositories {
        grailsPlugins()
        grailsHome()
        grailsCentral()
        mavenLocal()
        mavenCentral()
        ebr()
    }

    dependencies {
    }

    plugins {
        compile(':extended-validation:1.0.4')
        compile(":webflow:$grailsVersion")
        compile(":hibernate:$grailsVersion")
        compile(":jquery:1.5.2")
        build(":tomcat:$grailsVersion")
        build(':cloud-foundry:1.0.0.M3')
    }
}

grails.plugin.location.'docs' = "../docs"
