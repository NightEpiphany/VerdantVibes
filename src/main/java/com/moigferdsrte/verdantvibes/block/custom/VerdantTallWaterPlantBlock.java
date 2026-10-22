package com.moigferdsrte.verdantvibes.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.NonNull;

import java.util.List;

public class VerdantTallWaterPlantBlock extends VerdantTallPlantBlock implements LiquidBlockContainer {

    public VerdantTallWaterPlantBlock(BlockBehaviour.Properties properties, List<Block> mayPlaceOn, VoxelShape voxelShape) {
        super(properties, mayPlaceOn, voxelShape);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(FACING, Direction.NORTH)
                .setValue(HALF, DoubleBlockHalf.LOWER)
                .setValue(WATERLOGGED, false));
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, @NonNull BlockGetter level, @NonNull BlockPos pos) {
        if (this.mayPlaceOn.contains(state.getBlock()) || state.is(BlockTags.SUPPORTS_VEGETATION)
                || state.is(Blocks.SAND) || state.is(Blocks.GRAVEL)) {
            return level.getBlockState(pos.above(2)).isAir();
        }
        return false;
    }

    @Override
    protected void entityInside(
            @NonNull BlockState state,
            @NonNull Level level,
            @NonNull BlockPos pos,
            Entity entity,
            @NonNull InsideBlockEffectApplier effectApplier,
            boolean isMoving
    ) {
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluid = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState()
                .setValue(WATERLOGGED, fluid.isSourceOfType(Fluids.WATER))
                .setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected @NonNull BlockState updateShape(
            @NonNull BlockState state,
            @NonNull LevelReader level,
            @NonNull ScheduledTickAccess tickAccess,
            @NonNull BlockPos pos,
            @NonNull Direction direction,
            @NonNull BlockPos neighborPos,
            @NonNull BlockState neighborState,
            @NonNull RandomSource random
    ) {
        BlockState updated = super.updateShape(
                state, level, tickAccess, pos, direction, neighborPos, neighborState, random);
        if (!updated.isAir()) {
            tickAccess.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return updated;
    }

    @Override
    @NonNull
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected boolean canSurvive(BlockState state, @NonNull LevelReader level, @NonNull BlockPos pos) {
        if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
            BlockState below = level.getBlockState(pos.below());
            return below.is(this) && below.getValue(HALF) == DoubleBlockHalf.LOWER
                    && !state.getValue(WATERLOGGED);
        }
        if (state.is(this)) {
            return state.getValue(WATERLOGGED) && this.mayPlaceOn(level.getBlockState(pos.below()), level, pos);
        }
        FluidState fluid = level.getFluidState(pos);
        return super.canSurvive(state, level, pos) && fluid.is(FluidTags.WATER) && fluid.getAmount() == 8;
    }

    @Override
    public boolean canPlaceLiquid(
            LivingEntity player,
            @NonNull BlockGetter level,
            @NonNull BlockPos pos,
            @NonNull BlockState state,
            @NonNull Fluid fluid
    ) {
        return false;
    }

    @Override
    public boolean placeLiquid(
            @NonNull LevelAccessor level,
            @NonNull BlockPos pos,
            @NonNull BlockState state,
            @NonNull FluidState fluidState
    ) {
        return false;
    }
}
