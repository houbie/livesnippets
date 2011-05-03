package grails.doc.macros

import java.util.regex.Pattern

class TagSourceMacro extends ArtifactSourceMacro {

    TagSourceMacro(basedir) {
        super(basedir)
    }

    String getName() { "tagSrc" }

    String getParamName() { "tag" }

    Pattern getRegularExpression(String tagName) {
        ~/(?s)(\s*?def\s+?$tagName\s*?=\s*?\{\s*?attrs\s*?,{0,1}\s*?(body){0,1}\s*?->.+?)(\/\*\*|def\s*[a-zA-Z]+?\s*=\s*\{)/
    }

}