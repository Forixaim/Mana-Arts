package net.forixaim.mana_arts.world;

import net.forixaim.mana_arts.ManaArts;
import net.forixaim.mana_arts.registry.ManaArtsRegistries;
import net.minecraft.tags.TagKey;

public interface ManaSourceTags
{
    TagKey<ManaSource> MANUAL_CHARGING = TagKey.create(ManaArtsRegistries.RegistryKeys.MANA_SOURCE_TAGS, ManaArts.identifier("manual_charging"));
    TagKey<ManaSource> PASSIVE_REGENERATION = TagKey.create(ManaArtsRegistries.RegistryKeys.MANA_SOURCE_TAGS, ManaArts.identifier("passive_regeneration"));
}
