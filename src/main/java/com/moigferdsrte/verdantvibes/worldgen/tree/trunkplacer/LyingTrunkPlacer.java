package com.moigferdsrte.verdantvibes.worldgen.tree.trunkplacer;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.moigferdsrte.verdantvibes.worldgen.WorldgenInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.function.BiConsumer;

public final class LyingTrunkPlacer extends TrunkPlacer {
    public static final MapCodec<LyingTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec(instance ->
            trunkPlacerParts(instance).apply(instance, LyingTrunkPlacer::new));

    public LyingTrunkPlacer(int baseHeight, int heightRandA, int heightRandB) {
        super(baseHeight, heightRandA, heightRandB);
    }

    @Override
    protected @NonNull TrunkPlacerType<?> type() {
        return WorldgenInit.LYING_TRUNK_PLACER;
    }

    @Override
    public @NonNull List<FoliagePlacer.FoliageAttachment> placeTrunk(
            @NonNull WorldGenLevel level,
            @NonNull BiConsumer<BlockPos, BlockState> trunkSetter,
            @NonNull RandomSource random,
            int treeHeight,
            BlockPos origin,
            @NonNull TreeFeature tree
    ) {
        placeBelowTrunkBlock(level, trunkSetter, random, origin.below(), tree);

        Direction direction = random.nextBoolean() ? Direction.NORTH : Direction.EAST;
        Direction.Axis axis = direction.getAxis();
        for (int i = 0; i < treeHeight; i++) {
            BlockPos logPos = origin.relative(direction, i - 2);
            placeLog(level, trunkSetter, random, logPos, tree,
                    state -> state.setValue(RotatedPillarBlock.AXIS, axis));
        }

        return ImmutableList.of();
    }
}
