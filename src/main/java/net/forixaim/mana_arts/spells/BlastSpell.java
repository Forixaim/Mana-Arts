package net.forixaim.mana_arts.spells;

import net.forixaim.mana_arts.api.data.element.Element;
import net.forixaim.mana_arts.api.data.internal.CastContext;
import net.forixaim.mana_arts.api.data.spell.OffensiveSpell;
import net.forixaim.mana_arts.api.managers.ElementManager;
import net.forixaim.mana_arts.registry.entries.ManaArtsEntities;
import net.forixaim.mana_arts.registry.entries.ManaArtsSpellModifiers;
import net.forixaim.mana_arts.world.entity.spell.Blast;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class BlastSpell extends OffensiveSpell {
    public BlastSpell(ResourceLocation id) {
        super(id);
        this.baseCost = 0;
        this.baseDamage = 10;
        addAllowedModifier(ManaArtsSpellModifiers.BLAST_RADIUS);
        addAllowedModifier(ManaArtsSpellModifiers.PROJECTILE_SIZE);
    }
    @Override
    public void cast(LivingEntity entity, CastContext context) {
        super.cast(entity, context);
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
