package net.forixaim.mana_arts.client.ui.screen.spell_creation;

import net.forixaim.mana_arts.ManaArts;
import net.forixaim.mana_arts.api.data.element.Element;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ElementSelectButton extends AbstractButton {

    private final Holder<Element> element;
    private final ElementSelectCallback onPress;
    private static final ResourceLocation BUTTON_TEXTURE = ManaArts.identifier("textures/gui/spell_screen/spell_slot.png");
    private static final ResourceLocation BUTTON_TEXTURE_PRESSED = ManaArts.identifier("textures/gui/spell_screen/spell_slot_selected.png");
    private static final ResourceLocation BUTTON_TEXTURE_HOVERED = ManaArts.identifier("textures/gui/spell_screen/spell_slot_hovered.png");
    private final int index;
    private boolean selected;

    public ElementSelectButton(int x, int y, Holder<Element> element, ElementSelectCallback onPress, int index) {
        super(x, y, 26, 26, Component.empty());
        this.element = element;
        this.onPress = onPress;
        selected = false;
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setSelected(boolean selected)
    {
        this.selected = selected;
    }

    public Holder<Element> getElement() {
        return element;
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (selected)
        {
            guiGraphics.blit(BUTTON_TEXTURE_PRESSED, getX(), getY(), 0, 0, getWidth(), getHeight(), getWidth(), getHeight());
        }
        else if (isHoveredOrFocused())
        {
            guiGraphics.blit(BUTTON_TEXTURE_HOVERED, getX(), getY(), 0, 0, getWidth(), getHeight(), getWidth(), getHeight());
        }
        else
        {
            guiGraphics.blit(BUTTON_TEXTURE, getX(), getY(), 0, 0, getWidth(), getHeight(), getWidth(), getHeight());
        }
        ResourceLocation texture = element.value().getIconLocation();
        guiGraphics.blit(texture, getX() + 5, getY() + 5, 0, 0, 16, 16, 16, 16);
    }

    @Override
    public void onPress() {
        onPress.onPress(this);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }

    public interface ElementSelectCallback {
        void onPress(ElementSelectButton button);
    }
}
