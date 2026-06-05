package net.forixaim.mana_arts.netcode.client;

import io.netty.buffer.ByteBuf;
import net.forixaim.mana_arts.netcode.ManagedCustomPacketPayload;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public record SpellModificationRequest(ModifierType modifierType, CompoundTag context) implements ManagedCustomPacketPayload
{
    public static final StreamCodec<ByteBuf, SpellModificationRequest> STREAM_CODEC = StreamCodec.composite(
            ModifierType.STREAM_CODEC, SpellModificationRequest::modifierType,
            ByteBufCodecs.COMPOUND_TAG, SpellModificationRequest::context,
            SpellModificationRequest::new
    );

    public enum ModifierType implements StringRepresentable {
        ADD, MODIFY, REMOVE;

        public static final StreamCodec<ByteBuf, ModifierType> STREAM_CODEC = ByteBufCodecs.fromCodec(StringRepresentable.fromEnum(ModifierType::values));
        @Override
        public @NotNull String getSerializedName() {
            return this.name().toLowerCase();
        }
    }
}
