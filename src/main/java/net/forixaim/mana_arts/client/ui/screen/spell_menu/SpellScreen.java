package net.forixaim.mana_arts.client.ui.screen.spell_menu;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
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
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
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
    private final Map<ResourceLocation, Double> modifiersMap = Maps.newHashMap();
    private final List<ModifierSlot> modifiers = Lists.newArrayList();
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
        for (SpellSlot slot : visibleSlots)
        {
            this.removeWidget(slot);
        }
        this.visibleSlots.clear();
        for (int i = 0; i < spellSlots.size(); i++)
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
            int x = leftPos + 50;
            this.focusedSlot = slotIndex;
            this.modifiersMap.clear();
            this.modifiersMap.putAll(clickedSpell.getModifiers());
            this.modifiers.clear();
            SpellTypeModifier spellTypeModifier = new SpellTypeModifier(x, initialTop, 187, 26, clickedSpell.getSpell());
            ElementTypeModifier elementTypeModifier = new ElementTypeModifier(x, initialTop + 26, 187, 26, clickedSpell.getElement());
            List<Map.Entry<ResourceLocation, Double>> entries = modifiersMap.entrySet().stream().toList();
            for (int j = 0; j < entries.size(); j++)
            {
                ModifierSlot result = new ModifierSlot(x, initialTop + 26 * (j + 2), 187, 26, ModifierManager.getModifier(entries.get(j).getKey()), entries.get(j).getValue());
                if (j == 4)
                {
                    break;
                }
                modifiers.add(result);
            }

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
