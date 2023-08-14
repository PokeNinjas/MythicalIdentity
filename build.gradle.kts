import com.mythicalnetwork.gradle.Dependencies
import com.mythicalnetwork.gradle.Versions
import com.mythicalnetwork.gradle.ProjectInfo
import com.mythicalnetwork.gradle.Repos

plugins {
    java
    idea
    id("io.github.juuxel.loom-vineflower") version ("1.11.+")
    id("org.quiltmc.loom") version("1.2.+")
    kotlin("jvm") version ("1.8.22")
    kotlin("kapt") version("1.8.22")
}

group = ProjectInfo.GROUP
version = ProjectInfo.VERSION
base.archivesName.set("MythicalIdentity")

loom {
    mixin.defaultRefmapName.set("mixins.${project.name}.refmap.json")
    interfaceInjection.enableDependencyInterfaceInjection.set(true)
    accessWidenerPath.set(file("src/main/resources/mythicalidentity.accesswidener"))
}

repositories {
    mavenCentral()
    for (repo in Repos.BASE)
        if (repo.contains("sonatype.org"))
            maven(url = repo) {
                name = "sonatype-oss-snapshots1"
                mavenContent { snapshotsOnly() }
            }
        else maven(url = repo)
}


dependencies {
    minecraft("com.mojang:minecraft:${Versions.MINECRAFT}")

    @Suppress("UnstableApiUsage")
    mappings(loom.layered {
        mappings(Dependencies.QUILT_MAPPINGS)
        officialMojangMappings()
        parchment("org.parchmentmc.data:parchment-${Versions.MINECRAFT}:${Versions.PARCHMENT}@zip")
    })

    for (dep in Dependencies.INCLUDE)
        include(dep)?.let {
            if (!dep.contains("owo-sentinel"))
                modImplementation(it)
        }

    for (dep in Dependencies.DONT_INCLUDE) {
        modImplementation(dep)
        if (dep.contains("owo-lib")) {
            annotationProcessor(dep)
            kapt(dep)
        }
    }
    for (dep in Dependencies.NON_MOD_APIS)
        implementation(dep)


    testImplementation(Dependencies.JUNIT_JUPITER_API)
    testRuntimeOnly(Dependencies.JUNIT_JUPITER_ENGINE)
    modImplementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    modCompileOnly(fileTree(mapOf("dir" to "compileOnly", "include" to listOf("*.jar"))))
}

tasks.processResources {
    inputs.property("version", version)

    filesMatching("quilt.mod.json") {
        expand("version" to version)
    }
}

tasks.test.get().useJUnitPlatform()