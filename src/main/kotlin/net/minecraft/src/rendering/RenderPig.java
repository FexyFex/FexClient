package net.minecraft.src.rendering;

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


import net.minecraft.src.entity.EntityLiving;
import net.minecraft.src.entity.EntityPig;
import net.minecraft.src.model.ModelBase;

public class RenderPig extends RenderLiving
{

    public RenderPig(ModelBase modelbase, ModelBase modelbase1, float f)
    {
        super(modelbase, f);
        setRenderPassModel(modelbase1);
    }

    protected boolean func_180_a(EntityPig entitypig, int i)
    {
        loadTexture("/mob/saddle.png");
        return i == 0 && entitypig.rideable;
    }

    protected boolean shouldRenderPass(EntityLiving entityliving, int i)
    {
        return func_180_a((EntityPig)entityliving, i);
    }
}
