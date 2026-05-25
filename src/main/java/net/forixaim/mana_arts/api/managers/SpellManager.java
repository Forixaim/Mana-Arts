package net.forixaim.mana_arts.api.managers;

import com.google.common.collect.Maps;
import net.forixaim.mana_arts.api.data.Spell;
import net.forixaim.mana_arts.registry.ManaArtsRegistries;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public final class SpellManager
{
    private SpellManager() {}
    private static final Map<ResourceLocation, Holder<Spell>> SPELLS = Maps.newHashMap();

    public static void load()
    {
        SPELLS.clear();
        ManaArtsRegistries.SPELLS.holders().forEach(spell -> SPELLS.put(spell.key().location(), spell));
    }

    public static Holder<Spell> getSpell(String resourceLocation)
    {
        return SPELLS.get(ResourceLocation.tryParse(resourceLocation));
    }
}
