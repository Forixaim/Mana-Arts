package net.forixaim.mana_arts.api.loaders;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import net.forixaim.mana_arts.ManaArts;
import net.forixaim.mana_arts.netcode.server.DatapackSync;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public final class ElementReloadListener extends SimpleJsonResourceReloadListener
{
    public static final ElementReloadListener INSTANCE = new ElementReloadListener();

    private static final String DIRECTORY = ManaArts.MOD_ID + "/elements";

    private ElementReloadListener()
    {
        super(new GsonBuilder().create(), DIRECTORY);
    }
    @Override
    protected void apply(@NotNull Map<ResourceLocation, JsonElement> resourceLocationJsonElementMap, @NotNull ResourceManager resourceManager, @NotNull ProfilerFiller profilerFiller)
    {

    }

    public static void processServerPacket(DatapackSync packet)
    {
        if (packet.packetType() == DatapackSync.PacketType.ELEMENT)
        {

        }
    }
}
