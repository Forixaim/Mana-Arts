package net.forixaim.mana_arts.spells;

import net.forixaim.mana_arts.api.data.internal.CastContext;
import net.forixaim.mana_arts.api.data.spell.Spell;
import net.forixaim.mana_arts.registry.entries.ManaArtsEntities;
import net.forixaim.mana_arts.world.entity.spell.Blast;
import net.minecraft.world.entity.LivingEntity;

public class BlastSpell extends Spell {
    public BlastSpell() {
        this.baseCost = 0;
    }
    @Override
    public void cast(LivingEntity entity, CastContext context) {
        super.cast(entity, context);
        Blast blast = ManaArtsEntities.BLAST.get().create(entity.level());
        if (blast == null) return;
        blast.setOwner(entity);
        blast.setCastContext(context);
        entity.level().addFreshEntity(blast);
    }
}
