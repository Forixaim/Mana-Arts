package net.forixaim.mana_arts.world.capabilities;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.forixaim.mana_arts.registry.entries.ManaArtsAttributes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

public class ManaEntity
{
    private double mana;

    public double getMana() {
        return this.mana;
    }

    public ManaEntity()
    {
        mana = 0;
    }

    public ManaEntity(CompoundTag tag)
    {
        this();
        deserialize(tag);
    }

    public void setMana(Player player, double amount) {
        double max = player.getAttributeValue(ManaArtsAttributes.MAX_MANA);
        this.mana = Mth.clamp(amount, 0.0, max);
    }

    public void modifyMana(Player player, double amount) {
        this.setMana(player, this.mana + amount);
    }

    public CompoundTag serialize()
    {
        CompoundTag result = new CompoundTag();
        result.putDouble("mana", this.mana);
        return result;
    }

    public void deserialize(CompoundTag tag)
    {
        if (tag.contains("mana"))
        {
            this.mana = tag.getDouble("mana");
        }
    }

    public static final Codec<ManaEntity> CODEC = CompoundTag.CODEC.xmap(ManaEntity::new, ManaEntity::serialize);

    public static final StreamCodec<FriendlyByteBuf, ManaEntity> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.COMPOUND_TAG, ManaEntity::serialize,
            ManaEntity::new
    );
}
