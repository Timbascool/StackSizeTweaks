package me.timbas.stacksizetweaks.mixin;


import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import me.timbas.stacksizetweaks.StackSizeTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(net.minecraft.world.Container.class)
public interface ContainerMixin {


    @ModifyReturnValue(
            method = "getMaxStackSize()I",
            at = @At("RETURN")
    )
    private int increaseMaxStackSize(int original) {
        return StackSizeTweaks.ABSOLUTE_MAX_STACK_SIZE;
    }
}