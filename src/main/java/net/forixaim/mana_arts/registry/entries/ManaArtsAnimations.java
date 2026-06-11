package net.forixaim.mana_arts.registry.entries;

import net.forixaim.mana_arts.ManaArts;
import net.forixaim.mana_arts.api.animation.type.CastAnimation;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.gameasset.Armatures;

public class ManaArtsAnimations
{
    public static AnimationManager.AnimationAccessor<CastAnimation> CAST;

    public static void init(AnimationManager.AnimationRegistryEvent event)
    {
        event.newBuilder(ManaArts.MOD_ID, ManaArtsAnimations::build);
    }

    public static void build(AnimationManager.AnimationBuilder builder)
    {
        CAST = builder.nextAccessor("cast_combo/cast1", access -> new CastAnimation(0.1f, 0.15f, access, Armatures.BIPED, Armatures.BIPED.get().handR));
    }
}
