package com.mythicalnetwork.mythicalidentity.mixin.client;

import com.mythicalnetwork.mythicalidentity.frontend.util.Hoverable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Mixin(ModelPart.class)
public class ModelPartMixin implements Hoverable {
    @Shadow
    @Final
    public List<ModelPart.Cube> cubes;
    @Unique
    Function<ResourceLocation, RenderType> renderType = null;

    @Unique
    boolean hovered = false;

    @Override
    public boolean isHovered(@NotNull Vec3 start, @NotNull Vec3 end) {
        for (ModelPart.Cube cube : cubes) {
            if (((Hoverable) cube).isHovered(start, end)) {
                ModelPart modelPart = (ModelPart) (Object) this;
                ((Hoverable) cube).setOffset(new Vec3(modelPart.x, modelPart.y - 75, modelPart.z));
                // yaw pitch roll
                ((Hoverable) cube).setRotation(new Vec3(modelPart.xRot, modelPart.yRot, modelPart.zRot));
                return true;
            }
        }
        return false;
    }

    @Override
    public void setRenderTypeReference(@NotNull Function<ResourceLocation, RenderType> renderType) {
        this.renderType = renderType;
        cubes.forEach(cube -> ((Hoverable) cube).setRenderTypeReference(renderType));
    }

    @Override
    public void setHovered(boolean hovered) {
        this.hovered = hovered;
    }

    @Override
    public void setOffset(@NotNull Vec3 offset) {
    }

    @Override
    public void setRotation(@NotNull Vec3 rotation) {

    }
}
