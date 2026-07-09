package me.timbas.stacksizetweaks.client.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.timbas.stacksizetweaks.StackSizeTweaks;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;


@Mixin(GuiGraphics.class)
public abstract class ItemRendererMixin {

    @Unique
    private static final ResourceLocation SMALL_FONT =
            ResourceLocation.fromNamespaceAndPath(StackSizeTweaks.MOD_ID, "small_font");

    @Unique
    private static String stacksizetweaks$formatCountText(String original) {
        if (original == null || !original.matches("\\d+")) {
            return original;
        }

        int count = Integer.parseInt(original);
        return stacksizetweaks$formatCount(count, StackSizeTweaks.CONFIG.shortenItemAmounts);
    }

    @Unique
    private static final ResourceLocation TINY_FONT =
            ResourceLocation.fromNamespaceAndPath(StackSizeTweaks.MOD_ID, "tiny_font");


    @Unique
    private static Component stacksizetweaks$makeText(String text, FontOption fontOption) {

        return switch (fontOption) {
            case Vanilla -> Component.literal(text);

            case Small -> Component.literal(text)
                    .withStyle(style -> style.withFont(SMALL_FONT));

            case Tiny -> Component.literal(text)
                    .withStyle(style -> style.withFont(TINY_FONT));

            case null -> Component.literal(text);
        };
    }

    @Unique
    private static String stacksizetweaks$formatCount(int count, boolean shortened) {

        if (count < 1_000) {
            return Integer.toString(count);
        }


        float newCount = count;
        String suffix;

        float multiplier = shortened ? 1 : 10;

        if (count < 100_000 * multiplier) {
            suffix = "K";
            newCount /= 1000;
        } else if (count < 100_000_000 * multiplier) {
            suffix = "M";
            newCount /= 1_000_000;
        } else {
            suffix = "B";
            newCount /= 1_000_000_000;
        }

        // 3.3K, 33K, 0,3M When shortened
        // 3.3K, 33K, 333K When not shortened
        // Round to 1 decimal
        double rounded = Math.round(newCount * 10.0) / 10.0;

        // Always have 2 or 3 figures
        if (rounded >= 10.0) {
            return Math.round(rounded) + suffix;
        }

        // If exact value don't show decimal
        if (rounded == (int) rounded) {
            return ((int) rounded) + suffix;
        }

        // Show 1 decimal
        return String.format("%.1f%s", rounded, suffix);
    }
}