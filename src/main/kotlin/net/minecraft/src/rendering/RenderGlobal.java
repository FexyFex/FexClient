package net.minecraft.src.rendering;// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.nio.IntBuffer;
import java.util.*;

import me.fexclient.MinecraftFexClientConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.src.*;
import net.minecraft.src.block.World;
import net.minecraft.src.camera.ICamera;
import net.minecraft.src.datatype.MovingObjectPosition;
import net.minecraft.src.datatype.Vec3D;
import net.minecraft.src.entity.*;
import net.minecraft.src.helpers.MathHelper;
import net.minecraft.src.item.Item;
import net.minecraft.src.item.ItemStack;
import net.minecraft.src.opengl.GLAllocation;
import net.minecraft.src.opengl.buffer.ImageBufferDownload;
import net.minecraft.src.world.WorldRenderer;
import org.lwjgl.opengl.ARBOcclusionQuery;
import org.lwjgl.opengl.GL11;

public class RenderGlobal implements IWorldAccess {

    public RenderGlobal(Minecraft minecraft, RenderEngine renderengine) {
        field_1458_a = new ArrayList();
        worldRenderersToUpdate = new ArrayList();
        field_1436_w = false;
        field_1435_x = 0;
        renderDistance = -1;
        field_1424_I = 2;
        field_1457_b = new int[50000];
        field_1456_c = GLAllocation.createDirectIntBuffer(64);
        field_1415_R = new ArrayList();
        field_1455_d = 0;
        field_1454_e = GLAllocation.generateDisplayLists(1);
        field_1453_f = -9999D;
        field_1452_g = -9999D;
        field_1451_h = -9999D;
        field_1449_j = 0;
        mc = minecraft;
        renderEngine = renderengine;
        byte byte0 = 64;
        field_1440_s = GLAllocation.generateDisplayLists(byte0 * byte0 * byte0 * 3);
        field_1436_w = minecraft.func_6251_l().checkARBOcclusion();
        if (field_1436_w) {
            field_1456_c.clear();
            field_1437_v = GLAllocation.createDirectIntBuffer(byte0 * byte0 * byte0);
            field_1437_v.clear();
            field_1437_v.position(0);
            field_1437_v.limit(byte0 * byte0 * byte0);
            ARBOcclusionQuery.glGenQueriesARB(field_1437_v);
        }
        field_1434_y = GLAllocation.generateDisplayLists(3);
        GL11.glPushMatrix();
        GL11.glNewList(field_1434_y, 4864 /*GL_COMPILE*/);
        func_950_f();
        GL11.glEndList();
        GL11.glPopMatrix();
        Tessellator tessellator = Tessellator.instance;
        field_1433_z = field_1434_y + 1;
        GL11.glNewList(field_1433_z, 4864 /*GL_COMPILE*/);
        byte byte1 = 64;
        int i = 256 / byte1 + 2;
        float f = 16F;
        for (int j = -byte1 * i; j <= byte1 * i; j += byte1) {
            for (int l = -byte1 * i; l <= byte1 * i; l += byte1) {
                tessellator.startDrawingQuads();
                tessellator.addVertex(j + 0, f, l + 0);
                tessellator.addVertex(j + byte1, f, l + 0);
                tessellator.addVertex(j + byte1, f, l + byte1);
                tessellator.addVertex(j + 0, f, l + byte1);
                tessellator.draw();
            }

        }

        GL11.glEndList();
        field_1432_A = field_1434_y + 2;
        GL11.glNewList(field_1432_A, 4864 /*GL_COMPILE*/);
        f = -16F;
        tessellator.startDrawingQuads();
        for (int k = -byte1 * i; k <= byte1 * i; k += byte1) {
            for (int i1 = -byte1 * i; i1 <= byte1 * i; i1 += byte1) {
                tessellator.addVertex(k + byte1, f, i1 + 0);
                tessellator.addVertex(k + 0, f, i1 + 0);
                tessellator.addVertex(k + 0, f, i1 + byte1);
                tessellator.addVertex(k + byte1, f, i1 + byte1);
            }

        }

        tessellator.draw();
        GL11.glEndList();
    }

    private void func_950_f() {
        Random random = new Random(10842L);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        for (int i = 0; i < 1500; i++) {
            double d = random.nextFloat() * 2.0F - 1.0F;
            double d1 = random.nextFloat() * 2.0F - 1.0F;
            double d2 = random.nextFloat() * 2.0F - 1.0F;
            double d3 = 0.25F + random.nextFloat() * 0.25F;
            double d4 = d * d + d1 * d1 + d2 * d2;
            if (d4 >= 1.0D || d4 <= 0.01D) {
                continue;
            }
            d4 = 1.0D / Math.sqrt(d4);
            d *= d4;
            d1 *= d4;
            d2 *= d4;
            double d5 = d * 100D;
            double d6 = d1 * 100D;
            double d7 = d2 * 100D;
            double d8 = Math.atan2(d, d2);
            double d9 = Math.sin(d8);
            double d10 = Math.cos(d8);
            double d11 = Math.atan2(Math.sqrt(d * d + d2 * d2), d1);
            double d12 = Math.sin(d11);
            double d13 = Math.cos(d11);
            double d14 = random.nextDouble() * 3.1415926535897931D * 2D;
            double d15 = Math.sin(d14);
            double d16 = Math.cos(d14);
            for (int j = 0; j < 4; j++) {
                double d17 = 0.0D;
                double d18 = (double) ((j & 2) - 1) * d3;
                double d19 = (double) ((j + 1 & 2) - 1) * d3;
                double d20 = d17;
                double d21 = d18 * d16 - d19 * d15;
                double d22 = d19 * d16 + d18 * d15;
                double d23 = d22;
                double d24 = d21 * d12 + d20 * d13;
                double d25 = d20 * d12 - d21 * d13;
                double d26 = d25 * d9 - d23 * d10;
                double d27 = d24;
                double d28 = d23 * d9 + d25 * d10;
                tessellator.addVertex(d5 + d26, d6 + d27, d7 + d28);
            }

        }

        tessellator.draw();
    }

    public void func_946_a(World world) {
        if (worldObj != null) {
            worldObj.removeWorldAccess(this);
        }
        field_1453_f = -9999D;
        field_1452_g = -9999D;
        field_1451_h = -9999D;
        RenderManager.instance.func_852_a(world);
        worldObj = world;
        blockRenderer = new RenderBlocks(world);
        if (world != null) {
            world.addWorldAccess(this);
            loadRenderers();
        }
    }

