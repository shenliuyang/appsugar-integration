val dynamicProps = HashMap<String, String>()

val refreshData = tasks.register("refreshData") {
    dynamicProps["dbunit.enabled"] = "true"
}
tasks.register("updateDb") {
    dependsOn(refreshData)
    dynamicProps["spring.jpa.hibernate.ddl-auto"] = "update"
}
tasks.register("createDb") {
    dependsOn(refreshData)
    dynamicProps["spring.jpa.hibernate.ddl-auto"] = "create"
}
val profiles = listOf("mysql")
profiles.onEach { tasks.register(it) { doFirst { dynamicProps["spring.profiles.active"] = "$it" } } }

tasks {
    test {
        failFast = true
        useJUnitPlatform()
        testLogging { showStandardStreams = true }
        doFirst { systemProperties.putAll(dynamicProps) }
    }
}