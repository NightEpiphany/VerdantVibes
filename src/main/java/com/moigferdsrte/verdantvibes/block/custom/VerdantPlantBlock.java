package com.moigferdsrte.verdantvibes.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.NonNull;

import java.util.List;

public class VerdantPlantBlock extends BushBlock implements BonemealableBlock, SimpleWaterloggedBlock {
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private final VoxelShape voxelShape;
    private final List<Block> mayPlaceOn;

    public VerdantPlantBlock(BlockBehaviour.Properties properties, List<Block> mayPlaceOn, VoxelShape voxelShape) {
        super(properties);
        this.mayPlaceOn = List.copyOf(mayPlaceOn);
        this.voxelShape = voxelShape;
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false));
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
    protected boolean mayPlaceOn(BlockState state, @NonNull BlockGetter level, @NonNull BlockPos pos) {
        return this.mayPlaceOn.contains(state.getBlock()) || super.mayPlaceOn(state, level, pos);
    }

    @Override
    protected @NonNull VoxelShape getShape(BlockState state, @NonNull BlockGetter level, @NonNull BlockPos pos, @NonNull CollisionContext context) {
        Vec3 offset = state.getOffset(pos);
        return this.voxelShape.move(offset.x, offset.y, offset.z);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    @Override
    protected @NonNull RenderShape getRenderShape(@NonNull BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected @NonNull BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    protected @NonNull BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NonNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, WATERLOGGED);
    }

    @Override
    public boolean isValidBonemealTarget(@NonNull LevelReader level, @NonNull BlockPos pos, @NonNull BlockState state, @NonNull BonemealSource source) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(@NonNull Level level, @NonNull RandomSource random, @NonNull BlockPos pos, @NonNull BlockState state, @NonNull BonemealSource source) {
        return true;
    }

    @Override
    public void performBonemeal(@NonNull ServerLevel level, @NonNull RandomSource random, @NonNull BlockPos pos, @NonNull BlockState state, @NonNull BonemealSource source) {
        popResource(level, pos, new ItemStack(this));
    }
}
