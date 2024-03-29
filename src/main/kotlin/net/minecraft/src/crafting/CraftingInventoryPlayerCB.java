package net.minecraft.src.crafting;

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


import net.minecraft.src.entity.EntityPlayer;
import net.minecraft.src.inventory.*;
import net.minecraft.src.item.ItemStack;

public class CraftingInventoryPlayerCB extends CraftingInventoryCB
{

    public CraftingInventoryPlayerCB(InventoryPlayer inventoryplayer)
    {
        this(inventoryplayer, true);
    }

    public CraftingInventoryPlayerCB(InventoryPlayer inventoryplayer, boolean flag)
    {
        craftMatrix = new InventoryCrafting(this, 2, 2);
        craftResult = new InventoryCraftResult();
        field_20124_c = false;
        field_20124_c = flag;
        func_20117_a(new SlotCrafting(craftMatrix, craftResult, 0, 144, 36));
        for(int i = 0; i < 2; i++)
        {
            for(int i1 = 0; i1 < 2; i1++)
            {
                func_20117_a(new Slot(craftMatrix, i1 + i * 2, 88 + i1 * 18, 26 + i * 18));
            }

        }

        for(int j = 0; j < 4; j++)
        {
            int j1 = j;
            func_20117_a(new SlotArmor(this, inventoryplayer, inventoryplayer.getSizeInventory() - 1 - j, 8, 8 + j * 18, j1));
        }

        for(int k = 0; k < 3; k++)
        {
            for(int k1 = 0; k1 < 9; k1++)
            {
                func_20117_a(new Slot(inventoryplayer, k1 + (k + 1) * 9, 8 + k1 * 18, 84 + k * 18));
            }

        }

        for(int l = 0; l < 9; l++)
        {
            func_20117_a(new Slot(inventoryplayer, l, 8 + l * 18, 142));
        }

        onCraftMatrixChanged(craftMatrix);
    }

    public void onCraftMatrixChanged(IInventory iinventory)
    {
        int ai[] = new int[9];
        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 3; j++)
            {
                int k = -1;
                if(i < 2 && j < 2)
                {
                    ItemStack itemstack = craftMatrix.getStackInSlot(i + j * 2);
                    if(itemstack != null)
                    {
                        k = itemstack.itemID;
                    }
                }
                ai[i + j * 3] = k;
            }

        }

        craftResult.setInventorySlotContents(0, CraftingManager.getInstance().craft(ai));
    }

    public void onCraftGuiClosed(EntityPlayer entityplayer)
    {
        super.onCraftGuiClosed(entityplayer);
        for(int i = 0; i < 4; i++)
        {
            ItemStack itemstack = craftMatrix.getStackInSlot(i);
            if(itemstack != null)
            {
                entityplayer.dropPlayerItem(itemstack);
                craftMatrix.setInventorySlotContents(i, null);
            }
        }

    }

    public boolean func_20120_b(EntityPlayer entityplayer)
    {
        return true;
    }

    public InventoryCrafting craftMatrix;
    public IInventory craftResult;
    public boolean field_20124_c;
}
