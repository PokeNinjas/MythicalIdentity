package com.mythicalnetwork.mythicalidentity.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mythicalnetwork.mythicalidentity.frontend.util.Hoverable;
import com.mythicalnetwork.mythicalidentity.frontend.util.MousePicker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.concurrent.atomic.AtomicBoolean;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin<T extends LivingEntity, M extends EntityModel<T>> {

    @Inject(
            method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/entity/LivingEntityRenderer;getOverlayCoords(Lnet/minecraft/world/entity/LivingEntity;F)I",
                    shift = At.Shift.AFTER
            ),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void mythicalidentity$CapturePlayerRender(T entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight, CallbackInfo ci, float f, float g, float h, float j, float i, float k, float l, Minecraft minecraft, boolean bl, boolean bl2, boolean bl3, RenderType renderType, VertexConsumer vertexConsumer){
//        if(Minecraft.getInstance().screen instanceof EffectRenderingInventoryScreen inventoryScreen && entity instanceof LocalPlayer) {
//            PoseStack stack = new PoseStack();
//            stack.setIdentity();
//            stack.mulPoseMatrix(RenderSystem.getModelViewStack().last().pose());
//            int mouseX = (int)(Minecraft.getInstance().mouseHandler.xpos() * (double)Minecraft.getInstance().getWindow().getGuiScaledWidth() / (double)Minecraft.getInstance().getWindow().getScreenWidth());
//            int mouseY = (int)(Minecraft.getInstance().mouseHandler.ypos() * (double)Minecraft.getInstance().getWindow().getGuiScaledHeight() / (double)Minecraft.getInstance().getWindow().getScreenHeight());
//            Vec3 ray = MousePicker.INSTANCE.getRay(RenderSystem.getProjectionMatrix(), stack.last().pose(),
//                    mouseX / (float) Minecraft.getInstance().getWindow().getGuiScaledWidth() * 2 - 1,
//                    mouseY / (float) Minecraft.getInstance().getWindow().getGuiScaledHeight() * 2 - 1);
//            Vec3 start = new Vec3(8,-37.5,0);
//            Vec3 end = start.add(ray.normalize().scale(1000f));
//            LivingEntityRenderer<T, ?> livingEntityRenderer = (LivingEntityRenderer<T, ?>) (Object) this;
//            Model model = livingEntityRenderer.getModel();
//            if(model instanceof AgeableListModel<?> ageableListModel){
//                ageableListModel.bodyParts().forEach(part -> {
//                    Hoverable hoverable = (Hoverable) (Object) part;
//                    hoverable.setRenderTypeReference(s -> livingEntityRenderer.getModel().renderType(s));
//                    hoverable.setHovered(hoverable.isHovered(start, end));
//                });
//                ageableListModel.headParts().forEach(part -> {
//                    Hoverable hoverable = (Hoverable) (Object) part;
//                    hoverable.setRenderTypeReference(s -> livingEntityRenderer.getModel().renderType(s));
//                    hoverable.setHovered(hoverable.isHovered(start, end));
//                });
//            }
//        } else {
//            LivingEntityRenderer<T, ?> livingEntityRenderer = (LivingEntityRenderer<T, ?>) (Object) this;
//            Model model = livingEntityRenderer.getModel();
//            if(model instanceof AgeableListModel<?> ageableListModel){
//                ageableListModel.bodyParts().forEach(part -> {
//                    Hoverable hoverable = (Hoverable) (Object) part;
//                    hoverable.setRenderTypeReference(s -> livingEntityRenderer.getModel().renderType(s));
//                    hoverable.setHovered(false);
//                });
//                ageableListModel.headParts().forEach(part -> {
//                    Hoverable hoverable = (Hoverable) (Object) part;
//                    hoverable.setRenderTypeReference(s -> livingEntityRenderer.getModel().renderType(s));
//                    hoverable.setHovered(false);
//                });
//            }
//        }
    }
}
