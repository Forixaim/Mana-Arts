package net.forixaim.mana_arts.netcode.server.mana_entity;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public record SpellElementSync(CompoundTag tag, SpelLElementSyncType syncType) implements ManaEntityPacket {
    public static final StreamCodec<ByteBuf, SpellElementSync> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.COMPOUND_TAG,
                    SpellElementSync::tag,
                    SpelLElementSyncType.STREAM_CODEC,
                    SpellElementSync::syncType,
                    SpellElementSync::new
            );

    public enum SpelLElementSyncType implements StringRepresentable {
        ELEMENT, SPELL;

        public static final Codec<SpelLElementSyncType> CODEC = StringRepresentable.fromEnum(SpelLElementSyncType::values);
        public static final StreamCodec<ByteBuf, SpelLElementSyncType> STREAM_CODEC = ByteBufCodecs.fromCodec(CODEC);
        @Override
        public @NotNull String getSerializedName() {
            return this.name().toLowerCase();
        }
    }
}
