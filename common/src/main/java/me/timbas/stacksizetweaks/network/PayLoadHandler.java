package me.timbas.stacksizetweaks.network;

import me.timbas.stacksizetweaks.StackSizeTweaks;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.Optional;

public class PayLoadHandler {

    public static void handlePickUpServer(ServerPlayer player, int slotIndex, int amount, int containerId)
    {
        AbstractContainerMenu menu = player.containerMenu;

        if (containerId != menu.containerId) return;
        if (amount <= 0 || amount > StackSizeTweaks.ABSOLUTE_MAX_STACK_SIZE) return;

        if (!menu.isValidSlotIndex(slotIndex)) return;
        Slot slot = menu.getSlot(slotIndex);

        ItemStack carried = menu.getCarried();
        ItemStack clicked = slot.getItem();

        // If empty slot, leave desired amount, else pick up desired amount
        boolean emptySlot = clicked.isEmpty();
        if (emptySlot)
        {
            if (carried.isEmpty()) return;

            amount = Math.min(amount, carried.getCount());
            amount = Math.min(amount, carried.getMaxStackSize());


            ItemStack placed = carried.copyWithCount(amount);

            amount = Math.min(amount, slot.getMaxStackSize(placed));
            if (amount <= 0) return;

            placed.setCount(amount);

            if (!slot.mayPlace(placed)) return;

            slot.set(placed);
            carried.shrink(amount);
        }
        else
        {
            if (!slot.mayPickup(player)) return;
            if (!ItemStack.isSameItemSameComponents(clicked, carried) && !carried.isEmpty()) return;

            int freeAmount = carried.isEmpty() ? clicked.getMaxStackSize() : carried.getMaxStackSize() - carried.getCount();
            amount = Math.min(amount, freeAmount);
            if (amount <= 0) return;

            if (player.isCreative())
            {
                ItemStack sourceStack = slot.getItem();
                ItemStack removed = sourceStack.copyWithCount(Math.min(amount, sourceStack.getCount()));

                sourceStack.shrink(removed.getCount());

                if (carried.isEmpty()) {
                    menu.setCarried(removed);
                } else {
                    carried.grow(removed.getCount());
                }

                slot.onTake(player, removed);
            }
            else
            {
                Optional<ItemStack> removed = slot.tryRemove(amount, amount, player);

                removed.ifPresent(itemStack -> {
                    if (carried.isEmpty()) {
                        menu.setCarried(itemStack);
                    }
                    else {
                        carried.grow(itemStack.getCount());
                    }

                    slot.onTake(player, itemStack);
                });
            }
        }

        slot.setChanged();
        menu.broadcastFullState();
        player.inventoryMenu.broadcastFullState();
    }
}
