package net.minecraft.src.block;

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.Block;
import net.minecraft.src.Material;
import net.minecraft.src.item.Item;

import java.util.Random;

public class BlockOre extends Block {

    public BlockOre(int i, int j) {
        super(i, j, Material.rock);
    }

    public int idDropped(int i, Random random) {
        if (blockID == oreCoal.blockID) {
            return Item.coal.shiftedIndex;
        }
        if (blockID == oreDiamond.blockID) {
            return Item.diamond.shiftedIndex;
        } else {
            return blockID;
        }
    }

    public int quantityDropped(Random random) {
        return 1;
    }
}
