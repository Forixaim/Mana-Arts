package net.forixaim.mana_arts.client.ui.screen.spell_menu;

import net.forixaim.mana_arts.ManaArts;
import net.forixaim.mana_arts.api.data.spell.SpellModifier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ModifierSlot extends AbstractWidget
{
    private static final ResourceLocation MODIFIER_SLOT_TEXTURE = ManaArts.identifier("textures/gui/spell_screen/modifier.png");
    private final Holder<SpellModifier<?>> modifier;
    private final double currentValue;
    public ModifierSlot(int x, int y, int width, int height, Holder<SpellModifier<?>> modifier, double currentValue) {
        super(x, y, width, height, Component.empty());
        this.modifier = modifier;
        this.currentValue = currentValue;
    }

    private Component getKey()
    {
        ResourceLocation rl = ResourceLocation.parse(modifier.getRegisteredName());
        return Component.translatable("modifier.".concat(rl.getNamespace()).concat(".").concat(rl.getPath()));
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int i, int i1, float v) {
        guiGraphics.blit(MODIFIER_SLOT_TEXTURE, getX(), getY(), 0, 0, width, height, 187, 26);
        guiGraphics.drawString(Minecraft.getInstance().font, getKey(), getX() + 10, getY() + 5, 0xFFFFFF);
        guiGraphics.drawString(Minecraft.getInstance().font, String.format("%.2f", currentValue), getX() + 10, getY() + 15, 0xFFFFFF);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }
}
