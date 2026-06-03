package net.forixaim.mana_arts.spells;

import net.forixaim.mana_arts.api.data.element.Element;
import net.forixaim.mana_arts.api.data.internal.CastContext;
import net.forixaim.mana_arts.api.data.spell.Spell;
import net.forixaim.mana_arts.api.managers.ElementManager;
import net.forixaim.mana_arts.registry.entries.ManaArtsEntities;
import net.forixaim.mana_arts.world.entity.spell.Blast;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class BlastSpell extends Spell {
    public BlastSpell() {
        this.baseCost = 0;
    }
    @Override
    public void cast(LivingEntity entity, CastContext context) {
        super.cast(entity, context);
        Element element = ElementManager.getElement(context.element()).value();
        Blast blast = ManaArtsEntities.BLAST.get().create(entity.level());
        if (blast == null) return;
        blast.setOwner(entity);
        blast.setCastContext(context);
        blast.setPos(entity.getX(), entity.getEyeY(), entity.getZ());
        Vec3 angle = entity.getLookAngle();
        float velocity = (float) (1.0f * element.velocityModifier());
        blast.shoot(angle.x, angle.y, angle.z, velocity, 0);
        entity.level().addFreshEntity(blast);
    }
}
