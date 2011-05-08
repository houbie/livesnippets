package grails.doc.macros

import java.util.regex.Pattern
import org.radeox.macro.BaseMacro
import org.radeox.macro.CodeMacro
import org.radeox.macro.parameter.BaseMacroParameter
import org.radeox.macro.parameter.MacroParameter
import org.radeox.util.Encoder

class SourceMacro extends BaseMacro {
    List baseDirs

    SourceMacro(basedir) {
        if (!(basedir instanceof Collection || basedir.class.array)) basedir = [basedir]
        baseDirs = basedir.collect { f -> f as File }
    }

    String getName() { "src" }

    String getCodeType(Map params) {
        return params.type ?: 'java'
    }

    void execute(Writer out, MacroParameter params) {
        boolean inline = params.params.inline as boolean
        File file = findFile(getFileName(params.params))
        String text = getSource(file, params.params)
        String id = Encoder.escape(params.params.toString())
        if (text) {
            if (!inline) {
                out << /<p><a href="#${id}" id="a_${id}" /
                out << /onclick="document.getElementById('${id}').style.display='inline'; document.getElementById('a_${id}').style.display='none'">/
                out << /Show Source<\/a><\/p><div id="${id}" style="display:none;">/
            } else {
                out << /<div id="${id}">/
            }
            text = Encoder.escape(text)

            def macro = new CodeMacro()
            macro.setInitialContext(initialContext)
            def macroParams = new BaseMacroParameter()
            macroParams.setParams(getCodeType(params.params))
            macroParams.content = text
            macro.execute(out, macroParams)
            out << "<span class=\"file-name\">${file?.name}</span></div>"
        }
    }

    File findFile(String fileName) {
        for (dir in baseDirs) {
            File result
            dir.traverse(nameFilter: fileName) { result = it }
            if (result) return result
        }
        return null
    }

    String getSource(File file, Map params) {
        String text = file?.text ?: ""

        String code
        if (params.from) {
            code = findCodeByFromToLines(params, text)
        } else {
            code = findCodeByRegex(params, text)
        }
        return code ?: "$params NOT FOUND"
    }

    Pattern getRegularExpression(Map params) {
        if (params.regex) {
            return Pattern.compile(params.regex)
        }
        return ~/(?s)(.*)/
    }

    String getFileName(Map params) {
        return params.'file'
    }

    String findCodeByRegex(Map params, String text) {
        Pattern regex = getRegularExpression(params)
        if (regex != null) {
            def matcher = regex.matcher(text)
            if (matcher.find()) {
                return matcher.group(1)
            }
            return null
        }
    }

    String findCodeByFromToLines(Map params, String text) {
        List codeLines = text.readLines()
        int from = getLineNumber(params.from, codeLines)
        int to
        if (params.lines || params.to) {
            to = params.lines ? (from + params.lines.toInteger() - 1) : getLineNumber(params.to, codeLines, from)
        } else {
            to = findBlock(from, codeLines)
        }
        to = [from, to].max()
        StringBuilder result = new StringBuilder()
        from.upto(to) {
            if (it < codeLines.size()) {
                result << codeLines[it] << '\n'
            }
        }
        return result.toString()
    }

    protected int getLineNumber(String condition, List codeLines, int start = 0) {
        if (condition.isInteger()) {
            return condition.toInteger() - 1
        }

        String unescapedCondition = Encoder.unescape(condition)
        for (i in start..<codeLines.size()) {
            if (codeLines[i].trim().startsWith(unescapedCondition)) {
                return i
            }
        }
        return codeLines.size()
    }

    protected int findBlock(int start, codeLines) {
        int startChars = 0
        int endChars = 0
        for (i in start..<codeLines.size()) {
            startChars += countSubstrings(codeLines[i], blockStart)
            endChars += countSubstrings(codeLines[i], blockEnd)
            if (startChars > 0 && endChars == startChars) {
                return i
            }
        }
        return codeLines.size()
    }

    String getBlockStart() {'{'}

    String getBlockEnd() {'}'}

    protected int countSubstrings(String s, String substring) {
        //TODO: use regexp so that this can also be used for xml start/end elements
        int count = 0
        int pos = -1
        while ((pos = s.indexOf(substring, pos + 1)) > -1) count++
        return count
    }

}