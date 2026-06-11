package net.forixaim.mana_arts.client.ui.screen.spell_creation;

import net.forixaim.mana_arts.ManaArts;
import net.forixaim.mana_arts.api.data.spell.Spell;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class SpellSelectButton extends AbstractButton
{
    private final Holder<Spell> spell;
    private final ISpellSelectCallback callback;
    private boolean selected;
    private final int index;
    private static final ResourceLocation BUTTON_TEXTURE = ManaArts.identifier("textures/gui/spell_screen/spell_slot.png");
    private static final ResourceLocation BUTTON_TEXTURE_PRESSED = ManaArts.identifier("textures/gui/spell_screen/spell_slot_selected.png");
    private static final ResourceLocation BUTTON_TEXTURE_HOVERED = ManaArts.identifier("textures/gui/spell_screen/spell_slot_hovered.png");

    public SpellSelectButton(int x, int y, Holder<Spell> spell, ISpellSelectCallback callback, int index)
    {
        super(x, y, 26, 26, Component.empty());
        this.spell = spell;
        this.selected = false;
        this.callback = callback;
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setSelected(boolean selected)
    {
        this.selected = selected;
    }

    public Holder<Spell> getSpell() {
        return spell;
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
        ResourceLocation texture = spell.value().getIconLocation();
        guiGraphics.blit(texture, getX() + 5, getY() + 5, 0, 0, 16, 16, 16, 16);
    }

    @Override
    public void onPress() {
        callback.onSpellSelected(this);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }

    public interface ISpellSelectCallback
    {
        void onSpellSelected(SpellSelectButton spell);
    }
}