    public void loadRenderers() {
        Block.leaves.setGraphicsLevel(mc.gameSettings.fancyGraphics);
        renderDistance = mc.gameSettings.renderDistance;
        if (worldRenderers != null) {
            for (int i = 0; i < worldRenderers.length; i++) {
                worldRenderers[i].func_1204_c();
            }

        }
        int j = 64 << 3 - renderDistance;
        if (j > 400) {j = 400;}
        renderChunksWide = j / 16 + 1;
        renderChunksTall = 8;
        renderChunksDeep = j / 16 + 1;
        worldRenderers = new WorldRenderer[renderChunksWide * renderChunksTall * renderChunksDeep];
        sortedWorldRenderers = new WorldRenderer[renderChunksWide * renderChunksTall * renderChunksDeep];
        int k = 0;
        int l = 0;
        field_1431_B = 0;
        field_1430_C = 0;
        field_1429_D = 0;
        field_1428_E = renderChunksWide;
        field_1427_F = renderChunksTall;
        field_1426_G = renderChunksDeep;
        for (int i1 = 0; i1 < worldRenderersToUpdate.size(); i1++) {
            ((WorldRenderer) worldRenderersToUpdate.get(i1)).needsUpdate = false;
        }

        worldRenderersToUpdate.clear();
        field_1458_a.clear();
        for (int j1 = 0; j1 < renderChunksWide; j1++) {
            for (int k1 = 0; k1 < renderChunksTall; k1++) {
                for (int l1 = 0; l1 < renderChunksDeep; l1++) {
                    worldRenderers[(l1 * renderChunksTall + k1) * renderChunksWide + j1] = new WorldRenderer(worldObj, field_1458_a, j1 * 16, k1 * 16, l1 * 16, 16, field_1440_s + k);
                    if (field_1436_w) {
                        worldRenderers[(l1 * renderChunksTall + k1) * renderChunksWide + j1].field_1732_z = field_1437_v.get(l);
                    }
                    worldRenderers[(l1 * renderChunksTall + k1) * renderChunksWide + j1].isWaitingOnOcclusionQuery = false;
                    worldRenderers[(l1 * renderChunksTall + k1) * renderChunksWide + j1].isVisible = true;
                    worldRenderers[(l1 * renderChunksTall + k1) * renderChunksWide + j1].isInFrustrum = true;
                    worldRenderers[(l1 * renderChunksTall + k1) * renderChunksWide + j1].field_1735_w = l++;
                    worldRenderers[(l1 * renderChunksTall + k1) * renderChunksWide + j1].markDirty();
                    sortedWorldRenderers[(l1 * renderChunksTall + k1) * renderChunksWide + j1] = worldRenderers[(l1 * renderChunksTall + k1) * renderChunksWide + j1];
                    worldRenderersToUpdate.add(worldRenderers[(l1 * renderChunksTall + k1) * renderChunksWide + j1]);
                    k += 3;
                }

            }

        }

        if (worldObj != null) {
            EntityPlayerSP entityplayersp = mc.thePlayer;
            func_956_b(MathHelper.floor_double(((Entity) (entityplayersp)).posX), MathHelper.floor_double(((Entity) (entityplayersp)).posY), MathHelper.floor_double(((Entity) (entityplayersp)).posZ));
            try {
                Arrays.sort(sortedWorldRenderers, new EntitySorter(entityplayersp));
            } catch (IllegalArgumentException e) { System.out.println("TimSort failed with IllegalArgumentException!"); }
        }
        field_1424_I = 2;
    }

    public void func_951_a(Vec3D vec3d, ICamera icamera, float f) {
        if (field_1424_I > 0) {
            field_1424_I--;
            return;
        }
        TileEntityRenderer.instance.setRenderingContext(worldObj, renderEngine, mc.fontRenderer, mc.thePlayer, f);
        RenderManager.instance.func_857_a(worldObj, renderEngine, mc.fontRenderer, mc.thePlayer, mc.gameSettings, f);
        field_1423_J = 0;
        field_1422_K = 0;
        field_1421_L = 0;
        EntityPlayerSP entityplayersp = mc.thePlayer;
        RenderManager.renderPosX = ((Entity) (entityplayersp)).lastTickPosX + (((Entity) (entityplayersp)).posX - ((Entity) (entityplayersp)).lastTickPosX) * (double) f;
        RenderManager.renderPosY = ((Entity) (entityplayersp)).lastTickPosY + (((Entity) (entityplayersp)).posY - ((Entity) (entityplayersp)).lastTickPosY) * (double) f;
        RenderManager.renderPosZ = ((Entity) (entityplayersp)).lastTickPosZ + (((Entity) (entityplayersp)).posZ - ((Entity) (entityplayersp)).lastTickPosZ) * (double) f;
        TileEntityRenderer.staticPlayerX = ((Entity) (entityplayersp)).lastTickPosX + (((Entity) (entityplayersp)).posX - ((Entity) (entityplayersp)).lastTickPosX) * (double) f;
        TileEntityRenderer.staticPlayerY = ((Entity) (entityplayersp)).lastTickPosY + (((Entity) (entityplayersp)).posY - ((Entity) (entityplayersp)).lastTickPosY) * (double) f;
        TileEntityRenderer.staticPlayerZ = ((Entity) (entityplayersp)).lastTickPosZ + (((Entity) (entityplayersp)).posZ - ((Entity) (entityplayersp)).lastTickPosZ) * (double) f;
        List list = worldObj.func_658_i();
        field_1423_J = list.size();
        for (int i = 0; i < list.size(); i++) {
            Entity entity = (Entity) list.get(i);
            if (entity.isInRangeToRenderVec3D(vec3d) && icamera.isBoundingBoxInFrustum(entity.boundingBox) && (entity != mc.thePlayer || mc.gameSettings.thirdPersonView)) {
                field_1422_K++;
                RenderManager.instance.renderEntity(entity, f);
            }
        }

        for (int j = 0; j < field_1458_a.size(); j++) {
            TileEntityRenderer.instance.renderTileEntity((TileEntity) field_1458_a.get(j), f);
        }

    }

    public String func_953_b() {
        return (new StringBuilder()).append("C: ").append(field_1417_P).append("/").append(field_1420_M).append(". F: ").append(field_1419_N).append(", O: ").append(field_1418_O).append(", E: ").append(field_1416_Q).toString();
    }

    public String func_957_c() {
        return (new StringBuilder()).append("E: ").append(field_1422_K).append("/").append(field_1423_J).append(". B: ").append(field_1421_L).append(", I: ").append(field_1423_J - field_1421_L - field_1422_K).toString();
    }

    private void func_956_b(int i, int j, int k) {
        i -= 8;
        j -= 8;
        k -= 8;
        field_1431_B = 0x7fffffff;
        field_1430_C = 0x7fffffff;
        field_1429_D = 0x7fffffff;
        field_1428_E = 0x80000000;
        field_1427_F = 0x80000000;
        field_1426_G = 0x80000000;
        int l = renderChunksWide * 16;
        int i1 = l / 2;
        for (int j1 = 0; j1 < renderChunksWide; j1++) {
            int k1 = j1 * 16;
            int l1 = (k1 + i1) - i;
            if (l1 < 0) {
                l1 -= l - 1;
            }
            l1 /= l;
            k1 -= l1 * l;
            if (k1 < field_1431_B) {
                field_1431_B = k1;
            }
            if (k1 > field_1428_E) {
                field_1428_E = k1;
            }
            for (int i2 = 0; i2 < renderChunksDeep; i2++) {
                int j2 = i2 * 16;
                int k2 = (j2 + i1) - k;
                if (k2 < 0) {
                    k2 -= l - 1;
                }
                k2 /= l;
                j2 -= k2 * l;
                if (j2 < field_1429_D) {
                    field_1429_D = j2;
                }
                if (j2 > field_1426_G) {
                    field_1426_G = j2;
                }
                for (int l2 = 0; l2 < renderChunksTall; l2++) {
                    int i3 = l2 * 16;
                    if (i3 < field_1430_C) {
                        field_1430_C = i3;
                    }
                    if (i3 > field_1427_F) {
                        field_1427_F = i3;
                    }
                    WorldRenderer worldrenderer = worldRenderers[(i2 * renderChunksTall + l2) * renderChunksWide + j1];
                    boolean flag = worldrenderer.needsUpdate;
                    worldrenderer.func_1197_a(k1, i3, j2);
                    if (!flag && worldrenderer.needsUpdate) {
                        worldRenderersToUpdate.add(worldrenderer);
                    }
                }

            }

        }

    }

