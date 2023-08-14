package com.mythicalnetwork.mythicalidentity.frontend.util

import com.mojang.math.Matrix4f
import com.mythicalnetwork.mythicalidentity.mixin.client.Matrix4fAccessor
import kotlin.math.pow

object MagicMath {

    fun bias(x: Float, bias: Float): Float {
        val k = (1.0 - bias).pow(3.0)
        return ((x * k) / (x * k - x + 1.0)).toFloat()
    }

    fun fma(a: Float, b: Float, c: Float): Float {
        return a * b + c
    }

    fun invertGeneric(input: Matrix4f): Matrix4f {
        val result = input.copy()
        val inputAccessor: Matrix4fAccessor = input as Matrix4fAccessor
        val resultAccessor: Matrix4fAccessor = result as Matrix4fAccessor
        val a: Float = inputAccessor.m00() * inputAccessor.m11() - inputAccessor.m01() * inputAccessor.m10()
        val b: Float = inputAccessor.m00() * inputAccessor.m12() - inputAccessor.m02() * inputAccessor.m10()
        val c: Float = inputAccessor.m00() * inputAccessor.m13() - inputAccessor.m03() * inputAccessor.m10()
        val d: Float = inputAccessor.m01() * inputAccessor.m12() - inputAccessor.m02() * inputAccessor.m11()
        val e: Float = inputAccessor.m01() * inputAccessor.m13() - inputAccessor.m03() * inputAccessor.m11()
        val f: Float = inputAccessor.m02() * inputAccessor.m13() - inputAccessor.m03() * inputAccessor.m12()
        val g: Float = inputAccessor.m20() * inputAccessor.m31() - inputAccessor.m21() * inputAccessor.m30()
        val h: Float = inputAccessor.m20() * inputAccessor.m32() - inputAccessor.m22() * inputAccessor.m30()
        val i: Float = inputAccessor.m20() * inputAccessor.m33() - inputAccessor.m23() * inputAccessor.m30()
        val j: Float = inputAccessor.m21() * inputAccessor.m32() - inputAccessor.m22() * inputAccessor.m31()
        val k: Float = inputAccessor.m21() * inputAccessor.m33() - inputAccessor.m23() * inputAccessor.m31()
        val l: Float = inputAccessor.m22() * inputAccessor.m33() - inputAccessor.m23() * inputAccessor.m32()
        var det = a * l - b * k + c * j + d * i - e * h + f * g
        det = 1.0f / det
        resultAccessor.m00(fma(inputAccessor.m11(), l, fma(-inputAccessor.m12(), k, inputAccessor.m13() * j)) * det)
        resultAccessor.m01(fma(-inputAccessor.m01(), l, fma(inputAccessor.m02(), k, -inputAccessor.m03() * j)) * det)
        resultAccessor.m02(fma(inputAccessor.m31(), f, fma(-inputAccessor.m32(), e, inputAccessor.m33() * d)) * det)
        resultAccessor.m03(fma(-inputAccessor.m21(), f, fma(inputAccessor.m22(), e, -inputAccessor.m23() * d)) * det)
        resultAccessor.m10(fma(-inputAccessor.m10(), l, fma(inputAccessor.m12(), i, -inputAccessor.m13() * h)) * det)
        resultAccessor.m11(fma(inputAccessor.m00(), l, fma(-inputAccessor.m02(), i, inputAccessor.m03() * h)) * det)
        resultAccessor.m12(fma(-inputAccessor.m30(), f, fma(inputAccessor.m32(), c, -inputAccessor.m33() * b)) * det)
        resultAccessor.m13(fma(inputAccessor.m20(), f, fma(-inputAccessor.m22(), c, inputAccessor.m23() * b)) * det)
        resultAccessor.m20(fma(inputAccessor.m10(), k, fma(-inputAccessor.m11(), i, inputAccessor.m13() * g)) * det)
        resultAccessor.m21(fma(-inputAccessor.m00(), k, fma(inputAccessor.m01(), i, -inputAccessor.m03() * g)) * det)
        resultAccessor.m22(fma(inputAccessor.m30(), e, fma(-inputAccessor.m31(), c, inputAccessor.m33() * a)) * det)
        resultAccessor.m23(fma(-inputAccessor.m20(), e, fma(inputAccessor.m21(), c, -inputAccessor.m23() * a)) * det)
        resultAccessor.m30(fma(-inputAccessor.m10(), j, fma(inputAccessor.m11(), h, -inputAccessor.m12() * g)) * det)
        resultAccessor.m31(fma(inputAccessor.m00(), j, fma(-inputAccessor.m01(), h, inputAccessor.m02() * g)) * det)
        resultAccessor.m32(fma(-inputAccessor.m30(), d, fma(inputAccessor.m31(), b, -inputAccessor.m32() * a)) * det)
        resultAccessor.m33(fma(inputAccessor.m20(), d, fma(-inputAccessor.m21(), b, inputAccessor.m22() * a)) * det)
        return result
    }

    fun invertProjection(input: Matrix4f): Matrix4f {
        val result = input.copy()
        val inputAccessor =(input as Any) as Matrix4fAccessor
        val resultAccessor = (result as Any) as Matrix4fAccessor
        val a = 1.0f / (inputAccessor.m00() * inputAccessor.m11())
        val l = -1.0f / (inputAccessor.m23() * inputAccessor.m32())
        resultAccessor.m00(inputAccessor.m11() * a)
        resultAccessor.m01(0f)
        resultAccessor.m02(0f)
        resultAccessor.m03(0f)
        resultAccessor.m10(0f)
        resultAccessor.m11(inputAccessor.m00() * a)
        resultAccessor.m12(0f)
        resultAccessor.m13(0f)
        resultAccessor.m20(0f)
        resultAccessor.m21(0f)
        resultAccessor.m22(-inputAccessor.m32() * l)
        resultAccessor.m23(inputAccessor.m22() * l)
        return result
    }
}