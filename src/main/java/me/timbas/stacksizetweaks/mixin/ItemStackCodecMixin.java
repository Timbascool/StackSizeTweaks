package me.timbas.stacksizetweaks.mixin;

import me.timbas.stacksizetweaks.StackSizeTweaks;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ItemStack.class)
public abstract class ItemStackCodecMixin {
    @ModifyConstant(method = "method_57371", constant = @Constant(intValue = 99))
    private static int increaseRange(int original) {
        return StackSizeTweaks.MAX_STACK_SIZE;
    }
}