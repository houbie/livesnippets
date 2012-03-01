<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Image gallery</title>
    <style type="text/css">
    a {
        text-decoration: none;
        color: black;
    }
        
    .thumb:hover img {
        border: 2px solid blue;
    }

    .thumb span {
        position: absolute;
        visibility: hidden;
    }

    .thumb:hover span {
        visibility: visible;
        left: 150px;
    }
    </style>

</head>

<body>
<h2>Image gallery</h2>
<p>
    This is a small image gallery that uses two variants of the imagePopUp tag: classical tag and GSP tag.
</p>

<widget:imagePopUp image="parakeet" caption="GSP imagePopUp tag"/>
<br>
<classic:imagePopUp image="frog" caption="Classical imagePopUp tag"/>
<br>

</body>
</html>