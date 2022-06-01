/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 6/1/22, 4:23 PM by Carina The Latest changes made by Carina on 6/1/22, 4:01 PM All contents of "build.gradle.kts" are protected by copyright.
 * The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java-library")
    idea
    id("io.papermc.paperweight.userdev") version "1.3.5"
    kotlin("jvm") version "1.7.+"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("xyz.jpenilla.run-paper") version "1.0.6" // Adds runServer and runMojangMappedServer tasks for testing
}

group = "de.carina"
version = "1.0.0"
description = "The theHunter minigame but in a kotlin project remake"


repositories {
    mavenCentral()
    gradlePluginPortal()
    maven("https://repo.codemc.io/repository/maven-snapshots/")
}

dependencies {
    paperDevBundle("1.18.2-R0.1-SNAPSHOT")
    testImplementation(kotlin("test"))
    implementation("net.wesjd:anvilgui:1.5.3-SNAPSHOT")
}



tasks {
    assemble {
        dependsOn(reobfJar)
    }
    runServer {
        minecraftVersion("1.18.2")
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
    test {
        useJUnitPlatform()
    }
}
java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}