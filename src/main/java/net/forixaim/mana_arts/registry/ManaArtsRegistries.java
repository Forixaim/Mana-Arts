package net.forixaim.mana_arts.registry;

import com.google.common.collect.Lists;
import net.forixaim.mana_arts.ManaArts;
import net.forixaim.mana_arts.api.data.element.Element;
import net.forixaim.mana_arts.api.data.spell.Spell;
import net.forixaim.mana_arts.api.data.spell.SpellModifier;
import net.forixaim.mana_arts.registry.entries.*;
import net.forixaim.mana_arts.world.ManaSource;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;

import java.util.List;

public class ManaArtsRegistries
{
    public static final Registry<Element> ELEMENTS = new RegistryBuilder<>(RegistryKeys.ELEMENTS).create();
    public static final Registry<Spell> SPELLS = new RegistryBuilder<>(RegistryKeys.SPELLS).create();
    public static final Registry<SpellModifier<?>> SPELL_MODIFIERS = new RegistryBuilder<>(RegistryKeys.SPELL_MODIFIERS).create();
    public static final Registry<ManaSource> MANA_SOURCE_TAGS = new RegistryBuilder<>(RegistryKeys.MANA_SOURCE_TAGS).create();

    public static class RegistryKeys {
        public static final ResourceKey<Registry<Element>> ELEMENTS = ResourceKey.createRegistryKey(ManaArts.identifier("elements"));
        public static final ResourceKey<Registry<Spell>> SPELLS = ResourceKey.createRegistryKey(ManaArts.identifier("spells"));
        public static final ResourceKey<Registry<SpellModifier<?>>> SPELL_MODIFIERS = ResourceKey.createRegistryKey(ManaArts.identifier("spell_modifiers"));
        public static final ResourceKey<Registry<ManaSource>> MANA_SOURCE_TAGS = ResourceKey.createRegistryKey(ManaArts.identifier("mana_source_tags"));
    }

    public static final List<DeferredRegister<?>> REGISTERS = Lists.newArrayList(
            ManaArtsEntities.REGISTRY,
            ManaArtsSpells.REGISTRY,
            ManaArtsElements.REGISTRY,
            ManaArtsAttributes.REGISTRY,
            ManaArtsAttachments.REGISTRY,
            ManaArtsSpellModifiers.REGISTRY,
            ManaArtsParticles.REGISTRY,
            ManaArtsSounds.REGISTRY,
            ManaArtsWeaponData.REGISTRY,
            ManaArtsSkills.REGISTRY
    );

    public static void onRegister(final NewRegistryEvent event)
    {
        event.register(ELEMENTS);
        event.register(SPELLS);
        event.register(SPELL_MODIFIERS);
        event.register(MANA_SOURCE_TAGS);
    }
}
