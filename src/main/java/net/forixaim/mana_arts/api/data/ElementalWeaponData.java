package net.forixaim.mana_arts.api.data;

import net.forixaim.mana_arts.ManaArts;
import net.minecraft.core.Holder;
import net.minecraft.nbt.Tag;

public class ElementalWeaponData
{
    public Holder<Element> imbuedElement;

    public void setImbuedElement(Holder<Element> element)
    {
        imbuedElement = element;
    }

    public Holder<Element> getImbuedElement()
    {
        return imbuedElement;
    }

    public static ElementalWeaponData deserialize(Tag tag)
    {
        ManaArts.LOGGER.debug("There's no deserializer mainly because this is dynamic.");
        return new ElementalWeaponData();
    }
}
