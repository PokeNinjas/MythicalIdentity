rootProject.name = "MythicalIdentity"

pluginManagement.repositories {
        maven("https://maven.fabricmc.net/")
        maven("https://maven.architectury.dev/")
        maven("https://maven.minecraftforge.net/")
        maven("https://maven.quiltmc.org/repository/release")
        gradlePluginPortal()
}

plugins {
    id("com.gradle.enterprise") version "3.14.1"
}

gradleEnterprise.buildScan {
    termsOfServiceUrl = "https://gradle.com/terms-of-service"
    termsOfServiceAgree = "yes"
}