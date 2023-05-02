package net.minecraft.src;

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.client.Minecraft;
import net.minecraft.client.MinecraftApplet;
import net.minecraft.src.error.UnexpectedThrowable;
import net.minecraft.src.gui.PanelCrashReport;


import java.awt.*;

public final class MinecraftImpl extends Minecraft {

    public MinecraftImpl(Component component, Canvas canvas, MinecraftApplet minecraftapplet, int i, int j, boolean flag, Frame frame) {
        super(component, canvas, minecraftapplet, i, j, flag);
        mainFrame = frame;
    }

    public void displayUnexpectedThrowable(UnexpectedThrowable unexpectedthrowable) {
        mainFrame.removeAll();
        mainFrame.add(new PanelCrashReport(unexpectedthrowable), "Center");
        mainFrame.validate();
    }

    final Frame mainFrame; /* synthetic field */
}
