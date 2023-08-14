package com.mythicalnetwork.mythicalidentity.frontend.util

import net.minecraft.client.model.geom.ModelPart
import java.util.*

object ModelUtils {

    fun getModelPartTree(path: String, root: ModelPart): List<ModelPart> {
        var mutablePath = path
        val parts: MutableList<ModelPart> = ArrayList()
        parts.add(root)
        if (root.children.isEmpty()) return parts
        mutablePath = mutablePath.substring(mutablePath.indexOf('.') + 1)
        var split = mutablePath.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        if (split[0] == "root") {
            split = split.copyOfRange(1, split.size)
        }
        var part = root
        for (s in split) {
            if (part.hasChild(s)) {
                part = part.getChild(s)
                parts.add(part)
            }
        }
        return parts
    }
}