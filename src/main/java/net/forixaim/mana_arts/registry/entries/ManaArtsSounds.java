package net.forixaim.mana_arts.registry.entries;

import net.forixaim.mana_arts.ManaArts;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ManaArtsSounds
{
    public static final DeferredRegister<SoundEvent> REGISTRY = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, ManaArts.MOD_ID);
    public static final DeferredHolder<SoundEvent, SoundEvent> LIGHT_EXPLOSION = registerSound("light.explosion");
    public static final DeferredHolder<SoundEvent, SoundEvent> LIGHT_SHOOT = registerSound("light.shoot");

    private static DeferredHolder<SoundEvent, SoundEvent> registerSound(String name) {
        ResourceLocation res = ResourceLocation.fromNamespaceAndPath(ManaArts.MOD_ID, name);
        return REGISTRY.register(name, () -> SoundEvent.createVariableRangeEvent(res));
    }
}
