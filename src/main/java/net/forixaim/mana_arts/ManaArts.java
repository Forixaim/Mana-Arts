package net.forixaim.mana_arts;

import com.mojang.logging.LogUtils;
import net.forixaim.mana_arts.api.loaders.ElementReloadListener;
import net.forixaim.mana_arts.registry.ManaArtsRegistries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import org.slf4j.Logger;

@Mod(ManaArts.MOD_ID)
public final class ManaArts
{
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final String MOD_ID = "mana_arts";

    public static ResourceLocation identifier(String path)
    {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public ManaArts(IEventBus modEventBus, ModContainer container)
    {
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(ManaArtsRegistries::onRegister);
        NeoForge.EVENT_BUS.register(this);
        container.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }
    private void commonSetup(final FMLCommonSetupEvent event) {
    }

    @SubscribeEvent
    public void addReloadListeners(AddReloadListenerEvent event)
    {
        event.addListener(ElementReloadListener.INSTANCE);
    }


    private void clientSetup(final FMLClientSetupEvent event)
    {
    }
}
