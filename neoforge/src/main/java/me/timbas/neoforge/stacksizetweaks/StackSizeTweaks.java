package me.timbas.neoforge.stacksizetweaks;

import me.timbas.stacksizetweaks.StackSizeHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

@Mod(me.timbas.stacksizetweaks.StackSizeTweaks.MOD_ID)
public final class StackSizeTweaks {
    public StackSizeTweaks(IEventBus modBus) {

        if (FMLEnvironment.dist == Dist.CLIENT)
        {
            StackSizeTweaksClient.ClientInit();
        }

        me.timbas.stacksizetweaks.StackSizeTweaks.init();

        modBus.addListener(this::changeAllStackSizes);

        if (FMLEnvironment.dist == Dist.CLIENT)
        {
            NeoForge.EVENT_BUS.addListener(this::addCountTooltip);
        }
    }

    public void changeAllStackSizes(ModifyDefaultComponentsEvent event) {
        for (Item item : BuiltInRegistries.ITEM) {
            event.modify(item, builder ->
                    builder.set(
                            DataComponents.MAX_STACK_SIZE,
                            StackSizeHelper.getMaxStackSize(item)
                    )
            );
        }
    }

    public void addCountTooltip(ItemTooltipEvent event) {
        int count = event.getItemStack().getCount();

        if (me.timbas.stacksizetweaks.StackSizeTweaks.CONFIG.amountTooltip && count > 999)
        {
            String amount = String.format("%,d", count);
            event.getToolTip().add(1, Component.literal(amount).withStyle(ChatFormatting.GRAY));
        }
    }
}
