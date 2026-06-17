package me.timbas.neoforge.stacksizetweaks;


import me.timbas.stacksizetweaks.StackSizeTweaksConfigScreen;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

public final class StackSizeTweaksClient {

    public static void ClientInit()
    {
        ModLoadingContext.get().registerExtensionPoint(
                IConfigScreenFactory .class,
                () -> (client, parent) -> StackSizeTweaksConfigScreen.createScreen(parent)
        );
    }
}
