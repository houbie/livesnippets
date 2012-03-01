class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?" {
            constraints {
                // apply constraints here
            }
        }

        "/gallery"(view: "/gsptaglib/gallery")

        "/"(view: "/index")
        "500"(view: '/error')
    }
}
