package net.forixaim.mana_arts.netcode.server.mana_entity;

import io.netty.buffer.ByteBuf;
import net.forixaim.mana_arts.netcode.ManagedCustomPacketPayload;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record LearnedDataSync(CompoundTag data) implements ManagedCustomPacketPayload {
    public static final StreamCodec<ByteBuf, LearnedDataSync> STREAM_CODEC = ByteBufCodecs.COMPOUND_TAG.map(LearnedDataSync::new, LearnedDataSync::data);

}
