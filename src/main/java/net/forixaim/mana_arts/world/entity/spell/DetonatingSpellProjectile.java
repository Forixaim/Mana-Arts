package net.forixaim.mana_arts.world.entity.spell;

import com.google.common.collect.Lists;
import net.forixaim.mana_arts.api.data.element.Element;
import net.forixaim.mana_arts.api.data.spell.Spell;
import net.forixaim.mana_arts.api.managers.ElementManager;
import net.forixaim.mana_arts.api.managers.SpellManager;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class DetonatingSpellProjectile extends SpellProjectile
{
    protected List<Float> blastRadiusModifiers = Lists.newArrayList();

    protected DetonatingSpellProjectile(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
    }

    protected float getBlastRadius()
    {
        float result = 1.5f;
        for (Float modifier : blastRadiusModifiers)
        {
            result *= modifier;
        }
        return result;
    }

    public void addBlastModifier(float modifier)
    {
        blastRadiusModifiers.add(modifier);
    }

    @Override
    public void remove(@NotNull RemovalReason reason) {
        if (reason == RemovalReason.DISCARDED) detonate();
        super.remove(reason);
    }

    public void addBlastModifier(double modifier)
    {
        addBlastModifier((float) modifier);
    }

    protected void detonate()
    {
        Element element = ElementManager.getElement(getCastContext().element()).value();
        Entity owner = getOwner();
        DamageSource magicDamage;
        if (owner instanceof LivingEntity livingEntity)
        {
            if (livingEntity instanceof Player player)
            {
                magicDamage = level().damageSources().playerAttack(player);
            }
            else
            {
                magicDamage = level().damageSources().mobAttack(livingEntity);
            }
        }
        else
        {
            magicDamage = level().damageSources().magic();
        }
        Holder<Spell> spell = SpellManager.getSpell(getCastContext().spell());
        if (element.table().getEffects().containsKey(spell))
        {
            element.table().getEffectFor(spell).onExpire().accept(this.position(), this.level());
        }
        else
        {
            if (this.level() instanceof ServerLevel serverLevel)
            {
                serverLevel.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 1.0F, 1.0F);
                serverLevel.sendParticles(ParticleTypes.EXPLOSION_EMITTER, this.getX(), this.getY(), this.getZ(), 2, 0.1, 0.1, 0.1, 0.0);
            }
        }
        AABB blastBox = this.getBoundingBox().inflate(getBlastRadius());
        List<LivingEntity> entities = this.level().getEntitiesOfClass(LivingEntity.class, blastBox);
        entities.forEach( entity -> {
            float damage = this.getCastContext().calculateDamage();
            if (entity.hurt(magicDamage, damage))
            {
                if (element.onHitEffect() != null)
                    element.onHitEffect().accept(magicDamage, entity);
            }
        });
    }
}
