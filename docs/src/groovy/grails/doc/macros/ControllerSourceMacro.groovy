package grails.doc.macros

import java.util.regex.Pattern

class ControllerSourceMacro extends ArtifactSourceMacro {

    ControllerSourceMacro(basedir) {
        super(basedir)
    }

    String getName() { "controllerSrc" }

    String getParamName() { "action" }

    Pattern getRegularExpression(String actionName) {
        ~/(?s)(\s*?def\s+?$actionName\s*?=\s*?\{\s*?.+?)(\/\*\*|def\s*[a-zA-Z]+?\s*=\s*\{)/
    }

}