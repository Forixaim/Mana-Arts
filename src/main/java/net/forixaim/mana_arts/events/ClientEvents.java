package net.forixaim.mana_arts.events;

import net.forixaim.mana_arts.ManaArts;
import net.forixaim.mana_arts.client.input.InputHandler;
import net.forixaim.mana_arts.client.input.ManaArtsKeyMapping;
import net.forixaim.mana_arts.client.ui.overlay.ManaHUD;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

@EventBusSubscriber(modid = ManaArts.MOD_ID, value = Dist.CLIENT)
public class ClientEvents
{
	@SubscribeEvent
	public static void onKeyRegister(RegisterKeyMappingsEvent event)
	{
		event.register(ManaArtsKeyMapping.CAST_SPELL);
	}

	@SubscribeEvent
	public static void onClientTickBegin(final ClientTickEvent.Pre event)
	{
		InputHandler.tick();
	}

	public static void registerGuiLayers(RegisterGuiLayersEvent event) {
		event.registerAbove(
				VanillaGuiLayers.PLAYER_HEALTH,
				ManaArts.identifier("mana_hud"),
				new ManaHUD()
		);
	}
}