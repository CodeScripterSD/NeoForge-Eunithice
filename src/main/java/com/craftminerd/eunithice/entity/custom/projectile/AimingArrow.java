package com.craftminerd.eunithice.entity.custom.projectile;

import com.craftminerd.eunithice.entity.ModEntities;
import com.craftminerd.eunithice.item.ModItems;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class AimingArrow extends AbstractArrow {

    public AimingArrow(EntityType<? extends AimingArrow> entityType, Level level) {
        super(entityType, level);
    }

    public AimingArrow(Level level, LivingEntity owner, ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon) {
        super(ModEntities.AIMING_ARROW.get(), owner, level, pickupItemStack, firedFromWeapon);
    }

    public AimingArrow(Level level, double x, double y, double z, ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon) {
        super(ModEntities.AIMING_ARROW.get(), x, y, z, level, pickupItemStack, firedFromWeapon);
    }

    public Vec3 aimAt(EntityAnchorArgument.Anchor anchor, Vec3 posTarget) {
        Vec3 posRoot = anchor.apply(this);
        Vec3 posPartial = posTarget.subtract(posRoot);
        return posPartial.multiply(0.5f, 0.5f, 0.5f);
    }

    @Override
    public void tick() {

        LivingEntity TARGET = level().getNearestEntity(level().getEntitiesOfClass(Mob.class, getBoundingBox().inflate(10d)), TargetingConditions.forCombat(), (LivingEntity) this.getOwner(), this.getX(), this.getY(), this.getZ());
        Vec3 vec3 = this.getDeltaMovement();
        if (TARGET != null && this.getOwner() != null && ((LivingEntity)this.getOwner()).hasLineOfSight(TARGET)) {
            vec3 = vec3.add(aimAt(EntityAnchorArgument.Anchor.EYES, TARGET.position().add(0, TARGET.getBbHeight() / 2f, 0)));
            vec3 = vec3.multiply(0.5d, 0.5d, 0.5d);
            this.setDeltaMovement(vec3);
        }
        super.tick();
    }

    @Override
    protected double getDefaultGravity() {
        return 0.02;
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(ModItems.AIMING_ARROW.get());
    }
}
