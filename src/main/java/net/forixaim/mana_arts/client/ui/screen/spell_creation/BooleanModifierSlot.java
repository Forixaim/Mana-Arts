package net.forixaim.mana_arts.client.ui.screen.spell_creation;

import net.forixaim.mana_arts.ManaArts;
import net.forixaim.mana_arts.api.data.spell.SpellModifier;
import net.forixaim.mana_arts.world.entity.spell.SpellProjectile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BooleanModifierSlot extends ModifierSlot{
    private final Holder<SpellModifier<? extends SpellProjectile>> modifier;
    private boolean value;
    private static final ResourceLocation MODIFIER_SLOT_TEXTURE = ManaArts.identifier("textures/gui/spell_edit/boolean_modifier.png");

    public BooleanModifierSlot(int x, int y, Holder<SpellModifier<? extends SpellProjectile>> modifier, SpellEditScreen parent) {
        super(x, y, 184, 39, Component.empty(), parent);
        this.modifier = modifier;
    }

    public BooleanModifierSlot(int x, int y, Holder<SpellModifier<? extends SpellProjectile>> modifier, boolean value, SpellEditScreen parent)
    {
        this(x, y, modifier, parent);
        this.value = value;
    }

    private Component getKey()
    {
        ResourceLocation rl = ResourceLocation.parse(modifier.getRegisteredName());
        return Component.translatable("modifier.".concat(rl.getNamespace()).concat(".").concat(rl.getPath()));
    }

    @Override
    public AbstractWidget getWidget() {
        return null;
    }

    @Override
    public Holder<SpellModifier<?>> getSpellModifier() {
        return modifier;
    }

    @Override
    public double getModifierValue() {
        return value ? 1 : 0;
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        guiGraphics.blit(MODIFIER_SLOT_TEXTURE, getX(), getY(), 0, 0, width, height, 184, 39);
        guiGraphics.drawString(Minecraft.getInstance().font, getKey(), getX() + 10, getY() + 5, 0xFFFFFF);
    }

    @Override
    protected void updateWidgetNarration(@NotNull NarrationElementOutput narrationElementOutput) {

    }

    @Override
    public @NotNull List<? extends GuiEventListener> children() {
        return List.of();
    }
}
