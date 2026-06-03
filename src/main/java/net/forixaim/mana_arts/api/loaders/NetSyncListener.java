package net.forixaim.mana_arts.api.loaders;

import net.forixaim.mana_arts.netcode.server.DatapackSync;

public interface NetSyncListener
{
    void sync(final DatapackSync sync);
}
