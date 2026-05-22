package net.forixaim.mana_arts.registry.registers;

import net.forixaim.mana_arts.api.data.Element;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ElementRegister extends DeferredRegister<Element>
{

    protected ElementRegister(ResourceKey<? extends Registry<Element>> registryKey, String namespace)
    {
        super(registryKey, namespace);
    }
}
