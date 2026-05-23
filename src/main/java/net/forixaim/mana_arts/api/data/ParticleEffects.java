package net.forixaim.mana_arts.api.data;

import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.function.BiConsumer;

public class ParticleEffects
{
    public BiConsumer<Vec3, Level> spawn;
    public BiConsumer<Vec3, Level> tick;
    public BiConsumer<Vec3, Level> onHitBlock;
    public BiConsumer<Vec3, Level> onHitEntity;
    public BiConsumer<Vec3, Level> onExpire;
}
