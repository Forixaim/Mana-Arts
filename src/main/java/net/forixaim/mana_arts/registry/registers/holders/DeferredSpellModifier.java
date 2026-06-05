package net.forixaim.mana_arts.registry.registers.holders;

import net.forixaim.mana_arts.api.data.spell.SpellModifier;
import net.forixaim.mana_arts.world.entity.spell.SpellProjectile;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.DeferredHolder;

public class DeferredSpellModifier<T extends SpellModifier<? extends SpellProjectile>> extends DeferredHolder<SpellModifier<? extends SpellProjectile>, T> {
    public DeferredSpellModifier(ResourceKey<SpellModifier<?>> key) {
        super(key);
    }
}
