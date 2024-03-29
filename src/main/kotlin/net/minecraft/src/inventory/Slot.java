package net.minecraft.src.inventory;// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


import net.minecraft.src.item.ItemStack;

public class Slot
{

    public Slot(IInventory iinventory, int i, int j, int k)
    {
        inventory = iinventory;
        slotIndex = i;
        field_20006_b = j;
        field_20008_c = k;
    }

    public void onPickupFromSlot()
    {
        onSlotChanged();
    }

    public boolean isItemValid(ItemStack itemstack)
    {
        return true;
    }

    public ItemStack getStack()
    {
        return inventory.getStackInSlot(slotIndex);
    }

    public boolean func_20005_c()
    {
        return getStack() != null;
    }

    public void putStack(ItemStack itemstack)
    {
        inventory.setInventorySlotContents(slotIndex, itemstack);
        onSlotChanged();
    }

    public void onSlotChanged()
    {
        inventory.onInventoryChanged();
    }

    public int getSlotStackLimit()
    {
        return inventory.getInventoryStackLimit();
    }

    public int func_775_c()
    {
        return -1;
    }

    public ItemStack clickOnSlot(int stackSize) {
        return inventory.decrStackSize(slotIndex, stackSize);
    }

    private final int slotIndex;
    private final IInventory inventory;
    public int field_20007_a;
    public int field_20006_b;
    public int field_20008_c;
}
