package net.forixaim.mana_arts.world.capabilities;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.forixaim.mana_arts.api.data.Element;
import net.forixaim.mana_arts.api.data.Spell;
import net.forixaim.mana_arts.api.data.SpellContainer;
import net.forixaim.mana_arts.registry.entries.ManaArtsAttributes;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.attachment.IAttachmentHolder;

import java.util.List;

public class  ManaEntity
{
    private double mana;
    private Holder<Element> currentElement;
    private List<SpellContainer> spells;
    private Holder<Spell> currentSpell;

    public ManaEntity(IAttachmentHolder iAttachmentHolder)
    {
    }

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

    public static final StreamCodec<ByteBuf, ManaEntity> STREAM_CODEC = ByteBufCodecs.COMPOUND_TAG.map(ManaEntity::new, ManaEntity::serialize);
}
