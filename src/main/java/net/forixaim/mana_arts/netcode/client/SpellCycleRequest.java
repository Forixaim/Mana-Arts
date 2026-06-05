package net.forixaim.mana_arts.netcode.client;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.forixaim.mana_arts.netcode.ManagedCustomPacketPayload;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public record SpellCycleRequest(CycleType cycleType) implements ManagedCustomPacketPayload
{
    public static final StreamCodec<ByteBuf, SpellCycleRequest> STREAM_CODEC = CycleType.STREAM_CODEC.map(SpellCycleRequest::new, SpellCycleRequest::cycleType);

    public enum CycleType implements StringRepresentable {
        NEXT, PREVIOUS;

        public static final Codec<CycleType> CODEC = StringRepresentable.fromEnum(CycleType::values);
        public static final StreamCodec<ByteBuf, CycleType> STREAM_CODEC = ByteBufCodecs.fromCodec(CODEC);

        @Override
        public @NotNull String getSerializedName() {
            return this.name().toLowerCase();
        }
    }
}
