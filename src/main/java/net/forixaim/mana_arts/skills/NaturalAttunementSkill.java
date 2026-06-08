package net.forixaim.mana_arts.skills;

import net.forixaim.mana_arts.registry.entries.ManaArtsAttachments;
import net.forixaim.mana_arts.world.ManaSource;
import net.forixaim.mana_arts.world.ManaSourceTags;
import net.minecraft.nbt.CompoundTag;
import yesman.epicfight.skill.SkillBuilder;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.passive.PassiveSkill;

public class NaturalAttunementSkill extends PassiveSkill
{
    private float manaRegenRate = 0;
    public NaturalAttunementSkill(SkillBuilder<? extends SkillBuilder> builder) {
        super(builder);
    }

    @Override
    public void loadDatapackParameters(CompoundTag parameters) {
        super.loadDatapackParameters(parameters);
        manaRegenRate = parameters.getFloat("manaRegenRate");
    }

    @Override
    public void updateContainer(SkillContainer container) {
        super.updateContainer(container);
        container.getExecutor().getOriginal().getData(ManaArtsAttachments.MANA_ENTITY).modifyMana(container.getExecutor().getOriginal(), new ManaSource(manaRegenRate, ManaSourceTags.PASSIVE_REGENERATION));
    }
}
