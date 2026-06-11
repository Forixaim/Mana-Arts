package net.forixaim.mana_arts.registry.entries;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.forixaim.mana_arts.ManaArts;
import net.forixaim.mana_arts.api.data.element.Element;
import net.forixaim.mana_arts.api.data.element.ParticleEffects;
import net.forixaim.mana_arts.api.managers.ElementManager;
import net.forixaim.mana_arts.registry.registers.ElementRegister;
import net.forixaim.mana_arts.registry.registers.holders.DeferredElement;
import net.forixaim.mana_arts.world.ParticleUtil;
import net.forixaim.mana_arts.world.entity.spell.Blast;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import org.joml.Vector4i;
import yesman.epicfight.api.utils.LevelUtil;

import static net.forixaim.mana_arts.client.renderer.BlastRenderer.vertex;

public class ManaArtsElements
{
    public static final ElementRegister REGISTRY = ElementRegister.create(ManaArts.MOD_ID);

    public static final DeferredElement FIRE = REGISTRY.registerElement("fire", rl -> Element.builder()
            .setBasicAttributes(0.8, 1, 1)
            .modifyEffectTable(table -> {
                table.getColor().set(255, 122, 0, 0);
            })
            .build(rl));

    public static final DeferredElement LIGHT = REGISTRY.registerElement("light", rl -> Element.builder()
            .setBasicAttributes(0.6, 0.7, 1.6)
            .modifyEffectTable(table -> {
                table.getColor().set(255, 243, 173, 0);
                table.getEffects().put(ManaArtsSpells.BLAST,
                        new ParticleEffects(
                                (a, b) -> {
                                    if (b instanceof ServerLevel serverLevel)
                                    {
                                        ParticleUtil.sendAlwaysVisibleParticles(serverLevel, ManaArtsParticles.LIGHT_GLIMMER.get(), a.x(), a.y(), a.z(), 1, 0, 0, 0, 0);
                                        serverLevel.playSound(null, a.x(), a.y(), a.z(), ManaArtsSounds.LIGHT_SHOOT.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
                                    }
                                },
                                (a, b) -> {
                                    if (b instanceof ServerLevel serverLevel)
                                    {
                                        ParticleUtil.sendAlwaysVisibleParticles(serverLevel, ManaArtsParticles.LIGHT_GLIMMER.get(), a.x(), a.y(), a.z(), 1, 0, 0, 0, 0);
                                    }
                                },
                                (a, b) -> {},
                                (a, b) -> {},
                                (a, b) -> {
                                    if (b instanceof ServerLevel serverLevel)
                                    {
                                        ParticleUtil.sendAlwaysVisibleParticles(serverLevel, ManaArtsParticles.EXPLOSION.get(), a.x(), a.y(), a.z(), 1, 0, 0, 0, 0);
                                        serverLevel.playSound(null, a.x(), a.y(), a.z(), ManaArtsSounds.LIGHT_EXPLOSION.get(), SoundSource.PLAYERS, 0.7F, 1.0F);
                                    }
                                }));
                table.getProjectileRenderOverrides().put(
                        ManaArtsEntities.BLAST.get(), (pEntity, entityYaw, partialTick, poseStack, bufferSource, packedLight, renderer) ->
                        {
                            poseStack.pushPose();
                            Vector4i color = new Vector4i(255,255,255,0);
                            poseStack.translate(0.0D, 0.5D, 0.0D);
                            if (pEntity.getCastContext() != null) {
                                double scale = ElementManager.getElement(pEntity.getCastContext().element()).value().sizeModifier() * 4;
                                poseStack.scale((float) scale, (float) scale, (float) scale);
                            }

                            poseStack.mulPose(renderer.entityRenderDispatcher.cameraOrientation());
                            if (pEntity instanceof Blast blast && blast.getLifetime() % 4 == 0)
                            {
                                float randomAngle = blast.getRandom().nextFloat() * 360;
                                poseStack.mulPose(com.mojang.math.Axis.ZP.rotationDegrees(randomAngle));
                            }


                            PoseStack.Pose pose = poseStack.last();
                            VertexConsumer vertexconsumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(ManaArts.identifier("textures/spells/light/blast.png")));

                            float r = color.x() / 255.0F;
                            float g = color.y() / 255.0F;
                            float b = color.z() / 255.0F;
                            float a = color.w() / 255.0F;

                            vertex(vertexconsumer, pose, packedLight, -0.5F, -0.5f, 0, 1, r, g, b, a);
                            vertex(vertexconsumer, pose, packedLight, 0.5F, -0.5f, 1, 1, r, g, b, a);
                            vertex(vertexconsumer, pose, packedLight, 0.5F, 0.5f, 1, 0, r, g, b, a);
                            vertex(vertexconsumer, pose, packedLight, -0.5F, 0.5f, 0, 0, r, g, b, a);

                            poseStack.popPose();
                        }
                );
            }).build(rl));
}
