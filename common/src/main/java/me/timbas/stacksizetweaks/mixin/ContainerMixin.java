package me.timbas.stacksizetweaks.mixin;


import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import me.timbas.stacksizetweaks.StackSizeHelper;
import net.minecraft.world.Container;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Container.class)
public interface ContainerMixin {


    @ModifyReturnValue(
            method = "getMaxStackSize()I",
            at = @At("RETURN")
    )
    private int increaseMaxStackSize(int original) {
        return StackSizeHelper.getContainerMaxStackSize((Container) this);
    }
}