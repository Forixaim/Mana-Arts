package net.forixaim.mana_arts.client.ui.screen.spell_creation;

import net.forixaim.mana_arts.ManaArts;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class SpellEditContainer extends AbstractWidget {
    //This is Fixed size and position
    private static final ResourceLocation SPELL_EDIT_TEXTURE = ManaArts.identifier("textures/gui/spell_edit/container.png");
    public SpellEditContainer(int x, int y, int width, int height) {
        super(x, y, width, height, Component.empty());
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        guiGraphics.blit(SPELL_EDIT_TEXTURE, getX(), getY(), 0, 0, 256, 256, 256, 256);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }
}
