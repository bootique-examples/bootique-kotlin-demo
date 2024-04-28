import org.gradle.kotlin.dsl.version
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.gradle.dsl.Coroutines

plugins {
    application
    kotlin("jvm").version("1.4.10")
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

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-script-util")
    implementation("org.jetbrains.kotlin:kotlin-compiler-embeddable")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:$coroutinesVersion")
    implementation("org.jetbrains.kotlin:kotlin-scripting-jvm-host:1.4.10")

    implementation("io.bootique.kotlin:bootique-kotlin")
    implementation("io.bootique.kotlin:bootique-kotlin-config")
    implementation("io.bootique.kotlin:bootique-kotlin-undertow")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
