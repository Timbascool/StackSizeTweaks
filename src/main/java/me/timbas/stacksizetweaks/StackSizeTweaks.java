package me.timbas.stacksizetweaks;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.timbas.stacksizetweaks.StackSizeTweaksConfig;

import java.util.List;

public class StackSizeTweaks implements ModInitializer {

    public static final int MAX_STACK_SIZE = Integer.MAX_VALUE;
    public static final String MOD_ID = "stacksizetweaks";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);



    public static final StackSizeTweaksConfig CONFIG = StackSizeTweaksConfig.createAndLoad();


    @Override
    public void onInitialize() {
        LOGGER.info("Initialized Stack Size Tweaks!");

        StackSizeHelper.mapFromOverrides(CONFIG.overrides());

        CONFIG.optionForKey(CONFIG.keys.overrides).observe(value ->
                StackSizeHelper.overridesMap = StackSizeHelper.mapFromOverrides((List<String>) value)
        );
    }

}