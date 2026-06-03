package net.forixaim.mana_arts.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.forixaim.mana_arts.api.managers.ElementManager;
import net.forixaim.mana_arts.world.entity.spell.Blast;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class BlastRenderer extends EntityRenderer<Blast> {
    protected BlastRenderer(EntityRendererProvider.Context context) {
        super(context);
    }



    @Override
    public ResourceLocation getTextureLocation(Blast blast) {
        return null;
    }

    @Override
    public void render(@NotNull Blast pEntity, float entityYaw, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        if (pEntity.getCastContext() != null)
        {
            double scale = ElementManager.getElement(pEntity.getCastContext().element()).value().sizeModifier();
            poseStack.scale((float) scale, (float) scale, (float) scale);
        }
        poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        PoseStack.Pose pose = poseStack.last();
        VertexConsumer vertexconsumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(getTextureLocation(pEntity)));
        vertex(vertexconsumer, pose, packedLight, 0.0F, 0, 0, 1);
        vertex(vertexconsumer, pose, packedLight, 1.0F, 0, 1, 1);
        vertex(vertexconsumer, pose, packedLight, 1.0F, 1, 1, 0);
        vertex(vertexconsumer, pose, packedLight, 0.0F, 1, 0, 0);
        poseStack.popPose();
        super.render(pEntity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

    private static void vertex(VertexConsumer consumer, PoseStack.Pose pose, int packedLight, float x, int y, int u, int v) {
        consumer.addVertex(pose, x - 0.5F, (float)y - 0.25F, 0.0F).setColor(-1).setUv((float)u, (float)v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(packedLight).setNormal(pose, 0.0F, 1.0F, 0.0F);
    }
}
