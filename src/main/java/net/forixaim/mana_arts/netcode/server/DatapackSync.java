package net.forixaim.mana_arts.netcode.server;

import io.netty.buffer.ByteBuf;
import net.forixaim.mana_arts.netcode.ManagedCustomPacketPayload;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import yesman.epicfight.api.utils.ByteBufCodecsExtends;

import java.util.ArrayList;
import java.util.List;

public record DatapackSync(PacketType packetType, List<CompoundTag> tags) implements ManagedCustomPacketPayload
{
    public static final StreamCodec<ByteBuf, DatapackSync> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecsExtends.enumCodec(DatapackSync.PacketType.class),
                    DatapackSync::packetType,
                    ByteBufCodecsExtends.listOf(ByteBufCodecs.COMPOUND_TAG),
                    DatapackSync::tags,
                    DatapackSync::new
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
