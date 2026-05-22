package net.forixaim.mana_arts.registry.registers;

import net.forixaim.mana_arts.api.data.Spell;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SpellRegister extends DeferredRegister<Spell>
{
    protected SpellRegister(ResourceKey<? extends Registry<Spell>> registryKey, String namespace)
    {
        super(registryKey, namespace);
    }
}
