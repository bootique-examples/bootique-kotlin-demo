import org.gradle.kotlin.dsl.version
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.gradle.dsl.Coroutines

plugins {
    application
    kotlin("jvm").version("1.3.11")
}

val bootiqueVersion: String by project
val coroutinesVersion: String by project

repositories {
    jcenter()
}

application {
    mainClassName = "io.bootique.kotlin.demo.AppKt"
}

dependencies {
    implementation(enforcedPlatform("io.bootique.bom:bootique-bom:$bootiqueVersion"))

    compile("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    compile("org.jetbrains.kotlin:kotlin-reflect")
    compile("org.jetbrains.kotlin:kotlin-script-util")
    compile("org.jetbrains.kotlin:kotlin-compiler-embeddable")
    compile("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:$coroutinesVersion")

    compile("io.bootique.kotlin:bootique-kotlin")
    compile("io.bootique.kotlin:bootique-kotlin-config")
    compile("io.bootique.kotlin:bootique-kotlin-undertow")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
