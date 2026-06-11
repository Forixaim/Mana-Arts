package net.forixaim.mana_arts.registry.entries;

import net.forixaim.mana_arts.ManaArts;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ManaArtsParticles
{
    public static final DeferredRegister<ParticleType<?>> REGISTRY = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, ManaArts.MOD_ID);
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> LIGHT_GLIMMER = REGISTRY.register("light_glimmer", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> EXPLOSION = REGISTRY.register("explosion", () -> new SimpleParticleType(false));
}
