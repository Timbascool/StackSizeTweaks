package me.timbas.fabric.stacksizetweaks;

import me.timbas.stacksizetweaks.StackSizeHelper;
import me.timbas.stacksizetweaks.network.PickUpAmountPayload;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.core.component.DataComponents;

public final class StackSizeTweaks implements ModInitializer {
    @Override
    public void onInitialize() {
        me.timbas.stacksizetweaks.StackSizeTweaks.init();

        DefaultItemComponentEvents.MODIFY.register(modifyContext -> {
            modifyContext.modify(item -> true, (builder, item) -> builder
                    .set(DataComponents.MAX_STACK_SIZE, StackSizeHelper.getMaxStackSize(item, item.components())));
        });
    }
}
