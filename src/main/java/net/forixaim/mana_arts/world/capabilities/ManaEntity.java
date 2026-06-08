package net.forixaim.mana_arts.world.capabilities;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.forixaim.mana_arts.DeveloperSwitches;
import net.forixaim.mana_arts.ManaArts;
import net.forixaim.mana_arts.api.data.element.Element;
import net.forixaim.mana_arts.api.data.internal.SpellContainer;
import net.forixaim.mana_arts.api.managers.ElementManager;
import net.forixaim.mana_arts.client.ui.screen.spell_menu.SpellScreen;
import net.forixaim.mana_arts.netcode.server.mana_entity.CurrentSpellIndexSync;
import net.forixaim.mana_arts.netcode.server.mana_entity.ManaEntityPacket;
import net.forixaim.mana_arts.netcode.server.mana_entity.ManaValueSync;
import net.forixaim.mana_arts.netcode.server.mana_entity.SpellElementSync;
import net.forixaim.mana_arts.registry.entries.ManaArtsAttributes;
import net.forixaim.mana_arts.registry.entries.ManaArtsElements;
import net.forixaim.mana_arts.registry.entries.ManaArtsSpells;
import net.forixaim.mana_arts.world.ManaSource;
import net.forixaim.mana_arts.world.ManaSourceTags;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ManaEntity implements INBTSerializable<CompoundTag>
{
    private double mana;
    private double overchargeReserve;
    private final List<Holder<Element>> elements;
    private final List<SpellContainer> spells;
    private IAttachmentHolder original;
    private int currentSpell;

    public SpellContainer getCurrentSpell() {
        return spells.get(currentSpell);
    }

    public int getCurrentSpellIndex() {
        return currentSpell;
    }

    public void setCurrentSpell(int index) {
        currentSpell = index;
        CurrentSpellIndexSync packet = new CurrentSpellIndexSync(currentSpell);
        if (original instanceof ServerPlayer serverPlayer)
        {
            PacketDistributor.sendToPlayer(serverPlayer, packet);
        }
    }

    public void onSyncSpell(CurrentSpellIndexSync packet)
    {
        currentSpell = packet.index();
        if (original instanceof LocalPlayer)
        {
            Screen currentScreen = Minecraft.getInstance().screen;
            if (currentScreen instanceof SpellScreen spellScreen)
            {
                spellScreen.sync();
            }
        }
    }

    public void cycleNextSpell() {
        setCurrentSpell( (currentSpell + 1) % spells.size());
    }

    @OnlyIn(Dist.CLIENT)
    public void cycleNextSpellNoSync() {
        //This is used for client prediction
        currentSpell = (currentSpell + 1) % spells.size();
    }

    @OnlyIn(Dist.CLIENT)
    public void cyclePreviousSpellNoSync() {
        currentSpell = (currentSpell - 1) % spells.size();
    }

    public void cyclePreviousSpell() {
        setCurrentSpell( (currentSpell - 1) % spells.size());
    }


    public ManaEntity(IAttachmentHolder iAttachmentHolder) {
        this();
        original = iAttachmentHolder;
    }

    public void debugInit()
    {
        spells.clear();
        SpellContainer spell = new SpellContainer();
        spell.setSpell(ManaArtsSpells.BLAST);
        spell.setElement(ManaArtsElements.LIGHT);
        addSpell(spell);
        setCurrentSpell(spells.indexOf(spell));
    }

    public List<SpellContainer> getSpells() {
        return spells;
    }

    public void addSpell(SpellContainer spell) {
        spells.add(spell);
        sync(SyncType.SPELLS);
    }

    public void removeSpell(SpellContainer spell) {
        if (!spells.contains(spell)) return;
        spells.remove(spell);
        sync(SyncType.SPELLS);
    }

    public void addElement(Holder<Element> element) {
        if (elements.contains(element)) return;
        elements.add(element);
        sync(SyncType.ELEMENTS);
    }

    public void removeElement(Holder<Element> element) {
        if (!elements.contains(element)) return;
        elements.remove(element);
        sync(SyncType.ELEMENTS);
    }

    public List<Holder<Element>> getElements() {
        return ImmutableList.copyOf(elements);
    }

    public double getMana() {
        return this.mana;
    }

    public ManaEntity()
    {
        mana = 0;
        elements = Lists.newArrayList();
        spells = Lists.newArrayList();
        currentSpell = -1;
        if (DeveloperSwitches.DEBUG_MODE)
        {
            debugInit();
        }
    }

    public void setMana(Player player, ManaSource source) {
        double max = player.getAttributeValue(ManaArtsAttributes.MAX_MANA);
        double amount = source.amount();
        if (this.mana + source.amount() > max && source.is(ManaSourceTags.MANUAL_CHARGING))
        {
            double toSub = max - this.getMana();
            this.mana = Mth.clamp(amount, 0.0, max);
            amount -= toSub;
            overchargeReserve = Mth.clamp(amount, 0.0, max);
        }
        else
        {
            this.mana = Mth.clamp(amount, 0.0, max);
        }
        sync(SyncType.MANA);
    }

    public void modifyMana(Player player, ManaSource source) {
        this.setMana(player, source);
    }

    @OnlyIn(Dist.CLIENT)
    public void onServerSync(SyncType type, ManaEntityPacket packet)
    {
        switch (type)
        {
            case MANA -> {
                if (packet instanceof ManaValueSync(Double syncMana, Double overcharge))
                {
                    this.mana = syncMana;
                    this.overchargeReserve = overcharge;
                }
            }
            case SPELLS, ELEMENTS, CURRENT_SPELL -> {
                if (packet instanceof SpellElementSync(CompoundTag tag, SpellElementSync.SpelLElementSyncType syncType))
                {
                    switch (syncType)
                    {
                        case ELEMENT -> {
                            this.elements.clear();
                            this.elements.addAll(deserializeElements(tag.getList("elements", Tag.TAG_STRING)));
                        }
                        case SPELL -> {
                            this.spells.clear();
                            this.spells.addAll(deserializeSpells(tag.getList("spells", Tag.TAG_COMPOUND)));
                        }
                    }
                    if (original instanceof LocalPlayer localPlayer)
                    {
                        Screen currentScreen = Minecraft.getInstance().screen;
                        if (currentScreen instanceof SpellScreen spellScreen)
                        {
                            spellScreen.sync();
                        }
                    }
                }
            }
        }
    }

    private void sync(SyncType type)
    {
        if (original instanceof ServerPlayer serverPlayer)
        {
            switch (type)
            {
                case ALL -> {
                    syncMana(serverPlayer);
                    syncSpells(serverPlayer);
                    syncElements(serverPlayer);
                }
                case MANA -> syncMana(serverPlayer);
                case SPELLS -> syncSpells(serverPlayer);
                case ELEMENTS -> syncElements(serverPlayer);
            }
        }
    }

    private void syncMana(ServerPlayer serverPlayer)
    {
        ManaValueSync packet = new ManaValueSync(mana, overchargeReserve);
        PacketDistributor.sendToPlayer(serverPlayer, packet);
    }

    public void syncSpells(ServerPlayer serverPlayer)
    {
        CompoundTag tag = new CompoundTag();
        tag.put("spells", serializeSpells());
        SpellElementSync packet = new SpellElementSync(tag, SpellElementSync.SpelLElementSyncType.SPELL);
        PacketDistributor.sendToPlayer(serverPlayer, packet);
    }

    public void syncElements(ServerPlayer serverPlayer)
    {
        CompoundTag tag = new CompoundTag();
        tag.put("elements", serializeElements());
        SpellElementSync packet = new SpellElementSync(tag, SpellElementSync.SpelLElementSyncType.ELEMENT);
        PacketDistributor.sendToPlayer(serverPlayer, packet);
    }

    private ListTag serializeSpells()
    {
        ListTag result = new ListTag();
        for (SpellContainer spell : this.spells)
        {
            result.add(spell.serialize());
        }
        return result;
    }

    private ListTag serializeElements()
    {
        ListTag result = new ListTag();
        for (Holder<Element> element : this.elements)
        {
            result.add(StringTag.valueOf(element.getRegisteredName()));
        }
        return result;
    }

    private List<Holder<Element>> deserializeElements(ListTag elements)
    {
        List<Holder<Element>> result = Lists.newArrayList();
        for (int i = 0; i < elements.size(); i++)
        {
            String element = elements.getString(i);
            result.add(ElementManager.getElement(ResourceLocation.parse(element)));
        }
        return result;
    }

    private List<SpellContainer> deserializeSpells(ListTag spells)
    {
        List<SpellContainer> result = Lists.newArrayList();
        for (int i = 0; i < spells.size(); i++)
        {
            result.add(SpellContainer.deserialize(spells.getCompound(i)));
        }
        return result;
    }

    public CompoundTag serializeNBT(HolderLookup.@NotNull Provider provider)
    {
        ManaArts.LOGGER.debug("Serializing ManaEntity {} for {} side", original, FMLEnvironment.dist.name());
        CompoundTag result = new CompoundTag();
        result.putDouble("mana", this.mana);
        result.putInt("currentSpell", currentSpell);
        result.put("elements", serializeElements());
        result.put("spells", serializeSpells());
        return result;
    }

    public void deserializeNBT(HolderLookup.@NotNull Provider provider, CompoundTag tag)
    {
        ManaArts.LOGGER.debug("Deserializing ManaEntity {} for {} side", original, FMLEnvironment.dist.name());
        if (tag.contains("mana", Tag.TAG_DOUBLE))
        {
            this.mana = tag.getDouble("mana");
        }
        if (tag.contains("currentSpell", Tag.TAG_INT))
        {
            this.currentSpell = tag.getInt("currentSpell");
        }
        if (tag.contains("elements", Tag.TAG_LIST))
        {
            ListTag elements = tag.getList("elements", Tag.TAG_STRING);
            this.elements.addAll(deserializeElements(elements));
        }
        if (tag.contains("spells", Tag.TAG_LIST))
        {
            ListTag spells = tag.getList("spells", Tag.TAG_COMPOUND);
            this.spells.addAll(deserializeSpells(spells));
        }
    }

    public enum SyncType {
        ALL,
        MANA,
        SPELLS,
        ELEMENTS,
        CURRENT_SPELL
    }
}
