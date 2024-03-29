package net.minecraft.src.crafting;// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


import net.minecraft.src.entity.EntityPlayer;
import net.minecraft.src.inventory.IInventory;
import net.minecraft.src.item.ItemStack;

public class InventoryCraftResult implements IInventory {
    public InventoryCraftResult() {
        stackResult = new ItemStack[1];
    }

    public int getSizeInventory() {
        return 1;
    }

    public ItemStack getStackInSlot(int i) {
        return stackResult[i];
    }

    public String getInvName() {
        return "Result";
    }

    public ItemStack decrStackSize(int i, int j) {
        if (stackResult[i] != null) {
            ItemStack itemstack = stackResult[i];
            stackResult[i] = null;
            return itemstack;
        } else {
            return null;
        }
    }

    public void setInventorySlotContents(int i, ItemStack itemstack) {
        stackResult[i] = itemstack;
    }

    public int getInventoryStackLimit() {
        return 64;
    }

    public void onInventoryChanged() {
    }

    public boolean isInRangeOfPlayer(EntityPlayer entityplayer) {
        return true;
    }

    private ItemStack stackResult[];
}
