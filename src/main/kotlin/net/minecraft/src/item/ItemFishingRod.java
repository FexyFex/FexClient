package net.minecraft.src.item;// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.block.World;
import net.minecraft.src.entity.EntityFish;
import net.minecraft.src.entity.EntityPlayer;

public class ItemFishingRod extends Item
{

    public ItemFishingRod(int i)
    {
        super(i);
        maxDamage = 64;
    }

    public boolean isFull3D()
    {
        return true;
    }

    public boolean shouldRotateAroundWhenRendering()
    {
        return true;
    }

    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
        if(entityplayer.fishEntity != null)
        {
            int i = entityplayer.fishEntity.func_4043_i();
            itemstack.damageItem(i);
            entityplayer.swingItem();
        } else
        {
            world.playSoundAtEntity(entityplayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
            if(!world.multiplayerWorld)
            {
                world.entityJoinedWorld(new EntityFish(world, entityplayer));
            }
            entityplayer.swingItem();
        }
        return itemstack;
    }
}
