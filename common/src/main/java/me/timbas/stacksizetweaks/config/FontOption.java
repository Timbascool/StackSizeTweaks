package me.timbas.stacksizetweaks.config;

import dev.isxander.yacl3.api.NameableEnum;
import net.minecraft.network.chat.Component;

public enum FontOption implements NameableEnum {
    Vanilla,
    Small,
    Tiny;


    @Override
    public Component getDisplayName() {
        return Component.translatable("config.stacksizetweaks.font_option." + name().toLowerCase());

    }
}
