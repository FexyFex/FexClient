package net.minecraft.src.crafting;// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.entity.EntityPlayer;
import net.minecraft.src.inventory.IInventory;
import net.minecraft.src.inventory.InventoryPlayer;
import net.minecraft.src.inventory.Slot;
import net.minecraft.src.item.ItemStack;

import java.util.*;

public abstract class CraftingInventoryCB
{

    public CraftingInventoryCB()
    {
        field_20123_d = new ArrayList();
        inventorySlots = new ArrayList();
        unusedList = 0;
        craftMatrix = 0;
        field_20121_g = new ArrayList();
        craftResult = new HashSet();
    }

    protected void func_20117_a(Slot slot)
    {
        slot.field_20007_a = inventorySlots.size();
        inventorySlots.add(slot);
        field_20123_d.add(null);
    }

    public void func_20114_a()
    {
        for(int i = 0; i < inventorySlots.size(); i++)
        {
            ItemStack itemstack = ((Slot) inventorySlots.get(i)).getStack();
            ItemStack itemstack1 = (ItemStack)field_20123_d.get(i);
            if(ItemStack.func_20107_a(itemstack1, itemstack))
            {
                continue;
            }
            itemstack1 = itemstack != null ? itemstack.copy() : null;
            field_20123_d.set(i, itemstack1);
            for(int j = 0; j < field_20121_g.size(); j++)
            {
                ((ICrafting)field_20121_g.get(j)).func_20159_a(this, i, itemstack1);
            }

        }

    }

    public Slot func_20118_a(int i)
    {
        return (Slot) inventorySlots.get(i);
    }

