package net.forixaim.mana_arts.api.managers;

import com.google.common.collect.Maps;
import net.forixaim.mana_arts.api.data.Spell;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public final class SpellManager
{
    private SpellManager() {}
    public static final Map<ResourceLocation, Spell> SPELLS = Maps.newHashMap();
}
