package net.minecraft.src.gui;// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.StringTranslate;
import net.minecraft.src.crafting.CraftingInventoryCB;
import net.minecraft.src.inventory.InventoryPlayer;
import net.minecraft.src.inventory.Slot;
import net.minecraft.src.item.ItemStack;
import net.minecraft.src.rendering.RenderHelper;
import net.minecraft.src.rendering.RenderItem;
import org.lwjgl.opengl.GL11;

public abstract class GuiContainer extends GuiScreen
{

    public GuiContainer(CraftingInventoryCB craftinginventorycb)
    {
        xSize = 176;
        ySize = 166;
        inventorySlots = craftinginventorycb;
    }

    public void initGui()
    {
        super.initGui();
        mc.thePlayer.field_20068_h = inventorySlots;
    }

    public void drawScreen(int i, int j, float f)
    {
        drawDefaultBackground();
        int k = (width - xSize) / 2;
        int l = (height - ySize) / 2;
        drawGuiContainerBackgroundLayer(f);
        GL11.glPushMatrix();
        GL11.glRotatef(180F, 1.0F, 0.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef(k, l, 0.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(32826 /*GL_RESCALE_NORMAL_EXT*/);
        Slot slot = null;
        for(int i1 = 0; i1 < inventorySlots.inventorySlots.size(); i1++)
        {
            Slot slot1 = (Slot)inventorySlots.inventorySlots.get(i1);
            drawSlotInventory(slot1);
            if(func_20081_a(slot1, i, j))
            {
                slot = slot1;
                GL11.glDisable(2896 /*GL_LIGHTING*/);
                GL11.glDisable(2929 /*GL_DEPTH_TEST*/);
                int j1 = slot1.field_20006_b;
                int l1 = slot1.field_20008_c;
                drawGradientRect(j1, l1, j1 + 16, l1 + 16, 0x80ffffff, 0x80ffffff);
                GL11.glEnable(2896 /*GL_LIGHTING*/);
                GL11.glEnable(2929 /*GL_DEPTH_TEST*/);
            }
        }

        InventoryPlayer inventoryplayer = mc.thePlayer.inventory;
        if(inventoryplayer.getCurrentlySelectedItemStack() != null)
        {
            GL11.glTranslatef(0.0F, 0.0F, 32F);
            itemRenderer.renderItemIntoGUI(fontRenderer, mc.renderEngine, inventoryplayer.getCurrentlySelectedItemStack(), i - k - 8, j - l - 8);
            itemRenderer.renderItemOverlayIntoGUI(fontRenderer, mc.renderEngine, inventoryplayer.getCurrentlySelectedItemStack(), i - k - 8, j - l - 8);
        }
        GL11.glDisable(32826 /*GL_RESCALE_NORMAL_EXT*/);
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(2896 /*GL_LIGHTING*/);
        GL11.glDisable(2929 /*GL_DEPTH_TEST*/);
        drawGuiContainerForegroundLayer();
        if(inventoryplayer.getCurrentlySelectedItemStack() == null && slot != null && slot.func_20005_c())
        {
            String s = (new StringBuilder()).append("").append(StringTranslate.func_20162_a().func_20161_b(slot.getStack().func_20109_f())).toString().trim();
            if(s.length() > 0)
            {
                int k1 = (i - k) + 12;
                int i2 = j - l - 12;
                int j2 = fontRenderer.getStringWidth(s);
                drawGradientRect(k1 - 3, i2 - 3, k1 + j2 + 3, i2 + 8 + 3, 0xc0000000, 0xc0000000);
                fontRenderer.drawStringWithShadow(s, k1, i2, -1);
            }
        }
        GL11.glEnable(2896 /*GL_LIGHTING*/);
        GL11.glEnable(2929 /*GL_DEPTH_TEST*/);
        GL11.glPopMatrix();
    }

    protected void drawGuiContainerForegroundLayer()
    {
    }

    protected abstract void drawGuiContainerBackgroundLayer(float f);

    private void drawSlotInventory(Slot slot)
    {
        int i = slot.field_20006_b;
        int j = slot.field_20008_c;
        ItemStack itemstack = slot.getStack();
        if(itemstack == null)
        {
            int k = slot.func_775_c();
            if(k >= 0)
            {
                GL11.glDisable(2896 /*GL_LIGHTING*/);
                mc.renderEngine.bindTexture(mc.renderEngine.getTexture("/gui/items.png"));
                drawTexturedModalRect(i, j, (k % 16) * 16, (k / 16) * 16, 16, 16);
                GL11.glEnable(2896 /*GL_LIGHTING*/);
                return;
            }
        }
        itemRenderer.renderItemIntoGUI(fontRenderer, mc.renderEngine, itemstack, i, j);
        itemRenderer.renderItemOverlayIntoGUI(fontRenderer, mc.renderEngine, itemstack, i, j);
    }

    private Slot getSlotAtPosition(int i, int j)
    {
        for(int k = 0; k < inventorySlots.inventorySlots.size(); k++)
        {
            Slot slot = (Slot)inventorySlots.inventorySlots.get(k);
            if(func_20081_a(slot, i, j))
            {
                return slot;
            }
        }

        return null;
    }

    private boolean func_20081_a(Slot slot, int i, int j)
    {
        int k = (width - xSize) / 2;
        int l = (height - ySize) / 2;
        i -= k;
        j -= l;
        return i >= slot.field_20006_b - 1 && i < slot.field_20006_b + 16 + 1 && j >= slot.field_20008_c - 1 && j < slot.field_20008_c + 16 + 1;
    }

    protected void mouseClicked(int i, int j, int k)
    {
        if(k == 0 || k == 1)
        {
            Slot slot = getSlotAtPosition(i, j);
            int l = (width - xSize) / 2;
            int i1 = (height - ySize) / 2;
            boolean flag = i < l || j < i1 || i >= l + xSize || j >= i1 + ySize;
            int j1 = -1;
            if(slot != null)
            {
                j1 = slot.field_20007_a;
            }
            if(flag)
            {
                j1 = -999;
            }
            if(j1 != -1)
            {
                mc.playerController.pickItemMaybe(inventorySlots.unusedList, j1, k, mc.thePlayer);
            }
        }
    }

    protected void mouseMovedOrUp(int i, int j, int k)
    {
        if(k != 0);
    }

    protected void keyTyped(char c, int i)
    {
        if(i == 1 || i == mc.gameSettings.keyBindInventory.keyCode)
        {
            mc.thePlayer.func_20059_m();
        }
    }

    public void onGuiClosed()
    {
        if(mc.thePlayer == null)
        {
            return;
        } else
        {
            mc.playerController.func_20086_a(inventorySlots.unusedList, mc.thePlayer);
            return;
        }
    }

    public boolean doesGuiPauseGame()
    {
        return false;
    }

    private static RenderItem itemRenderer = new RenderItem();
    protected int xSize;
    protected int ySize;
    public CraftingInventoryCB inventorySlots;

}
