package com.moigferdsrte.verdantvibes.block.custom;

import com.moigferdsrte.verdantvibes.particles.ParticleInit;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.NonNull;

import java.util.List;

public class BurnweedBlock extends VerdantTallPlantBlock implements BonemealableBlock {
    private static final IntegerProperty SIZE = IntegerProperty.create("size", 0, 2);

    public BurnweedBlock(BlockBehaviour.Properties properties, List<Block> mayPlaceOn, VoxelShape voxelShape) {
        super(properties, mayPlaceOn, voxelShape);
        this.registerDefaultState(this.defaultBlockState().setValue(SIZE, 0).setValue(WATERLOGGED, false));
    }

    @Override
    public void animateTick(BlockState state, @NonNull Level level, @NonNull BlockPos pos, @NonNull RandomSource random) {
        if (state.getValue(HALF) == DoubleBlockHalf.UPPER && level.isClientSide() && random.nextInt(4) == 0) {
            level.addParticle(
                    ParticleInit.BURNWEED_PARTICLES,
                    pos.getX() + 0.5D,
                    pos.getY() + 0.5D + 0.5D * random.nextDouble(),
                    pos.getZ() + 0.5D,
                    0,
                    0,
                    0
            );
        }
        super.animateTick(state, level, pos, random);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, @NonNull ItemStack stack) {
        BlockPos upperPos = pos.above();
        BlockState upper = this.defaultBlockState()
                .setValue(HALF, DoubleBlockHalf.UPPER)
                .setValue(FACING, state.getValue(FACING))
                .setValue(SIZE, state.getValue(SIZE));
        level.setBlock(upperPos, DoublePlantBlock.copyWaterloggedFrom(level, upperPos, upper), 3);
    }

    public static void placeAt(LevelAccessor level, BlockState state, BlockPos pos, int flags) {
        BlockState lower = state.setValue(HALF, DoubleBlockHalf.LOWER);
        BlockState upper = state.setValue(HALF, DoubleBlockHalf.UPPER);
        level.setBlock(pos, DoublePlantBlock.copyWaterloggedFrom(level, pos, lower), flags);
        level.setBlock(pos.above(), DoublePlantBlock.copyWaterloggedFrom(level, pos.above(), upper), flags);
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
        BlockState state = super.getStateForPlacement(context);
        return state == null ? null : state.setValue(SIZE, context.getLevel().getRandom().nextInt(3));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NonNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(SIZE);
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
