package net.minecraft.src.rendering;// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.IntBuffer;
import javax.imageio.ImageIO;

import net.minecraft.src.GameSettings;
import net.minecraft.src.Tessellator;
import net.minecraft.src.gui.font.FontAllowedCharacters;
import net.minecraft.src.opengl.GLAllocation;
import net.minecraft.src.rendering.RenderEngine;
import org.lwjgl.opengl.GL11;

public class FontRenderer
{

    public FontRenderer(GameSettings gamesettings, String s, RenderEngine renderengine)
    {
        charWidth = new int[256];
        fontTextureName = 0;
        buffer = GLAllocation.createDirectIntBuffer(1024 /*GL_FRONT_LEFT*/);
        BufferedImage bufferedimage;
        try
        {
            bufferedimage = ImageIO.read((RenderEngine.class).getResourceAsStream(s));
        }
        catch(IOException ioexception)
        {
            throw new RuntimeException(ioexception);
        }
        int i = bufferedimage.getWidth();
        int j = bufferedimage.getHeight();
        int ai[] = new int[i * j];
        bufferedimage.getRGB(0, 0, i, j, ai, 0, i);
        for(int k = 0; k < 256; k++)
        {
            int l = k % 16;
            int k1 = k / 16;
            int j2 = 7;
            do
            {
                if(j2 < 0)
                {
                    break;
                }
                int i3 = l * 8 + j2;
                boolean flag = true;
                for(int l3 = 0; l3 < 8 && flag; l3++)
                {
                    int i4 = (k1 * 8 + l3) * i;
                    int k4 = ai[i3 + i4] & 0xff;
                    if(k4 > 0)
                    {
                        flag = false;
                    }
                }

                if(!flag)
                {
                    break;
                }
                j2--;
            } while(true);
            if(k == 32)
            {
                j2 = 2;
            }
            charWidth[k] = j2 + 2;
        }

        fontTextureName = renderengine.allocateAndSetupTexture(bufferedimage);
        fontDisplayLists = GLAllocation.generateDisplayLists(288);
        Tessellator tessellator = Tessellator.instance;
        for(int i1 = 0; i1 < 256; i1++)
        {
            GL11.glNewList(fontDisplayLists + i1, 4864 /*GL_COMPILE*/);
            tessellator.startDrawingQuads();
            int l1 = (i1 % 16) * 8;
            int k2 = (i1 / 16) * 8;
            float f = 7.99F;
            float f1 = 0.0F;
            float f2 = 0.0F;
            tessellator.addVertexWithUV(0.0D, 0.0F + f, 0.0D, (float)l1 / 128F + f1, ((float)k2 + f) / 128F + f2);
            tessellator.addVertexWithUV(0.0F + f, 0.0F + f, 0.0D, ((float)l1 + f) / 128F + f1, ((float)k2 + f) / 128F + f2);
            tessellator.addVertexWithUV(0.0F + f, 0.0D, 0.0D, ((float)l1 + f) / 128F + f1, (float)k2 / 128F + f2);
            tessellator.addVertexWithUV(0.0D, 0.0D, 0.0D, (float)l1 / 128F + f1, (float)k2 / 128F + f2);
            tessellator.draw();
            GL11.glTranslatef(charWidth[i1], 0.0F, 0.0F);
            GL11.glEndList();
        }

        for(int j1 = 0; j1 < 32; j1++)
        {
            int i2 = (j1 >> 3 & 1) * 85;
            int l2 = (j1 >> 2 & 1) * 170 + i2;
            int j3 = (j1 >> 1 & 1) * 170 + i2;
            int k3 = (j1 >> 0 & 1) * 170 + i2;
            if(j1 == 6)
            {
                l2 += 85;
            }
            boolean flag1 = j1 >= 16;
            if(gamesettings.anaglyph)
            {
                int j4 = (l2 * 30 + j3 * 59 + k3 * 11) / 100;
                int l4 = (l2 * 30 + j3 * 70) / 100;
                int i5 = (l2 * 30 + k3 * 70) / 100;
                l2 = j4;
                j3 = l4;
                k3 = i5;
            }
            if(flag1)
            {
                l2 /= 4;
                j3 /= 4;
                k3 /= 4;
            }
            GL11.glNewList(fontDisplayLists + 256 + j1, 4864 /*GL_COMPILE*/);
            GL11.glColor3f((float)l2 / 255F, (float)j3 / 255F, (float)k3 / 255F);
            GL11.glEndList();
        }

    }

    public void drawStringWithShadow(String s, int i, int j, int k)
    {
        renderString(s, i + 1, j + 1, k, true);
        drawString(s, i, j, k);
    }

    public void drawString(String s, int x, int y, int color) {
        renderString(s, x, y, color, false);
    }

    public void renderString(String s, int x, int y, int color, boolean flag) {
        if(s == null)
        {
            return;
        }
        if(flag)
        {
            int l = color & 0xff000000;
            color = (color & 0xfcfcfc) >> 2;
            color += l;
        }
        GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, fontTextureName);
        float f = (float)(color >> 16 & 0xff) / 255F;
        float f1 = (float)(color >> 8 & 0xff) / 255F;
        float f2 = (float)(color & 0xff) / 255F;
        float f3 = (float)(color >> 24 & 0xff) / 255F;
        if(f3 == 0.0F)
        {
            f3 = 1.0F;
        }
        GL11.glColor4f(f, f1, f2, f3);
        buffer.clear();
        GL11.glPushMatrix();
        GL11.glTranslatef(x, y, 0.0F);
        for(int i1 = 0; i1 < s.length(); i1++)
        {
            for(; s.charAt(i1) == '\247' && s.length() > i1 + 1; i1 += 2)
            {
                int j1 = "0123456789abcdef".indexOf(s.toLowerCase().charAt(i1 + 1));
                if(j1 < 0 || j1 > 15)
                {
                    j1 = 15;
                }
                buffer.put(fontDisplayLists + 256 + j1 + (flag ? 16 : 0));
                if(buffer.remaining() == 0)
                {
                    buffer.flip();
                    GL11.glCallLists(buffer);
                    buffer.clear();
                }
            }

            int k1 = FontAllowedCharacters.field_20157_a.indexOf(s.charAt(i1));
            if(k1 >= 0) {
                buffer.put(fontDisplayLists + k1 + 32);
            }
            if(buffer.remaining() == 0) {
                buffer.flip();
                GL11.glCallLists(buffer);
                buffer.clear();
            }
        }

        buffer.flip();
        GL11.glCallLists(buffer);
        GL11.glPopMatrix();
    }

    public int getStringWidth(String s)
    {
        if(s == null)
        {
            return 0;
        }
        int i = 0;
        for(int j = 0; j < s.length(); j++)
        {
            if(s.charAt(j) == '\247')
            {
                j++;
                continue;
            }
            int k = FontAllowedCharacters.field_20157_a.indexOf(s.charAt(j));
            if(k >= 0)
            {
                i += charWidth[k + 32];
            }
        }

        return i;
    }

    private int charWidth[];
    public int fontTextureName;
    private int fontDisplayLists;
    private IntBuffer buffer;
}
