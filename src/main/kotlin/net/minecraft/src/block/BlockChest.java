package net.minecraft.src.block;// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.Block;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Material;
import net.minecraft.src.entity.EntityItem;
import net.minecraft.src.entity.EntityPlayer;
import net.minecraft.src.entity.TileEntity;
import net.minecraft.src.entity.TileEntityChest;
import net.minecraft.src.inventory.IInventory;
import net.minecraft.src.inventory.InventoryLargeChest;
import net.minecraft.src.item.ItemStack;
import org.lwjgl.Sys;

import java.util.Random;

public class BlockChest extends BlockContainer
{

    public BlockChest(int i)
    {
        super(i, Material.wood);
        random = new Random();
        blockIndexInTexture = 26;
    }

    public int getBlockTexture(IBlockAccess iblockaccess, int x, int y, int z, int side)
    {
        if(side == 1) {
            return blockIndexInTexture - 1;
        }
        if(side == 0) {
            return blockIndexInTexture - 1;
        }
        int blockLeft = iblockaccess.getBlockId(x, y, z - 1);
        int blockRight = iblockaccess.getBlockId(x, y, z + 1);
        int blockBehind = iblockaccess.getBlockId(x - 1, y, z);
        int blockFront = iblockaccess.getBlockId(x + 1, y, z);
        if (blockLeft == blockID || blockRight == blockID) {
            if (side == 2 || side == 3) {
                return blockIndexInTexture;
            }
            int i2 = 0;
            if(blockLeft == blockID)
            {
                i2 = -1;
            }
            int k2 = iblockaccess.getBlockId(x - 1, y, blockLeft != blockID ? z + 1 : z - 1);
            int i3 = iblockaccess.getBlockId(x + 1, y, blockLeft != blockID ? z + 1 : z - 1);
            if(side == 4)
            {
                i2 = -1 - i2;
            }
            byte byte1 = 5;
            if((Block.opaqueCubeLookup[blockBehind] || Block.opaqueCubeLookup[k2]) && !Block.opaqueCubeLookup[blockFront] && !Block.opaqueCubeLookup[i3]) {
                byte1 = 5;
            }
            if((Block.opaqueCubeLookup[blockFront] || Block.opaqueCubeLookup[i3]) && !Block.opaqueCubeLookup[blockBehind] && !Block.opaqueCubeLookup[k2]) {
                byte1 = 4;
            }
            return (side != byte1 ? blockIndexInTexture + 32 : blockIndexInTexture + 16) + i2;
        }
        if(blockBehind == blockID || blockFront == blockID)
        {
            if(side == 4 || side == 5)
            {
                return blockIndexInTexture;
            }
            int j2 = 0;
            if(blockBehind == blockID)
            {
                j2 = -1;
            }
            int l2 = iblockaccess.getBlockId(blockBehind != blockID ? x + 1 : x - 1, y, z - 1);
            int j3 = iblockaccess.getBlockId(blockBehind != blockID ? x + 1 : x - 1, y, z + 1);
            if(side == 3)
            {
                j2 = -1 - j2;
            }
            byte byte2 = 3;
            if((Block.opaqueCubeLookup[blockLeft] || Block.opaqueCubeLookup[l2]) && !Block.opaqueCubeLookup[blockRight] && !Block.opaqueCubeLookup[j3])
            {
                byte2 = 3;
            }
            if((Block.opaqueCubeLookup[blockRight] || Block.opaqueCubeLookup[j3]) && !Block.opaqueCubeLookup[blockLeft] && !Block.opaqueCubeLookup[l2])
            {
                byte2 = 2;
            }
            return (side != byte2 ? blockIndexInTexture + 32 : blockIndexInTexture + 16) + j2;
        }
        byte byte0 = 3;
        if(Block.opaqueCubeLookup[blockLeft] && !Block.opaqueCubeLookup[blockRight])
        {
            byte0 = 3;
        }
        if(Block.opaqueCubeLookup[blockRight] && !Block.opaqueCubeLookup[blockLeft])
        {
            byte0 = 2;
        }
        if(Block.opaqueCubeLookup[blockBehind] && !Block.opaqueCubeLookup[blockFront])
        {
            byte0 = 5;
        }
        if(Block.opaqueCubeLookup[blockFront] && !Block.opaqueCubeLookup[blockBehind])
        {
            byte0 = 4;
        }
        return side != byte0 ? blockIndexInTexture : blockIndexInTexture + 1;
    }

    public int getBlockTextureFromSide(int i) {
        if(i == 1) {
            return blockIndexInTexture - 1;
        }
        if(i == 0) {
            return blockIndexInTexture - 1;
        }
        if(i == 3) {
            return blockIndexInTexture + 1;
        } else {
            return blockIndexInTexture;
        }
    }

