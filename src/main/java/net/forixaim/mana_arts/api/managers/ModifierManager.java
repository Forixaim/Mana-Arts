package net.forixaim.mana_arts.api.managers;

import com.google.common.collect.Maps;
import net.forixaim.mana_arts.api.data.spell.SpellModifier;
import net.forixaim.mana_arts.registry.ManaArtsRegistries;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public final class ModifierManager
{
    private ModifierManager() {}

    private static final Map<ResourceLocation, Holder<SpellModifier<?>>> MODIFIERS = Maps.newHashMap();

    public static void load()
    {
        MODIFIERS.clear();
        ManaArtsRegistries.SPELL_MODIFIERS.holders().forEach(modifier -> MODIFIERS.put(modifier.key().location(), modifier));
    }

    public static Holder<SpellModifier<?>> getModifier(ResourceLocation id)
    {
        return MODIFIERS.get(id);
    }

    public static Holder<SpellModifier<?>> getModifier(String id)
    {
        return MODIFIERS.get(ResourceLocation.tryParse(id));
    }
}
