package net.forixaim.mana_arts.api.data;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.function.Consumer;

/**
 * A class representing an element.
 */
public class Element
{
    protected ResourceLocation id;
    protected double damageModifier;
    protected double sizeModifier;
    protected double velocityModifier;
    protected Consumer<DamageSource> onHitEffect;

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

    public static class Builder
    {
        protected ResourceLocation parent;
        protected Double damageModifier;
        protected Double sizeModifier;
        protected Double velocityModifier;

        public Builder()
        {
            damageModifier = null;
            sizeModifier = null;
            velocityModifier = null;
            parent = null;
        }

        public void defaultTo()
        {
            if (damageModifier == null)
            {
                damageModifier = 1.0;
            }
            if (sizeModifier == null)
            {
                sizeModifier = 1.0;
            }
            if (velocityModifier == null)
            {
                velocityModifier = 1.0;
            }
        }

        public void merge()
        {
            Deque<Builder> deque = new ArrayDeque<>();
            Builder current = this;
            while (current != null)
            {
                deque.push(this);
            }
        }

        public Element build()
        {
            return new Element();
        }
    }
}
