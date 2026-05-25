package net.forixaim.mana_arts.api.data;

import com.google.common.collect.Maps;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

/**
 * A serializable class that holds the context of a spell cast.
 */
public record CastContext(ResourceLocation spell, ResourceLocation element, Map<ResourceLocation, Double> modifiers)
{
    public static final StreamCodec<ByteBuf, CastContext> STREAM_CODEC =
            ByteBufCodecs.COMPOUND_TAG.map(CastContext::deserialize, CastContext::serialize);

    private CompoundTag serialize()
    {
        CompoundTag result = new CompoundTag();
        result.putString("spell", spell.toString());
        result.putString("element", element.toString());
        CompoundTag modifiersTag = new CompoundTag();
        modifiers.forEach((key, value) -> modifiersTag.putDouble(key.toString(), value));
        result.put("modifiers", modifiersTag);
        return result;
    }

    private static CastContext deserialize(CompoundTag tag)
    {
        Map<ResourceLocation, Double> modifiers = Maps.newHashMap();
        CompoundTag modifiersTag = tag.getCompound("modifiers");
        modifiersTag.getAllKeys().forEach(key -> modifiers.put(ResourceLocation.tryParse(key), modifiersTag.getDouble(key)));
        return new CastContext(ResourceLocation.tryParse(tag.getString("spell")), ResourceLocation.tryParse(tag.getString("element")), modifiers);
    }
}
