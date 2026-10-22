package com.moigferdsrte.verdantvibes.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.FabricSpriteSet;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public final class BurnweedParticleProvider implements ParticleProvider<SimpleParticleType> {
    private final FabricSpriteSet sprites;

    public BurnweedParticleProvider(FabricSpriteSet sprites) {
        this.sprites = sprites;
    }

    @Override
    public Particle createParticle(
            @NonNull SimpleParticleType type,
            @NonNull ClientLevel level,
            double x,
            double y,
            double z,
            double velocityX,
            double velocityY,
            double velocityZ,
            @NonNull RandomSource random
    ) {
        return new BurnweedParticles(level, x, y, z, this.sprites.get(random), random);
    }
}
