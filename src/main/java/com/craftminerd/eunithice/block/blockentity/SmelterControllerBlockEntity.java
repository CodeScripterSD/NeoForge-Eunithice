package com.craftminerd.eunithice.block.blockentity;

import com.craftminerd.eunithice.Eunithice;
import com.craftminerd.eunithice.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class SmelterControllerBlockEntity extends BlockEntity {
    // All units same fuel value.
    // All units have independent time remaining to result.
    // List<Integer> progressLevels -> length equal to number of units.
    // Deciding whether I want to make it check its bounds like the Tinker's Construct Smeltery, or have predefined confines like Immersive Engineering coke smelter.
    // Probably latter, but potentially upgrade to adjustable bounds once we have a working prototype.
    // Assuming a 3x3x3, we need at most 27 slots for input, 27 slots for output, and 1 slot for fuel.
    // That's a lot of slots, so I'll need to figure out a way of mathematically determining screen and menu proportions that accurately places the slots in the correct positions.
    // We will have 3 types of block relevant to the smelter array.
    // 1 -> Smelter Controller - This is the block that checks the bounds and operates as the full housing for the smelter.
    // 2 -> Smelter Block - This block contributes nothing to the functionality, only serving as a placeholder for blocks for the smelter.
    // 3 -> Smelter Inventory Block - These blocks are responsible for increasing the smelting capacity of the multiblock.
    // For optimization, should combine layers into a single slot that processes X amount of items at once, where X is the number of Inventory Blocks in that layer.
    // This way, we only need 3 slots in the smelter.
    // This behavior may be undesired, so what about
    // Only Inventory Blocks in the same layer as the Controller are counted, giving the inventory a maximum size of 9.
    // This gives space for special blocks that allow automated item Input and Output on the top and bottom.
    // Can group the slots into 3s, where we tick progress for all slots at the same time.
    // Items that are added into a slot while the Smelter progress is above 0 will not begin smelting until the progress reaches 0 again.
    // Can do this by setting slot ids to process when we begin smelting.
    // Then, how do we handle when item X in slot [X], [a], [b] is removed, but a and b are not empty? We can use the same ids to check if an item remains in the slot
    // When an item is removed from a slot that is set to be processed, unmark the slot for processing.
    // So, the final data should look like:
    private int litTime;
    private int litDuration;
    private final NonNullList<Integer> progresses = NonNullList.create(); // [groupA_progress, groupB_progress, groupC_progress]
    private int maxProgress;
    private final ArrayList<Boolean> slotsActivelyCooking = new ArrayList<>();
    private boolean isAssembled;
    public SmelterControllerBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.SMELTER_CONTROLLER_BLOCK_ENTITY.get(), pos, blockState);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, SmelterControllerBlockEntity entity) {
        // run if we are assembled
        // make sure we should still be assembled
        // tick progresses and handle craft
        if (entity.isAssembled()) {
            if (!entity.checkShouldBeAssembled()) {
                entity.setChanged();
                return;
            }

        }
    }

    public boolean checkShouldBeAssembled() {
        Level level = this.getLevel();
        if (level == null || level.isClientSide) return false;
        BlockState state = level.getBlockState(this.getBlockPos());
        if (!state.is(ModBlocks.SMELTER_CONTROLLER)) return false;
        Eunithice.LOGGER.info("Definitely should check");
        Direction searchDirection = state.getValue(HorizontalDirectionalBlock.FACING).getOpposite();
        BlockPos center = this.getBlockPos().relative(searchDirection);
        boolean old = this.isAssembled;
        boolean a = search3x3(level, center, true);
        boolean b = search3x3(level, center, false);
        boolean c = search3x3(level, center, false);
        Eunithice.LOGGER.info("a {} b {} c {}", a, b, c);
        boolean now = a && b && c;
        if (old != now) {
            this.isAssembled = now;
            setChanged();
        }
        return this.isAssembled;
    }

    private boolean search3x3(Level level, BlockPos pos, boolean flag) {
        if (flag) {
            int countInventories = 0;
            int countControllers = 0;
            for (int i = -1; i < 1; i++) {
                for (int j = -1; j < 1; j++) {
                    if (countControllers > 1) return false; // too many controllers
                    BlockState offset = withOffset(level, pos, i, j);
                    if (offset.is(ModBlocks.SMELTER_INVENTORY)) countInventories++; // inventory
                    else if (offset.is(ModBlocks.SMELTER_CONTROLLER)) countControllers++; // 1 controller
                    else if (!offset.is(ModBlocks.SMELTER_HOUSING)) {// not a housing, controller, or inventory
                        return false;
                    }
                }
            }
            //TODO: store the amount of connected inventories so we don't need to recalculate 500 times per tick
            return countControllers == 1;
        } else {
            for (int i = -1; i < 1; i++) {
                for (int j = -1; j < 1; j++) {
                    if (!withOffset(level, pos, i, j).is(ModBlocks.SMELTER_HOUSING)) return false;
                }
            }
            return true;
        }
    }

    private BlockState withOffset(Level level, BlockPos pos, int x, int z) {
        return level.getBlockState(pos.offset(x, 0, z));
    }

    private boolean isAssembled() {
        return isAssembled;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        tag.putInt("LitTime", litTime);
        tag.putInt("LitDuration", litDuration);
        tag.putInt("MaxProgress", maxProgress);
        tag.putIntArray("Progresses", progresses);
        List<Byte> activelyCookingSlots = new ArrayList<>(List.of());
        slotsActivelyCooking.forEach(val -> activelyCookingSlots.add((byte) (val ? 1 : 0)));
        tag.putByteArray("SlotsActivelyCooking", activelyCookingSlots);
        tag.putBoolean("IsAssembled", isAssembled);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        this.litTime = tag.getInt("LitTime");
        this.litDuration = tag.getInt("LitDuration");
        this.maxProgress = tag.getInt("MaxProgress");
        int[] progresses = tag.getIntArray("Progresses");
        this.progresses.clear();
        for (int j : progresses) {
            this.progresses.add(j);
        }
        byte[] activelyCookingSlots = tag.getByteArray("SlotsActivelyCooking");
        for (byte j : activelyCookingSlots) {
            this.slotsActivelyCooking.add(j == 1);
        }
        this.isAssembled = tag.getBoolean("IsAssembled");
    }


}
