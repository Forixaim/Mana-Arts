package net.forixaim.mana_arts.registry.registers.holders;

import net.forixaim.mana_arts.api.data.element.Element;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.DeferredHolder;

public class DeferredElement extends DeferredHolder<Element, Element> {
    public DeferredElement(ResourceKey<Element> key) {
        super(key);
    }
}
