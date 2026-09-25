package me.timbas.stacksizetweaks;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.client.ClientScreenInputEvent;
import dev.architectury.networking.NetworkManager;
import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
import me.timbas.stacksizetweaks.client.mixin.AbstractContainerScreenAccessor;
import me.timbas.stacksizetweaks.network.PayLoadHandler;
import me.timbas.stacksizetweaks.network.PickUpAmountPayload;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.lwjgl.glfw.GLFW;

public class KeyHandler {

    public static final KeyMapping.Category KEY_CATEGORY = new KeyMapping.Category(Identifier.fromNamespaceAndPath(StackSizeTweaks.MOD_ID, "main"));

    public static final KeyMapping PICK_UP_AMOUNT = new KeyMapping(
            "key.stacksizetweaks.pick_up_amount",
            GLFW.GLFW_KEY_UNKNOWN,
            KEY_CATEGORY
    );

    public static final KeyMapping PICK_UP_PERCENTAGE = new KeyMapping(
            "key.stacksizetweaks.pick_up_percentage",
            GLFW.GLFW_KEY_UNKNOWN,
            KEY_CATEGORY
    );

    public static void register() {
        KeyMappingRegistry.register(PICK_UP_PERCENTAGE);
        KeyMappingRegistry.register(PICK_UP_AMOUNT);

        ClientScreenInputEvent.KEY_PRESSED_PRE.register((minecraft, screen, keyEvent) -> {
            if (!(screen instanceof AbstractContainerScreen)) return EventResult.pass();

            boolean pressedPickUpAmount = PICK_UP_AMOUNT.matches(keyEvent);
            boolean pressedPickUpPercentage = PICK_UP_PERCENTAGE.matches(keyEvent);

            if (!pressedPickUpAmount && !pressedPickUpPercentage) {
                return EventResult.pass();
            }

            handleInput(minecraft, screen, pressedPickUpAmount, pressedPickUpPercentage);
            return EventResult.interruptFalse();
        });
    }

    public static void handleInput(Minecraft minecraft, Screen screen, boolean pressedPickUpAmount, boolean pressedPickUpPercentage) {
        if (!pressedPickUpAmount && !pressedPickUpPercentage) return;

        Player player = minecraft.player;

        if (player == null) return;

        AbstractContainerMenu menu = player.containerMenu;

        if (!(screen instanceof AbstractContainerScreenAccessor accessor)) return;

        Slot slot = accessor.getTheHoveredSlot();
        if (slot == null) return;


        int slotIndex = menu.slots.indexOf(slot);

        ItemStack carried = menu.getCarried();
        ItemStack clicked = slot.getItem();

        int amount = 0;

        boolean emptySlot = clicked.isEmpty();

        if (pressedPickUpAmount) {
            amount = StackSizeTweaks.CONFIG.pickUpAmount;
        } else if (pressedPickUpPercentage) {
            ItemStack reference = emptySlot ? carried : clicked;
            amount = (int) Math.ceil((reference.getCount() * StackSizeTweaks.CONFIG.pickUpPercentage / 100f));
        }

        if (emptySlot) {

            if (carried.isEmpty()) return;

            amount = Math.min(amount, carried.getCount());
            amount = Math.min(amount, carried.getMaxStackSize());


            ItemStack placed = carried.copyWithCount(amount);

            amount = Math.min(amount, slot.getMaxStackSize(placed));
        } else {
            if (!slot.mayPickup(player)) return;
            if (!ItemStack.isSameItemSameComponents(clicked, carried) && !carried.isEmpty()) return;

            int freeAmount = carried.isEmpty() ? clicked.getMaxStackSize() : carried.getMaxStackSize() - carried.getCount();
            amount = Math.min(amount, freeAmount);
        }

        if (amount <= 0) return;

        if (screen instanceof CreativeModeInventoryScreen) {
            PayLoadHandler.handlePickUpClient(player, slotIndex, amount, menu.containerId);
        } else if (NetworkManager.canServerReceive(PickUpAmountPayload.TYPE)) {
            NetworkManager.sendToServer(new PickUpAmountPayload(slotIndex, amount, menu.containerId));
        }
    }
}
