package com.moigferdsrte.verdantvibes.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.BonemealSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.block.Block;
import org.jspecify.annotations.NonNull;

import java.util.List;

public class VerdantGrowablePlantBlock extends VerdantPlantBlock {
    private final VerdantTallPlantBlock grownPlant;

    public VerdantGrowablePlantBlock(
            BlockBehaviour.Properties properties,
            List<Block> mayPlaceOn,
            VoxelShape voxelShape,
            VerdantTallPlantBlock grownPlant
    ) {
        super(properties, mayPlaceOn, voxelShape);
        this.grownPlant = grownPlant;
    }

    @Override
    public void performBonemeal(
            @NonNull ServerLevel level,
            @NonNull RandomSource random,
            @NonNull BlockPos pos,
            @NonNull BlockState state,
            @NonNull BonemealSource source
    ) {
        if (level.getBlockState(pos.above()).isAir()) {
            BlockState grown = this.grownPlant.defaultBlockState()
                    .setValue(VerdantTallPlantBlock.FACING, state.getValue(FACING));
            level.setBlock(pos, grown, 3);
            level.setBlock(pos.above(), grown.setValue(VerdantTallPlantBlock.HALF,
                    DoubleBlockHalf.UPPER), 3);
        }
    }
}
