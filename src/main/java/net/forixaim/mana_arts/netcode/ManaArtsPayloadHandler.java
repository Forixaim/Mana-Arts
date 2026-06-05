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
import net.forixaim.mana_arts.netcode.server.mana_entity.ManaValueSync;
import net.forixaim.mana_arts.netcode.server.mana_entity.SpellElementSync;
import net.forixaim.mana_arts.registry.entries.ManaArtsAttachments;
import net.forixaim.mana_arts.registry.entries.ManaArtsElements;
import net.forixaim.mana_arts.registry.entries.ManaArtsSpells;
import net.forixaim.mana_arts.world.capabilities.ManaEntity;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import yesman.epicfight.api.exception.DatapackException;


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
                    SpellContainer debugSpell = new SpellContainer();
                    debugSpell.setSpell(ManaArtsSpells.BLAST);
                    debugSpell.setElement(ManaArtsElements.LIGHT);
                    mage.addSpell(debugSpell);
                }
                case MODIFY -> {
                    //TODO: Implement Modification Logic
                }
                case REMOVE -> {
                    //TODO: Implement Removal Logic
                }
            }
        });
    }

    static void handleCast(final CastRequest data, final IPayloadContext context) {
        context.enqueueWork(() -> {
            ManaEntity mage = context.player().getData(ManaArtsAttachments.MANA_ENTITY);
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
            CastContext castContext = mage.getCurrentSpell().buildContext();
            mage.getCurrentSpell().handleCast(castContext, context.player());
        });
    }
}
