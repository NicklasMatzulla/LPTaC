/*
 * Copyright 2023 Nicklas Matzulla
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:Suppress("VulnerableLibrariesLocal", "SpellCheckingInspection")

import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import dev.s7a.gradle.minecraft.server.tasks.LaunchMinecraftServerTask
import dev.s7a.gradle.minecraft.server.tasks.LaunchMinecraftServerTask.JarUrl

plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.3"
    id("dev.s7a.gradle.minecraft.server") version "2.1.1"
}

group = "de.nicklasmatzulla"
version = "1.0.0-RELEASE"

repositories {
    mavenCentral()
    maven { setUrl("https://repo.papermc.io/repository/maven-public/") }
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.28")
    annotationProcessor("org.projectlombok:lombok:1.18.28")
    implementation("org.jetbrains:annotations:24.0.1")
    compileOnly("io.papermc.paper:paper-api:1.19.4-R0.1-SNAPSHOT")
    compileOnly("net.luckperms:api:5.4")
    implementation("net.kyori:adventure-text-minimessage:4.14.0")
}

bukkit {
    main = "de.nicklasmatzulla.lptac.LPTaC"
    name = "LPTaC"
    version = project.version.toString()
    apiVersion = "1.19"
    depend = listOf("LuckPerms")
    description = "LPTaC allows to create a sorted tablist and chat"
    authors = listOf("Nicklas Matzulla")
}

tasks {
    named<ShadowJar>("shadowJar") {
        archiveClassifier.set("shadow")
        mergeServiceFiles()
        minimize()
    }
    build {
        dependsOn(shadowJar)
    }
    task<LaunchMinecraftServerTask>("testPlugin") {
        dependsOn("build")
        doFirst {
            copy {
                from(buildDir.resolve("libs/${project.name}-${project.version}-shadow.jar"))
                into(buildDir.resolve("MinecraftServer/plugins"))
            }
        }
        jarUrl.set(JarUrl.Paper("1.19.4"))
        agreeEula.set(true)
    }

}