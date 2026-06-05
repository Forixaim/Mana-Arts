package net.forixaim.mana_arts.netcode.server.mana_entity;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record ManaValueSync(Double tag) implements ManaEntityPacket
{
    public static final StreamCodec<ByteBuf, ManaValueSync> STREAM_CODEC = ByteBufCodecs.DOUBLE.map(ManaValueSync::new, ManaValueSync::tag);
}
