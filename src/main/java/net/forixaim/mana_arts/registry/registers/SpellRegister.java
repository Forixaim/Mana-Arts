package net.forixaim.mana_arts.registry.registers;

import net.forixaim.mana_arts.api.data.Element;
import net.forixaim.mana_arts.api.data.Spell;
import net.forixaim.mana_arts.registry.ManaArtsRegistries;
import net.forixaim.mana_arts.registry.registers.holders.DeferredElement;
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
        return (SpellRegister) DeferredRegister.create(ManaArtsRegistries.SPELLS, modId);
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
        ;
        return (DeferredSpell<T>) this.register(id, builder);
    }
}
