package com.craftminerd.eunithice.item.custom;

import com.craftminerd.eunithice.item.enchantment.ModEnchantments;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;

public class MechanicalBow extends BowItem {
    public MechanicalBow(Properties properties) {
        super(properties);
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int timeRemaining) {
        if (level instanceof ServerLevel serverLevel && livingEntity instanceof Player player) {
            final int timeElapsed = this.getUseDuration(stack, livingEntity) - timeRemaining;
            int maxTime = 150;
            int trueTimeRemaining = maxTime - timeElapsed;
//            int timeElapsed = maxTime - trueTimeRemaining;
            float percentageProgress = (float) timeElapsed / maxTime;
            int interval = Math.max(5 - stack.getEnchantmentLevel(DeferredHolder.create(ModEnchantments.FIRING_SPEED)), (int) ((1f - percentageProgress) * 10));
            if (trueTimeRemaining % interval == 0) {
                float f2 = getPowerForTime(Math.max(4, timeElapsed));
                shoot(serverLevel, player, player.getUsedItemHand(), stack, draw(stack, player.getProjectile(stack), player), f2 * 3f, 1.0f, f2 == 1f, null);
                level.playSound(
                        null,
                        player.getX(),
                        player.getY(),
                        player.getZ(),
                        SoundEvents.ARROW_SHOOT,
                        SoundSource.PLAYERS,
                        1.0F,
                        1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + f2 * 0.5F
                );
            }
        }
        super.onUseTick(level, livingEntity, stack, timeRemaining);
    }
}
