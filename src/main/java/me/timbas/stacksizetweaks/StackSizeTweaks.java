package me.timbas.stacksizetweaks;

import net.fabricmc.api.ModInitializer;
<<<<<<< Updated upstream
<<<<<<< Updated upstream
import net.minecraft.world.item.Items;
=======
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
>>>>>>> Stashed changes
=======
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
>>>>>>> Stashed changes
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.timbas.stacksizetweaks.StackSizeTweaksConfig;

import java.util.Collection;
import java.util.List;

public class StackSizeTweaks implements ModInitializer {

    public static final int MAX_STACK_SIZE = Integer.MAX_VALUE;
    public static final String MOD_ID = "stacksizetweaks";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


    public static final StackSizeTweaksConfig CONFIG = StackSizeTweaksConfig.createAndLoad();


    @Override
    public void onInitialize() {
        LOGGER.info("Initialized Stack Size Tweaks!");

<<<<<<< Updated upstream
        StackSizeHelper.overridesMap = StackSizeHelper.mapFromOverrides(CONFIG.overrides());
=======
        StackSizeHelper.mapFromOverrides(CONFIG.overrides());
        StackSizeHelper.ChangeAllStackSizes();
<<<<<<< Updated upstream
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes

        CONFIG.optionForKey(CONFIG.keys.overrides).observe(value -> {
                    StackSizeHelper.overridesMap = StackSizeHelper.mapFromOverrides((List<String>) value);
                    StackSizeHelper.ChangeAllStackSizes();
                }
        );

    }


}