package me.timbas.fabric.stacksizetweaks;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

public class StackSizeTweaksClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
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
