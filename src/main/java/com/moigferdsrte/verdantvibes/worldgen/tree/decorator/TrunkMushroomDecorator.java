package com.moigferdsrte.verdantvibes.worldgen.tree.decorator;

import com.mojang.serialization.MapCodec;
import com.moigferdsrte.verdantvibes.block.custom.BracketMushroomBlock;
import com.moigferdsrte.verdantvibes.init.ModBlocks;
import com.moigferdsrte.verdantvibes.worldgen.WorldgenInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import org.jspecify.annotations.NonNull;

public final class TrunkMushroomDecorator extends TreeDecorator {
    public static final TrunkMushroomDecorator INSTANCE = new TrunkMushroomDecorator();
    public static final MapCodec<TrunkMushroomDecorator> CODEC = MapCodec.unit(INSTANCE);

    private TrunkMushroomDecorator() {
    }

    @Override
    protected @NonNull TreeDecoratorType<?> type() {
        return WorldgenInit.TRUNK_MUSHROOM;
    }

    @Override
    public void place(Context context) {
        RandomSource random = context.random();
        context.logs().forEach(logPos -> {
            if (random.nextInt(4) == 0) {
                placeMushroom(context, logPos.west(), Direction.EAST);
            }
            if (random.nextInt(4) == 0) {
                placeMushroom(context, logPos.east(), Direction.WEST);
            }
            if (random.nextInt(4) == 0) {
                placeMushroom(context, logPos.north(), Direction.SOUTH);
            }
            if (random.nextInt(4) == 0) {
                placeMushroom(context, logPos.south(), Direction.NORTH);
            }
        });
    }

    private static void placeMushroom(Context context, BlockPos pos, Direction facing) {
        if (context.isAir(pos)) {
            context.setBlock(pos, ModBlocks.BRACKET_MUSHROOM.defaultBlockState()
                    .setValue(BracketMushroomBlock.FACING, facing));
        }
    }
}
