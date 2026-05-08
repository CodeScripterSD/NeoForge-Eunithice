package com.craftminerd.eunithice.entity;

import com.craftminerd.eunithice.Eunithice;
import com.craftminerd.eunithice.entity.custom.PostarEntity;
import com.craftminerd.eunithice.entity.projectile.AimingArrow;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, Eunithice.MODID);

    public static final Supplier<EntityType<PostarEntity>> POSTAR = ENTITY_TYPES.register("postar", () ->
            EntityType.Builder.of(PostarEntity::new, MobCategory.CREATURE).sized(0.75f, 0.75f).build("postar"));

    public static final Supplier<EntityType<AimingArrow>> AIMING_ARROW = ENTITY_TYPES.register("aiming_arrow", () ->
            EntityType.Builder.<AimingArrow>of(AimingArrow::new, MobCategory.MISC)
                    .sized(0.5f,0.5f)
                    .eyeHeight(0.65f)
                    .passengerAttachments(0.765f)
                    .clientTrackingRange(8)
                    .build("aiming_arrow"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
