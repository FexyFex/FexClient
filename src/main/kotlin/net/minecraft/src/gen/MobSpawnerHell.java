package net.minecraft.src.gen;// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


import net.minecraft.src.entity.EntityGhast;
import net.minecraft.src.entity.EntityPigZombie;

public class MobSpawnerHell extends MobSpawnerBase
{

    public MobSpawnerHell()
    {
        biomeMonsters = (new Class[] {
            EntityGhast.class, EntityPigZombie.class
        });
        biomeCreatures = new Class[0];
    }
}