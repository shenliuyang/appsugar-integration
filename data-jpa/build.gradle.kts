apply {
    plugin("io.freefair.lombok")
}
val entityGraphVersion = "2.4.2"
dependencies {
    api("org.springframework.boot:spring-boot-starter-data-jpa")
    api("com.cosium.spring.data:spring-data-jpa-entity-graph:$entityGraphVersion")
    api("com.querydsl:querydsl-jpa")
    api("com.h2database:h2")
    api("mysql:mysql-connector-java")
    //
    annotationProcessor("com.querydsl:querydsl-apt:4.4.0:jpa")
    annotationProcessor("javax.annotation:javax.annotation-api")
    annotationProcessor("javax.persistence:javax.persistence-api")
    annotationProcessor("org.hibernate:hibernate-jpamodelgen")
    annotationProcessor("com.cosium.spring.data:spring-data-jpa-entity-graph-generator:$entityGraphVersion")
    //
    compileOnly("org.projectlombok:lombok")
}