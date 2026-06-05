package net.forixaim.mana_arts.api.data.spell;

import net.minecraft.resources.ResourceLocation;

public abstract class OffensiveSpell extends Spell{
    protected float baseDamage;

    public OffensiveSpell(ResourceLocation id) {
        super(id);
    }

    public float getBaseDamage() {
        return baseDamage;
    }
}
