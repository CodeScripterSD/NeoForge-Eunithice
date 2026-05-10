package com.craftminerd.eunithice.event;

import com.craftminerd.eunithice.Eunithice;
import com.craftminerd.eunithice.item.enchantment.ModEnchantments;
import com.craftminerd.eunithice.network.RenameItemPacket;
import com.craftminerd.eunithice.network.ServerPayloadHandler;
import com.craftminerd.eunithice.network.TriggerBlockData;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.MainThreadPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.registries.DeferredHolder;

@EventBusSubscriber(modid = Eunithice.MODID)
public class ModEvents {
    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");
        registrar.playToServer(TriggerBlockData.TYPE, TriggerBlockData.STREAM_CODEC,
                new MainThreadPayloadHandler<>(ServerPayloadHandler::handleTriggerData));
        registrar.playToServer(RenameItemPacket.TYPE, RenameItemPacket.STREAM_CODEC,
                new MainThreadPayloadHandler<>(ServerPayloadHandler::handleRenameItem));
    }

    @SubscribeEvent
    public static void handleRightClick(PlayerInteractEvent.RightClickItem event) {
        ItemStack stack = event.getItemStack();
        int level = EnchantmentHelper.getTagEnchantmentLevel(DeferredHolder.create(ModEnchantments.TELEPORTITIS), stack);

        if (level > 0) {
            Player player = event.getEntity();
            Vec3 raycast = getPOVHitPosition(player.level(), player, ClipContext.Fluid.NONE, level * 2);
            if (!player.level().isClientSide()) {
                if (!player.isCreative()) {
                    stack.hurtAndBreak(Math.max((level - 1) * 2 + player.getRandom().nextInt(3), 1), player, EquipmentSlot.MAINHAND);
                }
                player.resetFallDistance();
            }
            if (player instanceof ServerPlayer sp) {
                sp.connection.aboveGroundTickCount = 0;
            }
            player.teleportTo(raycast.x, raycast.y, raycast.z);
            player.playSound(SoundEvents.PLAYER_TELEPORT, 1, (player.getRandom().nextFloat() - player.getRandom().nextFloat() * 0.1f + 1f));
            event.setCanceled(true);
        }
    }

    private static Vec3 getPOVHitPosition(Level level, LivingEntity entity, ClipContext.Fluid pFluidMode, double distance) {
        // Copied from Item
        float f = entity.getXRot();
        float f1 = entity.getYRot();
        Vec3 vec3 = entity.getEyePosition();
        float f2 = Mth.cos(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
        float f3 = Mth.sin(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
        float f4 = -Mth.cos(-f * ((float) Math.PI / 180F));
        float f5 = Mth.sin(-f * ((float) Math.PI / 180F));
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        Vec3 vec31 = vec3.add((double) f6 * distance, (double) f5 * distance, (double) f7 * distance);
        BlockHitResult hit = level.clip(new ClipContext(vec3, vec31, ClipContext.Block.COLLIDER, pFluidMode, entity));

        // My Additions
        hit.getBlockPos().relative(hit.getDirection().getOpposite());
        Vec3 semiFinal = new Vec3(hit.getBlockPos().relative(hit.getDirection()).getX() + 0.5, hit.getBlockPos().relative(hit.getDirection()).getY(), hit.getBlockPos().relative(hit.getDirection()).getZ() + 0.5);
        BlockPos semiFinalPos = new BlockPos((int) semiFinal.x, (int) semiFinal.y, (int) semiFinal.z);
        BlockState stateAbove = level.getBlockState(semiFinalPos.above());
        BlockState stateBelow = level.getBlockState(semiFinalPos.below());
        if (stateAbove.isAir() || !stateAbove.getFluidState().isEmpty()) return semiFinal;
        else return semiFinal.add(0, -1, 0);
    }
}
