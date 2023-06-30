package net.minecraft.src.player;

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.client.Minecraft;
import net.minecraft.src.Block;
import net.minecraft.src.block.World;
import net.minecraft.src.entity.Entity;
import net.minecraft.src.entity.EntityClientPlayerMP;
import net.minecraft.src.entity.EntityPlayer;
import net.minecraft.src.item.ItemStack;
import net.minecraft.src.networking.NetClientHandler;
import net.minecraft.src.packet.*;

public class PlayerControllerMP extends PlayerController {

    public PlayerControllerMP(Minecraft minecraft, NetClientHandler netclienthandler) {
        super(minecraft);
        previouslyMinedBlockX = -1;
        previouslyMinedBlockY = -1;
        previouslyMinedBlockZ = -1;
        miningProgress = 0.0F;
        field_1080_g = 0.0F;
        playSoundCounter = 0.0F;
        miningCooldown = 0;
        isCurrentlyMining = false;
        field_1075_l = 0;
        netClientHandler = netclienthandler;
    }

    public void flipPlayer(EntityPlayer entityplayer) {
        entityplayer.rotationYaw = -180F;
    }

    public boolean sendBlockRemoved(int x, int y, int z, int side) {
        netClientHandler.addToSendQueue(new Packet14BlockDig(3, x, y, z, side));
        int blockId = mc.theWorld.getBlockId(x, y, z);
        boolean flag = super.sendBlockRemoved(x, y, z, side);
        ItemStack itemstack = mc.thePlayer.getCurrentEquippedItem();
        if (itemstack != null) {
            itemstack.hitBlock(blockId, x, y, z);
            if (itemstack.stackSize == 0) {
                itemstack.func_1097_a(mc.thePlayer);
                mc.thePlayer.destroyCurrentEquippedItem();
            }
        }
        return flag;
    }

    public void clickBlock(int x, int y, int z, int side) {
        isCurrentlyMining = true;
        netClientHandler.addToSendQueue(new Packet14BlockDig(0, x, y, z, side));
        int blockId = mc.theWorld.getBlockId(x, y, z);
        if (blockId > 0 && miningProgress == 0.0F) {
            Block.blocksList[blockId].onBlockClicked(mc.theWorld, x, y, z, mc.thePlayer);
        }
        if (blockId > 0 && Block.blocksList[blockId].blockStrength(mc.thePlayer) >= 1.0F) {
            sendBlockRemoved(x, y, z, side);
        }
    }

    public void resetMiningEfforts() {
        if (!isCurrentlyMining) {
            return;
        } else {
            isCurrentlyMining = false;
            netClientHandler.addToSendQueue(new Packet14BlockDig(2, 0, 0, 0, 0));
            miningProgress = 0.0F;
            miningCooldown = 0;
            return;
        }
    }

    public void sendBlockRemoving(int x, int y, int z, int side) {
        isCurrentlyMining = true;
        func_730_e();
        netClientHandler.addToSendQueue(new Packet14BlockDig(1, x, y, z, side));
        if (miningCooldown > 0) {
            miningCooldown--;
            return;
        }
        if (x == previouslyMinedBlockX && y == previouslyMinedBlockY && z == previouslyMinedBlockZ) {
            int blockId = mc.theWorld.getBlockId(x, y, z);
            if (blockId == 0) {
                return;
            }
            Block block = Block.blocksList[blockId];
            miningProgress += block.blockStrength(mc.thePlayer);
            if (playSoundCounter % 4F == 0.0F && block != null) {
                mc.sndManager.playSound(block.stepSound.func_1145_d(), (float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F, (block.stepSound.func_1147_b() + 1.0F) / 8F, block.stepSound.func_1144_c() * 0.5F);
            }
            playSoundCounter++;
            if (miningProgress >= 1.0F) {
                sendBlockRemoved(x, y, z, side);
                miningProgress = 0.0F;
                field_1080_g = 0.0F;
                playSoundCounter = 0.0F;
                miningCooldown = 5;
            }
        } else {
            miningProgress = 0.0F;
            field_1080_g = 0.0F;
            playSoundCounter = 0.0F;
            previouslyMinedBlockX = x;
            previouslyMinedBlockY = y;
            previouslyMinedBlockZ = z;
        }
    }

    public void setPartialTime(float f) {
        if (miningProgress <= 0.0F) {
            mc.ingameGUI.field_6446_b = 0.0F;
            mc.renderGlobal.field_1450_i = 0.0F;
        } else {
            float f1 = field_1080_g + (miningProgress - field_1080_g) * f;
            mc.ingameGUI.field_6446_b = f1;
            mc.renderGlobal.field_1450_i = f1;
        }
    }

    public float getBlockReachDistance() {
        return 4F;
    }

    public void func_717_a(World world) {
        super.func_717_a(world);
    }

    public void updateController() {
        func_730_e();
        field_1080_g = miningProgress;
        mc.sndManager.func_4033_c();
    }

    private void func_730_e() {
        int i = mc.thePlayer.inventory.currentHotBarSlot;
        if (i != field_1075_l) {
            field_1075_l = i;
            netClientHandler.addToSendQueue(new Packet16BlockItemSwitch(field_1075_l));
        }
    }

    public boolean sendPlaceBlock(EntityPlayer entityplayer, World world, ItemStack itemstack, int x, int y, int z, int side) {
        func_730_e();
        boolean flag = super.sendPlaceBlock(entityplayer, world, itemstack, x, y, z, side);
        netClientHandler.addToSendQueue(new Packet15Place(x, y, z, side, entityplayer.inventory.getCurrentHotBarSlot()));
        return flag;
    }

    public boolean sendUseItem(EntityPlayer entityplayer, World world, ItemStack itemstack) {
        func_730_e();
        boolean flag = super.sendUseItem(entityplayer, world, itemstack);
        netClientHandler.addToSendQueue(new Packet15Place(-1, -1, -1, 255, entityplayer.inventory.getCurrentHotBarSlot()));
        return flag;
    }

    public EntityPlayer func_4087_b(World world) {
        return new EntityClientPlayerMP(mc, world, mc.session, netClientHandler);
    }

    public void func_6472_b(EntityPlayer entityplayer, Entity entity) {
        func_730_e();
        netClientHandler.addToSendQueue(new Packet7(entityplayer.field_620_ab, entity.field_620_ab, 1));
        entityplayer.attackTargetEntityWithCurrentItem(entity);
    }

    public void func_6475_a(EntityPlayer entityplayer, Entity entity) {
        func_730_e();
        netClientHandler.addToSendQueue(new Packet7(entityplayer.field_620_ab, entity.field_620_ab, 0));
        entityplayer.useCurrentItemOnEntity(entity);
    }

    public ItemStack pickItemMaybe(int i, int j, int k, EntityPlayer entityplayer) {
        short word0 = entityplayer.field_20068_h.func_20111_a(entityplayer.inventory);
        ItemStack itemstack = super.pickItemMaybe(i, j, k, entityplayer);
        netClientHandler.addToSendQueue(new Packet102(i, j, k, itemstack, word0));
        return itemstack;
    }

    public void func_20086_a(int i, EntityPlayer entityplayer) {
        if (i == -9999) {
            return;
        } else {
            return;
        }
    }

    private int previouslyMinedBlockX;
    private int previouslyMinedBlockY;
    private int previouslyMinedBlockZ;
    public float miningProgress;
    private float field_1080_g;
    private float playSoundCounter;
    private int miningCooldown;
    public boolean isCurrentlyMining;
    private NetClientHandler netClientHandler;
    private int field_1075_l;
}
