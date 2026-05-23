package net.forixaim.mana_arts.registry.registers.holders;

import net.forixaim.mana_arts.api.data.Spell;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.DeferredHolder;

public class DeferredSpell<T extends Spell> extends DeferredHolder<Spell, T> {
    public DeferredSpell(ResourceKey<Spell> key) {
        super(key);
    }
}
