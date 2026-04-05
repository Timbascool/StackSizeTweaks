package me.timbas.stacksizetweaks.mixin;


import net.minecraft.world.Container;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AnvilMenu.class)
public class AnvilMenuMixin {
    @Redirect(
            method = "onTake",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/Container;setItem(ILnet/minecraft/world/item/ItemStack;)V",
                    ordinal = 2
            )
    )
    void fixStackedEnchantedBooksBeingDeleted(Container container, int i, ItemStack itemStack) {
        if (i == 1 && itemStack.isEmpty()) {
            ItemStack ingredient = container.getItem(1);

            if (ingredient.is(Items.ENCHANTED_BOOK) && ingredient.getCount() > 1) {
                ingredient.shrink(1);
                container.setItem(1, ingredient);
            }
        }


    }
}