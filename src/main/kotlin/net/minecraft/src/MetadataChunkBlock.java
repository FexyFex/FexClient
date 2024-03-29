package net.minecraft.src;// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


import net.minecraft.src.Block;
import net.minecraft.src.enums.EnumSkyBlock;
import net.minecraft.src.block.World;

public class MetadataChunkBlock
{

    public MetadataChunkBlock(EnumSkyBlock enumskyblock, int i, int j, int k, int l, int i1, int j1)
    {
        field_1299_a = enumskyblock;
        field_1298_b = i;
        field_1304_c = j;
        field_1303_d = k;
        field_1302_e = l;
        field_1301_f = i1;
        field_1300_g = j1;
    }

    public void func_4127_a(World world)
    {
        int i = (field_1302_e - field_1298_b) + 1;
        int j = (field_1301_f - field_1304_c) + 1;
        int k = (field_1300_g - field_1303_d) + 1;
        int l = i * j * k;
        if(l > 32768)
        {
            return;
        }
        for(int i1 = field_1298_b; i1 <= field_1302_e; i1++)
        {
            for(int j1 = field_1303_d; j1 <= field_1300_g; j1++)
            {
                if(!world.blockExists(i1, 0, j1))
                {
                    continue;
                }
                for(int k1 = field_1304_c; k1 <= field_1301_f; k1++)
                {
                    if(k1 < 0 || k1 >= 128)
                    {
                        continue;
                    }
                    int l1 = world.getSavedLightValue(field_1299_a, i1, k1, j1);
                    int i2 = 0;
                    int blockId = world.getBlockId(i1, k1, j1);
                    int k2 = Block.lightOpacity[blockId];
                    if(k2 == 0)
                    {
                        k2 = 1;
                    }
                    int l2 = 0;
                    if(field_1299_a == EnumSkyBlock.Sky)
                    {
                        if(world.canExistingBlockSeeTheSky(i1, k1, j1))
                        {
                            l2 = 15;
                        }
                    } else
                    if(field_1299_a == EnumSkyBlock.Block)
                    {
                        l2 = Block.lightValue[blockId];
                    }
                    if(k2 >= 15 && l2 == 0)
                    {
                        i2 = 0;
                    } else
                    {
                        int i3 = world.getSavedLightValue(field_1299_a, i1 - 1, k1, j1);
                        int k3 = world.getSavedLightValue(field_1299_a, i1 + 1, k1, j1);
                        int l3 = world.getSavedLightValue(field_1299_a, i1, k1 - 1, j1);
                        int i4 = world.getSavedLightValue(field_1299_a, i1, k1 + 1, j1);
                        int j4 = world.getSavedLightValue(field_1299_a, i1, k1, j1 - 1);
                        int k4 = world.getSavedLightValue(field_1299_a, i1, k1, j1 + 1);
                        i2 = i3;
                        if(k3 > i2)
                        {
                            i2 = k3;
                        }
                        if(l3 > i2)
                        {
                            i2 = l3;
                        }
                        if(i4 > i2)
                        {
                            i2 = i4;
                        }
                        if(j4 > i2)
                        {
                            i2 = j4;
                        }
                        if(k4 > i2)
                        {
                            i2 = k4;
                        }
                        i2 -= k2;
                        if(i2 < 0)
                        {
                            i2 = 0;
                        }
                        if(l2 > i2)
                        {
                            i2 = l2;
                        }
                    }
                    if(l1 == i2)
                    {
                        continue;
                    }
                    world.setLightValue(field_1299_a, i1, k1, j1, i2);
                    int j3 = i2 - 1;
                    if(j3 < 0)
                    {
                        j3 = 0;
                    }
                    world.neighborLightPropagationChanged(field_1299_a, i1 - 1, k1, j1, j3);
                    world.neighborLightPropagationChanged(field_1299_a, i1, k1 - 1, j1, j3);
                    world.neighborLightPropagationChanged(field_1299_a, i1, k1, j1 - 1, j3);
                    if(i1 + 1 >= field_1302_e)
                    {
                        world.neighborLightPropagationChanged(field_1299_a, i1 + 1, k1, j1, j3);
                    }
                    if(k1 + 1 >= field_1301_f)
                    {
                        world.neighborLightPropagationChanged(field_1299_a, i1, k1 + 1, j1, j3);
                    }
                    if(j1 + 1 >= field_1300_g)
                    {
                        world.neighborLightPropagationChanged(field_1299_a, i1, k1, j1 + 1, j3);
                    }
                }

            }

        }

    }

    public boolean func_866_a(int i, int j, int k, int l, int i1, int j1)
    {
        if(i >= field_1298_b && j >= field_1304_c && k >= field_1303_d && l <= field_1302_e && i1 <= field_1301_f && j1 <= field_1300_g)
        {
            return true;
        }
        int k1 = 1;
        if(i >= field_1298_b - k1 && j >= field_1304_c - k1 && k >= field_1303_d - k1 && l <= field_1302_e + k1 && i1 <= field_1301_f + k1 && j1 <= field_1300_g + k1)
        {
            int l1 = field_1302_e - field_1298_b;
            int i2 = field_1301_f - field_1304_c;
            int j2 = field_1300_g - field_1303_d;
            if(i > field_1298_b)
            {
                i = field_1298_b;
            }
            if(j > field_1304_c)
            {
                j = field_1304_c;
            }
            if(k > field_1303_d)
            {
                k = field_1303_d;
            }
            if(l < field_1302_e)
            {
                l = field_1302_e;
            }
            if(i1 < field_1301_f)
            {
                i1 = field_1301_f;
            }
            if(j1 < field_1300_g)
            {
                j1 = field_1300_g;
            }
            int k2 = l - i;
            int l2 = i1 - j;
            int i3 = j1 - k;
            int j3 = l1 * i2 * j2;
            int k3 = k2 * l2 * i3;
            if(k3 - j3 <= 2)
            {
                field_1298_b = i;
                field_1304_c = j;
                field_1303_d = k;
                field_1302_e = l;
                field_1301_f = i1;
                field_1300_g = j1;
                return true;
            }
        }
        return false;
    }

    public final EnumSkyBlock field_1299_a;
    public int field_1298_b;
    public int field_1304_c;
    public int field_1303_d;
    public int field_1302_e;
    public int field_1301_f;
    public int field_1300_g;
}
