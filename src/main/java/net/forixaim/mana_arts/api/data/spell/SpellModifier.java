package net.forixaim.mana_arts.api.data.spell;


import net.forixaim.mana_arts.world.entity.spell.SpellProjectile;

import java.util.function.Consumer;

public record SpellModifier(double minValue, double maxValue, double defaultValue, double complexity, double complexityScaling, Consumer<SpellProjectile> projectileModifier)
{
    public static SpellModifier createRanged(double minValue, double maxValue, double defaultValue, double complexity, double complexityScaling)
    {
        return new SpellModifier(defaultValue, minValue, maxValue, complexity, complexityScaling, spellProjectile -> {});
    }

    public static SpellModifier createBoolean(Boolean defaultValue, double complexity)
    {
        double result = 0;
        if (defaultValue)
            result = 1;
        return new SpellModifier(result, 0, 1, complexity, 0, spellProjectile -> {});
    }
}
