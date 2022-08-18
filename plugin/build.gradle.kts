import net.minecrell.pluginyml.bukkit.BukkitPluginDescription

version = "1.0.0"

plugins {
    `kotlin-dsl` version ("2.1.7")
    `kotlin-dsl-precompiled-script-plugins`
    id("net.minecrell.plugin-yml.bukkit") version "0.5.2"
}

dependencies {
    implementation("com.github.cryptomorin:XSeries:9.0.0")
    implementation("de.tr7zw:item-nbt-api:2.10.0")
    implementation("fr.minuskube.inv:smart-invs:1.2.7")
    implementation(project(":api"))

    compileOnly("org.spigotmc:spigot-api:1.19.2-R0.1-SNAPSHOT")

    library(kotlin("stdlib")) // All platforms
    library("com.google.code.gson", "gson", "2.8.7") // All platforms
    bukkitLibrary("com.google.code.gson", "gson", "2.8.7") // Bukkit only
}

// Configure plugin.yml generation
bukkit {
    name = "CraftingRealm"
    load = BukkitPluginDescription.PluginLoadOrder.STARTUP
    main = "net.endrealm.minecraft.crafting.plugin.CraftingPluginImpl"
    apiVersion = "1.19"
    authors = listOf("Gerolmed")

    commands {
        register("stationopen") {
            description = "Open station"
        }
    }
}

tasks {
    named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
        relocate("de.tr7zw", "mc.ultimatecore.crafting.depends")
        relocate("fr.minuskube.inv", "mc.ultimatecore.crafting.depends")
    }
}
