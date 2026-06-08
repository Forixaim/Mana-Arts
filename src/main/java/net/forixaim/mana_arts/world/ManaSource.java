package net.forixaim.mana_arts.world;

import com.google.common.collect.ImmutableList;
import net.minecraft.tags.TagKey;

import java.util.List;

public record ManaSource(double amount, List<TagKey<ManaSource>> tags)
{
    @SafeVarargs
    public ManaSource (double amount, TagKey<ManaSource>... tags)
    {
        this(amount, ImmutableList.<TagKey<ManaSource>>builder().add(tags).build());
    }

    public boolean is(TagKey<ManaSource> tag)
    {
        return tags().contains(tag);
    }
}
