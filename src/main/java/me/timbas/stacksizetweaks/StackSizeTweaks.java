package me.timbas.stacksizetweaks;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StackSizeTweaks implements ModInitializer {

    public static final int MAX_STACK_SIZE = Integer.MAX_VALUE;
    public static final String MOD_ID = "stacksizetweaks";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final InventoryTweaksConfig CONFIG = InventoryTweaksConfig.createAndLoad();
    @Override
    public void onInitialize() {
        LOGGER.info("Initialized Stack Size Tweaks!");
    }

}