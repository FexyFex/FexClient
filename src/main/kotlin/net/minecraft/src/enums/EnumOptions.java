package net.minecraft.src.enums;// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public enum EnumOptions
{
    MUSIC("options.music", true, false),
    SOUND("options.sound", true, false),
    INVERT_MOUSE("options.invertMouse", false, true),
    SENSITIVITY("options.sensitivity", true, false),
    RENDER_DISTANCE("options.renderDistance", false, false),
    VIEW_BOBBING("options.viewBobbing", false, true),
    ANAGLYPH("options.anaglyph", false, true),
    LIMIT_FRAMERATE("options.limitFramerate", false, true),
    DIFFICULTY("options.difficulty", false, false),
    GRAPHICS("options.graphics", false, false);
/*
    public static net.minecraft.src.enums.EnumOptions[] values()
    {
        return (net.minecraft.src.enums.EnumOptions[])field_20141_n.clone();
    }

    public static net.minecraft.src.enums.EnumOptions valueOf(String s)
    {
        return (net.minecraft.src.enums.EnumOptions)Enum.valueOf(net.minecraft.src.enums.EnumOptions.class, s);
    }
*/
    public static EnumOptions func_20137_a(int i)
    {
        EnumOptions aenumoptions[] = values();
        int j = aenumoptions.length;
        for(int k = 0; k < j; k++)
        {
            EnumOptions enumoptions = aenumoptions[k];
            if(enumoptions.func_20135_c() == i)
            {
                return enumoptions;
            }
        }

        return null;
    }

    private EnumOptions(String s1, boolean flag, boolean flag1)
    {
        field_20142_m = s1;
        field_20144_k = flag;
        field_20143_l = flag1;
    }

    public boolean func_20136_a()
    {
        return field_20144_k;
    }

    public boolean func_20140_b()
    {
        return field_20143_l;
    }

    public int func_20135_c()
    {
        return ordinal();
    }

    public String func_20138_d()
    {
        return field_20142_m;
    }
/*
    public static final net.minecraft.src.enums.EnumOptions MUSIC;
    public static final net.minecraft.src.enums.EnumOptions SOUND;
    public static final net.minecraft.src.enums.EnumOptions INVERT_MOUSE;
    public static final net.minecraft.src.enums.EnumOptions SENSITIVITY;
    public static final net.minecraft.src.enums.EnumOptions RENDER_DISTANCE;
    public static final net.minecraft.src.enums.EnumOptions VIEW_BOBBING;
    public static final net.minecraft.src.enums.EnumOptions ANAGLYPH;
    public static final net.minecraft.src.enums.EnumOptions LIMIT_FRAMERATE;
    public static final net.minecraft.src.enums.EnumOptions DIFFICULTY;
    public static final net.minecraft.src.enums.EnumOptions GRAPHICS;
*/
    private final boolean field_20144_k;
    private final boolean field_20143_l;
    private final String field_20142_m;
/*
    private static final net.minecraft.src.enums.EnumOptions field_20141_n[]; /* synthetic field */
/*
    static 
    {
        MUSIC = new net.minecraft.src.enums.EnumOptions("MUSIC", 0, "options.music", true, false);
        SOUND = new net.minecraft.src.enums.EnumOptions("SOUND", 1, "options.sound", true, false);
        INVERT_MOUSE = new net.minecraft.src.enums.EnumOptions("INVERT_MOUSE", 2, "options.invertMouse", false, true);
        SENSITIVITY = new net.minecraft.src.enums.EnumOptions("SENSITIVITY", 3, "options.sensitivity", true, false);
        RENDER_DISTANCE = new net.minecraft.src.enums.EnumOptions("RENDER_DISTANCE", 4, "options.renderDistance", false, false);
        VIEW_BOBBING = new net.minecraft.src.enums.EnumOptions("VIEW_BOBBING", 5, "options.viewBobbing", false, true);
        ANAGLYPH = new net.minecraft.src.enums.EnumOptions("ANAGLYPH", 6, "options.anaglyph", false, true);
        LIMIT_FRAMERATE = new net.minecraft.src.enums.EnumOptions("LIMIT_FRAMERATE", 7, "options.limitFramerate", false, true);
        DIFFICULTY = new net.minecraft.src.enums.EnumOptions("DIFFICULTY", 8, "options.difficulty", false, false);
        GRAPHICS = new net.minecraft.src.enums.EnumOptions("GRAPHICS", 9, "options.graphics", false, false);
        field_20141_n = (new net.minecraft.src.enums.EnumOptions[] {
            MUSIC, SOUND, INVERT_MOUSE, SENSITIVITY, RENDER_DISTANCE, VIEW_BOBBING, ANAGLYPH, LIMIT_FRAMERATE, DIFFICULTY, GRAPHICS
        });
    }
*/
}
