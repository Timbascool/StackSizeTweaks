package me.timbas.stacksizetweaks.mixin;

import me.timbas.stacksizetweaks.StackSizeTweaks;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin {
    @Shadow
    public abstract ItemStack getItem();

    @ModifyConstant(method = "merge(Lnet/minecraft/world/entity/item/ItemEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;)V",
            constant = @Constant())
    private static int increaseMergeStackLimit(int value) {
        return StackSizeTweaks.MAX_STACK_SIZE;
    }
}
