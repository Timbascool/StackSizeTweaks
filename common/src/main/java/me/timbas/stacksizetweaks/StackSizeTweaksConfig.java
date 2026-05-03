package me.timbas.stacksizetweaks;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

import java.util.ArrayList;
import java.util.List;

@Config(name = StackSizeTweaks.MOD_ID)
public class StackSizeTweaksConfig implements ConfigData {

    @ConfigEntry.Category("rendering")
    public boolean useCustomFont = true;

    @ConfigEntry.Category("rendering")
    public boolean shortenItemAmounts = true;

    @ConfigEntry.Category("categories")
    public int itemStackLimit = 512;

    @ConfigEntry.Category("categories")
    public int blockStackLimit = 256;

    @ConfigEntry.Category("categories")
    public int foodStackLimit = 128;

    @ConfigEntry.Category("categories")
    public int lowStackLimit = 64;

    @ConfigEntry.Category("categories")
    public int stewStackLimit = 32;

    @ConfigEntry.Category("categories")
    public int potionStackLimit = 16;

    @ConfigEntry.Category("categories")
    public int bucketStackSize = 16;

    @ConfigEntry.Category("categories")
    public int discStackSize = 16;

    @ConfigEntry.Category("categories")
    public int enchantedBookStackLimit = 16;

    @ConfigEntry.Category("categories")
    public int nonStackableStackLimit = 1;

    @ConfigEntry.Category("overrides")
    public List<String> overrides = new ArrayList<>(List.of(
            "minecraft:example_item=64"
    ));
}