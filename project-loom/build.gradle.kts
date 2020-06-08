dependencies {
    compileOnly("org.springframework.boot:spring-boot-starter-webflux")
    compileOnly("org.springframework.boot:spring-boot-starter-reactor-netty")
    compileOnly("org.springframework.boot:spring-boot-starter-undertow")
    compileOnly("org.springframework.boot:spring-boot-starter-tomcat")
}
tasks {
    java {
        sourceCompatibility = org.gradle.api.JavaVersion.VERSION_1_8
    }
    test { systemProperties["spring.jpa.hibernate.ddl-auto"] = "update" }
}