package me.timbas.stacksizetweaks;

import me.timbas.stacksizetweaks.config.StackSizeTweaksConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public final class StackSizeTweaks {
    public static final String MOD_ID = "stacksizetweaks";
    public static final int ABSOLUTE_MAX_STACK_SIZE = 1_000_000_000;
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static StackSizeTweaksConfig CONFIG;

    public static void init() {

        StackSizeTweaksConfig.HANDLER.load();
        CONFIG = StackSizeTweaksConfig.HANDLER.instance();
        StackSizeHelper.overridesMap = StackSizeHelper.mapFromOverrides(CONFIG.overrides);
    }
}
