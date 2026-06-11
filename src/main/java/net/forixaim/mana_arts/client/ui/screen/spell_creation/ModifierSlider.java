package net.forixaim.mana_arts.client.ui.screen.spell_creation;

import net.forixaim.mana_arts.ManaArts;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.Component;

public class ModifierSlider extends AbstractSliderButton {
    private final double minValue;
    private final double maxValue;
    private final SliderModifierSlot parent;
    public ModifierSlider(int x, int y, double minValue, double maxValue, double currentValue, SliderModifierSlot parent) {
        super(x, y, 178, 10, Component.empty(), (currentValue - minValue) / (maxValue - minValue));
        ManaArts.LOGGER.debug("Creating ModifierSlider with minValue: {}, maxValue: {}, currentValue: {}", minValue, maxValue, currentValue);
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.parent = parent;
        this.updateMessage();
    }
    public double getActualValue() {
        return this.minValue + (this.value * (this.maxValue - this.minValue));
    }
    @Override
    protected void updateMessage() {
        this.setMessage(Component.literal(String.format("%.2f", this.getActualValue())));
    }

    @Override
    protected void applyValue() {
        parent.updateModifier(getActualValue());
    }
}
