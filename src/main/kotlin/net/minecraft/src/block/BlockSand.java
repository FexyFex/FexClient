package net.minecraft.src.block;

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import me.fexclient.MinecraftFexClientConfig;
import net.minecraft.src.Block;
import net.minecraft.src.Material;
import net.minecraft.src.entity.EntityFallingSand;

import java.util.Random;

public class BlockSand extends Block {
    public BlockSand(int i, int j) {
        super(i, j, Material.sand);
    }

    public void onBlockAdded(World world, int i, int j, int k) {
        world.scheduleBlockUpdate(i, j, k, blockID);
    }

    public void onNeighborBlockChange(World world, int i, int j, int k, int l) {
        world.scheduleBlockUpdate(i, j, k, blockID);
    }

    public void updateTick(World world, int i, int j, int k, Random random) {
        tryToFall(world, i, j, k);
    }

    private void tryToFall(World world, int i, int j, int k) {
        if (MinecraftFexClientConfig.doFalling) {
            int l = i;
            int i1 = j;
            int j1 = k;
            if (canFallBelow(world, l, i1 - 1, j1) && i1 >= 0) {
                EntityFallingSand entityfallingsand = new EntityFallingSand(world, (float) i + 0.5F, (float) j + 0.5F, (float) k + 0.5F, blockID);
                //if (fallInstantly) {
                if (false) {
                    while (!entityfallingsand.isDead) {
                        entityfallingsand.onUpdate();
                    }
                } else {
                    world.entityJoinedWorld(entityfallingsand);
                }
            }
        }
    }

    public int tickRate() {
        return 3;
    }

    public static boolean canFallBelow(World world, int i, int j, int k) {
        int l = world.getBlockId(i, j, k);
        if (l == 0) {
            return true;
        }
        if (l == fire.blockID) {
            return true;
        }
        Material material = blocksList[l].blockMaterial;
        if (material == Material.water) {
            return true;
        }
        return material == Material.lava;
    }

    public static boolean fallInstantly = false;

}
