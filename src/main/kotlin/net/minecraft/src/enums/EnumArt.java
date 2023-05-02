package net.minecraft.src.enums;

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public enum EnumArt
{
    Kebab("Kebab", 16, 16, 0, 0),
    Aztec("Aztec", 16, 16, 16, 0),
    Alban("Alban", 16, 16, 32, 0),
    Aztec2("Aztec2", 16, 16, 48, 0),
    Bomb("Bomb", 16, 16, 64, 0),
    Plant("Plant", 16, 16, 80, 0),
    Wasteland("Wasteland", 16, 16, 96, 0),
    Pool("Pool", 32, 16, 0, 32),
    Courbet("Courbet", 32, 16, 32, 32),
    Sea("Sea", 32, 16, 64, 32),
    Sunset("Sunset", 32, 16, 96, 32),
    Creebet("Creebet", 32, 16, 128, 32),
    Wanderer("Wanderer", 16, 32, 0, 64),
    Graham("Graham", 16, 32, 16, 64),
    Match("Match", 32, 32, 0, 128),
    Bust("Bust", 32, 32, 32, 128),
    Stage("Stage", 32, 32, 64, 128),
    Void("Void", 32, 32, 96, 128),
    SkullAndRoses("SkullAndRoses", 32, 32, 128, 128),
    Fighters("Fighters", 64, 32, 0, 96),
    Pointer("Pointer", 64, 64, 0, 192),
    Pigscene("Pigscene", 64, 64, 64, 192),
    Skeleton("Skeleton", 64, 48, 192, 64),
    DonkeyKong("DonkeyKong", 64, 48, 192, 112);
/*
    public static net.minecraft.src.enums.EnumArt[] values()
    {
        return (net.minecraft.src.enums.EnumArt[])field_1630_D.clone();
    }

    public static net.minecraft.src.enums.EnumArt valueOf(String s)
    {
        return (net.minecraft.src.enums.EnumArt)Enum.valueOf(net.minecraft.src.enums.EnumArt.class, s);
    }
*/
    private EnumArt(String s1, int j, int k, int l, int i1)
    {
        field_1624_y = s1;
        field_1623_z = j;
        field_1636_A = k;
        field_1634_B = l;
        field_1632_C = i1;
    }
/*
    public static final net.minecraft.src.enums.EnumArt Kebab;
    public static final net.minecraft.src.enums.EnumArt Aztec;
    public static final net.minecraft.src.enums.EnumArt Alban;
    public static final net.minecraft.src.enums.EnumArt Aztec2;
    public static final net.minecraft.src.enums.EnumArt Bomb;
    public static final net.minecraft.src.enums.EnumArt Plant;
    public static final net.minecraft.src.enums.EnumArt Wasteland;
    public static final net.minecraft.src.enums.EnumArt Pool;
    public static final net.minecraft.src.enums.EnumArt Courbet;
    public static final net.minecraft.src.enums.EnumArt Sea;
    public static final net.minecraft.src.enums.EnumArt Sunset;
    public static final net.minecraft.src.enums.EnumArt Creebet;
    public static final net.minecraft.src.enums.EnumArt Wanderer;
    public static final net.minecraft.src.enums.EnumArt Graham;
    public static final net.minecraft.src.enums.EnumArt Match;
    public static final net.minecraft.src.enums.EnumArt Bust;
    public static final net.minecraft.src.enums.EnumArt Stage;
    public static final net.minecraft.src.enums.EnumArt Void;
    public static final net.minecraft.src.enums.EnumArt SkullAndRoses;
    public static final net.minecraft.src.enums.EnumArt Fighters;
    public static final net.minecraft.src.enums.EnumArt Pointer;
    public static final net.minecraft.src.enums.EnumArt Pigscene;
    public static final net.minecraft.src.enums.EnumArt Skeleton;
    public static final net.minecraft.src.enums.EnumArt DonkeyKong;
*/
    public final String field_1624_y;
    public final int field_1623_z;
    public final int field_1636_A;
    public final int field_1634_B;
    public final int field_1632_C;
/*
    private static final net.minecraft.src.enums.EnumArt field_1630_D[]; /* synthetic field */
/*
    static 
    {
        Kebab = new net.minecraft.src.enums.EnumArt("Kebab", 0, "Kebab", 16, 16, 0, 0);
        Aztec = new net.minecraft.src.enums.EnumArt("Aztec", 1, "Aztec", 16, 16, 16, 0);
        Alban = new net.minecraft.src.enums.EnumArt("Alban", 2, "Alban", 16, 16, 32, 0);
        Aztec2 = new net.minecraft.src.enums.EnumArt("Aztec2", 3, "Aztec2", 16, 16, 48, 0);
        Bomb = new net.minecraft.src.enums.EnumArt("Bomb", 4, "Bomb", 16, 16, 64, 0);
        Plant = new net.minecraft.src.enums.EnumArt("Plant", 5, "Plant", 16, 16, 80, 0);
        Wasteland = new net.minecraft.src.enums.EnumArt("Wasteland", 6, "Wasteland", 16, 16, 96, 0);
        Pool = new net.minecraft.src.enums.EnumArt("Pool", 7, "Pool", 32, 16, 0, 32);
        Courbet = new net.minecraft.src.enums.EnumArt("Courbet", 8, "Courbet", 32, 16, 32, 32);
        Sea = new net.minecraft.src.enums.EnumArt("Sea", 9, "Sea", 32, 16, 64, 32);
        Sunset = new net.minecraft.src.enums.EnumArt("Sunset", 10, "Sunset", 32, 16, 96, 32);
        Creebet = new net.minecraft.src.enums.EnumArt("Creebet", 11, "Creebet", 32, 16, 128, 32);
        Wanderer = new net.minecraft.src.enums.EnumArt("Wanderer", 12, "Wanderer", 16, 32, 0, 64);
        Graham = new net.minecraft.src.enums.EnumArt("Graham", 13, "Graham", 16, 32, 16, 64);
        Match = new net.minecraft.src.enums.EnumArt("Match", 14, "Match", 32, 32, 0, 128);
        Bust = new net.minecraft.src.enums.EnumArt("Bust", 15, "Bust", 32, 32, 32, 128);
        Stage = new net.minecraft.src.enums.EnumArt("Stage", 16, "Stage", 32, 32, 64, 128);
        Void = new net.minecraft.src.enums.EnumArt("Void", 17, "Void", 32, 32, 96, 128);
        SkullAndRoses = new net.minecraft.src.enums.EnumArt("SkullAndRoses", 18, "SkullAndRoses", 32, 32, 128, 128);
        Fighters = new net.minecraft.src.enums.EnumArt("Fighters", 19, "Fighters", 64, 32, 0, 96);
        Pointer = new net.minecraft.src.enums.EnumArt("Pointer", 20, "Pointer", 64, 64, 0, 192);
        Pigscene = new net.minecraft.src.enums.EnumArt("Pigscene", 21, "Pigscene", 64, 64, 64, 192);
        Skeleton = new net.minecraft.src.enums.EnumArt("Skeleton", 22, "Skeleton", 64, 48, 192, 64);
        DonkeyKong = new net.minecraft.src.enums.EnumArt("DonkeyKong", 23, "DonkeyKong", 64, 48, 192, 112);
        field_1630_D = (new net.minecraft.src.enums.EnumArt[] {
            Kebab, Aztec, Alban, Aztec2, Bomb, Plant, Wasteland, Pool, Courbet, Sea, 
            Sunset, Creebet, Wanderer, Graham, Match, Bust, Stage, Void, SkullAndRoses, Fighters, 
            Pointer, Pigscene, Skeleton, DonkeyKong
        });
    }
*/
}
