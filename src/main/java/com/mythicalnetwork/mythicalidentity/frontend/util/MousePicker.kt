package com.mythicalnetwork.mythicalidentity.frontend.util

import com.mojang.math.Matrix4f
import com.mojang.math.Vector4f
import net.minecraft.world.phys.Vec3

object MousePicker {
    fun getRay(projectionMatrix: Matrix4f, viewMatrix: Matrix4f, normalisedMouseX: Float, normalisedMouseY: Float): Vec3 {
        val clipCoords = Vector4f(normalisedMouseX, -normalisedMouseY, -1.0f, 1.0f)
        val eyeSpace = toEyeCoords(projectionMatrix, clipCoords)
        return toWorldCoords(viewMatrix, eyeSpace)
    }

    fun toEyeCoords(projectionMatrix: Matrix4f, clipCoords: Vector4f): Vector4f {
        val inverse = MagicMath.invertProjection(projectionMatrix)
        val result = Vector4f(clipCoords.x(), clipCoords.y(), clipCoords.z(), clipCoords.w())
        result.transform(inverse)
        result.set(result.x(), result.y(), -1.0f, 0.0f)
        return result
    }
    fun toWorldCoords(viewMatrix: Matrix4f, eyeCoords: Vector4f): Vec3 {
        val inverse = MagicMath.invertGeneric(viewMatrix)
        val result = Vector4f(eyeCoords.x(), eyeCoords.y(), eyeCoords.z(), eyeCoords.w())
        result.transform(inverse)
        return Vec3(result.x().toDouble(), result.y().toDouble(), result.z().toDouble()).normalize()
    }
}