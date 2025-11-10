plugins {
    java
    checkstyle
}

group = "com.example"
version = "1.0-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

checkstyle {
    toolVersion = "10.12.3"
    configFile = file("google_checks.xml")
    isIgnoreFailures = false
    maxErrors = 0
    maxWarnings = 0
}