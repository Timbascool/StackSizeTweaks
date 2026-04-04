package me.timbas.stacksizetweaks;

import io.wispforest.owo.config.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Modmenu(modId = StackSizeTweaks.MOD_ID)
@Config(name = StackSizeTweaks.MOD_ID,
        wrapperName = "InventoryTweaksConfig")
public class StackSizeTweaksConfigModel {

    @SectionHeader("Rendering")
    public boolean useCustomFont = true;
    public boolean shortenItemAmounts = true;

    @SectionHeader("StackLimits")
    public int itemStackLimit = 512;
    public int blockStackLimit = 256;
    public int foodStackLimit = 128;
    public int lowStackLimit = 64;
    public int stewStackLimit = 32;
    public int potionStackLimit = 16;
    public int nonStackableStackLimit = 1;

    @SectionHeader("Overrides")
    @Nest
    public Overrides overrides = new Overrides();
    public static class Overrides {
        @Expanded
        public List<String> overrideIds = new ArrayList<>(List.of(
                "minecraft:example_item"
        ));
        @Expanded
        public List<Integer> overrideValues = new ArrayList<>(List.of(
                64
        ));
    }
}
