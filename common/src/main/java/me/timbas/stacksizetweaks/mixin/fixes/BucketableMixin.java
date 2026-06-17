package me.timbas.stacksizetweaks.mixin.fixes;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Bucketable.class)
public interface BucketableMixin {

    @WrapOperation(
            method = "bucketMobPickup",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemUtils;createFilledResult(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;Z)Lnet/minecraft/world/item/ItemStack;"
            )
    )
    private static ItemStack fixBucketDesync(
            ItemStack itemStack, Player player, ItemStack newItemStack, boolean limitCreativeStackSize, Operation<ItemStack> original
    ) {
        if (player.level().isClientSide()) {
            return itemStack;
        }
        return original.call(itemStack, player,newItemStack, limitCreativeStackSize);
    }
}
