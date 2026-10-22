package com.moigferdsrte.verdantvibes.init;

import com.moigferdsrte.verdantvibes.VerdantVibes;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public final class ModCreativeTab {
    public static final ResourceKey<CreativeModeTab> KEY =
            ResourceKey.create(Registries.CREATIVE_MODE_TAB, VerdantVibes.id("verdantvibes"));

    private static boolean registered;

    private ModCreativeTab() {
    }

    public static void register() {
        if (registered) {
            return;
        }
        CreativeModeTab tab = CreativeModeTab.builder(CreativeModeTab.Row.TOP, 7)
                .title(Component.translatable("itemGroup.verdantvibes"))
                .icon(() -> new ItemStack(ModBlocks.DRAGON_TREE))
                .displayItems((parameters, output) -> {
                    output.accept(ModBlocks.GARDENING_TABLE);
                    output.accept(ModBlocks.MONSTERA);
                    output.accept(ModBlocks.SNAKE_PLANT);
                    output.accept(ModBlocks.PARLOUR_PALM);
                    output.accept(ModBlocks.MONEY_TREE);
                    output.accept(ModBlocks.LOBELIA);
                    output.accept(ModBlocks.DRAGON_TREE);
                    output.accept(ModBlocks.CATTAILS);
                    output.accept(ModBlocks.BURNWEED);
                    output.accept(ModBlocks.CANDY_TUFT);
                    output.accept(ModBlocks.PERIWINKLE);
                    output.accept(ModBlocks.IVY);
                    output.accept(ModBlocks.BRACKET_MUSHROOM);
                })
                .build();
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, KEY, tab);
        registered = true;
    }
}
