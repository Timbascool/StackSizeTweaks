package me.timbas.stacksizetweaks;

import dev.architectury.networking.NetworkManager;
import me.timbas.stacksizetweaks.config.StackSizeTweaksConfig;
import me.timbas.stacksizetweaks.network.PayLoadHandler;
import me.timbas.stacksizetweaks.network.PickUpAmountPayload;
import net.minecraft.server.level.ServerPlayer;
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
        StackSizeHelper.containerOverridesMap = StackSizeHelper.mapFromOverrides(CONFIG.containerOverrides);

        NetworkManager.registerReceiver(NetworkManager.Side.C2S,
                PickUpAmountPayload.TYPE, PickUpAmountPayload.CODEC,
                (payload, context) -> {
                    ServerPlayer player = (ServerPlayer) context.getPlayer();
                    context.queue(() -> {
                        PayLoadHandler.handlePickUpServer(player, payload.slot(), payload.amount(), payload.containerId());
                    });
                });
    }

    public static void clientInit()
    {
        KeyHandler.register();
    }

}
