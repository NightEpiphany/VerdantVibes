package com.moigferdsrte.verdantvibes.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.NonNull;

import java.util.List;

public class VerdantPetalPlantBlock extends FlowerBedBlock implements SimpleWaterloggedBlock {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private final VoxelShape voxelShape;
    private final List<Block> mayPlaceOn;

    public VerdantPetalPlantBlock(BlockBehaviour.Properties properties, List<Block> mayPlaceOn, VoxelShape voxelShape) {
        super(properties, 3);
        this.mayPlaceOn = List.copyOf(mayPlaceOn);
        this.voxelShape = voxelShape;
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false));
    }

    @Override
    public boolean canBeReplaced(@NonNull BlockState state, @NonNull BlockPlaceContext context) {
        return this.canBeReplaced(state, context, AMOUNT);
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, @NonNull BlockGetter level, @NonNull BlockPos pos) {
        if (this.mayPlaceOn.contains(state.getBlock())) {
            return !state.is(Blocks.WATER) || !level.getBlockState(pos.below()).is(Blocks.WATER);
        }
        return super.mayPlaceOn(state, level, pos);
    }

    @Override
    public @NonNull BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        return super.getStateForPlacement(context)
                .setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    @Override
    public @NonNull FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected @NonNull BlockState updateShape(@NonNull BlockState state, @NonNull LevelReader levelReader, @NonNull ScheduledTickAccess scheduledTickAccess, @NonNull BlockPos blockPos, @NonNull Direction direction, @NonNull BlockPos neighborPos, @NonNull BlockState neighborState, @NonNull RandomSource randomSource) {
        if (state.getValue(WATERLOGGED)) {
            scheduledTickAccess.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelReader));
        }
        return super.updateShape(state, levelReader, scheduledTickAccess, blockPos, direction, neighborPos, neighborState, randomSource);
    }

    @Override
    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, AMOUNT, WATERLOGGED);
    }

    @Override
    public @NonNull VoxelShape getShape(@NonNull BlockState state, @NonNull BlockGetter level, @NonNull BlockPos pos, @NonNull CollisionContext context) {
        return this.voxelShape;
    }

    @Override
    protected @NonNull RenderShape getRenderShape(@NonNull BlockState state) {
        return RenderShape.MODEL;
    }
}
