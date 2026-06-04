package me.timbas.stacksizetweaks.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.serialization.Codec;
import me.timbas.stacksizetweaks.StackSizeTweaks;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemStack.class)
public abstract class ItemStackCodecMixin {

    @ModifyExpressionValue(method = "method_57371", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/util/ExtraCodecs;intRange(II)Lcom/mojang/serialization/Codec;"))
    private static Codec<Integer> increaseMaxStackSize(Codec<Integer> original) {
        return ExtraCodecs.intRange(1, StackSizeTweaks.ABSOLUTE_MAX_STACK_SIZE);
    }

    /*
    @ModifyConstant(method = "method_57371", constant = @Constant(intValue = 99))
    private static int increaseMaxStackSize(int original) {
        return StackSizeTweaks.ABSOLUTE_MAX_STACK_SIZE;
    }
     */
}