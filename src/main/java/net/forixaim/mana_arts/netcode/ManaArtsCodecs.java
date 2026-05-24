package net.forixaim.mana_arts.netcode;

import com.mojang.serialization.Codec;
import net.forixaim.mana_arts.world.capabilities.ManaEntity;

public interface ManaArtsCodecs
{
    Codec<ManaEntity> MANA_ENTITY_CODEC = Codec.unit(ManaEntity::new);


}
