package net.forixaim.mana_arts.registry.registers;

import net.forixaim.mana_arts.api.data.Element;
import net.forixaim.mana_arts.registry.ManaArtsRegistries;
import net.forixaim.mana_arts.registry.registers.holders.DeferredElement;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ElementRegister extends DeferredRegister<Element.Builder>
{
    protected ElementRegister(ResourceKey<? extends Registry<Element.Builder>> registryKey, String namespace)
    {
        super(registryKey, namespace);
    }

    public static ElementRegister create(String modId)
    {
        return (ElementRegister) DeferredRegister.create(ManaArtsRegistries.ELEMENTS, modId);
    }

    public DeferredElement registerElement(String id, Supplier<Element.Builder> builder)
    {
        ResourceKey<Element.Builder> key = ResourceKey.create(
                ManaArtsRegistries.RegistryKeys.ELEMENTS,
                ResourceLocation.fromNamespaceAndPath(getNamespace(), id)
        );
        ;
        return (DeferredElement) this.register(id, builder);
    }


}
