package com.craftminerd.eunithice.block.custom;

import com.craftminerd.eunithice.block.blockentity.ModBlockEntities;
import com.craftminerd.eunithice.block.blockentity.TriggerBlockEntity;
import com.mojang.serialization.MapCodec;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class TriggerBlock extends BaseEntityBlock implements SimpleWaterloggedBlock, EntityBlock {
    public static final MapCodec<TriggerBlock> CODEC = simpleCodec(TriggerBlock::new);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    protected static final AABB TOUCH_AABB = new AABB(0, 0, 0, 1, 1, 1);

    public TriggerBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, false).setValue(POWERED, false));
    }

    @Override
    public @org.jetbrains.annotations.Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return level.isClientSide ? null : createTickerHelper(blockEntityType, ModBlockEntities.TRIGGER_BLOCK_ENTITY.get(), TriggerBlockEntity::serverTick);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        BlockEntity blockentity = level.getBlockEntity(pos);
        if (blockentity instanceof TriggerBlockEntity trigger) {
            return trigger.usedBy(player) ? InteractionResult.sidedSuccess(level.isClientSide) : InteractionResult.PASS;
        } else {
            return InteractionResult.PASS;
        }
    }

    @Override
    protected boolean isSignalSource(BlockState state) {
        return true;
    }

    @Override
    protected int getSignal(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return getSignalForState(state);
    }

    @Override
    protected int getDirectSignal(BlockState state, BlockGetter level, BlockPos pos, Direction side) {
        return side == Direction.UP ? this.getSignalForState(state) : 0;
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (!movedByPiston && !state.is(newState.getBlock())) {
            if (this.getSignalForState(state) > 0) {
                this.updateNeighbours(level, pos);
            }

            super.onRemove(state, level, pos, newState, false);
        }
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        int i = this.getSignalForState(state);
        if (i > 0) {
            this.checkPressed(level, pos, state, i);
        }
    }

    public void checkPressed(Level level, BlockPos pos, BlockState state, int currentSignal) {
        if (level.getBlockEntity(pos) instanceof TriggerBlockEntity triggerEntity) {
            Vec3 scale = triggerEntity.getTriggerScale();
            Vec3 offset = triggerEntity.getTriggerOffset();
            int i = this.getSignalStrength(level, new AABB(Vec3.ZERO.add(scale.scale(-0.5d)), scale.scale(0.5d)), Vec3.atCenterOf(pos).add(offset), triggerEntity.getSensitivity());
//            int i = this.getSignalStrength(level, Vec3.atLowerCornerOf(pos).add(triggerEntity.getTriggerOffset()), triggerEntity.getSensitivity());
            boolean flag1 = i > 0;
            if (currentSignal != i) {
                BlockState blockstate = state.setValue(POWERED, i > 0);
                level.setBlock(pos, blockstate, 2);
                this.updateNeighbours(level, pos);
                level.setBlocksDirty(pos, state, blockstate);
            }

            if (flag1) {
                level.scheduleTick(new BlockPos(pos), this, 20);
            }
        }
    }

    public int getSignalForState(BlockState state) {
        return state.getValue(POWERED) ? 15 : 0;
    }

    public int getSignalStrength(Level level, AABB aabb, Vec3 pos, Sensitivity sensitivity) {
        return getEntityCount(level, aabb.move(pos), sensitivity.getEntityType()) > 0 ? 15 : 0;
    }

    public int getSignalStrength(Level level, Vec3 pos, Sensitivity sensitivity) {
        return getEntityCount(level, TOUCH_AABB.move(pos), sensitivity.getEntityType()) > 0 ? 15 : 0;
    }

    protected static int getEntityCount(Level level, AABB box, Class<? extends Entity> entityClass) {
        return level.getEntitiesOfClass(entityClass, box, EntitySelector.NO_SPECTATORS.and(p_289691_ -> !p_289691_.isIgnoringBlockTriggers())).size();
    }

    public void updateNeighbours(Level level, BlockPos pos) {
        level.updateNeighborsAt(pos, this);
        level.updateNeighborsAt(pos.below(), this);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        // Should probably always show a box, in case the player wants to destroy it but doesn't have another one in their inventory
        return Shapes.box(0.2D, 0.2D, 0.2D, 0.8D, 0.8D, 0.8D);
//        return (context.isHoldingItem(ModBlocks.TRIGGER_BLOCK.asItem()) || context.isHoldingItem(ModItems.CONFIG_TOOL.get())) ? Shapes.box(0.2D, 0.2D, 0.2D, 0.8D, 0.8D, 0.8D) : Shapes.empty();
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(WATERLOGGED, context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER)
                .setValue(POWERED, false);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(POWERED, WATERLOGGED);
    }

    @Override
    protected boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {
        return state.getFluidState().isEmpty();
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new TriggerBlockEntity(blockPos, blockState);
    }

    public enum Sensitivity implements StringRepresentable {
        EVERYTHING("everything", 0, Entity.class),
        LIVING("living", 1, LivingEntity.class),
        PLAYERS("players", 2, Player.class),
        MOBS("mobs", 3, Mob.class),
        ARMOR_STANDS("armor_stands", 4, ArmorStand.class);

        public static final StreamCodec<ByteBuf, Sensitivity> STREAM_CODEC = new StreamCodec<ByteBuf, Sensitivity>() {
            @Override
            public Sensitivity decode(ByteBuf buffer) {
                return Sensitivity.byInt(buffer.readInt());
            }

            @Override
            public void encode(ByteBuf buffer, Sensitivity value) {
                buffer.writeInt(value.getId());
            }
        };

        private final String name;
        private final int id;
        private final Class<? extends Entity> entityType;

        Sensitivity(String name, int id, Class<? extends Entity> entityType) {
            this.name = name;
            this.id = id;
            this.entityType = entityType;
        }

        public int getId() {
            return this.id;
        }

        public Class<? extends Entity> getEntityType() {
            return this.entityType;
        }

        public static Sensitivity byInt(int id) {
            return switch (id) {
                case 1 -> LIVING;
                case 2 -> PLAYERS;
                case 3 -> MOBS;
                case 4 -> ARMOR_STANDS;
                default -> EVERYTHING;
            };
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }
    }

}
