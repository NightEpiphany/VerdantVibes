package com.moigferdsrte.verdantvibes;

import net.fabricmc.api.ModInitializer;

import com.moigferdsrte.verdantvibes.init.ModBlocks;
import com.moigferdsrte.verdantvibes.init.ModCreativeTab;
import com.moigferdsrte.verdantvibes.init.VillagerInit;
import com.moigferdsrte.verdantvibes.particles.ParticleInit;
import com.moigferdsrte.verdantvibes.worldgen.WorldgenInit;
import com.moigferdsrte.verdantvibes.worldgen.VillageBuildings;
import net.minecraft.resources.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class VerdantVibes implements ModInitializer {
	public static final String MOD_ID = "verdantvibes";


	@Override
	public void onInitialize() {
		ModBlocks.register();
		ModCreativeTab.register();
		ParticleInit.register();
		VillagerInit.register();
		WorldgenInit.initialize();
		VillageBuildings.initialize();
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
