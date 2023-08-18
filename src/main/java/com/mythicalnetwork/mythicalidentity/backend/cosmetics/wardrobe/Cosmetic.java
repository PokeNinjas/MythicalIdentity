package com.mythicalnetwork.mythicalidentity.backend.cosmetics.wardrobe;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

/**
 * Represents a cosmetic.
 * <p>
 * Has a name, description, and an Item reference.
 * Also contains an OutfitPart reference, which is used to determine where the cosmetic is rendered.
 */
public class Cosmetic {
    private ResourceLocation id;
    private Component name;
    private Component description;
    private Item item;
    private OutfitPart outfitPart;

    public Cosmetic(ResourceLocation id, Component name, Component description, Item item, OutfitPart outfitPart) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.item = item;
    }

    public ResourceLocation getId() {
        return id;
    }

    public Component getName() {
        return name;
    }

    public Component getDescription() {
        return description;
    }

    public Item getItem() {
        return item;
    }

    public OutfitPart getOutfitPart() {
        return outfitPart;
    }
}
