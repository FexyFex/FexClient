package net.minecraft.src.enums;// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public enum EnumSkyBlock
{
    Sky(15),
    Block(0);
/*
    public static net.minecraft.src.enums.EnumSkyBlock[] values()
    {
        return (net.minecraft.src.enums.EnumSkyBlock[])field_1721_d.clone();
    }

    public static net.minecraft.src.enums.EnumSkyBlock valueOf(String s)
    {
        return (net.minecraft.src.enums.EnumSkyBlock)Enum.valueOf(net.minecraft.src.enums.EnumSkyBlock.class, s);
    }
*/
    private EnumSkyBlock(int j)
    {
        field_1722_c = j;
    }
/*
    public static final net.minecraft.src.enums.EnumSkyBlock Sky;
    public static final net.minecraft.src.enums.EnumSkyBlock net.minecraft.src.Block;
*/
    public final int field_1722_c;
/*
    private static final net.minecraft.src.enums.EnumSkyBlock field_1721_d[]; /* synthetic field */
/*
    static 
    {
        Sky = new net.minecraft.src.enums.EnumSkyBlock("Sky", 0, 15);
        net.minecraft.src.Block = new net.minecraft.src.enums.EnumSkyBlock("net.minecraft.src.Block", 1, 0);
        field_1721_d = (new net.minecraft.src.enums.EnumSkyBlock[] {
            Sky, net.minecraft.src.Block
        });
    }
*/
}
