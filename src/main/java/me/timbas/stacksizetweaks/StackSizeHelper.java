package me.timbas.stacksizetweaks;

import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.component.UseRemainder;


public class StackSizeHelper {
    public static int GetMaxStackSize(Item item) {


        // Handle overrides
        Identifier id = BuiltInRegistries.ITEM.getKey(item);

        int index = StackSizeTweaks.CONFIG.overrides.overrideIds().indexOf(id.toString());

        if (index != -1 && index < StackSizeTweaks.CONFIG.overrides.overrideValues().size()) {
            return StackSizeTweaks.CONFIG.overrides.overrideValues().get(index);
        }


        // Handle Manual Overrides

        if (item == Items.REDSTONE || item == Items.RESIN_CLUMP || item == Items.STRING) {
            return StackSizeTweaks.CONFIG.itemStackLimit();
        }

        // Handle categories
        if (item instanceof PotionItem) {
            return StackSizeTweaks.CONFIG.potionStackLimit();
        }

        UseRemainder useRemainder = item.components().get(DataComponents.USE_REMAINDER);
        if (useRemainder != null) {
            if (useRemainder.convertInto().getItem() == Items.BOWL) {
                return StackSizeTweaks.CONFIG.stewStackLimit();
            }
        }

        if (item.components().has(DataComponents.FOOD)) {
            return StackSizeTweaks.CONFIG.foodStackLimit();
        }

        if (item.getDefaultMaxStackSize() == 1) {
            return StackSizeTweaks.CONFIG.nonStackableStackLimit();
        }

        if (item.getDefaultMaxStackSize() == 16) {
            return StackSizeTweaks.CONFIG.lowStackLimit();
        }

        if (item instanceof BlockItem) {
            return StackSizeTweaks.CONFIG.blockStackLimit();
        }

        return StackSizeTweaks.CONFIG.itemStackLimit();
    }
}
