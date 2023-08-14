package com.mythicalnetwork.mythicalidentity.frontend.util

import net.minecraft.client.renderer.RenderType
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.phys.Vec3
import java.util.function.Function

interface Hoverable {

    companion object {
        val whiteTexture: ResourceLocation = ResourceLocation("mythicalidentity:textures/special/white.png")
    }
    fun isHovered(start: Vec3, end: Vec3): Boolean
    fun setHovered(hovered: Boolean)
    fun setOffset(offset: Vec3)
    fun setRotation(rotation: Vec3)
    fun setRenderTypeReference(renderType: Function<ResourceLocation, RenderType>)
}