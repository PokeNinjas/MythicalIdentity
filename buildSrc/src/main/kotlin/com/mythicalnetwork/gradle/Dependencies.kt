package com.mythicalnetwork.gradle

object Dependencies {
    const val QUILT_MAPPINGS = "org.quiltmc:quilt-mappings:${Versions.MINECRAFT}+build.${Versions.QUILT_MAPPINGS}:intermediary-v2"
    const val QUILT_KOTLIN_CORE = "org.quiltmc.quilt-kotlin-libraries:core:${Versions.QUILT_KOTLIN_LIBRARIES}"
    const val QUILT_KOTLIN_LIBRARIES = "org.quiltmc.quilt-kotlin-libraries:library:${Versions.QUILT_KOTLIN_LIBRARIES}"
    const val QUILT_LOADER = "org.quiltmc:quilt-loader:${Versions.QUILT_LOADER}"
    const val QUILTED_FABRIC_API = "org.quiltmc.quilted-fabric-api:quilted-fabric-api:${Versions.QUILTED_FABRIC_API}-${Versions.MINECRAFT}"
    const val ARCHITECTURY = "dev.architectury:architectury-fabric:6.5.69"
    const val COBBLEMON = "com.cobblemon:fabric:1.3.1+1.19.2-SNAPSHOT"
    const val JUNIT_JUPITER_API = "org.junit.jupiter:junit-jupiter-api:5.9.2"
    const val JUNIT_JUPITER_ENGINE = "org.junit.jupiter:junit-jupiter-engine:5.9.2"
    const val FABRIC_LANGUAGE_KOTLIN = "net.fabricmc:fabric-language-kotlin:${Versions.FABRIC_LANGUAGE_KOTLIN}"
}