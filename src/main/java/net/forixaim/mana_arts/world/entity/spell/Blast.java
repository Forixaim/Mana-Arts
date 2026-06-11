package net.forixaim.mana_arts.world.entity.spell;

import net.forixaim.mana_arts.api.data.element.Element;
import net.forixaim.mana_arts.api.data.element.ParticleEffects;
import net.forixaim.mana_arts.api.managers.ElementManager;
import net.forixaim.mana_arts.registry.entries.ManaArtsSpells;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class Blast extends DetonatingSpellProjectile
{
    private int lifetime = 40;
    public Blast(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
    }

    public int getLifetime() {
        return lifetime;
    }

    @Override
    public void tick() {
        super.tick();
        Vec3 originalVec = this.getDeltaMovement();
        double d7;
        double d2;
        double d3;
        if (magnitude(originalVec) > 0.0)
        {
            double d5 = originalVec.x;
            double d6 = originalVec.y;
            double d1 = originalVec.z;
            d7 = this.getX() + d5;
            d2 = this.getY() + d6;
            d3 = this.getZ() + d1;
        }
        else
        {
            d7 = this.getX() + 0;
            d2 = this.getY() + 0;
            d3 = this.getZ() + 0;
        }
        this.setPos(d7, d2, d3);

        if (!this.level().isClientSide) {
            lifetime--;
            if (lifetime <= 0) {
                this.discard();
                return;
            }


            HitResult hitResult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
            if (hitResult.getType() != HitResult.Type.MISS) {
                this.onHit(hitResult);
            }
            if (this.cachedElement != null && this.cachedSpell != null)
            {
                ParticleEffects blastEffects = this.cachedElement.value().table().getEffectFor(cachedSpell);
                if (blastEffects != null && blastEffects.tick() != null) {
                    blastEffects.tick().accept(this.position(), this.level());
                }
                else
                {
                    ((ServerLevel)this.level()).sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, this.getX(), this.getY(), this.getZ(), 1, 0.0, 0.0, 0.0, 0.0);
                }
            }
            else
            {
                ((ServerLevel)this.level()).sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, this.getX(), this.getY(), this.getZ(), 1, 0.0, 0.0, 0.0, 0.0);
            }
        }
        super.tick();
    }



    private double magnitude(Vec3 vec)
    {
        return Math.sqrt(vec.x * vec.x + vec.y * vec.y + vec.z * vec.z);
    }


    @Override
    protected void onHit(@NotNull HitResult result)
    {
        super.onHit(result);
        this.discard();
    }

    @Override
    public void onAddedToLevel() {
        super.onAddedToLevel();
    }
}
