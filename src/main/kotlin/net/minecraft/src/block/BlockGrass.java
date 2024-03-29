package net.minecraft.src.block;// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.Block;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Material;
import net.minecraft.src.colorizer.ColorizerGrass;

import java.util.Random;

public class BlockGrass extends Block {

    public BlockGrass(int i) {
        super(i, Material.ground);
        blockIndexInTexture = 3;
        setTickOnLoad(true);
    }

    public int getBlockTexture(IBlockAccess iblockaccess, int x, int y, int z, int side) {
        if (side == 1) {
            return 0;
        }
        if (side == 0) {
            return 2;
        }
        Material material = iblockaccess.getBlockMaterial(x, y + 1, z);
        return material != Material.snow && material != Material.builtSnow ? 3 : 68;
    }

    public int colorMultiplier(IBlockAccess iblockaccess, int i, int j, int k) {
        iblockaccess.func_4075_a().func_4069_a(i, k, 1, 1);
        double d = iblockaccess.func_4075_a().temperature[0];
        double d1 = iblockaccess.func_4075_a().humidity[0];
        return ColorizerGrass.func_4147_a(d, d1);
    }

    public void updateTick(World world, int i, int j, int k, Random random) {
        if (world.multiplayerWorld) {
            return;
        }
        if (world.getBlockLightValue(i, j + 1, k) < 4 && world.getBlockMaterial(i, j + 1, k).getCanBlockGrass()) {
            if (random.nextInt(4) != 0) {
                return;
            }
            world.setBlockWithNotify(i, j, k, dirt.blockID);
        } else if (world.getBlockLightValue(i, j + 1, k) >= 9) {
            int l = (i + random.nextInt(3)) - 1;
            int i1 = (j + random.nextInt(5)) - 3;
            int j1 = (k + random.nextInt(3)) - 1;
            if (world.getBlockId(l, i1, j1) == dirt.blockID && world.getBlockLightValue(l, i1 + 1, j1) >= 4 && !world.getBlockMaterial(l, i1 + 1, j1).getCanBlockGrass()) {
                world.setBlockWithNotify(l, i1, j1, grass.blockID);
            }
        }
    }

    public int idDropped(int i, Random random) {
        return dirt.idDropped(0, random);
    }
}
