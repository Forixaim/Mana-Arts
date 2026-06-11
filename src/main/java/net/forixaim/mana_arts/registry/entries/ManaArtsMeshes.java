package net.forixaim.mana_arts.registry.entries;

import net.forixaim.mana_arts.ManaArts;
import yesman.epicfight.api.client.model.ClassicMesh;
import yesman.epicfight.api.client.model.Meshes;

public class ManaArtsMeshes
{
    public static Meshes.MeshAccessor<ClassicMesh> SPHERE = Meshes.MeshAccessor.create(ManaArts.MOD_ID, "particle/sphere", jsonAssetLoader -> jsonAssetLoader.loadClassicMesh(ClassicMesh::new));

}
