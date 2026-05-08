package com.craftminerd.eunithice.item.custom;

import com.craftminerd.eunithice.entity.projectile.AimingArrow;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.Nullable;

public class AimingArrowItem extends ArrowItem {
    public AimingArrowItem(Properties properties) {
        super(properties);
    }

    @Override
    public AbstractArrow createArrow(Level level, ItemStack ammo, LivingEntity shooter, @Nullable ItemStack weapon) {
        return new AimingArrow(level, shooter, ammo, weapon);
    }


    @Override
    public Projectile asProjectile(Level level, Position pos, ItemStack stack, Direction direction) {
        AimingArrow aimingArrow = new AimingArrow(level, pos.x(), pos.y(), pos.z(), stack.copyWithCount(1), null);
        aimingArrow.pickup = AbstractArrow.Pickup.ALLOWED;
        return aimingArrow;
    }

    @Override
    public boolean isInfinite(ItemStack ammo, ItemStack bow, LivingEntity livingEntity) {
        return EnchantmentHelper.getTagEnchantmentLevel(DeferredHolder.create(Enchantments.INFINITY), bow) > 0;
    }
}
