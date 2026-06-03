package net.forixaim.mana_arts.registry.entries;

import net.forixaim.mana_arts.ManaArts;
import net.forixaim.mana_arts.world.entity.spell.Blast;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ManaArtsEntities
{
    public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(Registries.ENTITY_TYPE, ManaArts.MOD_ID);
    public static final DeferredHolder<EntityType<?>, EntityType<Blast>> BLAST = REGISTRY.register("blast", () -> EntityType.Builder.of(Blast::new, MobCategory.MISC).sized(1,1).build("blast"));
}
