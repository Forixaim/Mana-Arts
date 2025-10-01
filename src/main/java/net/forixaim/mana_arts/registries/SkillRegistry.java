package net.forixaim.mana_arts.registries;

import net.forixaim.bs_api.battle_arts_skills.BattleArtsSkillCategories;
import net.forixaim.mana_arts.ManaArts;
import net.forixaim.mana_arts.skills.ManaCharge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.forgeevent.SkillBuildEvent;
import yesman.epicfight.skill.Skill;

@Mod.EventBusSubscriber(modid = ManaArts.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SkillRegistry
{
    public static Skill MANA_CHARGE;

    @SubscribeEvent
    public static void onBuild(SkillBuildEvent event)
    {
        SkillBuildEvent.ModRegistryWorker worker = event.createRegistryWorker(ManaArts.MODID);

        MANA_CHARGE = worker.build("mana_charge", ManaCharge::new, Skill.createBuilder().setCategory(BattleArtsSkillCategories.MANA_ART).setActivateType(Skill.ActivateType.HELD));
    }
}