    public int func_943_a(EntityPlayer entityplayer, int i, double d) {
        if (mc.gameSettings.renderDistance != renderDistance) {
            loadRenderers();
        }
        if (i == 0) {
            field_1420_M = 0;
            field_1419_N = 0;
            field_1418_O = 0;
            field_1417_P = 0;
            field_1416_Q = 0;
        }
        double d1 = entityplayer.lastTickPosX + (entityplayer.posX - entityplayer.lastTickPosX) * d;
        double d2 = entityplayer.lastTickPosY + (entityplayer.posY - entityplayer.lastTickPosY) * d;
        double d3 = entityplayer.lastTickPosZ + (entityplayer.posZ - entityplayer.lastTickPosZ) * d;
        double d4 = entityplayer.posX - field_1453_f;
        double d5 = entityplayer.posY - field_1452_g;
        double d6 = entityplayer.posZ - field_1451_h;
        if (d4 * d4 + d5 * d5 + d6 * d6 > 16D) {
            field_1453_f = entityplayer.posX;
            field_1452_g = entityplayer.posY;
            field_1451_h = entityplayer.posZ;
            func_956_b(MathHelper.floor_double(entityplayer.posX), MathHelper.floor_double(entityplayer.posY), MathHelper.floor_double(entityplayer.posZ));
            try {
                Arrays.sort(sortedWorldRenderers, new EntitySorter(entityplayer));
            } catch (IllegalArgumentException e) { System.out.println("TimSort Failed with IllegalArgumentException!"); }
        }
        int j = 0;
        if (field_1436_w && !mc.gameSettings.anaglyph && i == 0) {
            int k = 0;
            int l = 16;
            func_962_a(k, l);
            for (int i1 = k; i1 < l; i1++) {
                sortedWorldRenderers[i1].isVisible = true;
            }

            j += func_952_a(k, l, i, d);
            do {
                byte byte0 = (byte) l;
                l *= 2;
                if (l > sortedWorldRenderers.length) {
                    l = sortedWorldRenderers.length;
                }
                GL11.glDisable(3553 /*GL_TEXTURE_2D*/);
                GL11.glDisable(2896 /*GL_LIGHTING*/);
                GL11.glDisable(3008 /*GL_ALPHA_TEST*/);
                GL11.glDisable(2912 /*GL_FOG*/);
                GL11.glColorMask(false, false, false, false);
                GL11.glDepthMask(false);
                func_962_a(byte0, l);
                GL11.glPushMatrix();
                float f = 0.0F;
                float f1 = 0.0F;
                float f2 = 0.0F;
                for (int j1 = byte0; j1 < l; j1++) {
                    if (sortedWorldRenderers[j1].canRender()) {
                        sortedWorldRenderers[j1].isInFrustrum = false;
                        continue;
                    }
                    if (!sortedWorldRenderers[j1].isInFrustrum) {
                        sortedWorldRenderers[j1].isVisible = true;
                    }
                    if (!sortedWorldRenderers[j1].isInFrustrum || sortedWorldRenderers[j1].isWaitingOnOcclusionQuery) {
                        continue;
                    }
                    float f3 = MathHelper.sqrt_float(sortedWorldRenderers[j1].distanceToEntity(entityplayer));
                    int k1 = (int) (1.0F + f3 / 128F);
                    if (field_1435_x % k1 != j1 % k1) {
                        continue;
                    }
                    WorldRenderer worldrenderer = sortedWorldRenderers[j1];
                    float f4 = (float) ((double) worldrenderer.field_1755_i - d1);
                    float f5 = (float) ((double) worldrenderer.field_1754_j - d2);
                    float f6 = (float) ((double) worldrenderer.field_1753_k - d3);
                    float f7 = f4 - f;
                    float f8 = f5 - f1;
                    float f9 = f6 - f2;
                    if (f7 != 0.0F || f8 != 0.0F || f9 != 0.0F) {
                        GL11.glTranslatef(f7, f8, f9);
                        f += f7;
                        f1 += f8;
                        f2 += f9;
                    }
                    ARBOcclusionQuery.glBeginQueryARB(35092 /*GL_SAMPLES_PASSED_ARB*/, sortedWorldRenderers[j1].field_1732_z);
                    sortedWorldRenderers[j1].callOcclusionQueryList();
                    ARBOcclusionQuery.glEndQueryARB(35092 /*GL_SAMPLES_PASSED_ARB*/);
                    sortedWorldRenderers[j1].isWaitingOnOcclusionQuery = true;
                }

                GL11.glPopMatrix();
                GL11.glColorMask(true, true, true, true);
                GL11.glDepthMask(true);
                GL11.glEnable(3553 /*GL_TEXTURE_2D*/);
                GL11.glEnable(3008 /*GL_ALPHA_TEST*/);
                GL11.glEnable(2912 /*GL_FOG*/);
                j += func_952_a(byte0, l, i, d);
            } while (l < sortedWorldRenderers.length);
        } else {
            j += func_952_a(0, sortedWorldRenderers.length, i, d);
        }
        return j;
    }

    private void func_962_a(int i, int j) {
        for (int k = i; k < j; k++) {
            if (!sortedWorldRenderers[k].isWaitingOnOcclusionQuery) {
                continue;
            }
            field_1456_c.clear();
            ARBOcclusionQuery.glGetQueryObjectuARB(sortedWorldRenderers[k].field_1732_z, 34919 /*GL_QUERY_RESULT_AVAILABLE_ARB*/, field_1456_c);
            if (field_1456_c.get(0) != 0) {
                sortedWorldRenderers[k].isWaitingOnOcclusionQuery = false;
                field_1456_c.clear();
                ARBOcclusionQuery.glGetQueryObjectuARB(sortedWorldRenderers[k].field_1732_z, 34918 /*GL_QUERY_RESULT_ARB*/, field_1456_c);
                sortedWorldRenderers[k].isVisible = field_1456_c.get(0) != 0;
            }
        }

    }

