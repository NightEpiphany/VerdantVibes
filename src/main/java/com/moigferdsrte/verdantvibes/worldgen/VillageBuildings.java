package com.moigferdsrte.verdantvibes.worldgen;

import com.mojang.datafixers.util.Pair;
import com.moigferdsrte.verdantvibes.VerdantVibes;
import com.moigferdsrte.verdantvibes.mixin.StructureTemplatePoolAccessor;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class VillageBuildings {
    private static final Logger LOGGER = LoggerFactory.getLogger(VillageBuildings.class);
    private static final Identifier EMPTY_PROCESSOR_LIST = Identifier.withDefaultNamespace("empty");

    private VillageBuildings() {
    }

    public static void initialize() {
        ServerLifecycleEvents.SERVER_STARTING.register(VillageBuildings::addBuildings);
    }

    private static void addBuildings(MinecraftServer server) {
        HolderLookup.RegistryLookup<StructureTemplatePool> pools =
                server.registryAccess().lookupOrThrow(Registries.TEMPLATE_POOL);
        HolderLookup.RegistryLookup<StructureProcessorList> processors =
                server.registryAccess().lookupOrThrow(Registries.PROCESSOR_LIST);
        ResourceKey<StructureProcessorList> processorKey =
                ResourceKey.create(Registries.PROCESSOR_LIST, EMPTY_PROCESSOR_LIST);
        Holder<StructureProcessorList> emptyProcessor = processors.get(processorKey)
                .orElseThrow(() -> new IllegalStateException("Missing minecraft:empty processor list"));

        addBuilding(pools, "minecraft:village/plains/streets", "verdantvibes:gardener_plains",
                emptyProcessor, 2);
        addBuilding(pools, "minecraft:village/taiga/streets", "verdantvibes:gardener_taiga",
                emptyProcessor, 2);
    }

    private static void addBuilding(HolderLookup.RegistryLookup<StructureTemplatePool> pools,
                                    String poolName, String structureName,
                                    Holder<StructureProcessorList> processor, int weight) {
        ResourceKey<StructureTemplatePool> poolKey =
                ResourceKey.create(Registries.TEMPLATE_POOL, Identifier.parse(poolName));
        StructureTemplatePool pool = pools.get(poolKey).map(Holder.Reference::value).orElse(null);
        if (pool == null) {
            LOGGER.warn("Could not add {} because structure pool {} is missing", structureName, poolName);
            return;
        }

        StructurePoolElement piece = SinglePoolElement.legacy(structureName, processor)
                .apply(StructureTemplatePool.Projection.RIGID);
        StructureTemplatePoolAccessor accessor = (StructureTemplatePoolAccessor) pool;
        List<Pair<StructurePoolElement, Integer>> existingTemplates = accessor.verdantVibes$getRawTemplates();
        List<Pair<StructurePoolElement, Integer>> rawTemplates =
                new ArrayList<>(existingTemplates.size() + 1);
        rawTemplates.addAll(existingTemplates);
        rawTemplates.add(Pair.of(piece, weight));
        accessor.verdantVibes$setRawTemplates(rawTemplates);

        ObjectArrayList<StructurePoolElement> templates = accessor.verdantVibes$getTemplates();
        templates.ensureCapacity(templates.size() + weight);
        for (int i = 0; i < weight; i++) {
            templates.add(piece);
        }
    }
}
