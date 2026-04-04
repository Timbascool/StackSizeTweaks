package me.timbas.stacksizetweaks.mixin;


import me.timbas.stacksizetweaks.StackSizeTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(net.minecraft.world.Container.class)
public interface ContainerMixin {
    @Inject(method = "getMaxStackSize()I", at = @At("HEAD"), cancellable = true)
    private void increaseStackLimit(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(StackSizeTweaks.MAX_STACK_SIZE);
    }
}