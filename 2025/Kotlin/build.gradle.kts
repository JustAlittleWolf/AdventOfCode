plugins {
    kotlin("jvm") version "2.3.0"
}

group = "me.wolfii"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("reflect"))
}

kotlin {
    jvmToolchain(25)
}