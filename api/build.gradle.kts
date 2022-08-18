version = "1.0.0"

dependencies {
    implementation("com.github.cryptomorin:XSeries:9.0.0")
    implementation("de.tr7zw:item-nbt-api:2.10.0")

    compileOnly("org.spigotmc:spigot-api:1.19.2-R0.1-SNAPSHOT")
}
repositories {
    mavenCentral()
}

tasks {
    named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
        relocate("de.tr7zw", "mc.ultimatecore.crafting.depends")
    }
}
