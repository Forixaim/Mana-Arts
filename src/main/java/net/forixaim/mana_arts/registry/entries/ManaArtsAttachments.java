package net.forixaim.mana_arts.registry.entries;

import net.forixaim.mana_arts.ManaArts;
import net.forixaim.mana_arts.world.capabilities.ManaEntity;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ManaArtsAttachments
{
    public static final DeferredRegister<AttachmentType<?>> REGISTRY = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, ManaArts.MOD_ID);
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<ManaEntity>> MANA_ENTITY = REGISTRY.register("mana_entity", () -> AttachmentType.builder(ManaEntity::new).sync().build());

}
