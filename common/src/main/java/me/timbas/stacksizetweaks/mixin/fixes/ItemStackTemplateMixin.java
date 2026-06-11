package me.timbas.stacksizetweaks.mixin.fixes;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.serialization.Codec;
import me.timbas.stacksizetweaks.StackSizeTweaks;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStackTemplate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemStackTemplate.class)
public class ItemStackTemplateMixin {

    @ModifyExpressionValue(method = "lambda$static$0", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/util/ExtraCodecs;intRange(II)Lcom/mojang/serialization/Codec;"))
    private static Codec<Integer> increaseMaxTemplateStackCount(Codec<Integer> original) {
        return ExtraCodecs.intRange(1, StackSizeTweaks.ABSOLUTE_MAX_STACK_SIZE);
    }
}