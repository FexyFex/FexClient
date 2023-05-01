package net.minecraft.src.item;// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


import net.minecraft.src.block.World;
import net.minecraft.src.entity.EntityPlayer;

public class ItemSoup extends ItemFood
{

    public ItemSoup(int i, int j)
    {
        super(i, j);
    }

    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
        super.onItemRightClick(itemstack, world, entityplayer);
        return new ItemStack(bowlEmpty);
    }
}
