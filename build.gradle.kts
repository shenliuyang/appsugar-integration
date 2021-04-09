plugins {
    val sysProps = System.getProperties()
    val lombokVersion: String by sysProps
    val springBootVersion: String by sysProps
    val releasePluginVersion: String by sysProps
    id("net.researchgate.release") version releasePluginVersion
    id("org.springframework.boot") version springBootVersion
    id("io.freefair.lombok") version lombokVersion
    idea
    java
    `java-library`
}

allprojects {
    apply {
        plugin("java-library")
        plugin("java")
        plugin("idea")
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
    }
    configurations {
        testCompile.get().extendsFrom(compileOnly.get())
    }
    val repos = listOf("http://maven.aliyun.com/nexus/content/groups/public", "https://jcenter.bintray.com/", "https://repo.spring.io/milestone")
    repositories {
        mavenLocal()
        repos.forEach { maven(it) }
    }
    dependencies {
        testImplementation("org.apache.ant:ant:1.10.1")
        testImplementation("org.dbunit:dbunit:2.5.4")
        testImplementation("org.springframework.boot:spring-boot-starter-test") { exclude("junit") }
        testImplementation("org.junit.jupiter:junit-jupiter-api")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    }
    idea {
        module {
            inheritOutputDirs = false
            outputDir = file("$buildDir/classes/kotlin/main/")
            testOutputDir = file("$buildDir/classes/kotlin/test/")
        }
    }
    tasks {
        java { sourceCompatibility = org.gradle.api.JavaVersion.VERSION_1_8 }
        bootJar { enabled = false }
        test {
            failFast = true
            useJUnitPlatform()
            testLogging { showStandardStreams = true }
        }
    }
}


