import com.mythicalnetwork.gradle.Dependencies
import com.mythicalnetwork.gradle.Versions
import com.mythicalnetwork.gradle.ProjectInfo

plugins {
    id("java")
    id("io.github.juuxel.loom-quiltflower") version ("1.8.+")
    id("org.quiltmc.loom") version("1.0.+")
    kotlin("jvm") version ("1.8.0")
}

group = ProjectInfo.GROUP
version = ProjectInfo.VERSION

loom {
    mixin {
        defaultRefmapName.set("mixins.${project.name}.refmap.json")
    }
    interfaceInjection {
        enableDependencyInterfaceInjection.set(true)
    }
}

repositories {
    mavenCentral()
    maven(url = "https://dl.cloudsmith.io/public/geckolib3/geckolib/maven/")
    maven("https://maven.impactdev.net/repository/development/")
    maven(url = "https://maven.quiltmc.org/repository/snapshot/")
    maven (url = "https://maven.quiltmc.org/repository/release")
    maven (url = "https://maven.fabricmc.net/")
    maven("https://maven.architectury.dev/")
}


dependencies {
    minecraft("com.mojang:minecraft:${Versions.MINECRAFT}")
    mappings(loom.layered {
        mappings(Dependencies.QUILT_MAPPINGS)
        officialMojangMappings()
    })
    modImplementation(Dependencies.QUILT_LOADER)
    modImplementation(Dependencies.QUILT_KOTLIN_CORE)
    modImplementation(Dependencies.QUILT_KOTLIN_LIBRARIES)
//    using FLK works, but using QKL pulls in version 5.0.0 of QSL, which is for 1.19.4, for some reason?
//    modImplementation(Dependencies.FABRIC_LANGUAGE_KOTLIN)
    modImplementation(Dependencies.QUILTED_FABRIC_API)
    modImplementation(Dependencies.ARCHITECTURY)
    modImplementation(Dependencies.COBBLEMON)

    testImplementation(Dependencies.JUNIT_JUPITER_API)
    testRuntimeOnly(Dependencies.JUNIT_JUPITER_ENGINE)
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}