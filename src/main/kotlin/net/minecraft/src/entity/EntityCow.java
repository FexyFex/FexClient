package net.minecraft.src.entity;// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


import net.minecraft.src.block.World;
import net.minecraft.src.item.Item;
import net.minecraft.src.item.ItemStack;
import net.minecraft.src.nbt.NBTTagCompound;

public class EntityCow extends EntityAnimals
{

    public EntityCow(World world)
    {
        super(world);
        unusedBoolean = false;
        texture = "/mob/cow.png";
        setSize(0.9F, 1.3F);
    }

    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
    }

    protected String getLivingSound()
    {
        return "mob.cow";
    }

    protected String getHurtSound()
    {
        return "mob.cowhurt";
    }

    protected String getDeathSound()
    {
        return "mob.cowhurt";
    }

    protected float getSoundVolume()
    {
        return 0.4F;
    }

    protected int getDropItemId()
    {
        return Item.leather.shiftedIndex;
    }

    public boolean interact(EntityPlayer entityplayer)
    {
        ItemStack itemstack = entityplayer.inventory.getCurrentHotBarSlot();
        if(itemstack != null && itemstack.itemID == Item.bucketEmpty.shiftedIndex)
        {
            entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentHotBarSlot, new ItemStack(Item.bucketMilk));
            return true;
        } else
        {
            return false;
        }
    }

    public boolean unusedBoolean;
}
