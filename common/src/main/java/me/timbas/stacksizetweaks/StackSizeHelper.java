package me.timbas.stacksizetweaks;

import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class StackSizeHelper {

    public static Map<String, Integer> overridesMap = new HashMap<>();

    public static int getMaxStackSize(Item item) {

        var components = item.components();

        // Exceptions

        // Item cannot have both durability and be stackable
        if (components.has(DataComponents.MAX_DAMAGE))
        {
            return item.getDefaultMaxStackSize();
        }

        int newStackSize;

        // Handle overrides
        ResourceLocation id = BuiltInRegistries.ITEM.getKey(item);

        // var foodComponent = components.get(DataComponents.FOOD);

        var useRemainder = components.get(DataComponents.USE_REMAINDER);
        Item remainderItem = useRemainder == null ? null : useRemainder.convertInto().getItem();

        Integer maxStackSize = components.get(DataComponents.MAX_STACK_SIZE);

        if (overridesMap.containsKey(id.toString())) {
            newStackSize = overridesMap.get(id.toString());
        }
        // Handle manual overrides
        else if (item == Items.REDSTONE || item == Items.STRING) {
            newStackSize = StackSizeTweaks.CONFIG.itemStackLimit;
        }

        // Handle categories

        // Potions
        else if (components.has(DataComponents.POTION_CONTENTS) && item != Items.TIPPED_ARROW) {
            newStackSize = StackSizeTweaks.CONFIG.potionStackLimit;
        }
        // Buckets
        else if (components.has(DataComponents.BUCKET_ENTITY_DATA) || item == Items.LAVA_BUCKET || item == Items.WATER_BUCKET || item == Items.MILK_BUCKET || item == Items.POWDER_SNOW_BUCKET) {
            newStackSize = StackSizeTweaks.CONFIG.bucketStackLimit;
        }
        // Discs and goat horns
        else if (components.has(DataComponents.JUKEBOX_PLAYABLE) || item == Items.GOAT_HORN) {
            newStackSize = StackSizeTweaks.CONFIG.playableStackLimit;
        }
        // Stews
        else if (remainderItem == Items.BOWL) {
            newStackSize = StackSizeTweaks.CONFIG.stewStackLimit;
        }
        // Foods
        else if (components.has(DataComponents.FOOD)) {
            newStackSize = StackSizeTweaks.CONFIG.foodStackLimit;
        }
        // Enchanted books
        else if (item == Items.ENCHANTED_BOOK) {
            newStackSize = StackSizeTweaks.CONFIG.enchantedBookStackLimit;
        }
        // Boats and minecarts
        else if (item instanceof BoatItem || item instanceof MinecartItem)
        {
            newStackSize = StackSizeTweaks.CONFIG.vehicleStackLimit;
        }
        // Beds
        else if (item instanceof BedItem)
        {
            newStackSize = StackSizeTweaks.CONFIG.bedStackLimit;
        }
        // Banner patterns
        else if (item instanceof BannerPatternItem)
        {
            newStackSize = StackSizeTweaks.CONFIG.bannerPatternStackLimit;
        }
        // Items with a default stack size of 1
        else if (maxStackSize != null && maxStackSize.equals(1)) {
            newStackSize = StackSizeTweaks.CONFIG.nonStackableStackLimit;
        }
        // Items with a default stack size of 16
        else if (maxStackSize != null && maxStackSize.equals(16)) {
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


        // 0 in config means max possible stack size
        if (newStackSize == 0) return StackSizeTweaks.ABSOLUTE_MAX_STACK_SIZE;

        return Math.clamp(newStackSize, 1, StackSizeTweaks.ABSOLUTE_MAX_STACK_SIZE);
    }


    // Kinda unnecessary
    public static Map<String, Integer> mapFromOverrides(List<String> overrides) {

        Map<String, Integer> map = new HashMap<>();

        for (String entry : overrides) {
            String[] parts = entry.split("=", 2);
            if (parts.length < 2) continue;

            String id = parts[0];
            int value = Integer.parseInt(parts[1].trim());

            map.put(id, value);
        }

        return map;
    }
}
