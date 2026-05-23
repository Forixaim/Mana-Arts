package net.forixaim.mana_arts.api.data;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.fml.ModList;
import yesman.epicfight.EpicFight;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.EntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.function.Consumer;

public abstract class Spell
{
    public static final Spell DEFAULT = new Spell() {};

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

    public final void preCast(LivingEntity entity)
    {
        vanillaOrEpicFight(entity, this::preCastVanilla, this::preCastEpicFight);
    }

    private void vanillaOrEpicFight(LivingEntity entity, Consumer<LivingEntity> vanilla, Consumer<LivingEntityPatch<? extends LivingEntity>> epicFight)
    {
        if (ModList.get().isLoaded(EpicFight.MODID) && EpicFightCapabilities.getEntityPatch(entity, EntityPatch.class) instanceof LivingEntityPatch<? extends LivingEntity> entityPatch)
        {
            epicFight.accept(entityPatch);
        }
        else
        {
            vanilla.accept(entity);
        }
    }

    public abstract void preCastEpicFight(LivingEntityPatch<? extends LivingEntity> entityPatch);
    public abstract void preCastVanilla(LivingEntity entity);

    public final void cast(LivingEntity entity) {
        vanillaOrEpicFight(entity, this::castVanilla, this::castEpicFight);
    }

    public abstract void castEpicFight(LivingEntityPatch<? extends LivingEntity> entityPatch);
    public abstract void castVanilla(LivingEntity entity);

    public final void postCast(LivingEntity entity) {
        vanillaOrEpicFight(entity, this::postCastVanilla, this::postCastEpicFight);
    }

    public abstract void postCastEpicFight(LivingEntityPatch<? extends LivingEntity> entityPatch);
    public abstract void postCastVanilla(LivingEntity entity);

    public abstract void onTick(LivingEntity entity);


    public static class Builder
    {

    }
}
