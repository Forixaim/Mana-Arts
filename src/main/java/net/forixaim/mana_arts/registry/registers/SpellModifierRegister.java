package net.forixaim.mana_arts.registry.registers;

import net.forixaim.mana_arts.api.data.spell.SpellModifier;
import net.forixaim.mana_arts.registry.ManaArtsRegistries;
import net.forixaim.mana_arts.registry.registers.holders.DeferredSpellModifier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;

public class SpellModifierRegister extends DeferredRegister<SpellModifier<?>> {
    protected SpellModifierRegister(ResourceKey<? extends Registry<SpellModifier<?>>> registryKey, String namespace) {
        super(registryKey, namespace);
    }

    public static SpellModifierRegister create(String modId) {
        return new SpellModifierRegister(ManaArtsRegistries.RegistryKeys.SPELL_MODIFIERS, modId);
    }

    public <T extends SpellModifier<?>> DeferredSpellModifier<T> registerModifier(String id, Function<ResourceLocation, SpellModifier<?>> builder) {
        this.register(id, builder);
        ResourceKey<SpellModifier<?>> key = ResourceKey.create(ManaArtsRegistries.RegistryKeys.SPELL_MODIFIERS, ResourceLocation.fromNamespaceAndPath(getNamespace(), id));
        return new DeferredSpellModifier<>(key);
    }
}
