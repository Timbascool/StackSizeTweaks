package me.timbas.stacksizetweaks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;


public final class StackSizeTweaks {
    public static final String MOD_ID = "stacksizetweaks";
    public static final int ABSOLUTE_MAX_STACK_SIZE = 2100000000;
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static StackSizeTweaksConfig CONFIG;

    public static void init() {

        StackSizeTweaksConfig.HANDLER.load();
        CONFIG = StackSizeTweaksConfig.HANDLER.instance();
        StackSizeHelper.overridesMap = StackSizeHelper.mapFromOverrides(CONFIG.overrides);
    }
}
