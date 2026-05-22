package net.forixaim.mana_arts.registry.entries;


import net.forixaim.mana_arts.ManaArts;
import net.forixaim.mana_arts.api.data.Element;
import net.forixaim.mana_arts.api.data.ManaArtsWeaponData;
import yesman.epicfight.registry.deferred.CustomDataRegister;
import yesman.epicfight.registry.deferred.holders.DeferredCustomData;
import yesman.epicfight.world.capabilities.item.custom.CustomData;

public class EpicFightCustomDataRegister
{
    public static final CustomDataRegister REGISTRY = CustomDataRegister.createWeapon(ManaArts.MOD_ID);

    public static final DeferredCustomData<CustomData<ManaArtsWeaponData>> MANA_ARTS_DATA = REGISTRY.registerCustomData("mana_arts_data", () -> CustomData.createDeserializable(new ManaArtsWeaponData(), ManaArtsWeaponData::deserialize));
}
