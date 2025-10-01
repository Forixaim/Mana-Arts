package net.forixaim.mana_arts.registries;

import net.forixaim.mana_arts.ManaArts;
import net.forixaim.mana_arts.animations.ManaArtsAnimations;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.animation.AnimationManager;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = ManaArts.MODID)
public class AnimationRegistry
{
    @SubscribeEvent
    public static void onRegister(AnimationManager.AnimationRegistryEvent event)
    {
        event.newBuilder(ManaArts.MODID, AnimationRegistry::globalRegister);
    }

    public static void globalRegister(AnimationManager.AnimationBuilder builder)
    {
        ManaArtsAnimations.build(builder);
    }
}
