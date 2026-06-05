package net.forixaim.mana_arts.events;

import net.forixaim.mana_arts.DeveloperSwitches;
import net.forixaim.mana_arts.ManaArts;
import net.forixaim.mana_arts.netcode.server.DatapackSync;
import net.forixaim.mana_arts.registry.entries.ManaArtsAttachments;
import net.forixaim.mana_arts.world.capabilities.ManaEntity;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
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

    @SubscribeEvent
    public static void onPlayerJoin(EntityJoinLevelEvent event)
    {
        if (event.getEntity() instanceof ServerPlayer player)
        {
            ManaEntity mage = player.getData(ManaArtsAttachments.MANA_ENTITY);
            if (DeveloperSwitches.DEBUG_MODE)
            {
                mage.debugInit();
            }
        }
    }
}
