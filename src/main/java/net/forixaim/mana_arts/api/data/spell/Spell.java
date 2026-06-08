package net.forixaim.mana_arts.api.data.spell;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.forixaim.mana_arts.api.data.internal.CastContext;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.fml.ModList;
import yesman.epicfight.EpicFight;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.EntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.List;
import java.util.function.BiConsumer;

public abstract class Spell
{
    protected ResourceLocation id;
    public double baseCost;
    protected final List<ResourceLocation> allowedModifiers = Lists.newArrayList();

    public List<ResourceLocation> getAllowedModifiers() {
        return ImmutableList.copyOf(allowedModifiers);
    }

    public void addAllowedModifier(Holder<SpellModifier<?>> id)
    {
        allowedModifiers.add(ResourceLocation.parse(id.getRegisteredName()));
    }

    public Spell(ResourceLocation id) {
        this.id = id;
    }

    public String getTranslationKey()
    {
        return "spell.".concat(id.getNamespace()).concat(".").concat(id.getPath());
    }

    public Component getTranslatedName()
    {
        return Component.translatable(getTranslationKey());
    }

    public ResourceLocation getIconLocation()
    {
        return ResourceLocation.fromNamespaceAndPath(id.getNamespace(), "textures/gui/spell_icons/".concat(id.getPath()).concat(".png"));
    }

    public void preCast(LivingEntity entity, CastContext context)
    {
        vanillaOrEpicFight(entity, context, this::preCastVanilla, this::preCastEpicFight);
    }



    public void preCastEpicFight(LivingEntityPatch<? extends LivingEntity> entityPatch, CastContext context) {}
    public void preCastVanilla(LivingEntity entity, CastContext context) {}

    public void cast(LivingEntity entity, CastContext context) {
        vanillaOrEpicFight(entity, context, this::castVanilla, this::castEpicFight);
    }

    public void castEpicFight(LivingEntityPatch<? extends LivingEntity> entityPatch, CastContext context) {}
    public void castVanilla(LivingEntity entity, CastContext context) {}

    public void postCast(LivingEntity entity, CastContext context) {
        vanillaOrEpicFight(entity, context, this::postCastVanilla, this::postCastEpicFight);
    }

    public void postCastEpicFight(LivingEntityPatch<? extends LivingEntity> entityPatch, CastContext context) {}
    public void postCastVanilla(LivingEntity entity, CastContext context) {}

    public void onTick(LivingEntity entity) {

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

}
