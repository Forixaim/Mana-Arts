package net.forixaim.mana_arts.client.ui.screen.spell_creation;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.forixaim.mana_arts.api.data.element.Element;
import net.forixaim.mana_arts.api.data.spell.Spell;
import net.forixaim.mana_arts.api.data.spell.SpellModifier;
import net.forixaim.mana_arts.api.managers.ModifierManager;
import net.forixaim.mana_arts.client.ui.screen.spell_menu.SpellMenuContainer;
import net.forixaim.mana_arts.client.ui.screen.spell_menu.SpellScreen;
import net.forixaim.mana_arts.netcode.client.SpellModificationRequest;
import net.forixaim.mana_arts.world.capabilities.ManaEntity;
import net.forixaim.mana_arts.world.entity.spell.SpellProjectile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.List;
import java.util.Map;

public class SpellEditScreen extends Screen
{
    public final int editingIndex;
    private int spellPage;
    private int elementPage;
    private int modifierPage;
    private int selectedSpellIndex;
    private int selectedElementIndex;
    private final SaveButton saveButton = new SaveButton((width - 256) / 2 + 240, (height - 160) / 2 + 140, this::onSaveButtonClicked);
    private final List<Holder<Element>> learnedElements = Lists.newArrayList();
    private final List<Holder<Spell>> learnedSpells = Lists.newArrayList();
    private final List<SpellSelectButton> visibleSpells = Lists.newArrayList();
    private final List<ElementSelectButton> visibleElements = Lists.newArrayList();
    private final Map<Holder<SpellModifier<? extends SpellProjectile>>, Double> modifiers = Maps.newHashMap();
    private final List<ModifierSlot> visibleModifiers = Lists.newArrayList();
    int leftPos;
    int topPos;
    private final ManaEntity mage;
    private final boolean isNewSpell;
    public SpellEditScreen(ManaEntity mage, int editingIndex) {
        super(Component.empty());
        this.editingIndex = editingIndex;
        selectedElementIndex = -1;
        selectedSpellIndex = -1;
        this.mage = mage;
        this.isNewSpell = false;
    }

    public SpellEditScreen(ManaEntity mage)
    {
        super(Component.empty());
        editingIndex = -1;
        selectedElementIndex = -1;
        selectedSpellIndex = -1;
        this.mage = mage;
        this.isNewSpell = true;
    }

    private void onSaveButtonClicked(SaveButton button)
    {
        SpellModificationRequest request = new SpellModificationRequest(isNewSpell ? SpellModificationRequest.ModifierType.ADD : SpellModificationRequest.ModifierType.MODIFY, assembleNewSpell());
        PacketDistributor.sendToServer(request);
        Minecraft.getInstance().setScreen(new SpellScreen());
    }

    private CompoundTag assembleNewSpell()
    {
        CompoundTag spellTag = new CompoundTag();
        if (editingIndex >= 0)
        {
            spellTag.putInt("editingIndex", editingIndex);
        }
        spellTag.putString("spell", learnedSpells.get(selectedSpellIndex).getRegisteredName());
        spellTag.putString("element", learnedElements.get(selectedElementIndex).getRegisteredName());
        CompoundTag modifiers = new CompoundTag();
        this.modifiers.forEach((key, value) -> modifiers.putDouble(key.getRegisteredName(), value));
        spellTag.put("modifiers", modifiers);
        return spellTag;
    }

    @Override
    protected void init() {
        super.init();
        leftPos = (this.width - SpellMenuContainer.imageWidth) / 2;
        topPos = (this.height - SpellMenuContainer.imageHeight) / 2;
        spellPage = 0;
        elementPage = 0;
        modifierPage = 0;

        addBackground();
        addMageData();
        this.addRenderableWidget(new SaveButton(leftPos + 233, topPos + 212, this::onSaveButtonClicked));
        this.addRenderableWidget(new DiscardButton(leftPos + 210, topPos + 212, this::onDiscardButtonClicked));
        refreshVisibleData();
        if (selectedSpellIndex >= 0)
        {
            refreshModifiers();
        }

    }

    private void onDiscardButtonClicked(AbstractButton button)
    {
        Minecraft.getInstance().setScreen(new SpellScreen());
    }

    private void addBackground()
    {
        this.addRenderableOnly(new SpellEditContainer(leftPos, topPos, width, height));
    }

