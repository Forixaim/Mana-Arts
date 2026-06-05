package net.forixaim.mana_arts.client.ui.screen.spell_menu;

import net.forixaim.mana_arts.ManaArts;
import net.forixaim.mana_arts.api.data.element.Element;
import net.forixaim.mana_arts.api.data.spell.Spell;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class ElementTypeModifier extends AbstractWidget {
    private static final ResourceLocation SPELL_TYPE_TEXTURE = ManaArts.identifier("textures/gui/spell_screen/spell.png");
    private static final int WIDTH = 189;
    private static final int HEIGHT = 26;
    private final Holder<Element> spellHolder;
    public ElementTypeModifier(int x, int y, int width, int height, Holder<Element> spellHolder) {
        super(x, y, width, height, Component.empty());
        this.spellHolder = spellHolder;
    }

    @Override
    protected void renderWidget(@NotNull GuiGraphics guiGraphics, int i, int i1, float v) {
        guiGraphics.blit(SPELL_TYPE_TEXTURE, getX(), getY(), 0, 0, WIDTH, HEIGHT, 189, 26);
        guiGraphics.blit(spellHolder.value().getIconLocation(), getX() + 5, getY() + 5, 0, 0, 16, 16, 16, 16);
    }

    @Override
    protected void updateWidgetNarration(@NotNull NarrationElementOutput narrationElementOutput) {

    }
}
