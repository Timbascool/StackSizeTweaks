package me.timbas.stacksizetweaks.client.mixin;

import me.timbas.stacksizetweaks.StackSizeTweaks;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;


@Mixin(GuiGraphics.class)
public abstract class ItemRendererMixin {
    @Redirect(method = "renderItemDecorations(Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;IILjava/lang/String;)V",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Ljava/lang/String;IIIZ)I"))
    private int redirectDrawString(GuiGraphics instance, Font arg, String string2, int i2, int j2, int k, boolean bl, Font font, ItemStack itemStack, int i, int j, String string) {

        String newText = stacksizetweaks$formatCount(itemStack.getCount(), StackSizeTweaks.CONFIG.shortenItemAmounts);
        Component component = stacksizetweaks$makeText(newText, StackSizeTweaks.CONFIG.useCustomFont);

        return instance.drawString(arg, component, i + 19 - 2 - font.width(component), j2, k, true);
    }

    @Unique
    private static final ResourceLocation CUSTOM_FONT = ResourceLocation.fromNamespaceAndPath(StackSizeTweaks.MOD_ID, "inventory_font");

    @Unique
    private static Component stacksizetweaks$makeText(String text, boolean useCustomFont) {

        if (useCustomFont) {
            return Component.literal(text).withStyle(style -> style.withFont(CUSTOM_FONT));
        } else {
            return Component.literal(text);
        }
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
