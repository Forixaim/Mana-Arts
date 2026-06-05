package net.forixaim.mana_arts.netcode;

import net.forixaim.mana_arts.ManaArts;
import net.forixaim.mana_arts.netcode.client.CastRequest;
import net.forixaim.mana_arts.netcode.client.SpellCycleRequest;
import net.forixaim.mana_arts.netcode.client.SpellModificationRequest;
import net.forixaim.mana_arts.netcode.server.DatapackSync;
import net.forixaim.mana_arts.netcode.server.mana_entity.CurrentSpellIndexSync;
import net.forixaim.mana_arts.netcode.server.mana_entity.ManaValueSync;
import net.forixaim.mana_arts.netcode.server.mana_entity.SpellElementSync;
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
        //Client-bound
        registrar.playToClient(
                ManagedCustomPacketPayload.CLIENT_BOUND_DATAPACK_SYNC,
                DatapackSync.STREAM_CODEC,
                ManaArtsPayloadHandler::handleDataPack
        );
        registrar.playToClient(
                ManagedCustomPacketPayload.CLIENT_BOUND_MANA_ENTITY_SYNC,
                ManaValueSync.STREAM_CODEC,
                ManaArtsPayloadHandler::handleSync
        );
        registrar.playToClient(
                ManagedCustomPacketPayload.CLIENT_BOUND_SPELL_ELEMENT_SYNC,
                SpellElementSync.STREAM_CODEC,
                ManaArtsPayloadHandler::handleSpellElementSync
        );
        registrar.playToClient(
                ManagedCustomPacketPayload.CLIENT_BOUND_SPELL_INDEX_SYNC,
                CurrentSpellIndexSync.STREAM_CODEC,
                ManaArtsPayloadHandler::handleSpellSync
        );


        //Server-bound
        registrar.playToServer(
                ManagedCustomPacketPayload.SERVER_BOUND_CAST_REQUEST,
                CastRequest.STREAM_CODEC,
                ManaArtsPayloadHandler::handleCast
        );
        registrar.playToServer(
                ManagedCustomPacketPayload.SERVER_BOUND_SPELL_CYCLE_REQUEST,
                SpellCycleRequest.STREAM_CODEC,
                ManaArtsPayloadHandler::handleSpellCycle
        );
        registrar.playToServer(
                ManagedCustomPacketPayload.SERVER_BOUND_SPELL_MODIFICATION_REQUEST,
                SpellModificationRequest.STREAM_CODEC,
                ManaArtsPayloadHandler::handleModification
        );
    }
}
