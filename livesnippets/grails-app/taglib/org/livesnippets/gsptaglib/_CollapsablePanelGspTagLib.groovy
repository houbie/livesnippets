package org.livesnippets.gsptaglib
// Generated code, DO NOT EDIT!

import org.codehaus.groovy.grails.web.taglib.*

class _CollapsablePanelGspTagLib {
static namespace = "t"
Closure collapsablePanel = { attrs, body ->
out.print('\n\n')

boolean collapsed = Boolean.valueOf(attrs.collapsed)

out.print('\n<div class="collapsablePanel">\n    <div class="header"></div>\n    <div class="body"></div>\n</div>\n')
}
}
