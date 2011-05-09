package org.livesnippets.webflow

import org.livesnippets.webflow.Developer.Experience

class Project implements Serializable {

    static hasMany = [team: Developer, stories: UserStory]

    String name
    String description
    Developer lead

    String toString() {name}

    static constraints = {
        name(blank: false, unique: true)
        description(blank: false, widget: "textarea")
        lead(nullable: false, validator: {
            if (it.experience != Experience.SENIOR) {
                return "project.lead.experience"
            }
        })
    }
}