    public boolean canPlaceBlockAt(World world, int i, int j, int k)
    {
        int l = 0;
        if(world.getBlockId(i - 1, j, k) == blockID)
        {
            l++;
        }
        if(world.getBlockId(i + 1, j, k) == blockID)
        {
            l++;
        }
        if(world.getBlockId(i, j, k - 1) == blockID)
        {
            l++;
        }
        if(world.getBlockId(i, j, k + 1) == blockID)
        {
            l++;
        }
        if(l > 1)
        {
            return false;
        }
        if(isThereANeighborChest(world, i - 1, j, k))
        {
            return false;
        }
        if(isThereANeighborChest(world, i + 1, j, k))
        {
            return false;
        }
        if(isThereANeighborChest(world, i, j, k - 1))
        {
            return false;
        }
        return !isThereANeighborChest(world, i, j, k + 1);
    }

    private boolean isThereANeighborChest(World world, int i, int j, int k)
    {
        if(world.getBlockId(i, j, k) != blockID)
        {
            return false;
        }
        if(world.getBlockId(i - 1, j, k) == blockID)
        {
            return true;
        }
        if(world.getBlockId(i + 1, j, k) == blockID)
        {
            return true;
        }
        if(world.getBlockId(i, j, k - 1) == blockID)
        {
            return true;
        }
        return world.getBlockId(i, j, k + 1) == blockID;
    }

    public void onBlockRemoval(World world, int i, int j, int k)
    {
        TileEntityChest tileentitychest = (TileEntityChest)world.getBlockTileEntity(i, j, k);
label0:
        for(int l = 0; l < tileentitychest.getSizeInventory(); l++)
        {
            ItemStack itemstack = tileentitychest.getStackInSlot(l);
            if(itemstack == null)
            {
                continue;
            }
            float f = random.nextFloat() * 0.8F + 0.1F;
            float f1 = random.nextFloat() * 0.8F + 0.1F;
            float f2 = random.nextFloat() * 0.8F + 0.1F;
            do
            {
                if(itemstack.stackSize <= 0)
                {
                    continue label0;
                }
                int i1 = random.nextInt(21) + 10;
                if(i1 > itemstack.stackSize)
                {
                    i1 = itemstack.stackSize;
                }
                itemstack.stackSize -= i1;
                EntityItem entityitem = new EntityItem(world, (float)i + f, (float)j + f1, (float)k + f2, new ItemStack(itemstack.itemID, i1, itemstack.itemDamage));
                float f3 = 0.05F;
                entityitem.motionX = (float)random.nextGaussian() * f3;
                entityitem.motionY = (float)random.nextGaussian() * f3 + 0.2F;
                entityitem.motionZ = (float)random.nextGaussian() * f3;
                world.entityJoinedWorld(entityitem);
            } while(true);
        }

        super.onBlockRemoval(world, i, j, k);
    }

    public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer) {
        Object obj = (TileEntityChest)world.getBlockTileEntity(i, j, k);
        if(world.isBlockOpaqueCube(i, j + 1, k))
        {
            return true;
        }
        if(world.getBlockId(i - 1, j, k) == blockID && world.isBlockOpaqueCube(i - 1, j + 1, k))
        {
            return true;
        }
        if(world.getBlockId(i + 1, j, k) == blockID && world.isBlockOpaqueCube(i + 1, j + 1, k))
        {
            return true;
        }
        if(world.getBlockId(i, j, k - 1) == blockID && world.isBlockOpaqueCube(i, j + 1, k - 1))
        {
            return true;
        }
        if(world.getBlockId(i, j, k + 1) == blockID && world.isBlockOpaqueCube(i, j + 1, k + 1))
        {
            return true;
        }
        if(world.getBlockId(i - 1, j, k) == blockID)
        {
            obj = new InventoryLargeChest("Large chest", (TileEntityChest)world.getBlockTileEntity(i - 1, j, k), ((IInventory) (obj)));
        }
        if(world.getBlockId(i + 1, j, k) == blockID)
        {
            obj = new InventoryLargeChest("Large chest", ((IInventory) (obj)), (TileEntityChest)world.getBlockTileEntity(i + 1, j, k));
        }
        if(world.getBlockId(i, j, k - 1) == blockID)
        {
            obj = new InventoryLargeChest("Large chest", (TileEntityChest)world.getBlockTileEntity(i, j, k - 1), ((IInventory) (obj)));
        }
        if(world.getBlockId(i, j, k + 1) == blockID)
        {
            obj = new InventoryLargeChest("Large chest", ((IInventory) (obj)), (TileEntityChest)world.getBlockTileEntity(i, j, k + 1));
        }
        if (!world.multiplayerWorld) {
            entityplayer.displayGUIChest(((IInventory) (obj)));
        }
        return true;
    }

    protected TileEntity SetBlockEntity()
    {
        return new TileEntityChest();
    }

    private Random random;
}
