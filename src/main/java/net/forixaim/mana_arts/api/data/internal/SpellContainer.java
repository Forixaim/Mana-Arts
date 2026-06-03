package net.forixaim.mana_arts.api.data.internal;

import com.google.common.collect.Maps;
import net.forixaim.mana_arts.api.data.element.Element;
import net.forixaim.mana_arts.api.data.spell.Spell;
import net.forixaim.mana_arts.api.data.serializers.SerializerHelper;
import net.forixaim.mana_arts.api.managers.ElementManager;
import net.forixaim.mana_arts.api.managers.SpellManager;
import net.forixaim.mana_arts.netcode.client.CastRequest;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.ApiStatus;

import java.util.Map;

@ApiStatus.Internal
public class SpellContainer
{
    private Holder<Element> element;
    private Holder<Spell> spell;
    private final Map<ResourceLocation, Double> modifiers;

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

    public Map<ResourceLocation, Double> getModifiers()
    {
        return modifiers;
    }

    public void setModifiers(Map<ResourceLocation, Double> modifiers)
    {
        this.modifiers.putAll(modifiers);
    }

    public void setSpell(Holder<Spell> spell)
    {
        this.spell = spell;
    }

    @OnlyIn(Dist.CLIENT)
    public void sendCastRequest() {
        CastRequest resultPacket = new CastRequest(new CompoundTag());
        PacketDistributor.sendToServer(resultPacket);
    }

    public CastContext buildContext() {
        return new CastContext(ResourceLocation.parse(spell.getRegisteredName()), ResourceLocation.parse(element.getRegisteredName()), modifiers);
    }


    public void handleCast(CastContext context, Player player) {
        spell.value().cast(player, context);
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
            Map<ResourceLocation, Double> modifiers = SerializerHelper.deserializeModifiers(tag.getCompound("modifiers"));
            result.modifiers.putAll(modifiers);
        }
        return new SpellContainer();
    }
}
