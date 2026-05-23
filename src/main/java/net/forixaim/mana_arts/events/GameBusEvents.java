package net.forixaim.mana_arts.events;

import net.forixaim.mana_arts.ManaArts;
import net.forixaim.mana_arts.netcode.server.DatapackSync;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;

@EventBusSubscriber(modid = ManaArts.MOD_ID)
public class GameBusEvents
{
    public static void onDatapackSynchronize(final OnDatapackSyncEvent event)
    {
        DatapackSync elementPacket = new DatapackSync(DatapackSync.PacketType.ELEMENT);
    }
}
