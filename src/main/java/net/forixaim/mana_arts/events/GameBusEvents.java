package net.forixaim.mana_arts.events;

import net.forixaim.mana_arts.DeveloperSwitches;
import net.forixaim.mana_arts.ManaArts;
import net.forixaim.mana_arts.client.input.InputHandler;
import net.forixaim.mana_arts.netcode.server.DatapackSync;
import net.forixaim.mana_arts.registry.entries.ManaArtsAttachments;
import net.forixaim.mana_arts.world.capabilities.ManaEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;

@EventBusSubscriber(modid = ManaArts.MOD_ID)
public class GameBusEvents
{
    @SubscribeEvent
    public static void onDatapackSynchronize(final OnDatapackSyncEvent event)
    {
        DatapackSync elementPacket = new DatapackSync(DatapackSync.PacketType.ELEMENT);
    }

    @SubscribeEvent
    public static void onPlayerJoin(final EntityJoinLevelEvent event)
    {
        if (event.getLevel().isClientSide()) return;
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {

            ManaEntity mana = serverPlayer.getData(ManaArtsAttachments.MANA_ENTITY);

            if (DeveloperSwitches.DEBUG_MODE) {
                mana.debugInit();
            }
            serverPlayer.setData(ManaArtsAttachments.MANA_ENTITY, mana);
        }
    }
}
