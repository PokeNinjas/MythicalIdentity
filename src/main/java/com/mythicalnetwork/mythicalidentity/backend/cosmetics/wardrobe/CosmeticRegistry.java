package com.mythicalnetwork.mythicalidentity.backend.cosmetics.wardrobe;

import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class CosmeticRegistry {
    public static final Map<ResourceLocation, Cosmetic> COSMETICS = new HashMap<>();

    public static void registerCosmetic(Cosmetic cosmetic) {
        COSMETICS.put(cosmetic.getId(), cosmetic);
    }

    public static Cosmetic getCosmetic(ResourceLocation id) {
        return COSMETICS.get(id);
    }

    public static Map<ResourceLocation, Cosmetic> getCosmetics() {
        return COSMETICS;
    }

    public static void clearCosmetics() {
        COSMETICS.clear();
    }

    public static void registerCosmetics() {

    }
}
