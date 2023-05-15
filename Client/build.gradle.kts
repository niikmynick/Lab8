plugins {
    kotlin("jvm") version "1.8.20"
    application
    kotlin("plugin.serialization") version "1.8.0"
    id("application")
    id("org.openjfx.javafxplugin") version "0.0.8"
}

repositories {
    mavenCentral()
}

group = "me.fk724s"
version = "1.0-SNAPSHOT"

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
    implementation("org.apache.logging.log4j:log4j-core:2.20.0")
    implementation(project(":Common"))
    implementation("no.tornado:tornadofx:1.7.20")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
}

javafx {
    modules("javafx.controls", "javafx.fxml", "javafx.graphics")
}
tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(11)
}

application {
    mainClass.set("ClientKt")
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}