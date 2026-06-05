package net.forixaim.mana_arts.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.forixaim.mana_arts.ManaArts;
import net.forixaim.mana_arts.api.data.element.Element;
import net.forixaim.mana_arts.api.managers.ElementManager;
import net.forixaim.mana_arts.world.entity.spell.Blast;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector4i;

public class BlastRenderer extends SpellProjectileRenderer<Blast>
{
    public BlastRenderer(EntityRendererProvider.Context context)
    {
        super(context);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Blast blast)
    {
        return ManaArts.identifier("textures/entity/blast.png");
    }

    @Override
    public void render(@NotNull Blast pEntity, float entityYaw, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight)
    {
        if (this.shouldOverride(pEntity)) {
            super.render(pEntity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
            return;
        }

        poseStack.pushPose();

        Element element = ElementManager.getElement(pEntity.getCastContext().element()).value();
        Vector4i color = new Vector4i(element.table().getColor());



        if (pEntity.getCastContext() != null) {
            double scale = ElementManager.getElement(pEntity.getCastContext().element()).value().sizeModifier();
            poseStack.scale((float) scale, (float) scale, (float) scale);
        }

        poseStack.translate(0.0D, 0.25D, 0.0D);

        poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());

        PoseStack.Pose pose = poseStack.last();
        VertexConsumer vertexconsumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(getTextureLocation(pEntity)));

        float r = color.x() / 255.0F;
        float g = color.y() / 255.0F;
        float b = color.z() / 255.0F;
        float a = color.w() / 255.0F;

        vertex(vertexconsumer, pose, packedLight, -0.5F, 0, 0, 1, r, g, b, a);
        vertex(vertexconsumer, pose, packedLight, 0.5F, 0, 1, 1, r, g, b, a);
        vertex(vertexconsumer, pose, packedLight, 0.5F, 1, 1, 0, r, g, b, a);
        vertex(vertexconsumer, pose, packedLight, -0.5F, 1, 0, 0, r, g, b, a);

        poseStack.popPose();

        super.render(pEntity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }



    public static void vertex(VertexConsumer consumer, PoseStack.Pose pose, int packedLight, float x, float y, float u, float v, float r, float g, float b, float a) {
        consumer.addVertex(pose, x, y, 0.0F).setColor(r, g, b, a).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(packedLight).setNormal(pose, 0.0F, 1.0F, 0.0F);
    }
}
