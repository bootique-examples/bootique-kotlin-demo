import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "io.bootique.kotlin.demo"
version = "1.0-SNAPSHOT"

buildscript {
    var kotlinVersion: String by extra
    kotlinVersion = "1.2.30"

    repositories {
        jcenter()
    }

    dependencies {
        classpath(kotlin("gradle-plugin", kotlinVersion))
    }

}

plugins {
    application
    kotlin("jvm") version "1.2.30"
}

val kotlinVersion: String by extra

repositories {
    jcenter()
}

application {
    mainClassName = "io.bootique.kotlin.demo.AppKt"
}

dependencies {
    compile("io.bootique.kotlin:bootique-kotlin:0.25")
    compile("io.bootique.undertow:bootique-undertow:0.25")
    compile(kotlin("stdlib-jdk8", kotlinVersion))
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

