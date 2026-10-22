package com.moigferdsrte.verdantvibes.item;

import com.moigferdsrte.verdantvibes.VerdantVibes;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import org.jspecify.annotations.NonNull;

import java.util.function.Consumer;

@SuppressWarnings("deprecation")
public class VerdantBlockItem extends BlockItem {
    private final String blockName;

    public VerdantBlockItem(String blockName, Block block, Item.Properties properties) {
        super(block, properties);
        this.blockName = blockName;
    }

    @Override
    public void appendHoverText(
            @NonNull ItemStack stack,
            Item.@NonNull TooltipContext context,
            @NonNull TooltipDisplay display,
            Consumer<Component> tooltipAdder,
            @NonNull TooltipFlag flag
    ) {
        tooltipAdder.accept(Component.translatable(
                "block." + VerdantVibes.MOD_ID + "." + this.blockName + ".info"
        ).withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
        super.appendHoverText(stack, context, display, tooltipAdder, flag);
    }
}
