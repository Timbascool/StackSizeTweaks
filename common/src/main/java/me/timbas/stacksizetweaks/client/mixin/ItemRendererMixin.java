package me.timbas.stacksizetweaks.client.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import me.timbas.stacksizetweaks.StackSizeTweaks;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(GuiGraphics.class)
public abstract class ItemRendererMixin {

    @Shadow
    @Final
    private PoseStack pose;

    @Inject(method = "renderItemCount", at = @At("HEAD"), cancellable = true)
    private void changeItemCountText(Font font, ItemStack itemStack, int i, int j, @Nullable String string, CallbackInfo ci) {
        if (itemStack.getCount() != 1 || string != null) {
            String newText = stacksizetweaks$formatCount(itemStack.getCount(), StackSizeTweaks.CONFIG.shortenItemAmounts);
            Component renderedText = stacksizetweaks$makeText(newText, StackSizeTweaks.CONFIG.useCustomFont);

            PoseStack pose =
                    this.pose;

            pose.pushPose();
            pose.translate(0.0F, 0.0F, 200.0F);

            ((GuiGraphics) (Object) this).drawString(font, renderedText,
                    i + 17 - font.width(renderedText),
                    j + 9,
                    0xFFFFFF,
                    true);

            pose.popPose();
        }

        ci.cancel();
    }

    @Unique
    private static final ResourceLocation CUSTOM_FONT =
            ResourceLocation.fromNamespaceAndPath(StackSizeTweaks.MOD_ID, "inventory_font");

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