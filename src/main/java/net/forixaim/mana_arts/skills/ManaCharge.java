package net.forixaim.mana_arts.skills;

import com.mojang.logging.LogUtils;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.gui.overlays.ManaBarOverlay;
import net.forixaim.bs_api.animations.ManaArtsAnimations;
import net.forixaim.bs_api.battle_arts_skills.mana_arts.ManaArt;
import net.forixaim.bs_api.client.KeyBinds;
import net.forixaim.mana_arts.client.CameraEngine;
import net.minecraft.client.KeyMapping;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraftforge.fml.ModList;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillBuilder;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.modules.HoldableSkill;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

import java.util.UUID;

public class ManaCharge extends ManaArt implements HoldableSkill
{
    private static final UUID EVENT_UUID = UUID.fromString("9e069b17-fff4-4ae0-a7ae-79ec84a667dc");


    public ManaCharge(SkillBuilder<? extends Skill> builder)
    {
        super(builder);
    }

    @Override
    public void onInitiate(SkillContainer container)
    {
        super.onInitiate(container);

        container.getExecutor().getEventListener().addEventListener(PlayerEventListener.EventType.MOVEMENT_INPUT_EVENT, EVENT_UUID, event -> {
            if (event.getPlayerPatch().isHoldingSkill(this))
            {
                event.getMovementInput().forwardImpulse = 0;
                event.getMovementInput().leftImpulse = 0;
                event.getMovementInput().jumping = false;
                event.getMovementInput().shiftKeyDown = false;
            }
        });
    }

    @Override
    public void onRemoved(SkillContainer container)
    {
        container.getExecutor().getEventListener().removeListener(PlayerEventListener.EventType.MOVEMENT_INPUT_EVENT, EVENT_UUID);
        super.onRemoved(container);
    }

    @Override
    public void holdTick(SkillContainer caster)
    {
        if (caster.getExecutor().isLogicalClient())
        {
            CameraEngine.getInstance().shakeCamera(1f);
        }
        HoldableSkill.super.holdTick(caster);
        caster.getExecutor().getOriginal().level().addParticle(ParticleTypes.SOUL_FIRE_FLAME, caster.getExecutor().getOriginal().getX() + ((caster.getExecutor().getOriginal().getRandom().nextDouble() * 2) - 1) * 0.5, caster.getExecutor().getOriginal().getY(), caster.getExecutor().getOriginal().getZ() + ((caster.getExecutor().getOriginal().getRandom().nextDouble() * 2) - 1) * 0.5, ((caster.getExecutor().getOriginal().getRandom().nextDouble() * 2) - 1) * 0.1, 0.1, ((caster.getExecutor().getOriginal().getRandom().nextDouble() * 2) - 1) * 0.1);
        if (ModList.get().isLoaded(IronsSpellbooks.MODID))
        {
            try
            {
                if (MagicData.getPlayerMagicData(caster.getExecutor().getOriginal()).getMana() < caster.getExecutor().getOriginal().getAttributeValue(AttributeRegistry.MAX_MANA.get()))
                {
                    int manaCharge = (int) (caster.getExecutor().getOriginal().getAttributeValue(AttributeRegistry.MAX_MANA.get()) / 200);
                    if (manaCharge == 0)
                    {
                        manaCharge += 2;
                    }

                    MagicData.getPlayerMagicData(caster.getExecutor().getOriginal()).addMana(manaCharge);
                }

            }
            catch (Exception e)
            {
                LogUtils.getLogger().warn(e.getMessage());
            }

        }
    }

    @Override
    public void startHolding(SkillContainer container)
    {
        container.getExecutor().getEntityState().setState(EntityState.MOVEMENT_LOCKED, true);
        container.getExecutor().playAnimationSynchronized(ManaArtsAnimations.CHARGE_1, 0);
    }

    @Override
    public void onStopHolding(SkillContainer skillContainer)
    {
        skillContainer.getExecutor().stopPlaying(ManaArtsAnimations.CHARGE_1);
        skillContainer.getExecutor().getAnimator().stopPlaying(ManaArtsAnimations.CHARGE_1);
        skillContainer.getExecutor().playAnimationSynchronized(ManaArtsAnimations.CHARGE_RELEASE_1, 0);
        skillContainer.getExecutor().getEntityState().setState(EntityState.MOVEMENT_LOCKED, false);
        if (!skillContainer.getExecutor().isLogicalClient()) {
            this.cancelOnServer(skillContainer, null);
        }
        else
        {
            this.cancelOnClient(skillContainer, null);
        }
        HoldableSkill.super.onStopHolding(skillContainer);
    }

    @Override
    public KeyMapping getKeyMapping()
    {
        return KeyBinds.USE_MANA_ART;
    }
}
