package net.forixaim.mana_arts.client.input;

import com.mojang.blaze3d.platform.InputConstants;
import net.forixaim.mana_arts.generated.LangKeys;
import net.minecraft.client.KeyMapping;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.neoforge.client.settings.KeyModifier;
import org.lwjgl.glfw.GLFW;

public class ManaArtsKeyMapping
{
    public static KeyMapping CAST_SPELL = mapping(LangKeys.KEY_CAST_SPELL, GLFW.GLFW_KEY_Z);
    public static KeyMapping OPEN_SPELL_MENU = mapping(LangKeys.KEY_OPEN_SPELL_MENU, GLFW.GLFW_KEY_K);


    public static KeyMapping mapping(String name, int keyCode)
    {
        return new KeyMapping(name, KeyConflictContext.IN_GAME, KeyModifier.CONTROL, InputConstants.Type.KEYSYM, keyCode, LangKeys.KEY_CATEGORIES);
    }
}
