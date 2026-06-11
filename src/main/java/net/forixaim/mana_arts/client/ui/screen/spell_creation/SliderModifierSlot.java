package net.forixaim.mana_arts.client.ui.screen.spell_creation;

import net.forixaim.mana_arts.ManaArts;
import net.forixaim.mana_arts.api.data.spell.SpellModifier;
import net.forixaim.mana_arts.world.entity.spell.SpellProjectile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SliderModifierSlot extends ModifierSlot
{
    private static final ResourceLocation MODIFIER_SLOT_TEXTURE = ManaArts.identifier("textures/gui/spell_edit/slider_modifier.png");
    private final Holder<SpellModifier<?>> modifier;
    private final List<GuiEventListener> children = new ArrayList<>();
    private final ModifierSlider slider;

    public SliderModifierSlot(int x, int y, Holder<SpellModifier<? extends SpellProjectile>> modifier, double currentValue, SpellEditScreen parent) {
        super(x, y, 184, 39, Component.empty(), parent);
        this.modifier = modifier;
        int sliderX = this.getX() + 3;
        int sliderY = this.getY() + 26;
        this.slider = new ModifierSlider(sliderX, sliderY, modifier.value().minValue(), modifier.value().maxValue(), currentValue, this);
    }

    public ModifierSlider getWidget() {
        return slider;
    }

    private Component getKey()
    {
        ResourceLocation rl = ResourceLocation.parse(modifier.getRegisteredName());
        return Component.translatable("modifier.".concat(rl.getNamespace()).concat(".").concat(rl.getPath()));
    }

    void updateModifier(double value) {
        this.parent.updateModifier(modifier, value);
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        guiGraphics.blit(MODIFIER_SLOT_TEXTURE, getX(), getY(), 0, 0, width, height, 184, 39);
        String fullText = getKey().getString();
        guiGraphics.drawString(Minecraft.getInstance().font, fullText, getX() + 10, getY() + 5, 0xFFFFFF);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }

    @Override
    public Holder<SpellModifier<? extends SpellProjectile>> getSpellModifier() {
        return modifier;
    }

    @Override
    public double getModifierValue() {
        return slider.getActualValue();
    }

    @Override
    public @NotNull List<? extends GuiEventListener> children() {
        return children;
    }
}
