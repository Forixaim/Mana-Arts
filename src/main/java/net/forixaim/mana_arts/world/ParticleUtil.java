package net.forixaim.mana_arts.world;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

public class ParticleUtil
{
    public static <T extends ParticleOptions> void sendAlwaysVisibleParticles(ServerLevel level, T pType, double pPosX, double pPosY, double pPosZ, int pParticleCount, double pXOffset, double pYOffset, double pZOffset, double pSpeed) {
        int i = 0;
        for(int j = 0; j < level.players().size(); ++j) {
            ServerPlayer serverplayer = level.players().get(j);
            if (level.sendParticles(serverplayer, pType, true, pPosX, pPosY, pPosZ, pParticleCount, pXOffset, pYOffset, pZOffset, pSpeed)) {
                ++i;
            }
        }
    }
}