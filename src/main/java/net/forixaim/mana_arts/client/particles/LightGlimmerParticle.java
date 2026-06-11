package net.forixaim.mana_arts.client.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.NotNull;

public class LightGlimmerParticle extends TextureSheetParticle {
    protected LightGlimmerParticle(ClientLevel level, double x, double y, double z, SpriteSet sprites) {
        super(level, x, y, z);
        this.setSpriteFromAge(sprites);

    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void tick() {
        super.tick();
        this.roll += 0.1f;
        this.alpha -= 0.1f;
        this.quadSize += 0.1f;
        if (alpha <= 0)
            remove();
    }

    public record Provider(SpriteSet spriteSet) implements ParticleProvider<SimpleParticleType> {
        @Override
        public @NotNull Particle createParticle(@NotNull SimpleParticleType simpleParticleType, @NotNull ClientLevel clientLevel, double v, double v1, double v2, double v3, double v4, double v5) {
            return new LightGlimmerParticle(clientLevel, v, v1, v2, spriteSet);
        }
    }
}
