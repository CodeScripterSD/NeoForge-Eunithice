package com.craftminerd.eunithice.item.custom;

import com.craftminerd.eunithice.item.enchantment.ModEnchantments;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ExperimentalBow extends BowItem {
    public ExperimentalBow(Properties properties) {
        super(properties);
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 0;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.NONE;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        boolean hasAmmo = !player.getProjectile(stack).isEmpty();
        InteractionResultHolder<ItemStack> ret = EventHooks.onArrowNock(stack, level, player, hand, hasAmmo);
        if (ret != null) return ret;
        else if (!player.hasInfiniteMaterials() && !hasAmmo) {
            return InteractionResultHolder.fail(stack);
        } else {
            ItemStack projectile = player.getProjectile(stack);
            int i = EventHooks.onArrowLoose(stack, level, player, 25, hasAmmo || player.hasInfiniteMaterials());
            if (i < 0) return InteractionResultHolder.consume(stack);

            float f = getPowerForTime(i);
            if (!(f < 0.1D)) {
                List<ItemStack> list = draw(stack, projectile, player);
                if (level instanceof ServerLevel server && !list.isEmpty()) {
                    shoot(server, player, hand, stack, list, f*3f, 1f, f == 1f, null);
                    player.getCooldowns().addCooldown(this, getCooldownDuration(stack));
                }
            }
            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + f * 0.5F);
            player.awardStat(Stats.ITEM_USED.get(this));
        }

        return super.use(level, player, hand);
    }

    private int getCooldownDuration(ItemStack stack) {
        return Math.max(20 - (5 * EnchantmentHelper.getTagEnchantmentLevel(DeferredHolder.create(ModEnchantments.FIRING_SPEED), stack)), 0);
    }

    @Override
    protected void shootProjectile(LivingEntity shooter, Projectile projectile, int index, float velocity, float inaccuracy, float angle, @Nullable LivingEntity target) {
        shootFromRotation(projectile, shooter.getXRot(), shooter.getYRot() + angle, 0.0F, velocity, inaccuracy);
    }

    public void shootFromRotation(Projectile arrow, float x, float y, float z, float velocity, float inaccuracy) {
        float f = -Mth.sin(y * ((float)Math.PI / 180F)) * Mth.cos(x * ((float)Math.PI / 180F));
        float f1 = -Mth.sin((x + z) * ((float)Math.PI / 180F));
        float f2 = Mth.cos(y * ((float)Math.PI / 180F)) * Mth.cos(x * ((float)Math.PI / 180F));
        arrow.shoot(f, f1, f2, velocity, inaccuracy);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tooltip.eunithice.shortbow"));
    }
}
