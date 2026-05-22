package net.forixaim.mana_arts.netcode.server;

import io.netty.buffer.ByteBuf;
import net.forixaim.mana_arts.netcode.ManagedCustomPacketPayload;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import yesman.epicfight.api.utils.ByteBufCodecsExtends;
import yesman.epicfight.network.server.SPDatapackSync;

import java.util.ArrayList;
import java.util.List;

public record DatapackSync(PacketType packetType, List<CompoundTag> tags) implements ManagedCustomPacketPayload
{
    public static final StreamCodec<ByteBuf, yesman.epicfight.network.server.SPDatapackSync> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecsExtends.enumCodec(yesman.epicfight.network.server.SPDatapackSync.PacketType.class),
                    yesman.epicfight.network.server.SPDatapackSync::packetType,
                    ByteBufCodecsExtends.listOf(ByteBufCodecs.COMPOUND_TAG),
                    yesman.epicfight.network.server.SPDatapackSync::tags,
                    yesman.epicfight.network.server.SPDatapackSync::new
            );

    public DatapackSync(PacketType packetType) {
        this(packetType, new ArrayList<>());
    }


    public void addTag(CompoundTag compound) {
        this.tags.add(compound);
    }

    public enum PacketType
    {
        ELEMENT, SPELL
    }
}
