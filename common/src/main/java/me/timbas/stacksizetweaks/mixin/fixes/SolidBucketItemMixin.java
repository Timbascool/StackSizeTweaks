package me.timbas.stacksizetweaks.mixin.fixes;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SolidBucketItem;
import net.minecraft.world.item.context.UseOnContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SolidBucketItem.class)
public class SolidBucketItemMixin {

    @WrapOperation(
            method = "useOn",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;setItemInHand(Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/item/ItemStack;)V")
    )
    private void fixStackedPowderSnow(Player player, InteractionHand interactionHand, ItemStack itemStack, Operation<Void> original) {
        ItemStack stack = player.getItemInHand(interactionHand);

        if (stack.getCount() > 1 && !player.hasInfiniteMaterials()) {
            ItemStack emptyBucket = new ItemStack(Items.BUCKET);

            if (!player.getInventory().add(emptyBucket)) {
                player.drop(emptyBucket, false);
            }
            return;
        }

        original.call(player, interactionHand, itemStack);
    }
}
