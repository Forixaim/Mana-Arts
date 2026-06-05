package net.forixaim.mana_arts.registry.entries;

import net.forixaim.mana_arts.ManaArts;
import net.forixaim.mana_arts.api.data.spell.SpellModifier;
import net.forixaim.mana_arts.registry.registers.SpellModifierRegister;
import net.forixaim.mana_arts.registry.registers.holders.DeferredSpellModifier;
import net.forixaim.mana_arts.world.entity.spell.DetonatingSpellProjectile;
import net.forixaim.mana_arts.world.entity.spell.SpellProjectile;

public class ManaArtsSpellModifiers
{
    public static final SpellModifierRegister REGISTRY = SpellModifierRegister.create(ManaArts.MOD_ID);

    public static final DeferredSpellModifier<SpellModifier<SpellProjectile>> PROJECTILE_SIZE = REGISTRY.registerModifier(
            "projectile_size",
            id -> SpellModifier.createRanged(1.0f, 100.0, 100.0, 0, 0, spellProjectile ->
                    spellProjectile.addScaleModifier(spellProjectile.getCastContext().modifiers().get(id) / 100.0))
    );

    public static final DeferredSpellModifier<SpellModifier<DetonatingSpellProjectile>> BLAST_RADIUS = REGISTRY.registerModifier(
            "blast_radius",
            id -> SpellModifier.<DetonatingSpellProjectile>createRanged(1.0f, 100.0, 100.0, 0, 0, spellProjectile ->
                    spellProjectile.addBlastModifier(spellProjectile.getCastContext().modifiers().get(id) / 100.0))
    );
}
