package me.timbas.stacksizetweaks.mixin.fixes;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;


// Increase stack size containers drop to avoid lag
@Mixin(Containers.class)
public class ContainersMixin {
    @ModifyExpressionValue(
            method = "dropItemStack",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/RandomSource;nextInt(I)I")
    )
    private static int modifyContainerDropAmount(int original, Level level, double x, double y, double z, ItemStack itemStack) {
        float scale = (float) itemStack.getMaxStackSize() / 64;

        return (int) (original * scale);
    }
}
