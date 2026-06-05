package net.forixaim.mana_arts.client.input;

import net.forixaim.mana_arts.client.ui.screen.spell_menu.SpellScreen;
import net.forixaim.mana_arts.registry.entries.ManaArtsAttachments;
import net.forixaim.mana_arts.world.capabilities.ManaEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;

public class InputHandler
{
    public static void tick()
    {
        if (ManaArtsKeyMapping.CAST_SPELL.consumeClick())
        {
            LocalPlayer player = Minecraft.getInstance().player;
            if (player == null) return;
            ManaEntity mana = player.getData(ManaArtsAttachments.MANA_ENTITY);
            mana.getCurrentSpell().sendCastRequest();
        }

        if (ManaArtsKeyMapping.OPEN_SPELL_MENU.consumeClick())
        {
            Minecraft.getInstance().setScreen(new SpellScreen());
        }
    }
}
