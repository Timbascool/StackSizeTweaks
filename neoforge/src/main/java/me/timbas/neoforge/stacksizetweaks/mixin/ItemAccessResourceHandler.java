package me.timbas.neoforge.stacksizetweaks.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import me.timbas.stacksizetweaks.StackSizeTweaks;
import net.neoforged.neoforge.transfer.item.ItemAccessItemHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemAccessItemHandler.class)
public class ItemAccessResourceHandler {
    @ModifyReturnValue(
            method = "getCapacity*",
            at = @At("RETURN")
    )
    private int increaseMaxStackSize(int original, ItemResource resource) {
        return resource.isEmpty() ? StackSizeTweaks.ABSOLUTE_MAX_STACK_SIZE : Math.min(resource.getMaxStackSize(), StackSizeTweaks.ABSOLUTE_MAX_STACK_SIZE);
    }
}

