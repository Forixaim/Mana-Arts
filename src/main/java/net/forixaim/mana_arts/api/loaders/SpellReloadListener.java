package net.forixaim.mana_arts.api.loaders;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import net.forixaim.mana_arts.api.managers.SpellManager;
import net.forixaim.mana_arts.netcode.server.DatapackSync;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public final class SpellReloadListener extends SimpleJsonResourceReloadListener
{
    public static final SpellReloadListener INSTANCE = new SpellReloadListener();

    private static final String DIRECTORY = "mana_arts/spells";

    private SpellReloadListener()
    {
        super(new GsonBuilder().create(), DIRECTORY);
    }

    @Override
    protected void apply(@NotNull Map<ResourceLocation, JsonElement> resourceLocationJsonElementMap, @NotNull ResourceManager resourceManager, @NotNull ProfilerFiller profilerFiller)
    {
        SpellManager.load();
    }

    public static void processServerPacket(DatapackSync sync)
    {
        if (sync.packetType() == DatapackSync.PacketType.SPELL)
        {
            SpellManager.load();
        }
    }
}