    private int func_952_a(int i, int j, int k, double d) {
        field_1415_R.clear();
        int l = 0;
        for (int i1 = i; i1 < j; i1++) {
            if (k == 0) {
                field_1420_M++;
                if (sortedWorldRenderers[i1].skipRenderPass[k]) {
                    field_1416_Q++;
                } else if (!sortedWorldRenderers[i1].isInFrustrum) {
                    field_1419_N++;
                } else if (field_1436_w && !sortedWorldRenderers[i1].isVisible) {
                    field_1418_O++;
                } else {
                    field_1417_P++;
                }
            }
            if (sortedWorldRenderers[i1].skipRenderPass[k] || !sortedWorldRenderers[i1].isInFrustrum || !sortedWorldRenderers[i1].isVisible) {
                continue;
            }
            int j1 = sortedWorldRenderers[i1].getGLCallListForPass(k);
            if (j1 >= 0) {
                field_1415_R.add(sortedWorldRenderers[i1]);
                l++;
            }
        }

        EntityPlayerSP entityplayersp = mc.thePlayer;
        double d1 = ((EntityPlayer) (entityplayersp)).lastTickPosX + (((EntityPlayer) (entityplayersp)).posX - ((EntityPlayer) (entityplayersp)).lastTickPosX) * d;
        double d2 = ((EntityPlayer) (entityplayersp)).lastTickPosY + (((EntityPlayer) (entityplayersp)).posY - ((EntityPlayer) (entityplayersp)).lastTickPosY) * d;
        double d3 = ((EntityPlayer) (entityplayersp)).lastTickPosZ + (((EntityPlayer) (entityplayersp)).posZ - ((EntityPlayer) (entityplayersp)).lastTickPosZ) * d;
        int k1 = 0;
        for (int l1 = 0; l1 < field_1414_S.length; l1++) {
            field_1414_S[l1].func_859_b();
        }

        for (int i2 = 0; i2 < field_1415_R.size(); i2++) {
            WorldRenderer worldrenderer = (WorldRenderer) field_1415_R.get(i2);
            int j2 = -1;
            for (int k2 = 0; k2 < k1; k2++) {
                if (field_1414_S[k2].func_862_a(worldrenderer.field_1755_i, worldrenderer.field_1754_j, worldrenderer.field_1753_k)) {
                    j2 = k2;
                }
            }

            if (j2 < 0) {
                j2 = k1++;
                field_1414_S[j2].func_861_a(worldrenderer.field_1755_i, worldrenderer.field_1754_j, worldrenderer.field_1753_k, d1, d2, d3);
            }
            field_1414_S[j2].func_858_a(worldrenderer.getGLCallListForPass(k));
        }

        func_944_a(k, d);
        return l;
    }

    public void func_944_a(int i, double d) {
        for (int j = 0; j < field_1414_S.length; j++) {
            field_1414_S[j].func_860_a();
        }

    }

    public void func_945_d() {
        field_1435_x++;
    }

