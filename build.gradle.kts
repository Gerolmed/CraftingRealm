plugins {
    `java-library`
    id("com.github.johnrengelman.shadow") version "7.1.2" apply false
    id("xyz.jpenilla.run-paper") version "1.0.6"
}

buildscript {
    repositories {
        maven("https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath("io.freefair.gradle:lombok-plugin:6.3.0")
        classpath("gradle.plugin.com.github.johnrengelman:shadow:7.1.2")
    }
}

group = "net.endrealm.minecraft"
version = "1.0-SNAPSHOT"

allprojects {

    group = "net.endrealm.minecraft.crafting"

    apply(plugin = "java")
    apply(plugin = "io.freefair.lombok")
    apply(plugin = "com.github.johnrengelman.shadow")

    repositories {
        mavenLocal()
        mavenCentral()

        maven("https://libraries.minecraft.net/")
        maven("https://repo.codemc.io/repository/nms/")
        maven("https://repo.codemc.org/repository/maven-public/")
        maven("https://repo.rapture.pw/repository/maven-releases/")
        maven("https://repo.glaremasters.me/repository/concuncan/")
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
        maven("https://jitpack.io")
        maven("https://maven.enginehub.org/repo/")
    }

    dependencies {
        compileOnly(fileTree("libs").matching {
            include("**/*.jar")
        })
        compileOnly(rootProject.fileTree("libs").matching {
            include("**/*.jar")
        })
        implementation("org.jetbrains:annotations:23.0.0")
    }

    tasks.withType<JavaCompile> {
        options.encoding = Charsets.UTF_8.name()
    }

    tasks.withType<Javadoc> {
        options.encoding = Charsets.UTF_8.name()
    }

    tasks.withType<ProcessResources> {
        filteringCharset = Charsets.UTF_8.name()
    }
    java {
        toolchain {
            toolchain.languageVersion.set(JavaLanguageVersion.of(17))
        }
    }
}

repositories {
    mavenCentral()
}


tasks.runServer {
    minecraftVersion("1.19.2")
    javaLauncher.set(javaToolchains.launcherFor {
        languageVersion.set(JavaLanguageVersion.of(17))
    })
}

afterEvaluate {
    val moveJars by tasks.register("moveFiles", DefaultTask::class);

    moveJars.doFirst {
        this.project.tasks.runServer {
            pluginJars.setFrom(childProjects.values.filter { project -> project.name.contains("plugin") }.map {
                it.tasks.named<AbstractArchiveTask>("shadowJar").flatMap { shadow -> shadow.archiveFile }.get().asFile
            })
        }
    }

    tasks.runServer {
        dependsOn(moveJars)
    }
}
