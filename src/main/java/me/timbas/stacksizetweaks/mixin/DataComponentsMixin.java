package me.timbas.stacksizetweaks.mixin;

import me.timbas.stacksizetweaks.StackSizeTweaks;
import net.minecraft.core.component.DataComponents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;


@Mixin(DataComponents.class)
public class DataComponentsMixin {
    @ModifyConstant(method = "method_58570", constant = @Constant(intValue = 99))
    private static int increaseMaxStackSize(int original) {
        return StackSizeTweaks.MAX_STACK_SIZE;
    }
}