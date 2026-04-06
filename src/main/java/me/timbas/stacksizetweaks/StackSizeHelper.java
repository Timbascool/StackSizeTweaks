package me.timbas.stacksizetweaks;

import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
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
        if (item.components().has(DataComponents.POTION_CONTENTS))
        {
            return StackSizeTweaks.CONFIG.potionStackLimit();
        }

        if (item.components().has(DataComponents.BUCKET_ENTITY_DATA) || item == Items.LAVA_BUCKET || item == Items.WATER_BUCKET || item == Items.MILK_BUCKET || item == Items.POWDER_SNOW_BUCKET)
        {
            return StackSizeTweaks.CONFIG.bucketStackSize();
        }

        if (item.components().has(DataComponents.JUKEBOX_PLAYABLE))
        {
            return StackSizeTweaks.CONFIG.discStackSize();
        }

        UseRemainder useRemainder = item.components().get(DataComponents.USE_REMAINDER);
        if (useRemainder != null && useRemainder.convertInto().is(Items.BOWL))
        {
            return StackSizeTweaks.CONFIG.stewStackLimit();
        }

        if (item.components().has(DataComponents.FOOD)) {
            return StackSizeTweaks.CONFIG.foodStackLimit();
        }

        if (item == Items.ENCHANTED_BOOK) {
            return StackSizeTweaks.CONFIG.enchantedBookStackLimit();
        }

        Integer maxStackSize = item.components().get(DataComponents.MAX_STACK_SIZE);
        if (maxStackSize != null && maxStackSize == 1) {
            return StackSizeTweaks.CONFIG.nonStackableStackLimit();
        }

        maxStackSize = item.components().get(DataComponents.MAX_STACK_SIZE);
        if (maxStackSize != null && maxStackSize == 16) {
            return StackSizeTweaks.CONFIG.lowStackLimit();
        }

        if (item instanceof BlockItem) {
            return StackSizeTweaks.CONFIG.blockStackLimit();
        }

        return StackSizeTweaks.CONFIG.itemStackLimit();
    }


    // Kinda unnecessary after switching to default item components
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


    public static void ChangeAllStackSizes() {
        DefaultItemComponentEvents.MODIFY.register(modifyContext -> {
            modifyContext.modify(item -> true, (builder, item) -> builder.set(DataComponents.MAX_STACK_SIZE, GetMaxStackSize(item)));
        });
    }
}
