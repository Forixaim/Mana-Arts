package net.forixaim.mana_arts.client.input;

import net.forixaim.mana_arts.client.ui.screen.spell_menu.SpellScreen;
import net.forixaim.mana_arts.netcode.ManaArtsNetworkManager;
import net.forixaim.mana_arts.netcode.client.SpellCycleRequest;
import net.forixaim.mana_arts.registry.entries.ManaArtsAttachments;
import net.forixaim.mana_arts.world.capabilities.ManaEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.neoforged.neoforge.network.PacketDistributor;

public class InputHandler
{
    public static void tick()
    {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;
        if (ManaArtsKeyMapping.CAST_SPELL.consumeClick())
        {
            ManaEntity mana = player.getData(ManaArtsAttachments.MANA_ENTITY);
            mana.getCurrentSpell().sendCastRequest();
        }

        if (ManaArtsKeyMapping.OPEN_SPELL_MENU.consumeClick())
        {
            Minecraft.getInstance().setScreen(new SpellScreen());
        }

        if (ManaArtsKeyMapping.CYCLE_SPELL_PREVIOUS.consumeClick())
        {
            //Client prediction
            ManaEntity mage = player.getData(ManaArtsAttachments.MANA_ENTITY);
            if (mage.getSpells().size() > 1)
            {
                mage.cyclePreviousSpellNoSync();
                SpellCycleRequest request = new SpellCycleRequest(SpellCycleRequest.CycleType.PREVIOUS);
                PacketDistributor.sendToServer(request);
            }

        }
        if (ManaArtsKeyMapping.CYCLE_SPELL_NEXT.consumeClick())
        {
            //Client prediction
            ManaEntity mage = player.getData(ManaArtsAttachments.MANA_ENTITY);
            if (mage.getSpells().size() > 1)
            {
                mage.cycleNextSpellNoSync();
                SpellCycleRequest request = new SpellCycleRequest(SpellCycleRequest.CycleType.NEXT);
                PacketDistributor.sendToServer(request);
            }
        }
    }
}
