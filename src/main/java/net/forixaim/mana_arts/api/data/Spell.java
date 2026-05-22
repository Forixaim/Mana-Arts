package net.forixaim.mana_arts.api.data;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class Spell
{
    protected ResourceLocation id;
    public String getTranslationKey()
    {
        return "element.".concat(id.getNamespace()).concat(".").concat(id.getPath());
    }


    public Component getTranslatedName()
    {
        return Component.translatable(getTranslationKey());
    }

    public ResourceLocation getIconLocation()
    {
        return ResourceLocation.fromNamespaceAndPath(id.getNamespace(), "textures/gui/mana_arts/elements/".concat(id.getPath()).concat(".png"));
    }
}
