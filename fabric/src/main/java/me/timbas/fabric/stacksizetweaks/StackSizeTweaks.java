package me.timbas.fabric.stacksizetweaks;

import me.timbas.stacksizetweaks.StackSizeHelper;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;

public final class StackSizeTweaks implements ModInitializer {
    @Override
    public void onInitialize() {
        me.timbas.stacksizetweaks.StackSizeTweaks.init();

        DefaultItemComponentEvents.MODIFY.register(modifyContext -> {
            modifyContext.modify(item -> true, (builder, item) -> builder
                    .set(DataComponents.MAX_STACK_SIZE, StackSizeHelper.getMaxStackSize(item, item.components())));
        });

        ItemTooltipCallback.EVENT.register((stack, context, tooltipType, lines) -> {
            int count = stack.getCount();

            if (me.timbas.stacksizetweaks.StackSizeTweaks.CONFIG.amountTooltip && count > 999)
            {
                String amount = String.format("%,d", count);
                lines.add(1, Component.literal(amount).withStyle(ChatFormatting.GRAY));
            }
        });
    }
}
