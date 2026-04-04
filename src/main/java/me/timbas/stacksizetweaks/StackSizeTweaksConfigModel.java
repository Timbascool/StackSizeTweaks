package me.timbas.stacksizetweaks;

import io.wispforest.owo.config.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Modmenu(modId = StackSizeTweaks.MOD_ID)
@Config(name = StackSizeTweaks.MOD_ID,
        wrapperName = "StackSizeTweaksConfig")
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
    public int enchantedBookStackLimit = 16;
    public int nonStackableStackLimit = 1;

    @SectionHeader("Overrides")
    @Expanded
    public List<String> overrides = new ArrayList<>(List.of(
            "minecraft:enchanted_book: 16",
            "minecraft:example_item: 64"
    ));
}
