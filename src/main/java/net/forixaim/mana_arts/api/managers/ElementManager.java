package net.forixaim.mana_arts.api.managers;

import com.google.common.collect.Maps;
import net.forixaim.mana_arts.ManaArts;
import net.forixaim.mana_arts.api.data.Element;
import net.forixaim.mana_arts.registry.ManaArtsRegistries;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public final class ElementManager
{
    private ElementManager() {}
    private static final Map<ResourceLocation, Element.Builder> ELEMENTS = Maps.newConcurrentMap();

    public static void refresh()
    {
        ManaArtsRegistries.ELEMENTS.entrySet().forEach( element -> ELEMENTS.put(element.getKey().location(), element.getValue()));
    }

    public static Element.Builder getElement(ResourceLocation id)
    {
        return ELEMENTS.get(id);
    }

}
