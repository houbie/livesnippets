package org.livesnippets.gsptaglib

class ClassicTagLib {
    static namespace = "classic"

    /**
     * Example widget that shows a hoverable image thumbnail
     * classical taglib implementation
     *
     * @attr image REQUIRED the name of the image
     * @attr caption OPTIONAL image caption
     * @attr captionKey OPTIONAL i18n key for the image caption
     */
    Closure imagePopUp = { attrs, body ->
        def caption= attrs.captionKey? g.message(code: attrs.captionKey) : attrs.caption
        out << '<a class="thumb" href="#thumb">'
        out << g.img(dir: "images", file: "${attrs.image}_thumb.jpg", width: "100px", height: "75px")
        out << '<span>'
        out << g.img(dir: "images", file: "${attrs.image}.jpg")
        out << '<br/>'
        out << caption
        out << '</span></a>'
    }
}
