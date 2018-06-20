import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.apache.tools.ant.filters.ReplaceTokens
import org.gradle.api.JavaVersion.VERSION_1_8
import java.net.URI
import org.apache.tools.ant.filters.*
import org.gradle.plugins.ide.idea.model.IdeaLanguageLevel

plugins {
    `java-gradle-plugin`
    kotlin("jvm") version "1.2.50"
    id("com.github.johnrengelman.shadow") version "2.0.3"
    idea
}

group = "com.gitlab.martijn_heil"
version = "1.0-SNAPSHOT"
description = "FullBright"

apply {
    plugin("java")
    plugin("kotlin")
    plugin("idea")
}

java {
    sourceCompatibility = VERSION_1_8
    targetCompatibility = VERSION_1_8
}

tasks["build"].dependsOn("shadowJar")
defaultTasks = listOf("build")

tasks {
    withType<ProcessResources> {
        filter(mapOf(Pair("tokens", mapOf(Pair("version", version)))), ReplaceTokens::class.java)
    }
    withType<ShadowJar> {
        this.classifier = null
        this.configurations = listOf(project.configurations.shadow)
    }
}

repositories {
    maven { url = URI("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") }
    maven { url = URI("https://jitpack.io") }
    maven { url = URI("http://maven.sk89q.com/repo/") }


    mavenCentral()
    mavenLocal()
}

idea {
    project {
        languageLevel = IdeaLanguageLevel("1.8")
    }
    module {
        isDownloadJavadoc = true
        isDownloadSources = true
    }
}

dependencies {
    compileOnly("org.bukkit:bukkit:1.12.2-R0.1-SNAPSHOT") { isChanging = true }
    implementation("com.gitlab.martijn-heil:NinCommands:55bf03cf21") { isChanging = true }

    shadow(kotlin("stdlib"))
}