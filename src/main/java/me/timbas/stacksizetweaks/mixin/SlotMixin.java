package me.timbas.stacksizetweaks.mixin;

import me.timbas.stacksizetweaks.StackSizeTweaks;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Slot.class)
public abstract class SlotMixin {
    @Inject(method = "getMaxStackSize()I", at = @At("HEAD"), cancellable = true)
    private void increaseStackLimit(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(StackSizeTweaks.MAX_STACK_SIZE);
    }
}