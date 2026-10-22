package com.moigferdsrte.verdantvibes.worldgen;

import com.moigferdsrte.verdantvibes.VerdantVibes;
import com.moigferdsrte.verdantvibes.worldgen.tree.decorator.TrunkIvyDecorator;
import com.moigferdsrte.verdantvibes.worldgen.tree.decorator.TrunkMushroomDecorator;
import com.moigferdsrte.verdantvibes.worldgen.tree.trunkplacer.LyingTrunkPlacer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBiomeTags;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import java.util.function.Predicate;

public final class WorldgenInit {
    private static final TagKey<Biome> CANDY_TUFT_BIOMES =
            TagKey.create(Registries.BIOME, id("candy_tuft_biomes"));

    public static final TrunkPlacerType<LyingTrunkPlacer> LYING_TRUNK_PLACER =
            Registry.register(BuiltInRegistries.TRUNK_PLACER_TYPE,
                    id("lying_trunk_placer"), new TrunkPlacerType<>(LyingTrunkPlacer.CODEC));
    public static final TreeDecoratorType<TrunkIvyDecorator> TRUNK_IVY =
            Registry.register(BuiltInRegistries.TREE_DECORATOR_TYPE,
                    id("trunk_ivy"), new TreeDecoratorType<>(TrunkIvyDecorator.CODEC));
    public static final TreeDecoratorType<TrunkMushroomDecorator> TRUNK_MUSHROOM =
            Registry.register(BuiltInRegistries.TREE_DECORATOR_TYPE,
                    id("trunk_mushroom"), new TreeDecoratorType<>(TrunkMushroomDecorator.CODEC));

    private WorldgenInit() {
    }

    public static void initialize() {
        addFeature(BiomeSelectors.includeByKey(Biomes.BIRCH_FOREST, Biomes.OLD_GROWTH_BIRCH_FOREST),
                "birch_ivy_placed");
        addFeature(BiomeSelectors.includeByKey(Biomes.FOREST), "burnweed");
        addFeature(BiomeSelectors.tag(CANDY_TUFT_BIOMES), "candy_tuft");
        addFeature(BiomeSelectors.tag(ConventionalBiomeTags.IS_SWAMP), "cattails_swamp");
        addFeature(BiomeSelectors.tag(BiomeTags.IS_RIVER), "cattails");
        addFeature(BiomeSelectors.tag(BiomeTags.IS_SAVANNA), "dragon_tree");
        addFeature(BiomeSelectors.tag(BiomeTags.IS_SAVANNA), "lobelia");
        addFeature(BiomeSelectors.tag(BiomeTags.IS_TAIGA), "lying_spruce_mushroom_placed");
        addFeature(BiomeSelectors.tag(ConventionalBiomeTags.IS_SWAMP), "money_tree");
        addFeature(BiomeSelectors.tag(BiomeTags.IS_JUNGLE), "monstera");
        addFeature(BiomeSelectors.includeByKey(Biomes.FOREST), "oak_ivy_placed");
        addFeature(BiomeSelectors.tag(BiomeTags.IS_JUNGLE), "parlour_palm");
        addFeature(BiomeSelectors.tag(BiomeTags.IS_FOREST), "periwinkle");
        addFeature(BiomeSelectors.tag(ConventionalBiomeTags.IS_DESERT), "snake_plant");
        addFeature(BiomeSelectors.tag(BiomeTags.IS_TAIGA), "spruce_mushroom_placed");

        BiomeModifications.create(id("lying_birch_mushroom"))
                .add(ModificationPhase.ADDITIONS,
                        BiomeSelectors.includeByKey(Biomes.BIRCH_FOREST, Biomes.OLD_GROWTH_BIRCH_FOREST),
                        context -> context.getGenerationSettings().addFeature(
                                GenerationStep.Decoration.VEGETAL_DECORATION, placedFeature("lying_birch_mushroom_placed")));
    }

    private static void addFeature(Predicate<BiomeSelectionContext> selector,
                                   String featureName) {
        BiomeModifications.addFeature(selector, GenerationStep.Decoration.VEGETAL_DECORATION, placedFeature(featureName));
    }

    private static ResourceKey<PlacedFeature> placedFeature(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, id(name));
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(VerdantVibes.MOD_ID, path);
    }
}
