package net.minecraft.src.block;// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Material;
import net.minecraft.src.enums.EnumSkyBlock;

import java.util.Random;

public class BlockIce extends BlockBreakable {

    public BlockIce(int i, int j) {
        super(i, j, Material.ice, false);
        slipperiness = 0.98F;
        setTickOnLoad(true);
    }

    public int getRenderBlockPass() {
        return 1;
    }

    public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int i, int j, int k, int l) {
        return super.shouldSideBeRendered(iblockaccess, i, j, k, 1 - l);
    }

    public void onBlockRemoval(World world, int i, int j, int k) {
        Material material = world.getBlockMaterial(i, j - 1, k);
        if (material.getIsSolid() || material.getIsLiquid()) {
            world.setBlockWithNotify(i, j, k, waterStill.blockID);
        }
    }

    public int quantityDropped(Random random) {
        return 0;
    }

    public void updateTick(World world, int i, int j, int k, Random random) {
        if (world.getSavedLightValue(EnumSkyBlock.Block, i, j, k) > 11 - lightOpacity[blockID]) {
            dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k));
            world.setBlockWithNotify(i, j, k, waterMoving.blockID);
        }
    }
}
