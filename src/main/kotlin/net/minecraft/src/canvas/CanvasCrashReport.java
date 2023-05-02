package net.minecraft.src.canvas;

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.awt.Canvas;
import java.awt.Dimension;

public class CanvasCrashReport extends Canvas
{

    public CanvasCrashReport(int i)
    {
        setPreferredSize(new Dimension(i, i));
        setMinimumSize(new Dimension(i, i));
    }
}
