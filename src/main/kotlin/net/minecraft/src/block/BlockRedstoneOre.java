package net.minecraft.src.block;

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.Block;
import net.minecraft.src.Material;
import net.minecraft.src.entity.Entity;
import net.minecraft.src.entity.EntityPlayer;
import net.minecraft.src.item.Item;

import java.util.Random;

public class BlockRedstoneOre extends Block {

    public BlockRedstoneOre(int i, int j, boolean flag) {
        super(i, j, Material.rock);
        if (flag) {
            setTickOnLoad(true);
        }
        field_468_a = flag;
    }

    public int tickRate() {
        return 30;
    }

    public void onBlockClicked(World world, int i, int j, int k, EntityPlayer entityplayer) {
        func_320_h(world, i, j, k);
        super.onBlockClicked(world, i, j, k, entityplayer);
    }

    public void onEntityWalking(World world, int i, int j, int k, Entity entity) {
        func_320_h(world, i, j, k);
        super.onEntityWalking(world, i, j, k, entity);
    }

    public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer) {
        func_320_h(world, i, j, k);
        return super.blockActivated(world, i, j, k, entityplayer);
    }

    private void func_320_h(World world, int i, int j, int k) {
        func_319_i(world, i, j, k);
        if (blockID == oreRedstone.blockID) {
            world.setBlockWithNotify(i, j, k, oreRedstoneGlowing.blockID);
        }
    }

    public void updateTick(World world, int i, int j, int k, Random random) {
        if (blockID == oreRedstoneGlowing.blockID) {
            world.setBlockWithNotify(i, j, k, oreRedstone.blockID);
        }
    }

    public int idDropped(int i, Random random) {
        return Item.redstone.shiftedIndex;
    }

    public int quantityDropped(Random random) {
        return 4 + random.nextInt(2);
    }

    public void randomDisplayTick(World world, int i, int j, int k, Random random) {
        if (field_468_a) {
            func_319_i(world, i, j, k);
        }
    }

    private void func_319_i(World world, int i, int j, int k) {
        Random random = world.rand;
        double d = 0.0625D;
        for (int l = 0; l < 6; l++) {
            double d1 = (float) i + random.nextFloat();
            double d2 = (float) j + random.nextFloat();
            double d3 = (float) k + random.nextFloat();
            if (l == 0 && !world.isBlockOpaqueCube(i, j + 1, k)) {
                d2 = (double) (j + 1) + d;
            }
            if (l == 1 && !world.isBlockOpaqueCube(i, j - 1, k)) {
                d2 = (double) (j + 0) - d;
            }
            if (l == 2 && !world.isBlockOpaqueCube(i, j, k + 1)) {
                d3 = (double) (k + 1) + d;
            }
            if (l == 3 && !world.isBlockOpaqueCube(i, j, k - 1)) {
                d3 = (double) (k + 0) - d;
            }
            if (l == 4 && !world.isBlockOpaqueCube(i + 1, j, k)) {
                d1 = (double) (i + 1) + d;
            }
            if (l == 5 && !world.isBlockOpaqueCube(i - 1, j, k)) {
                d1 = (double) (i + 0) - d;
            }
            if (d1 < (double) i || d1 > (double) (i + 1) || d2 < 0.0D || d2 > (double) (j + 1) || d3 < (double) k || d3 > (double) (k + 1)) {
                world.spawnParticle("reddust", d1, d2, d3, 0.0D, 0.0D, 0.0D);
            }
        }

    }

    private boolean field_468_a;
}
