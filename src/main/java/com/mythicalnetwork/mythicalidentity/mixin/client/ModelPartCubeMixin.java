package com.mythicalnetwork.mythicalidentity.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mythicalnetwork.mythicalidentity.frontend.util.Hoverable;
import com.mythicalnetwork.mythicalidentity.frontend.util.MousePicker;
import com.mythicalnetwork.mythicalidentity.frontend.util.OrientedBoundingBox;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Function;

@Mixin(targets = "net.minecraft.client.model.geom.ModelPart$Cube")
public class ModelPartCubeMixin implements Hoverable {
    @Unique
    Function<ResourceLocation, RenderType> renderType = null;
    @Unique
    VertexConsumer white = null;

    @Unique
    boolean hovered = false;
    @Unique
    Vec3 offset = Vec3.ZERO;
    @Unique
    Vec3 rotation = Vec3.ZERO;
    @Override
    public boolean isHovered(@NotNull Vec3 start, @NotNull Vec3 end) {
        ModelPart.Cube cube = (ModelPart.Cube) (Object) this;
        AABB box = new AABB(cube.minX, cube.minY, cube.minZ, cube.maxX, cube.maxY, cube.maxZ);
        box = box.move(offset);
        OrientedBoundingBox obb = new OrientedBoundingBox(box);
        obb.rotate((float) rotation.x(), (float) rotation.y());
        hovered = obb.clip(start, end);
//        for(int i = 0; i < 10000; i++){
//            Vec3 pos = start.add(end.scale((i+1)*0.01f));
//            hovered = obb.contains(pos);
//            if(hovered)
//                break;
//        }
        return hovered;
    }

    @Unique
    Matrix4f matrix4f = new Matrix4f();

    @Inject(method = "compile", at = @At("HEAD"))
    public void mythicalidentity$CompileHead(PoseStack.Pose pose, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha, CallbackInfo ci) {
        matrix4f = pose.pose();
    }
    @Redirect(method = "compile", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/VertexConsumer;vertex(FFFFFFFFFIIFFF)V"))
    public void mythicalidentity$RedirectVertex(VertexConsumer instance, float x, float y, float z, float red, float green, float blue, float alpha, float texU, float texV, int overlayUV, int lightmapUV, float normalX, float normalY, float normalZ) {
        if(white == null && renderType != null) {
            MultiBufferSource buffer = Minecraft.getInstance().renderBuffers().bufferSource();
            white = buffer.getBuffer(renderType.apply(Hoverable.Companion.getWhiteTexture()));
        }
        if(hovered && white != null) {
            white.vertex(x,y,z, 1, 0, 1, alpha, texU, texV, OverlayTexture.pack(OverlayTexture.NO_WHITE_U, OverlayTexture.RED_OVERLAY_V), lightmapUV, normalX, normalY, normalZ);
        } else {
            instance.vertex(x,y,z, red, green, blue, alpha, texU, texV, overlayUV, lightmapUV, normalX, normalY, normalZ);
        }
    }

    @Override
    public void setRenderTypeReference(@NotNull Function<ResourceLocation, RenderType> renderType) {
        this.renderType = renderType;
    }

    @Override
    public void setHovered(boolean hovered) {
        this.hovered = hovered;
    }

    @Override
    public void setOffset(@NotNull Vec3 offset) {
        this.offset = offset;
    }

    @Override
    public void setRotation(@NotNull Vec3 rotation) {
        this.rotation = rotation;
    }
}
