package net.forixaim.mana_arts.api.managers;

import com.google.common.collect.Maps;
import net.forixaim.mana_arts.api.data.Element;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public final class ElementManager
{
    private ElementManager() {}
    private static final Map<ResourceLocation, Element> ELEMENTS = Maps.newConcurrentMap();

    public static void refresh()
    {

    }

}
