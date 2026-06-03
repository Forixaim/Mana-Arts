package net.forixaim.mana_arts.registry;

import com.google.common.collect.Lists;
import net.forixaim.mana_arts.ManaArts;
import net.forixaim.mana_arts.api.data.element.Element;
import net.forixaim.mana_arts.api.data.spell.Spell;
import net.forixaim.mana_arts.registry.entries.*;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;

import java.util.List;

public class ManaArtsRegistries
{
    public static final Registry<Element> ELEMENTS = new RegistryBuilder<>(RegistryKeys.ELEMENTS).create();
    public static final Registry<Spell> SPELLS = new RegistryBuilder<>(RegistryKeys.SPELLS).create();

    public static class RegistryKeys {
        public static final ResourceKey<Registry<Element>> ELEMENTS = ResourceKey.createRegistryKey(ManaArts.identifier("elements"));
        public static final ResourceKey<Registry<Spell>> SPELLS = ResourceKey.createRegistryKey(ManaArts.identifier("spells"));
    }

    public static final List<DeferredRegister<?>> REGISTERS = Lists.newArrayList(
            ManaArtsEntities.REGISTRY,
            ManaArtsSpells.REGISTRY,
            ManaArtsElements.REGISTRY,
            ManaArtsAttributes.REGISTRY,
            ManaArtsAttachments.REGISTRY,
            ManaArtsWeaponData.REGISTRY
    );

    public static void onRegister(final NewRegistryEvent event)
    {
        event.register(ELEMENTS);
        event.register(SPELLS);
    }
}
