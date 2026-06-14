package net.forixaim.mana_arts.api.data.internal;

import io.netty.buffer.ByteBuf;
import net.forixaim.mana_arts.api.data.element.Element;
import net.forixaim.mana_arts.api.data.serializers.SerializerHelper;
import net.forixaim.mana_arts.api.data.spell.OffensiveSpell;
import net.forixaim.mana_arts.api.data.spell.Spell;
import net.forixaim.mana_arts.api.data.spell.SpellModifier;
import net.forixaim.mana_arts.api.managers.ElementManager;
import net.forixaim.mana_arts.api.managers.SpellManager;
import net.forixaim.mana_arts.world.entity.spell.SpellProjectile;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

/**
 * A serializable class that holds the context of a spell cast.
 */
public record CastContext(Holder<Spell> spell, Holder<Element> element, Map<Holder<SpellModifier<? extends SpellProjectile>>, Double> modifiers)
{
    public static final StreamCodec<ByteBuf, CastContext> STREAM_CODEC =
            ByteBufCodecs.COMPOUND_TAG.map(CastContext::deserialize, CastContext::serialize);

    public CompoundTag serialize()
    {
        CompoundTag result = new CompoundTag();
        result.putString("spell", spell.toString());
        result.putString("element", element.toString());
        result.put("modifiers", SerializerHelper.serializeModifiers(modifiers));
        return result;
    }

    public float calculateDamage()
    {

        if (spell instanceof OffensiveSpell offensiveSpell)
        {
            return (float) (offensiveSpell.getBaseDamage() * element.value().damageModifier());
        }
        return 0;
    }

    public static CastContext deserialize(CompoundTag tag)
    {
        return new CastContext(SpellManager.getSpell(ResourceLocation.tryParse(tag.getString("spell"))), ElementManager.getElement(ResourceLocation.tryParse(tag.getString("element"))), SerializerHelper.deserializeModifiers(tag));
    }
}
