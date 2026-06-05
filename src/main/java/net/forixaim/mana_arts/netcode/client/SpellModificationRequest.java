package net.forixaim.mana_arts.netcode.client;

import io.netty.buffer.ByteBuf;
import net.forixaim.mana_arts.netcode.ManagedCustomPacketPayload;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record SpellModificationRequest(CompoundTag context) implements ManagedCustomPacketPayload
{
    public static final StreamCodec<ByteBuf, SpellModificationRequest> STREAM_CODEC = ByteBufCodecs.COMPOUND_TAG.map(SpellModificationRequest::new, SpellModificationRequest::context);
}
