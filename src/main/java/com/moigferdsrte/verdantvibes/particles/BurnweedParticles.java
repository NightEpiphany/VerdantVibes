package com.moigferdsrte.verdantvibes.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.RandomSource;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public final class BurnweedParticles extends SingleQuadParticle {
    private final int perParticleLifetime;

    BurnweedParticles(
            ClientLevel level,
            double x,
            double y,
            double z,
            TextureAtlasSprite sprite,
            RandomSource random
    ) {
        super(level, x, y, z, sprite);
        this.perParticleLifetime = random.nextInt(30, 70);
        this.lifetime = perParticleLifetime;
        this.xd += 0.1D * Math.sin(random.nextDouble());
        this.zd += 0.1D * Math.cos(random.nextDouble());
    }

    @Override
    public @NonNull Layer getLayer() {
        return Layer.TRANSLUCENT;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.age >= this.perParticleLifetime) {
            this.remove();
            return;
        }

        this.alpha = (float) Math.cos(((double) this.age / this.perParticleLifetime) * Math.PI / 2.0D);
    }
}
