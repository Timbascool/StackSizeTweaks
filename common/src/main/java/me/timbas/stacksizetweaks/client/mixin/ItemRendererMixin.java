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
    private static final ResourceLocation CUSTOM_FONT =
            ResourceLocation.fromNamespaceAndPath(StackSizeTweaks.MOD_ID, "inventory_font");

    @Unique
    private static String stacksizetweaks$formatCountText(String original) {
        if (original == null || !original.matches("\\d+")) {
            return original;
        }

        int count = Integer.parseInt(original);
        return stacksizetweaks$formatCount(count, StackSizeTweaks.CONFIG.shortenItemAmounts);
    }

    @WrapOperation(
            method = "renderItemDecorations(Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;IILjava/lang/String;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Font;width(Ljava/lang/String;)I")
    )
    private int modifyWidth(Font font, String text, Operation<Integer> original) {
        String formatted = stacksizetweaks$formatCountText(text);

        if (!StackSizeTweaks.CONFIG.useCustomFont) {
            return original.call(font, formatted);
        }

        Component component = Component.literal(formatted)
                .withStyle(style -> style.withFont(CUSTOM_FONT));

        return font.width(component);
    }

    @WrapOperation(
            method = "renderItemDecorations(Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;IILjava/lang/String;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Ljava/lang/String;IIIZ)I")
    )
    private int modifyDrawString(GuiGraphics instance, Font font, String text, int x, int y, int color, boolean shadow, Operation<Integer> original) {
        String formatted = stacksizetweaks$formatCountText(text);

        if (!StackSizeTweaks.CONFIG.useCustomFont) {
            return original.call(instance, font, formatted, x, y, color, shadow);
        }

        Component component = Component.literal(formatted).withStyle(style -> style.withFont(CUSTOM_FONT));
        return instance.drawString(font, component, x, y, color, shadow);
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

        double rounded = Math.round(newCount * 10.0) / 10.0;

        if (rounded >= 10.0) {
            return Math.round(rounded) + suffix;
        }
        if (rounded == (int) rounded) {
            return ((int) rounded) + suffix;
        }
        return String.format("%.1f%s", rounded, suffix);
    }
}