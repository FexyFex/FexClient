package net.minecraft.src.block;// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


import net.minecraft.src.Block;
import net.minecraft.src.Material;
import net.minecraft.src.entity.TileEntity;

public abstract class BlockContainer extends Block
{

    protected BlockContainer(int i, Material material)
    {
        super(i, material);
        isBlockContainer[i] = true;
    }

    protected BlockContainer(int i, int j, Material material)
    {
        super(i, j, material);
    }

    public void onBlockAdded(World world, int i, int j, int k)
    {
        super.onBlockAdded(world, i, j, k);
        world.setBlockTileEntity(i, j, k, SetBlockEntity());
    }

    public void onBlockRemoval(World world, int i, int j, int k)
    {
        super.onBlockRemoval(world, i, j, k);
        world.removeBlockTileEntity(i, j, k);
    }

    protected abstract TileEntity SetBlockEntity();
}
