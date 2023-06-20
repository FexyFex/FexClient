package net.minecraft.src.player;// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.client.Minecraft;
import net.minecraft.src.Block;
import net.minecraft.src.block.World;
import net.minecraft.src.entity.Entity;
import net.minecraft.src.entity.EntityPlayer;
import net.minecraft.src.entity.EntityPlayerSP;
import net.minecraft.src.item.ItemStack;

public class PlayerController {

    public PlayerController(Minecraft minecraft) {
        field_1064_b = false;
        mc = minecraft;
    }

    public void func_717_a(World world) {
    }

    public void clickBlock(int i, int j, int k, int l) {
        sendBlockRemoved(i, j, k, l);
    }

    public boolean sendBlockRemoved(int x, int y, int z, int l) {
        mc.effectRenderer.func_1186_a(x, y, z);
        World world = mc.theWorld;
        Block block = Block.blocksList[world.getBlockId(x, y, z)];
        int i1 = world.getBlockMetadata(x, y, z);
        boolean flag = world.setBlockWithNotify(x, y, z, 0);
        if (block != null && flag) {
            mc.sndManager.playSound(block.stepSound.func_1146_a(), (float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F, (block.stepSound.func_1147_b() + 1.0F) / 2.0F, block.stepSound.func_1144_c() * 0.8F);
            block.onBlockDestroyedByPlayer(world, x, y, z, i1);
        }
        return flag;
    }

    public void sendBlockRemoving(int i, int j, int k, int l) {
    }

    public void resetMiningEfforts() {
    }

    public void setPartialTime(float f) {
    }

    public float getBlockReachDistance() {
        return 5F;
    }

    public boolean sendUseItem(EntityPlayer entityplayer, World world, ItemStack itemstack) {
        int i = itemstack.stackSize;
        ItemStack itemstack1 = itemstack.useItemRightClick(world, entityplayer);
        if (itemstack1 != itemstack || itemstack1 != null && itemstack1.stackSize != i) {
            entityplayer.inventory.mainInventory[entityplayer.inventory.currentItem] = itemstack1;
            if (itemstack1.stackSize == 0) {
                entityplayer.inventory.mainInventory[entityplayer.inventory.currentItem] = null;
            }
            return true;
        } else {
            return false;
        }
    }

    public void flipPlayer(EntityPlayer entityplayer) {
    }

    public void updateController() {
    }

    public boolean shouldDrawHUD() {
        return true;
    }

    public void func_6473_b(EntityPlayer entityplayer) {
    }

    public boolean sendPlaceBlock(EntityPlayer entityplayer, World world, ItemStack itemstack, int i, int j, int k, int l) {
        int i1 = world.getBlockId(i, j, k);
        if (i1 > 0 && Block.blocksList[i1].blockActivated(world, i, j, k, entityplayer)) {
            return true;
        }
        if (itemstack == null) {
            return false;
        } else {
            return itemstack.useItem(entityplayer, world, i, j, k, l);
        }
    }

    public EntityPlayer func_4087_b(World world) {
        return new EntityPlayerSP(mc, world, mc.session, world.worldProvider.worldType);
    }

    public void func_6475_a(EntityPlayer entityplayer, Entity entity) {
        entityplayer.useCurrentItemOnEntity(entity);
    }

    public void func_6472_b(EntityPlayer entityplayer, Entity entity) {
        entityplayer.attackTargetEntityWithCurrentItem(entity);
    }

    public ItemStack func_20085_a(int i, int j, int k, EntityPlayer entityplayer) {
        return entityplayer.field_20068_h.func_20116_a(j, k, entityplayer);
    }

    public void func_20086_a(int i, EntityPlayer entityplayer) {
        entityplayer.field_20068_h.onCraftGuiClosed(entityplayer);
        entityplayer.field_20068_h = entityplayer.field_20069_g;
    }

    protected final Minecraft mc;
    public boolean field_1064_b;
}
