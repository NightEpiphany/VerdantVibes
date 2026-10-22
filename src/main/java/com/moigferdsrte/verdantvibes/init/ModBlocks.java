package com.moigferdsrte.verdantvibes.init;

import com.moigferdsrte.verdantvibes.VerdantVibes;
import com.moigferdsrte.verdantvibes.block.custom.BracketMushroomBlock;
import com.moigferdsrte.verdantvibes.block.custom.BurnweedBlock;
import com.moigferdsrte.verdantvibes.block.custom.TurnableFlowerPotBlock;
import com.moigferdsrte.verdantvibes.item.VerdantBlockItem;
import com.moigferdsrte.verdantvibes.block.custom.VerdantGrowablePlantBlock;
import com.moigferdsrte.verdantvibes.block.custom.VerdantPetalPlantBlock;
import com.moigferdsrte.verdantvibes.block.custom.VerdantPlantBlock;
import com.moigferdsrte.verdantvibes.block.custom.VerdantTallPlantBlock;
import com.moigferdsrte.verdantvibes.block.custom.VerdantTallWaterPlantBlock;
import com.moigferdsrte.verdantvibes.block.custom.VerdantVineBlock;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.List;

public final class ModBlocks {
    public static final Block GARDENING_TABLE = register("gardening_table",
            new Block(properties("gardening_table", Blocks.CRAFTING_TABLE)));

    public static final Block MONSTERA = register("monstera", new VerdantPlantBlock(
            properties("monstera", BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .noCollision()
                    .instabreak()
                    .sound(SoundType.CHERRY_SAPLING)
                    .offsetType(BlockBehaviour.OffsetType.XZ)
                    .ignitedByLava()
                    .pushReaction(PushReaction.POPPED)),
            List.of(), Block.box(3, 0, 3, 13, 15, 13)));

    public static final Block SNAKE_PLANT = register("snake_plant", new VerdantPlantBlock(
            properties("snake_plant", MONSTERA), List.of(Blocks.SAND, Blocks.RED_SAND),
            Block.box(2, 0, 2, 14, 16, 14)));

    public static final Block PARLOUR_PALM = register("parlour_palm", new VerdantPlantBlock(
            properties("parlour_palm", MONSTERA), List.of(Blocks.SAND),
            Block.box(2, 0, 2, 14, 22, 14)));

    public static final Block MONEY_TREE = register("money_tree", new VerdantPlantBlock(
            properties("money_tree", MONSTERA), List.of(Blocks.MUD),
            Block.box(3, 0, 3, 13, 10, 13)));

    public static final Block LOBELIA = register("lobelia", new VerdantPlantBlock(
            properties("lobelia", MONSTERA), List.of(), Block.box(3, 0, 3, 13, 14, 13)));

    public static final Block TALL_DRAGON_TREE = register("tall_dragon_tree",
            new VerdantTallPlantBlock(
                    properties("tall_dragon_tree", MONSTERA)
                            .sound(SoundType.GRASS)
                            .strength(0.2F),
                    List.of(Blocks.SAND), Block.box(2, 0, 3, 13, 16, 13)));

    public static final Block DRAGON_TREE = register("dragon_tree", new VerdantGrowablePlantBlock(
            properties("dragon_tree", TALL_DRAGON_TREE), List.of(Blocks.SAND),
            Block.box(2, 0, 3, 13, 23, 13), (VerdantTallPlantBlock) TALL_DRAGON_TREE));

    public static final Block CANDY_TUFT = register("candy_tuft", new VerdantPetalPlantBlock(
            properties("candy_tuft", Blocks.PINK_PETALS), List.of(Blocks.GRAVEL, Blocks.COARSE_DIRT),
            Block.box(0, 0, 0, 16, 3, 16)));

    public static final Block PERIWINKLE = register("periwinkle", new VerdantPetalPlantBlock(
            properties("periwinkle", Blocks.PINK_PETALS), List.of(),
            Block.box(0, 0, 0, 16, 3, 16)));

    public static final Block IVY = register("ivy",
            new VerdantVineBlock(properties("ivy", Blocks.VINE)));

    public static final Block CATTAILS = register("cattails", new VerdantTallWaterPlantBlock(
            properties("cattails", Blocks.TALL_GRASS).offsetType(BlockBehaviour.OffsetType.XYZ),
            List.of(Blocks.MUD, Blocks.GRAVEL, Blocks.COARSE_DIRT, Blocks.SAND),
            Block.box(2, 0, 3, 13, 16, 13)));

    public static final Block BURNWEED = register("burnweed", new BurnweedBlock(
            properties("burnweed", Blocks.TALL_GRASS).offsetType(BlockBehaviour.OffsetType.XYZ),
            List.of(), Block.box(2, 0, 2, 14, 16, 14)));

