package com.craftminerd.eunithice.entity.client;

import com.craftminerd.eunithice.Eunithice;
import com.craftminerd.eunithice.entity.custom.PostarEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class PostarModel<T extends PostarEntity> extends HierarchicalModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Eunithice.MODID, "postar"), "main");
    private final ModelPart Body;
    private final ModelPart RingX;
    private final ModelPart RingZ;
    private final ModelPart Root;

    public PostarModel(ModelPart root) {
        this.Body = root.getChild("Body");
        this.RingX = this.Body.getChild("RingX");
        this.RingZ = this.Body.getChild("RingZ");
        this.Root = this.Body.getChild("Root");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Body = partdefinition.addOrReplaceChild("Body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition RingX = Body.addOrReplaceChild("RingX", CubeListBuilder.create().texOffs(-2, 0).addBox(-3.0F, -8.0F, -1.0F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(-2, 0).addBox(-3.0F, 7.0F, -1.0F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(3, 0).addBox(-4.0F, -7.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(3, 0).addBox(-4.0F, 6.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(2, 0).addBox(-6.0F, -6.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(2, 0).addBox(-6.0F, 5.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(2, 0).addBox(-6.0F, -5.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(2, 0).addBox(-6.0F, 4.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(2, 0).addBox(-7.0F, -4.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(2, 0).addBox(-7.0F, 3.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(2, 0).addBox(-8.0F, -3.0F, -1.0F, 1.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(3, 0).addBox(3.0F, 6.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(2, 0).addBox(4.0F, 5.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(2, 0).addBox(5.0F, 4.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(2, 0).addBox(6.0F, 3.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(2, 0).addBox(7.0F, -3.0F, -1.0F, 1.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(2, 0).addBox(6.0F, -4.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(2, 0).addBox(5.0F, -5.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(2, 0).addBox(4.0F, -6.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(3, 0).addBox(3.0F, -7.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.0F, 0.0F));

        PartDefinition RingZ = Body.addOrReplaceChild("RingZ", CubeListBuilder.create(), PartPose.offset(0.0F, 10.0F, 0.0F));

        PartDefinition cube_r1 = RingZ.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(2, 0).addBox(1.0F, 0.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(2, 0).addBox(1.0F, 17.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -9.0F, -2.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r2 = RingZ.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(3, 0).addBox(2.0F, 0.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(3, 0).addBox(2.0F, 15.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0F, -3.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r3 = RingZ.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(3, 0).addBox(2.0F, 0.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(3, 0).addBox(2.0F, 13.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -7.0F, -4.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r4 = RingZ.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(3, 0).addBox(2.0F, 0.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(3, 0).addBox(2.0F, 11.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -6.0F, -5.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r5 = RingZ.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(3, 0).addBox(2.0F, 0.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(3, 0).addBox(2.0F, 8.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, -6.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r6 = RingZ.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(3, 0).addBox(2.0F, 0.0F, -1.0F, 1.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, -7.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r7 = RingZ.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(3, 0).addBox(2.0F, 0.0F, -1.0F, 1.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, 12.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r8 = RingZ.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(2, 0).addBox(1.0F, 0.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(2, 0).addBox(1.0F, -17.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 8.0F, 6.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r9 = RingZ.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(3, 0).addBox(2.0F, 0.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(3, 0).addBox(2.0F, -15.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 7.0F, 8.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r10 = RingZ.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(3, 0).addBox(2.0F, 0.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(3, 0).addBox(2.0F, -13.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.0F, 9.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r11 = RingZ.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(3, 0).addBox(2.0F, 0.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(3, 0).addBox(2.0F, -11.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.0F, 10.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r12 = RingZ.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(3, 0).addBox(2.0F, 0.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(3, 0).addBox(2.0F, -8.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.0F, 11.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r13 = RingZ.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(-2, 0).addBox(-3.0F, 0.0F, -1.0F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(-2, 0).addBox(-3.0F, -19.0F, -1.0F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 9.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition Root = Body.addOrReplaceChild("Root", CubeListBuilder.create().texOffs(-6, -4).addBox(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 16, 16);
    }

    @Override
    public void setupAnim(PostarEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.applyHeadRotation(netHeadYaw, headPitch);

        this.animate(entity.idleAnimationState, PostarAnimations.Idle, ageInTicks, 1f);
    }

    private void applyHeadRotation(float headYaw, float headPitch) {
        this.Root.yRot = headYaw * ((float)Math.PI / 180F);
        this.Root.xRot = headPitch * ((float)Math.PI / 180f);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        Body.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }

    @Override
    public ModelPart root() {
        return Body;
    }
}
