package net.forixaim.mana_arts.netcode;

import net.forixaim.mana_arts.ManaArts;
import net.forixaim.mana_arts.api.loaders.ElementReloadListener;
import net.forixaim.mana_arts.netcode.server.DatapackSync;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import yesman.epicfight.api.exception.DatapackException;

public interface ManaArtsPayloadHandler
{
    static void handleDataPack(final DatapackSync data, final IPayloadContext context) {
        try {
            switch (data.packetType()) {
                case ELEMENT -> ElementReloadListener.processServerPacket(data);
            }
        } catch (Exception e) {
            ManaArts.LOGGER.error("Error while handling datapack sync", e);
            throw new DatapackException(e.getMessage());
        }
    }
}
