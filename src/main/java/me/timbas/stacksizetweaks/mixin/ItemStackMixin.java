package me.timbas.stacksizetweaks.mixin;

import me.timbas.stacksizetweaks.StackSizeHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Inject(method = "getMaxStackSize", at = @At("HEAD"), cancellable = true)
    private void increaseStackLimit(CallbackInfoReturnable<Integer> cir) {

        ItemStack stack = (ItemStack) (Object) this;
        Item item = stack.getItem();

        cir.setReturnValue(StackSizeHelper.GetMaxStackSize(item));
    }
}