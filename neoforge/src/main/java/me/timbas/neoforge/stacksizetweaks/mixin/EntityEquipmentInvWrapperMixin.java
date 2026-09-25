package me.timbas.neoforge.stacksizetweaks.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import me.timbas.stacksizetweaks.StackSizeTweaks;
import net.neoforged.neoforge.items.wrapper.EntityEquipmentInvWrapper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityEquipmentInvWrapper.class)
public class EntityEquipmentInvWrapperMixin {
    @ModifyReturnValue(
            method = "getSlotLimit",
            at = @At("RETURN")
    )
    private int increaseMaxStackSize(int original) {
        return original == 99 ? StackSizeTweaks.ABSOLUTE_MAX_STACK_SIZE : original;
    }
}
