package net.forixaim.mana_arts.client.ui.screen.spell_menu;

import net.forixaim.mana_arts.ManaArts;
import net.forixaim.mana_arts.api.data.internal.SpellContainer;
import net.forixaim.mana_arts.api.managers.ModifierManager;
import net.forixaim.mana_arts.generated.LangKeys;
import net.forixaim.mana_arts.registry.entries.ManaArtsAttachments;
import net.forixaim.mana_arts.world.capabilities.ManaEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.concurrent.atomic.AtomicInteger;

public class SpellScreen extends Screen
{
    private SpellSlot focusedSlot;


    public SpellScreen() {
        super(Component.translatable(LangKeys.MANA_ARTS_GUI_SPELL_MENU));
    }

    @Override
    protected void init() {
        super.init();
        int leftPos = (this.width - SpellMenuContainer.imageWidth) / 2;
        int topPos = (this.height - SpellMenuContainer.imageHeight) / 2;
        this.addRenderableOnly(new SpellMenuContainer(leftPos, topPos, this.width, this.height, Component.translatable(LangKeys.MANA_ARTS_GUI_SPELL_MENU)));
        if (Minecraft.getInstance().player == null) return;
        ManaEntity mage = Minecraft.getInstance().player.getData(ManaArtsAttachments.MANA_ENTITY);
        AtomicInteger initialTop = new AtomicInteger(topPos + 51);
        final int initialLeft = leftPos + 19;
        mage.getSpells().forEach(
                spellContainer -> {
                    SpellSlot slot = new SpellSlot(initialLeft, initialTop.get(), 26, 26, spellContainer, (container, spellSlot) -> {
                        focusedSlot = spellSlot;
                        spellSlot.select();
                        deselectOthers();
                    });
                    if (mage.getCurrentSpell() == spellContainer)
                    {
                        slot.setEquipped(true);
                    }
                    this.addRenderableWidget(slot);
                    initialTop.addAndGet(26);
                }
        );
        this.addRenderableWidget(new SpellSlot(initialLeft, initialTop.get(), 26, 26, null, (container, spellSlot) ->
                ManaArts.LOGGER.debug("Not implemented yet"))
        );
    }

    private void deselectOthers()
    {
        for (var widget : this.renderables)
        {
            if (widget instanceof SpellSlot slot)
            {
                slot.deselect();
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.focusedSlot != null)
        {
            renderModifiers(focusedSlot.getSpell());
        }
    }

    private void renderModifiers(SpellContainer spellContainer)
    {
        int leftPos = (this.width - SpellMenuContainer.imageWidth) / 2;
        int topPos = (this.height - SpellMenuContainer.imageHeight) / 2;
        int x = leftPos + 48;
        AtomicInteger y = new AtomicInteger(topPos + 51);
        this.addRenderableWidget(new SpellTypeModifier(x, y.get(), 189, 26, spellContainer.getSpell()));
        y.addAndGet(26);
        this.addRenderableWidget(new ElementTypeModifier(x, y.get(), 189, 26, spellContainer.getElement()));
        spellContainer.getModifiers().forEach((modifierLocation, value) -> {
            this.addRenderableWidget(new ModifierSlot(x, y.get(), 189, 26, ModifierManager.getModifier(modifierLocation), value));
            y.addAndGet(26);
        });
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
