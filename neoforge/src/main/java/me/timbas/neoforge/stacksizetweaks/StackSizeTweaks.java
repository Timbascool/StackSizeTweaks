package me.timbas.neoforge.stacksizetweaks;

import me.timbas.stacksizetweaks.StackSizeHelper;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent;

@Mod(me.timbas.stacksizetweaks.StackSizeTweaks.MOD_ID)
public final class StackSizeTweaks {
    public StackSizeTweaks(IEventBus modBus) {

        if (FMLEnvironment.getDist() == Dist.CLIENT)
        {
            StackSizeTweaksClient.ClientInit();
        }

        me.timbas.stacksizetweaks.StackSizeTweaks.init();

        modBus.register(this);
    }

    @SubscribeEvent
    public void ChangeAllStackSizes(ModifyDefaultComponentsEvent event) {
        for (Item item : BuiltInRegistries.ITEM) {
            event.modify(item, builder -> {
                DataComponentMap components = builder.build();
                builder.set(DataComponents.MAX_STACK_SIZE,
                        StackSizeHelper.getMaxStackSize(item, components));
            });
        }
    }
}
