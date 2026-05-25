package net.forixaim.mana_arts.api.data.serializers;

import com.google.common.collect.Maps;
import net.forixaim.mana_arts.api.data.SpellModifier;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public interface SerializerHelper
{
    static CompoundTag serializeModifiers(Map<ResourceLocation, Double> modifiers)
    {
        CompoundTag modifiersTag = new CompoundTag();
        modifiers.forEach((key, value) -> modifiersTag.putDouble(key.toString(), value));
        return modifiersTag;
    }

    static Map<ResourceLocation, Double> deserializeModifiers(CompoundTag tag)
    {
        Map<ResourceLocation, Double> modifiers = Maps.newHashMap();
        CompoundTag modifiersTag = tag.getCompound("modifiers");
        modifiersTag.getAllKeys().forEach(key -> modifiers.put(ResourceLocation.tryParse(key), modifiersTag.getDouble(key)));
        return modifiers;
    }
}
