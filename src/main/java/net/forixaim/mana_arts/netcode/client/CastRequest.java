package net.forixaim.mana_arts.netcode.client;

import io.netty.buffer.ByteBuf;
import net.forixaim.mana_arts.api.data.internal.CastContext;
import net.forixaim.mana_arts.netcode.ManagedCustomPacketPayload;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record CastRequest(CompoundTag context) implements ManagedCustomPacketPayload {
    public static final StreamCodec<ByteBuf, CastRequest> STREAM_CODEC = ByteBufCodecs.COMPOUND_TAG.map(CastRequest::new, CastRequest::serialize);

    public CompoundTag serialize() {
        return context;
    }

    public CastContext buildContext() {
        return CastContext.deserialize(context);
    }
}
