package me.timbas.stacksizetweaks.mixin.fixes;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractFurnaceBlockEntity.class)
public abstract class AbstractFurnaceBlockEntityMixin {

    @Inject(method = "canPlaceItem", at = @At("HEAD"), cancellable = true)
    private void limitLavaStackSize(int slot, ItemStack itemStack, CallbackInfoReturnable<Boolean> cir)
    {
        if (slot == 1 && itemStack.is(Items.LAVA_BUCKET))
        {
            ItemStack fuelSlot = ((AbstractFurnaceBlockEntity)(Object)this).getItem(1);
            if (!fuelSlot.isEmpty() && fuelSlot.is(Items.LAVA_BUCKET))
            {
                cir.setReturnValue(false);
                cir.cancel();
            }
        }
    }
}
