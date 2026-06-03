package net.forixaim.mana_arts.api.managers;

import com.google.common.collect.Maps;
import net.forixaim.mana_arts.api.data.spell.SpellModifier;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public final class ModifierManager
{
    private ModifierManager() {}

    private static final Map<ResourceLocation, SpellModifier> MODIFIERS = Maps.newHashMap();

    private static void load()
    {
        MODIFIERS.clear();
    }
}
