package net.minecraft.src.gui;

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.entity.EntityPlayer;
import net.minecraft.src.rendering.RenderHelper;
import net.minecraft.src.rendering.RenderManager;
import org.lwjgl.opengl.GL11;

public class GuiInventory extends GuiContainer {
    public GuiInventory(EntityPlayer entityplayer) {
        super(entityplayer.craftingInventoryPlayer);
        field_948_f = true;
    }

    protected void drawGuiContainerForegroundLayer() {
        fontRenderer.drawString("Crafting", 86, 16, 0x404040);
    }

    public void drawScreen(int i, int j, float f) {
        super.drawScreen(i, j, f);
        xSize_lo = i;
        ySize_lo = j;
    }

    protected void drawGuiContainerBackgroundLayer(float f) {
        int i = mc.renderEngine.getTexture("/gui/inventory.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(i);
        int j = (width - xSize) / 2;
        int k = (height - ySize) / 2;
        drawTexturedModalRect(j, k, 0, 0, xSize, ySize);
        GL11.glEnable(32826 /*GL_RESCALE_NORMAL_EXT*/);
        GL11.glEnable(2903 /*GL_COLOR_MATERIAL*/);
        GL11.glPushMatrix();
        GL11.glTranslatef(j + 51, k + 75, 50F);
        float f1 = 30F;
        GL11.glScalef(-f1, f1, f1);
        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
        float f2 = mc.thePlayer.renderYawOffset;
        float f3 = mc.thePlayer.rotationYaw;
        float f4 = mc.thePlayer.rotationPitch;
        float f5 = (float) (j + 51) - xSize_lo;
        float f6 = (float) ((k + 75) - 50) - ySize_lo;
        GL11.glRotatef(135F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GL11.glRotatef(-135F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-(float) Math.atan(f6 / 40F) * 20F, 1.0F, 0.0F, 0.0F);
        mc.thePlayer.renderYawOffset = (float) Math.atan(f5 / 40F) * 20F;
        mc.thePlayer.rotationYaw = (float) Math.atan(f5 / 40F) * 40F;
        mc.thePlayer.rotationPitch = -(float) Math.atan(f6 / 40F) * 20F;
        GL11.glTranslatef(0.0F, mc.thePlayer.yOffset, 0.0F);
        RenderManager.instance.renderEntityWithPosYaw(mc.thePlayer, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
        mc.thePlayer.renderYawOffset = f2;
        mc.thePlayer.rotationYaw = f3;
        mc.thePlayer.rotationPitch = f4;
        GL11.glPopMatrix();
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(32826 /*GL_RESCALE_NORMAL_EXT*/);
    }

    public float xSize_lo;
    public float ySize_lo;
}
