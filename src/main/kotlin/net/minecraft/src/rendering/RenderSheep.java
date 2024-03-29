package net.minecraft.src.rendering;// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


import net.minecraft.src.entity.EntityLiving;
import net.minecraft.src.entity.EntitySheep;
import net.minecraft.src.model.ModelBase;

public class RenderSheep extends RenderLiving
{

    public RenderSheep(ModelBase modelbase, ModelBase modelbase1, float f)
    {
        super(modelbase, f);
        setRenderPassModel(modelbase1);
    }

    protected boolean func_176_a(EntitySheep entitysheep, int i)
    {
        loadTexture("/mob/sheep_fur.png");
        return i == 0 && !entitysheep.sheared;
    }

    protected boolean shouldRenderPass(EntityLiving entityliving, int i)
    {
        return func_176_a((EntitySheep)entityliving, i);
    }
}
