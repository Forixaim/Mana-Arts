package net.forixaim.mana_arts.netcode;

import net.forixaim.mana_arts.ManaArts;
import net.forixaim.mana_arts.netcode.server.DatapackSync;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = ManaArts.MOD_ID)
public final class ManaArtsNetworkManager
{
    private ManaArtsNetworkManager() {}
    private static final String PROTOCOL_VERSION = "1";

    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event)
    {
        final PayloadRegistrar registrar = event.registrar(PROTOCOL_VERSION);
        registrar.playToClient(
                ManagedCustomPacketPayload.CLIENT_BOUND_DATAPACK_SYNC,
                DatapackSync.STREAM_CODEC,
                ManaArtsPayloadHandler::handleDataPack
        );
    }
}
