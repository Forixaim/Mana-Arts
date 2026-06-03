package net.forixaim.mana_arts.registry.registers;

import net.forixaim.mana_arts.api.data.spell.Spell;
import net.forixaim.mana_arts.registry.ManaArtsRegistries;
import net.forixaim.mana_arts.registry.registers.holders.DeferredSpell;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class SpellRegister extends DeferredRegister<Spell>
{
    public static SpellRegister create(String modId)
    {
        return new SpellRegister(ManaArtsRegistries.RegistryKeys.SPELLS, modId);
    }

    protected SpellRegister(ResourceKey<? extends Registry<Spell>> registryKey, String namespace)
    {
        super(registryKey, namespace);
    }

    public <T extends Spell> DeferredSpell<T> registerSpell(String id, Supplier<T> builder)
    {
        ResourceKey<Spell> key = ResourceKey.create(
                ManaArtsRegistries.RegistryKeys.SPELLS,
                ResourceLocation.fromNamespaceAndPath(getNamespace(), id)
        );
        this.register(id, builder);
        return new DeferredSpell<>(key);
    }
}
