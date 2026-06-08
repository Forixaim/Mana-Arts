package net.forixaim.mana_arts.client.ui.screen.spell_menu;

import net.forixaim.mana_arts.ManaArts;
import net.forixaim.mana_arts.api.data.internal.SpellContainer;
import net.forixaim.mana_arts.api.data.spell.Spell;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class SpellSlot extends AbstractButton
{
    private boolean selected = false;
    private boolean equipped = false;
    private boolean hidden = false;
    private final SpellContainer spellHolder;
    private final OnSlotSelected pressCallback;
    private static final int WIDTH = 26;
    private static final int HEIGHT = 26;
    private static final ResourceLocation BUTTON_TEXTURE = ManaArts.identifier("textures/gui/spell_screen/spell_slot.png");
    private static final ResourceLocation BUTTON_TEXTURE_PRESSED = ManaArts.identifier("textures/gui/spell_screen/spell_slot_selected.png");
    private static final ResourceLocation BUTTON_TEXTURE_HOVERED = ManaArts.identifier("textures/gui/spell_screen/spell_slot_hovered.png");
    private static final ResourceLocation ADD_ICON = ManaArts.identifier("textures/gui/spell_screen/add.png");
    public static final ResourceLocation EQUIPPED_BORDER = ManaArts.identifier("textures/gui/spell_screen/equipped_border.png");

    public SpellSlot(int x, int y, int width, int height, SpellContainer initialSpell, OnSlotSelected pressCallback) {
        super(x, y, width, height, initialSpell != null ? initialSpell.getSpell().value().getTranslatedName() : Component.translatable("text.mana_arts.add_spell"));
        this.spellHolder = initialSpell;
        this.pressCallback = pressCallback;
    }

    public SpellContainer getSpell() {
        return spellHolder;
    }


    public void setSpell(Holder<Spell> newSpellHolder) {
        this.spellHolder.setSpell(newSpellHolder);
        this.setMessage(newSpellHolder.value().getTranslatedName());
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public void setEquipped(boolean equipped) {
        this.equipped = equipped;
    }

    @Override
    public void onPress() {
        pressCallback.onSelected(this.spellHolder, this);
    }

    public void select()
    {
        selected = true;
    }

    public void deselect()
    {
        selected = false;
    }

    @Override
    protected void renderWidget(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        ResourceLocation icon = spellHolder != null ? spellHolder.getSpell().value().getIconLocation() : ADD_ICON;
        if (selected)
        {
            guiGraphics.blit(BUTTON_TEXTURE_PRESSED, getX(), getY(), 0, 0, WIDTH, HEIGHT, 26, 26);
        }
        else if (isHoveredOrFocused())
        {
            Component str = spellHolder != null ? Component.translatable(spellHolder.getElement().value().getTranslationKey()).append(" ").append(spellHolder.getSpell().value().getTranslatedName()): Component.translatable("text.mana_arts.add_spell");
            guiGraphics.drawStringWithBackdrop(Minecraft.getInstance().font, str, mouseX, mouseY, 0, 0xFFFFFF);
            guiGraphics.blit(BUTTON_TEXTURE_HOVERED, getX(), getY(), 0, 0, WIDTH, HEIGHT, 26, 26);
        }
        else
        {
            guiGraphics.blit(BUTTON_TEXTURE, getX(), getY(), 0, 0, WIDTH, HEIGHT, 26, 26);
        }
        int centerX = getX() + 5;
        int centerY = getY() + 5;
        guiGraphics.blit(icon, centerX, centerY, 0, 0, 16, 16, 16, 16);
        if (equipped)
        {
            guiGraphics.blit(EQUIPPED_BORDER, getX(), getY(), 0, 0, WIDTH, HEIGHT, 26, 26);
        }
    }


    @Override
    protected void updateWidgetNarration(@NotNull NarrationElementOutput narrationElementOutput) {

    }

    @FunctionalInterface
    public interface OnSlotSelected {
        void onSelected(SpellContainer clickedSpell, SpellSlot clickedSlot);
    }
}
