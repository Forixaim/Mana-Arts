package net.forixaim.mana_arts.world.capabilities;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.forixaim.mana_arts.ManaArts;
import net.forixaim.mana_arts.api.data.element.Element;
import net.forixaim.mana_arts.api.data.internal.SpellContainer;
import net.forixaim.mana_arts.api.managers.ElementManager;
import net.forixaim.mana_arts.registry.entries.ManaArtsAttributes;
import net.forixaim.mana_arts.registry.entries.ManaArtsElements;
import net.forixaim.mana_arts.registry.entries.ManaArtsSpells;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.attachment.IAttachmentSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ManaEntity
{
    private double mana;
    private final List<Holder<Element>> elements;
    private final List<SpellContainer> spells;
    private IAttachmentHolder original;
    private SpellContainer currentSpell;

    public SpellContainer getCurrentSpell() {
        return currentSpell;
    }

    public void setCurrentSpell(SpellContainer currentSpell) {
        this.currentSpell = currentSpell;
    }

    public ManaEntity(IAttachmentHolder iAttachmentHolder) {
        mana = 0;
        original = iAttachmentHolder;
        elements = Lists.newArrayList();
        spells = Lists.newArrayList();
        currentSpell = new SpellContainer();
    }

    public void debugInit()
    {
        currentSpell = new SpellContainer();
        currentSpell.setSpell(ManaArtsSpells.BLAST);
        currentSpell.setElement(ManaArtsElements.LIGHT);
    }

    public void init(LivingEntity original)
    {
        this.original = original;
    }

    public double getMana() {
        return this.mana;
    }

    public ManaEntity()
    {
        mana = 0;
        elements = Lists.newArrayList();
        spells = Lists.newArrayList();
        currentSpell = new SpellContainer();
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
        ManaArts.LOGGER.debug("Serializing ManaEntity {} for {} side", original, FMLEnvironment.dist.name());
        CompoundTag result = new CompoundTag();
        result.putDouble("mana", this.mana);
        result.put("currentSpell", currentSpell.serialize());
        ListTag elements = new ListTag();
        for (Holder<Element> element : this.elements)
        {
            elements.add(StringTag.valueOf(element.getRegisteredName()));
        }
        ListTag spells = new ListTag();
        for (SpellContainer spell : this.spells)
        {
            spells.add(spell.serialize());
        }
        result.put("elements", elements);
        result.put("spells", spells);
        return result;
    }

    public void deserialize(CompoundTag tag)
    {
        ManaArts.LOGGER.debug("Deserializing ManaEntity {} for {} side", original, FMLEnvironment.dist.name());
        if (tag.contains("mana", Tag.TAG_DOUBLE))
        {
            this.mana = tag.getDouble("mana");
        }
        if (tag.contains("currentSpell", Tag.TAG_COMPOUND))
        {
            this.currentSpell = SpellContainer.deserialize(tag.getCompound("currentSpell"));
        }
        if (tag.contains("elements", Tag.TAG_LIST))
        {
            ListTag elements = tag.getList("elements", Tag.TAG_STRING);
            for (int i = 0; i < elements.size(); i++)
            {
                String element = elements.getString(i);
                this.elements.add(ElementManager.getElement(ResourceLocation.parse(element)));
            }
        }
        if (tag.contains("spells", Tag.TAG_LIST))
        {
            ListTag spells = tag.getList("spells", Tag.TAG_COMPOUND);
            for (int i = 0; i < spells.size(); i++)
            {
                this.spells.add(SpellContainer.deserialize(spells.getCompound(i)));
            }
        }
    }

    public static final Codec<ManaEntity> CODEC = CompoundTag.CODEC.xmap(ManaEntity::new, ManaEntity::serialize);

    public static final StreamCodec<ByteBuf, ManaEntity> STREAM_CODEC = ByteBufCodecs.COMPOUND_TAG.map(ManaEntity::new, ManaEntity::serialize);

    public static final class ManaEntitySerializer implements IAttachmentSerializer<CompoundTag, ManaEntity> {
        @Override
        public @NotNull ManaEntity read(@NotNull IAttachmentHolder iAttachmentHolder, @NotNull CompoundTag compoundTag, HolderLookup.@NotNull Provider provider) {
            var magicData = new ManaEntity(iAttachmentHolder);
            magicData.deserialize(compoundTag);
            return magicData;
        }

        @Override
        public @Nullable CompoundTag write(ManaEntity manaEntity, HolderLookup.@NotNull Provider provider) {
            return manaEntity.serialize();
        }
    }
}
