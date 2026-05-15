package com.craftminerd.eunithice.block.blockentity;

import com.craftminerd.eunithice.Eunithice;
import com.craftminerd.eunithice.block.ModBlocks;
import com.craftminerd.eunithice.util.ModTags;
import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class SmelteryControllerBlockEntity extends BlockEntity {
    // All units same fuel value.
    // All units have independent time remaining to result.
    // List<Integer> progressLevels -> length equal to number of units.
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
    private final List<BlockPos> effectBlocks = Lists.newArrayList();
    public SmelteryControllerBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.SMELTERY_CONTROLLER_BLOCK_ENTITY.get(), pos, blockState);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, SmelteryControllerBlockEntity entity) {
        // run if we are assembled
        // make sure we should still be assembled
        // tick progresses and handle craft
        long i = level.getGameTime();
        List<BlockPos> list = entity.effectBlocks;
        if (i % 40L == 0L) {
            entity.isAssembled = entity.updateShape(level, pos.relative(state.getValue(HorizontalDirectionalBlock.FACING).getOpposite()), list);
        }
    }
    
    private boolean updateShape(Level level, BlockPos pos, List<BlockPos> positions) {
        positions.clear();
        int controllers = 0;
        int inventories = 0;
        
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    BlockPos off = pos.offset(x, y, z);
                    BlockState state = level.getBlockState(off);
                    Eunithice.LOGGER.info("{}, {}, {} : {}", off.getX(), off.getY(), off.getZ(), state);
                    if (state.is(ModBlocks.SMELTERY_CONTROLLER)) {
                        controllers++;
                    } else if (state.is(ModBlocks.SMELTERY_INVENTORY)) {
                        inventories++;
                    } else if (!state.is(ModTags.Blocks.SMELTERY_VALID_BLOCKS)) {
                        return false;
                    }
                    positions.add(off);
                }
            }
        }
        return positions.size() >= 27 && controllers == 1 && inventories <= 8;
    }

    public boolean isAssembled() {
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


    public void activate(Level level, BlockPos pos) {
        this.isAssembled = updateShape(level, pos, effectBlocks);
    }
}
