package com.mythicalnetwork.gradle

object Dependencies {
    const val QUILT_MAPPINGS = "org.quiltmc:quilt-mappings:${Versions.MINECRAFT}+build.${Versions.QUILT_MAPPINGS}:intermediary-v2"
    const val QUILT_KOTLIN_CORE = "org.quiltmc.quilt-kotlin-libraries:core:${Versions.QUILT_KOTLIN_CORE}"
    const val QUILT_KOTLIN_LIBRARIES = "org.quiltmc.quilt-kotlin-libraries:library:${Versions.QUILT_KOTLIN_LIBRARIES}"
    const val QUILT_LOADER = "org.quiltmc:quilt-loader:${Versions.QUILT_LOADER}"
    const val QUILTED_FABRIC_API = "org.quiltmc.quilted-fabric-api:quilted-fabric-api:${Versions.QUILTED_FABRIC_API}-${Versions.MINECRAFT}"
    const val ARCHITECTURY = "dev.architectury:architectury-fabric:6.5.85"
    const val JUNIT_JUPITER_API = "org.junit.jupiter:junit-jupiter-api:5.10.0"
    const val JUNIT_JUPITER_ENGINE = "org.junit.jupiter:junit-jupiter-engine:5.10.0"
    const val OWOLIB = "io.wispforest:owo-lib:${Versions.OWOLIB}"
    const val OWOSENTINEL = "io.wispforest:owo-sentinel:${Versions.OWOLIB}"
    const val LUCKPERMS = "net.luckperms:api:5.4"
    const val FABRIC_PERMS_API = "me.lucko:fabric-permissions-api:0.2-SNAPSHOT"
    const val MODMENU = "com.terraformersmc:modmenu:${Versions.MODMENU}"
    const val PLACEHOLDERAPI = "eu.pb4:placeholder-api:${Versions.PLACEHOLDERAPI}"
    const val GECKOLIB = "software.bernie.geckolib:geckolib-quilt-1.19:${Versions.GECKOLIB}"
    const val VEIL = "foundry.veil:Veil-quilt-1.19.2:${Versions.VEIL}"
    const val KINGDOMS_API_COMMON = "com.pokeninjas:common-api:${Versions.KINGDOMS_API}"
    const val KINGDOMS_API_FABRIC = "com.pokeninjas:fabric-api:${Versions.KINGDOMS_API}"
    const val LIGHT_REDIS_MANAGER = "dev.lightdream:redis-manager:${Versions.LIGHT_REDIS_MANAGER}"
    const val LIGHT_LOGGER = "dev.lightdream:logger:+"
    const val LIGHT_FILE_MANAGER = "dev.lightdream:file-manager:+"
    const val LIGHT_COMMAND_MANAGER = "dev.lightdream:command-manager-common:+"
    const val LIGHT_COMMAND_MANAGER_FABRIC = "dev.lightdream:command-manager-fabric-1-19:+"
    const val BAD_PACKETS = "lol.bai:badpackets:fabric-0.2.1"

    val INCLUDE = listOf(
        OWOSENTINEL,
        PLACEHOLDERAPI,
        GECKOLIB,
        VEIL
    )

    val DONT_INCLUDE = listOf(
        QUILT_KOTLIN_CORE,
        QUILT_KOTLIN_LIBRARIES,
        QUILT_LOADER,
        QUILTED_FABRIC_API,
        ARCHITECTURY,
        OWOLIB,
        LUCKPERMS,
        FABRIC_PERMS_API,
        MODMENU,
        KINGDOMS_API_COMMON,
        KINGDOMS_API_FABRIC,
        LIGHT_REDIS_MANAGER,
        BAD_PACKETS
    )

    val NON_MOD_APIS = listOf(
        LIGHT_LOGGER,
        LIGHT_FILE_MANAGER,
        LIGHT_COMMAND_MANAGER,
        LIGHT_COMMAND_MANAGER_FABRIC
    )
}