package grails.doc.macros

import java.util.regex.Pattern

abstract class ArtifactSourceMacro extends GroovySourceMacro {

    ArtifactSourceMacro(basedir) {
        super(basedir)
    }

    abstract String getParamName()

    abstract Pattern getRegularExpression(String methodName)

    Pattern getRegularExpression(Map params) {
        def artifactDef = params[paramName]?.split(/\./)
        if (artifactDef?.size() == 2) {
            return getRegularExpression(artifactDef[1])
        }
        return super.getRegularExpression(params)
    }

    String getClassName(Map params) {
        def artifactDef = params[paramName]?.split(/\./)
        if (artifactDef?.size() == 2) {
            return artifactDef[0]
        }
        return super.getClassName(params)
    }

}