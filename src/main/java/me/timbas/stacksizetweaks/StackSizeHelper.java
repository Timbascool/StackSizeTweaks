package me.timbas.stacksizetweaks;

import me.timbas.stacksizetweaks.StackSizeTweaksConfig;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.component.UseRemainder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class StackSizeHelper {

    public static Map<String, Integer> overridesMap = new HashMap<>();

    public static int GetMaxStackSize(Item item) {


        // Handle overrides

        Identifier id = BuiltInRegistries.ITEM.getKey(item);

        if (overridesMap.containsKey(id.toString())) {
            return overridesMap.get(id.toString());
        }


        // Handle manual overrides

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

        if (item == Items.ENCHANTED_BOOK)
        {
            return StackSizeTweaks.CONFIG.enchantedBookStackLimit();
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
    public static Map<String, Integer> mapFromOverrides(List<String> overrides) {

        Map<String, Integer> map = new HashMap<>();

        for (String entry : overrides) {
            String[] parts = entry.split(":");
            if (parts.length < 3) continue;

            String id = parts[0] + ":" + parts[1];
            int value = Integer.parseInt(parts[2].trim());

            map.put(id, value);
        }

        return map;
    }
}
