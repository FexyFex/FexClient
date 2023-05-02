package net.minecraft.client;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.MinecraftAppletImpl;
import net.minecraft.src.Session;
import net.minecraft.src.canvas.CanvasMinecraftApplet;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Canvas;

// Referenced classes of package net.minecraft.client:
//            net.minecraft.client.Minecraft

public class MinecraftApplet extends Applet {

    public MinecraftApplet() {
        mcThread = null;
    }

    public void init() {
        mcCanvas = new CanvasMinecraftApplet(this);
        boolean flag = false;
        if (getParameter("fullscreen") != null) {
            flag = getParameter("fullscreen").equalsIgnoreCase("true");
        }
        mc = new MinecraftAppletImpl(this, this, mcCanvas, this, getWidth(), getHeight(), flag);
        mc.minecraftUri = getDocumentBase().getHost();
        if (getDocumentBase().getPort() > 0) {
            mc.minecraftUri += ":" + getDocumentBase().getPort();
        }
        if (getParameter("username") != null && getParameter("sessionid") != null) {
            mc.session = new Session(getParameter("username"), getParameter("sessionid"));
            System.out.println((new StringBuilder()).append("Setting user: ").append(mc.session.playerName).append(", ").append(mc.session.field_6543_c).toString());
            if (getParameter("mppass") != null) {
                mc.session.field_6542_d = getParameter("mppass");
            }
        } else {
            mc.session = new Session("Player", "");
        }
        if (getParameter("loadmap_user") != null && getParameter("loadmap_id") != null) {
            mc.field_6310_s = getParameter("loadmap_user");
            mc.field_6309_t = Integer.parseInt(getParameter("loadmap_id"));
        } else if (getParameter("server") != null && getParameter("port") != null) {
            mc.setServer(getParameter("server"), Integer.parseInt(getParameter("port")));
        }
        mc.field_6317_l = true;
        setLayout(new BorderLayout());
        add(mcCanvas, "Center");
        mcCanvas.setFocusable(true);
        validate();
        return;
    }

    public void func_6233_a() {
        if (mcThread != null) {
            return;
        } else {
            mcThread = new Thread(mc, "net.minecraft.client.Minecraft main thread");
            mcThread.start();
            return;
        }
    }

    public void start() {
        if (mc != null) {
            mc.isWorldLoaded = false;
        }
    }

    public void stop() {
        if (mc != null) {
            mc.isWorldLoaded = true;
        }
    }

    public void destroy() {
        shutdown();
    }

    public void shutdown() {
        if (mcThread == null) {
            return;
        }
        mc.shutdown();
        try {
            mcThread.join(10000L);
        } catch (InterruptedException interruptedexception) {
            try {
                mc.shutdownMinecraftApplet();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        mcThread = null;
    }

    public void clearApplet() {
        mcCanvas = null;
        mc = null;
        mcThread = null;
        try {
            removeAll();
            validate();
        } catch (Exception exception) {
        }
    }

    private Canvas mcCanvas;
    private Minecraft mc;
    private Thread mcThread;
}