    public static final Block BRACKET_MUSHROOM = register("bracket_mushroom",
            new BracketMushroomBlock(properties("bracket_mushroom", BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BROWN)
                    .noCollision()
                    .instabreak()
                    .sound(SoundType.GRASS))));

    public static final Block POTTED_MONSTERA = registerPotted("potted_monstera", MONSTERA);
    public static final Block POTTED_SNAKE_PLANT = registerPotted("potted_snake_plant", SNAKE_PLANT);
    public static final Block POTTED_PARLOUR_PALM = registerPotted("potted_parlour_palm", PARLOUR_PALM);
    public static final Block POTTED_MONEY_TREE = registerPotted("potted_money_tree", MONEY_TREE);
    public static final Block POTTED_DRAGON_TREE = registerPotted("potted_dragon_tree", DRAGON_TREE);
    public static final Block POTTED_LOBELIA = registerPotted("potted_lobelia", LOBELIA);
    public static final Block POTTED_IVY = registerPotted("potted_ivy", IVY);
    public static final Block POTTED_BURNWEED = registerPotted("potted_burnweed", BURNWEED);

    private static boolean registered;

    private ModBlocks() {
    }

    public static void register() {
        if (registered) {
            return;
        }
        registerBlock("gardening_table", GARDENING_TABLE);
        registerBlock("monstera", MONSTERA);
        registerBlock("snake_plant", SNAKE_PLANT);
        registerBlock("parlour_palm", PARLOUR_PALM);
        registerBlock("money_tree", MONEY_TREE);
        registerBlock("lobelia", LOBELIA);
        registerBlock("tall_dragon_tree", TALL_DRAGON_TREE);
        registerBlock("dragon_tree", DRAGON_TREE);
        registerBlock("candy_tuft", CANDY_TUFT);
        registerBlock("periwinkle", PERIWINKLE);
        registerBlock("ivy", IVY);
        registerBlock("cattails", CATTAILS);
        registerBlock("burnweed", BURNWEED);
        registerBlock("bracket_mushroom", BRACKET_MUSHROOM);
        registerBlock("potted_monstera", POTTED_MONSTERA);
        registerBlock("potted_snake_plant", POTTED_SNAKE_PLANT);
        registerBlock("potted_parlour_palm", POTTED_PARLOUR_PALM);
        registerBlock("potted_money_tree", POTTED_MONEY_TREE);
        registerBlock("potted_dragon_tree", POTTED_DRAGON_TREE);
        registerBlock("potted_lobelia", POTTED_LOBELIA);
        registerBlock("potted_ivy", POTTED_IVY);
        registerBlock("potted_burnweed", POTTED_BURNWEED);

        registerItem("gardening_table", GARDENING_TABLE);
        registerInfoItem("monstera", MONSTERA);
        registerInfoItem("snake_plant", SNAKE_PLANT);
        registerInfoItem("parlour_palm", PARLOUR_PALM);
        registerInfoItem("money_tree", MONEY_TREE);
        registerInfoItem("lobelia", LOBELIA);
        registerInfoItem("dragon_tree", DRAGON_TREE);
        registerInfoItem("candy_tuft", CANDY_TUFT);
        registerInfoItem("periwinkle", PERIWINKLE);
        registerInfoItem("ivy", IVY);
        registerInfoItem("cattails", CATTAILS);
        registerInfoItem("burnweed", BURNWEED);
        registerInfoItem("bracket_mushroom", BRACKET_MUSHROOM);
        registerItem("potted_monstera", POTTED_MONSTERA);
        registerItem("potted_snake_plant", POTTED_SNAKE_PLANT);
        registerItem("potted_parlour_palm", POTTED_PARLOUR_PALM);
        registerItem("potted_money_tree", POTTED_MONEY_TREE);
        registerItem("potted_dragon_tree", POTTED_DRAGON_TREE);
        registerItem("potted_lobelia", POTTED_LOBELIA);
        registerItem("potted_ivy", POTTED_IVY);
        registerItem("potted_burnweed", POTTED_BURNWEED);
        registered = true;
    }

    private static Block register(String name, Block block) {
        return block;
    }

    private static Block registerPotted(String name, Block content) {
        return new TurnableFlowerPotBlock(
                content,
                properties(name, Blocks.POTTED_CORNFLOWER)
        );
    }

    private static BlockBehaviour.Properties properties(String name, BlockBehaviour.Properties properties) {
        return properties.setId(ResourceKey.create(Registries.BLOCK, VerdantVibes.id(name)));
    }

    private static BlockBehaviour.Properties properties(String name, Block copy) {
        return properties(name, BlockBehaviour.Properties.ofFullCopy(copy));
    }

    private static void registerBlock(String name, Block block) {
        Registry.register(BuiltInRegistries.BLOCK, VerdantVibes.id(name), block);
    }

    private static void registerItem(String name, Block block) {
        Registry.register(
                BuiltInRegistries.ITEM,
                VerdantVibes.id(name),
                new BlockItem(block, new BlockItem.Properties()
                        .useBlockDescriptionPrefix()
                        .setId(ResourceKey.create(Registries.ITEM, VerdantVibes.id(name))))
        );
    }

    private static void registerInfoItem(String name, Block block) {
        Registry.register(
                BuiltInRegistries.ITEM,
                VerdantVibes.id(name),
                new VerdantBlockItem(name, block, new Item.Properties()
                        .useBlockDescriptionPrefix()
                        .setId(ResourceKey.create(Registries.ITEM, VerdantVibes.id(name))))
        );
    }
}
