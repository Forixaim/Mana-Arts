package net.forixaim.mana_arts.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.forixaim.mana_arts.api.data.element.Element;
import net.forixaim.mana_arts.api.managers.ElementManager;
import net.forixaim.mana_arts.world.entity.spell.SpellProjectile;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.Holder;
import org.jetbrains.annotations.NotNull;

public abstract class SpellProjectileRenderer<T extends SpellProjectile> extends EntityRenderer<T>
{
    protected SpellProjectileRenderer(EntityRendererProvider.Context context)
    {
        super(context);
    }

    public boolean shouldOverride(T pEntity)
    {
        if (pEntity.getCastContext() == null) return false;
        if (pEntity.getCastContext().element() != null)
        {
            Holder<Element> element = ElementManager.getElement(pEntity.getCastContext().element());
            return element.value().table().getProjectileRenderOverrides().containsKey(pEntity.getType());
        }
        return false;
    }

    @Override
    public void render(T pEntity, float entityYaw, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight)
    {
        if (pEntity.getCastContext() != null && pEntity.getCastContext().element() != null)
        {
            Holder<Element> element = ElementManager.getElement(pEntity.getCastContext().element());
            if (element.value().table().getProjectileRenderOverrides().containsKey(pEntity.getType()))
            {
                element.value().table().getProjectileRenderOverrides().get(pEntity.getType()).accept(pEntity, entityYaw, partialTick, poseStack, bufferSource, packedLight, this);
            }
        }
    }
}
