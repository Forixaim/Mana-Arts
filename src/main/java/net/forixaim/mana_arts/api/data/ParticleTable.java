package net.forixaim.mana_arts.api.data;

import com.google.common.collect.Maps;
import net.minecraft.core.Holder;

import java.util.Map;

public class ParticleTable
{
    public final Map<Holder<Spell>, ParticleEffects> effects = Maps.newHashMap();

    public ParticleTable() {
        effects.put(Holder.direct(Spell.DEFAULT), new ParticleEffects());
    }

    public ParticleEffects getEffect(Holder<Spell> spell)
    {
        return effects.getOrDefault(spell, effects.get(Holder.direct(Spell.DEFAULT)));
    }
}