    public ItemStack pickItemMaybe(int slotIndex, int mouseButton, EntityPlayer entityplayer) {
        ItemStack itemstack = null;
        if(mouseButton == 0 || mouseButton == 1) {
            InventoryPlayer inventoryplayer = entityplayer.inventory;
            if(slotIndex == -999) { // probably means that it's outside the inventory, so drop it
                if(inventoryplayer.getCurrentlySelectedItemStack() != null) {
                    if(mouseButton == 0) { // left click
                        entityplayer.dropPlayerItem(inventoryplayer.getCurrentlySelectedItemStack());
                        inventoryplayer.setCurrentlySelectedItemStack(null);
                    }
                    if(mouseButton == 1) { // right click
                        entityplayer.dropPlayerItem(inventoryplayer.getCurrentlySelectedItemStack().splitStack(1));
                        if(inventoryplayer.getCurrentlySelectedItemStack().stackSize == 0) {
                            inventoryplayer.setCurrentlySelectedItemStack(null);
                        }
                    }
                }
            } else { // we have clicked on a slot that is inside the inventory. WOW!
                Slot slot = (Slot) inventorySlots.get(slotIndex);
                if(slot != null) {
                    // if the player clicks on a non-empty slot then immediately publish onSlotChanged???? Why tf
                    // Nothing has changed thus far...
                    slot.onSlotChanged();
                    ItemStack selectedStack = slot.getStack();

                    if (selectedStack != null)
                        itemstack = selectedStack.copy();

                    if(selectedStack != null || inventoryplayer.getCurrentlySelectedItemStack() != null) {
                        if(selectedStack != null && inventoryplayer.getCurrentlySelectedItemStack() == null) {
                            // Pick stuff up from the hovering slot?
                            int stackSize = mouseButton != 0 ? (selectedStack.stackSize + 1) / 2 : selectedStack.stackSize;
                            inventoryplayer.setCurrentlySelectedItemStack(slot.clickOnSlot(stackSize));

                            if(selectedStack.stackSize == 0) // initialize to null if slot is empty.
                                slot.putStack(null); // A safety mechanism I guess?

                            slot.onPickupFromSlot();
                        } else
                        if (selectedStack == null && inventoryplayer.getCurrentlySelectedItemStack() != null && slot.isItemValid(inventoryplayer.getCurrentlySelectedItemStack())) {
                            int pickedStackSize = mouseButton != 0 ? 1 : inventoryplayer.getCurrentlySelectedItemStack().stackSize;
                            if(pickedStackSize > slot.getSlotStackLimit()) {
                                pickedStackSize = slot.getSlotStackLimit();
                            }
                            slot.putStack(inventoryplayer.getCurrentlySelectedItemStack().splitStack(pickedStackSize));
                            if(inventoryplayer.getCurrentlySelectedItemStack().stackSize == 0)
                            {
                                inventoryplayer.setCurrentlySelectedItemStack(null);
                            }
                        } else
                        if(selectedStack != null && inventoryplayer.getCurrentlySelectedItemStack() != null)
                        {
                            if(slot.isItemValid(inventoryplayer.getCurrentlySelectedItemStack()))
                            {
                                if(selectedStack.itemID != inventoryplayer.getCurrentlySelectedItemStack().itemID)
                                {
                                    if(inventoryplayer.getCurrentlySelectedItemStack().stackSize <= slot.getSlotStackLimit())
                                    {
                                        ItemStack itemstack2 = selectedStack;
                                        slot.putStack(inventoryplayer.getCurrentlySelectedItemStack());
                                        inventoryplayer.setCurrentlySelectedItemStack(itemstack2);
                                    }
                                } else
                                if(selectedStack.itemID == inventoryplayer.getCurrentlySelectedItemStack().itemID)
                                {
                                    if(mouseButton == 0)
                                    {
                                        int i1 = inventoryplayer.getCurrentlySelectedItemStack().stackSize;
                                        if(i1 > slot.getSlotStackLimit() - selectedStack.stackSize)
                                        {
                                            i1 = slot.getSlotStackLimit() - selectedStack.stackSize;
                                        }
                                        if(i1 > inventoryplayer.getCurrentlySelectedItemStack().getMaxStackSize() - selectedStack.stackSize)
                                        {
                                            i1 = inventoryplayer.getCurrentlySelectedItemStack().getMaxStackSize() - selectedStack.stackSize;
                                        }
                                        inventoryplayer.getCurrentlySelectedItemStack().splitStack(i1);
                                        if(inventoryplayer.getCurrentlySelectedItemStack().stackSize == 0)
                                        {
                                            inventoryplayer.setCurrentlySelectedItemStack(null);
                                        }
                                        selectedStack.stackSize += i1;
                                    } else
                                    if(mouseButton == 1) {
                                        int j1 = 1;
                                        if(j1 > slot.getSlotStackLimit() - selectedStack.stackSize)
                                        {
                                            j1 = slot.getSlotStackLimit() - selectedStack.stackSize;
                                        }
                                        if(j1 > inventoryplayer.getCurrentlySelectedItemStack().getMaxStackSize() - selectedStack.stackSize)
                                        {
                                            j1 = inventoryplayer.getCurrentlySelectedItemStack().getMaxStackSize() - selectedStack.stackSize;
                                        }
                                        inventoryplayer.getCurrentlySelectedItemStack().splitStack(j1);
                                        if(inventoryplayer.getCurrentlySelectedItemStack().stackSize == 0)
                                        {
                                            inventoryplayer.setCurrentlySelectedItemStack(null);
                                        }
                                        selectedStack.stackSize += j1;
                                    }
                                }
                            } else
                            if(selectedStack.itemID == inventoryplayer.getCurrentlySelectedItemStack().itemID && inventoryplayer.getCurrentlySelectedItemStack().getMaxStackSize() > 1)
                            {
                                int k1 = selectedStack.stackSize;
                                if(k1 > 0 && k1 + inventoryplayer.getCurrentlySelectedItemStack().stackSize <= inventoryplayer.getCurrentlySelectedItemStack().getMaxStackSize())
                                {
                                    inventoryplayer.getCurrentlySelectedItemStack().stackSize += k1;
                                    selectedStack.splitStack(k1);
                                    if(selectedStack.stackSize == 0)
                                    {
                                        slot.putStack(null);
                                    }
                                    slot.onPickupFromSlot();
                                }
                            }
                        }
                    }
                }
            }
        }
        return itemstack;
    }

    public void onCraftGuiClosed(EntityPlayer entityplayer)
    {
        InventoryPlayer inventoryplayer = entityplayer.inventory;
        if(inventoryplayer.getCurrentlySelectedItemStack() != null)
        {
            entityplayer.dropPlayerItem(inventoryplayer.getCurrentlySelectedItemStack());
            inventoryplayer.setCurrentlySelectedItemStack(null);
        }
    }

    public void onCraftMatrixChanged(IInventory iinventory)
    {
        func_20114_a();
    }

    public void func_20119_a(int i, ItemStack itemstack)
    {
        func_20118_a(i).putStack(itemstack);
    }

    public void func_20115_a(ItemStack aitemstack[])
    {
        for(int i = 0; i < aitemstack.length; i++)
        {
            func_20118_a(i).putStack(aitemstack[i]);
        }

    }

    public void func_20112_a(int i, int j)
    {
    }

    public short func_20111_a(InventoryPlayer inventoryplayer)
    {
        craftMatrix++;
        return craftMatrix;
    }

    public void func_20113_a(short word0)
    {
    }

    public void func_20110_b(short word0)
    {
    }

    public abstract boolean func_20120_b(EntityPlayer entityplayer);

    public List field_20123_d;
    public List inventorySlots;
    public int unusedList;
    private short craftMatrix;
    protected List field_20121_g;
    private Set craftResult;
}
