package net.forixaim.mana_arts.netcode.server.mana_entity;

import io.netty.buffer.ByteBuf;
import net.forixaim.mana_arts.netcode.ManagedCustomPacketPayload;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record CurrentSpellIndexSync(int index) implements ManagedCustomPacketPayload {
    public static final StreamCodec<ByteBuf, CurrentSpellIndexSync> STREAM_CODEC = ByteBufCodecs.INT.map(CurrentSpellIndexSync::new, CurrentSpellIndexSync::index);
}
