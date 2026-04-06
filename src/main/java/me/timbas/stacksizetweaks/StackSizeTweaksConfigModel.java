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

    @SectionHeader("Categories")
    @RestartRequired
    public int itemStackLimit = 512;
    @RestartRequired
    public int blockStackLimit = 256;
    @RestartRequired
    public int foodStackLimit = 128;
    @RestartRequired
    public int lowStackLimit = 64;
    @RestartRequired
    public int stewStackLimit = 32;
    @RestartRequired
    public int potionStackLimit = 16;
    @RestartRequired
    public int bucketStackSize = 16;
    @RestartRequired
    public int discStackSize = 16;
    @RestartRequired
    public int enchantedBookStackLimit = 16;
    @RestartRequired
    public int nonStackableStackLimit = 1;

    @SectionHeader("Overrides")
    @Expanded
    @RestartRequired
    public List<String> overrides = new ArrayList<>(List.of(
            "minecraft:example_item=64"
    ));
}
