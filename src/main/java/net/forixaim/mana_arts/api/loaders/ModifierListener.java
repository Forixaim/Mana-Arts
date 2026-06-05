package net.forixaim.mana_arts.api.loaders;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import net.forixaim.mana_arts.api.managers.ModifierManager;
import net.forixaim.mana_arts.netcode.server.DatapackSync;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public final class ModifierListener extends SimpleJsonResourceReloadListener implements NetSyncListener {
    public static final ModifierListener INSTANCE = new ModifierListener();

    private ModifierListener()
    {
        super(new GsonBuilder().create(), "mana_arts/modifiers");
    }

    @Override
    public void sync(DatapackSync sync) {

    }

    @Override
    protected void apply(@NotNull Map<ResourceLocation, JsonElement> resourceLocationJsonElementMap, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        ModifierManager.load();
    }
}
