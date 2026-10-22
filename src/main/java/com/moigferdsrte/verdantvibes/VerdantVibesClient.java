package com.moigferdsrte.verdantvibes;

import com.moigferdsrte.verdantvibes.init.ModBlocks;
import com.moigferdsrte.verdantvibes.particles.BurnweedParticleProvider;
import com.moigferdsrte.verdantvibes.particles.ParticleInit;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockColorRegistry;
import net.minecraft.client.color.block.BlockTintSources;

import java.util.List;

@Environment(EnvType.CLIENT)
public final class VerdantVibesClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ParticleProviderRegistry.getInstance().register(
                ParticleInit.BURNWEED_PARTICLES,
                BurnweedParticleProvider::new
        );

        BlockColorRegistry.register(
                List.of(BlockTintSources.foliage()),
                ModBlocks.PARLOUR_PALM,
                ModBlocks.DRAGON_TREE,
                ModBlocks.MONSTERA,
                ModBlocks.LOBELIA,
                ModBlocks.IVY,
                ModBlocks.MONEY_TREE,
                ModBlocks.TALL_DRAGON_TREE,
                ModBlocks.CATTAILS,
                ModBlocks.BURNWEED
        );
        BlockColorRegistry.register(
                List.of(BlockTintSources.constant(-1), BlockTintSources.foliage()),
                ModBlocks.CANDY_TUFT,
                ModBlocks.PERIWINKLE
        );
    }
}
