package me.timbas.stacksizetweaks;

import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class StackSizeHelper {

    public static Map<String, Integer> overridesMap = new HashMap<>();

    public static int getMaxStackSize(Item item) {

        // Exceptions

        // Item cannot have both durability and be stackable
        if (item.components().has(DataComponents.MAX_DAMAGE))
        {
            return item.getDefaultMaxStackSize();
        }

        int newStackSize;

        // Handle overrides
        ResourceLocation id = BuiltInRegistries.ITEM.getKey(item);

        // var foodComponent = item.components().get(DataComponents.FOOD);

        var useRemainder = item.components().get(DataComponents.USE_REMAINDER);
        ItemStack remainderItem = useRemainder == null ? ItemStack.EMPTY : useRemainder.convertInto();

        Integer maxStackSize = item.components().get(DataComponents.MAX_STACK_SIZE);

        if (overridesMap.containsKey(id.toString())) {
            newStackSize = overridesMap.get(id.toString());
        }
        // Handle manual overrides
        else if (item == Items.REDSTONE || item == Items.STRING) {
            newStackSize = StackSizeTweaks.CONFIG.itemStackLimit;
        }

        // Handle categories

        // Potions
        else if (item.components().has(DataComponents.POTION_CONTENTS)) {
            newStackSize = StackSizeTweaks.CONFIG.potionStackLimit;
        }
        // Buckets
        else if (item.components().has(DataComponents.BUCKET_ENTITY_DATA) || item == Items.LAVA_BUCKET || item == Items.WATER_BUCKET || item == Items.MILK_BUCKET || item == Items.POWDER_SNOW_BUCKET) {
            newStackSize = StackSizeTweaks.CONFIG.bucketStackSize;
        }
        // Discs
        else if (item.components().has(DataComponents.JUKEBOX_PLAYABLE)) {
            newStackSize = StackSizeTweaks.CONFIG.discStackSize;
        }
        // Stews
        else if (remainderItem.is(Items.BOWL)) {
            newStackSize = StackSizeTweaks.CONFIG.stewStackLimit;
        }
        // Foods
        else if (item.components().has(DataComponents.FOOD)) {
            newStackSize = StackSizeTweaks.CONFIG.foodStackLimit;
        }
        // Enchanted books
        else if (item == Items.ENCHANTED_BOOK) {
            newStackSize = StackSizeTweaks.CONFIG.enchantedBookStackLimit;
        }
        // Items with a default stack size of 1
        else if (maxStackSize != null && maxStackSize == 1) {
            newStackSize = StackSizeTweaks.CONFIG.nonStackableStackLimit;
        }
        // Items with a default stack size of 16
        else if (maxStackSize != null && maxStackSize == 16) {
            newStackSize = StackSizeTweaks.CONFIG.lowStackLimit;
        }
        // Blocks
        else if (item instanceof BlockItem) {
            newStackSize = StackSizeTweaks.CONFIG.blockStackLimit;
        }
        // Items
        else {
            newStackSize = StackSizeTweaks.CONFIG.itemStackLimit;
        }

        if (newStackSize == 0) return StackSizeTweaks.ABSOLUTE_MAX_STACK_SIZE;

        return Math.clamp(newStackSize, 1, StackSizeTweaks.ABSOLUTE_MAX_STACK_SIZE);
    }


    // Kinda unnecessary
    public static Map<String, Integer> mapFromOverrides(List<String> overrides) {

        Map<String, Integer> map = new HashMap<>();

        for (String entry : overrides) {
            String[] parts = entry.split("=");
            if (parts.length < 2) continue;

            String id = parts[0];
            int value = Integer.parseInt(parts[1].trim());

            map.put(id, value);
        }

        return map;
    }
}
