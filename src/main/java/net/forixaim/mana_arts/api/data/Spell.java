package net.forixaim.mana_arts.api.data;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.fml.ModList;
import yesman.epicfight.EpicFight;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.EntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public abstract class Spell
{
    protected ResourceLocation id;
    public String getTranslationKey()
    {
        return "element.".concat(id.getNamespace()).concat(".").concat(id.getPath());
    }

    public Component getTranslatedName()
    {
        return Component.translatable(getTranslationKey());
    }

    public ResourceLocation getIconLocation()
    {
        return ResourceLocation.fromNamespaceAndPath(id.getNamespace(), "textures/gui/mana_arts/elements/".concat(id.getPath()).concat(".png"));
    }

    public final void preCast(LivingEntity entity, CastContext context)
    {
        vanillaOrEpicFight(entity, context, this::preCastVanilla, this::preCastEpicFight);
    }

    private void vanillaOrEpicFight(LivingEntity entity, CastContext context, BiConsumer<LivingEntity, CastContext> vanilla, BiConsumer<LivingEntityPatch<? extends LivingEntity>, CastContext> epicFight)
    {
        if (ModList.get().isLoaded(EpicFight.MODID) && EpicFightCapabilities.getEntityPatch(entity, EntityPatch.class) instanceof LivingEntityPatch<? extends LivingEntity> entityPatch)
        {
            epicFight.accept(entityPatch, context);
        }
        else
        {
            vanilla.accept(entity, context);
        }
    }

    public abstract void preCastEpicFight(LivingEntityPatch<? extends LivingEntity> entityPatch, CastContext context);
    public abstract void preCastVanilla(LivingEntity entity, CastContext context);

    public final void cast(LivingEntity entity, CastContext context) {
        vanillaOrEpicFight(entity, context, this::castVanilla, this::castEpicFight);
    }

    public abstract void castEpicFight(LivingEntityPatch<? extends LivingEntity> entityPatch, CastContext context);
    public abstract void castVanilla(LivingEntity entity, CastContext context);

    public final void postCast(LivingEntity entity, CastContext context) {
        vanillaOrEpicFight(entity, context, this::postCastVanilla, this::postCastEpicFight);
    }

    public abstract void postCastEpicFight(LivingEntityPatch<? extends LivingEntity> entityPatch, CastContext context);
    public abstract void postCastVanilla(LivingEntity entity, CastContext context);

    public abstract void onTick(LivingEntity entity);


    public static class Builder
    {

    }
}
