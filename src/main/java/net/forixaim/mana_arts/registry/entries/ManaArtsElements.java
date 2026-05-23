package net.forixaim.mana_arts.registry.entries;

import net.forixaim.mana_arts.ManaArts;
import net.forixaim.mana_arts.api.data.Element;
import net.forixaim.mana_arts.registry.registers.ElementRegister;
import net.forixaim.mana_arts.registry.registers.holders.DeferredElement;

public class ManaArtsElements
{
    public static final ElementRegister REGISTRY = ElementRegister.create(ManaArts.MOD_ID);

    public static final DeferredElement FIRE = REGISTRY.registerElement("fire", () -> Element)
}
