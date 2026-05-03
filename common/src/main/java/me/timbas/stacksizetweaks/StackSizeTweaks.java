package me.timbas.stacksizetweaks;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public final class StackSizeTweaks {
    public static final String MOD_ID = "stacksizetweaks";
    public static final int ABSOLUTE_MAX_STACK_SIZE = Integer.MAX_VALUE;
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static StackSizeTweaksConfig CONFIG;

    public static void init() {

        AutoConfig.register(StackSizeTweaksConfig.class, GsonConfigSerializer::new);
        CONFIG = AutoConfig.getConfigHolder(StackSizeTweaksConfig.class).getConfig();

        StackSizeHelper.overridesMap = StackSizeHelper.mapFromOverrides(CONFIG.overrides);

        StackSizeHelper.mapFromOverrides(CONFIG.overrides);
    }
}
