package net.forixaim.mana_arts.registry.registers.holders;

import net.forixaim.mana_arts.api.data.Element;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.DeferredHolder;

public class DeferredElement extends DeferredHolder<Element.Builder, Element.Builder> {
    public DeferredElement(ResourceKey<Element.Builder> key) {
        super(key);
    }
}
