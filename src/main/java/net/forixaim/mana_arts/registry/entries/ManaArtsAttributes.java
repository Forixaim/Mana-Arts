package net.forixaim.mana_arts.registry.entries;

import net.forixaim.mana_arts.ManaArts;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ManaArtsAttributes
{
    public static final DeferredRegister<Attribute> REGISTRY = DeferredRegister.create(Registries.ATTRIBUTE, ManaArts.MOD_ID);

    public static final DeferredHolder<Attribute, Attribute> MAX_MANA = REGISTRY.register("max_mana", () -> new RangedAttribute("attribute.name." + ManaArts.MOD_ID + ".max_mana", 100.0D, 0.0D, 1024d));

}
