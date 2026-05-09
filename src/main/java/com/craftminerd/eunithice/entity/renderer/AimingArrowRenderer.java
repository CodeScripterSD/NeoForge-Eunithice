package com.craftminerd.eunithice.entity.renderer;

import com.craftminerd.eunithice.entity.projectile.AimingArrow;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class AimingArrowRenderer extends ArrowRenderer<AimingArrow> {
    public static final ResourceLocation AIMING_ARROW_LOCATION = ResourceLocation.withDefaultNamespace("textures/entity/projectiles/aiming_arrow.png");

    public AimingArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(AimingArrow entity) {
        return AIMING_ARROW_LOCATION;
    }
}
