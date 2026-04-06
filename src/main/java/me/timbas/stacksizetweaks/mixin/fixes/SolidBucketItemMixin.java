package me.timbas.stacksizetweaks.mixin.fixes;

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

    @Inject(method = "useOn", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/entity/player/Player;setItemInHand(Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/item/ItemStack;)V"),
            cancellable = true)
    private void fixStackedPowderSnow(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
        Player player = context.getPlayer();
        net.minecraft.world.item.ItemStack stack = context.getItemInHand();

        if (player != null && stack.getCount() > 0 && !player.hasInfiniteMaterials()) {
            ItemStack emptyBucket = new ItemStack(Items.BUCKET);
            if (!player.getInventory().add(emptyBucket)) {
                player.drop(emptyBucket, false);
            }

            cir.setReturnValue(InteractionResult.SUCCESS);
        }
    }
}
