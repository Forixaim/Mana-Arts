package net.forixaim.mana_arts.world.entity.spell;

import net.forixaim.mana_arts.api.data.internal.CastContext;
import net.forixaim.mana_arts.api.managers.ElementManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public abstract class SpellProjectile extends Projectile
{
    public static EntityDataAccessor<CompoundTag> CAST_CONTEXT = SynchedEntityData.defineId(SpellProjectile.class, EntityDataSerializers.COMPOUND_TAG);
    private CastContext builtContext;

    protected SpellProjectile(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
    }

    public void setCastContext(CastContext context) {
        this.entityData.set(CAST_CONTEXT, context.serialize());
        this.builtContext = context;
    }

    public CastContext getCastContext() {
        return this.builtContext;
    }

    @Override
    public @NotNull EntityDimensions getDimensions(@NotNull Pose pose) {
        double scale = ElementManager.getElement(this.builtContext.element()).value().sizeModifier();
        return super.getDimensions(pose).scale((float) scale);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(CAST_CONTEXT, new CompoundTag());
    }

    @Override
    protected void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.put("cast_context", this.entityData.get(CAST_CONTEXT));
    }

    @Override
    protected void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.entityData.set(CAST_CONTEXT, tag.getCompound("cast_context"));
        this.builtContext = CastContext.deserialize(this.entityData.get(CAST_CONTEXT));
    }
}
