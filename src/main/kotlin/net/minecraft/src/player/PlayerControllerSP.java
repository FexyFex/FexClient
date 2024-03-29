package net.minecraft.src.player;// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.client.Minecraft;
import net.minecraft.src.Block;
import net.minecraft.src.block.World;
import net.minecraft.src.entity.EntityPlayer;
import net.minecraft.src.item.ItemStack;

public class PlayerControllerSP extends PlayerController
{

    public PlayerControllerSP(Minecraft minecraft)
    {
        super(minecraft);
        field_1074_c = -1;
        field_1073_d = -1;
        field_1072_e = -1;
        curBlockDamage = 0.0F;
        prevBlockDamage = 0.0F;
        field_1069_h = 0.0F;
        field_1068_i = 0;
    }

    public void flipPlayer(EntityPlayer entityplayer)
    {
        entityplayer.rotationYaw = -180F;
    }

    public boolean sendBlockRemoved(int x, int y, int z, int l)
    {
        int i1 = mc.theWorld.getBlockId(x, y, z);
        int j1 = mc.theWorld.getBlockMetadata(x, y, z);
        boolean flag = super.sendBlockRemoved(x, y, z, l);
        ItemStack itemstack = mc.thePlayer.getCurrentEquippedItem();
        boolean flag1 = mc.thePlayer.canHarvestBlock(Block.blocksList[i1]);
        if(itemstack != null)
        {
            itemstack.hitBlock(i1, x, y, z);
            if(itemstack.stackSize == 0)
            {
                itemstack.func_1097_a(mc.thePlayer);
                mc.thePlayer.destroyCurrentEquippedItem();
            }
        }
        if(flag && flag1)
        {
            Block.blocksList[i1].harvestBlock(mc.theWorld, x, y, z, j1);
        }
        return flag;
    }

    public void clickBlock(int i, int j, int k, int l)
    {
        int i1 = mc.theWorld.getBlockId(i, j, k);
        if(i1 > 0 && curBlockDamage == 0.0F)
        {
            Block.blocksList[i1].onBlockClicked(mc.theWorld, i, j, k, mc.thePlayer);
        }
        if(i1 > 0 && Block.blocksList[i1].blockStrength(mc.thePlayer) >= 1.0F)
        {
            sendBlockRemoved(i, j, k, l);
        }
    }

    public void resetMiningEfforts()
    {
        curBlockDamage = 0.0F;
        field_1068_i = 0;
    }

    public void sendBlockRemoving(int i, int j, int k, int l)
    {
        if(field_1068_i > 0)
        {
            field_1068_i--;
            return;
        }
        if(i == field_1074_c && j == field_1073_d && k == field_1072_e)
        {
            int i1 = mc.theWorld.getBlockId(i, j, k);
            if(i1 == 0)
            {
                return;
            }
            Block block = Block.blocksList[i1];
            curBlockDamage += block.blockStrength(mc.thePlayer);
            if(field_1069_h % 4F == 0.0F && block != null)
            {
                mc.sndManager.playSound(block.stepSound.func_1145_d(), (float)i + 0.5F, (float)j + 0.5F, (float)k + 0.5F, (block.stepSound.func_1147_b() + 1.0F) / 8F, block.stepSound.func_1144_c() * 0.5F);
            }
            field_1069_h++;
            if(curBlockDamage >= 1.0F)
            {
                sendBlockRemoved(i, j, k, l);
                curBlockDamage = 0.0F;
                prevBlockDamage = 0.0F;
                field_1069_h = 0.0F;
                field_1068_i = 5;
            }
        } else
        {
            curBlockDamage = 0.0F;
            prevBlockDamage = 0.0F;
            field_1069_h = 0.0F;
            field_1074_c = i;
            field_1073_d = j;
            field_1072_e = k;
        }
    }

    public void setPartialTime(float f)
    {
        if(curBlockDamage <= 0.0F)
        {
            mc.ingameGUI.field_6446_b = 0.0F;
            mc.renderGlobal.field_1450_i = 0.0F;
        } else
        {
            float f1 = prevBlockDamage + (curBlockDamage - prevBlockDamage) * f;
            mc.ingameGUI.field_6446_b = f1;
            mc.renderGlobal.field_1450_i = f1;
        }
    }

    public float getBlockReachDistance()
    {
        return 4F;
    }

    public void func_717_a(World world)
    {
        super.func_717_a(world);
    }

    public void updateController()
    {
        prevBlockDamage = curBlockDamage;
        mc.sndManager.func_4033_c();
    }

    private int field_1074_c;
    private int field_1073_d;
    private int field_1072_e;
    private float curBlockDamage;
    private float prevBlockDamage;
    private float field_1069_h;
    private int field_1068_i;
}
