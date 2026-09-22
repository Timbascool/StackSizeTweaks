package me.timbas.stacksizetweaks.network;

import me.timbas.stacksizetweaks.StackSizeTweaks;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

public record PickUpAmountPayload(int slot, int amount, int containerId) implements CustomPacketPayload {
    public static final Identifier PICK_UP_PAYLOAD_ID = Identifier.fromNamespaceAndPath(StackSizeTweaks.MOD_ID, "pick_up");

    public static final CustomPacketPayload.Type<PickUpAmountPayload> TYPE = new CustomPacketPayload.Type<>(PICK_UP_PAYLOAD_ID);

    public static final StreamCodec<RegistryFriendlyByteBuf, PickUpAmountPayload> CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.VAR_INT,
                    PickUpAmountPayload::slot,
                    ByteBufCodecs.VAR_INT,
                    PickUpAmountPayload::amount,
                    ByteBufCodecs.VAR_INT,
                    PickUpAmountPayload::containerId,
                    PickUpAmountPayload::new
            );

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
