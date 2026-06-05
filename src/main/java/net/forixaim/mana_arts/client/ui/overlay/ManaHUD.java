package net.forixaim.mana_arts.client.ui.overlay;

import net.forixaim.mana_arts.ManaArts;
import net.forixaim.mana_arts.registry.entries.ManaArtsAttachments;
import net.forixaim.mana_arts.registry.entries.ManaArtsAttributes;
import net.forixaim.mana_arts.world.capabilities.ManaEntity;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class ManaHUD implements LayeredDraw.Layer {
    private static final ResourceLocation CONTAINER = ManaArts.identifier("textures/gui/mana_bar/container.png");
    private static final ResourceLocation BAR = ManaArts.identifier("textures/gui/mana_bar/bar.png");

    @Override
    public void render(GuiGraphics guiGraphics, @NotNull DeltaTracker deltaTracker) {
        Minecraft minecraft = Minecraft.getInstance();
        assert minecraft.player != null;
        ManaEntity mage = minecraft.player.getData(ManaArtsAttachments.MANA_ENTITY);
        double mana = mage.getMana();
        double maxMana = minecraft.player.getAttributeValue(ManaArtsAttributes.MAX_MANA);
        double manaPercentage = mana / maxMana;
        int left = minecraft.gui.leftHeight;

        int screenWidth = minecraft.getWindow().getGuiScaledWidth();
        int screenHeight = minecraft.getWindow().getGuiScaledHeight();

        int x = left + screenWidth / 2 - 140;
        int y = screenHeight - left;

        int barWidth = (int) (manaPercentage * 79);

        guiGraphics.blit(CONTAINER, x, y, 0, 0, 81, 9, 81,9);
        guiGraphics.blit(BAR, x + 1, y, 0, 0, barWidth, 9, 81, 9);
        int manaText = Math.toIntExact(Math.round(mana));
        int maxManaText = Math.toIntExact(Math.round(maxMana));
        String manaString = manaText + "/" + maxManaText;
        guiGraphics.drawCenteredString(minecraft.font, manaString, x + 40, y + 2, 0xFFFFFF);
        minecraft.gui.leftHeight += 10;
    }
}
