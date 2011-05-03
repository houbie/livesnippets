package org.livesnippets.webflow

class Developer implements Serializable {
    enum Experience {
        JUNIOR, MEDIOR, SENIOR
    }

    String name
    Experience experience

    String toString() {name}

    static constraints = {
        name(unique: true, blank: false)
        experience(nullable: false)
    }
}
