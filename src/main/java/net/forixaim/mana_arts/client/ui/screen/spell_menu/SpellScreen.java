package net.forixaim.mana_arts.client.ui.screen.spell_menu;

import com.google.common.collect.Lists;
import net.forixaim.mana_arts.api.data.internal.SpellContainer;
import net.forixaim.mana_arts.client.ui.screen.spell_creation.SpellEditScreen;
import net.forixaim.mana_arts.generated.LangKeys;
import net.forixaim.mana_arts.netcode.client.SpellModificationRequest;
import net.forixaim.mana_arts.registry.entries.ManaArtsAttachments;
import net.forixaim.mana_arts.world.capabilities.ManaEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SpellScreen extends Screen
{
    private int focusedSlot;
    int leftPos;
    int topPos;
    int upArrowX;
    int upArrowY;
    int downArrowX;
    int downArrowY;
    int page;
    int initialTop;
    int initialLeft;
    ManaEntity mage;
    private final List<SpellContainer> spellSlots = Lists.newArrayList();
    private final List<SpellSlot> visibleSlots = Lists.newArrayList();
    public SpellScreen() {
        super(Component.translatable(LangKeys.MANA_ARTS_GUI_SPELL_MENU));
    }
    SpellChoice edit;
    SpellChoice delete;



    @Override
    protected void init() {
        super.init();
        leftPos = (this.width - SpellMenuContainer.imageWidth) / 2;
        topPos = (this.height - SpellMenuContainer.imageHeight) / 2;
        page = 0;
        initialTop = topPos + 51;
        initialLeft = leftPos + 19;
        upArrowX = leftPos + 22;
        upArrowY = leftPos + 24;
        focusedSlot = -1;
        mage = Minecraft.getInstance().player != null ? Minecraft.getInstance().player.getData(ManaArtsAttachments.MANA_ENTITY) : null;
        int x = leftPos + 50;
        edit = new SpellChoice(x, initialTop, SpellChoice.ChoiceType.EDIT, onClick -> {
            if (focusedSlot != -1 && mage != null)
            {
                Minecraft.getInstance().setScreen(new SpellEditScreen(mage, focusedSlot));
            }
        });
        delete = new SpellChoice(x, initialTop + 26, SpellChoice.ChoiceType.DELETE, onClick -> {
            ManaEntity mage = Minecraft.getInstance().player != null ? Minecraft.getInstance().player.getData(ManaArtsAttachments.MANA_ENTITY) : null;
            if (mage == null) return;
            if (focusedSlot == -1) return;
            CompoundTag tag = new CompoundTag();
            if (mage.getSpells().contains(visibleSlots.get(focusedSlot).getSpell()))
            {
                tag.putInt("slot", mage.getSpells().indexOf(visibleSlots.get(focusedSlot).getSpell()));
                SpellModificationRequest removal = new SpellModificationRequest(SpellModificationRequest.ModifierType.REMOVE, tag);
                PacketDistributor.sendToServer(removal);
                focusedSlot = -1;
                this.removeWidget(edit);
                this.removeWidget(delete);
            }
        });
        refreshBackground();
        refreshSpells();
        refreshVisibleSlots();
        renderSpellSlots();
    }

    private void renderChoices()
    {
        if (focusedSlot == -1)
        {
            return;
        }
        this.addRenderableWidget(edit);
        this.addRenderableWidget(delete);
    }

    public void sync()
    {
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

    private void scroll()
    {
        this.focusedSlot = -1;
    }

    private void renderScrollButtons()
    {

    }

    private boolean shouldRenderScrollButtons()
    {
        return spellSlots.size() > 6;
    }

    private void refreshVisibleSlots()
    {
        for (SpellSlot slot : visibleSlots)
        {
            this.removeWidget(slot);
        }
        this.visibleSlots.clear();
        for (int i = 6 * page; i < spellSlots.size(); i++)
        {
            SpellSlot resultingSlot = getSpellSlot(i);
            visibleSlots.add(resultingSlot);
            if (visibleSlots.size() == 6)
            {
                break;
            }

        }
        if (focusedSlot != -1)
        {
            visibleSlots.get(focusedSlot % 6).select();
            renderChoices();
        }
        if (visibleSlots.size() < 6)
        {
            visibleSlots.add(new SpellSlot(initialLeft, initialTop + 26 * visibleSlots.size(), 26, 26, null, (clickedSpell, clickedSlot) -> {
                onAdd();
        }));
        }
    }

    private @NotNull SpellSlot getSpellSlot(int i) {
        final int slotIndex = i;
        SpellSlot resultingSlot = new SpellSlot(initialLeft, initialTop + 26 * i, 26, 26, spellSlots.get(i), (clickedSpell, clickedSlot) -> {
            this.focusedSlot = slotIndex;
            this.renderChoices();
            deselectOthers();
            clickedSlot.select();
        });
        if (mage.getCurrentSpellIndex() == i)
        {
            resultingSlot.setEquipped(true);
        }
        return resultingSlot;
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
        if (mage == null) return;
        Minecraft.getInstance().setScreen(new SpellEditScreen(mage));
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
    public boolean isPauseScreen() {
        return false;
    }
}
