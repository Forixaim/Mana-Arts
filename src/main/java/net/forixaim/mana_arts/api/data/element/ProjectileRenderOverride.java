package net.forixaim.mana_arts.api.data.element;

import com.mojang.blaze3d.vertex.PoseStack;
import net.forixaim.mana_arts.client.renderer.SpellProjectileRenderer;
import net.forixaim.mana_arts.world.entity.spell.SpellProjectile;
import net.minecraft.client.renderer.MultiBufferSource;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface ProjectileRenderOverride
{
    void accept(@NotNull SpellProjectile pEntity, float entityYaw, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight, SpellProjectileRenderer<? extends SpellProjectile> renderer);
}
