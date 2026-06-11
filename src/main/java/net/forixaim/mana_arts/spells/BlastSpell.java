package net.forixaim.mana_arts.spells;

import net.forixaim.mana_arts.api.data.element.Element;
import net.forixaim.mana_arts.api.data.internal.CastContext;
import net.forixaim.mana_arts.api.data.spell.OffensiveSpell;
import net.forixaim.mana_arts.api.managers.ElementManager;
import net.forixaim.mana_arts.registry.entries.ManaArtsEntities;
import net.forixaim.mana_arts.registry.entries.ManaArtsSpellModifiers;
import net.forixaim.mana_arts.registry.entries.ManaArtsSpells;
import net.forixaim.mana_arts.world.entity.spell.Blast;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class BlastSpell extends OffensiveSpell {
    public BlastSpell(ResourceLocation id) {
        super(id);
        this.baseCost = 0;
        this.baseDamage = 10;
        addAllowedModifier(ManaArtsSpellModifiers.BLAST_RADIUS);
        addAllowedModifier(ManaArtsSpellModifiers.PROJECTILE_SIZE);
    }

    @Override
    public void cast(LivingEntity entity, CastContext context, Joint joint) {
        super.cast(entity, context, joint);

    }

    @Override
    public void castEpicFight(LivingEntityPatch<? extends LivingEntity> entityPatch, CastContext context, Joint castingJoint) {
        super.castEpicFight(entityPatch, context, castingJoint);
        Element element = ElementManager.getElement(context.element()).value();
        Blast blast = ManaArtsEntities.BLAST.get().create(entityPatch.getOriginal().level());
        if (blast == null) return;
        Vec3 pos = new Vec3(entityPatch.getOriginal().getX(), entityPatch.getOriginal().getEyeY() - (blast.getBoundingBox().getYsize() / 2), entityPatch.getOriginal().getZ());
        Vec3 angle = entityPatch.getOriginal().getLookAngle();
        if (castingJoint != null)
        {
            OpenMatrix4f jointMatrix = entityPatch.getArmature().getBoundTransformFor(entityPatch.getAnimator().getPose(0.0F), castingJoint).mulFront(OpenMatrix4f.createTranslation((float) entityPatch.getOriginal().getX(), (float) entityPatch.getOriginal().getY(), (float) entityPatch.getOriginal().getZ()).mulBack(OpenMatrix4f.createRotatorDeg(180.0F, Vec3f.Y_AXIS).mulBack(entityPatch.getModelMatrix(0.0F))));
            jointMatrix.translate(0, 0.5f, 0);
            pos = jointMatrix.toTranslationVector().toDoubleVector();
        }
        blast.setOwner(entityPatch.getOriginal());
        blast.setCastContext(context);
        blast.setPos(pos);
        float velocity = (float) (1.0f * element.velocityModifier());
        blast.shoot(angle.x, angle.y, angle.z, velocity, 0);
        entityPatch.getOriginal().level().addFreshEntity(blast);
    }

    @Override
    public void castVanilla(LivingEntity entity, CastContext context) {
        super.castVanilla(entity, context);
        Element element = ElementManager.getElement(context.element()).value();
        Blast blast = ManaArtsEntities.BLAST.get().create(entity.level());
        if (blast == null) return;
        blast.setOwner(entity);
        blast.setCastContext(context);
        blast.setPos(entity.getX(), entity.getEyeY() - (blast.getBoundingBox().getYsize() / 2), entity.getZ());
        Vec3 angle = entity.getLookAngle();
        float velocity = (float) (1.0f * element.velocityModifier());
        blast.shoot(angle.x, angle.y, angle.z, velocity, 0);
        entity.level().addFreshEntity(blast);
    }
}
