package net.forixaim.mana_arts.world.entity.spell;

import net.forixaim.mana_arts.api.data.element.Element;
import net.forixaim.mana_arts.api.data.internal.CastContext;
import net.forixaim.mana_arts.api.data.spell.Spell;
import net.forixaim.mana_arts.api.managers.ElementManager;
import net.forixaim.mana_arts.api.managers.SpellManager;
import net.forixaim.mana_arts.registry.entries.ManaArtsSpells;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class SpellProjectile extends Projectile
{
    private List<Float> scaleModifiers = Lists.newArrayList();
    public static EntityDataAccessor<CompoundTag> CAST_CONTEXT = SynchedEntityData.defineId(SpellProjectile.class, EntityDataSerializers.COMPOUND_TAG);
    private CastContext builtContext;
    protected Holder<Element> cachedElement;
    protected Holder<Spell> cachedSpell;

    protected SpellProjectile(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
    }

    public void setCastContext(CastContext context) {
        this.entityData.set(CAST_CONTEXT, context.serialize());
        this.builtContext = context;
        this.cachedSpell = SpellManager.getSpell(context.spell());
        this.cachedElement = ElementManager.getElement(context.element());
    }

    @Override
    public void onAddedToLevel() {
        super.onAddedToLevel();
    }

    public CastContext getCastContext() {
        if (this.builtContext == null) {
            this.builtContext = CastContext.deserialize(this.entityData.get(CAST_CONTEXT));
        }
        return this.builtContext;
    }

    public void addScaleModifier(float modifier) {
        this.scaleModifiers.add(modifier);
    }

    public void addScaleModifier(double modifier) {
        this.scaleModifiers.add((float) modifier);
    }

    public float getTotalScaleModifier() {
        float resultingScale = 1.0f;
        resultingScale *= (float) ElementManager.getElement(this.builtContext.element()).value().sizeModifier();
        return resultingScale * this.scaleModifiers.stream().reduce(1.0f, (a, b) -> a * b);
    }

    @Override
    public @NotNull EntityDimensions getDimensions(@NotNull Pose pose) {
        return super.getDimensions(pose).scale(getTotalScaleModifier());
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
