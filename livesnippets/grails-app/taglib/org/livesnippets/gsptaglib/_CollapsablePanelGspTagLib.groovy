package org.livesnippets.gsptaglib
// Generated code, DO NOT EDIT!


class _CollapsablePanelGspTagLib {
static namespace = "t"
/**
     * A collapsable panel example
     *
     * @attr collapsed OPTIONAL the initial state of the panel, default is false
     */
def collapsablePanel = { attrs, body ->
out.print('\n\n')

boolean collapsed = Boolean.valueOf(attrs.collapsed)

out.print('\n<div class="collapsablePanel">\n    <div class="header"></div>\n    <div class="body"></div>\n</div>\n')
}
}
