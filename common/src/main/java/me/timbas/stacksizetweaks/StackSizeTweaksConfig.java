package me.timbas.stacksizetweaks;


import com.google.gson.GsonBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.minecraft.resources.ResourceLocation;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class StackSizeTweaksConfig {


    public static final ConfigClassHandler<StackSizeTweaksConfig> HANDLER =
            ConfigClassHandler.createBuilder(StackSizeTweaksConfig.class)
                    .id(ResourceLocation.fromNamespaceAndPath(StackSizeTweaks.MOD_ID, "config"))
                    .serializer(config -> GsonConfigSerializerBuilder.create(config)
                            .setPath(Path.of(".", "config", "stacksizetweaks.json5").normalize())
                            .appendGsonBuilder(GsonBuilder::setPrettyPrinting)
                            .setJson5(true)
                            .build())
                    .build();

    @SerialEntry
    public boolean useCustomFont = true;

    @SerialEntry
    public boolean shortenItemAmounts = false;

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
    public int bucketStackSize = 16;

    @SerialEntry
    public int discStackSize = 16;

    @SerialEntry
    public int enchantedBookStackLimit = 16;

    @SerialEntry
    public int nonStackableStackLimit = 1;

    @SerialEntry
    public List<String> overrides = new ArrayList<>(List.of(
            "minecraft:example_item=64"
    ));
}