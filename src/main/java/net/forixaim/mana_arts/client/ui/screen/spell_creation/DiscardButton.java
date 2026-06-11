package net.forixaim.mana_arts.client.ui.screen.spell_creation;

import net.forixaim.mana_arts.ManaArts;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class DiscardButton extends AbstractButton {
    private final PressCallback callback;
    private static final ResourceLocation BUTTON_TEXTURE = ManaArts.identifier("textures/gui/spell_edit/exit.png");
    private static final ResourceLocation BUTTON_TEXTURE_HOVERED = ManaArts.identifier("textures/gui/spell_edit/exit_hovered.png");

    public DiscardButton(int x, int y, PressCallback callback) {
        super(x, y, 18, 18, Component.empty());
        this.callback = callback;
    }

    @Override
    public void onPress() {
        callback.onPress(this);
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (isHoveredOrFocused()) {
            guiGraphics.blit(BUTTON_TEXTURE_HOVERED, this.getX(), this.getY(), 0, 0, width, height, width, height);
        }
        else
        {
            guiGraphics.blit(BUTTON_TEXTURE, this.getX(), this.getY(), 0, 0, width, height, width, height);
        }
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }

    public interface PressCallback
    {
        void onPress(DiscardButton button);
    }
}
