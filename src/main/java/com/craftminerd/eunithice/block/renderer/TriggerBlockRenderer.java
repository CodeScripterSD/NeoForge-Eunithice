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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TriggerBlockRenderer implements BlockEntityRenderer<TriggerBlockEntity> {
    @Override
    public void render(TriggerBlockEntity triggerBlockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        Player player = Minecraft.getInstance().player;
        assert player != null;
        if (triggerBlockEntity.getShowBoundingBox()) {
            Vec3 offset = triggerBlockEntity.getTriggerOffset();
            Vec3 scale = triggerBlockEntity.getTriggerScale();
            Vec3 cornerA = offset.add(scale.add(-1d, -1d, -1d).scale(-0.5d));
            Vec3 cornerB = offset.add(scale.add(1d,1d,1d).scale(0.5d));
            double x = cornerA.x();
            double y = cornerA.y();
            double z = cornerA.z();
            double xB = cornerB.x();
            double yB = cornerB.y();
            double zB = cornerB.z();
            VertexConsumer consumer = bufferSource.getBuffer(RenderType.lines());
            LevelRenderer.renderLineBox(poseStack, consumer, x, y, z, xB, yB, zB, 0.5F, 1.0F, 0.5F, 1.0F, 0.0f, 0.0f, 1.0f);
            double a = 0.25F;
            double b = 0.75F;
            LevelRenderer.renderLineBox(poseStack, consumer, a, a, a, b, b, b, 1.0F, 0.0F, 0.0F, 1F);
        } else if (player.isHolding(ModItems.CONFIG_TOOL.asItem()) || player.isHolding(ModBlocks.TRIGGER_BLOCK.asItem())) {
            VertexConsumer consumer = bufferSource.getBuffer(RenderType.lines());
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
