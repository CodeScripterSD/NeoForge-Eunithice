package com.craftminerd.eunithice.screen.custom;

import com.craftminerd.eunithice.block.ModBlocks;
import com.craftminerd.eunithice.block.blockentity.TriggerBlockEntity;
import com.craftminerd.eunithice.block.custom.TriggerBlock;
import com.craftminerd.eunithice.network.TriggerBlockData;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class TriggerScreen extends Screen {
    private static final Component POSITION_LABEL = Component.translatable("eunithice.trigger_block.position");
    private static final Component SIZE_LABEL = Component.translatable("eunithice.trigger_block.size");
    private static final Component ENTITY_SENSITIVITY_LABEL = Component.translatable("eunithice.trigger_block.entity_sensitivity");
    private static final Component SHOW_BOUNDING_BOX_LABEL = Component.translatable("eunithice.trigger_block.show_boundingbox");
    private final TriggerBlockEntity trigger;
    private TriggerBlock.Sensitivity initialSensitivity;
    private boolean initialShowBoundingBox;
    private EditBox posXEdit;
    private EditBox posYEdit;
    private EditBox posZEdit;
    private EditBox sizeXEdit;
    private EditBox sizeYEdit;
    private EditBox sizeZEdit;
    private CycleButton<TriggerBlock.Sensitivity> entitySensitivityButton;
    private CycleButton<Boolean> toggleBoundingBox;
    private final DecimalFormat decimalFormat = new DecimalFormat("0.0###");

    public TriggerScreen(TriggerBlockEntity trigger) {
        super(Component.translatable(ModBlocks.TRIGGER_BLOCK.get().getDescriptionId()));
        this.trigger = trigger;
        this.decimalFormat.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
    }

    private void onDone() {
        if (this.sendToServer())
            this.minecraft.setScreen(null);
    }

    private void onCancel() {
        this.trigger.setSensitivity(this.initialSensitivity);
        this.trigger.setShowBoundingBox(this.initialShowBoundingBox);
        this.minecraft.setScreen(null);
    }

    @Override
    public void onClose() {
        this.onCancel();
    }

    @Override
    protected void init() {
        this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, p_99460_ -> this.onDone()).bounds(this.width / 2 - 4 - 150, 210, 150, 20).build());
        this.addRenderableWidget(Button.builder(CommonComponents.GUI_CANCEL, p_99457_ -> this.onCancel()).bounds(this.width / 2 + 4, 210, 150, 20).build());
        this.initialSensitivity = this.trigger.getSensitivity();
        this.initialShowBoundingBox = this.trigger.getShowBoundingBox();
        this.entitySensitivityButton = this.addRenderableWidget(
                CycleButton.<TriggerBlock.Sensitivity>builder(sensitivity -> Component.translatable("eunithice.trigger_block.mode." + sensitivity.getSerializedName()))
                        .withValues(TriggerBlock.Sensitivity.values())
                        .withInitialValue(this.initialSensitivity)
                        .displayOnlyValue()
                        .create(this.width / 2 + 4 + 75, 160, 75, 20, ENTITY_SENSITIVITY_LABEL, (button, sensitivity) -> {
                            this.trigger.setSensitivity(sensitivity);
                        }));
        this.toggleBoundingBox = this.addRenderableWidget(
                CycleButton.onOffBuilder(this.trigger.getShowBoundingBox())
                        .displayOnlyValue()
                        .create(this.width / 2 + 4 + 100, 80, 50, 20, SHOW_BOUNDING_BOX_LABEL, (p_169849_, p_169850_) ->
                                this.trigger.setShowBoundingBox(p_169850_))
        );
        Vec3 offset = this.trigger.getTriggerOffset();
        this.posXEdit = new EditBox(this.font, this.width / 2 - 152, 80, 80, 20, Component.translatable("structure_block.position.x"));
        this.posXEdit.setMaxLength(15);
        this.posXEdit.setValue(Double.toString(offset.x()));
        this.addWidget(this.posXEdit);
        this.posYEdit = new EditBox(this.font, this.width / 2 - 72, 80, 80, 20, Component.translatable("structure_block.position.y"));
        this.posYEdit.setMaxLength(15);
        this.posYEdit.setValue(Double.toString(offset.y()));
        this.addWidget(this.posYEdit);
        this.posZEdit = new EditBox(this.font, this.width / 2 + 8, 80, 80, 20, Component.translatable("structure_block.position.z"));
        this.posZEdit.setMaxLength(15);
        this.posZEdit.setValue(Double.toString(offset.z()));
        this.addWidget(this.posZEdit);
        Vec3 scale = this.trigger.getTriggerScale();
        this.sizeXEdit = new EditBox(this.font, this.width / 2 - 152, 120, 80, 20, Component.translatable("structure_block.size.x"));
        this.sizeXEdit.setMaxLength(15);
        this.sizeXEdit.setValue(Double.toString(scale.x()));
        this.addWidget(this.sizeXEdit);
        this.sizeYEdit = new EditBox(this.font, this.width / 2 - 72, 120, 80, 20, Component.translatable("structure_block.size.y"));
        this.sizeYEdit.setMaxLength(15);
        this.sizeYEdit.setValue(Double.toString(scale.y()));
        this.addWidget(this.sizeYEdit);
        this.sizeZEdit = new EditBox(this.font, this.width / 2 + 8, 120, 80, 20, Component.translatable("structure_block.size.z"));
        this.sizeZEdit.setMaxLength(15);
        this.sizeZEdit.setValue(Double.toString(scale.z()));
        this.addWidget(this.sizeZEdit);
    }

    private boolean sendToServer() {
        int i = 8;
        Vec3 offset = new Vec3(
                this.parseCoordinate(this.posXEdit.getValue(), i), this.parseCoordinate(this.posYEdit.getValue(), i), this.parseCoordinate(this.posZEdit.getValue(), i)
        );
        Vec3 scale = new Vec3(
                this.parseCoordinate(this.sizeXEdit.getValue(), 1, 5), this.parseCoordinate(this.sizeYEdit.getValue(), 1, 5), this.parseCoordinate(this.sizeZEdit.getValue(), 1, 5)
        );
        PacketDistributor.sendToServer(new TriggerBlockData(
                        this.trigger.getBlockPos(),
                        offset,
                        scale,
                        this.trigger.getSensitivity(),
                        this.trigger.getShowBoundingBox()));

        return true;
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderTransparentBackground(guiGraphics);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (super.keyPressed(keyCode, scanCode, modifiers)) {
            return true;
        } else if (keyCode != 257 && keyCode != 335) {
            return false;
        } else {
            this.onDone();
            return true;
        }
    }

    /**
     * Renders the graphical user interface (GUI) element.
     *
     * @param guiGraphics the GuiGraphics object used for rendering.
     * @param mouseX      the x-coordinate of the mouse cursor.
     * @param mouseY      the y-coordinate of the mouse cursor.
     * @param partialTick the partial tick time.
     */
    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        TriggerBlock.Sensitivity sensitivity = this.trigger.getSensitivity();
        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 10, 16777215);

        guiGraphics.drawString(this.font, POSITION_LABEL, this.width / 2 - 153, 70, 10526880);
        this.posXEdit.render(guiGraphics, mouseX, mouseY, partialTick);
        this.posYEdit.render(guiGraphics, mouseX, mouseY, partialTick);
        this.posZEdit.render(guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.drawString(this.font, ENTITY_SENSITIVITY_LABEL, this.width / 2 + 154 - this.font.width(ENTITY_SENSITIVITY_LABEL), 150, 10526880);

        guiGraphics.drawString(this.font, SIZE_LABEL, this.width / 2 - 153, 110, 10526880);
        this.sizeXEdit.render(guiGraphics, mouseX, mouseY, partialTick);
        this.sizeYEdit.render(guiGraphics, mouseX, mouseY, partialTick);
        this.sizeZEdit.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private double parseCoordinate(String coordinate, int clamp) {
        try {
            return Math.clamp(Double.parseDouble(coordinate), -clamp, clamp);
        } catch (NumberFormatException numberformatexception) {
            return 0;
        }
    }
    private double parseCoordinate(String coordinate, int min, int max) {
        try {
            return Math.clamp(Double.parseDouble(coordinate), min, max);
        } catch (NumberFormatException numberformatexception) {
            return 0;
        }
    }
}
