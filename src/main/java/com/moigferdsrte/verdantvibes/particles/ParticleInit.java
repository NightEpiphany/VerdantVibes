package com.moigferdsrte.verdantvibes.particles;

import com.moigferdsrte.verdantvibes.VerdantVibes;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.particles.SimpleParticleType;

public final class ParticleInit {
    public static final SimpleParticleType BURNWEED_PARTICLES = FabricParticleTypes.simple(true);

    private static boolean registered;

    private ParticleInit() {
    }

    public static void register() {
        if (registered) {
            return;
        }

        Registry.register(
                BuiltInRegistries.PARTICLE_TYPE,
                VerdantVibes.id("burnweed_particles"),
                BURNWEED_PARTICLES
        );
        registered = true;
    }
}
