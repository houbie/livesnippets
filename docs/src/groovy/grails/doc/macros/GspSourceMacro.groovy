package grails.doc.macros

class GspSourceMacro extends SourceMacro {
    List baseDirs

    GspSourceMacro(basedir) {
        super(basedir)
    }

    String getName() { 'gspSrc' }

    String getCodeType(Map params) { 'xml' }

    String getFileName(Map params) {
        return "${getViewName(params)}.gsp"
    }


    String getViewName(Map params) {
        return params.'view'
    }

}