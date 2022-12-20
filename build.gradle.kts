import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

plugins {
    val enableNative = false

    java
    id("com.github.johnrengelman.shadow") version "6.1.0"

    if (enableNative) {
        kotlin("multiplatform") version "1.7.20"
    } else
        kotlin("jvm") version "1.7.20"

}

group = "coloryr.colormirai"
version = "4.1.1"

repositories {
    mavenCentral()
}

tasks.withType(KotlinJvmCompile::class.java) {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.withType<JavaCompile> {
    options.encoding= "utf-8"
}

dependencies {
    implementation("org.apache.logging.log4j:log4j-api:2.19.0")
    implementation("org.apache.logging.log4j:log4j-core:2.19.0")

    implementation("com.alibaba:fastjson:2.0.20")
    implementation("org.java-websocket:Java-WebSocket:1.5.3")
    implementation("javax.servlet:javax.servlet-api:4.0.1")
    implementation("org.jline:jline:3.21.0")
    implementation("io.netty:netty-all:4.1.84.Final")
    implementation("com.google.code.gson:gson:2.10")

    val miraiVersion = "2.13.2"

    compile("net.mamoe", "mirai-core", miraiVersion)
    compile("net.mamoe", "mirai-core-utils", miraiVersion)
    compile("net.mamoe", "mirai-console" , miraiVersion)
    compile("net.mamoe", "mirai-console-terminal", miraiVersion)
    compile("net.mamoe", "mirai-console-frontend-base", miraiVersion)
}

kotlin {
//    val hostOs = System.getProperty("os.name")
//    val isMingwX64 = hostOs.startsWith("Windows")
//    val nativeTarget = when {
//        hostOs == "Mac OS X" -> macosX64("native")
//        hostOs == "Linux" -> linuxX64("native")
//        isMingwX64 -> mingwX64("native")
//        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
//    }
//
//    nativeTarget.apply {
//        binaries {
//            executable {
//                entryPoint = "main"
//            }
//        }
//    }
//    sourceSets {
//        val nativeMain by getting
//        val nativeTest by getting
//    }
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "coloryr.colormirai.ColorMiraiMain"
    }
}