package net.forixaim.mana_arts.netcode;

import net.forixaim.mana_arts.DeveloperSwitches;
import net.forixaim.mana_arts.ManaArts;
import net.forixaim.mana_arts.api.data.internal.CastContext;
import net.forixaim.mana_arts.api.data.internal.SpellContainer;
import net.forixaim.mana_arts.netcode.client.CastRequest;
import net.forixaim.mana_arts.netcode.client.SpellCycleRequest;
import net.forixaim.mana_arts.netcode.client.SpellModificationRequest;
import net.forixaim.mana_arts.netcode.server.DatapackSync;
import net.forixaim.mana_arts.netcode.server.mana_entity.CurrentSpellIndexSync;
import net.forixaim.mana_arts.netcode.server.mana_entity.LearnedDataSync;
import net.forixaim.mana_arts.netcode.server.mana_entity.ManaValueSync;
import net.forixaim.mana_arts.netcode.server.mana_entity.SpellElementSync;
import net.forixaim.mana_arts.registry.entries.ManaArtsAttachments;
import net.forixaim.mana_arts.registry.entries.ManaArtsElements;
import net.forixaim.mana_arts.registry.entries.ManaArtsSpells;
import net.forixaim.mana_arts.world.ManaSource;
import net.forixaim.mana_arts.world.ManaSourceTags;
import net.forixaim.mana_arts.world.capabilities.ManaEntity;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import yesman.epicfight.api.exception.DatapackException;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;


public interface ManaArtsPayloadHandler
{
    static void handleDataPack(final DatapackSync data, final IPayloadContext context) {
        context.enqueueWork(() -> {
            try {
                data.packetType().getListener().sync(data);
            } catch (Exception e) {
                ManaArts.LOGGER.error("Error while handling datapack sync", e);
                throw new DatapackException(e.getMessage());
            }
        });
    }

    static void handleSpellCycle(SpellCycleRequest data, IPayloadContext context) {
        context.enqueueWork(() -> {
            ManaEntity mage = context.player().getData(ManaArtsAttachments.MANA_ENTITY);
            switch (data.cycleType()) {
                case NEXT -> mage.cycleNextSpell();
                case PREVIOUS -> mage.cyclePreviousSpell();
            }
        });
    }

    static void handleSpellSync(CurrentSpellIndexSync data, IPayloadContext context) {
        context.enqueueWork(() -> context.player().getData(ManaArtsAttachments.MANA_ENTITY).onSyncSpell(data));
    }

    static void handleSync(final ManaValueSync data, final IPayloadContext context) {
        context.enqueueWork( () -> context.player().getData(ManaArtsAttachments.MANA_ENTITY).onServerSync(ManaEntity.SyncType.MANA, data));
    }

    static void handleSpellElementSync(final SpellElementSync data, final IPayloadContext context) {
        context.enqueueWork(() -> context.player().getData(ManaArtsAttachments.MANA_ENTITY.get()).onServerSync(ManaEntity.SyncType.ELEMENTS, data));
    }

    static void handleModification(final SpellModificationRequest data, final IPayloadContext context) {
        context.enqueueWork(() -> {
            ManaEntity mage = context.player().getData(ManaArtsAttachments.MANA_ENTITY);
            switch (data.modifierType()) {
                case ADD -> {
                    SpellContainer result = SpellContainer.deserialize(data.context());
                    mage.addSpell(result);
                    if (mage.getSpells().size() == 1)
                    {
                        mage.setCurrentSpell(0);
                    }
                }
                case MODIFY -> {
                    SpellContainer result = SpellContainer.deserialize(data.context());
                    int index = data.context().getInt("editingIndex");
                    mage.modifySpell(result, index);
                }
                case REMOVE -> {
                    //TODO: Implement Removal Logic
                    if (data.context().contains("slot"))
                    {
                        int index = data.context().getInt("slot");
                        mage.removeSpell(index);
                    }
                }
            }
        });
    }

    static void handleCast(final CastRequest data, final IPayloadContext context) {
        context.enqueueWork(() -> {
            ManaEntity mage = context.player().getData(ManaArtsAttachments.MANA_ENTITY);
            double manaCost = mage.getCurrentSpell().getManaCost();
            ManaSource source = new ManaSource(-manaCost, ManaSourceTags.MANA_COST);
            mage.modifyMana(context.player(), source);
            if (mage.getCurrentSpellIndex() == -1) return;
            if (!mage.getCurrentSpell().hasSpell()) {
                if (DeveloperSwitches.DEBUG_MODE)
                {
                    ManaArts.LOGGER.debug("Tried to cast spell but no spell is selected, creating debug spell");
                    mage.debugInit();
                }
                else
                {
                    ManaArts.LOGGER.warn("Tried to cast spell but no spell is selected");
                    context.player().sendSystemMessage(Component.translatable("mana_arts.no_spell_selected"));
                    return;
                }
            }
            if (ModList.get().isLoaded("epicfight") && EpicFightCapabilities.getPlayerPatch(context.player()) instanceof ServerPlayerPatch serverPlayerPatch)
            {
                if (serverPlayerPatch.getEntityState().canBasicAttack()) {
                    mage.queueSpell();
                    mage.getQueuedSpell().handleCastEpicFight(serverPlayerPatch);
                }
            }
            else
            {
                CastContext castContext = mage.getCurrentSpell().buildContext();
                mage.getCurrentSpell().handleCast(castContext, context.player());
            }
        });
    }

    static void handleLearnedDataSync(final LearnedDataSync packet, final IPayloadContext context)
    {
        context.enqueueWork(
                () -> {
                    ManaEntity mage = context.player().getData(ManaArtsAttachments.MANA_ENTITY);
                    if (packet.data().contains("elements")) {
                        ListTag elements = packet.data().getList("elements", ListTag.TAG_STRING);
                        mage.deserializeElements(elements).forEach(mage::addElement);
                    }

                    if (packet.data().contains("spells")) {
                        ListTag spells = packet.data().getList("spells", ListTag.TAG_STRING);
                        mage.deserializeLearnedSpells(spells).forEach(mage::addLearnedSpell);
                    }
                }
        );
    }
}
