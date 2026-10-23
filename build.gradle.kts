plugins {
    kotlin("jvm")
    application
}

repositories {
    mavenCentral()
}

dependencies {
    runtimeOnly(kotlin("scripting-jsr223"))
}

application {
    mainClass = "MainKt"
}