    public void func_4142_a(float f) {
        if (mc.theWorld.worldProvider.field_4220_c) {
            return;
        }
        GL11.glDisable(3553 /*GL_TEXTURE_2D*/);
        Vec3D vec3d = worldObj.func_4079_a(mc.thePlayer, f);
        float f1 = (float) vec3d.xCoord;
        float f2 = (float) vec3d.yCoord;
        float f3 = (float) vec3d.zCoord;
        if (mc.gameSettings.anaglyph) {
            float f4 = (f1 * 30F + f2 * 59F + f3 * 11F) / 100F;
            float f5 = (f1 * 30F + f2 * 70F) / 100F;
            float f7 = (f1 * 30F + f3 * 70F) / 100F;
            f1 = f4;
            f2 = f5;
            f3 = f7;
        }
        GL11.glColor3f(f1, f2, f3);
        Tessellator tessellator = Tessellator.instance;
        GL11.glDepthMask(false);
        GL11.glEnable(2912 /*GL_FOG*/);
        GL11.glColor3f(f1, f2, f3);
        GL11.glCallList(field_1433_z);
        GL11.glDisable(2912 /*GL_FOG*/);
        GL11.glDisable(3008 /*GL_ALPHA_TEST*/);
        GL11.glEnable(3042 /*GL_BLEND*/);
        GL11.glBlendFunc(770, 771);
        float af[] = worldObj.worldProvider.func_4097_b(worldObj.getCelestialAngle(f), f);
        if (af != null) {
            GL11.glDisable(3553 /*GL_TEXTURE_2D*/);
            GL11.glShadeModel(7425 /*GL_SMOOTH*/);
            GL11.glPushMatrix();
            GL11.glRotatef(90F, 1.0F, 0.0F, 0.0F);
            float f8 = worldObj.getCelestialAngle(f);
            GL11.glRotatef(f8 <= 0.5F ? 0.0F : 180F, 0.0F, 0.0F, 1.0F);
            tessellator.startDrawing(6);
            tessellator.setColorRGBA_F(af[0], af[1], af[2], af[3]);
            tessellator.addVertex(0.0D, 100D, 0.0D);
            int i = 16;
            tessellator.setColorRGBA_F(af[0], af[1], af[2], 0.0F);
            for (int j = 0; j <= i; j++) {
                float f12 = ((float) j * 3.141593F * 2.0F) / (float) i;
                float f14 = MathHelper.sin(f12);
                float f15 = MathHelper.cos(f12);
                tessellator.addVertex(f14 * 120F, f15 * 120F, -f15 * 40F * af[3]);
            }

            tessellator.draw();
            GL11.glPopMatrix();
            GL11.glShadeModel(7424 /*GL_FLAT*/);
        }
        GL11.glEnable(3553 /*GL_TEXTURE_2D*/);
        GL11.glBlendFunc(1, 1);
        GL11.glPushMatrix();
        float f6 = 0.0F;
        float f9 = 0.0F;
        float f10 = 0.0F;
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glTranslatef(f6, f9, f10);
        GL11.glRotatef(0.0F, 0.0F, 0.0F, 1.0F);
        GL11.glRotatef(worldObj.getCelestialAngle(f) * 360F, 1.0F, 0.0F, 0.0F);
        float f11 = 30F;
        GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, renderEngine.getTexture("/terrain/sun.png"));
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(-f11, 100D, -f11, 0.0D, 0.0D);
        tessellator.addVertexWithUV(f11, 100D, -f11, 1.0D, 0.0D);
        tessellator.addVertexWithUV(f11, 100D, f11, 1.0D, 1.0D);
        tessellator.addVertexWithUV(-f11, 100D, f11, 0.0D, 1.0D);
        tessellator.draw();
        f11 = 20F;
        GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, renderEngine.getTexture("/terrain/moon.png"));
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(-f11, -100D, f11, 1.0D, 1.0D);
        tessellator.addVertexWithUV(f11, -100D, f11, 0.0D, 1.0D);
        tessellator.addVertexWithUV(f11, -100D, -f11, 0.0D, 0.0D);
        tessellator.addVertexWithUV(-f11, -100D, -f11, 1.0D, 0.0D);
        tessellator.draw();
        GL11.glDisable(3553 /*GL_TEXTURE_2D*/);
        float f13 = worldObj.func_679_f(f);
        if (f13 > 0.0F) {
            GL11.glColor4f(f13, f13, f13, f13);
            GL11.glCallList(field_1434_y);
        }
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(3042 /*GL_BLEND*/);
        GL11.glEnable(3008 /*GL_ALPHA_TEST*/);
        GL11.glEnable(2912 /*GL_FOG*/);
        GL11.glPopMatrix();
        GL11.glColor3f(f1 * 0.2F + 0.04F, f2 * 0.2F + 0.04F, f3 * 0.6F + 0.1F);
        GL11.glDisable(3553 /*GL_TEXTURE_2D*/);
        GL11.glCallList(field_1432_A);
        GL11.glEnable(3553 /*GL_TEXTURE_2D*/);
        GL11.glDepthMask(true);
    }

    public void func_4141_b(float f) {
        if (mc.theWorld.worldProvider.field_4220_c) {
            return;
        }
        if (mc.gameSettings.fancyGraphics) {
            func_6510_c(f);
            return;
        }
        GL11.glDisable(2884 /*GL_CULL_FACE*/);
        float f1 = (float) (mc.thePlayer.lastTickPosY + (mc.thePlayer.posY - mc.thePlayer.lastTickPosY) * (double) f);
        byte b32 = 32;
        int i = 256 / b32;
        Tessellator tessellator = Tessellator.instance;
        GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, renderEngine.getTexture("/environment/clouds.png"));
        GL11.glEnable(3042 /*GL_BLEND*/);
        GL11.glBlendFunc(770, 771);
        Vec3D vec3d = worldObj.func_628_d(f);
        float x = (float) vec3d.xCoord;
        float y = (float) vec3d.yCoord;
        float z = (float) vec3d.zCoord;
        if (mc.gameSettings.anaglyph) {
            float f5 = (x * 30F + y * 59F + z * 11F) / 100F;
            float f7 = (x * 30F + y * 70F) / 100F;
            float f8 = (x * 30F + z * 70F) / 100F;
            x = f5;
            y = f7;
            z = f8;
        }
        float f6 = 0.0004882813F;
        double d = mc.thePlayer.prevPosX + (mc.thePlayer.posX - mc.thePlayer.prevPosX) * (double) f + (double) (((float) field_1435_x + f) * 0.03F);
        double d1 = mc.thePlayer.prevPosZ + (mc.thePlayer.posZ - mc.thePlayer.prevPosZ) * (double) f;
        int j = MathHelper.floor_double(d / 2048D);
        int k = MathHelper.floor_double(d1 / 2048D);
        d -= j * 2048 /*GL_EXP*/;
        d1 -= k * 2048 /*GL_EXP*/;
        float f9 = (120F - f1) + 0.33F;
        float f10 = (float) (d * (double) f6);
        float f11 = (float) (d1 * (double) f6);
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA_F(x, y, z, 0.8F);
        for (int l = -b32 * i; l < b32 * i; l += b32) {
            for (int i1 = -b32 * i; i1 < b32 * i; i1 += b32) {
                tessellator.addVertexWithUV(l + 0, f9, i1 + b32, (float) (l + 0) * f6 + f10, (float) (i1 + b32) * f6 + f11);
                tessellator.addVertexWithUV(l + b32, f9, i1 + b32, (float) (l + b32) * f6 + f10, (float) (i1 + b32) * f6 + f11);
                tessellator.addVertexWithUV(l + b32, f9, i1 + 0, (float) (l + b32) * f6 + f10, (float) (i1 + 0) * f6 + f11);
                tessellator.addVertexWithUV(l + 0, f9, i1 + 0, (float) (l + 0) * f6 + f10, (float) (i1 + 0) * f6 + f11);
            }

        }

        tessellator.draw();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(3042 /*GL_BLEND*/);
        GL11.glEnable(2884 /*GL_CULL_FACE*/);
    }

    public void func_6510_c(float f) {
        GL11.glDisable(2884 /*GL_CULL_FACE*/);
        float f1 = (float) (mc.thePlayer.lastTickPosY + (mc.thePlayer.posY - mc.thePlayer.lastTickPosY) * (double) f);
        Tessellator tessellator = Tessellator.instance;
        float f2 = 12F;
        float f3 = 4F;
        double d = (mc.thePlayer.prevPosX + (mc.thePlayer.posX - mc.thePlayer.prevPosX) * (double) f + (double) (((float) field_1435_x + f) * 0.03F)) / (double) f2;
        double d1 = (mc.thePlayer.prevPosZ + (mc.thePlayer.posZ - mc.thePlayer.prevPosZ) * (double) f) / (double) f2 + 0.33000001311302185D;
        float f4 = (108F - f1 + MinecraftFexClientConfig.cloudHeightModifier) + 0.33F;
        int i = MathHelper.floor_double(d / 2048D);
        int j = MathHelper.floor_double(d1 / 2048D);
        d -= i * 2048 /*GL_EXP*/;
        d1 -= j * 2048 /*GL_EXP*/;
        GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, renderEngine.getTexture("/environment/clouds.png"));
        GL11.glEnable(3042 /*GL_BLEND*/);
        GL11.glBlendFunc(770, 771);
        Vec3D vec3d = worldObj.func_628_d(f);
        float f5 = (float) vec3d.xCoord;
        float f6 = (float) vec3d.yCoord;
        float f7 = (float) vec3d.zCoord;
        if (mc.gameSettings.anaglyph) {
            float f8 = (f5 * 30F + f6 * 59F + f7 * 11F) / 100F;
            float f10 = (f5 * 30F + f6 * 70F) / 100F;
            float f12 = (f5 * 30F + f7 * 70F) / 100F;
            f5 = f8;
            f6 = f10;
            f7 = f12;
        }
        float f9 = (float) (d * 0.0D);
        float f11 = (float) (d1 * 0.0D);
        float f13 = 0.00390625F;
        f9 = (float) MathHelper.floor_double(d) * f13;
        f11 = (float) MathHelper.floor_double(d1) * f13;
        float f14 = (float) (d - (double) MathHelper.floor_double(d));
        float f15 = (float) (d1 - (double) MathHelper.floor_double(d1));
        int k = 8;
        byte byte0 = 3;
        float f16 = 0.0009765625F;
        GL11.glScalef(f2, 1.0F, f2);
        for (int l = 0; l < 2; l++) {
            if (l == 0) {
                GL11.glColorMask(false, false, false, false);
            } else {
                GL11.glColorMask(true, true, true, true);
            }
            for (int i1 = -byte0 + 1; i1 <= byte0; i1++) {
                for (int j1 = -byte0 + 1; j1 <= byte0; j1++) {
                    tessellator.startDrawingQuads();
                    float f17 = i1 * k;
                    float f18 = j1 * k;
                    float f19 = f17 - f14;
                    float f20 = f18 - f15;
                    if (f4 > -f3 - 1.0F) {
                        tessellator.setColorRGBA_F(f5 * 0.7F, f6 * 0.7F, f7 * 0.7F, 0.8F);
                        tessellator.setNormal(0.0F, -1F, 0.0F);
                        tessellator.addVertexWithUV(f19 + 0.0F, f4 + 0.0F, f20 + (float) k, (f17 + 0.0F) * f13 + f9, (f18 + (float) k) * f13 + f11);
                        tessellator.addVertexWithUV(f19 + (float) k, f4 + 0.0F, f20 + (float) k, (f17 + (float) k) * f13 + f9, (f18 + (float) k) * f13 + f11);
                        tessellator.addVertexWithUV(f19 + (float) k, f4 + 0.0F, f20 + 0.0F, (f17 + (float) k) * f13 + f9, (f18 + 0.0F) * f13 + f11);
                        tessellator.addVertexWithUV(f19 + 0.0F, f4 + 0.0F, f20 + 0.0F, (f17 + 0.0F) * f13 + f9, (f18 + 0.0F) * f13 + f11);
                    }
                    if (f4 <= f3 + 1.0F) {
                        tessellator.setColorRGBA_F(f5, f6, f7, 0.8F);
                        tessellator.setNormal(0.0F, 1.0F, 0.0F);
                        tessellator.addVertexWithUV(f19 + 0.0F, (f4 + f3) - f16, f20 + (float) k, (f17 + 0.0F) * f13 + f9, (f18 + (float) k) * f13 + f11);
                        tessellator.addVertexWithUV(f19 + (float) k, (f4 + f3) - f16, f20 + (float) k, (f17 + (float) k) * f13 + f9, (f18 + (float) k) * f13 + f11);
                        tessellator.addVertexWithUV(f19 + (float) k, (f4 + f3) - f16, f20 + 0.0F, (f17 + (float) k) * f13 + f9, (f18 + 0.0F) * f13 + f11);
                        tessellator.addVertexWithUV(f19 + 0.0F, (f4 + f3) - f16, f20 + 0.0F, (f17 + 0.0F) * f13 + f9, (f18 + 0.0F) * f13 + f11);
                    }
                    tessellator.setColorRGBA_F(f5 * 0.9F, f6 * 0.9F, f7 * 0.9F, 0.8F);
                    if (i1 > -1) {
                        tessellator.setNormal(-1F, 0.0F, 0.0F);
                        for (int k1 = 0; k1 < k; k1++) {
                            tessellator.addVertexWithUV(f19 + (float) k1 + 0.0F, f4 + 0.0F, f20 + (float) k, (f17 + (float) k1 + 0.5F) * f13 + f9, (f18 + (float) k) * f13 + f11);
                            tessellator.addVertexWithUV(f19 + (float) k1 + 0.0F, f4 + f3, f20 + (float) k, (f17 + (float) k1 + 0.5F) * f13 + f9, (f18 + (float) k) * f13 + f11);
                            tessellator.addVertexWithUV(f19 + (float) k1 + 0.0F, f4 + f3, f20 + 0.0F, (f17 + (float) k1 + 0.5F) * f13 + f9, (f18 + 0.0F) * f13 + f11);
                            tessellator.addVertexWithUV(f19 + (float) k1 + 0.0F, f4 + 0.0F, f20 + 0.0F, (f17 + (float) k1 + 0.5F) * f13 + f9, (f18 + 0.0F) * f13 + f11);
                        }

                    }
                    if (i1 <= 1) {
                        tessellator.setNormal(1.0F, 0.0F, 0.0F);
                        for (int l1 = 0; l1 < k; l1++) {
                            tessellator.addVertexWithUV((f19 + (float) l1 + 1.0F) - f16, f4 + 0.0F, f20 + (float) k, (f17 + (float) l1 + 0.5F) * f13 + f9, (f18 + (float) k) * f13 + f11);
                            tessellator.addVertexWithUV((f19 + (float) l1 + 1.0F) - f16, f4 + f3, f20 + (float) k, (f17 + (float) l1 + 0.5F) * f13 + f9, (f18 + (float) k) * f13 + f11);
                            tessellator.addVertexWithUV((f19 + (float) l1 + 1.0F) - f16, f4 + f3, f20 + 0.0F, (f17 + (float) l1 + 0.5F) * f13 + f9, (f18 + 0.0F) * f13 + f11);
                            tessellator.addVertexWithUV((f19 + (float) l1 + 1.0F) - f16, f4 + 0.0F, f20 + 0.0F, (f17 + (float) l1 + 0.5F) * f13 + f9, (f18 + 0.0F) * f13 + f11);
                        }

                    }
                    tessellator.setColorRGBA_F(f5 * 0.8F, f6 * 0.8F, f7 * 0.8F, 0.8F);
                    if (j1 > -1) {
                        tessellator.setNormal(0.0F, 0.0F, -1F);
                        for (int i2 = 0; i2 < k; i2++) {
                            tessellator.addVertexWithUV(f19 + 0.0F, f4 + f3, f20 + (float) i2 + 0.0F, (f17 + 0.0F) * f13 + f9, (f18 + (float) i2 + 0.5F) * f13 + f11);
                            tessellator.addVertexWithUV(f19 + (float) k, f4 + f3, f20 + (float) i2 + 0.0F, (f17 + (float) k) * f13 + f9, (f18 + (float) i2 + 0.5F) * f13 + f11);
                            tessellator.addVertexWithUV(f19 + (float) k, f4 + 0.0F, f20 + (float) i2 + 0.0F, (f17 + (float) k) * f13 + f9, (f18 + (float) i2 + 0.5F) * f13 + f11);
                            tessellator.addVertexWithUV(f19 + 0.0F, f4 + 0.0F, f20 + (float) i2 + 0.0F, (f17 + 0.0F) * f13 + f9, (f18 + (float) i2 + 0.5F) * f13 + f11);
                        }

                    }
                    if (j1 <= 1) {
                        tessellator.setNormal(0.0F, 0.0F, 1.0F);
                        for (int j2 = 0; j2 < k; j2++) {
                            tessellator.addVertexWithUV(f19 + 0.0F, f4 + f3, (f20 + (float) j2 + 1.0F) - f16, (f17 + 0.0F) * f13 + f9, (f18 + (float) j2 + 0.5F) * f13 + f11);
                            tessellator.addVertexWithUV(f19 + (float) k, f4 + f3, (f20 + (float) j2 + 1.0F) - f16, (f17 + (float) k) * f13 + f9, (f18 + (float) j2 + 0.5F) * f13 + f11);
                            tessellator.addVertexWithUV(f19 + (float) k, f4 + 0.0F, (f20 + (float) j2 + 1.0F) - f16, (f17 + (float) k) * f13 + f9, (f18 + (float) j2 + 0.5F) * f13 + f11);
                            tessellator.addVertexWithUV(f19 + 0.0F, f4 + 0.0F, (f20 + (float) j2 + 1.0F) - f16, (f17 + 0.0F) * f13 + f9, (f18 + (float) j2 + 0.5F) * f13 + f11);
                        }

                    }
                    tessellator.draw();
                }

            }

        }

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(3042 /*GL_BLEND*/);
        GL11.glEnable(2884 /*GL_CULL_FACE*/);
    }

    public boolean updateRenderers(EntityPlayer entityplayer, boolean flag) {
        Collections.sort(worldRenderersToUpdate, new RenderSorter(entityplayer));
        int i = worldRenderersToUpdate.size() - 1;
        int j = worldRenderersToUpdate.size();
        for (int k = 0; k < j; k++) {
            WorldRenderer worldrenderer = (WorldRenderer) worldRenderersToUpdate.get(i - k);
            if (!flag) {
                if (worldrenderer.distanceToEntity(entityplayer) > 1024F) {
                    if (worldrenderer.isInFrustrum) {
                        if (k >= 3) {
                            return false;
                        }
                    } else if (k >= 1) {
                        return false;
                    }
                }
            } else if (!worldrenderer.isInFrustrum) {
                continue;
            }
            worldrenderer.updateRenderer();
            worldRenderersToUpdate.remove(worldrenderer);
            worldrenderer.needsUpdate = false;
        }

        return worldRenderersToUpdate.size() == 0;
    }

    public void func_959_a(EntityPlayer entityplayer, MovingObjectPosition movingobjectposition, int i, ItemStack itemstack, float f) {
        Tessellator tessellator = Tessellator.instance;
        GL11.glEnable(3042 /*GL_BLEND*/);
        GL11.glEnable(3008 /*GL_ALPHA_TEST*/);
        GL11.glBlendFunc(770, 1);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, (MathHelper.sin((float) System.currentTimeMillis() / 100F) * 0.2F + 0.4F) * 0.5F);
        if (i == 0) {
            if (field_1450_i > 0.0F) {
                GL11.glBlendFunc(774, 768);
                int j = renderEngine.getTexture("/terrain.png");
                GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, j);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
                GL11.glPushMatrix();
                int k = worldObj.getBlockId(movingobjectposition.blockX, movingobjectposition.blockY, movingobjectposition.blockZ);
                Block block = k <= 0 ? null : Block.blocksList[k];
                GL11.glDisable(3008 /*GL_ALPHA_TEST*/);
                GL11.glPolygonOffset(-3F, -3F);
                GL11.glEnable(32823 /*GL_POLYGON_OFFSET_FILL*/);
                tessellator.startDrawingQuads();
                double d = entityplayer.lastTickPosX + (entityplayer.posX - entityplayer.lastTickPosX) * (double) f;
                double d1 = entityplayer.lastTickPosY + (entityplayer.posY - entityplayer.lastTickPosY) * (double) f;
                double d2 = entityplayer.lastTickPosZ + (entityplayer.posZ - entityplayer.lastTickPosZ) * (double) f;
                tessellator.setTranslationD(-d, -d1, -d2);
                tessellator.disableColor();
                if (block == null) {
                    block = Block.stone;
                }
                blockRenderer.renderBlockUsingTexture(block, movingobjectposition.blockX, movingobjectposition.blockY, movingobjectposition.blockZ, 240 + (int) (field_1450_i * 10F));
                tessellator.draw();
                tessellator.setTranslationD(0.0D, 0.0D, 0.0D);
                GL11.glPolygonOffset(0.0F, 0.0F);
                GL11.glDisable(32823 /*GL_POLYGON_OFFSET_FILL*/);
                GL11.glEnable(3008 /*GL_ALPHA_TEST*/);
                GL11.glDepthMask(true);
                GL11.glPopMatrix();
            }
        } else if (itemstack != null) {
            GL11.glBlendFunc(770, 771);
            float f1 = MathHelper.sin((float) System.currentTimeMillis() / 100F) * 0.2F + 0.8F;
            GL11.glColor4f(f1, f1, f1, MathHelper.sin((float) System.currentTimeMillis() / 200F) * 0.2F + 0.5F);
            int l = renderEngine.getTexture("/terrain.png");
            GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, l);
            int i1 = movingobjectposition.blockX;
            int j1 = movingobjectposition.blockY;
            int k1 = movingobjectposition.blockZ;
            if (movingobjectposition.sideHit == 0) {
                j1--;
            }
            if (movingobjectposition.sideHit == 1) {
                j1++;
            }
            if (movingobjectposition.sideHit == 2) {
                k1--;
            }
            if (movingobjectposition.sideHit == 3) {
                k1++;
            }
            if (movingobjectposition.sideHit == 4) {
                i1--;
            }
            if (movingobjectposition.sideHit == 5) {
                i1++;
            }
        }
        GL11.glDisable(3042 /*GL_BLEND*/);
        GL11.glDisable(3008 /*GL_ALPHA_TEST*/);
    }

    public void drawSelectionBox(EntityPlayer entityplayer, MovingObjectPosition movingobjectposition, int i, ItemStack itemstack, float f) {
        if (i == 0 && movingobjectposition.typeOfHit == 0) {
            GL11.glEnable(3042 /*GL_BLEND*/);
            GL11.glBlendFunc(770, 771);
            GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.4F);
            GL11.glLineWidth(2.0F);
            GL11.glDisable(3553 /*GL_TEXTURE_2D*/);
            GL11.glDepthMask(false);
            float f1 = 0.002F;
            int j = worldObj.getBlockId(movingobjectposition.blockX, movingobjectposition.blockY, movingobjectposition.blockZ);
            if (j > 0) {
                Block.blocksList[j].setBlockBoundsBasedOnState(worldObj, movingobjectposition.blockX, movingobjectposition.blockY, movingobjectposition.blockZ);
                double d = entityplayer.lastTickPosX + (entityplayer.posX - entityplayer.lastTickPosX) * (double) f;
                double d1 = entityplayer.lastTickPosY + (entityplayer.posY - entityplayer.lastTickPosY) * (double) f;
                double d2 = entityplayer.lastTickPosZ + (entityplayer.posZ - entityplayer.lastTickPosZ) * (double) f;
                drawOutlinedBoundingBox(Block.blocksList[j].getSelectedBoundingBoxFromPool(worldObj, movingobjectposition.blockX, movingobjectposition.blockY, movingobjectposition.blockZ).expand(f1, f1, f1).getOffsetBoundingBox(-d, -d1, -d2));
            }
            GL11.glDepthMask(true);
            GL11.glEnable(3553 /*GL_TEXTURE_2D*/);
            GL11.glDisable(3042 /*GL_BLEND*/);
        }
    }

    private void drawOutlinedBoundingBox(AxisAlignedBB axisalignedbb) {
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawing(3);
        tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
        tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
        tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
        tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
        tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
        tessellator.draw();
        tessellator.startDrawing(3);
        tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
        tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
        tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
        tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
        tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
        tessellator.draw();
        tessellator.startDrawing(1);
        tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
        tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
        tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
        tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
        tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
        tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
        tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
        tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
        tessellator.draw();
    }

    // I think this funtion might check all loaded chunks and send them into an update queue. Not 100% sure though
    public void func_949_a(int i, int j, int k, int l, int i1, int j1) {
        int k1 = MathHelper.bucketInt(i, 16);
        int l1 = MathHelper.bucketInt(j, 16);
        int i2 = MathHelper.bucketInt(k, 16);
        int j2 = MathHelper.bucketInt(l, 16);
        int k2 = MathHelper.bucketInt(i1, 16);
        int l2 = MathHelper.bucketInt(j1, 16);
        for (int i3 = k1; i3 <= j2; i3++) {
            int j3 = i3 % renderChunksWide;
            if (j3 < 0) {
                j3 += renderChunksWide;
            }
            for (int k3 = l1; k3 <= k2; k3++) {
                int l3 = k3 % renderChunksTall;
                if (l3 < 0) {
                    l3 += renderChunksTall;
                }
                for (int i4 = i2; i4 <= l2; i4++) {
                    int j4 = i4 % renderChunksDeep;
                    if (j4 < 0) {
                        j4 += renderChunksDeep;
                    }
                    int k4 = (j4 * renderChunksTall + l3) * renderChunksWide + j3;
                    WorldRenderer worldrenderer = worldRenderers[k4];
                    if (!worldrenderer.needsUpdate) {
                        worldRenderersToUpdate.add(worldrenderer);
                    }
                    worldrenderer.markDirty();
                }

            }

        }

    }

    public void func_934_a(int i, int j, int k) {
        func_949_a(i - 1, j - 1, k - 1, i + 1, j + 1, k + 1);
    }

    public void markBlockRangeNeedsUpdate(int i, int j, int k, int l, int i1, int j1) {
        func_949_a(i - 1, j - 1, k - 1, l + 1, i1 + 1, j1 + 1);
    }

    public void func_960_a(ICamera icamera, float f) {
        for (int i = 0; i < worldRenderers.length; i++) {
            if (!worldRenderers[i].canRender() && (!worldRenderers[i].isInFrustrum || (i + field_1449_j & 0xf) == 0)) {
                worldRenderers[i].updateInFrustrum(icamera);
            }
        }

        field_1449_j++;
    }

    public void playRecord(String s, int i, int j, int k) {
        if (s != null) {
            mc.ingameGUI.func_553_b((new StringBuilder()).append("C418 - ").append(s).toString());
        }
        mc.sndManager.func_331_a(s, i, j, k, 1.0F, 1.0F);
    }

    public void playSound(String s, double d, double d1, double d2,
                          float f, float f1) {
        float f2 = 16F;
        if (f > 1.0F) {
            f2 *= f;
        }
        if (mc.thePlayer.getDistanceSq(d, d1, d2) < (double) (f2 * f2)) {
            mc.sndManager.playSound(s, (float) d, (float) d1, (float) d2, f, f1);
        }
    }

    public void spawnParticle(String s, double d, double d1, double d2,
                              double d3, double d4, double d5) {
        double d6 = mc.thePlayer.posX - d;
        double d7 = mc.thePlayer.posY - d1;
        double d8 = mc.thePlayer.posZ - d2;
        if (d6 * d6 + d7 * d7 + d8 * d8 > 256D) {
            return;
        }
        if (s == "bubble") {
            mc.effectRenderer.func_1192_a(new EntityBubbleFX(worldObj, d, d1, d2, d3, d4, d5));
        } else if (s == "smoke") {
            mc.effectRenderer.func_1192_a(new EntitySmokeFX(worldObj, d, d1, d2));
        } else if (s == "portal") {
            mc.effectRenderer.func_1192_a(new EntityPortalFX(worldObj, d, d1, d2, d3, d4, d5));
        } else if (s == "explode") {
            mc.effectRenderer.func_1192_a(new EntityExplodeFX(worldObj, d, d1, d2, d3, d4, d5));
        } else if (s == "flame") {
            mc.effectRenderer.func_1192_a(new EntityFlameFX(worldObj, d, d1, d2, d3, d4, d5));
        } else if (s == "lava") {
            mc.effectRenderer.func_1192_a(new EntityLavaFX(worldObj, d, d1, d2));
        } else if (s == "splash") {
            mc.effectRenderer.func_1192_a(new EntitySplashFX(worldObj, d, d1, d2, d3, d4, d5));
        } else if (s == "largesmoke") {
            mc.effectRenderer.func_1192_a(new EntitySmokeFX(worldObj, d, d1, d2, 2.5F));
        } else if (s == "reddust") {
            mc.effectRenderer.func_1192_a(new EntityReddustFX(worldObj, d, d1, d2));
        } else if (s == "snowballpoof") {
            mc.effectRenderer.func_1192_a(new EntitySlimeFX(worldObj, d, d1, d2, Item.snowball));
        } else if (s == "slime") {
            mc.effectRenderer.func_1192_a(new EntitySlimeFX(worldObj, d, d1, d2, Item.slimeBall));
        }
    }

    public void obtainEntitySkin(Entity entity) {
        entity.func_20046_s();
        if (entity.field_20047_bv != null) {
            renderEngine.obtainImageData(entity.field_20047_bv, new ImageBufferDownload());
        }
        if (entity.skinUrl != null) {
            renderEngine.obtainImageData(entity.skinUrl, new ImageBufferDownload());
        }
    }

    public void releaseEntitySkin(Entity entity) {
        if (entity.field_20047_bv != null) {
            renderEngine.releaseImageData(entity.field_20047_bv);
        }
        if (entity.skinUrl != null) {
            renderEngine.releaseImageData(entity.skinUrl);
        }
    }

    public void updateAllRenderers() {
        for (int i = 0; i < worldRenderers.length; i++) {
            if (!worldRenderers[i].field_1747_A) {
                continue;
            }
            if (!worldRenderers[i].needsUpdate) {
                worldRenderersToUpdate.add(worldRenderers[i]);
            }
            worldRenderers[i].markDirty();
        }

    }

    public void func_935_a(int i, int j, int k, TileEntity tileentity) {
    }

    public List field_1458_a;
    private World worldObj;
    private RenderEngine renderEngine;
    private List worldRenderersToUpdate;
    private WorldRenderer sortedWorldRenderers[];
    private WorldRenderer worldRenderers[];
    private int renderChunksWide;
    private int renderChunksTall;
    private int renderChunksDeep;
    private int field_1440_s;
    private Minecraft mc;
    private RenderBlocks blockRenderer;
    private IntBuffer field_1437_v;
    private boolean field_1436_w;
    private int field_1435_x;
    private int field_1434_y;
    private int field_1433_z;
    private int field_1432_A;
    private int field_1431_B;
    private int field_1430_C;
    private int field_1429_D;
    private int field_1428_E;
    private int field_1427_F;
    private int field_1426_G;
    public int renderDistance;
    private int field_1424_I;
    private int field_1423_J;
    private int field_1422_K;
    private int field_1421_L;
    int field_1457_b[];
    IntBuffer field_1456_c;
    private int field_1420_M;
    private int field_1419_N;
    private int field_1418_O;
    private int field_1417_P;
    private int field_1416_Q;
    private List field_1415_R;
    private RenderList field_1414_S[] = {
            new RenderList(), new RenderList(), new RenderList(), new RenderList()
    };
    int field_1455_d;
    int field_1454_e;
    double field_1453_f;
    double field_1452_g;
    double field_1451_h;
    public float field_1450_i;
    int field_1449_j;
}
