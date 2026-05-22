package net.forixaim.mana_arts.netcode;

import com.google.common.collect.Maps;
import net.forixaim.mana_arts.ManaArts;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.network.server.SPDatapackSync;

import java.util.Map;
import java.util.NoSuchElementException;

public interface ManagedCustomPacketPayload extends CustomPacketPayload
{
    Map<Class<? extends CustomPacketPayload>, Type<?>> PAYLOAD_TYPES = Maps.newHashMap();
    CustomPacketPayload.Type<SPDatapackSync> CLIENT_BOUND_DATAPACK_SYNC = registerPayloadType(SPDatapackSync.class, ManaArts.MOD_ID, "client_bound_sync_datapack");

    static <T extends yesman.epicfight.network.ManagedCustomPacketPayload> CustomPacketPayload.Type<T> registerPayloadType(Class<T> type, String modId, String payloadId)
    {
        CustomPacketPayload.Type<T> packet = new CustomPacketPayload.Type<T>(ResourceLocation.fromNamespaceAndPath(modId, payloadId));
        PAYLOAD_TYPES.put(type, packet);

        return packet;
    }

    @Override
    default CustomPacketPayload.@NotNull Type<? extends CustomPacketPayload> type()
    {
        if (!PAYLOAD_TYPES.containsKey(this.getClass()))
        {
            throw new NoSuchElementException("Unregistered packet: " + this.getClass());
        }

        return PAYLOAD_TYPES.get(this.getClass());
    }
}
