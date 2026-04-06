package me.timbas.stacksizetweaks;

import io.wispforest.owo.config.Option;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

import me.timbas.stacksizetweaks.StackSizeTweaksConfig;


public class StackSizeTweaks implements ModInitializer {

    public static final int MAX_STACK_SIZE = Integer.MAX_VALUE;
    public static final String MOD_ID = "stacksizetweaks";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


    public static final StackSizeTweaksConfig CONFIG = StackSizeTweaksConfig.createAndLoad();


    @Override
    public void onInitialize() {
        LOGGER.info("Initialized Stack Size Tweaks!");

        StackSizeHelper.overridesMap = StackSizeHelper.mapFromOverrides(CONFIG.overrides());

        StackSizeHelper.mapFromOverrides(CONFIG.overrides());
        StackSizeHelper.ChangeAllStackSizes();

        Option<List<String>> option = CONFIG.optionForKey(CONFIG.keys.overrides);

        if (option != null) {
            option.observe(value -> {
                        StackSizeHelper.overridesMap = StackSizeHelper.mapFromOverrides(value);
                        StackSizeHelper.ChangeAllStackSizes();
                    }
            );
        }

    }


}