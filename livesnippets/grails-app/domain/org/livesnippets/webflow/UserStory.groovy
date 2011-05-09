package org.livesnippets.webflow

class UserStory implements Serializable {
    String name
    String description
    Developer owner

    String toString() {name}

    static constraints = {
        name(blank: false)
        description(blank: true, nullable: true, widget: "textarea")
        owner(blank: false)
    }
}
