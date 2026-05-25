package net.forixaim.mana_arts.api.managers;

import com.google.common.collect.Maps;
import net.forixaim.mana_arts.ManaArts;
import net.forixaim.mana_arts.api.data.Element;
import net.forixaim.mana_arts.registry.ManaArtsRegistries;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public final class ElementManager
{
    private ElementManager() {}
    private static final Map<ResourceLocation, Holder<Element.Builder>> ELEMENTS = Maps.newConcurrentMap();

    public static void load()
    {
        ELEMENTS.clear();
        ManaArtsRegistries.ELEMENTS.holders().forEach(element -> ELEMENTS.put(element.key().location(), element));
    }

    public static Holder<Element.Builder> getElement(ResourceLocation id)
    {
        return ELEMENTS.get(id);
    }

}
