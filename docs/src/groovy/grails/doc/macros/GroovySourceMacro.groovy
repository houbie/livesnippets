package grails.doc.macros

import java.util.regex.Pattern
import org.radeox.macro.BaseMacro
import org.radeox.macro.CodeMacro
import org.radeox.macro.parameter.BaseMacroParameter
import org.radeox.macro.parameter.MacroParameter
import org.radeox.util.Encoder

class GroovySourceMacro extends SourceMacro {
    List baseDirs

    GroovySourceMacro(basedir) {
        super(basedir)
    }

    String getName() { "groovySrc" }

    String getFileName(Map params) {
        return "${getClassName(params)}.groovy"
    }


    String getClassName(Map params) {
        return params.'class'
    }

}