package net.forixaim.mana_arts.api.data.element;

import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.function.BiConsumer;

public record ParticleEffects(
        BiConsumer<Vec3, Level> spawn,
        BiConsumer<Vec3, Level> tick,
        BiConsumer<Vec3, Level> onHitBlock,
        BiConsumer<Vec3, Level> onHitEntity,
        BiConsumer<Vec3, Level> onExpire
) {
    public static class Builder
    {

    }
}
