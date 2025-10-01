package net.forixaim.mana_arts.animations;

import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.ActionAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.gameasset.Armatures;

public class ManaArtsAnimations
{

    public static AnimationManager.AnimationAccessor<StaticAnimation> CHARGE_1;
    public static AnimationManager.AnimationAccessor<ActionAnimation> CHARGE_RELEASE_1;


    public static void build(final AnimationManager.AnimationBuilder builder)
    {
        CHARGE_1 = builder.nextAccessor("mana_arts/charge1", access ->
                new StaticAnimation(0.3f,true, access, Armatures.BIPED));
        CHARGE_RELEASE_1 = builder.nextAccessor("mana_arts/charge_release1", access ->
                new ActionAnimation(0.1f, access, Armatures.BIPED)
                        .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                        .addState(EntityState.MOVEMENT_LOCKED, true));
    }
}
