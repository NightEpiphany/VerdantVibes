package com.moigferdsrte.verdantvibes.worldgen.tree.decorator;

import com.mojang.serialization.MapCodec;
import com.moigferdsrte.verdantvibes.block.custom.VerdantVineBlock;
import com.moigferdsrte.verdantvibes.init.ModBlocks;
import com.moigferdsrte.verdantvibes.worldgen.WorldgenInit;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import org.jspecify.annotations.NonNull;

public final class TrunkIvyDecorator extends TreeDecorator {
    public static final TrunkIvyDecorator INSTANCE = new TrunkIvyDecorator();
    public static final MapCodec<TrunkIvyDecorator> CODEC = MapCodec.unit(INSTANCE);

    private TrunkIvyDecorator() {
    }

    @Override
    protected @NonNull TreeDecoratorType<?> type() {
        return WorldgenInit.TRUNK_IVY;
    }

    @Override
    public void place(Context context) {
        RandomSource random = context.random();
        context.logs().forEach(logPos -> {
            if (random.nextInt(3) > 0) {
                placeIvy(context, logPos.west(), VerdantVineBlock.EAST);
            }
            if (random.nextInt(3) > 0) {
                placeIvy(context, logPos.east(), VerdantVineBlock.WEST);
            }
            if (random.nextInt(3) > 0) {
                placeIvy(context, logPos.north(), VerdantVineBlock.SOUTH);
            }
            if (random.nextInt(3) > 0) {
                placeIvy(context, logPos.south(), VerdantVineBlock.NORTH);
            }
        });
    }

    private static void placeIvy(Context context, BlockPos pos, BooleanProperty side) {
        if (context.isAir(pos)) {
            context.setBlock(pos, ModBlocks.IVY.defaultBlockState().setValue(side, true));
        }
    }
}
