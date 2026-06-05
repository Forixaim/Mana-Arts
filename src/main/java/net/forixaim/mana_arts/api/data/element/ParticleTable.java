package net.forixaim.mana_arts.api.data.element;

import com.google.common.collect.Maps;
import net.forixaim.mana_arts.api.data.spell.Spell;
import net.forixaim.mana_arts.world.entity.spell.SpellProjectile;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.EntityType;
import org.joml.Vector4i;

import java.util.Map;

public final class ParticleTable
{
    private final Vector4i color = new Vector4i();
    private final Map<Holder<Spell>, ParticleEffects> effects = Maps.newHashMap();
    private final Map<EntityType<? extends SpellProjectile>, ProjectileRenderOverride> projectileRenderOverrides = Maps.newHashMap();

    public Map<EntityType<? extends SpellProjectile>, ProjectileRenderOverride> getProjectileRenderOverrides() {
        return projectileRenderOverrides;
    }

    public boolean hasEffectFor(Holder<Spell> spell) {
        return this.effects.containsKey(spell);
    }

    public ParticleEffects getEffectFor(Holder<Spell> spell) {
        return this.effects.get(spell);
    }

    public Map<Holder<Spell>, ParticleEffects> getEffects() {
        return effects;
    }

    public Vector4i getColor() {
        return color;
    }
}
