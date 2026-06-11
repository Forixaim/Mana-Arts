package net.forixaim.mana_arts.client.ui.screen.spell_creation;

import net.forixaim.mana_arts.api.data.spell.SpellModifier;
import net.forixaim.mana_arts.world.entity.spell.SpellProjectile;
import net.minecraft.client.gui.components.AbstractContainerWidget;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;

public abstract class ModifierSlot extends AbstractContainerWidget
{
    protected SpellEditScreen parent;
    public ModifierSlot(int x, int y, int width, int height, Component message, SpellEditScreen parent) {
        super(x, y, width, height, message);
        this.parent = parent;
    }

    public abstract AbstractWidget getWidget();

    public abstract Holder<SpellModifier<? extends SpellProjectile>> getSpellModifier();
    public abstract double getModifierValue();
}
