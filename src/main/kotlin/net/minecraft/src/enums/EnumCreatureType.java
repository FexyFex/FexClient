package net.minecraft.src.enums;// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


import net.minecraft.src.IMobs;
import net.minecraft.src.entity.EntityAnimals;

public enum EnumCreatureType {
    monster(IMobs.class, 100),
    creature(EntityAnimals.class, 20);

    /*
        public static net.minecraft.src.enums.EnumCreatureType[] values()
        {
            return (net.minecraft.src.enums.EnumCreatureType[])field_6518_e.clone();
        }

        public static net.minecraft.src.enums.EnumCreatureType valueOf(String s)
        {
            return (net.minecraft.src.enums.EnumCreatureType)Enum.valueOf(net.minecraft.src.enums.EnumCreatureType.class, s);
        }
    */
    private EnumCreatureType(Class class1, int j) {
        field_4278_c = class1;
        maxNumberOfEntityType = j;
    }

    /*
        public static final net.minecraft.src.enums.EnumCreatureType monster;
        public static final net.minecraft.src.enums.EnumCreatureType creature;
    */
    public final Class field_4278_c;
    public final int maxNumberOfEntityType;
/*
    private static final net.minecraft.src.enums.EnumCreatureType field_6518_e[]; /* synthetic field */
/*
    static 
    {
        monster = new net.minecraft.src.enums.EnumCreatureType("monster", 0, net.minecraft.src.IMobs.class, 100);
        creature = new net.minecraft.src.enums.EnumCreatureType("creature", 1, net.minecraft.src.entity.EntityAnimals.class, 20);
        field_6518_e = (new net.minecraft.src.enums.EnumCreatureType[] {
            monster, creature
        });
    }
*/
}
