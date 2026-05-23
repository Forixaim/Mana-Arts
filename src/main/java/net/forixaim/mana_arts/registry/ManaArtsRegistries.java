package net.forixaim.mana_arts.registry;

import net.forixaim.mana_arts.ManaArts;
import net.forixaim.mana_arts.api.data.Element;
import net.forixaim.mana_arts.api.data.Spell;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;

public class ManaArtsRegistries
{
    public static final Registry<Element.Builder> ELEMENTS = new RegistryBuilder<>(RegistryKeys.ELEMENTS).create();
    public static final Registry<Spell> SPELLS = new RegistryBuilder<>(RegistryKeys.SPELLS).create();

    public static class RegistryKeys {
        public static final ResourceKey<Registry<Element.Builder>> ELEMENTS = ResourceKey.createRegistryKey(ManaArts.identifier("elements"));
        public static final ResourceKey<Registry<Spell>> SPELLS = ResourceKey.createRegistryKey(ManaArts.identifier("spells"));
    }

    public static void onRegister(final NewRegistryEvent event)
    {
        event.register(ELEMENTS);
        event.register(SPELLS);
    }
}
