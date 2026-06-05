package net.forixaim.mana_arts.registry.entries;

import net.forixaim.mana_arts.ManaArts;
import net.forixaim.mana_arts.skills.NaturalAttunementSkill;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import yesman.epicfight.registry.EpicFightRegistries;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.passive.PassiveSkill;

public final class ManaArtsSkills
{
    private ManaArtsSkills() {}
    public static final DeferredRegister<Skill> REGISTRY = DeferredRegister.create(EpicFightRegistries.SKILL, ManaArts.MOD_ID);
    public static final DeferredHolder<Skill, NaturalAttunementSkill> NATURAL_ATTUNEMENT = REGISTRY.register("natural_attunement", rl -> PassiveSkill.createPassiveBuilder(NaturalAttunementSkill::new).build(rl));
}
