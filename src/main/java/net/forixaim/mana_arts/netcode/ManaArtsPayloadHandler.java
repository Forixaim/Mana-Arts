package net.forixaim.mana_arts.netcode;

import net.forixaim.mana_arts.ManaArts;
import net.forixaim.mana_arts.api.data.internal.CastContext;
import net.forixaim.mana_arts.netcode.client.CastRequest;
import net.forixaim.mana_arts.netcode.server.DatapackSync;
import net.forixaim.mana_arts.registry.entries.ManaArtsAttachments;
import net.forixaim.mana_arts.world.capabilities.ManaEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import yesman.epicfight.api.exception.DatapackException;


public interface ManaArtsPayloadHandler
{
    static void handleDataPack(final DatapackSync data, final IPayloadContext context) {
        try {
            data.packetType().getListener().sync(data);
        } catch (Exception e) {
            ManaArts.LOGGER.error("Error while handling datapack sync", e);
            throw new DatapackException(e.getMessage());
        }
    }

    static void handleCast(final CastRequest data, final IPayloadContext context) {
        context.enqueueWork(() -> {
            ManaEntity mage = context.player().getData(ManaArtsAttachments.MANA_ENTITY);
            CastContext castContext = mage.getCurrentSpell().buildContext();
            mage.getCurrentSpell().handleCast(castContext, context.player());
        });
    }
}
