plugins {
    val sysProps = System.getProperties()
    val lombokVersion: String by sysProps
    val springBootVersion: String by sysProps
    val releasePluginVersion: String by sysProps
    id("net.researchgate.release") version releasePluginVersion
    id("org.springframework.boot") version springBootVersion
    id("io.freefair.lombok") version lombokVersion
    `java-library`
}

allprojects {
    apply {
        plugin("java-library")
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
        plugin("io.freefair.lombok")
    }
    val repos = listOf("https://maven.aliyun.com/nexus/content/groups/public", "https://jcenter.bintray.com/")
    repositories {
        mavenLocal()
        repos.forEach { maven(it) }
    }

    dependencies {
        testImplementation("org.springframework.boot:spring-boot-starter-test")
    }


    tasks {
        bootJar { enabled = false }
        test {
            failFast = true
            useJUnitPlatform()
            testLogging { showStandardStreams = true }
        }

    }
}


