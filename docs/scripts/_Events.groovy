import grails.doc.DocPublisher

eventRefDocStart = { DocPublisher publisher ->
    def tagSourceMacro = classLoader.loadClass('grails.doc.macros.TagSourceMacro').newInstance(getAllDirs('grails-app/taglib'))
    publisher.registerMacro(tagSourceMacro)

    def controllerSourceMacro = classLoader.loadClass('grails.doc.macros.ControllerSourceMacro').newInstance(getAllDirs('grails-app/controllers'))
    publisher.registerMacro(controllerSourceMacro)

    def gspSourceMacro = classLoader.loadClass('grails.doc.macros.GspSourceMacro').newInstance(getAllDirs('grails-app/views'))
    publisher.registerMacro(gspSourceMacro)

    def allSrcDirs = getAllDirs('grails-app') + getAllDirs('scripts') + getAllDirs('src') + getAllDirs('test')
    def groovySourceMacro = classLoader.loadClass('grails.doc.macros.GroovySourceMacro').newInstance(allSrcDirs)
    publisher.registerMacro(groovySourceMacro)

    def sourceMacro = classLoader.loadClass('grails.doc.macros.SourceMacro').newInstance(allSrcDirs)
    publisher.registerMacro(sourceMacro)
}

List getAllDirs(String dir) {
    def result = [new File(basedir, dir)]
    def inlinePluginDirs = pluginSettings.inlinePluginDirectories
    if (inlinePluginDirs) {
        for (pluginDir in inlinePluginDirs) {
            result << new File(pluginDir.file, dir)
        }
    }
    return result
}
