package me.timbas.stacksizetweaks.mixin.fixes;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.CrafterBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CrafterBlockEntity.class)
public abstract class CrafterBlockEntityMixin {

    @WrapOperation(
            method = "canPlaceItem",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;getMaxStackSize()I"
            )
    )
    private int modifyCrafterSlotMaxStackSize(ItemStack slotStack, Operation<Integer> original) {
        return ((Container) (Object) this).getMaxStackSize(slotStack);
    }
}
