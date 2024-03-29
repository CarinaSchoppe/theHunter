/*
 Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 6/14/22, 12:49 PM by Carina The Latest changes made by Carina on 6/13/22, 12:42 PM All contents of "build.gradle.kts" are protected by copyright.
 * The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


plugins {
    id("idea")
    kotlin("jvm") version "+"
    //  id("io.papermc.paperweight.userdev") version "+"
    id("com.github.johnrengelman.shadow") version "+"
    id("xyz.jpenilla.run-paper") version "+" // Adds runServer and runMojangMappedServer tasks for testing

}

group = "de.pixels"
version = "1.3.5"
description = "The theHunter minigame but in a kotlin project remake"


repositories {
    mavenCentral()
    gradlePluginPortal()
    maven { url = uri("https://repo.papermc.io/repository/maven-public/") }
    maven("https://repo.codemc.io/repository/maven-snapshots/")
}

dependencies {
    //    implementation("com.google.code.gson:gson:+") // Gson
    // paperweight.paperDevBundle("+")
    compileOnly("io.papermc.paper:paper-api:+")
    testImplementation(kotlin("test"))
    implementation("net.wesjd:anvilgui:+")
    implementation("org.xerial:sqlite-jdbc:+")
}

java {
    // Configure the java toolchain. This allows gradle to auto-provision JDK 17 on systems that only have JDK 8 installed for example.
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}


tasks {
    runServer {
        minecraftVersion("1.20.1")
    }
    compileJava {
        options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything
        options.release.set(17)
    }
    javadoc {
        options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything
    }
    processResources {
        filteringCharset = Charsets.UTF_8.name() // We want UTF-8 for everything
    }
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "17"
            languageVersion = "2.0"
            apiVersion = "2.0"
        }
    }
    test {
        useJUnitPlatform()
    }
}
