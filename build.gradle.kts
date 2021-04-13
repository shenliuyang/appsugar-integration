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
        plugin("io.freefair.lombok")
    }
    val repos = listOf("https://maven.aliyun.com/nexus/content/groups/public", "https://jcenter.bintray.com/", "https://repo.spring.io/milestone")
    repositories {
        mavenLocal()
        repos.forEach { maven(it) }
    }
    dependencies {
        compileOnly("org.projectlombok:lombok")
        testImplementation("ant:ant:1.7.0")
        testImplementation("org.dbunit:dbunit:2.7.0")
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
        bootJar { enabled = false }
        test {
            failFast = true
            useJUnitPlatform()
            testLogging { showStandardStreams = true }
        }

    }
    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(8))
            vendor.set(JvmVendorSpec.ADOPTOPENJDK)
        }
    }

}


