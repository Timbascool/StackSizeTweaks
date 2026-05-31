package me.timbas.stacksizetweaks.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.serialization.Codec;
import me.timbas.stacksizetweaks.StackSizeTweaks;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.ExtraCodecs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;


@Mixin(value = DataComponents.class, priority = 1)
public class DataComponentsMixin {

    @ModifyExpressionValue(method = "method_58570", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/util/ExtraCodecs;intRange(II)Lcom/mojang/serialization/Codec;"))
    private static Codec<Integer> increaseMaxStackSize(Codec<Integer> original) {
        return ExtraCodecs.intRange(1, StackSizeTweaks.ABSOLUTE_MAX_STACK_SIZE);
    }

    /*
    @ModifyConstant(method = "method_58570", constant = @Constant(intValue = 99), require = 0)
    private static int increaseMaxStackSize(int original) {
        return StackSizeTweaks.ABSOLUTE_MAX_STACK_SIZE;
    }
    */

}