package net.forixaim.mana_arts.registry.registers;

import net.forixaim.mana_arts.api.data.element.Element;
import net.forixaim.mana_arts.registry.ManaArtsRegistries;
import net.forixaim.mana_arts.registry.registers.holders.DeferredElement;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;
import java.util.function.Supplier;

public class ElementRegister extends DeferredRegister<Element>
{
    protected ElementRegister(ResourceKey<? extends Registry<Element>> registryKey, String namespace)
    {
        super(registryKey, namespace);
    }

    public static ElementRegister create(String modId)
    {
        return new ElementRegister(ManaArtsRegistries.RegistryKeys.ELEMENTS, modId);
    }

    public DeferredElement registerElement(String id, Supplier<Element> builder)
    {
        ResourceKey<Element> key = ResourceKey.create(
                ManaArtsRegistries.RegistryKeys.ELEMENTS,
                ResourceLocation.fromNamespaceAndPath(getNamespace(), id)
        );
        ;
        return (DeferredElement) this.register(id, builder);
    }

    public DeferredElement registerElement(String id, Function<ResourceLocation, Element> builder)
    {
        ResourceKey<Element> key = ResourceKey.create(
                ManaArtsRegistries.RegistryKeys.ELEMENTS,
                ResourceLocation.fromNamespaceAndPath(getNamespace(), id)
        );
        this.register(id, builder);

        return new DeferredElement(key);
    }


}
