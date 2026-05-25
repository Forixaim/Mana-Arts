package net.forixaim.mana_arts.api.data;

import net.forixaim.mana_arts.api.data.serializers.SerializerHelper;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class SpellContainer
{
    private Holder<Spell> spell;
    private Map<ResourceLocation, Double> modifiers;

    public SpellContainer() {}

    public boolean hasSpell()
    {
        return spell != null;
    }

    public boolean hasSpell(Holder<Spell> spell)
    {
        return this.spell == spell;
    }

    public Holder<Spell> getSpell()
    {
        if (hasSpell())
        {
            return spell;
        }
        return null;
    }


    public void setSpell(Holder<Spell> spell)
    {

    }

    public CompoundTag serialize()
    {
        CompoundTag result = new CompoundTag();
        result.putString("spell", spell.getRegisteredName());
        result.put("modifiers", SerializerHelper.serializeModifiers(modifiers));
        return result;
    }

    public static SpellContainer deserialize(CompoundTag tag)
    {

    }
}
