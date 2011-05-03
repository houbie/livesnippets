grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.docs.output.dir = "web-app/docs"

grails.compile.artefacts.closures.convert = false

grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // uncomment to disable ehcache
        // excludes 'ehcache'
    }
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    repositories {
        grailsPlugins()
        grailsHome()
        grailsCentral()
        mavenLocal()
        mavenCentral()
    }
    dependencies {
        compile 'org.grails:grails-radeox:1.0-b4'
    }

    plugins {
        compile(':webflow:1.4.0.BUILD-SNAPSHOT')
        compile(':hibernate:1.4.0.BUILD-SNAPSHOT')
        compile(':jquery:1.4.4.1')
        provided(':tomcat:1.4.0.BUILD-SNAPSHOT')
    }

    grails.plugin.location.'docs'="../docs"
}
