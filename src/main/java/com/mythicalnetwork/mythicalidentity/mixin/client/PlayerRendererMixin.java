package com.mythicalnetwork.mythicalidentity.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mythicalnetwork.mythicalidentity.backend.cosmetics.wardrobe.Outfit;
import com.mythicalnetwork.mythicalidentity.frontend.MythicalIdentityClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerRenderer.class)
public class PlayerRendererMixin {
    /**
     * Injects into PlayerRenderer#render
        * <p>
     *     This is where the cosmetics are rendered.
     *     This doesn't do anything advanced yet but it should render <i>something</i> so you can see it's working.
     */
    @Inject(at = @At("TAIL"), method = "render(Lnet/minecraft/client/player/AbstractClientPlayer;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V")
    private void renderCosmetics(AbstractClientPlayer entity, float entityYaw, float partialTicks, PoseStack ps, MultiBufferSource buffer, int packedLight, CallbackInfo ci) {
        Outfit outfit = MythicalIdentityClient.INSTANCE.getPLAYER_COSMETICS().get(entity.getUUID());
        if(outfit != null) {
            ps.pushPose();
            ps.translate(0,entity.getBoundingBox().maxY, 0);
            for (int i = 0; i < outfit.getCosmetics().length; i++) {
                Item cosmeticItem = outfit.getCosmetics()[i].getCosmetic().getItem();
                ItemStack cosmeticStack = new ItemStack(cosmeticItem);
                Minecraft.getInstance().getItemRenderer().render(
                        cosmeticStack,
                        ItemTransforms.TransformType.HEAD,
                        false,
                        ps,
                        buffer,
                        packedLight, OverlayTexture.NO_OVERLAY,
                        Minecraft.getInstance().getItemRenderer().getModel(cosmeticStack, Minecraft.getInstance().level, entity, 42)
                );
            }
            ps.popPose();
        }
    }
}
