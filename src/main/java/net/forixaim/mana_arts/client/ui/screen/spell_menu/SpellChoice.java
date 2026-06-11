package net.forixaim.mana_arts.client.ui.screen.spell_menu;

import net.forixaim.mana_arts.ManaArts;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class SpellChoice extends AbstractButton
{
    private static final ResourceLocation SLOT_TEXTURE = ManaArts.identifier("textures/gui/spell_screen/modifier.png");
    private final ChoiceType type;
    private final SpellChoiceCallback callback;

    public SpellChoice(int x, int y, ChoiceType type, SpellChoiceCallback callback) {
        super(x, y, 187, 26, Component.empty());
        this.type = type;
        this.callback = callback;
    }

    public ChoiceType getType() {
        return type;
    }

    @Override
    public void onPress() {
        this.callback.onSpellChoiceClicked(this);
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int i, int i1, float v) {
        guiGraphics.blit(SLOT_TEXTURE, getX(), getY(), 0, 0, width, height, 187, 26);
        int xO = width / 2;
        int yO = height / 2;
        int textX = getX() + xO;
        int textY = getY() + yO;
        Component message = getType() == ChoiceType.DELETE ? Component.translatable("text.spell_delete") : Component.translatable("text.spell_edit");
        guiGraphics.drawCenteredString(Minecraft.getInstance().font, message, textX, textY, 0xffffff);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }

    public enum ChoiceType
    {
        EDIT,
        DELETE
    }

    @FunctionalInterface
    public interface SpellChoiceCallback
    {
        void onSpellChoiceClicked(SpellChoice choice);
    }
}
