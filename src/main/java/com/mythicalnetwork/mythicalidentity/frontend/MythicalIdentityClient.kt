package com.mythicalnetwork.mythicalidentity.frontend

import com.mythicalnetwork.mythicalidentity.backend.cosmetics.wardrobe.Outfit
import org.quiltmc.loader.api.ModContainer
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer
import java.util.UUID

object MythicalIdentityClient : ClientModInitializer {

    val PLAYER_COSMETICS: Map<UUID, Outfit> = mutableMapOf()

    override fun onInitializeClient(mod: ModContainer?) {

    }
}