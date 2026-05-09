package com.craftminerd.eunithice.entity.client;

import com.craftminerd.eunithice.Eunithice;
import com.craftminerd.eunithice.entity.custom.projectile.AimingArrow;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class AimingArrowRenderer extends ArrowRenderer<AimingArrow> {
    public static final ResourceLocation AIMING_ARROW_LOCATION = ResourceLocation.fromNamespaceAndPath(Eunithice.MODID, "textures/entity/projectiles/aiming_arrow.png");

    public AimingArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(AimingArrow entity) {
        return AIMING_ARROW_LOCATION;
    }
}
