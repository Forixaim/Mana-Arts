package net.forixaim.mana_arts.netcode;

import com.google.common.collect.Maps;
import net.forixaim.mana_arts.ManaArts;
import net.forixaim.mana_arts.netcode.server.DatapackSync;
import net.forixaim.mana_arts.netcode.server.ManaEntitySync;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.NoSuchElementException;

public interface ManagedCustomPacketPayload extends CustomPacketPayload
{
    Map<Class<? extends CustomPacketPayload>, Type<?>> PAYLOAD_TYPES = Maps.newHashMap();

    CustomPacketPayload.Type<DatapackSync> CLIENT_BOUND_DATAPACK_SYNC = registerPayloadType(DatapackSync.class, "client_bound_sync_datapack");
    CustomPacketPayload.Type<ManaEntitySync> CLIENT_BOUND_MANA_ENTITY_SYNC = registerPayloadType(ManaEntitySync.class, "client_bound_sync_mana_entity");


    static <T extends ManagedCustomPacketPayload> CustomPacketPayload.Type<T> registerPayloadType(Class<T> type, String payloadId)
    {
        CustomPacketPayload.Type<T> packet = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(ManaArts.MOD_ID, payloadId));
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
