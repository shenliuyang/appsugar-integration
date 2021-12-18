dependencies {
    api("org.springframework.boot:spring-boot-starter-data-jpa")
    api("org.springframework.boot:spring-boot-starter-test")
    api("org.apache.ant:ant:1.10.12")
    api("org.dbunit:dbunit:2.7.2")
    api("com.google.guava:guava:31.0.1-jre")
    api("com.h2database:h2")
    api(project(":data-jpa"))
}