package net.minecraft.src.world;

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.block.World;
import net.minecraft.src.canvas.CanvasIsomPreview;
import net.minecraft.src.world.chunk.ChunkLoader;
import net.minecraft.src.world.chunk.ChunkProviderIso;
import net.minecraft.src.world.chunk.IChunkProvider;

import java.io.File;

public class WorldIso extends World
{

    public WorldIso(CanvasIsomPreview canvasisompreview, File file, String s)
    {
        super(file, s);
        field_1051_z = canvasisompreview;
    }

    protected IChunkProvider func_4081_a(File file)
    {
        return new ChunkProviderIso(this, new ChunkLoader(file, false));
    }

    final CanvasIsomPreview field_1051_z; /* synthetic field */
}
