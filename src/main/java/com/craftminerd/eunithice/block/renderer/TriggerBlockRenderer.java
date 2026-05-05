package com.craftminerd.eunithice.block.renderer;

import com.craftminerd.eunithice.block.ModBlocks;
import com.craftminerd.eunithice.block.blockentity.TriggerBlockEntity;
import com.craftminerd.eunithice.item.ModItems;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TriggerBlockRenderer implements BlockEntityRenderer<TriggerBlockEntity> {
    public TriggerBlockRenderer(BlockEntityRendererProvider.Context context) {

    }
    @Override
    public void render(TriggerBlockEntity triggerBlockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        Player player = Minecraft.getInstance().player;
        if (player.isHolding(ModBlocks.TRIGGER_BLOCK.asItem())) {
            VertexConsumer consumer = bufferSource.getBuffer(RenderType.lines());
            double a = 0.25F;
            double b = 0.75F;
            LevelRenderer.renderLineBox(poseStack, consumer, a, a, a, b, b, b, 1.0F, 0.0F, 0.0F, 1F);
        } else if (player.isHolding(ModItems.CONFIG_TOOL.asItem())) {
            Vec3 triggerPos = triggerBlockEntity.getTriggerOffset();
            double x = triggerPos.x();
            double z = triggerPos.z();
            double y = triggerPos.y();
            VertexConsumer consumer = bufferSource.getBuffer(RenderType.lines());
            LevelRenderer.renderLineBox(poseStack, consumer, x, y, z, x + 1, y + 1, z + 1, 0.5F, 1.0F, 0.5F, 1.0F);
            double a = 0.25F;
            double b = 0.75F;
            LevelRenderer.renderLineBox(poseStack, consumer, a, a, a, b, b, b, 1.0F, 0.0F, 0.0F, 1F);
        }
    }

    @Override
    public int getViewDistance() {
        return 16;
    }
}
