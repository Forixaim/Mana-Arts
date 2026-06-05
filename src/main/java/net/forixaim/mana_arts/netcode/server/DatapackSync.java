package net.forixaim.mana_arts.netcode.server;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.forixaim.mana_arts.api.loaders.ElementReloadListener;
import net.forixaim.mana_arts.api.loaders.NetSyncListener;
import net.forixaim.mana_arts.api.loaders.SpellReloadListener;
import net.forixaim.mana_arts.netcode.ManagedCustomPacketPayload;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.api.utils.ByteBufCodecsExtends;

import java.util.ArrayList;
import java.util.List;

public record DatapackSync(PacketType packetType, List<CompoundTag> tags) implements ManagedCustomPacketPayload
{
    public static final StreamCodec<ByteBuf, DatapackSync> STREAM_CODEC =
            StreamCodec.composite(
                    PacketType.STREAM_CODEC,
                    DatapackSync::packetType,
                    ByteBufCodecsExtends.listOf(ByteBufCodecs.COMPOUND_TAG),
                    DatapackSync::tags,
                    DatapackSync::new
            );

    public DatapackSync(PacketType packetType) {
        this(packetType, new ArrayList<>());
    }

    public enum PacketType implements StringRepresentable
    {
        ELEMENT(ElementReloadListener.INSTANCE), SPELL(SpellReloadListener.INSTANCE);

        final NetSyncListener listener;

        PacketType(NetSyncListener listener) {
            this.listener = listener;
        }
        public NetSyncListener getListener() {
            return listener;
        }

        public static final Codec<PacketType> CODEC = StringRepresentable.fromEnum(PacketType::values);
        public static final StreamCodec<ByteBuf, PacketType> STREAM_CODEC = ByteBufCodecs.fromCodec(CODEC);

        @Override
        public @NotNull String getSerializedName() {
            return this.name().toLowerCase();
        }
    }
}
