<%@ page namespace="widget" %>
<%@ page docs="
Example widget that shows a hoverable image thumbnail
GSP taglib implementation

@attr image REQUIRED the name of the image
@attr caption OPTIONAL image caption
@attr captionKey OPTIONAL i18n key for the image caption
" %>

<%
    def caption = attrs.captionKey ? g.message(code: attrs.captionKey) : attrs.caption
%>
<a class="thumb" href="#thumb">
    <g:img dir="images" file="${attrs.image}_thumb.jpg" width="100px" height="75px" border="0"/>
    <span>
        <g:img dir="images" file="${attrs.image}.jpg"/>
        <br/>
        ${caption}
    </span>
</a>