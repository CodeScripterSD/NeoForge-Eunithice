package com.craftminerd.eunithice.entity.client;

import com.craftminerd.eunithice.Eunithice;
import com.craftminerd.eunithice.entity.custom.PostarEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class PostarRenderer extends MobRenderer<PostarEntity, PostarModel<PostarEntity>> {
    public PostarRenderer(EntityRendererProvider.Context context) {
        super(context, new PostarModel<>(context.bakeLayer(PostarModel.LAYER_LOCATION)), 0.25f);
    }

    @Override
    public ResourceLocation getTextureLocation(PostarEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(Eunithice.MODID, "textures/entity/postar/postar.png");
    }

    @Override
    public void render(PostarEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        if (entity.isBaby()) {
            poseStack.scale(0.5f, 0.5f, 0.5f);
        } else {
            poseStack.scale(1f, 1f, 1f);
        }
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }
}
