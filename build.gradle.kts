plugins {
    java
    application
    id("org.danilopianini.gradle-java-qa") version "1.70.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("org.openjfx.javafxplugin") version "0.1.0"

}

javafx {
    modules("javafx.controls", "javafx.fxml")
}

repositories {
    mavenCentral()
}

application {
    mainClass.set("com.project.paradoxplatformer.App")
}

dependencies {

    compileOnly("com.github.spotbugs:spotbugs-annotations:4.8.6")

    val slf4jVersion = "2.0.16"
    implementation("org.slf4j:slf4j-api:$slf4jVersion")
    runtimeOnly("ch.qos.logback:logback-classic:1.5.11")

    val jUnitVersion = "5.11.2"
    testImplementation("org.junit.jupiter:junit-jupiter-api:$jUnitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$jUnitVersion")
    
    implementation("org.apache.commons:commons-lang3:3.0")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.12.0")
    implementation("com.fasterxml.jackson.core:jackson-core:2.12.0")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.12.0")
    implementation("com.google.guava:guava:11.0.2")
    implementation("com.omertron:API-OMDB:1.5")
    implementation("org.jooq:jool:0.9.15")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events(*org.gradle.api.tasks.testing.logging.TestLogEvent.values())
        showStandardStreams = true
    }
}
