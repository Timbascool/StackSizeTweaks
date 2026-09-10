package me.timbas.stacksizetweaks.config;


import com.google.gson.GsonBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import me.timbas.stacksizetweaks.StackSizeTweaks;
import net.minecraft.resources.Identifier;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class StackSizeTweaksConfig {


    public static final ConfigClassHandler<StackSizeTweaksConfig> HANDLER =
            ConfigClassHandler.createBuilder(StackSizeTweaksConfig.class)
                    .id(Identifier.fromNamespaceAndPath(StackSizeTweaks.MOD_ID, "config"))
                    .serializer(config -> GsonConfigSerializerBuilder.create(config)
                            .setPath(Path.of(".", "config", "stacksizetweaks.json5").normalize())
                            .appendGsonBuilder(GsonBuilder::setPrettyPrinting)
                            .setJson5(true)
                            .build())
                    .build();

    @SerialEntry
    public boolean amountTooltip = true;

    @SerialEntry(comment = "Font options: \"Vanilla\", \"Small\", \"Tiny\"")
    public FontOption customFont = FontOption.Small;

    @SerialEntry
    public boolean shortenItemAmounts = true;

    @SerialEntry
    public boolean vanillaComparatorAmounts = false;

    @SerialEntry(comment = "Global stack limit for all redstone containers (dispensers, droppers, hoppers, crafters). 0 means default to item max stack size.")
    public int redstoneContainerStackLimit = 0;

    @SerialEntry(comment = "Stack limit for dispensers. 0 inherits from redstoneContainerStackLimit (or item max stack size).")
    public int dispenserStackLimit = 0;

    @SerialEntry(comment = "Stack limit for droppers. 0 inherits from redstoneContainerStackLimit (or item max stack size).")
    public int dropperStackLimit = 0;

    @SerialEntry(comment = "Stack limit for hoppers. 0 inherits from redstoneContainerStackLimit (or item max stack size).")
    public int hopperStackLimit = 0;

    @SerialEntry(comment = "Stack limit for crafters. 0 inherits from redstoneContainerStackLimit (or item max stack size).")
    public int crafterStackLimit = 0;

    @SerialEntry
    public int itemStackLimit = 512;

    @SerialEntry
    public int blockStackLimit = 256;

    @SerialEntry
    public int foodStackLimit = 128;

    @SerialEntry
    public int lowStackLimit = 64;

    @SerialEntry
    public int stewStackLimit = 32;

    @SerialEntry
    public int potionStackLimit = 16;

    @SerialEntry
    public int bucketStackLimit = 16;

    @SerialEntry
    public int playableStackLimit = 16;

    @SerialEntry
    public int vehicleStackLimit = 16;
    @SerialEntry
    public int bedStackLimit = 16;
    @SerialEntry
    public int bannerPatternStackLimit = 16;
    @SerialEntry
    public int enchantedBookStackLimit = 16;

    @SerialEntry
    public int nonStackableStackLimit = 1;

    @SerialEntry
    public List<String> overrides = new ArrayList<>(List.of(
            "minecraft:example_item=64"
    ));
}


