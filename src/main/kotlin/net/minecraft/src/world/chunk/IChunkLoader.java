package net.minecraft.src.world.chunk;// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.block.World;
import net.minecraft.src.world.chunk.Chunk;

import java.io.IOException;

public interface IChunkLoader
{

    public abstract Chunk loadChunk(World world, int i, int j) throws IOException;

    public abstract void saveChunk(World world, Chunk chunk) throws IOException;

    public abstract void saveExtraChunkData(World world, Chunk chunk) throws IOException;

    public abstract void func_814_a();

    public abstract void saveExtraData();
}
