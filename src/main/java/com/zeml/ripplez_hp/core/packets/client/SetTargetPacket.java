package com.zeml.ripplez_hp.core.packets.client;

import com.zeml.ripplez_hp.core.HermitPackets;
import com.zeml.ripplez_hp.init.AddonDataAttachmentTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public class SetTargetPacket implements CustomPacketPayload {
    private final int mode;
    private final String target;

    public SetTargetPacket(int mode, String target) {
        this.mode = mode;
        this.target = target;
    }


    private static CustomPacketPayload.Type<SetTargetPacket> type;

    public static class Handler implements HermitPackets.PacketOGHandler<SetTargetPacket> {

        public Handler(ResourceLocation packetId) {
            type = new CustomPacketPayload.Type<>(packetId);
        }

        @Override
        public Type<SetTargetPacket> type() {
            return type;
        }

        @Override
        public void encode(SetTargetPacket packet, RegistryFriendlyByteBuf buf) {
            buf.writeInt(packet.mode);
            buf.writeUtf(packet.target);
        }

        @Override
        public SetTargetPacket decode(RegistryFriendlyByteBuf buf) {
            return new SetTargetPacket(buf.readInt(), buf.readUtf());
        }

        @Override
        public void handle(SetTargetPacket payload, IPayloadContext context) {
            Player player = context.player();
            player.getData(AddonDataAttachmentTypes.HERMIT_DATA).setMode(payload.mode);
            player.getData(AddonDataAttachmentTypes.HERMIT_DATA).setTarget(payload.target);
        }

    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return type;
    }

}
