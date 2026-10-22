package com.moigferdsrte.verdantvibes.init;

import com.google.common.collect.ImmutableSet;
import com.moigferdsrte.verdantvibes.VerdantVibes;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.item.trading.TradeSet;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Set;

public final class VillagerInit {
    public static final PoiType GARDENING_POI = createPoi();
    public static final VillagerProfession GARDENER = createProfession();

    private static boolean registered;

    private VillagerInit() {
    }

    public static void register() {
        if (registered) {
            return;
        }

        Registry.register(
                BuiltInRegistries.VILLAGER_PROFESSION,
                VerdantVibes.id("gardener"),
                GARDENER
        );
        registered = true;
    }

    private static PoiType createPoi() {
        Set<BlockState> states =
                Set.copyOf(ModBlocks.GARDENING_TABLE.getStateDefinition().getPossibleStates());
        return PoiTypes.register(
                BuiltInRegistries.POINT_OF_INTEREST_TYPE,
                ResourceKey.create(Registries.POINT_OF_INTEREST_TYPE, VerdantVibes.id("gardening_poi")),
                states,
                1,
                1
        );
    }

    private static VillagerProfession createProfession() {
        Int2ObjectMap<ResourceKey<TradeSet>> tradeSets = new Int2ObjectOpenHashMap<>(4);
        for (int level = 1; level <= 4; level++) {
            tradeSets.put(
                    level,
                    ResourceKey.create(
                            Registries.TRADE_SET,
                            VerdantVibes.id("gardener/level_" + level)
                    )
            );
        }

        return new VillagerProfession(
                Component.translatable("entity.minecraft.villager.verdantvibes.gardener"),
                holder -> holder.value() == GARDENING_POI,
                holder -> holder.value() == GARDENING_POI,
                ImmutableSet.of(),
                ImmutableSet.of(),
                SoundEvents.FLOWERING_AZALEA_HIT,
                tradeSets
        );
    }
}
