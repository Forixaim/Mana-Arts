package net.forixaim.mana_arts.registry.entries;


import net.forixaim.mana_arts.ManaArts;
import net.forixaim.mana_arts.api.data.ElementalWeaponData;
import yesman.epicfight.registry.deferred.CustomDataRegister;
import yesman.epicfight.registry.deferred.holders.DeferredCustomData;
import yesman.epicfight.world.capabilities.item.custom.CustomData;

public class ManaArtsWeaponData
{
    public static final CustomDataRegister REGISTRY = CustomDataRegister.createWeapon(ManaArts.MOD_ID);

    public static final DeferredCustomData<CustomData<ElementalWeaponData>> MANA_ARTS_DATA = REGISTRY.registerCustomData("mana_arts_data", () -> CustomData.createDeserializable(new ElementalWeaponData(), ElementalWeaponData::deserialize));
}
