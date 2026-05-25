package net.forixaim.mana_arts.api.data;


public class SpellModifier
{
    private double minValue;
    private double maxValue;
    private double defaultValue;

    public SpellModifier(double defaultValue, double minValue, double maxValue)
    {

    }

    public static SpellModifier createRanged(double minValue, double maxValue, double defaultValue)
    {
        return new SpellModifier(defaultValue, minValue, maxValue);
    }

    public static SpellModifier createBoolean(Boolean defaultValue)
    {
        double result = 0;
        if (defaultValue)
            result = 1;
        return new SpellModifier(result, 0, 1);
    }
}
