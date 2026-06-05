package net.forixaim.mana_arts;

import com.mojang.logging.LogUtils;
import net.forixaim.mana_arts.api.loaders.ElementReloadListener;
import net.forixaim.mana_arts.api.loaders.ModifierListener;
import net.forixaim.mana_arts.api.loaders.SpellReloadListener;
import net.forixaim.mana_arts.client.renderer.BlastRenderer;
import net.forixaim.mana_arts.events.ClientEvents;
import net.forixaim.mana_arts.registry.ManaArtsRegistries;
import net.forixaim.mana_arts.registry.entries.ManaArtsAttributes;
import net.forixaim.mana_arts.registry.entries.ManaArtsEntities;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
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
        modEventBus.addListener(ManaArtsRegistries::onRegister);
        modEventBus.addListener(this::modifyAttributes);
        ManaArtsRegistries.REGISTERS.forEach(reg -> reg.register(modEventBus));
        NeoForge.EVENT_BUS.addListener(this::addReloadListeners);
        container.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
        if (FMLEnvironment.dist == Dist.CLIENT)
        {
            handleClientEvents(modEventBus, container);
        }
    }

    public void modifyAttributes(EntityAttributeModificationEvent event) {
        event.add(EntityType.PLAYER, ManaArtsAttributes.MAX_MANA);
    }

    private void handleClientEvents(IEventBus modEventBus, ModContainer container)
    {
        modEventBus.addListener(ClientEvents::registerGuiLayers);
    }
    private void commonSetup(final FMLCommonSetupEvent event) {
    }

    public void addReloadListeners(AddReloadListenerEvent event)
    {
        event.addListener(ElementReloadListener.INSTANCE);
        event.addListener(SpellReloadListener.INSTANCE);
        event.addListener(ModifierListener.INSTANCE);
    }

    @EventBusSubscriber(modid = MOD_ID)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void registerRenderersEvent(EntityRenderersEvent.RegisterRenderers event)
        {
            event.registerEntityRenderer(ManaArtsEntities.BLAST.get(), BlastRenderer::new);
        }
    }
}
