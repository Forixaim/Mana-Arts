package net.forixaim.mana_arts.events;

import net.forixaim.mana_arts.ManaArts;
import net.forixaim.mana_arts.netcode.server.DatapackSync;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(modid = ManaArts.MOD_ID)
public class GameBusEvents
{
    @SubscribeEvent
    public static void onDatapackSynchronize(final OnDatapackSyncEvent event)
    {
        DatapackSync elementPacket = new DatapackSync(DatapackSync.PacketType.ELEMENT);
        DatapackSync spellPacket = new DatapackSync(DatapackSync.PacketType.SPELL);

        PacketDistributor.sendToAllPlayers(elementPacket);
        PacketDistributor.sendToAllPlayers(spellPacket);
    }
}
