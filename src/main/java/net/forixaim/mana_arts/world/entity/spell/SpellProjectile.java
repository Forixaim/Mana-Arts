package net.forixaim.mana_arts.world.entity.spell;

import net.forixaim.mana_arts.ManaArts;
import net.forixaim.mana_arts.api.data.element.Element;
import net.forixaim.mana_arts.api.data.internal.CastContext;
import net.forixaim.mana_arts.api.data.spell.Spell;
import net.forixaim.mana_arts.api.managers.ElementManager;
import net.forixaim.mana_arts.api.managers.SpellManager;
import net.forixaim.mana_arts.registry.entries.ManaArtsSpellModifiers;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class SpellProjectile extends Projectile
{
    private final List<Float> scaleModifiers = new CopyOnWriteArrayList<>();
    public static EntityDataAccessor<CompoundTag> CAST_CONTEXT = SynchedEntityData.defineId(SpellProjectile.class, EntityDataSerializers.COMPOUND_TAG);
    private CastContext builtContext;
    protected Holder<Element> cachedElement;
    protected Holder<Spell> cachedSpell;
    protected Map<ResourceLocation, Modifier> modifiers;

    protected SpellProjectile(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
    }

    public void setCastContext(CastContext context) {
        this.entityData.set(CAST_CONTEXT, context.serialize());
        this.builtContext = context;
        this.cachedSpell = context.spell();
        this.cachedElement = context.element();
        if (context.modifiers().containsKey(ManaArtsSpellModifiers.PROJECTILE_SIZE))
        {
            this.addScaleModifier(context.modifiers().get(ManaArtsSpellModifiers.PROJECTILE_SIZE) / 100);
        }
    }

    @Override
    public void onAddedToLevel() {
        super.onAddedToLevel();

    }

    @Override
    public void tick() {
        super.tick();
        ManaArts.LOGGER.info("Total scale modifier: {}", getTotalScaleModifier());

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
        if (this.getCastContext() != null)
        {
            if (getCastContext().element() != null)
            {
                resultingScale *= (float) getCastContext().element().value().sizeModifier();
            }
            if (!scaleModifiers.isEmpty())
            {
                float totalScaleFromModifiers = 0;
                for (var modifier : scaleModifiers)
                {
                    totalScaleFromModifiers += modifier;
                }
                resultingScale *= totalScaleFromModifiers;
            }
            if (getCastContext().modifiers().containsKey(ManaArtsSpellModifiers.PROJECTILE_SIZE))
            {
                ManaArts.LOGGER.debug("Projectile size modifier: {}", getCastContext().modifiers().get(ManaArtsSpellModifiers.PROJECTILE_SIZE));
                resultingScale *= (getCastContext().modifiers().get(ManaArtsSpellModifiers.PROJECTILE_SIZE).floatValue() / 100f);
            }
        }

        return resultingScale;
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
