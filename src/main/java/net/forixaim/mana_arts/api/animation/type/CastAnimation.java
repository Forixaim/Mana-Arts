package net.forixaim.mana_arts.api.animation.type;

import net.forixaim.mana_arts.registry.entries.ManaArtsAttachments;
import net.forixaim.mana_arts.world.capabilities.ManaEntity;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.ActionAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class CastAnimation extends ActionAnimation {
    private final float castStartup;
    private final Joint castingJoint;
    public CastAnimation(float transitionTime, float castStartup, AnimationManager.AnimationAccessor<? extends ActionAnimation> accessor, AssetAccessor<? extends Armature> armature, Joint castingJoint) {
        super(transitionTime, accessor, armature);
        this.castingJoint = castingJoint;
        this.addProperty(AnimationProperty.StaticAnimationProperty.POSE_MODIFIER, Animations.ReusableSources.COMBO_ATTACK_DIRECTION_MODIFIER);
        this.castStartup = castStartup;
    }

    public CastAnimation(float transitionTime, float castStartup, float postDelay, AnimationManager.AnimationAccessor<? extends ActionAnimation> accessor, AssetAccessor<? extends Armature> armature, Joint castingJoint) {
        super(transitionTime, postDelay, accessor, armature);
        this.castingJoint = castingJoint;
        this.addProperty(AnimationProperty.StaticAnimationProperty.POSE_MODIFIER, Animations.ReusableSources.COMBO_ATTACK_DIRECTION_MODIFIER);
        this.castStartup = castStartup;
    }

    public CastAnimation(float transitionTime, float castStartup, float postDelay, String path, AssetAccessor<? extends Armature> armature, Joint castingJoint) {
        super(transitionTime, postDelay, path, armature);
        this.castingJoint = castingJoint;
        this.addProperty(AnimationProperty.StaticAnimationProperty.POSE_MODIFIER, Animations.ReusableSources.COMBO_ATTACK_DIRECTION_MODIFIER);
        this.castStartup = castStartup;
    }

    @Override
    public void tick(LivingEntityPatch<?> entitypatch) {
        super.tick(entitypatch);
        AnimationPlayer player = entitypatch.getAnimator().getPlayerFor(this.getAccessor());

        if (player == null) return;

        float elapsedTime = player.getElapsedTime();

        if (this.castStartup == elapsedTime)
        {
            if (!entitypatch.isLogicalClient())
            {
                ManaEntity mage = entitypatch.getOriginal().getData(ManaArtsAttachments.MANA_ENTITY);
                if (mage.getQueuedSpell() != null)
                {
                    mage.getQueuedSpell().handleCast(mage.getQueuedSpell().buildContext(), entitypatch.getOriginal(), castingJoint);
                    mage.clearQueuedSpell();
                }
            }
        }
    }


}
