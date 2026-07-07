package me.timbas.stacksizetweaks.mixin.fixes;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.timbas.stacksizetweaks.StackSizeTweaks;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractContainerMenu.class)
public class AbstractContainerMenuMixin {
    @WrapOperation(
            method = "getRedstoneSignalFromContainer",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/Container;getMaxStackSize(Lnet/minecraft/world/item/ItemStack;)I")
    )
    private static int modifyMaxStackSize(Container container, ItemStack itemStack, Operation<Integer> original)
    {
        if (StackSizeTweaks.CONFIG.vanillaComparatorAmounts) return Math.min(64, itemStack.getMaxStackSize());

        return original.call(container, itemStack);
    }
}