    private void addMageData()
    {
        mage.getLearnedSpells().forEach(spell -> {
            if (!learnedSpells.contains(spell))
            {
                learnedSpells.add(spell);
            }
        });
        mage.getElements().forEach(element -> {
            if (!learnedElements.contains(element))
            {
                learnedElements.add(element);
            }
        });
        if (!isNewSpell)
        {
            selectedSpellIndex = learnedSpells.indexOf(mage.getSpells().get(editingIndex).getSpell());
            selectedElementIndex = learnedElements.indexOf(mage.getSpells().get(editingIndex).getElement());
            mage.getSpells().get(editingIndex).getModifiers().forEach((k, v) -> {
                Holder<SpellModifier<? extends SpellProjectile>> holder = ModifierManager.getModifier(k);
                if (holder != null)
                {
                    modifiers.put(holder, v);
                }
            });
            refreshModifiers();
        }
    }

    private void refreshVisibleData()
    {
        visibleSpells.forEach(this::removeWidget);
        visibleElements.forEach(this::removeWidget);
        deselectAllSpells();
        deselectAllElements();
        visibleElements.clear();
        visibleSpells.clear();
        int counter = 0;
        for (int i = 6 * spellPage; i < learnedSpells.size(); i++)
        {
            visibleSpells.add(new SpellSelectButton(leftPos + 5, topPos + 51 + (26 * counter), learnedSpells.get(i), this::onSpellSelected, i));
            counter++;
            if (counter >= 6)
                break;
        }
        counter = 0;
        for (int i = 6 * elementPage; i < learnedElements.size(); i++)
        {
            visibleElements.add(new ElementSelectButton(leftPos + 36, topPos + 51 + (26 * counter), learnedElements.get(i), this::onElementSelected, i));
            counter++;
            if (counter >= 6)
                break;
        }
        visibleSpells.forEach(spell -> {
            if (selectedSpellIndex == spell.getIndex())
                spell.setSelected(true);
        });
        visibleElements.forEach(element -> {
            if (selectedElementIndex == element.getIndex())
                element.setSelected(true);
        });
        visibleSpells.forEach(this::addRenderableWidget);
        visibleElements.forEach(this::addRenderableWidget);
    }

    private void onElementSelected(ElementSelectButton button)
    {
        selectedElementIndex = button.getIndex();
        deselectAllElements();
        button.setSelected(true);

    }

    private void onSpellSelected(SpellSelectButton button)
    {
        if (selectedSpellIndex == button.getIndex())
            return;
        selectedSpellIndex = button.getIndex();
        deselectAllSpells();
        button.setSelected(true);
        modifiers.clear();
        modifiers.putAll(button.getSpell().value().initDefaultModifiers());
        modifierPage = 0;
        refreshModifiers();
    }

    private void deselectAllSpells()
    {
        visibleSpells.forEach(button -> button.setSelected(false));
    }

    private void deselectAllElements()
    {
        visibleElements.forEach(button -> button.setSelected(false));
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    void updateModifier(Holder<SpellModifier<? extends SpellProjectile>> modifier, double value) {
        modifiers.put(modifier, value);
    }

    private void onPageChange(int pageDelta) {
        // Save current visible adjustments to the map before destroying the widgets
        visibleModifiers.forEach(slot -> modifiers.put(slot.getSpellModifier(), slot.getModifierValue()));

        // Change page and redraw
        this.modifierPage += pageDelta;
        refreshModifiers();
    }

    private void refreshModifiers()
    {
        visibleModifiers.forEach(slot -> {
            this.removeWidget(slot);
            this.removeWidget(slot.getWidget());
        });
        visibleModifiers.clear();
        int count = 0;
        List<Map.Entry<Holder<SpellModifier<? extends SpellProjectile>>, Double>> entries = modifiers.entrySet().stream().toList();
        for (int i = modifierPage * 4; i < entries.size(); i++)
        {
            Holder<SpellModifier<? extends SpellProjectile>> modifier = entries.get(i).getKey();
            if (modifier.value().type() == SpellModifier.Type.BOOLEAN)
            {
                int boolValue = entries.get(i).getValue().intValue();
                boolean trueValue = boolValue > 0;
                visibleModifiers.add(new BooleanModifierSlot(leftPos + 67, topPos + 51 + (39 * count), entries.get(i).getKey(), trueValue, this));
            }
            else
            {
                visibleModifiers.add(new SliderModifierSlot(leftPos + 67, topPos + 51 + (39 * count), entries.get(i).getKey(), entries.get(i).getValue(), this));
            }
            count++;
            if (count >= 4)
                break;
        }
        visibleModifiers.forEach(slot -> {
            this.addRenderableOnly(slot);
            if (slot.getWidget() != null)
                this.addRenderableWidget(slot.getWidget());
        });
    }

}
