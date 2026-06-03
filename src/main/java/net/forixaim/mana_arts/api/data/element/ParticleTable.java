package net.forixaim.mana_arts.api.data.element;

import com.google.common.collect.Maps;
import net.forixaim.mana_arts.api.data.spell.Spell;
import net.forixaim.mana_arts.world.entity.spell.SpellProjectile;
import net.minecraft.core.Holder;

import java.util.Map;

public final class ParticleTable
{
    public final Map<Holder<Spell>, ParticleEffects> effects = Maps.newHashMap();
    public final Map<? extends SpellProjectile, ProjectileRenderOverride> projectileRenderOverrides = Maps.newHashMap();

}
