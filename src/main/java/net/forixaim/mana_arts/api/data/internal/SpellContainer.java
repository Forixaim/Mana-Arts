package net.forixaim.mana_arts.api.data.internal;

import com.google.common.collect.Maps;
import net.forixaim.mana_arts.api.data.element.Element;
import net.forixaim.mana_arts.api.data.spell.Spell;
import net.forixaim.mana_arts.api.data.serializers.SerializerHelper;
import net.forixaim.mana_arts.api.data.spell.SpellModifier;
import net.forixaim.mana_arts.api.managers.ElementManager;
import net.forixaim.mana_arts.api.managers.ModifierManager;
import net.forixaim.mana_arts.api.managers.SpellManager;
import net.forixaim.mana_arts.netcode.client.CastRequest;
import net.forixaim.mana_arts.registry.entries.ManaArtsAnimations;
import net.forixaim.mana_arts.world.entity.spell.SpellProjectile;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.ApiStatus;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Map;

@ApiStatus.Internal
public class SpellContainer
{
    private Holder<Element> element;
    private Holder<Spell> spell;
    private final Map<Holder<SpellModifier<? extends SpellProjectile>>, Double> modifiers;

    public SpellContainer() {
        modifiers = Maps.newHashMap();
    }

    public boolean hasSpell()
    {
        return spell != null;
    }

    public boolean hasSpell(Holder<Spell> spell)
    {
        return this.spell == spell;
    }

    public Holder<Spell> getSpell()
    {
        if (hasSpell())
        {
            return spell;
        }
        return null;
    }

    public double getManaCost()
    {
        return spell.value().baseCost;
    }

    public void setElement(Holder<Element> element) {
        this.element = element;
    }

    public Holder<Element> getElement()
    {
        return element;
    }

    public Map<Holder<SpellModifier<? extends SpellProjectile>>, Double> getModifiers()
    {
        return modifiers;
    }

    public void setModifiers(Map<Holder<SpellModifier<? extends SpellProjectile>>, Double> modifiers)
    {
        this.modifiers.putAll(modifiers);
    }

    public void setSpell(Holder<Spell> spell)
    {
        this.spell = spell;
        loadModifiers();
    }

    private void loadModifiers()
    {
        this.modifiers.clear();
        this.spell.value().getAllowedModifiers().forEach(
                holder -> this.modifiers.put(holder, holder.value().defaultValue())
        );
    }


    @OnlyIn(Dist.CLIENT)
    public void sendCastRequest() {
        CastRequest resultPacket = new CastRequest(new CompoundTag());
        PacketDistributor.sendToServer(resultPacket);
    }

    public CastContext buildContext() {
        return new CastContext(spell, element, modifiers);
    }


    public void handleCast(CastContext context, LivingEntity player) {
        spell.value().cast(player, context, null);
    }

    public void handleCast(CastContext context, LivingEntity livingEntity, Joint joint) {
        spell.value().cast(livingEntity, context, joint);
    }

    public void handleCastEpicFight(LivingEntityPatch<?> entitypatch) {
        entitypatch.playAnimationSynchronized(ManaArtsAnimations.CAST, 0);
    }

    public CompoundTag serialize()
    {
        CompoundTag result = new CompoundTag();
        if (hasSpell())
        {
            result.putString("spell", spell.getRegisteredName());
            result.put("modifiers", SerializerHelper.serializeModifiers(modifiers));
            result.putString("element", element.getRegisteredName());
        }
        return result;
    }

    public static SpellContainer deserialize(CompoundTag tag)
    {
        SpellContainer result = new SpellContainer();
        if (tag.contains("spell"))
        {
            result.setSpell(SpellManager.getSpell(tag.getString("spell")));
        }
        if (tag.contains("element"))
        {
            result.setElement(ElementManager.getElement(tag.getString("element")));
        }
        if (tag.contains("modifiers"))
        {
            Map<Holder<SpellModifier<? extends SpellProjectile>>, Double> modifiers = SerializerHelper.deserializeModifiers(tag);
            result.modifiers.putAll(modifiers);
        }
        return result;
    }
}
