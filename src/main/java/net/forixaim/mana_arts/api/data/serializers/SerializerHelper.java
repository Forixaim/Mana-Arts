package net.forixaim.mana_arts.api.data.serializers;

import com.google.common.collect.Maps;
import net.forixaim.mana_arts.api.data.spell.SpellModifier;
import net.forixaim.mana_arts.api.managers.ModifierManager;
import net.forixaim.mana_arts.world.entity.spell.SpellProjectile;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public interface SerializerHelper
{
    static CompoundTag serializeModifiers(Map<Holder<SpellModifier<? extends SpellProjectile>>, Double> modifiers)
    {
        CompoundTag modifiersTag = new CompoundTag();
        modifiers.forEach((key, value) -> modifiersTag.putDouble(key.getRegisteredName(), value));
        return modifiersTag;
    }

    static Map<Holder<SpellModifier<? extends SpellProjectile>>, Double> deserializeModifiers(CompoundTag tag)
    {
        Map<Holder<SpellModifier<? extends SpellProjectile>>, Double> modifiers = Maps.newHashMap();
        CompoundTag modifiersTag = tag.getCompound("modifiers");
        modifiersTag.getAllKeys().forEach(key -> modifiers.put(ModifierManager.getModifier(key), modifiersTag.getDouble(key)));
        return modifiers;
    }
}
