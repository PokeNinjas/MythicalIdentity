package com.mythicalnetwork.mythicalidentity.backend.cosmetics.wardrobe;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;

/**
 * Represents an outfit, which is a collection of cosmetics.
 * <p>
 * Has a name, description, and a list of Cosmetics.
 * Used to render in {@link com.mythicalnetwork.mythicalidentity.mixin.client.PlayerRendererMixin}
 */
public class Outfit {
    private String name;
    private String description;
    private CosmeticInstance[] cosmetics;

    public Outfit(String name, String description, CosmeticInstance[] cosmetics) {
        this.name = name;
        this.description = description;
        this.cosmetics = cosmetics;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public CosmeticInstance[] getCosmetics() {
        return cosmetics;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCosmetics(CosmeticInstance[] cosmetics) {
        this.cosmetics = cosmetics;
    }

    public CompoundTag toNbt() {
        CompoundTag tag = new CompoundTag();
        tag.putString("name", name);
        tag.putString("description", description);
        ListTag cosmeticList = new ListTag();
        for (CosmeticInstance cosmetic : cosmetics) {
            cosmeticList.add(cosmetic.toNbt());
        }
        tag.put("cosmetics", cosmeticList);
        return tag;
    }

    public static Outfit fromNbt(CompoundTag tag) {
        String name = tag.getString("name");
        String description = tag.getString("description");
        ListTag cosmeticList = tag.getList("cosmetics", 10);
        CosmeticInstance[] cosmetics = new CosmeticInstance[cosmeticList.size()];
        for (int i = 0; i < cosmeticList.size(); i++) {
            cosmetics[i] = CosmeticInstance.fromNbt(cosmeticList.getCompound(i));
        }
        return new Outfit(name, description, cosmetics);
    }
}
