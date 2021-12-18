val entityGraphVersion = "2.5.0"
configurations {
    testAnnotationProcessor.get().extendsFrom(annotationProcessor.get())
}
dependencies {
    val versions = dependencyManagement.importedProperties

    api("org.springframework.boot:spring-boot-starter-data-jpa")
    api("com.cosium.spring.data:spring-data-jpa-entity-graph:$entityGraphVersion")
    api("com.querydsl:querydsl-jpa")
    annotationProcessor("com.querydsl:querydsl-apt:${versions["querydsl.version"]}:jpa")
    annotationProcessor("javax.annotation:javax.annotation-api")
    annotationProcessor("javax.persistence:javax.persistence-api")
}