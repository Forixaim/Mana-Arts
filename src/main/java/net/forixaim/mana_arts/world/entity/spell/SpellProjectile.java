package net.forixaim.mana_arts.world.entity.spell;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;

public abstract class SpellProjectile extends Projectile
{
    public static EntityDataAccessor<String> SPELL = SynchedEntityData.defineId(SpellProjectile.class, EntityDataSerializers.STRING);
    public static EntityDataAccessor<String> ELEMENT = SynchedEntityData.defineId(SpellProjectile.class, EntityDataSerializers.STRING);

    protected SpellProjectile(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
    }
}
