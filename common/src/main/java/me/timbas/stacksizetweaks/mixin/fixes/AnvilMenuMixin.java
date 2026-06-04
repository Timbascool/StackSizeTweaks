package me.timbas.stacksizetweaks.mixin.fixes;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// Make anvils only consume one enchanted book from stack.
// Doesn't apply if in first input slot but xp cost is too high in survival anyway
@Mixin(AnvilMenu.class)
public class AnvilMenuMixin {

    @WrapOperation(
            method = "onTake",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/Container;setItem(ILnet/minecraft/world/item/ItemStack;)V"
            )
    )
    private void fixStackedEnchants(
            Container container,
            int slot,
            ItemStack stack,
            Operation<Void> original
    ) {
        if (slot == 1 && stack.isEmpty()) {
            ItemStack ingredient = container.getItem(1);

            if (ingredient.is(Items.ENCHANTED_BOOK) && ingredient.getCount() > 1) {
                ingredient.shrink(1);
                original.call(container, 1, ingredient);
                return;
            }
        }

        original.call(container, slot, stack);
    }
}