eventCreateWarStart = { warName, stagingDir ->
    println 'deleting tomcat-embedded jars'
    ant.delete {
        fileset(dir: "${stagingDir}/WEB-INF/lib") {
            include name: "tomcat*"
        }
    }
}
