package net.forixaim.mana_arts.api.data.element;

import com.google.common.collect.Maps;
import net.forixaim.mana_arts.api.data.spell.Spell;
import net.minecraft.core.Holder;

import java.util.Map;

public class ParticleTable
{
    public final Map<Holder<Spell>, ParticleEffects> effects = Maps.newHashMap();


}
