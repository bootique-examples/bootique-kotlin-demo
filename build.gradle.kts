import org.gradle.kotlin.dsl.version
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.gradle.dsl.Coroutines

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
    id("io.spring.dependency-management") version "1.0.4.RELEASE"
    kotlin("jvm") version "1.2.30"
}

val kotlinVersion: String by extra
val bootiqueVersion by project

repositories {
    jcenter()
}

application {
    mainClassName = "io.bootique.kotlin.demo.AppKt"
}

dependencyManagement {
    imports {
        mavenBom("io.bootique.bom:bootique-bom:$bootiqueVersion")
    }
}

dependencies {
    compile("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:0.22.5")

    compile("io.bootique.kotlin:bootique-kotlin")
    compile("io.bootique.kotlin:bootique-kotlin-config")
    compile("io.bootique.kotlin:bootique-kotlin-undertow:0.25")
    compile(kotlin("stdlib-jdk8", kotlinVersion))
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

kotlin {
    experimental.coroutines = Coroutines.ENABLE
}
