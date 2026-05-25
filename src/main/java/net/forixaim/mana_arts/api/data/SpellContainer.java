package net.forixaim.mana_arts.api.data;

import net.minecraft.core.Holder;

import java.util.Map;

public class SpellContainer
{
    private Holder<Spell> spell;
    private Map<SpellModifier, Float> modifiers;

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

    public void setSpell()
    {

    }
}
