package net.forixaim.mana_arts.api.data;

import com.google.common.collect.Maps;
import io.netty.buffer.ByteBuf;
import net.forixaim.mana_arts.api.data.serializers.SerializerHelper;
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
        result.put("modifiers", SerializerHelper.serializeModifiers(modifiers));
        return result;
    }

    private static CastContext deserialize(CompoundTag tag)
    {

        return new CastContext(ResourceLocation.tryParse(tag.getString("spell")), ResourceLocation.tryParse(tag.getString("element")), SerializerHelper.deserializeModifiers(tag.getCompound("modifiers")));
    }
}
