package net.minecraft.src.entity;

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.Block;
import net.minecraft.src.model.SignModel;
import net.minecraft.src.rendering.FontRenderer;
import org.lwjgl.opengl.GL11;

public class TileEntitySignRenderer extends TileEntitySpecialRenderer {

    public TileEntitySignRenderer() {
        signModel = new SignModel();
    }

    public void renderTileEntitySignAt(TileEntitySign tileentitysign, double d, double d1, double d2,
                                       float f) {
        Block block = tileentitysign.getBlockType();
        GL11.glPushMatrix();
        float f1 = 0.6666667F;
        if (block == Block.signPost) {
            GL11.glTranslatef((float) d + 0.5F, (float) d1 + 0.75F * f1, (float) d2 + 0.5F);
            float f2 = (float) (tileentitysign.getBlockMetadata() * 360) / 16F;
            GL11.glRotatef(-f2, 0.0F, 1.0F, 0.0F);
            signModel.field_1345_b.field_1403_h = true;
        } else {
            int i = tileentitysign.getBlockMetadata();
            float f3 = 0.0F;
            if (i == 2) {
                f3 = 180F;
            }
            if (i == 4) {
                f3 = 90F;
            }
            if (i == 5) {
                f3 = -90F;
            }
            GL11.glTranslatef((float) d + 0.5F, (float) d1 + 0.75F * f1, (float) d2 + 0.5F);
            GL11.glRotatef(-f3, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(0.0F, -0.3125F, -0.4375F);
            signModel.field_1345_b.field_1403_h = false;
        }
        bindTextureByName("/item/sign.png");
        GL11.glPushMatrix();
        GL11.glScalef(f1, -f1, -f1);
        signModel.func_887_a();
        GL11.glPopMatrix();
        FontRenderer fontrenderer = getFontRenderer();
        float f4 = 0.01666667F * f1;
        GL11.glTranslatef(0.0F, 0.5F * f1, 0.07F * f1);
        GL11.glScalef(f4, -f4, f4);
        GL11.glNormal3f(0.0F, 0.0F, -1F * f4);
        GL11.glDepthMask(false);
        int j = 0;
        for (int k = 0; k < tileentitysign.signText.length; k++) {
            String s = tileentitysign.signText[k];
            if (k == tileentitysign.lineBeingEdited) {
                s = (new StringBuilder()).append("> ").append(s).append(" <").toString();
                fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, k * 10 - tileentitysign.signText.length * 5, j);
            } else {
                fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, k * 10 - tileentitysign.signText.length * 5, j);
            }
        }

        GL11.glDepthMask(true);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glPopMatrix();
    }

    public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2,
                                   float f) {
        renderTileEntitySignAt((TileEntitySign) tileentity, d, d1, d2, f);
    }

    private SignModel signModel;
}
