rootProject.name = "crafting-realm"

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://repo.papermc.io/repository/maven-public/")
    }
}

fileTree(".") {
    include("**/build.gradle.kts")
    exclude("buildSrc/**")
    exclude("build.gradle.kts")
}.map {
    relativePath(it.parent)
        .replace(File.separator, ":")
}.forEach {
    include(it)
}
