package net.forixaim.mana_arts.api.data;

import net.forixaim.mana_arts.api.managers.ElementManager;
import net.minecraft.core.Holder;
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
public record Element(ResourceLocation id, double damageModifier, double sizeModifier, double velocityModifier, Consumer<DamageSource> onHitEffect)
{
    public static final Consumer<DamageSource> DEFAULT_ON_HIT_EFFECT = damageSource -> {};

    public Element(Builder builder, ResourceLocation id) {
        this(id, builder.damageModifier, builder.sizeModifier, builder.velocityModifier, builder.onHitEffect);
    }

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

    public static Builder builder()
    {
        return new Builder();
    }

    public static class Builder
    {
        protected ResourceLocation parent;
        protected Double damageModifier;
        protected Double sizeModifier;
        protected Double velocityModifier;
        protected Consumer<DamageSource> onHitEffect;

        Builder()
        {
            damageModifier = null;
            sizeModifier = null;
            velocityModifier = null;
            parent = null;
            onHitEffect = null;
        }

        public Builder setBasicAttributes(double damageModifier, double sizeModifier, double velocityModifier)
        {
            this.damageModifier = damageModifier;
            this.sizeModifier = sizeModifier;
            this.velocityModifier = velocityModifier;
            return this;
        }

        public Builder setOnHitEffect(Consumer<DamageSource> onHitEffect)
        {
            this.onHitEffect = onHitEffect;
            return this;
        }

        public Builder parent(ResourceLocation parent)
        {
            this.parent = parent;
            return this;
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
            if (onHitEffect == null)
            {
                onHitEffect = DEFAULT_ON_HIT_EFFECT;
            }
        }

        public Builder merge()
        {
            Deque<Builder> deque = new ArrayDeque<>();
            Builder result = this;
            Holder<Builder> current = Holder.direct(this);
            while (current != null)
            {
                deque.push(current.value());
                current = ElementManager.getElement(current.value().parent);
            }
            while (!deque.isEmpty())
            {
                Builder builder = deque.pop();
                if (builder.damageModifier != null)
                {
                    result.damageModifier = builder.damageModifier;
                }
                if (builder.sizeModifier != null)
                {
                    result.sizeModifier = builder.sizeModifier;
                }
                if (builder.velocityModifier != null)
                {
                    result.velocityModifier = builder.velocityModifier;
                }
            }
            result.defaultTo();
            return result;
        }

        public Element build(ResourceLocation id)
        {
            return new Element(merge(), id);
        }
    }
}
