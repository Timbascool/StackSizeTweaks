package me.timbas.stacksizetweaks.mixin.fixes;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.inventory.FurnaceFuelSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FurnaceFuelSlot.class)
public class FurnaceFuelSlotMixin {
    @WrapOperation(
            method = "getMaxStackSize",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/inventory/FurnaceFuelSlot;isBucket(Lnet/minecraft/world/item/ItemStack;)Z")
    )
    private boolean addLavaBucket(ItemStack itemStack, Operation<Boolean> original)
    {
        return itemStack.is(Items.BUCKET) || itemStack.is(Items.LAVA_BUCKET);
    }
}
