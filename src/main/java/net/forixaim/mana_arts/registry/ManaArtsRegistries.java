package net.forixaim.mana_arts.registry;

import net.forixaim.mana_arts.ManaArts;
import net.forixaim.mana_arts.api.data.Element;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.RegistryBuilder;

public class ManaArtsRegistries
{
    public static final Registry<Element> ELEMENTS = new RegistryBuilder<>(RegistryKeys.ELEMENTS).create();

    public static class RegistryKeys {
        public static final ResourceKey<Registry<Element>> ELEMENTS = ResourceKey.createRegistryKey(ManaArts.identifier("elements"));
    }
}
