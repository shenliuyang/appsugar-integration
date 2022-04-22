plugins {
    val sysProps = System.getProperties()
    val lombokVersion: String by sysProps
    val springBootVersion: String by sysProps
    val dependencyManagement: String by sysProps
    id("org.springframework.boot") version springBootVersion
    id("io.spring.dependency-management") version dependencyManagement
    id("io.freefair.lombok") version lombokVersion
    java
    `java-library`
    `maven-publish`
}


allprojects {
    apply {
        plugin("java")
        plugin("java-library")
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
        plugin("io.freefair.lombok")
        plugin("maven-publish")
        plugin("idea")
    }

    java {
        toolchain {
            //languageVersion.set(JavaLanguageVersion.of("11")) // "8"
        }
        withSourcesJar()
        sourceCompatibility = JavaVersion.VERSION_1_8
    }

    val repos = listOf("https://maven.aliyun.com/nexus/content/groups/public", "https://jcenter.bintray.com/")
    repositories {
        mavenLocal()
        repos.forEach { maven(it) }
    }
    publishing {
        publications {
            create<MavenPublication>("maven") {
                from(components["java"])
            }
        }
    }

    dependencies {
        testImplementation("org.springframework.boot:spring-boot-starter-test")
    }

    tasks {
        bootJar {
            enabled = false
            archiveClassifier.set("boot")
        }
        jar {
            enabled = true
            archiveClassifier.set("")
        }
        test {
            failFast = true
            useJUnitPlatform()
            testLogging { showStandardStreams = true }

        }
        register("install") {
            dependsOn("build", "publishToMavenLocal")
        }
    }
}
fun DependencyHandler.apiWithVersion(dependencyNotation: String) =
    api(dependencyNotation + ":" + dependencyManagement.managedVersions[dependencyNotation])

project(":data-jpa") {
    dependencies {
        val entityGraphVersion = "2.6.0"
        val versions = dependencyManagement.importedProperties
        apiWithVersion("org.springframework.boot:spring-boot-starter-data-jpa")
        apiWithVersion("com.querydsl:querydsl-jpa")
        apiWithVersion("org.apache.commons:commons-lang3")
        api("com.cosium.spring.data:spring-data-jpa-entity-graph:$entityGraphVersion")

        annotationProcessor("com.querydsl:querydsl-apt:${versions["querydsl.version"]}:jpa")
        annotationProcessor("javax.annotation:javax.annotation-api")
        annotationProcessor("javax.persistence:javax.persistence-api")

        annotationProcessor("org.hibernate:hibernate-jpamodelgen")
        annotationProcessor("com.cosium.spring.data:spring-data-jpa-entity-graph-generator:$entityGraphVersion")
    }
}

project(":data-jpa-test") {
    dependencies {
        apiWithVersion("org.springframework.boot:spring-boot-starter-data-jpa")
        apiWithVersion("org.springframework.boot:spring-boot-starter-test")
        apiWithVersion("com.h2database:h2")
        apiWithVersion("mysql:mysql-connector-java")
        api(project(":data-jpa"))

        api("org.apache.ant:ant:1.10.12")
        api("org.dbunit:dbunit:2.7.3")
        api("com.google.guava:guava:31.0.1-jre")
    }
}