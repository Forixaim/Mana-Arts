package net.forixaim.mana_arts.world.entity.spell;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

public class Blast extends SpellProjectile
{
    private int lifetime = 40;
    public Blast(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void tick() {

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
        }
        super.tick();
    }

    @Override
    public void onAddedToLevel() {
        super.onAddedToLevel();
    }
}
