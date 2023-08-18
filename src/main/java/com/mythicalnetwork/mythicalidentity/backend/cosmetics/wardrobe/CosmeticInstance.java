package com.mythicalnetwork.mythicalidentity.backend.cosmetics.wardrobe;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

/**
 * Represents a cosmetic instance.
 * <p>
 * Has a Cosmetic reference, and contains things like color, position, "effect" if we do that, etc.
 */
public class CosmeticInstance {

    private Cosmetic cosmetic;
    private int color;
    private Vec3 position;
    private Vec3 rotation;

    public CosmeticInstance(Cosmetic cosmetic) {
        this.cosmetic = cosmetic;
        this.color = 0xFFFFFF;
        this.position = Vec3.ZERO;
        this.rotation = Vec3.ZERO;
    }

    public Cosmetic getCosmetic() {
        return cosmetic;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public Vec3 getPosition() {
        return position;
    }

    public void setPosition(Vec3 position) {
        this.position = position;
    }

    public Vec3 getRotation() {
        return rotation;
    }

    public void setRotation(Vec3 rotation) {
        this.rotation = rotation;
    }

    public static CosmeticInstance fromNbt(CompoundTag tag) {
        CosmeticInstance instance = new CosmeticInstance(CosmeticRegistry.getCosmetic(ResourceLocation.tryParse(tag.getString("id"))));
        instance.setColor(tag.getInt("color"));
        instance.setPosition(new Vec3(tag.getDouble("x"), tag.getDouble("y"), tag.getDouble("z")));
        instance.setRotation(new Vec3(tag.getDouble("rx"), tag.getDouble("ry"), tag.getDouble("rz")));
        return instance;
    }

    public CompoundTag toNbt() {
        CompoundTag tag = new CompoundTag();
        tag.putString("id", cosmetic.getId().toString());
        tag.putInt("color", color);
        tag.putDouble("x", position.x);
        tag.putDouble("y", position.y);
        tag.putDouble("z", position.z);
        tag.putDouble("rx", rotation.x);
        tag.putDouble("ry", rotation.y);
        tag.putDouble("rz", rotation.z);
        return tag;
    }
}
