package me.timbas.stacksizetweaks.mixin.fixes;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(HopperBlockEntity.class)
public abstract class HopperBlockEntityMixin {

    @WrapOperation(
            method = "isFullContainer",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;getMaxStackSize()I"
            )
    )
    private static int modifyIsFullContainerMaxStackSize(ItemStack itemStack, Operation<Integer> original, @Local(argsOnly = true, ordinal = 0) Container container) {
        return container.getMaxStackSize(itemStack);
    }

    @WrapOperation(
            method = "inventoryFull",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;getMaxStackSize()I"
            )
    )
    private int modifyInventoryFullMaxStackSize(ItemStack itemStack, Operation<Integer> original) {
        return ((Container) (Object) this).getMaxStackSize(itemStack);
    }

    @WrapOperation(
            method = "tryMoveInItem",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;getMaxStackSize()I"
            )
    )
    private static int modifyTryMoveInSpace(ItemStack itemStack, Operation<Integer> original, @Local(argsOnly = true, ordinal = 1) Container container) {
        return container.getMaxStackSize(itemStack);
    }

    @WrapOperation(
            method = "tryMoveInItem",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/Container;setItem(ILnet/minecraft/world/item/ItemStack;)V"
            )
    )
    private static void wrapSetItemEmptySlot(Container container, int slot, ItemStack stack, Operation<Void> original) {
        int max = container.getMaxStackSize(stack);
        int toTransfer = Math.min(stack.getCount(), max);
        original.call(container, slot, stack.split(toTransfer));
    }

    @ModifyExpressionValue(
            method = "tryMoveInItem",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/world/item/ItemStack;EMPTY:Lnet/minecraft/world/item/ItemStack;",
                    ordinal = 0
            )
    )
    private static ItemStack returnRemainderIfPresent(ItemStack original, @Local(argsOnly = true, ordinal = 0) ItemStack itemStack) {
        return itemStack;
    }
}
