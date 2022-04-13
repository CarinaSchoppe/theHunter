/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 13.04.22, 13:35 by Carina The Latest changes made by Carina on 13.04.22, 13:35 All contents of "build.gradle.kts" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    `java-library`
    idea
    id("io.papermc.paperweight.userdev") version "1.3.5"
    kotlin("jvm") version "1.6.20"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("xyz.jpenilla.run-paper") version "1.0.6" // Adds runServer and runMojangMappedServer tasks for testing
}

group = "de.carina"
version = "1.0.0"
description = "The theHunter minigame but in a kotlin project remake"


repositories {
    maven ("https://repo.codemc.io/repository/maven-snapshots/")
}

dependencies {
    paperDevBundle("1.18.2-R0.1-SNAPSHOT")
    testImplementation(kotlin("test"))
    testImplementation ("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testRuntimeOnly ("org.junit.jupiter:junit-jupiter-engine:5.8.2")
    implementation("net.wesjd:anvilgui:1.5.3-SNAPSHOT")
}



tasks {
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