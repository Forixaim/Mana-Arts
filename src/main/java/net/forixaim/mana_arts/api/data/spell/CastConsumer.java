package net.forixaim.mana_arts.api.data.spell;

import net.forixaim.mana_arts.api.data.internal.CastContext;
import net.minecraft.world.entity.LivingEntity;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@FunctionalInterface
public interface CastConsumer {
    void accept(LivingEntityPatch<?> entity, CastContext context, Joint joint);
}
