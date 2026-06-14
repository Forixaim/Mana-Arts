package net.forixaim.mana_arts.api.data.spell;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.forixaim.mana_arts.api.data.element.Element;
import net.forixaim.mana_arts.api.data.internal.CastContext;
import net.forixaim.mana_arts.api.managers.ElementManager;
import net.forixaim.mana_arts.api.managers.ModifierManager;
import net.forixaim.mana_arts.api.managers.SpellManager;
import net.forixaim.mana_arts.registry.entries.ManaArtsSpells;
import net.forixaim.mana_arts.world.entity.spell.SpellProjectile;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.fml.ModList;
import yesman.epicfight.EpicFight;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.EntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public abstract class Spell
{
    protected ResourceLocation id;
    public double baseCost;
    protected final List<Holder<SpellModifier<? extends SpellProjectile>>> allowedModifiers = Lists.newArrayList();

    public List<Holder<SpellModifier<? extends SpellProjectile>>> getAllowedModifiers() {
        return ImmutableList.copyOf(allowedModifiers);
    }

    public void addAllowedModifier(Holder<SpellModifier<? extends SpellProjectile>> id)
    {
        allowedModifiers.add(id);
    }

    public Spell(ResourceLocation id) {
        this.id = id;
    }

    public Map<Holder<SpellModifier<? extends SpellProjectile>>, Double> initDefaultModifiers() {
        Map<Holder<SpellModifier<? extends SpellProjectile>>, Double> result = Maps.newHashMap();
        allowedModifiers.forEach(holder -> result.put(holder, holder.value().defaultValue()));
        return result;
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

    public void preCast(LivingEntity entity, CastContext context, Joint joint)
    {
        vanillaOrEpicFight(entity, context, this::preCastVanilla, this::preCastEpicFight, joint);
    }


    public void preCastEpicFight(LivingEntityPatch<? extends LivingEntity> entityPatch, CastContext context, Joint joint) {}
    public void preCastVanilla(LivingEntity entity, CastContext context) {}

    public void cast(LivingEntity entity, CastContext context, Joint joint) {
        vanillaOrEpicFight(entity, context, this::castVanilla, this::castEpicFight, joint);
        Element element = context.element().value();
        Holder<Spell> self = SpellManager.getSpell(this.id);
        if (element.table().getEffectFor(self) != null)
        {
            if (element.table().getEffectFor(self).spawn() != null)
            {
                element.table().getEffectFor(self).spawn().accept(entity.position(), entity.level());
            }
        }
    }

    public void castEpicFight(LivingEntityPatch<? extends LivingEntity> entityPatch, CastContext context, Joint joint) {}
    public void castVanilla(LivingEntity entity, CastContext context) {}

    public void postCast(LivingEntity entity, CastContext context, Joint joint) {
        vanillaOrEpicFight(entity, context, this::postCastVanilla, this::postCastEpicFight, joint);
    }

    public void postCastEpicFight(LivingEntityPatch<? extends LivingEntity> entityPatch, CastContext context, Joint joint) {}
    public void postCastVanilla(LivingEntity entity, CastContext context) {}

    public void onTick(LivingEntity entity) {

    }

    private void vanillaOrEpicFight(LivingEntity entity, CastContext context, BiConsumer<LivingEntity, CastContext> vanilla, CastConsumer epicFight, Joint joint)
    {
        if (ModList.get().isLoaded(EpicFight.MODID) && EpicFightCapabilities.getEntityPatch(entity, EntityPatch.class) instanceof LivingEntityPatch<? extends LivingEntity> entityPatch)
        {
            epicFight.accept(entityPatch, context, joint);
        }
        else
        {
            vanilla.accept(entity, context);
        }
    }

}
