package me.timbas.stacksizetweaks.client.mixin;

import me.timbas.stacksizetweaks.StackSizeTweaks;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FontDescription;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(GuiGraphicsExtractor.class)
public abstract class ItemRendererMixin {

    @Inject(method = "itemCount", at = @At("HEAD"), cancellable = true)
    private void changeItemCountText(Font font, ItemStack itemStack, int x, int y, @Nullable String countText, CallbackInfo ci) {
        if (itemStack.getCount() != 1 || countText != null) {
            String newText = stacksizetweaks$formatCount(itemStack.getCount(), StackSizeTweaks.CONFIG.shortenItemAmounts);
            Component renderedText = stacksizetweaks$makeText(newText, StackSizeTweaks.CONFIG.useCustomFont);

            ((GuiGraphicsExtractor) (Object) this).text(font, renderedText, x + 17 - font.width(renderedText), y + 9, -1, true);
        }

        ci.cancel();
    }

    @Unique
    private static final FontDescription CUSTOM_FONT =
            new FontDescription.Resource(
                    Identifier.fromNamespaceAndPath(StackSizeTweaks.MOD_ID, "inventory_font")
            );

    @Unique
    private static Component stacksizetweaks$makeText(String text, boolean useCustomFont) {

        if (useCustomFont) {
            return Component.literal(text)
                    .withStyle(style -> style.withFont(CUSTOM_FONT));
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