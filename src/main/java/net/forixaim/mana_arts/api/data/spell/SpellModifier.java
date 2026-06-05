package net.forixaim.mana_arts.api.data.spell;


import net.forixaim.mana_arts.world.entity.spell.SpellProjectile;

import java.util.function.Consumer;

public record SpellModifier<T extends SpellProjectile>(double minValue, double maxValue, double defaultValue, double complexity, double complexityScaling, Consumer<T> projectileModifier)
{


    public static <E extends SpellProjectile> SpellModifier<E> createRanged(double minValue, double maxValue, double defaultValue, double complexity, double complexityScaling, Consumer<E> projectileModifier)
    {
        return new SpellModifier<>(defaultValue, minValue, maxValue, complexity, complexityScaling, spellProjectile -> {});
    }

    public static <E extends SpellProjectile>SpellModifier<E> createBoolean(Boolean defaultValue, double complexity, Consumer<E> projectileModifier)
    {
        double result = 0;
        if (defaultValue)
            result = 1;
        return new SpellModifier<>(result, 0, 1, complexity, 0, spellProjectile -> {});
    }
}
