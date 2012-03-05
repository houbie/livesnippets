package org.livesnippets.gsptaglib
// Generated code, DO NOT EDIT!

import org.codehaus.groovy.grails.web.taglib.*

class _ImagePopUpGspTagLib {
static namespace = "widget"
/**
 * 
 * Example widget that shows a hoverable image thumbnail
 * GSP taglib implementation
 * 
 * @attr image REQUIRED the name of the image
 * @attr caption OPTIONAL image caption
 * @attr captionKey OPTIONAL i18n key for the image caption
 */
Closure imagePopUp = { attrs, body ->
assert attrs.image!= null, "Required tag attribute image may not be null"
out.print('\n')
out.print('\n\n')

def caption = attrs.captionKey ? g.message(code: attrs.captionKey) : attrs.caption

out.print('\n<a class="thumb" href="#thumb">\n    ')
out.print(g.img(['dir':("images"),'file':("${attrs.image}_thumb.jpg"),'width':("100px"),'height':("75px"),'border':("0")] as GroovyPageAttributes,null))
out.print('\n    <span>\n        ')
out.print(g.img(['dir':("images"),'file':("${attrs.image}.jpg")] as GroovyPageAttributes,null))
out.print('\n        <br/>\n        ')
out.print(caption)
out.print('\n    </span>\n</a>')
}
}
