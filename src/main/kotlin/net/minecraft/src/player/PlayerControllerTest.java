package net.minecraft.src.player;

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.client.Minecraft;
import net.minecraft.src.Block;
import net.minecraft.src.Session;
import net.minecraft.src.block.World;
import net.minecraft.src.entity.EntityPlayer;
import net.minecraft.src.item.ItemStack;

public class PlayerControllerTest extends PlayerController {
    public PlayerControllerTest(Minecraft minecraft) {
        super(minecraft);
        field_1064_b = true;
    }

    public void func_6473_b(EntityPlayer entityplayer) {
        for (int i = 0; i < 9; i++) {
            if (entityplayer.inventory.mainInventory[i] == null) {
                mc.thePlayer.inventory.mainInventory[i] = new ItemStack(((Block) Session.registeredBlocksList.get(i)).blockID);
            } else {
                mc.thePlayer.inventory.mainInventory[i].stackSize = 1;
            }
        }

    }

    public boolean shouldDrawHUD() {
        return false;
    }

    public void func_717_a(World world) {
        super.func_717_a(world);
    }

    public void updateController() {
    }
}
