package net.forixaim.mana_arts.client.ui.screen.spell_menu;

import com.google.common.collect.Lists;
import net.forixaim.mana_arts.api.data.internal.SpellContainer;
import net.forixaim.mana_arts.api.managers.ModifierManager;
import net.forixaim.mana_arts.generated.LangKeys;
import net.forixaim.mana_arts.netcode.client.SpellModificationRequest;
import net.forixaim.mana_arts.registry.entries.ManaArtsAttachments;
import net.forixaim.mana_arts.world.capabilities.ManaEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class SpellScreen extends Screen
{
    private int focusedSlot;
    int leftPos;
    int topPos;
    int initialTop;
    int initialLeft;
    ManaEntity mage;
    private final List<SpellContainer> spellSlots = Lists.newArrayList();
    private final List<SpellSlot> visibleSlots = Lists.newArrayList();
    public SpellScreen() {
        super(Component.translatable(LangKeys.MANA_ARTS_GUI_SPELL_MENU));
    }

    @Override
    protected void init() {
        super.init();
        leftPos = (this.width - SpellMenuContainer.imageWidth) / 2;
        topPos = (this.height - SpellMenuContainer.imageHeight) / 2;
        initialTop = topPos + 51;
        initialLeft = leftPos + 19;
        focusedSlot = -1;
        mage = Minecraft.getInstance().player != null ? Minecraft.getInstance().player.getData(ManaArtsAttachments.MANA_ENTITY) : null;
        refreshBackground();
        refreshSpells();
        refreshVisibleSlots();
        renderSpellSlots();
    }

    public void sync()
    {
        this.clearWidgets();
        refreshBackground();
        refreshSpells();
        refreshVisibleSlots();
        renderSpellSlots();
    }

    private void refreshBackground()
    {
        this.addRenderableOnly(new SpellMenuContainer(leftPos, topPos, this.width, this.height, Component.translatable(LangKeys.MANA_ARTS_GUI_SPELL_MENU)));
    }

    private void renderSpellSlots()
    {
        for (SpellSlot slot : visibleSlots)
        {
            this.addRenderableWidget(slot);
        }
    }

    private void refreshVisibleSlots()
    {
        this.visibleSlots.clear();
        for (int i = 0; i < spellSlots.size(); i++)
        {
            final int slotIndex = i;
            SpellSlot resultingSlot = new SpellSlot(initialLeft, initialTop + 26 * i, 26, 26, spellSlots.get(i), (clickedSpell, clickedSlot) -> {
                this.focusedSlot = slotIndex;
                deselectOthers();
                clickedSlot.select();
            });
            if (mage.getCurrentSpellIndex() == i)
            {
                resultingSlot.setEquipped(true);
            }
            visibleSlots.add(resultingSlot);
            if (visibleSlots.size() == 6)
            {
                break;
            }

        }
        if (focusedSlot != -1)
        {
            visibleSlots.get(focusedSlot % 6).select();
        }
        if (visibleSlots.size() < 6)
        {
            visibleSlots.add(new SpellSlot(initialLeft, initialTop + 26 * visibleSlots.size(), 26, 26, null, (clickedSpell, clickedSlot) -> {
                onAdd();
            }));
        }
    }

    private void refreshSpells()
    {
        if (Minecraft.getInstance().player == null) return;
        ManaEntity mage = Minecraft.getInstance().player.getData(ManaArtsAttachments.MANA_ENTITY);
        spellSlots.clear();
        spellSlots.addAll(mage.getSpells());
    }

    private void onAdd()
    {
        SpellModificationRequest packet = new SpellModificationRequest(SpellModificationRequest.ModifierType.ADD, new CompoundTag());
        PacketDistributor.sendToServer(packet);
    }

    @Override
    public void onClose() {
        super.onClose();
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
        if (this.focusedSlot != -1)
        {
            renderModifiers(spellSlots.get(this.focusedSlot));
        }
    }

    private void renderModifiers(SpellContainer spellContainer)
    {
        int leftPos = (this.width - SpellMenuContainer.imageWidth) / 2;
        int topPos = (this.height - SpellMenuContainer.imageHeight) / 2;
        int x = leftPos + 50;
        AtomicInteger y = new AtomicInteger(topPos + 51);
        this.addRenderableWidget(new SpellTypeModifier(x, y.get(), 189, 26, spellContainer.getSpell()));
        y.addAndGet(26);
        this.addRenderableWidget(new ElementTypeModifier(x, y.get(), 189, 26, spellContainer.getElement()));
        y.addAndGet(26);
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
