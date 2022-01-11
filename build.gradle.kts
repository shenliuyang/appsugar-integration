plugins {
    val sysProps = System.getProperties()
    val lombokVersion: String by sysProps
    val springBootVersion: String by sysProps
    val releasePluginVersion: String by sysProps
    val dependencyManagement: String by sysProps
    val kotlinVersion: String by sysProps
    id("net.researchgate.release") version releasePluginVersion
    id("org.springframework.boot") version springBootVersion
    id("io.spring.dependency-management") version dependencyManagement
    id("io.freefair.lombok") version lombokVersion
    kotlin("plugin.lombok") version kotlinVersion
    kotlin("jvm") version kotlinVersion
    kotlin("kapt") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion
    `maven-publish`
}


allprojects {
    apply {
        plugin("org.jetbrains.kotlin.jvm")
        plugin("org.jetbrains.kotlin.kapt")
        plugin("org.jetbrains.kotlin.plugin.spring")
        plugin("org.jetbrains.kotlin.plugin.jpa")
        plugin("java")
        plugin("java-library")
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
        plugin("io.freefair.lombok")
        plugin("maven-publish")
    }
    java {
        toolchain {
            //languageVersion.set(JavaLanguageVersion.of("8")) // "8"
        }
    }
    val repos = listOf("https://maven.aliyun.com/nexus/content/groups/public", "https://jcenter.bintray.com/")
    repositories {
        mavenLocal()
        repos.forEach { maven(it) }
    }
    publishing {
        publications {
            create<MavenPublication>("maven") {
                from(components["kotlin"])
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
    java {
        withSourcesJar()
    }
}
fun DependencyHandler.`apiWithVersion`(dependencyNotation: String) =
    api(dependencyNotation + ":" + dependencyManagement.managedVersions[dependencyNotation])

project(":data-jpa") {
    dependencies {
        val entityGraphVersion = "2.5.0"
        val versions = dependencyManagement.importedProperties
        apiWithVersion("org.springframework.boot:spring-boot-starter-data-jpa")
        apiWithVersion("com.querydsl:querydsl-jpa")
        apiWithVersion("org.apache.commons:commons-lang3")
        api("com.cosium.spring.data:spring-data-jpa-entity-graph:$entityGraphVersion")

        kapt("com.querydsl:querydsl-apt:${versions["querydsl.version"]}:jpa")
        kapt("javax.annotation:javax.annotation-api")
        kapt("javax.persistence:javax.persistence-api")

        kapt("org.hibernate:hibernate-jpamodelgen")
        kapt("com.cosium.spring.data:spring-data-jpa-entity-graph-generator:$entityGraphVersion")
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
        api("org.dbunit:dbunit:2.7.2")
        api("com.google.guava:guava:31.0.1-jre")
    }
}

project(":data-redis") {
    dependencies {
        apiWithVersion("org.springframework.boot:spring-boot-starter-data-redis")
    }
}
